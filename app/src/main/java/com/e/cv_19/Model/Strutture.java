package com.e.cv_19.Model;
public class Strutture {

    private String descrizione;
    private String immagine;
    private String indirizzo;
    private String nome;
    private String tipologia;
    private double latitudine;
    private double longitudine;
    private double valutazione;

    public Strutture(){

    }


    public Strutture(String descrizione, String immagine, String indirizzo, String nome, String tipologia, double latitudine, double longitudine) {
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.indirizzo = indirizzo;
        this.nome = nome;
        this.tipologia = tipologia;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getValutazione() {
        return valutazione;
    }

    public void setValutazione(double valutazione) {
        this.valutazione = valutazione;
    }
}

