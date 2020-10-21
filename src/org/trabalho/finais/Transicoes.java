package org.trabalho.finais;

public class Transicoes {

    String simbolo;
    String estado;





    public Transicoes() //Construtor para um estado final
    {
        this.simbolo = null;
        this.estado = null;
    }

    public Transicoes(String simbolo, String estado) {
        this.simbolo = simbolo;
        this.estado = estado;
    }
}
