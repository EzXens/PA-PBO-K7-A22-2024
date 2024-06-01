package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Pakan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.util.Optional;

public class EditPakanController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private TextField txtjumlahpakan;

    @FXML
    private ComboBox<String> combojenispakan;

    @FXML
    private DatePicker txttanggal;

    @FXML
    private Button btnEdit;

    private Pakan pakan;

    @FXML
    public void initialize() {
        txtid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtid.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtjumlahpakan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtjumlahpakan.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


    }

    public void setPakan(Pakan pakan) {
        this.pakan = pakan;
        txtid.setText(String.valueOf(pakan.getId()));
        txtnama.setText(pakan.getNama());
        txtjumlahpakan.setText(String.valueOf(pakan.getJumlahPakan()));
        combojenispakan.setValue(pakan.getJenis());
        txttanggal.setValue(pakan.getTanggalKadaluarsa());
        combojenispakan.getItems().addAll("Hijauan", "Konsentrat", "Suplemen");
    }

    private boolean isIdExists(Integer id) {
        for (Pakan pakan : Application.getInstance().getDataStore().getPakanList()) {
            if (pakan.getId() == id) {
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
            pakan.setId(Integer.parseInt(txtid.getText()));
            pakan.setNama(txtnama.getText());
            pakan.setJumlahPakan(Integer.parseInt(txtjumlahpakan.getText()));
            pakan.setTanggalKadaluarsa(txttanggal.getValue());
            pakan.setJenis(combojenispakan.getValue());

            Application.getInstance().getDataStore().updatePakan(pakan);

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
