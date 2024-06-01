package com.pa.pa_ternak.data;


import java.time.LocalDate;

public class Pakan {
    private Integer id;
    private String nama;
    private String jenis;
    private LocalDate tanggalKadaluarsa;
    private Integer jumlahPakan;

    // Konstruktor
    public Pakan(Integer id, String nama, String jenis, LocalDate tanggalKadaluarsa, Integer jumlahPakan) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.jumlahPakan = jumlahPakan;
    }

    // Getter dan Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    public void setTanggalKadaluarsa(LocalDate tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    public Integer getJumlahPakan() {
        return jumlahPakan;
    }

    public void setJumlahPakan(Integer jumlahPakan) {
        this.jumlahPakan = jumlahPakan;
    }

    // Metode untuk memakai pakan
    public boolean pakaiPakan(int jumlah) {
        if (jumlahPakan >= jumlah) {
            jumlahPakan -= jumlah;
            return true;
        } else {
            System.out.println("Jumlah pakan tidak mencukupi.");
            return false;
        }
    }

    // Metode untuk deskripsi pakan
    public String deskripsi() {
        return "Pakan{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", jenis='" + jenis + '\'' +
                ", tanggalKadaluarsa=" + tanggalKadaluarsa +
                ", jumlahPakan=" + jumlahPakan +
                '}';
    }
}
