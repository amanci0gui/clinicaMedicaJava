/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.time.LocalDate;

/**
 *
 * @author Pichau
 */
public class Consulta {
    private int idConsulta;
    private Clientes cliente;
    private Medico medico;
    private Horario horario;
    private LocalDate dataConsul;
    private String sintomas;

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public LocalDate getDataConsul() {
        return dataConsul;
    }

    public void setDataConsul(LocalDate dataConsul) {
        this.dataConsul = dataConsul;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public Consulta(Clientes cliente, Medico medico, Horario horario) {
        this.cliente = cliente;
        this.medico = medico;
        this.horario = horario;
    }

    public Consulta(int idConsulta, Clientes cliente, Medico medico, Horario horario, LocalDate dataConsul, String sintomas) {
        this.idConsulta = idConsulta;
        this.cliente = cliente;
        this.medico = medico;
        this.horario = horario;
        this.dataConsul = dataConsul;
        this.sintomas = sintomas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.idConsulta;
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
        final Consulta other = (Consulta) obj;
        return this.idConsulta == other.idConsulta;
    }

    @Override
    public String toString() {
        return dataConsul.toString();
    }
    
    
}
