package org.trabalho.finais;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.Objects;

public class Controller {

    /*Essa classe é responsável pela interface do programa, eventos de quando o usuário clica em algum botão, etc*/

    @FXML
    private TextArea grammar;

    @FXML
    private TextArea automata;

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField campotexto;

    @FXML
    private TextArea campopalavra;

    @FXML
    private TextArea campolista;


    public void initialize() {


        try {
            File AutomataFile = Operacoes.GetAutomataFile(Main.getPrimaryStage()); //Variável que recebe o arquivo contendo o AFD
            Operacoes.AutomataReader(AutomataFile);                     //Lê e aloca o autômato na memória
            Gramatica.AFDtoGrammar(Automata.self());                    //Monta a gramática e aloca na memória
            populateTextAreas(Automata.self(), Gramatica.self());    //Printa ambos na tela
        }
        catch (Exception e)
        {
            Objects.requireNonNull(Operacoes.CreateAlert("Insira um arquivo válido.", "Erro", "Info")).showAndWait();
        }


    }

    public void populateTextAreas(Automata AFD, Gramatica Grammar) //Printa automato e gramatica nas text areas
    {
        automata.setText(AFD.mostraAutomato());
        grammar.setText(Grammar.printGrammar());
    }


    public void checkWord() //Checa a palavra que o usuário digitou
    {
        campopalavra.setText("");
        campopalavra.setText(Operacoes.CheckWord(campotexto.getText(), Gramatica.self(), Gramatica.self().S, "", false)); //Retorna a palavra escrita pelo usuário

    }

    public void parseWordList()  //Lê o arquivo .csv contendo a lista de palavras e retorna o conjunto ACEITA e REJEITA.
    {
        try {
            campolista.setText("");
            campolista.setText(Operacoes.ParsedWordList(Main.getPrimaryStage()));
        }
        catch(Exception e)
        {
            Objects.requireNonNull(Operacoes.CreateAlert("Insira um arquivo válido.", "Erro", "Info")).showAndWait();
        }
    }

    public void outroAFD() {
        Automata.limpa();
        Gramatica.limpa();
        campolista.setText("");
        campopalavra.setText("");
        initialize();
    }



}
