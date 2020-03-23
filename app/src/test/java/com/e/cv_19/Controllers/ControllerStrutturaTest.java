package com.e.cv_19.Controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/*ATTENZIONE: A cause dell'utilizzo di funzioni di database all'interno del costruttore della
classe ControllerStruttura questo test JUnit NON è compilabile ed eseguibile

per il testing Black Box sono state indentificate le seguenti classi di equivalenza:
  Parametro user_id:
    -CE1: Utente che ha recensito la struttura (false)
    -CE2: Utente che non ha recensito la struttura (true)

 Avendo un solo parametro e solo il seguente accesso diretto al database si sono sviluppati 2 casi di test

   1. User_id_che_non_ha_recensito_la_struttura()
   2. User_id_che_ha_recensito_la_struttura()

  Per effettuare il test White Box tenendo contro del grafo del controllo di flusso (vedere foto) si sono
  sviluppati i seguenti casi di test per ottenere una Branch Converage e una Node Coverage:

   1. TestWhiteBoxPath_1_2_7()
   2. TestWhiteBoxPath_1_7()

    in quanto gli altri percorsi sono già verificati dai test Black Box.
 */

public class ControllerStrutturaTest {
    ControllerStruttura Controller;

    @Before
    public void setUp() throws Exception {
        Controller = new ControllerStruttura("oOc4KFqIijHjR3A27Moj",null);
    }

    @Test
    public void User_id_che_non_ha_recensito_la_struttura() {
        assertTrue(Controller.recensione_gia_scritta("ZSVV2hYSEngBomOHMOVDEM4bCHT2"));
    }

    @Test
    public void User_id_che_ha_recensito_la_struttura() {
        assertFalse(Controller.recensione_gia_scritta("XuMwkLtiAdRCYWhf6Uisv11uNby1"));
    }

    //White Box

    @Test
    public void TestWhiteBoxPath_1_7() {
        assertTrue(Controller.recensione_gia_scritta(" "));
    }

    @Test
    public void TestWhiteBoxPath_1_2_7() {
        assertTrue(Controller.recensione_gia_scritta("0MBDfqaV21eAzmrdIK41DIcluPR2"));
    }
}