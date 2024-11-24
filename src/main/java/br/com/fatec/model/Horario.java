/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.time.LocalTime;

/**
 *
 * @author Pichau
 */
public class Horario {
    private int idHorario;
    private LocalTime horario;

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Horario() {
    }

    public Horario(int idHorario, LocalTime horario) {
        this.idHorario = idHorario;
        this.horario = horario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.idHorario;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Horario other = (Horario) obj;
        return this.idHorario == other.idHorario;
    }

    @Override
    public String toString() {
        return horario.toString();
    }
    
    
}
