package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MantriHewanController {

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
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private ComboBox<String> combojenis;

    @FXML
    private TextField txtusia;

    @FXML
    private TextField txtberat;

    @FXML
    private ComboBox<String> combojeniskelamin;

    @FXML
    private Button btntambah;

    @FXML
    private Tab tablihat;

    @FXML
    private Slider sliderUkuranTeks;

    @FXML
    private ListView<Text> listViewHewanLapar;

    @FXML
    private ListView<Text> listViewPakan;

    private DataStore dataStore;
    private Hewan selectedHewan;
    private Vaksin selectedVaksin;

    @FXML
    public void initialize() {
        dataStore = Application.getInstance().getDataStore();
        updateListViewHewanVaksin();
        updateListViewVaksin();
    }

    public void OnClick(MouseEvent mouseEvent) {
    }

    public void LogoutClick(ActionEvent actionEvent) {
        try {
            ((Application) Application.getInstance()).showLoginScene();
        } catch (IOException e) {
            System.out.println("Error loading login.fxml: " + e.getMessage());
        }
    }

    public void setUserInfo(String username, String role) {
        lblnama.setText("Welcome! >> " + username);
        lblrole.setText("Role : " + role);
    }

    private void updateListViewHewanVaksin() {
        List<Hewan> hewanList = dataStore.getHewanList();
        listViewHewanLapar.getItems().clear();
        for (Hewan hewan : hewanList) {
            Text text = new Text(hewan.deskripsi());
            if (hewan.isStatusVaksin()) {
                text.setFill(Color.RED);
                text.setText(text.getText() + " - Belum Vaksin");
            } else {
                text.setFill(Color.GREEN);
                text.setText(text.getText() + " - Sudah Vaksin");
            }
            listViewHewanLapar.getItems().add(text);
        }
    }

    private void updateListViewVaksin() {
        List<Vaksin> vaksinList = dataStore.getVaksinList();
        listViewPakan.getItems().clear();
        for (Vaksin vaksin : vaksinList) {
            Text text = new Text(vaksin.getNama() + " - " + vaksin.getJumlahVaksin() + " biji (Kadaluwarsa: " + vaksin.getTanggalKadaluarsa() + ")");
            listViewPakan.getItems().add(text);
        }
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
    private void handleVaksinSelected(MouseEvent event) {
        String selectedItem = listViewPakan.getSelectionModel().getSelectedItem().getText();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(" - ");
            String vaksinNama = parts[0];
            selectedVaksin = dataStore.getVaksinList().stream()
                    .filter(vaksin -> vaksin.getNama().equals(vaksinNama))
                    .findFirst()
                    .orElse(null);
        }
    }

    @FXML
    private void handleBeriPakan(ActionEvent event) {
        if (selectedHewan != null && selectedVaksin != null) {
            if (!selectedHewan.isStatusVaksin()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hewan sudah di beri vaksin");
                alert.setHeaderText(null);
                alert.setContentText("Hewan ini tidak bisa diberi vaksin lagi!");
                alert.showAndWait();
                return;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Beri vaksin");
            dialog.setHeaderText("Masukkan jumlah vaksin yang akan diberikan:");
            dialog.setContentText("Jumlah:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(jumlahStr -> {
                try {
                    int jumlah = Integer.parseInt(jumlahStr);
                    if (jumlah <= 0 || jumlah > 3) {
                        showAlert("Input Error", "Jumlah vaksin harus antara 1 dan 3.");
                        return;
                    }
                    if (selectedVaksin.pakaiVaksin(jumlah)) {
                        selectedHewan.setStatusVaksin(false);
                        selectedHewan.setVaksinyangDiberikan(selectedVaksin.getNama());
                        dataStore.updateHewan(selectedHewan);
                        dataStore.updateVaksin(selectedVaksin);
                        updateListViewHewanVaksin();
                        updateListViewVaksin();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vaksin Diberikan");
                        alert.setHeaderText(null);
                        alert.setContentText("Vaksin berhasil diberikan kepada " + selectedHewan.getNama());
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Gagal Memberi Vaksin");
                        alert.setHeaderText(null);
                        alert.setContentText("Jumlah vaksin tidak mencukupi!");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Jumlah vaksin harus berupa angka.");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pilih Hewan dan Vaksin");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih hewan dan vaksin terlebih dahulu!");
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
