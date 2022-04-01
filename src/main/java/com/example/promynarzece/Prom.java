package com.example.promynarzece;

import javafx.application.Platform;

import java.util.Random;

//dziala jak konsument
public class Prom extends Thread {
    public int numerPromu;
    public int numerWKolejce;
    public int pojemnosc;
    public int auto;
    public int iloscAut = 0;
    public int czasPostoju;
    public int czasPrzeplywu;
    public int czasZjazdu = 200;
    public int czasWjazdu = 200;
    Buffer buf;
    Buffer bufTemp;
    KolejkaPromow kol;
    KolejkaPromow kolTemp;
    //PrzystanAnimacja przystan0;
    //PrzystanAnimacja przystan1;

    public void zerowanieTab(int[] tab, int n){
        for(int i = 0; i < n; i++){
            tab[i] = 0;
        }
    }

    public Prom(int numerPromu, int pojemnosc, int czasPostoju, int czasPrzeplywu, int numerWKolejce, int czasZjazdu, int czasWjazdu, Buffer buf1, Buffer buf2, KolejkaPromow kol1, KolejkaPromow kol2) {
        this.numerPromu = numerPromu;
        this.pojemnosc = pojemnosc;
        this.czasPostoju = czasPostoju;
        this.czasPrzeplywu = czasPrzeplywu;
        this.numerWKolejce = numerWKolejce;
        this.czasZjazdu = czasZjazdu;
        this.czasWjazdu = czasWjazdu;
        this.buf = buf1;
        this.bufTemp = buf2;
        this.kol = kol1;
        this.kolTemp = kol2;
        //this.przystan0 = przystan0;
        //this.przystan1 = przystan1;
    }

    public void run() {
        int[] miejscaNaPromie = new int[pojemnosc];
        zerowanieTab(miejscaNaPromie, pojemnosc);

        PromAnimacja animacja = new PromAnimacja(numerPromu, czasPrzeplywu, pojemnosc);
        animacja.numerWKolejce = numerWKolejce;
        animacja.numerWKolejceTemp = numerWKolejce;
        animacja.numerPrzystani = kol.numerPrzystani;
        animacja.iloscPromow = kol.N;
        animacja.start();

        System.out.println("Prom " + numerPromu + ", w kolejce: " + numerWKolejce);
        while(true) {
            while(kol.kolejnoscPromow[numerPromu-1] != 1){
                synchronized (this){
                    try {
                        if(kol.kolejkaPromow[kol.kolejnoscPromow[numerPromu-1]-2] == 0){
                            kol.przesunWKolejce(numerPromu);
                            numerWKolejce = kol.kolejnoscPromow[numerPromu-1];
                            if(kol.kolejnoscPromow[numerPromu-1] < 6){
                                animacja.numerWKolejce = numerWKolejce;
                                animacja.czyPrzesunac = true;
                                while(animacja.czyPrzesunac){
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            kol.czyjaKolej = numerPromu;
            numerWKolejce = kol.kolejnoscPromow[numerPromu-1];
            animacja.numerWKolejce = numerWKolejce;

            //rozladunek i zaladunek aut

            int j = 0;
            while(iloscAut != 0){
                iloscAut--;

                Platform.runLater(() -> {
                    animacja.stanPokladu.setText("[ " + iloscAut + "/" + pojemnosc + " ]");
                });

                animacja.iloscAut = iloscAut;
                animacja.czyUsuwa = true;

                PrzystanAnimacja animacjaPrzystani = new PrzystanAnimacja(kol.numerPrzystani, czasWjazdu, czasZjazdu);
                animacjaPrzystani.start();
                animacjaPrzystani.czyZjezdza = true;
                while(animacjaPrzystani.czyZjezdza){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Prom |" + numerPromu + "|, przystan " + kol.numerPrzystani + ", zjechalo " + j + " auto, nr.: " + miejscaNaPromie[j] + ", zostalo: " + iloscAut + " aut");
                miejscaNaPromie[j] = 0;
                j++;
            }
            long currentTime = System.currentTimeMillis();
            while((iloscAut != pojemnosc) && (System.currentTimeMillis() - currentTime < czasPostoju)){
                auto = buf.pobierz();
                for(int i = 0; i < pojemnosc; i++){
                    if(miejscaNaPromie[i] == 0){
                        PrzystanAnimacja animacjaPrzystani = new PrzystanAnimacja(kol.numerPrzystani, czasWjazdu, czasZjazdu);
                        animacjaPrzystani.start();
                        animacjaPrzystani.czyWjezdza = true;
                        while(animacjaPrzystani.czyWjezdza){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        miejscaNaPromie[i] = auto;
                        iloscAut++;
                        if(iloscAut < 10){
                            animacja.iloscAut = iloscAut;
                            animacja.czyPobiera = true;
                            while(animacja.czyPobiera){
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        Platform.runLater(() -> {
                            animacja.stanPokladu.setText("[ " + iloscAut + "/" + pojemnosc + " ]");
                        });

                        System.out.println("Prom |" + numerPromu + "|, przystan " + kol.numerPrzystani + ", nr.: " + auto + ", wjechalo: " + iloscAut + " aut");
                        //currentTime = System.currentTimeMillis();
                        break;
                    }
                }


            }

            //przesuwanie promow w kolejce

            kol.usunZKolejki(numerPromu);
            numerWKolejce = kol.kolejnoscPromow[numerPromu - 1];
            animacja.czyPlynie = true;
            System.out.println("Stan kolejki promow z " + kol.numerPrzystani + " przystani:");
            for(int i = 0; i < kol.N; i++){
                System.out.println((i + 1) + ". " + kol.kolejkaPromow[i]);
            }

            while(animacja.czyPlynie){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Buffer temp1 = buf;
            buf = bufTemp;
            bufTemp = temp1;
            KolejkaPromow temp2 = kol;
            kol = kolTemp;
            kolTemp = temp2;
            animacja.numerPrzystani = kol.numerPrzystani;

            //kol.dodajDoKolejki(numerPromu);
            kol.dodajNaKoniecKolejki(numerPromu);
            System.out.println("Dodano do kolejki " + kol.numerPrzystani + " prom " + numerPromu + ", ktory jest " + numerWKolejce + " w kolejce");
            numerWKolejce = kol.kolejnoscPromow[numerPromu-1];
            animacja.numerWKolejce = numerWKolejce;
            animacja.czyWKolejce = true;

            while(animacja.czyWKolejce){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


