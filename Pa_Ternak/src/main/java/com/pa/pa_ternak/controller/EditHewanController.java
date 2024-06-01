package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class EditHewanController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private TextField txtusia;

    @FXML
    private TextField txtberat;

    @FXML
    private ComboBox<String> combojeniskelamin;

    @FXML
    private ComboBox<String> combojenis;

    @FXML
    private Button btnEdit;

    private Hewan hewan;

    @FXML
    public void initialize() {
        txtid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtid.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtusia.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtusia.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtberat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtberat.setText(oldValue);
            }
        });
    }

    public void setHewan(Hewan hewan) {
        this.hewan = hewan;
        txtid.setText(String.valueOf(hewan.getId()));
        txtnama.setText(hewan.getNama());
        txtusia.setText(String.valueOf(hewan.getUsia()));
        txtberat.setText(String.valueOf(hewan.getBerat()));
        combojeniskelamin.setValue(hewan.getJenisKelamin());
        combojenis.setValue(hewan.getJenis());
        combojeniskelamin.getItems().addAll("Jantan", "Betina");
        combojenis.getItems().addAll("Unggas", "Mamalia");
    }

    private boolean isIdExists(int id) {
        for (Hewan hewan : Application.getInstance().getDataStore().getHewanList()) {
            if (hewan.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void UbahDataClick(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtid.getText().trim());

        if (isIdExists(id)) {
            showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Pengubahan Data");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Ingin Mengubah data ini?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            hewan.setId(Integer.parseInt(txtid.getText()));
            hewan.setNama(txtnama.getText());
            hewan.setUsia(Integer.parseInt(txtusia.getText()));
            hewan.setBerat(Double.parseDouble(txtberat.getText()));
            hewan.setJenisKelamin(combojeniskelamin.getValue());
            hewan.setJenis(combojenis.getValue());

            Application.getInstance().getDataStore().updateHewan(hewan);
            // Show information dialog
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Konfirmasi");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Data berhasil diubah");
            infoAlert.showAndWait();
        }
        // Close the window
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }
}
