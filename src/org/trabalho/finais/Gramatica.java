package org.trabalho.finais;

import java.util.ArrayList;

public class Gramatica {

    /*Convertendo um AFD, definido como M = (Q,Σ,δ,q0,F), para uma gramática regular (G = (V,T,S,P)), faremos da seguinte forma:
     1 - O símbolo inicial da gramática é q0, o estado inicial não-final do AFD.
     2 - Para cada transição qi à qj em algum símbolo, criar a regra de produção qi -> (simbolo)qj
     3 - Para cada estado final qk, formar a regra de produção qk -> λ
     */

    ArrayList<String> V; //Lista de Estados
    ArrayList<String> T; //Lista de Símbolos
    String S;           //Estado inicial não-terminal
    ArrayList<GProgram> P;  //Conjunto de Produções

    /*
    Representaremos V e T como uma lista de Strings. Já as Produções faremos da seguinte forma: Será uma lista de GPrograms, definimos um GProgram
    como <Estado de Partida> -> <Transição1> <Transição2> <Transição n>, onde uma Transição é composta por <Simbolo> <Estado>, por exemplo :
    Gprogram[1] = q0 -> a q1 | b q2 | c q3
    Gprogram[2] = q1 -> a q2 | b q3 | c q3
    Gprogram[n] = qn -> a qn ...
     */


    //Aqui criamos o singleton
    private static Gramatica self = new Gramatica();
    public static Gramatica self(){return self;}
    public static void limpa(){
        self = new Gramatica();
    }

    //Construtores
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




    public static void AFDtoGrammar(Automata AFD)  //Converte o AFD em Gramática Regular. Como estamos trabalhando com um singleton, a função pode ser void.
    {

        ArrayList<GProgram> Producoes = new ArrayList<>(); //Inicializa uma lista de produções


        //Estes atributos da gramática são basicamente apenas copiar do autômato
        Gramatica.self().S = AFD.getEstadoInicial();
        Gramatica.self().V = AFD.getEstados();
        Gramatica.self().T = AFD.getSimbolo();


        for(String Estado : Gramatica.self().V) //Para cada estado da gramática
        {
            GProgram tempGProgram = new GProgram(); //Cria um GProgram temporário

            tempGProgram.estadoPartida = Estado;    //onde o estado de partida dessa produção será o estado atual do loop


            for(Programa P : AFD.Programa) //Para cada aplicação P da função Programa do autômato
            {
                if(P.estado.equals(Estado)) //Se o estado x de (x,simbolo)=estadoDestino for igual ao estado atual da gramática. Basicamente cria
                {                          // todas as transições da aplicação P do programa de um certo estado. Por ex, todas as transições que partem de q0.
                    Transicoes tempTransicao = new Transicoes();    //Monta a regra de transição
                    tempTransicao.estado = P.estadoDestino;
                    tempTransicao.simbolo = P.simbolo;
                    tempGProgram.transicoesDestino.add(tempTransicao); //Adiciona a transição na lista de Gprogram do estado atual

                }


            }

            Gramatica.self().P.add(tempGProgram); //Adiciona a regra de produção na gramática

        }

        for(String Estado : Gramatica.self().V)  //Adiciona as regras de produções terminais na gramática
        {
            GProgram tempGProgram = new GProgram();
            tempGProgram.estadoPartida = Estado;

            if(AFD.EstadoFinal.contains(Estado))
            {
                Transicoes tempTransicao = new Transicoes();
                tempTransicao.estado = "";
                tempTransicao.simbolo = "ε";
                tempGProgram.transicoesDestino.add(tempTransicao);
                Gramatica.self().P.add(tempGProgram);

            }


        }
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

    //Getters e Setters
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


