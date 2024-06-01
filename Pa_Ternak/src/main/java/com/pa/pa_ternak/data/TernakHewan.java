package com.pa.pa_ternak.data;

public interface TernakHewan {
    public boolean isStatusLapar() ;

    public boolean isStatusVaksin() ;

    public void setStatusLapar(boolean statusLapar) ;

    public String getPakanYangDiberikan() ;

    public String getVaksinyangDiberikan() ;

    public void setStatusVaksin(boolean statusVaksin) ;

    public void setPakanYangDiberikan(String pakanYangDiberikan) ;

    public void setVaksinyangDiberikan(String VaksinyangDiberikan) ;
}
