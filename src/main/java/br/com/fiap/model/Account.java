package br.com.fiap.model;

import java.util.Random;

import br.com.fiap.enums.AccountType;

public class Account {
    private Long id;
    private String dataAbertura;
    private double saldoInicial;
    public boolean ativo;
    private AccountType tipoConta; 
    private Double number;
    private Double agency;
    private String name;
    private String CPF;

    public Account(Long id, String dataAbertura, double saldoInicial, boolean ativo, AccountType tipoConta, Double number,
            Double agency, String name, String cpf) {
        this.id = (id == null) ? Math.abs(new Random().nextLong()) : id;
        this.dataAbertura = dataAbertura;
        this.saldoInicial = saldoInicial;
        this.ativo = ativo;
        this.tipoConta = tipoConta;
        this.number = number;
        this.agency = agency;
        this.name = name;
        this.CPF = cpf;
    }

    public Long getId() {
        return id;
    }   

    public String getDataAbertura() {
        return dataAbertura;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean setAtivo(boolean ativo) {
        return this.ativo = ativo;
    }

    public AccountType getTipoConta() {
        return tipoConta;
    }

    public Double getNumber() {
        return number;
    }

    public Double getAgency() {
        return agency;
    }

    public String getName() {
        return name;
    }

    public String getCPF() {
        return CPF;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}
