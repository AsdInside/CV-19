package com.e.cv_19.Model;

public class Recensioni {
    private String Nickname;
    private Strutture  Struttura;
    private String testo;
    private int valutazione;

    public Recensioni(){}

    public  Recensioni(String autore,Strutture recensita,String testo,int val){
        this.Nickname = autore;
        this.Struttura = recensita;
        this.testo = testo;
        this.valutazione = val;
    }

    public String getTesto() {
        return this.testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getNickname() {
        return this.Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public Strutture getStruttura() {
        return this.Struttura;
    }

    public void setStruttura(Strutture struttura) {
        this.Struttura = struttura;
    }

    public int getValutazione() {
        return this.valutazione;
    }

    public void setValutazione(int valutazione) { this.valutazione = valutazione; }



}
