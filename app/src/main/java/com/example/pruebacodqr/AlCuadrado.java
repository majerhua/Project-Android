package com.example.pruebacodqr;

public interface AlCuadrado {
    interface View{
        void showResult(String result);
    }

    interface Presenter{
        void showResult(String result);
        void alCuadrado(String data);
    }

    interface Model{
        void alCuadrado(String data);
    }
}
