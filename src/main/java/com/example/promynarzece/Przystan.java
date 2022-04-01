package com.example.promynarzece;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

//dziala jak producent
public class Przystan extends Thread {
    public int numerPrzystani;
    public int predkoscProdukcji;
    public int maxCzasProdukcji;
    public int minCzasProdukcji;
    Buffer buf;
    Random rand = new Random();

    public Przystan(int numerPrzystani, int maxCzasProdukcji, int minCzasProdukcji, Buffer buf) {
        this.numerPrzystani = numerPrzystani;
        this.maxCzasProdukcji = maxCzasProdukcji;
        this.minCzasProdukcji = minCzasProdukcji;
        this.buf = buf;
    }

    public void run() {
        Rectangle auto = new Rectangle(15, 25);
        if(numerPrzystani == 0){
            auto.setX(95);
            auto.setY(140);
        }

        else if(numerPrzystani == 1){
            auto.setX(908);
            auto.setY(590);
        }
        auto.setFill(Color.RED);
        auto.setStroke(Color.BLACK);
        auto.setStrokeWidth(1);

        while(true) {

            predkoscProdukcji = (rand.nextInt(maxCzasProdukcji) + minCzasProdukcji);

            //System.out.println("Przystan " + numerPrzystani + ", wyprodukowalem auto " + predkoscProdukcji);

            Platform.runLater(() -> {
                Main.root.getChildren().add(auto);
            });
            Path droga = new Path();

            MoveTo miejsceAuta = new MoveTo();
            miejsceAuta.setX(auto.getX());
            miejsceAuta.setY(auto.getY());

            LineTo miejsceKolejki = new LineTo();
            miejsceKolejki.setX(auto.getX());
            if(numerPrzystani == 0){
                miejsceKolejki.setY(auto.getY() + 160);
            }
            else if(numerPrzystani == 1){
                miejsceKolejki.setY(auto.getY() - 135);
            }

            droga.getElements().addAll(miejsceAuta, miejsceKolejki);
            PathTransition przejazdPoDrodze = new PathTransition(Duration.millis(predkoscProdukcji), droga, auto);


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
                    //System.out.println("bruh" + numerPrzystani);
                    wait();
                    //System.out.println("czekam, przystan " + numerPrzystani);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*
            try {
                Thread.sleep(predkoscProdukcji);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             */
            buf.wstaw(predkoscProdukcji);
            Platform.runLater(() -> {
                Main.root.getChildren().remove(auto);
            });
            //System.out.println("Dodano do kolejki auto " + predkoscProdukcji + ", w przysani " + numerPrzystani);
        }
    }
}


