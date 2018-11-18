/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF;

/**
 *
 * @author Kurito
 */
public class Pileta extends Thread {

    private boolean empezar;
    private boolean termino;
    private long tiempoFin;
    private long tiempoActual;
    private long tiempoEmpieza;
    private int cantTotalPersonas, cantActualPersonas;

    public Pileta(long tiempoFin, long tiempoEmpieza, int cantTotalPersonas) {
        this.empezar = false;
        this.termino = false;
        this.tiempoActual = 0;
        this.tiempoFin = tiempoFin;
        this.tiempoEmpieza = tiempoEmpieza;
        this.cantTotalPersonas = cantTotalPersonas;
        this.cantActualPersonas = 0;
    }

    public void setEmpezar(boolean empezar) {
        this.empezar = empezar;

    }

    public int getCantActualPersonas() {
        return cantActualPersonas;
    }

    public void setCantActualPersonas(int cantActualPersonas) {
        this.cantActualPersonas = cantActualPersonas;
    }

    public void setCantTotalPersonas(int cantTotalPersonas) {
        this.cantTotalPersonas = cantTotalPersonas;
    }

    public int getCantTotalPersonas() {
        return cantTotalPersonas;
    }

    public boolean hayEspacio() {
        boolean hayEspacio = false;
        if (cantActualPersonas < cantTotalPersonas) {
            hayEspacio = true;
        }
        return hayEspacio;
    }

    public void incrementar() {
        cantActualPersonas++;
    }

    public void vaciar() {
        cantActualPersonas = 0;
    }

    public boolean estaLlena() {
        boolean estaLlena = false;

        if (cantActualPersonas == cantTotalPersonas) {
            estaLlena = true;
        }
        return estaLlena;
    }

    public synchronized long getTiempoActual() {
        return tiempoActual;
    }

    public synchronized void setTermino(boolean termino) {
        this.termino = termino;
    }

    public synchronized long getTiempoEmpieza() {
        return tiempoEmpieza;
    }

    public synchronized long getTiempoFin() {
        return tiempoFin;
    }

    public synchronized void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    @Override
    public void run() {

        while (!termino) {
            System.out.println("tiempoActual: " + tiempoActual);
            if (tiempoActual / 1000 == tiempoEmpieza / 1000) {
                System.out.println("Estoy empezando evento");
            }
            if (tiempoActual / 1000 == tiempoFin / 1000) {
                System.out.println("Estoy empezando evento");
                termino = true;
            }
        }
    }
}
