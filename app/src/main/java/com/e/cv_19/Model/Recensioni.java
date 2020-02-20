package com.e.cv_19.Model;

public class Recensioni {
    private String idAutore;
    private String struttura;
    private String testo;
    private int voto;

    public Recensioni(){}

    public  Recensioni(String autore,String recensita,String testo,int val){
        this.idAutore = autore;
        this.struttura = recensita;
        this.testo = testo;
        this.voto = val;
    }

    public String getTesto() {
        return this.testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getIdAutore() {
        return this.idAutore;
    }

    public void setIdAutore(String Nickname) {
        this.idAutore = Nickname;
    }

    public String getStruttura() {
        return this.struttura;
    }

    public void setStruttura(String struttura) {
        this.struttura = struttura;
    }

    public int getVoto() {
        return this.voto;
    }

    public void setVoto(int valutazione) { this.voto = valutazione; }



}

