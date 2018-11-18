/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF;

import TPOF.Buffer.GestionPileta;
import java.util.ArrayList;
import java.util.Timer;

/**
 *
 * @author Kurito
 */
public class Reloj implements Runnable {

    private long tiempoInicial;
    private long timeStamp;
    private double tiempoFinal;
    private long unaHora;
    private long receso;
    private int inicioIngresoParque, finIngresoParque, finActividades;
    private ArrayList<Integer> horarios;
    //Pileta pileta;
    GestionPileta ge;

    public Reloj(long tiempoInicial, double tiempoFinal, long timeStamp, GestionPileta ge, int horarioIngresoParque, int finIngresoParque, int finActividades) {
        this.tiempoInicial = tiempoInicial;
        this.tiempoFinal = tiempoFinal;
        this.timeStamp = timeStamp;
        this.ge = ge;
        unaHora = 60000;
        receso = 2500;
        gestionHorariosPiscina();
        this.inicioIngresoParque = horarioIngresoParque;
        this.finIngresoParque = finIngresoParque;
        this.finActividades = finActividades;

    }

    private void gestionHorariosPiscina() {
        horarios = new ArrayList();
        horarios.add(10000);//10750
        horarios.add(11000);
        horarios.add(12000);
        horarios.add(13000);
        horarios.add(14000);
        horarios.add(15000);
        horarios.add(16000);
        horarios.add(17000);
    }

    public double getTiempoFinal() {
        return tiempoFinal;
    }

    public long getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoFinal(double tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public void setTiempoInicial(long tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public void empezar() {
        tiempoInicial = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long actual = System.currentTimeMillis() - timeStamp;
        empezar();
        int i = 0;

        while (actual <= tiempoFinal) {

            actual = System.currentTimeMillis() - timeStamp;
            System.out.println("actual que tenes?: " + actual);
            if (horarios.get(i) / 1000 == actual / 1000) {
                //ge.empezarFuncion();
                System.out.println("Empezando la funcion del horario " + horarios.get(i) / 1000 + ":00");
                if (horarios.get(i) + 1000 - receso / 1000 >= actual - receso / 1000) {

                    System.out.println("Se terminó la funcion del horario " + horarios.get(i) / 1000 + ":00");

                }
            }
            if (horarios.get(i) == actual / 1000 && i != horarios.size() - 1) {
                i++;
            }
        }
    }

}
