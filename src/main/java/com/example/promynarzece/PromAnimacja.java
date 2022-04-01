package com.example.promynarzece;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PromAnimacja extends Thread {
    public boolean czyWKolejce = false;
    public boolean czyPlynie = false;
    public boolean czyPobiera = false;
    public boolean czyUsuwa = false;
    public boolean czyPrzesunac = false;
    public int numerPromu;
    public int numerPrzystani;
    public int numerWKolejce;
    public int numerWKolejceTemp;
    public int czasPrzeplywu;
    public int iloscAut = 0;
    public int iloscPromow;
    public int pojemnoscPromu;
    public Label stanPokladu = new Label();

    public PromAnimacja(int numerPromu, int czasPrzeplywu, int pojemnoscPromu){
        this.numerPromu = numerPromu;
        this.czasPrzeplywu = czasPrzeplywu;
        this.pojemnoscPromu = pojemnoscPromu;
    }

    public void PrzeplywanieDoDrugiegejPrzystani(AnchorPane prom)
    {
        TranslateTransition doPlyniecia = new TranslateTransition();
        doPlyniecia.setDuration(Duration.millis(300));
        doPlyniecia.setNode(prom);
        if(numerPrzystani == 0){
            doPlyniecia.setByX(45);
            doPlyniecia.setByY(90);
        }
        else if(numerPrzystani == 1){
            System.out.println(numerPrzystani);
            doPlyniecia.setByX(-45);
            doPlyniecia.setByY(-90);
        }

        RotateTransition przekrecSie = new RotateTransition(Duration.millis(300), prom);
        przekrecSie.setByAngle(-90);

        doPlyniecia.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            doPlyniecia.play();
            przekrecSie.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TranslateTransition doDrugiejPrzystani = new TranslateTransition();
        doDrugiejPrzystani.setDuration(Duration.millis(czasPrzeplywu));
        doDrugiejPrzystani.setNode(prom);
        if(numerPrzystani == 0){
            doDrugiejPrzystani.setByX(380);
        }
        else if (numerPrzystani == 1){
            doDrugiejPrzystani.setByX(-380);
        }


        doDrugiejPrzystani.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            doDrugiejPrzystani.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        czyPlynie = false;
    }
    public void WejdzDoKolejki(AnchorPane prom)
    {
        System.out.println("================Prom " + numerPromu + ", numer w kolejce: " + numerWKolejce);

        TranslateTransition doKolejki = new TranslateTransition();
        doKolejki.setDuration(Duration.millis(300));
        doKolejki.setNode(prom);
        if(numerPrzystani == 0){
            //doKolejki.setByY((6 - numerWKolejce) * 95);
            doKolejki.setByY(95);
        }
        else if(numerPrzystani == 1){
            //doKolejki.setByY((6 - numerWKolejce) * -95);
            doKolejki.setByY(-95);
        }

        RotateTransition przekrecSie = new RotateTransition(Duration.millis(300), prom);
        przekrecSie.setByAngle(-90);

        doKolejki.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            doKolejki.play();
            przekrecSie.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int j = 6;
        System.out.println("Moja wartosc j = " + numerWKolejce);
        if(iloscPromow < 6){
            while(numerWKolejce != j){
                TranslateTransition wKolejce = new TranslateTransition();
                wKolejce.setDuration(Duration.millis(300));
                wKolejce.setNode(prom);
                if(numerPrzystani == 0){
                    //doKolejki.setByY((6 - numerWKolejce) * 95);
                    wKolejce.setByY(95);
                }
                else if(numerPrzystani == 1){
                    //doKolejki.setByY((6 - numerWKolejce) * -95);
                    wKolejce.setByY(-95);
                }
                j--;
                wKolejce.setOnFinished(e -> {
                    synchronized (this) {
                        notify();
                    }
                });

                Platform.runLater(() -> {
                    wKolejce.play();
                });

                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        czyWKolejce = false;
    }
    public void PrzesunWKolejce(AnchorPane prom)
    {
        TranslateTransition przesun = new TranslateTransition();
        przesun.setDuration(Duration.millis(300));
        przesun.setNode(prom);
        if(numerPrzystani == 0){
            przesun.setByY(95);
        }
        else if(numerPrzystani == 1){
            przesun.setByY(-95);
        }

        przesun.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            przesun.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        czyPrzesunac = false;
    }

    public void run() {
        AnchorPane prom = new AnchorPane();
        Rectangle poklad = new Rectangle(60, 85);

        Rectangle[] autaNaPokladzie = new Rectangle[9];


        double miejscePromuX = 258;
        double miejscePromuY;
        if(numerWKolejce < 7){
            miejscePromuY = 568 - (numerWKolejce - 1) * 95;
        }
        else{
            miejscePromuY = 568 - (6 - 1) * 95;
        }

        prom.setLayoutX(miejscePromuX);
        prom.setLayoutY(miejscePromuY);
        poklad.setStroke(Color.BLACK);
        poklad.setStrokeWidth(1);
        poklad.setFill(Color.BROWN);

        prom.getChildren().add(poklad);

        double x = 41, y = 2.5;
        for(int i = 1; i < 10; i++){
            Rectangle auto = new Rectangle(15, 25);
            auto.setLayoutX(82 - x);
            auto.setLayoutY(0.25 + y);
            x += (15 + 3.75);
            if(i % 3 == 0){
                y += (25 + 2.5);
                x = 41;
            }
            auto.setFill(Color.RED);
            auto.setStroke(Color.BLACK);
            auto.setStrokeWidth(1);
            autaNaPokladzie[i-1] = auto;
            prom.getChildren().add(autaNaPokladzie[i-1]);
            autaNaPokladzie[i-1].setVisible(false);
        }

        stanPokladu.setText("[ 0/" + pojemnoscPromu + " ]");
        stanPokladu.setFont(Font.font("System", 12));
        stanPokladu.setLayoutX(30);
        stanPokladu.setLayoutY(85);
        prom.getChildren().add(stanPokladu);

        Platform.runLater(() -> {
            Main.root.getChildren().add(prom);
        });

        while (true){
            if(czyPlynie){
                PrzeplywanieDoDrugiegejPrzystani(prom);
            }
            if(czyWKolejce){
                WejdzDoKolejki(prom);
            }
            if(czyPrzesunac){
                if(numerWKolejce < 6)
                    PrzesunWKolejce(prom);
            }
            if(czyPobiera){
                autaNaPokladzie[iloscAut-1].setVisible(true);
                czyPobiera = false;
            }
            if(czyUsuwa){
                if (iloscAut < 9) {
                    autaNaPokladzie[iloscAut].setVisible(false);
                }
                czyUsuwa = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

