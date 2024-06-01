package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.DataStore;
import com.pa.pa_ternak.data.Feeder;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Pakan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FeederController {

    @FXML
    private AnchorPane navbaradmin;

    @FXML
    private Label lblmanajemenAkun;

    @FXML
    private Label lblmanajemenHewan;

    @FXML
    private Label lblmanajemenPakan;

    @FXML
    private Label lblmanajemenVaksin;

    @FXML
    private Label lblnama;

    @FXML
    private Label lblrole;

    @FXML
    private VBox home;

    @FXML
    private VBox home2;

    @FXML
    private ListView<Text> listViewHewanLapar;

    @FXML
    private ListView<Text> listViewPakan;

    @FXML
    private Button btnBeriPakan;

    private DataStore dataStore;
    private Feeder feeder;
    private Hewan selectedHewan;
    private Pakan selectedPakan;

    @FXML
    public void initialize() {
        dataStore = Application.getInstance().getDataStore();
        updateListViewHewanLapar();
        updateListViewPakan();
    }

    private void updateListViewHewanLapar() {
        List<Hewan> hewanList = dataStore.getHewanList();
        listViewHewanLapar.getItems().clear();
        for (Hewan hewan : hewanList) {
            Text text = new Text(hewan.deskripsi());
            if (hewan.isStatusLapar()) {
                text.setFill(Color.RED);
                text.setText(text.getText() + " - Lapar");
            } else {
                text.setFill(Color.GREEN);
                text.setText(text.getText() + " - Kenyang");
            }
            listViewHewanLapar.getItems().add(text);
        }
    }

    private void updateListViewPakan() {
        List<Pakan> pakanList = dataStore.getPakanList();
        listViewPakan.getItems().clear();
        for (Pakan pakan : pakanList) {
            Text text = new Text(pakan.getNama() + " - " + pakan.getJumlahPakan() + " kg (Kadaluwarsa: " + pakan.getTanggalKadaluarsa() + ")");
            listViewPakan.getItems().add(text);
        }
    }

    public void OnClick(MouseEvent mouseEvent) {
    }

    public void LogoutClick(ActionEvent actionEvent) {
        try {
            Application.getInstance().showLoginScene();
        } catch (IOException e) {
            System.out.println("Error loading login.fxml: " + e.getMessage());
        }
    }

    public void setUserInfo(String username, String role) {
        lblnama.setText("Welcome! >> " + username);
        lblrole.setText("Role : " + role);
    }

    @FXML
    private void handleHewanSelected(MouseEvent event) {
        String selectedItem = listViewHewanLapar.getSelectionModel().getSelectedItem().getText();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(" - ");
            int hewanId = Integer.parseInt(parts[0]);
            selectedHewan = dataStore.getHewanList().stream()
                    .filter(hewan -> hewan.getId() == hewanId)
                    .findFirst()
                    .orElse(null);
        }
    }

    @FXML
    private void handlePakanSelected(MouseEvent event) {
        String selectedItem = listViewPakan.getSelectionModel().getSelectedItem().getText();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(" - ");
            String pakanNama = parts[0];
            selectedPakan = dataStore.getPakanList().stream()
                    .filter(pakan -> pakan.getNama().equals(pakanNama))
                    .findFirst()
                    .orElse(null);
        }
    }

    @FXML
    private void handleBeriPakan(ActionEvent event) {
        if (selectedHewan != null && selectedPakan != null) {
            if (!selectedHewan.isStatusLapar()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hewan Kenyang");
                alert.setHeaderText(null);
                alert.setContentText("Hewan ini sudah kenyang dan tidak bisa diberi pakan lagi!");
                alert.showAndWait();
                return;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Beri Pakan");
            dialog.setHeaderText("Masukkan jumlah pakan yang akan diberikan:");
            dialog.setContentText("Jumlah:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(jumlahStr -> {
                try {
                    int jumlah = Integer.parseInt(jumlahStr);
                    if (jumlah <= 0 || jumlah > 5) {
                        showAlert("Input Error", "Jumlah pakan harus antara 1 dan 5.");
                        return;
                    }
                    if (selectedPakan.pakaiPakan(jumlah)) {
                        selectedHewan.setStatusLapar(false);
                        selectedHewan.setPakanYangDiberikan(selectedPakan.getNama());
                        dataStore.updateHewan(selectedHewan);
                        dataStore.updatePakan(selectedPakan);
                        updateListViewHewanLapar();
                        updateListViewPakan();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Pakan Diberikan");
                        alert.setHeaderText(null);
                        alert.setContentText("Pakan berhasil diberikan kepada " + selectedHewan.getNama());
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Gagal Memberi Pakan");
                        alert.setHeaderText(null);
                        alert.setContentText("Jumlah pakan tidak mencukupi!");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Jumlah pakan harus berupa angka.");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pilih Hewan dan Pakan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih hewan dan pakan terlebih dahulu!");
            alert.showAndWait();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
