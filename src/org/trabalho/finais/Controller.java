package org.trabalho.finais;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private TextArea grammar;

    @FXML
    private TextArea automata;

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField campotexto;





    public void initialize()
    {

    }

    public void populateTextAreas(Automata AFD, Gramatica Grammar)
    {
        automata.setText(AFD.mostraAutomato());
        grammar.setText(Grammar.printGrammar());
    }


    public String checkWord()
    {

       return Operacoes.CheckWord(campotexto.getText(), Gramatica.self()); //Retorna a palavra escrita pelo usuário

    }





}
