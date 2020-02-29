package com.e.cv_19.Model;

public class Cancellazione {
    private String nickname;
    private String motivazione;
    private String email;


    public Cancellazione() {

    }

    public String getNickname() {
        return nickname;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}