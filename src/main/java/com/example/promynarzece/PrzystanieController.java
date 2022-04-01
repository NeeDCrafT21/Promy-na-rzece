package com.example.promynarzece;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.System.exit;

public class PrzystanieController {

    //Przystan 0
    @FXML
    public Rectangle producent0;
    @FXML
    public AnchorPane kolejka0;
    @FXML
    public Rectangle auto1Kol0;
    @FXML
    public Rectangle auto2Kol0;
    @FXML
    public Rectangle auto3Kol0;
    @FXML
    public Rectangle auto4Kol0;
    @FXML
    public Rectangle auto5Kol0;
    @FXML
    public Rectangle auto6Kol0;
    @FXML
    public Rectangle auto7Kol0;
    @FXML
    public Rectangle auto8Kol0;
    @FXML
    public Rectangle auto9Kol0;
    @FXML
    public Rectangle auto10Kol0;
    @FXML
    public Label stanKolejki0;

    //Przystan 1
    @FXML
    public Rectangle producent1;
    @FXML
    public AnchorPane kolejka1;
    @FXML
    public Rectangle auto1Kol1;
    @FXML
    public Rectangle auto2Kol1;
    @FXML
    public Rectangle auto3Kol1;
    @FXML
    public Rectangle auto4Kol1;
    @FXML
    public Rectangle auto5Kol1;
    @FXML
    public Rectangle auto6Kol1;
    @FXML
    public Rectangle auto7Kol1;
    @FXML
    public Rectangle auto8Kol1;
    @FXML
    public Rectangle auto9Kol1;
    @FXML
    public Rectangle auto10Kol1;
    @FXML
    public Label stanKolejki1;

    //Menu i przyciski
    @FXML
    public TextField iloscPromowMenu;
    @FXML
    public TextField pojemnoscPromuMenu;
    @FXML
    public TextField czasPostojuMenu;
    @FXML
    public TextField czasPrzeplywuMenu;
    @FXML
    public TextField iloscAutWKolejceMenu;
    @FXML
    public TextField czasZjazduAutaMenu;
    @FXML
    public TextField czasWjazduAutaMenu;
    @FXML
    public TextField dolnaGranicaCzasuMenu;
    @FXML
    public TextField gornaGranicaCzasuMenu;

    @FXML
    public Button resetDanychMenu;
    @FXML
    public Button zapiszMenu;
    @FXML
    public Button wyjdzMenu;
    @FXML
    public Button startMenu;

    public int iloscAutWKolejce = 10;
    public int N = 3; //ilosc promow
    public int M = 2; //ilosc przystani
    public int pojemnoscPromu = 9;
    public int czasPostoju = 3000;
    public int czasPrzeplywu = 2000;
    public int czasZjazdu = 500;
    public int czasWjazdu = 500;
    public int maxCzasProdukcji = 500;
    public int minCzasProdukcji = 500;

    void usunAuta(){
        for(int i = 0; i < 10; i++){
            kolejka0.getChildren().get(i+1).setVisible(false);
            kolejka1.getChildren().get(i+1).setVisible(false);
        }
    }

    @FXML
    void initialize() throws FileNotFoundException {
        usunAuta();

        File plik = new File("dane.txt");
        Scanner wczytajDane = new Scanner(plik);
        String dane = wczytajDane.nextLine();

        iloscPromowMenu.setText(dane);
        dane = wczytajDane.nextLine();
        pojemnoscPromuMenu.setText(dane);
        dane = wczytajDane.nextLine();
        czasPostojuMenu.setText(dane);
        dane = wczytajDane.nextLine();
        czasPrzeplywuMenu.setText(dane);
        dane = wczytajDane.nextLine();
        iloscAutWKolejceMenu.setText(dane);
        dane = wczytajDane.nextLine();
        czasZjazduAutaMenu.setText(dane);
        dane = wczytajDane.nextLine();
        czasWjazduAutaMenu.setText(dane);
        dane = wczytajDane.nextLine();
        dolnaGranicaCzasuMenu.setText(dane);
        dane = wczytajDane.nextLine();
        gornaGranicaCzasuMenu.setText(dane);
        startMenu.setDisable(true);
        wczytajDane.close();

        zapiszMenu.setOnAction(e ->{
            N = Integer.parseInt(iloscPromowMenu.getText());
            pojemnoscPromu = Integer.parseInt(pojemnoscPromuMenu.getText());
            czasPostoju = Integer.parseInt(czasPostojuMenu.getText());
            czasPrzeplywu = Integer.parseInt(czasPrzeplywuMenu.getText());
            iloscAutWKolejce = Integer.parseInt(iloscAutWKolejceMenu.getText());
            czasZjazdu = Integer.parseInt(czasZjazduAutaMenu.getText());
            czasWjazdu = Integer.parseInt(czasWjazduAutaMenu.getText());
            minCzasProdukcji = Integer.parseInt(dolnaGranicaCzasuMenu.getText());
            maxCzasProdukcji = Integer.parseInt(gornaGranicaCzasuMenu.getText());
            startMenu.setDisable(false);

            PrintWriter zapiszDane = null;
            try{
                zapiszDane = new PrintWriter("dane.txt");
            }catch (FileNotFoundException e1){
                e1.printStackTrace();
            }
            zapiszDane.println(N);
            zapiszDane.println(pojemnoscPromu);
            zapiszDane.println(czasPostoju);
            zapiszDane.println(czasPrzeplywu);
            zapiszDane.println(iloscAutWKolejce);
            zapiszDane.println(czasZjazdu);
            zapiszDane.println(czasWjazdu);
            zapiszDane.println(minCzasProdukcji);
            zapiszDane.println(maxCzasProdukcji);
            zapiszDane.close();
        });

        resetDanychMenu.setOnAction(e ->{
            iloscPromowMenu.setText("5");
            pojemnoscPromuMenu.setText("9");
            czasPostojuMenu.setText("9000");
            czasPrzeplywuMenu.setText("2000");
            iloscAutWKolejceMenu.setText("10");
            czasZjazduAutaMenu.setText("300");
            czasWjazduAutaMenu.setText("500");
            dolnaGranicaCzasuMenu.setText("900");
            gornaGranicaCzasuMenu.setText("500");
        });

        wyjdzMenu.setOnAction(e ->{
            Stage stage = (Stage) wyjdzMenu.getScene().getWindow();
            stage.close();
            System.out.println("Zakonczono dzialanie programu");
            exit(0);
        });

        startMenu.setOnAction(e ->{
            startMenu.setDisable(true);
            zapiszMenu.setDisable(true);
            resetDanychMenu.setDisable(true);

            Buffer buf1 = new Buffer(iloscAutWKolejce, N, 0, kolejka0, producent0, stanKolejki0);
            Buffer buf2 = new Buffer(iloscAutWKolejce, N, 1, kolejka1, producent1, stanKolejki1);
            KolejkaPromow kol1 = new KolejkaPromow(N, 0);
            KolejkaPromow kol2 = new KolejkaPromow(N, 1);

            for(int i = 0; i < N; i++){
                kol1.kolejkaPromow[i] = i + 1;
            }

            Przystan[] przystanie = new Przystan[M];
            przystanie[0] = new Przystan(0, maxCzasProdukcji, minCzasProdukcji, buf1);
            przystanie[1] = new Przystan(1, maxCzasProdukcji, minCzasProdukcji, buf2);

            Prom[] promy = new Prom[N];
            for (int i = 0; i < N; i++) {
                Prom prom = new Prom(i+1, pojemnoscPromu, czasPostoju, czasPrzeplywu, i+1, czasZjazdu, czasWjazdu, buf1, buf2, kol1, kol2);
                promy[i] = prom;
            }

            for (int i = 0; i < M; i++) {
                przystanie[i].start();
            }

            for (int i = 0; i < N; i++) {
                promy[i].start();
            }
        });
    }
}
