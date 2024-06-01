package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.DataEntry;
import com.pa.pa_ternak.data.Feeder;
import com.pa.pa_ternak.data.MantriHewan;
import com.pa.pa_ternak.data.Pegawai;
import com.pa.pa_ternak.data.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminController {

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
    private TextField txtnama;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtpass;

    @FXML
    private ComboBox<String> comborole;

    @FXML
    private Button btntambah;

    @FXML
    private Tab tablihat;

    @FXML
    private ListView<Pegawai> listViewAkun;

    @FXML
    private Slider sliderUkuranTeks;

    private ArrayList<Pegawai> akunList = new ArrayList<>();
    private ArrayList<Admin> adminList = new ArrayList<>();

    @FXML
    public void initialize() {
        lblmanajemenAkun.setOnMouseClicked(this::handleManajemenAkunClick);
        lblmanajemenHewan.setOnMouseClicked(this::handleManajemenHewanClick);
        lblmanajemenPakan.setOnMouseClicked(this::handleManajemenPakanClick);
        lblmanajemenVaksin.setOnMouseClicked(this::handleManajemenVaksinClick);

        adminList.add(new Admin("1", "admin", "a", "Admin"));


        comborole.getItems().addAll("Data Entry", "Mantri Hewan", "Feeder");


        tablihat.setOnSelectionChanged(event -> {
            if (tablihat.isSelected()) {
                updateListView();
            }
        });


        sliderUkuranTeks.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewAkun.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
        });


        listViewAkun.setCellFactory(new Callback<ListView<Pegawai>, ListCell<Pegawai>>() {
            @Override
            public ListCell<Pegawai> call(ListView<Pegawai> listView) {
                return new AkunListCell();
            }
        });
    }
    @FXML
    public void handleManajemenAkunClick(MouseEvent event) {
        try {
            ((Application) Application.getInstance()).showMenuAdminScene(lblnama.getText());
        } catch (IOException e) {
            System.out.println("Error loading Menu_Admin_manajemen_hewan.fxml: " + e.getMessage());
        }
    }


    @FXML
    public void handleManajemenHewanClick(MouseEvent event) {
        try {
            ((Application) Application.getInstance()).showMenuAdminManajemenHewan(lblnama.getText());
        } catch (IOException e) {
            System.out.println("Error loading Menu_Admin_manajemen_hewan.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void handleManajemenPakanClick(MouseEvent event) {
        try {
            ((Application) Application.getInstance()).showMenuAdminManajemenPakan(lblnama.getText());
        } catch (IOException e) {
            System.out.println("Error loading Menu_Admin_manajemen_hewan.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void handleManajemenVaksinClick(MouseEvent event) {
        try {
            ((Application) Application.getInstance()).showMenuAdminManajemenVaksin(lblnama.getText());
        } catch (IOException e) {
            System.out.println("Error loading Menu_Admin_manajemen_hewan.fxml: " + e.getMessage());
        }
    }

    @FXML
    void clicktablihat(ActionEvent event) {
        updateListView();
    }

    private boolean isIdExists(String id) {
        for (Pegawai pegawai : Application.getInstance().getDataStore().getPegawaiList()) {
            if (pegawai.getUserid().equals(id)) {
                return true;
            }
        }
        return false;
    }


    @FXML
    void tambahdataclick(ActionEvent event) {
        try {
            String userid = txtid.getText();
            String nama = txtnama.getText().trim();
            String password = txtpass.getText().trim();
            String role = comborole.getValue();

            if (nama.isEmpty() || password.isEmpty() || role == null) {
                showAlert("Input Error", "Tolong Isi Semua Kolom");
                return;
            }

            if (!userid.matches("[A-Za-z0-9]{1,4}")) {
                showAlert("Input Error", "Id tidak boleh - dan Tidak Boleh Lebih dari 4 digit");
                return;
            }

            if (isIdExists(userid)) {
                showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
                return;
            }



            Pegawai pegawai;
            switch (role) {
                case "Data Entry":
                    pegawai = new DataEntry(userid, nama, password);
                    break;
                case "Mantri Hewan":
                    pegawai = new MantriHewan(userid, nama, password);
                    break;
                case "Feeder":
                    pegawai = new Feeder(userid, nama, password);
                    break;
                default:
                    throw new IllegalArgumentException("Role tidak valid");
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Penambahan Data");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Ingin menambah data ini?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
//                akunList.add(pegawai);
                Application.getInstance().getDataStore().addPegawai(pegawai);
                clearFields();
                updateListView();
                showAlert("Sukses", "Akun Brehasil Ditambahkans.");
            }

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtnama.clear();
        txtpass.clear();
        comborole.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateListView() {
        listViewAkun.getItems().clear();
//        listViewAkun.getItems().addAll(akunList);
        listViewAkun.getItems().addAll(Application.getInstance().getDataStore().getPegawaiList());
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
        lblnama.setText("Welcome! >> admin");
        lblrole.setText("Role : " + role);
    }

    private class AkunListCell extends ListCell<Pegawai> {
        private HBox content;
        private Label akunInfo;
        private Button editButton;
        private Button deleteButton;

        public AkunListCell() {
            super();
            akunInfo = new Label();
            editButton = new Button("Edit");
            deleteButton = new Button("Hapus");
            deleteButton.setId("deleteButton");

            editButton.setOnAction(event -> {
                Pegawai pegawai = getItem();
                if (pegawai != null) {
                    editAkun(pegawai);
                }
            });

            deleteButton.setOnAction(event -> {
                Pegawai pegawai = getItem();
                if (pegawai != null) {
                    deleteAkun(pegawai);
                }
            });

            content = new HBox(akunInfo, editButton, deleteButton);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Pegawai pegawai, boolean empty) {
            super.updateItem(pegawai, empty);
            if (pegawai != null && !empty) {
                akunInfo.setText(pegawai.toString());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    private void deleteAkun(Pegawai pegawai) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Penghapusan");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Yakin ingin menghapus akun ini?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
//            akunList.remove(pegawai);
            Application.getInstance().getDataStore().removePegawai(pegawai);
            updateListView();
            showAlert("Success", "Account deleted successfully.");
        }
    }

    private void editAkun(Pegawai pegawai) {
        try {
            Application.getInstance().showEditAkun(pegawai);
            listViewAkun.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
