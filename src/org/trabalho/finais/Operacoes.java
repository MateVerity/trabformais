package org.trabalho.finais;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Operacoes {

    public static File GetAutomataFile(Stage mainStage){ //Lê o arquivo contendo o AFD inicial, recebe uma Scene do javaFX

        FileChooser AutomataFileGetter = new FileChooser();
        Alert AutomataMsg = CreateAlert("Selecione o arquivo contendo o AFD.", "Selecione o arquivo", "Info");
        assert AutomataMsg != null;
        AutomataMsg.showAndWait();
        return AutomataFileGetter.showOpenDialog(mainStage);

    }

    public static Alert CreateAlert(String AlertMessage, String AlertTitle, String Tipo) //Cria um alerta para o usuário
    {
        if(Tipo.equals("Info")){
        Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
        newAlert.setTitle(AlertTitle);
        newAlert.setHeaderText(AlertMessage);
        return newAlert;


    }
        return null;
    }


    public static Automata AutomataReader(File AutomataFile) throws IOException //Lê o autômato do arquivo e o insere na memória.
    {

        Automata AFD = new Automata(null, null); //Cria automatô vazio
        

        ArrayList<String> EstadosTemp = new ArrayList<>(); //Lista temporária de estados
        ArrayList<String> SimbolosTemp = new ArrayList<>();  //Lista temporária de símbolos
        BufferedReader automataReader = new BufferedReader(new InputStreamReader(new FileInputStream(AutomataFile), "UTF-8"));
        String linha;
        linha = automataReader.readLine(); //Lê a primeira linha do autômato
            String[] Nome = linha.split("="); //Divide a linha, onde o nome do autômato será a primeira string, e o resto a segunda
            AFD.Nome = Nome[0]; //Recebe o nome do autômato
            Pattern p = Pattern.compile("(?<=\\{)([^\\}]+)(?=\\})"); //Regex para extrair os estados, alfabeto e estados finais.
            Matcher m = p.matcher(Nome[1]); //
            m.find();
            String[] Estados = m.group(1).split(",");
            int i = 0;
            while(i < Estados.length)
            {
                EstadosTemp.add(Estados[i]);
                i++;
            }
            m.find();







    return null;






    }


}
