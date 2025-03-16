package br.com.fiap.model;

public class Deposito {
    private Long accountId;
    private double valorDeposito; 

    public Deposito(Long accountId, double valorDeposito) {
        this.accountId = accountId;
        this.valorDeposito = valorDeposito;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(double amount) {
        this.valorDeposito = amount;
    }
}
