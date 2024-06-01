package com.pa.pa_ternak.data;

public abstract class Akun {
    protected String userid;
    protected String username;
    protected String password;

    public abstract void setUserid(String userid);
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);

    public abstract String getUserid();
    public abstract String getUsername();
    public abstract String getPassword();

    public Akun(){

    }

    public Akun(String userid){
        this.userid = userid;
    }

    public Akun(String userid, String username, String password){
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

}
