package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Admin;
import com.pa.pa_ternak.data.*;
import com.pa.pa_ternak.data.DataStore;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Label;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import org.w3c.dom.CDATASection;


public class loginController {

    @FXML
    private TextField txtusername;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private Label lblerror;

    private ArrayList<Admin> Akuns = new ArrayList<>();
    private String username; // Field untuk menyimpan username

    private ArrayList<Pegawai> akunList = new ArrayList<>();
    private ArrayList<Admin> adminList = new ArrayList<>();
    private DataStore dataStore;


    @FXML
    public void initialize() {
        // Mendapatkan instance DataStore dari Application
        this.dataStore = Application.getInstance().getDataStore();

        // Admin
        adminList.add(new Admin("1", "admin", "a", "Admin"));
    }

    @FXML
    void login_click(ActionEvent event) {
        String inputUsername = txtusername.getText().trim();
        String password = txtpassword.getText().trim();

        // Check admin accounts
        for (Admin admin : adminList) {
            if (admin.getUsername().equals(inputUsername) && admin.getPassword().equals(password)) {
                try {
                    Application.getInstance().showMenuAdminScene(admin.getUsername());
                } catch (IOException e) {
                    showAlert("Error", "Failed to load Admin menu.");
                }
                return;
            }
        }

        // Check pegawai accounts
        for (Pegawai pegawai : dataStore.getPegawaiList()) {
            if (pegawai.getUsername().equals(inputUsername) && pegawai.getPassword().equals(password)) {
                if (pegawai instanceof DataEntry) {
                    try {
                        Application.getInstance().showDataEntryMenu(pegawai.getUsername());
                    } catch (IOException e) {
                        showAlert("Error", "Failed to load Data Entry menu.");
                    }
                } else if (pegawai instanceof MantriHewan) {
                    try {
                        Application.getInstance().showMantriMenu(pegawai.getUsername());
                    } catch (IOException e) {
                        showAlert("Error", "Failed to load Mantri Hewan menu.");
                    }
                } else if (pegawai instanceof Feeder) {
                    try {
                        Application.getInstance().showFeederMenu(pegawai.getUsername());
                    } catch (IOException e) {
                        showAlert("Error", "Failed to load Feeder menu.");
                    }
                }
                return;
            }
        }

        showAlert("Login Failed", "Username Atau Password Salahhh");



    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
