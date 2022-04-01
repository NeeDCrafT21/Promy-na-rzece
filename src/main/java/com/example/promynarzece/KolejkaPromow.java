package com.example.promynarzece;

public class KolejkaPromow {
    public int czyjaKolej = 1;
    public  int numerPrzystani;
    public int N = 50; //ilosc promow
    int[] kolejkaPromow = new int[N];
    int[] kolejnoscPromow = new int[N];

    public void zerowanieTab(int[] tab, int n){
        for(int i = 0; i < n; i++){
            tab[i] = 0;
        }
    }
    public void wyliczanieKolejnosci(int[] tab, int n){
        for(int i = 0; i < n; i++){
            tab[i] = i + 1;
        }
    }

    public KolejkaPromow(int N, int numerPrzystani){
        this.N = N;
        this.numerPrzystani = numerPrzystani;
        zerowanieTab(kolejkaPromow, N);
        wyliczanieKolejnosci(kolejnoscPromow, N);
    }

//    public void dodajDoKolejki(int numerPromu){
//        for(int i = 0; i < N; i++){
//            if(kolejkaPromow[i] == 0){
//                kolejkaPromow[i] = numerPromu;
//                kolejnoscPromow[numerPromu - 1] = i + 1;
//                if(czyjaKolej == 0)
//                    czyjaKolej = kolejkaPromow[i];
//                break;
//            }
//        }
//    }

    public void dodajNaKoniecKolejki(int numerPromu){
        kolejkaPromow[N-1] = numerPromu;
        kolejnoscPromow[numerPromu - 1] = N;
        System.out.println(kolejnoscPromow[numerPromu - 1]);
    }

    synchronized void usunZKolejki(int numerPromu){
        kolejkaPromow[0] = 0;
        kolejnoscPromow[numerPromu-1] = 0;
        czyjaKolej = kolejkaPromow[0];
    }

    synchronized void przesunWKolejce(int numerPromu){
        kolejkaPromow[kolejnoscPromow[numerPromu-1]-2] = numerPromu;
        kolejkaPromow[kolejnoscPromow[numerPromu-1]-1] = 0;
        kolejnoscPromow[numerPromu-1]--;

    }
}


