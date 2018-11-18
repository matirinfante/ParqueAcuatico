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
public class Cliente implements Runnable {

    private Pulsera pulsera;
    private String nombre;
    private GestionEntrada ge;
    private boolean usaTransporte;

    public Cliente(Pulsera pulsera, String nombre, GestionEntrada ge, boolean usaTransporte) {
        this.pulsera = pulsera;
        this.nombre = nombre;
        this.ge = ge;
        this.usaTransporte = usaTransporte;
    }

    @Override
    public void run() {
        try {
            if (usaTransporte) {
                ge.esperarSubir();
                ge.descender();
            }
            int nro = ge.esperarEntrarParque();
            Thread.sleep(2000);
            ge.entrandoParque(nro);

        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
