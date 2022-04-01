package com.example.promynarzece;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PrzystanAnimacja extends Thread{
    public int numerPrzystani;
    public int czasWjazdu;
    public int czasZjazdu;
    public boolean czyWjezdza = false;
    public boolean czyZjezdza = false;

    Rectangle autko1;
    Rectangle autko2;

    public PrzystanAnimacja(int numerPrzystani, int czasWjazdu, int czasZjazdu){
        this.numerPrzystani = numerPrzystani;
        this.czasWjazdu = czasWjazdu;
        this.czasZjazdu = czasZjazdu;
    }

    public void wjezdzajNaProm(){
//        if(numerPrzystani == 0){
//            autko1.setX(200);
//            autko1.setY(570);
//        }
//        else if (numerPrzystani == 1){
//            autko1.setX(800);
//            autko1.setY(130);
//        }

        //autko1.setVisible(true);

        Path droga = new Path();

        MoveTo kolejka = new MoveTo();
        if(numerPrzystani == 0){
            kolejka.setX(100);
            kolejka.setY(600);
        }
        else if (numerPrzystani == 1){
            kolejka.setX(900);
            kolejka.setY(160);
        }

        LineTo prom = new LineTo();
        if(numerPrzystani == 0){
            prom.setX(270);
            prom.setY(600);
        }
        else if (numerPrzystani == 1){
            prom.setX(720);
            prom.setY(160);
        }

        droga.getElements().addAll(kolejka, prom);
        PathTransition przejazdPoDrodze = new PathTransition(Duration.millis(czasWjazdu), droga, autko1);


        przejazdPoDrodze.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            przejazdPoDrodze.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //autko1.setVisible(false);

        czyWjezdza = false;
    }

    public void zjezdzajZPromu(){

        //autko2.setVisible(true);

        Path droga1 = new Path();

        MoveTo prom1 = new MoveTo();
        if(numerPrzystani == 0){
            prom1.setX(230);
            prom1.setY(660);
        }
        else if (numerPrzystani == 1){
            prom1.setX(780);
            prom1.setY(100);
        }

        LineTo wyjazd = new LineTo();
        if(numerPrzystani == 0){
            wyjazd.setX(0);
            wyjazd.setY(660);
        }
        else if (numerPrzystani == 1){
            wyjazd.setX(1000);
            wyjazd.setY(100);
        }

        droga1.getElements().addAll(prom1, wyjazd);
        PathTransition przejazdPoDrodze1 = new PathTransition(Duration.millis(czasZjazdu), droga1, autko2);


        przejazdPoDrodze1.setOnFinished(e -> {
            synchronized (this) {
                notify();
            }
        });

        Platform.runLater(() -> {
            przejazdPoDrodze1.play();
        });

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //autko2.setVisible(false);

        czyZjezdza = false;
    }

    @Override
    public void run() {
        autko1 = new Rectangle(25, 15);
        //autko1.setVisible(false);
        autko1.setFill(Color.RED);
        autko1.setStroke(Color.BLACK);
        autko1.setStrokeWidth(1);

        Platform.runLater(() -> {
            Main.root.getChildren().add(autko1);
        });

        autko2 = new Rectangle(25, 15);
        //autko2.setVisible(false);
        autko2.setFill(Color.RED);
        autko2.setStroke(Color.BLACK);
        autko2.setStrokeWidth(1);

        Platform.runLater(() -> {
            Main.root.getChildren().add(autko2);
        });

        while(!czyWjezdza && !czyZjezdza);
        if(czyWjezdza){
            wjezdzajNaProm();
        }
        if(czyZjezdza){
            zjezdzajZPromu();
        }
        Platform.runLater(() -> {
            Main.root.getChildren().remove(autko1);
            Main.root.getChildren().remove(autko2);
        });
    }
}
