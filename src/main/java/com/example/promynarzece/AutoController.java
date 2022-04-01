package com.example.promynarzece;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AutoController implements Initializable {
    private int predkoscPrdukcji = 1000;
    @FXML
    private Rectangle auto;

    public AutoController(int predkoscPrdukcji){
        this.predkoscPrdukcji = predkoscPrdukcji;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition doKolejki = new TranslateTransition();
        doKolejki.setNode(auto);
        doKolejki.setDuration(Duration.millis(predkoscPrdukcji));
        doKolejki.setByY(-300);
        doKolejki.play();
    }
}
