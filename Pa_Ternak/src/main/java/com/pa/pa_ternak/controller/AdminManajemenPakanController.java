package com.pa.pa_ternak.controller;

import com.pa.pa_ternak.Application;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Pakan;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminManajemenPakanController {

    @FXML
    private VBox home;

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
    private VBox home2;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnama;

    @FXML
    private TextField txtjumlahpakan;

    @FXML
    private ComboBox<String> combojenispakan;

    @FXML
    private Button btntambah;

    @FXML
    private DatePicker txttanggal;

    @FXML
    private Tab tablihat;

    @FXML
    private Tab tabhewanyangdiberipakan;

    @FXML
    private Slider sliderUkuranTeks;

    @FXML
    private ListView<Pakan> listViewPakan;

    @FXML
    private ListView<Hewan> listViewHewanLapar;

    private ArrayList<Pakan> pakanList = new ArrayList<>();

    @FXML
    public void initialize() {
        lblmanajemenHewan.setOnMouseClicked(this::handleManajemenHewanClick);
        lblmanajemenPakan.setOnMouseClicked(this::handleManajemenPakanClick);
        lblmanajemenVaksin.setOnMouseClicked(this::handleManajemenVaksinClick);
        lblmanajemenAkun.setOnMouseClicked(this::handleManajemenAkunClick);

        // Initialize the ComboBox with jenis options
        combojenispakan.getItems().addAll("Hijauan", "Konsentrat", "Suplemen");

        // Add a listener to the "Lihat" tab to update the ListView when selected
        tablihat.setOnSelectionChanged(event -> {
            if (tablihat.isSelected()) {
                updateListView();
            }
        });

        // Add listener to slider to change text size
        sliderUkuranTeks.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewPakan.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
        });

        // Set custom cell factory for ListView
        listViewPakan.setCellFactory(new Callback<ListView<Pakan>, ListCell<Pakan>>() {
            @Override
            public ListCell<Pakan> call(ListView<Pakan> listView) {
                return new PakanListCell();
            }
        });

        // Set custom cell factory for ListViewHewanLapar
        listViewHewanLapar.setCellFactory(new Callback<ListView<Hewan>, ListCell<Hewan>>() {
            @Override
            public ListCell<Hewan> call(ListView<Hewan> listView) {
                return new HewanListCell();
            }
        });

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

    private boolean isIdExists(Integer id) {
        for (Pakan pakan : Application.getInstance().getDataStore().getPakanList()) {
            if (pakan.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void tambahdataclick(ActionEvent event) {
        try {
            // Get the input values
            Integer id = Integer.parseInt(txtid.getText().trim());
            String nama = txtnama.getText().trim();
            String jenis = combojenispakan.getValue();
            LocalDate tanggalKadaluarsa = txttanggal.getValue();
            Integer jumlahPakan = Integer.parseInt(txtjumlahpakan.getText().trim());

            if (nama.isEmpty() || jenis.isEmpty() || tanggalKadaluarsa == null || jumlahPakan == null) {
                showAlert("Input Error", "Tolong Isi Semua Kolom");
                return;
            }


            if (isIdExists(id)) {
                showAlert("Input Error", "ID sudah digunakan. Silakan gunakan ID lain.");
                return;
            }

            Pakan pakan = new Pakan(id, nama, jenis, tanggalKadaluarsa, jumlahPakan);

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Penambahan Data");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Ingin menambah data ini?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Application.getInstance().getDataStore().addPakan(pakan);

                txtid.clear();
                txtnama.clear();
                combojenispakan.setValue(null);
                txttanggal.setValue(null);
                txtjumlahpakan.clear();


                updateListView();

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
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
            System.out.println("Jenis pakan tidak valid: " + e.getMessage());
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
        listViewPakan.getItems().clear();
        listViewPakan.getItems().addAll(Application.getInstance().getDataStore().getPakanList());
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
        lblnama.setText("Welcome! >> admin " + username);
        lblrole.setText("Role : " + role);
    }

    @FXML
    void clicktabhewanyangdiberipakan(Event event) {
        if (tabhewanyangdiberipakan.isSelected()) {
            List<Hewan> hewanList = Application.getInstance().getDataStore().getHewanList();
            List<Hewan> hewanDiberiPakanList = new ArrayList<>();

            for (Hewan hewan : hewanList) {
                if (!hewan.isStatusLapar()) {
                    hewanDiberiPakanList.add(hewan);
                }
            }

            // Menampilkan informasi dalam ListView
            listViewHewanLapar.getItems().clear();
            listViewHewanLapar.getItems().addAll(hewanDiberiPakanList);
        }
    }


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
                hewanInfo.setText("ID: " + hewan.getId() + ", Hewan: " + hewan.getNama() + ", Pakan: " + hewan.getPakanYangDiberikan());
                setGraphic(content);
            }
        }
    }

    // Custom ListCell class for Pakan
    private class PakanListCell extends ListCell<Pakan> {
        private HBox content;
        private Label pakanInfo;
        private Button editButton;
        private Button deleteButton;

        public PakanListCell() {
            super();
            pakanInfo = new Label();
            pakanInfo.setStyle("-fx-text-fill: green;");
            editButton = new Button("Edit");
            deleteButton = new Button("Hapus");
            deleteButton.setId("deleteButton");

            editButton.setOnAction(event -> {
                Pakan pakan = getItem();
                if (pakan != null) {
                    editPakan(pakan);
                }
            });

            deleteButton.setOnAction(event -> {
                Pakan pakan = getItem();
                if (pakan != null) {
                    deletePakan(pakan);
                }
            });

            content = new HBox(pakanInfo, editButton, deleteButton);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Pakan pakan, boolean empty) {
            super.updateItem(pakan, empty);
            if (pakan != null && !empty) {
                pakanInfo.setText(pakan.deskripsi());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }


    private void deletePakan(Pakan pakan) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Penghapusan");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Yakin ingin menghapus data ini?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Application.getInstance().getDataStore().removePakan(pakan);
            updateListView();

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Konfirmasi");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Data berhasil dihapus");
            infoAlert.showAndWait();
        }
    }


    private void editPakan(Pakan pakan) {
        try {
            Application.getInstance().showEditPakann(pakan);
            listViewPakan.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
