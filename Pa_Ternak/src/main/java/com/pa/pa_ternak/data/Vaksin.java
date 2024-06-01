package com.pa.pa_ternak.data;

import java.time.LocalDate;

public class Vaksin {
    private Integer id;
    private String nama;
    private LocalDate tanggalKadaluarsa;
    private Double dosis;
    private String jenisVaksin;
    private Integer jumlahVaksin;

    public Vaksin(Integer id, String nama, LocalDate tanggalKadaluarsa, Double dosis, String jenisVaksin, Integer jumlahVaksin) {
        this.id = id;
        this.nama = nama;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.dosis = dosis;
        this.jenisVaksin = jenisVaksin;
        this.jumlahVaksin = jumlahVaksin;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LocalDate getTanggalKadaluarsa() {
        return this.tanggalKadaluarsa;
    }

    public void setTanggalKadaluarsa(LocalDate tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    public Double getDosis() {
        return this.dosis;
    }

    public void setDosis(Double dosis) {
        this.dosis = dosis;
    }

    public String getJenisVaksin() {
        return this.jenisVaksin;
    }

    public void setJenisVaksin(String jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public Integer getJumlahVaksin() {
        return this.jumlahVaksin;
    }

    public void setJumlahVaksin(Integer jumlahVaksin) {
        this.jumlahVaksin = jumlahVaksin;
    }

    public String deskripsi() {
        return "Vaksin{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", tanggalKadaluarsa=" + tanggalKadaluarsa +
                ", dosis=" + dosis +
                ", jenisVaksin='" + jenisVaksin + '\'' +
                ", jumlahVaksin=" + jumlahVaksin +
                '}';
    }

    public boolean pakaiVaksin(int jumlah) {
        if (jumlahVaksin >= jumlah) {
            jumlahVaksin -= jumlah;
            return true;
        } else {
            System.out.println("Jumlah vaksin tidak mencukupi.");
            return false;
        }
    }
}
