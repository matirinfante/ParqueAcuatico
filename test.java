/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF;

import TPOF.Buffer.GestionEntrada;
import TPOF.Buffer.GestionPileta;

/**
 *
 * @author Kurito
 */
public class test {

    public static void main(String[] args) {
        //Pileta pileta = new Pileta(16000, 5000);
        //GestionEntrada ge = new GestionEntrada(25, 4);
        GestionPileta gp = new GestionPileta(null);
        Reloj reloj = new Reloj(0, 18000, System.currentTimeMillis(), gp, 10000, 17000, 18000);
        Thread hiloReloj = new Thread(reloj);
        hiloReloj.start();
//        Pulsera pulsera = new Pulsera();
//        
//        Thread colectivo = new Thread(new Colectivo("Tutu", ge));
//        colectivo.start();
//        Cliente[] clientes = new Cliente[100];
//        for (int i = 0; i < clientes.length; i++) {
//            clientes[i] = new Cliente(pulsera, "cliente" + (i + 1), ge, true);
//        }
//        for (int i = 0; i < clientes.length; i++) {
//            Thread hilo = new Thread(clientes[i], "cliente" + (i + 1));
//            hilo.start();
//
//        }

    }
}
