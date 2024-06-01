package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Vaksin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class EditVaksinController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private TextField txtdosis;

    @FXML
    private TextField txtjumlah;

    @FXML
    private ComboBox<String> combojenisvaksin;

    @FXML
    private DatePicker datekadaluwarsa;

    @FXML
    private Button btnEdit;

    private Vaksin vaksin;

    public void setVaksin(Vaksin vaksin) {
        this.vaksin = vaksin;
        txtid.setText(String.valueOf(vaksin.getId()));
        txtnama.setText(vaksin.getNama());
        txtdosis.setText(String.valueOf(vaksin.getDosis()));
        txtjumlah.setText(String.valueOf(vaksin.getJumlahVaksin()));
        combojenisvaksin.setValue(vaksin.getJenisVaksin());
        datekadaluwarsa.setValue(vaksin.getTanggalKadaluarsa());
        combojenisvaksin.getItems().addAll("Vaksin Virus Hidup", "Vaksin Virus Mati" , "Vaksin Bakteri","Vaksin Toxoid");
    }

    @FXML
    public void initialize() {
        txtid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtid.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        txtjumlah.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtjumlah.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        txtdosis.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtdosis.setText(oldValue);
            }
        });
    }

    private boolean isIdExists(Integer id) {
        for (Vaksin vaksin : Application.getInstance().getDataStore().getVaksinList()) {
            if (vaksin.getId() == id) {
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

        Integer id = Integer.parseInt(txtid.getText().trim());

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
            vaksin.setId(Integer.parseInt(txtid.getText()));
            vaksin.setNama(txtnama.getText());
            vaksin.setDosis(Double.parseDouble(txtdosis.getText()));
            vaksin.setJumlahVaksin(Integer.parseInt(txtjumlah.getText()));
            vaksin.setJenisVaksin(combojenisvaksin.getValue());
            vaksin.setTanggalKadaluarsa(datekadaluwarsa.getValue());

            Application.getInstance().getDataStore().updateVaksin(vaksin);


            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Konfirmasi");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Data berhasil diubah");
            infoAlert.showAndWait();
        }

        Stage stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }
}
