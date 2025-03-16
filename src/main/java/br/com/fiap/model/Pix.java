package br.com.fiap.model;

public class Pix {
    private Long contaOrigem;     
    private Long contaDestino; 
    private double valor; 
    
    public Pix(Long contaOrigem, Long contaDestino, double valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public Long getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Long contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Long getContadestino() {
        return contaDestino;
    }

    public void setContadestino(Long contaDestino) {
        this.contaDestino = contaDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
