package com.example.carlos.proyectomascotas.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("mail")
    @Expose
    private String mail;

    @SerializedName("ipRasp")
    @Expose
    private String ipRasp;

    @SerializedName("necesitaPassword")
    @Expose
    private String necesitaPassword;

    public Usuario(String nickname, String password, String mail, String ipRasp, String necesitaPassword) {
        this.nickname = nickname;
        this.password = password;
        this.mail = mail;
        this.ipRasp = ipRasp;
        this.necesitaPassword = necesitaPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getIpRasp() {
        return ipRasp;
    }

    public String getNecesitaPassword() {
        return necesitaPassword;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIpRasp(String ipRasp) {
        this.ipRasp = ipRasp;
    }

    public void setNecesitaPassword(String necesitaPassword) {
        this.necesitaPassword = necesitaPassword;
    }
}
