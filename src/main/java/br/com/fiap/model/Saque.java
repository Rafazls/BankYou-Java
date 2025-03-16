package br.com.fiap.model;

public class Saque {
    private Long accountId;  // ID da conta
    private double valorSaque;   // Valor do saque

    public Saque(Long accountId, double valorSaque) {
        this.accountId = accountId;
        this.valorSaque = valorSaque;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getValorSaque() {
        return valorSaque;
    }

    public void setValorSaque(double valorSaque) {
        this.valorSaque = valorSaque;
    }
}
