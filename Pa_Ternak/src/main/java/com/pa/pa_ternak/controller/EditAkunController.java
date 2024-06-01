package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Pegawai;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class EditAkunController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private TextField txtpass;

    @FXML
    private ComboBox<String> comborole;

    @FXML
    private Button btnEdit;

    private Pegawai pegawai;

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
        txtid.setText(pegawai.getUserid());
        txtnama.setText(pegawai.getUsername());
        txtpass.setText(pegawai.getPassword());
        comborole.setValue(pegawai.getRole());

        comborole.getItems().addAll("Data Entry", "Mantri Hewan", "Feeder");
    }

    private boolean isIdExists(String id) {
        for (Pegawai pegawai : Application.getInstance().getDataStore().getPegawaiList()) {
            if (pegawai.getUserid().equals(id)) {
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
        String userid = txtid.getText().trim();

        if (!userid.matches("[A-Za-z0-9]{1,4}")) {
            showAlert("Input Error", "Id tidak boleh - dan Tidak Boleh Lebih dari 4 digit");
            return;
        }

        if (isIdExists(userid)) {
            showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Pengubahan Data");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Ingin mengubah data ini?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pegawai.setUserid(txtid.getText());
            pegawai.setUsername(txtnama.getText());
            pegawai.setPassword(txtpass.getText());
            pegawai.setRole(comborole.getValue());

            Application.getInstance().getDataStore().updatePegawai(pegawai);
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
