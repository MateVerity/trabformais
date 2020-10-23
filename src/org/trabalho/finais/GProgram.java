package org.trabalho.finais;

import java.util.ArrayList;

public class GProgram {


    String estadoPartida;
    ArrayList<Transicoes> transicoesDestino;

    public GProgram(String estadoPartida, ArrayList<Transicoes> transicoesDestino) {
        this.estadoPartida = estadoPartida;
        this.transicoesDestino = transicoesDestino;
    }

    public GProgram()
    {
        this.estadoPartida = null;
        this.transicoesDestino = new ArrayList<>();
    }
}
