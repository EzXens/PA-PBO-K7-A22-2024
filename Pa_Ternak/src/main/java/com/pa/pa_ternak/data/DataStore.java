package com.pa.pa_ternak.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<Pegawai> pegawaiList;
    private List<Hewan> hewanList;
    private List<Pakan> pakanList;
    private List<Vaksin> vaksinList;

    public DataStore() {
        this.pegawaiList = new ArrayList<>();
        this.hewanList = new ArrayList<>();
        this.pakanList = new ArrayList<>();
        this.vaksinList = new ArrayList<>();

        // Inisialisasi data contoh pegawai
        pegawaiList.add(new DataEntry("1001", "ari", "1234"));
        pegawaiList.add(new DataEntry("1002", "bima", "1234"));
        pegawaiList.add(new DataEntry("1003", "natan", "1234"));
        pegawaiList.add(new MantriHewan("1004", "rehan", "1234"));
        pegawaiList.add(new MantriHewan("1005", "jastin", "1234"));
        pegawaiList.add(new MantriHewan("1006", "aldi", "1234"));
        pegawaiList.add(new Feeder("1007", "ricky", "1234"));
        pegawaiList.add(new Feeder("1008", "ali", "1234"));
        pegawaiList.add(new Feeder("1009", "agung", "1234"));

        // Inisialisasi data contoh hewan
        hewanList.add(new Hewan(1, "Sapi", "Mamalia", 5, 200.0, "Betina"));
        hewanList.add(new Hewan(2, "Ayam", "Unggas", 2, 3.5, "Jantan"));
        hewanList.add(new Hewan(3, "Kambing", "Mamalia", 3, 50.0, "Jantan"));
        hewanList.add(new Hewan(4, "Bebek", "Unggas", 1, 2.0, "Betina"));
        hewanList.add(new Hewan(5, "Kuda", "Mamalia", 6, 300.0, "Jantan"));
        hewanList.add(new Hewan(6, "Angsa", "Unggas", 2, 4.0, "Betina"));
        hewanList.add(new Hewan(7, "Kelinci", "Mamalia", 1, 1.5, "Betina"));
        hewanList.add(new Hewan(8, "Merpati", "Unggas", 2, 0.5, "Jantan"));

        pakanList.add(new Pakan(1, "Pakan Rumput Hijau", "Hijauan", LocalDate.now(), 100));
        pakanList.add(new Pakan(2, "Pakan Konsentrat", "Konsentrat", LocalDate.now(), 150));
        pakanList.add(new Pakan(3, "Pakan Kambing Suplemen", "Suplemen", LocalDate.now(), 200));
        pakanList.add(new Pakan(4, "Pakan Hijau", "Hijauan", LocalDate.now(), 100));
        pakanList.add(new Pakan(5, "Pakan Konsentrat", "Konsentrat", LocalDate.now(), 150));
        pakanList.add(new Pakan(6, "Pakan Suplemen", "Suplemen", LocalDate.now(), 200));
        pakanList.add(new Pakan(7, "Pakan Rumput Hijau", "Hijauan", LocalDate.now(), 100));
        pakanList.add(new Pakan(8, "Pakan Konsentrat", "Konsentrat", LocalDate.now(), 150));
        pakanList.add(new Pakan(9, "Pakan Suplemen", "Suplemen", LocalDate.now(), 200));

        vaksinList.add(new Vaksin(1, "Vaksin Campak", LocalDate.of(2024, 6, 30), 1.5, "Vaksin Virus Hidup", 100));
        vaksinList.add(new Vaksin(2, "Vaksin Rabies", LocalDate.of(2024, 7, 15), 2.0, "Vaksin Virus Mati", 150));
        vaksinList.add(new Vaksin(3, "Vaksin Newcastle", LocalDate.of(2024, 8, 10), 1.2, "Vaksin Virus Hidup", 120));
        vaksinList.add(new Vaksin(4, "Vaksin Tetanus", LocalDate.of(2024, 9, 5), 1.8, "Vaksin Toxoid", 80));
        vaksinList.add(new Vaksin(5, "Vaksin Koksidiosis", LocalDate.of(2024, 10, 20), 1.0, "Vaksin Protozoa", 200));
        vaksinList.add(new Vaksin(6, "Vaksin Brucella Sapi", LocalDate.of(2025, 1, 5), 2.2, "Vaksin Bakteri", 90));
        vaksinList.add(new Vaksin(7, "Vaksin Pasteurella", LocalDate.of(2025, 2, 10), 1.3, "Vaksin Bakteri", 150));
        vaksinList.add(new Vaksin(8, "Vaksin Parvovirus", LocalDate.of(2025, 4, 15), 2.0, "Vaksin Virus Hidup", 100));
    }

    // Manajemen Pegawai
    public List<Pegawai> getPegawaiList() {
        return pegawaiList;
    }

    public void addPegawai(Pegawai pegawai) {
        pegawaiList.add(pegawai);
    }

    public void removePegawai(Pegawai pegawai) {
        pegawaiList.remove(pegawai);
    }

    public void updatePegawai(Pegawai updatedPegawai) {
        for (int i = 0; i < pegawaiList.size(); i++) {
            Pegawai pegawai = pegawaiList.get(i);
            if (pegawai.getUserid().equals(updatedPegawai.getUserid())) {
                pegawaiList.set(i, updatedPegawai);
                break;
            }
        }
    }

    // Manajemen Hewan
    public List<Hewan> getHewanList() {
        return hewanList;
    }

    public void addHewan(Hewan hewan) {
        hewanList.add(hewan);
    }

    public void removeHewan(Hewan hewan) {
        hewanList.remove(hewan);
    }

    public void updateHewan(Hewan updatedHewan) {
        for (int i = 0; i < hewanList.size(); i++) {
            Hewan hewan = hewanList.get(i);
            if (hewan.getId() == updatedHewan.getId()) {
                hewanList.set(i, updatedHewan);
                break;
            }
        }
    }

    //pakan
    public List<Pakan> getPakanList(){
        return pakanList;
    }

    public void addPakan(Pakan pakan) {
        pakanList.add(pakan);
    }

    public void removePakan(Pakan pakan) {
        pakanList.remove(pakan);
    }
    public void updatePakan(Pakan updatedPakan) {
        for (int i = 0; i < pakanList.size(); i++) {
            Pakan pakan = pakanList.get(i);
            if (pakan.getId() == updatedPakan.getId()) {
                pakanList.set(i, updatedPakan);
                break;
            }
        }
    }

    //vaksin
    public List<Vaksin> getVaksinList() {
        return vaksinList;
    }
    public void addVaksin(Vaksin vaksin) {
        vaksinList.add(vaksin);
    }
    public void removeVaksin(Vaksin vaksin) {
        vaksinList.remove(vaksin);
    }
    public void updateVaksin(Vaksin updatedVaksin) {
        for (int i = 0; i < vaksinList.size(); i++) {
            Vaksin vaksin = vaksinList.get(i);
            if (vaksin.getId() == updatedVaksin.getId()) {
                vaksinList.set(i, updatedVaksin);
                break;
            }
        }
    }


}

