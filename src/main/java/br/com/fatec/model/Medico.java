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
public class Medico {
    private int idMedico;
    private String nomeMedico;
    private String crm;
    private LocalDate dataNasc;
    private String telefone;
    private Especializacao especializacao;
    
    //getters e setters

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Especializacao getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(Especializacao especializacao) {
        this.especializacao = especializacao;
    }
    
    
    
    //CONSTRUTORES

    public Medico(Especializacao especializacao) {
        this.especializacao = especializacao;
    }

    public Medico(int idMedico, String nomeMedico, String crm, LocalDate dataNasc, String telefone, Especializacao especializacao) {
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.crm = crm;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.especializacao = especializacao;
    }
        

    
    //equals e hashcode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idMedico;
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
        final Medico other = (Medico) obj;
        return this.idMedico == other.idMedico;
    }

    @Override
    public String toString() {
        return nomeMedico;
    }
    
    
    
}
