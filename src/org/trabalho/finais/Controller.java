package org.trabalho.finais;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

//O objetivo dessa classe é apenas controlar a interface do programa.

public class Controller {

    @FXML
    private TextArea grammar;

    @FXML
    private TextArea automata;

    @FXML
    private AnchorPane ap;




    public void initialize(Stage primaryStage) throws IOException { //Inicializa o controlador, lendo o autômato e printando na tela.


        File AFD_File = Operacoes.GetAutomataFile(primaryStage);
        Automata AFD = Operacoes.AutomataReader(AFD_File);
        automata.setText(AFD.mostraAutomato());
        Gramatica Grammar = Gramatica.AFDtoGrammar(AFD);
        grammar.setText(Grammar.printGrammar());

    }






}
