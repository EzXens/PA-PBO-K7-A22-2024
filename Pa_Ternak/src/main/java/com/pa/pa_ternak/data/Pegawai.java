package com.pa.pa_ternak.data;

public class Pegawai extends Akun {
    private String role;

    public Pegawai(String userid, String username, String password, String role) {
        super(userid, username, password);
        this.role = role;
    }

    @Override
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getUserid() {
        return userid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Pegawai{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
