/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF.Buffer;

import TPOF.Pileta;
import TPOF.Reloj;
import java.util.concurrent.locks.*;

/**
 *
 * @author Kurito
 */
public class GestionPileta {

    private Lock aLock;
    private Condition enEspera;
    private Condition[] piletas;
    private Pileta[] cantPiletas;
    private boolean empezar, terminar, empezoFuncion;

    public GestionPileta(Pileta[] pile) {
        cantPiletas = pile;
        aLock = new ReentrantLock(true);
        enEspera = aLock.newCondition();
        piletas = new Condition[4];
        piletas[0] = aLock.newCondition();
        piletas[1] = aLock.newCondition();
        piletas[2] = aLock.newCondition();
        piletas[3] = aLock.newCondition();
        empezar = false;
        terminar = false;
    }

    //Metodo cliente
    public int esperarEntrarPiscina() {
        String nombre = Thread.currentThread().getName();
        int nroPiscina = verificarEspacio();

        if (nroPiscina != -1) {
            System.out.println("Hay espacio suficiente joven" + nombre + " :3");
        } else {
            System.out.println("Lo siento! Tendrá que esperar la siguiente funcion");
        }
        return nroPiscina;
    }

    public boolean esperandoFuncion(int nroPiscina) throws InterruptedException {
        try {
            aLock.lock();
            String nombre = Thread.currentThread().getName();
            System.out.println("Esperando la funcion de la pisciona " + (nroPiscina + 1) + " dijo: " + nombre);
            while (!empezar && empezoFuncion) {
                piletas[nroPiscina].await();
            }
            if (empezoFuncion) {
                System.out.println("Disfrutante de la funcion de la pileta " + nroPiscina + " dijo: " + nombre);
            } else {
                System.out.println("Se cancelo la funcion... :C");
            }
            return empezoFuncion;
        } finally {
            aLock.unlock();
        }
    }

    //Metodos llamados por reloj
    public void empezarFuncion() {
        empezar = true;
        empezoFuncion = false;
        int cumpleCondiciones = piscinasLlenas();
        if (cumpleCondiciones >= 3) {
            empezoFuncion = true;
            for (Condition pileta : piletas) {
                pileta.signalAll();
            }
        }
    }

    public void terminarFuncion() {
        empezar = false;
        for (Pileta pileta : cantPiletas) {
            pileta.vaciar();
        }
    }

    private int verificarEspacio() {
        boolean hayEspacio = false;
        int nroPileta = -1;
        int i = 0;

        while (!hayEspacio && (i < cantPiletas.length)) {
            if (cantPiletas[i].hayEspacio()) {
                cantPiletas[i].incrementar();
                nroPileta = i;
                hayEspacio = true;
            }
        }
        return nroPileta;
    }

    private int piscinasLlenas() {
        int llenas = 0;

        for (int i = 0; i < cantPiletas.length; i++) {
            if (cantPiletas[i].estaLlena()) {
                llenas++;
            }
        }
        return llenas;
    }
}
