package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Pegawai;
import com.pa.pa_ternak.data.Unggas;
import com.pa.pa_ternak.data.Mamalia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class AdminManajemenHewanController {

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
    private ListView<Hewan> listViewHewan;

    @FXML
    private Slider sliderUkuranTeks;

    private ArrayList<Hewan> hewanList = new ArrayList<>();


    @FXML
    public void initialize() {
//        lblmanajemenHewan.setOnMouseClicked(this::handleManajemenHewanClick);
        lblmanajemenPakan.setOnMouseClicked(this::handleManajemenPakanClick);
        lblmanajemenVaksin.setOnMouseClicked(this::handleManajemenVaksinClick);
        lblmanajemenAkun.setOnMouseClicked(this::handleManajemenAkunClick);

        combojeniskelamin.getItems().addAll("Jantan", "Betina");

        combojenis.getItems().addAll("Unggas", "Mamalia");

        tablihat.setOnSelectionChanged(event -> {
            if (tablihat.isSelected()) {
                updateListView();
            }
        });


        sliderUkuranTeks.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewHewan.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
        });


        listViewHewan.setCellFactory(new Callback<ListView<Hewan>, ListCell<Hewan>>() {
            @Override
            public ListCell<Hewan> call(ListView<Hewan> listView) {
                return new HewanListCell();
            }
        });

        // Add listener to restrict input to integers
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
    @FXML
    public void handleManajemenAkunClick(MouseEvent event) {
        try {
            ((Application) Application.getInstance()).showMenuAdminScene(lblnama.getText());
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
            // Menampilkan Menu_Admin_manajemen_hewan.fxml
            ((Application) Application.getInstance()).showMenuAdminManajemenVaksin(lblnama.getText());
        } catch (IOException e) {
            System.out.println("Error loading Menu_Admin_manajemen_hewan.fxml: " + e.getMessage());
        }
    }


    @FXML
    void clicktablihat(ActionEvent event) {
        updateListView();
    }

    private boolean isIdExists(int id) {
        for (Hewan hewan : Application.getInstance().getDataStore().getHewanList()) {
            if (hewan.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void tambahdataclick(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(txtid.getText().trim());
            String nama = txtnama.getText().trim();
            String jenis = combojenis.getValue();
            Integer usia = Integer.parseInt(txtusia.getText().trim());
            Double berat = Double.parseDouble(txtberat.getText().trim());
            String jenis_kelamin = combojeniskelamin.getValue();

            if (nama.isEmpty() || jenis.isEmpty() || jenis_kelamin.isEmpty()) {
                showAlert("Input Error", "Tolong Isi Semua Kolom");
                return;
            }

            if (isIdExists(id)) {
                showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
                return;
            }

            Hewan hewan;
            if (jenis.equalsIgnoreCase("Unggas")) {
                hewan = new Unggas(id, nama, usia, berat, jenis_kelamin);
            } else if (jenis.equalsIgnoreCase("Mamalia")) {
                hewan = new Mamalia(id, nama, usia, berat, jenis_kelamin);
            } else {
                throw new IllegalArgumentException("Jenis hewan tidak valid");
            }


            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Penambahan Data");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Ingin menambah data ini?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Add the new Hewan object to the ArrayList
                Application.getInstance().getDataStore().addHewan(hewan);

                // Clear the input fields
                txtid.clear();
                txtnama.clear();
                combojenis.setValue(null);
                txtusia.clear();
                txtberat.clear();
                combojeniskelamin.setValue(null);

                // Update the ListView
                updateListView();

                // Show information dialog
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Konfirmasi");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Data berhasil ditambah");
                infoAlert.showAndWait();
            }

        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Jenis hewan tidak valid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void updateListView() {
        listViewHewan.getItems().clear();
        listViewHewan.getItems().addAll(Application.getInstance().getDataStore().getHewanList());
    }

    public void OnClick(MouseEvent mouseEvent) {
    }

    public void LogoutClick(ActionEvent actionEvent) {
        try {
            ((Application) Application.getInstance()).showLoginScene();
        } catch (IOException e) {
            // Menggunakan System.out.println() untuk mencetak pesan kesalahan
            System.out.println("Error loading login.fxml: " + e.getMessage());
        }
    }

    public void setUserInfo(String username, String role) {
        lblnama.setText("Welcome! >> admin" );
        lblrole.setText("Role : " + role);

        if ("Data Entry".equals(role)) {
            lblnama.setText("Welcome! >>" + username);
            lblrole.setText("Role : " + role);
            lblmanajemenAkun.setVisible(false);
            lblmanajemenPakan.setVisible(false);
            lblmanajemenVaksin.setVisible(false);
        }
    }


    private class HewanListCell extends ListCell<Hewan> {
        private HBox content;
        private Label hewanInfo;
        private Button editButton;
        private Button deleteButton;

        public HewanListCell() {
            super();
            hewanInfo = new Label();
            editButton = new Button("Edit");
            deleteButton = new Button("Hapus");
            deleteButton.setId("deleteButton");

            editButton.setOnAction(event -> {
                Hewan hewan = getItem();
                if (hewan != null) {
                    editHewan(hewan);
                }
            });

            deleteButton.setOnAction(event -> {
                Hewan hewan = getItem();
                if (hewan != null) {
                    deleteHewan(hewan);
                }
            });

            content = new HBox(hewanInfo, editButton, deleteButton);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Hewan hewan, boolean empty) {
            super.updateItem(hewan, empty);
            if (hewan != null && !empty) {
                hewanInfo.setText(hewan.deskripsi("pakan", "vaksin", "status"));
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    private void deleteHewan(Hewan hewan) {
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Penghapusan");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Yakin ingin menghapus data ini?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Application.getInstance().getDataStore().removeHewan(hewan);
            updateListView();

            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setTitle("Konfirmasi");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Data berhasil dihapus");
            infoAlert.showAndWait();
        }
    }


    private void editHewan(Hewan hewan) {
        try {
            Application.getInstance().showEditHewan(hewan);
            listViewHewan.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
