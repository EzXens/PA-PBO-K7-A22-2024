package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Mamalia;
import com.pa.pa_ternak.data.Unggas;
import com.pa.pa_ternak.data.Vaksin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AdminManajemenVaksinController {

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
    private TextField txtdosis;

    @FXML
    private TextField txtjumlah;

    @FXML
    private ComboBox<String> combojenisvaksin;

    @FXML
    private DatePicker datekadaluwarsa;

    @FXML
    private Button btntambah;

    @FXML
    private Tab tablihat;

    @FXML
    private ListView<Vaksin> listViewVaksin;

    @FXML
    private Slider sliderUkuranTeks;

    @FXML
    private Tab tabhewanyangdiberivaksin;

    @FXML
    private ListView<Hewan> listViewHewanVaksin;

    private ArrayList<Vaksin> vaksinList = new ArrayList<>();


    @FXML
    public void initialize() {
        lblmanajemenHewan.setOnMouseClicked(this::handleManajemenHewanClick);
        lblmanajemenPakan.setOnMouseClicked(this::handleManajemenPakanClick);
        lblmanajemenVaksin.setOnMouseClicked(this::handleManajemenVaksinClick);
        lblmanajemenAkun.setOnMouseClicked(this::handleManajemenAkunClick);

        combojenisvaksin.getItems().addAll("Vaksin Virus Hidup", "Vaksin Virus Mati" , "Vaksin Bakteri","Vaksin Toxoid");


        // Add a listener to the "Lihat" tab to update the ListView when selected
        tablihat.setOnSelectionChanged(event -> {
            if (tablihat.isSelected()) {
                updateListView();
            }
        });

        // Add listener to slider to change text size
        sliderUkuranTeks.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewVaksin.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
        });

        // Set custom cell factory for ListView
        listViewVaksin.setCellFactory(new Callback<ListView<Vaksin>, ListCell<Vaksin>>() {
            @Override
            public ListCell<Vaksin> call(ListView<Vaksin> listView) {
                return new VaksinListCell();
            }
        });

        // Set custom cell factory for ListViewHewanLapar
        listViewHewanVaksin.setCellFactory(new Callback<ListView<Hewan>, ListCell<Hewan>>() {
            @Override
            public ListCell<Hewan> call(ListView<Hewan> listView) {
                return new AdminManajemenVaksinController.HewanListCell();
            }
        });

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
            // Menampilkan Menu_Admin_manajemen_hewan.fxml
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

    private boolean isIdExists(Integer id) {
        for (Vaksin vaksin : Application.getInstance().getDataStore().getVaksinList()) {
            if (vaksin.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void clicktablihat(ActionEvent event) {
        updateListView();
    }

    @FXML
    void tambahdataclick(ActionEvent event) {
        try {
            // Get the input values
            Integer id = Integer.parseInt(txtid.getText().trim());
            String nama = txtnama.getText().trim();
            String jenisVaksin = combojenisvaksin.getValue();
            Double dosis = Double.parseDouble(txtdosis.getText().trim());
            Integer jumlah = Integer.parseInt(txtjumlah.getText().trim());
            LocalDate tanggalKadaluarsa = datekadaluwarsa.getValue();

            if (nama.isEmpty() || jenisVaksin.isEmpty()) {
                showAlert("Input Error", "Tolong Isi Semua Kolom");
                return;
            }


            if (isIdExists(id)) {
                showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
                return;
            }


            Vaksin vaksin = new Vaksin(id,nama,tanggalKadaluarsa,dosis,jenisVaksin,jumlah);


            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Penambahan Data");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Ingin menambah data ini?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
               Application.getInstance().getDataStore().addVaksin(vaksin);

                // Clear the input fields
                txtid.clear();
                txtnama.clear();
                combojenisvaksin.setValue(null);
                txtjumlah.clear();
                txtdosis.clear();
                datekadaluwarsa.setValue(null);

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
            // Handle invalid number input
            System.out.println("Input tidak valid: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle invalid jenis input
            System.out.println("Jenis hewan tidak valid: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
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
        listViewVaksin.getItems().clear();
        listViewVaksin.getItems().addAll(Application.getInstance().getDataStore().getVaksinList());
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
        lblnama.setText("Welcome! >> admin " + username);
        lblrole.setText("Role : " + role);
    }


    // Custom ListCell class for Hewan
    private class HewanListCell extends ListCell<Hewan> {
        private HBox content;
        private Label hewanInfo;

        public HewanListCell() {
            super();
            hewanInfo = new Label();
            hewanInfo.setStyle("-fx-text-fill: green;");
            content = new HBox(hewanInfo);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Hewan hewan, boolean empty) {
            super.updateItem(hewan, empty);
            if (empty || hewan == null) {
                setGraphic(null);
            } else {
                hewanInfo.setText("ID: " + hewan.getId() + ", Hewan: " + hewan.getNama() + ", vaksin: " + hewan.getVaksinyangDiberikan());
                setGraphic(content);
            }
        }
    }

    private class VaksinListCell extends ListCell<Vaksin> {
        private HBox content;
        private Label vaksinInfo;
        private Button editButton;
        private Button deleteButton;

        public VaksinListCell() {
            super();
            vaksinInfo = new Label();
            editButton = new Button("Edit");
            deleteButton = new Button("Hapus");
            deleteButton.setId("deleteButton");

            editButton.setOnAction(event -> {
                Vaksin vaksin = getItem();
                if (vaksin != null) {
                    editVaksin(vaksin);
                }
            });

            deleteButton.setOnAction(event -> {
                Vaksin vaksin = getItem();
                if (vaksin != null) {
                    deleteVaksin(vaksin);
                }
            });

            content = new HBox(vaksinInfo, editButton, deleteButton);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Vaksin vaksin, boolean empty) {
            super.updateItem(vaksin, empty);
            if (vaksin != null && !empty) {
                vaksinInfo.setText(vaksin.deskripsi());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    // Method to delete a Hewan object
    private void deleteVaksin(Vaksin vaksin) {
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Penghapusan");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Yakin ingin menghapus data ini?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Application.getInstance().getDataStore().removeVaksin(vaksin);
            updateListView();

            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setTitle("Konfirmasi");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Data berhasil dihapus");
            infoAlert.showAndWait();
        }
    }

    @FXML
    void clicktabhewanyangdiberivaksin(Event event) {
        if (tabhewanyangdiberivaksin.isSelected()) {
            List<Hewan> hewanList = Application.getInstance().getDataStore().getHewanList();
            List<Hewan> hewanDiberiVaksinList = new ArrayList<>();

            for (Hewan hewan : hewanList) {
                if (!hewan.isStatusVaksin()) {
                    hewanDiberiVaksinList.add(hewan);
                }
            }

            // Menampilkan informasi dalam ListView
            listViewHewanVaksin.getItems().clear();
            listViewHewanVaksin.getItems().addAll(hewanDiberiVaksinList);
        }
    }

    private void editVaksin(Vaksin vaksin) {
        try {
            Application.getInstance().showEditVaksin(vaksin);
            listViewVaksin.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
