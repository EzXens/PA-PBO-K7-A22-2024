package com.pa.pa_ternak;

import com.pa.pa_ternak.controller.*;
import com.pa.pa_ternak.data.DataStore;
import com.pa.pa_ternak.data.Hewan;
import com.pa.pa_ternak.data.Pakan;
import com.pa.pa_ternak.data.Pegawai;
import com.pa.pa_ternak.data.Vaksin;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Application instance;
    private Stage primaryStage;
    private DataStore dataStore;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.dataStore = new DataStore();
        instance = this; // Inisialisasi instance
        showLoginScene();
    }

    // Metode getInstance untuk mendapatkan instance dari kelas Application
    public static Application getInstance() {
        return instance;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    // Method untuk menampilkan halaman login
    public void showLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 411, 405);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Login form re:farm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method untuk menampilkan halaman Menu_Admin setelah login berhasil
    public void showMenuAdminScene(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Admin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Admin");
        AdminController adminController = fxmlLoader.getController();
        adminController.setUserInfo(username, "Admin");
        primaryStage.show();
    }

    public void showMenuAdminManajemenHewan(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Admin_manajemen_hewan.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Admin Manajemen Hewan");
        AdminManajemenHewanController controller = fxmlLoader.getController();
        // Menetapkan informasi pengguna di AdminController
        controller.setUserInfo(username, "Admin");
        primaryStage.show();
    }
    public void showDataEntryMenu(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Admin_manajemen_hewan.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Pegawai Manajemen Hewan");
        AdminManajemenHewanController controller = fxmlLoader.getController();
        // Menetapkan informasi pengguna di AdminController
        controller.setUserInfo(username, "Data Entry");
        primaryStage.show();
    }

    public void showMenuAdminManajemenVaksin(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Admin_manajemen_Vaksin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Admin Manajemen Vaksin");
        AdminManajemenVaksinController controller = fxmlLoader.getController();
        // Menetapkan informasi pengguna di AdminController
        controller.setUserInfo(username, "Admin");
        primaryStage.show();
    }

    public void showMenuAdminManajemenPakan(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Admin_manajemen_pakan.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Admin Manajemen Pakan");
        AdminManajemenPakanController controller = fxmlLoader.getController();
        // Menetapkan informasi pengguna di AdminController
        controller.setUserInfo(username, "Admin");
        primaryStage.show();
    }

    public void showMantriMenu(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Mantri_Hewan.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Pegawai Beri Vaksin");
        MantriHewanController controller = fxmlLoader.getController();
        // Menetapkan informasi pengguna di AdminController
        controller.setUserInfo(username, "Mantri Hewan");
        primaryStage.show();
    }

    public void showFeederMenu(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu_Feeder.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Pegawai Beri Pakan");
        FeederController controller = fxmlLoader.getController();
        controller.setUserInfo(username, "Feeder");
        primaryStage.show();
    }

    public void showEditAkun(Pegawai pegawai) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAkun.fxml"));
        Parent root = loader.load();

        EditAkunController controller = loader.getController();
        controller.setPegawai(pegawai);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Pegawai");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    public void showEditHewan(Hewan hewan) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditHewan.fxml"));
        Parent root = loader.load();

        EditHewanController controller = loader.getController();
        controller.setHewan(hewan);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Hewan");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void showEditPakann(Pakan pakan) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPakan.fxml"));
        Parent root = loader.load();

        EditPakanController controller = loader.getController();
        controller.setPakan(pakan);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Pakan");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void showEditVaksin(Vaksin vaksin) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditVaksin.fxml"));
        Parent root = loader.load();

        EditVaksinController controller = loader.getController();
        controller.setVaksin(vaksin);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Vaksin");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
