package main;

import java.awt.EventQueue;

import telas.TelaPrincipal;

public class MainApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Inicializa a tela principal da aplicação
                    TelaPrincipal window = new TelaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}