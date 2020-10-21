package org.trabalho.finais;

public class Transiçoes {

    String simbolo;
    String estado;





    public Transiçoes() //Construtor para um estado final
    {
        this.simbolo = null;
        this.estado = null;
    }

    public Transiçoes(String simbolo, String estado) {
        this.simbolo = simbolo;
        this.estado = estado;
    }
}
