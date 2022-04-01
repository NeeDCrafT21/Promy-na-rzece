package com.example.promynarzece;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    public int numerBufora;
    public int iloscAutWKolejce = 100; //będzie wybierane
    public int N = 5; //ilosc promow
    public int aktualnaIloscAutWKolejce = 0;
    int[] kolejkaAut = new int[iloscAutWKolejce];
    final Lock dostep = new ReentrantLock();
    final Condition pusty = dostep.newCondition();
    final Condition pelny = dostep.newCondition();

    AnchorPane kolejka;
    Rectangle producent;
    Label stanKolejki;

    public void zerowanieTab(int[] tab, int n){
        for(int i = 0; i < n; i++){
            tab[i] = 0;
        }
    }

    public void licznikKolejki(){
        stanKolejki.setText("[ " + aktualnaIloscAutWKolejce + "/" + iloscAutWKolejce + " ]");
    }

    public Buffer(int iloscAutWKolejce, int N, int numerBufora, AnchorPane kolejka, Rectangle producent, Label stanKolejki) {
        this.iloscAutWKolejce = iloscAutWKolejce;
        this.N = N;
        this.numerBufora = numerBufora;
        this.producent = producent;
        this.kolejka = kolejka;
        this.stanKolejki = stanKolejki;
        zerowanieTab(kolejkaAut, iloscAutWKolejce);
    }

    public void wstaw(int auto) {
        boolean czyDodac = false;
        dostep.lock();
        try {
            if (aktualnaIloscAutWKolejce == iloscAutWKolejce) {
                try {
                    producent.setStroke(Color.RED);
                    pelny.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            producent.setStroke(Color.BLACK);
            kolejkaAut[aktualnaIloscAutWKolejce] = auto;
            aktualnaIloscAutWKolejce = aktualnaIloscAutWKolejce + 1;

            Platform.runLater(() -> {
                licznikKolejki();
            });

            if(aktualnaIloscAutWKolejce <= 10)
                kolejka.getChildren().get(11 - aktualnaIloscAutWKolejce).setVisible(true);
            pusty.signal();
        } finally {
            dostep.unlock();
        }
    }

    public int pobierz() {
        int auto;
        dostep.lock();
        try {
            if (aktualnaIloscAutWKolejce == 0) {
                try {
                    pusty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            auto = kolejkaAut[0];
            for(int i = 1; i < iloscAutWKolejce; i++){
                if(kolejkaAut[i] != 0){
                    kolejkaAut[i - 1] = kolejkaAut[i];
                    kolejkaAut[i] = 0;
                }
                else{
                    break;
                }
            }
            aktualnaIloscAutWKolejce = aktualnaIloscAutWKolejce - 1;


            Platform.runLater(() -> {
                licznikKolejki();
            });

            if(aktualnaIloscAutWKolejce < 10)
                kolejka.getChildren().get(10 - aktualnaIloscAutWKolejce).setVisible(false);
            pelny.signal();
        } finally {
            dostep.unlock();
        }
        return auto;
    }
}

