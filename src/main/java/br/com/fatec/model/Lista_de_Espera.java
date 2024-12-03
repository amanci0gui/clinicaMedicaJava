/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.util.Objects;

/**
 *
 * @author Pichau
 */
public class Lista_de_Espera {
    private int codLista;
    private String nome;
    private String sintomas;
    private String cpf;
    private boolean preferencial;

    public int getCodLista() {
        return codLista;
    }

    public void setCodLista(int codLista) {
        this.codLista = codLista;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isPreferencial() {
        return preferencial;
    }

    public void setPreferencial(boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Lista_de_Espera() {
    }

    public Lista_de_Espera(int codLista, String nome, String sintomas, String cpf, boolean preferencial) {
        this.codLista = codLista;
        this.nome = nome;
        this.sintomas = sintomas;
        this.cpf = cpf;
        this.preferencial = preferencial;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.codLista;
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
        final Lista_de_Espera other = (Lista_de_Espera) obj;
        return this.codLista == other.codLista;
    }

    

    @Override
    public String toString() {
        return nome;
    }
    
    
}
