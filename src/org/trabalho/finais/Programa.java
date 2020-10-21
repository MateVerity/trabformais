package org.trabalho.finais;

public class Programa { //Descreve a função programa aplicada a um estado qi e um simbolo si que computa ao estado qj.
    String estado;
    String simbolo;
    String estadoDestino;

    public Programa(String estado, String simbolo, String estadoDestino) {
        this.estado = estado;
        this.simbolo = simbolo;
        this.estadoDestino = estadoDestino;
    }

    public Programa()
    {
        this.estado = null;
        this.simbolo = null;
        this.estadoDestino = null;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String mostraPrograma()
    {
        String ProgString;
        return "\n("+this.estado+","+this.simbolo+")"+"="+this.estadoDestino;

    }

    //DEBUG
    public void printaPrograma()
    {
        System.out.println("("+this.estado+","+this.simbolo+")"+"="+this.estadoDestino);
    }


}
