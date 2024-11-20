/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 *a
 * @author Pichau
 */
public class Clientes {
    private int idCliente;
    private String nomeCliente;
    private String cpf;
    private LocalDate dataNasc;
    private String convOuPart;
    private String planoConv;
    private String telefone;
    
    //GETTERS E SETTERS

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getConvOuPart() {
        return convOuPart;
    }

    public void setConvOuPart(String convOuPart) {
        this.convOuPart = convOuPart;
    }

    public String getPlanoConv() {
        return planoConv;
    }

    public void setPlanoConv(String planoConv) {
        this.planoConv = planoConv;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    //CONSTRUTORES

    public Clientes() {
    }

    public Clientes(int idCliente, String nomeCliente, String cpf, LocalDate dataNasc, String convOuPart, String planoConv, String telefone) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.convOuPart = convOuPart;
        this.planoConv = planoConv;
        this.telefone = telefone;
    }
    
    //EQUALS E HASHCODE

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.idCliente;
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
        final Clientes other = (Clientes) obj;
        return this.idCliente == other.idCliente;
    }

    @Override
    public String toString() {
        return nomeCliente;
    }

    
    
    
    
}
