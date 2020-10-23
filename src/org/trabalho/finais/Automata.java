package org.trabalho.finais;


import java.util.ArrayList;

public class Automata {
    String Nome; //Nome dado ao autômato
    ArrayList<String> Estados; //Lista de estados, q0 até qn
    ArrayList<String> Simbolo; //Lista de símbolos que representam o alfabeto do autômato
    String EstadoInicial; //Representa o estado inicial do automato
    ArrayList<String> EstadoFinal; //Representa o estado final do automato
    ArrayList<Programa> Programa;

    private static Automata self = new Automata(null,null);
    public static Automata self(){return self;}



    //Limpa o singleton
    public static void limpa() {
        self = new Automata(null, null);
    }


    //Construtor
    public Automata(String nome, ArrayList<String> estados, ArrayList<String> simbolo, String estadoInicial, ArrayList<String> estadoFinal, ArrayList<org.trabalho.finais.Programa> programa) {
        Nome = nome;
        Estados = estados;
        Simbolo = simbolo;
        EstadoInicial = estadoInicial;
        EstadoFinal = estadoFinal;
        Programa = programa;
    }

    //Construtor nulo
    public Automata(String nome,String estadoInicial)
    {
        Nome = nome;
        Estados = new ArrayList<>();
        Simbolo = new ArrayList<>();
        EstadoInicial = estadoInicial;
        EstadoFinal = new ArrayList<>();
        Programa = new ArrayList<>();
    }


    //Getters e Setters
    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public ArrayList<String> getEstados() {
        return Estados;
    }

    public void setEstados(ArrayList<String> estados) {
        Estados = estados;
    }

    public ArrayList<String> getSimbolo() {
        return Simbolo;
    }

    public void setSimbolo(ArrayList<String> simbolo) {
        Simbolo = simbolo;
    }

    public String getEstadoInicial() {
        return EstadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        EstadoInicial = estadoInicial;
    }

    public ArrayList<String> getEstadoFinal() {
        return EstadoFinal;
    }

    public void setEstadoFinal(ArrayList<String> estadoFinal) {
        EstadoFinal = estadoFinal;
    }

    public ArrayList<org.trabalho.finais.Programa> getPrograma() {
        return Programa;
    }

    public void setPrograma(ArrayList<org.trabalho.finais.Programa> programa) {
        Programa = programa;
    }


    public String mostraAutomato()  //Printa o autômato na tela
    {
        StringBuilder AFDText;

        AFDText = new StringBuilder("Nome: " + this.Nome + "\nLista de Estados: " + this.Estados + "\nAlfabeto: " + this.Simbolo + "\nEstado Inicial: " + this.EstadoInicial
                + "\nEstados Finais: " + this.EstadoFinal);
        for (org.trabalho.finais.Programa programa : this.Programa) {
            AFDText.append(programa.mostraPrograma());
        }
        return AFDText.toString();


    }

    //DEBUG
    public void printaAutomato()
    {
        System.out.println("Nome: " + this.Nome +"\nLista de Estados: " +  this.Estados + "\nAlfabeto: " + this.Simbolo + "\nEstado Inicial: " + this.EstadoInicial
        + "\nEstados Finais: " + this.EstadoFinal);
        for (org.trabalho.finais.Programa programa : this.Programa) {
            programa.printaPrograma();
        }

    }
}

