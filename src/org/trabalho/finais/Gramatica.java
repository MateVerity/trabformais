package org.trabalho.finais;

import java.util.ArrayList;
import java.util.Iterator;

public class Gramatica {

    //Convertendo um AFD, definido como M = (Q,Σ,δ,q0,F), para uma gramática regular (G = (V,T,S,P)), faremos da seguinte forma:
    // 1 - O símbolo inicial da gramática é q0, o estado inicial não-final do AFD.
    // 2 - Para cada transição qi à qj em algum símbolo, criar a regra de produção qi -> (simbolo)qj
    // 3 - Para cada estado final qk, formar a regra de produção qk -> λ

    ArrayList<String> V; //Lista de Estados
    ArrayList<String> T; //Lista de Símbolos
    String S;           //Estado inicial não-terminal
    ArrayList<GProgram> P;  //Conjunto de Produções


    public Gramatica(ArrayList<String> v, ArrayList<String> t, String s, ArrayList<GProgram> p) {
        V = v;
        T = t;
        S = s;
        P = p;
    }

    public Gramatica()
    {
        this.V = new ArrayList<>() ;
        this.T = new ArrayList<>();
        this.S = null;
        this.P = new ArrayList<>();
    }


    public static Gramatica AFDtoGrammar(Automata AFD)
    {
        Gramatica tempGrammar = new Gramatica();
        ArrayList<GProgram> Producoes = new ArrayList<>();


        tempGrammar.S = AFD.getEstadoInicial();
        tempGrammar.V = AFD.getEstados();
        tempGrammar.T = AFD.getSimbolo();

        Iterator<Programa> iterator = AFD.Programa.iterator();
        while(iterator.hasNext())
        {
            GProgram tempGProgram = new GProgram();
            tempGProgram.estadoPartida = iterator.next().estado;



            Iterator<Programa> iterator2 = AFD.Programa.iterator();

            while(iterator2.hasNext()) {
                Programa temp = iterator2.next();
                if(temp.estado.equals(tempGProgram.estadoPartida)) {
                    Transiçoes tempTransicao = new Transiçoes();
                    tempTransicao.simbolo = temp.simbolo;
                    tempTransicao.estado = temp.estadoDestino;
                    tempGProgram.transicoesDestino.add(tempTransicao);


                }
            }

            tempGrammar.P.add(tempGProgram);


        }

        iterator = AFD.Programa.iterator();
        while(iterator.hasNext()) {
            GProgram tempGProgram = new GProgram();
            tempGProgram.estadoPartida = iterator.next().estado;
            if (AFD.EstadoFinal.contains(tempGProgram.estadoPartida)) {
                Transiçoes tempTransicao = new Transiçoes();
                tempTransicao.estado = "";
                tempTransicao.simbolo = "λ";
                tempGProgram.transicoesDestino.add(tempTransicao);

                if(!tempGrammar.P.forEach().estadoPartida.contains(tempGProgram.estadoPartida))
                {
                    tempGrammar.P.add(tempGProgram);
                    System.out.println(tempGProgram.estadoPartida + tempGProgram.transicoesDestino);
                }


            }

        }




        return tempGrammar;
    }


    public String printGrammar()
    {
        String grammarString = null;

        grammarString = "\nV :" + this.V + "\nT :" + this.T + "\nS :" + this.S;
        Iterator<GProgram> it = P.iterator();
        while(it.hasNext())
        {
            GProgram temp = it.next();
            grammarString = grammarString + "\n"+temp.estadoPartida + "->";
            Iterator<Transiçoes> ttemp = temp.transicoesDestino.iterator();
            while(ttemp.hasNext()) {
                Transiçoes tttemp = ttemp.next();
                grammarString = grammarString + tttemp.simbolo+" " + tttemp.estado+ " | ";
            }
        }

        return grammarString;

    }

}


