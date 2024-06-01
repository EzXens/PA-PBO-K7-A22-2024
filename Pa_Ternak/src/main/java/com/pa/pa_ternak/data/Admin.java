package com.pa.pa_ternak.data;

public class Admin extends Akun {

    private final String role = "Admin";

    public Admin(String userid, String username, String password,String role) {
        super(userid, username, password);

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


    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
