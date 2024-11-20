/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author Aluno
 */
public class Especializacao {
    private int idEspec;
    private String nomeEspec;

    public int getIdEspec() {
        return idEspec;
    }

    public void setIdEspec(int idEspec) {
        this.idEspec = idEspec;
    }

    public String getNomeEspec() {
        return nomeEspec;
    }

    public void setNomeEspec(String nomeEspec) {
        this.nomeEspec = nomeEspec;
    }

    public Especializacao() {
    }

    public Especializacao(int idEspec, String nomeEspec) {
        this.idEspec = idEspec;
        this.nomeEspec = nomeEspec;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.idEspec;
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
        final Especializacao other = (Especializacao) obj;
        return this.idEspec == other.idEspec;
    }

    @Override
    public String toString() {
        return nomeEspec;
    }
    
    
}
