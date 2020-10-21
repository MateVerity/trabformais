package org.trabalho.finais;

import java.util.ArrayList;

public class Gramatica {

    //Convertendo um AFD, definido como M = (Q,Σ,δ,q0,F), para uma gramática regular (G = (V,T,S,P)), faremos da seguinte forma:
    // 1 - O símbolo inicial da gramática é q0, o estado inicial não-final do AFD.
    // 2 - Para cada transição qi à qj em algum símbolo, criar a regra de produção qi -> (simbolo)qj
    // 3 - Para cada estado final qk, formar a regra de produção qk -> λ

    ArrayList<String> V; //Lista de Estados
    ArrayList<String> T; //Lista de Símbolos
    String S;           //Estado inicial não-terminal
    ArrayList<GProgram> P;  //Conjunto de Produções
          //Se é final ou não


    public Gramatica(ArrayList<String> v, ArrayList<String> t, String s, ArrayList<GProgram> p) {
        V = v;
        T = t;
        S = s;
        P = p;
    }

    public Gramatica() //Construtor de gramática vazia
    {
        this.V = new ArrayList<>() ;
        this.T = new ArrayList<>();
        this.S = null;
        this.P = new ArrayList<>();
    }


    public static Gramatica AFDtoGrammar(Automata AFD)  //Converte o AFD em Gramática Regular
    {
        Gramatica tempGrammar = new Gramatica();
        ArrayList<GProgram> Producoes = new ArrayList<>();


        tempGrammar.S = AFD.getEstadoInicial();
        tempGrammar.V = AFD.getEstados();
        tempGrammar.T = AFD.getSimbolo();

        for(String Estado : tempGrammar.V) //Para cada estado da gramática
        {
            GProgram tempGProgram = new GProgram();

            tempGProgram.estadoPartida = Estado;


            for(Programa P : AFD.Programa) //Cria uma regra de produção
            {
                if(P.estado.equals(Estado))
                {
                    Transicoes tempTransicao = new Transicoes();
                    tempTransicao.estado = P.estadoDestino;
                    tempTransicao.simbolo = P.simbolo;
                    tempGProgram.transicoesDestino.add(tempTransicao);

                }


            }

            tempGrammar.P.add(tempGProgram);

        }

        for(String Estado : tempGrammar.V)  //Cria uma regra de produção terminal para cada estado terminal do AFD
        {
            GProgram tempGProgram = new GProgram();
            tempGProgram.estadoPartida = Estado;

            if(AFD.EstadoFinal.contains(Estado))
            {
                Transicoes tempTransicao = new Transicoes();
                tempTransicao.estado = "";
                tempTransicao.simbolo = "ε";
                tempGProgram.transicoesDestino.add(tempTransicao);
                tempGrammar.P.add(tempGProgram);

            }


        }





        return tempGrammar;
    }


    public String printGrammar() //Cria uma string contendo a gramática, serve para printar na tela
    {
        StringBuilder grammarString;

        grammarString = new StringBuilder("V :" + this.V + "\nT :" + this.T + "\nS :" + this.S);
        for (GProgram temp : P) {
            grammarString.append("\n").append(temp.estadoPartida).append("->");
            for (Transicoes tttemp : temp.transicoesDestino) {
                if (tttemp.simbolo.equals("ε"))
                    grammarString.append(tttemp.simbolo);
                else
                    grammarString.append(tttemp.simbolo).append(" ").append(tttemp.estado).append(" | ");
            }
        }

        return grammarString.toString();

    }

    public ArrayList<String> getV() {
        return V;
    }

    public void setV(ArrayList<String> v) {
        V = v;
    }

    public ArrayList<String> getT() {
        return T;
    }

    public void setT(ArrayList<String> t) {
        T = t;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public ArrayList<GProgram> getP() {
        return P;
    }

    public void setP(ArrayList<GProgram> p) {
        P = p;
    }
}


