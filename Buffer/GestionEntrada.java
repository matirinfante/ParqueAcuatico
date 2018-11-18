/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF.Buffer;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Kurito
 */
public class GestionEntrada {
    //Entrada al parque

    //Colectivo
    private final Lock aLock;
    private final Condition enEspera, colectivo, descender;
    private int personaxCole, personasxColeActual;
    private boolean llegoDestino, estaViajando, volver, viajar;

    //Entrada al parque
    private int cantMolinetes;
    private Semaphore[] molinete;

    //Constructor
    public GestionEntrada(int personaxCole, int cantMolinetes) {
        this.personaxCole = personaxCole;
        this.personasxColeActual = 0;
        aLock = new ReentrantLock(true);
        enEspera = aLock.newCondition();
        colectivo = aLock.newCondition();
        descender = aLock.newCondition();
        llegoDestino = false;
        estaViajando = false;
        volver = false;
        viajar = false;
        semaforos(cantMolinetes);
    }

    private void semaforos(int cantidad) {
        molinete = new Semaphore[cantidad];
        for (int i = 0; i < cantidad; i++) {
            molinete[i] = new Semaphore(1, true);
        }
    }

    //Metodos para la entrada de personas al parque
    //Colectivo
    //Personas esperan colectivo
    public void esperarSubir() throws InterruptedException {
        try {
            aLock.lock();
            String nombre = Thread.currentThread().getName();
            System.out.println("Esperando para subir al colectivo " + nombre);
            while ((personasxColeActual == personaxCole) || estaViajando) {
                enEspera.await();
            }
            personasxColeActual++;
            if (personasxColeActual == personaxCole) {
                viajar = true;
                colectivo.signal();
            }
            System.out.println("Estoy a punto de viajar " + nombre);
        } finally {
            aLock.unlock();
        }
    }

    public void descender() throws InterruptedException {
        try {
            aLock.lock();
            String nombre = Thread.currentThread().getName();

            System.out.println("Esperando para bajar..." + nombre);
            while (!llegoDestino) {
                descender.await();
            }
            personasxColeActual--;

            if (personasxColeActual == 0) {
                volver = true;
                llegoDestino = false;
                colectivo.signal();
            } else {
                descender.signal();
            }
            System.out.println("Ya baje del colectivo");
        } finally {
            aLock.unlock();
        }
    }

    //Metodo de colectivo
    public void esperarViajar() throws InterruptedException {
        try {
            aLock.lock();
            System.out.println("Esperando para irse el colectivo...");
            while (!viajar || personasxColeActual < personaxCole) {
                colectivo.await();
            }
            System.out.println("Todo listo para partir...");
        } finally {
            aLock.unlock();
        }
    }

    public void esperarVolver() throws InterruptedException {
        try {
            aLock.lock();
            System.out.println("Esperando para volver");
            llegoDestino = true;
            descender.signal();
            while (!volver) {
                colectivo.await();
            }
            System.out.println("Volviendo...");
        } finally {
            aLock.unlock();
        }
    }

    public void volvioDestino() throws InterruptedException {
        try {
            aLock.lock();
            estaViajando = false;
            enEspera.signalAll();
            System.out.println("Listo para cargar de nuevo...!");
        } finally {
            aLock.unlock();
        }
    }
//Entrada al parque

    public int esperarEntrarParque() throws InterruptedException {
        String nombre = Thread.currentThread().getName();
        System.out.println("Esperando para entrar al parque " + nombre);
        Random r = new Random();
        int nroRandom = r.nextInt(molinete.length) + 1;
        molinete[nroRandom - 1].acquire();
        System.out.println("Haciendo tramite de entrada nro: " + nroRandom + " cliente: " + nombre);
        return nroRandom - 1;
    }

    public void entrandoParque(int nroMolinete) {
        String nombre = Thread.currentThread().getName();
        System.out.println("Ya estoy dentro del parque... a disfrutar - Dijo: " + nombre);
        molinete[nroMolinete].release();
    }

}
