/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF;

import TPOF.Buffer.GestionEntrada;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kurito
 */
public class Colectivo implements Runnable {

    private String nombre;
    private GestionEntrada ge;


    public Colectivo(String nombre, GestionEntrada ge) {
        this.nombre = nombre;
        this.ge = ge;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ge.esperarViajar();
                Thread.sleep(4000);
                ge.esperarVolver();
                Thread.sleep(4000);
                ge.volvioDestino();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
