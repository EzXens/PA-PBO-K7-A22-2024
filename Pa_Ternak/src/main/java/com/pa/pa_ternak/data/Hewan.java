package com.pa.pa_ternak.data;

public class Hewan implements TernakHewan {
    private int id;
    private String nama;
    private String jenis;
    private int usia;
    private double berat;
    private String jenisKelamin;
    private boolean statusLapar;
    private boolean statusVaksin;
    private String pakanYangDiberikan;
    private String VaksinyangDiberikan;

    public Hewan(int id, String nama, String jenis, int usia, double berat, String jenisKelamin) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.usia = usia;
        this.berat = berat;
        this.jenisKelamin = jenisKelamin;
        this.statusLapar = true; // Default semua hewan lapar
        this.statusVaksin = true;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public boolean isStatusLapar() {
        return statusLapar;
    }

    public boolean isStatusVaksin() {
        return statusVaksin;
    }

    public void setStatusVaksin(){
        this.statusVaksin = statusVaksin;
    }

    public void setStatusLapar(boolean statusLapar) {
        this.statusLapar = statusLapar;
    }

    public String getPakanYangDiberikan() {
        return pakanYangDiberikan;
    }

    public String getVaksinyangDiberikan() {
        return VaksinyangDiberikan;
    }

    public void setStatusVaksin(boolean statusVaksin) {
        this.statusVaksin = statusVaksin;
    }

    public void setPakanYangDiberikan(String pakanYangDiberikan) {
        this.pakanYangDiberikan = pakanYangDiberikan;
    }

    public void setVaksinyangDiberikan(String VaksinyangDiberikan) {
        this.VaksinyangDiberikan = VaksinyangDiberikan;
    }


    public String deskripsi() {
        return String.format("%d - %s (%s), Usia: %d tahun, Berat: %.1f kg, Kelamin: %s, Status: %s",
                id, nama, jenis, usia, berat, jenisKelamin, statusLapar ? "Lapar" : "Kenyang");
    }

    public String deskripsi(String... details) {
        String deskripsiDasar = deskripsi();
        String tambahan = "";

        for (String detail : details) {
            if (detail.equalsIgnoreCase("pakan")) {
                tambahan += String.format(", Pakan: %s", pakanYangDiberikan != null ? pakanYangDiberikan : "Belum diberi pakan");
            } else if (detail.equalsIgnoreCase("vaksin")) {
                tambahan += String.format(", Vaksin: %s", VaksinyangDiberikan != null ? VaksinyangDiberikan : "Belum diberi vaksin");
            } else if (detail.equalsIgnoreCase("status")) {
                tambahan += String.format(", Status Lapar: %s, Status Vaksin: %s",
                        statusLapar ? "Lapar" : "Kenyang", statusVaksin ? "Belum" : "Sudah");
            }
        }

        return deskripsiDasar + tambahan;
    }
}

