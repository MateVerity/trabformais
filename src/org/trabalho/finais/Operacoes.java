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

    public static File GetAutomataFile(Stage mainStage){ //Lê o arquivo contendo o AFD inicial

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

        int i = 0;
        Automata AFD = new Automata(null, null); //Cria autômato vazio

        BufferedReader automataReader = new BufferedReader(new InputStreamReader(new FileInputStream(AutomataFile), "UTF-8"));
        String linha;
        linha = automataReader.readLine(); //Lê a primeira linha do autômato
            String[] Splitter = linha.split("="); //Divide a linha, onde o nome do autômato será a primeira string, e o resto a segunda
            AFD.Nome = Splitter[0]; //Recebe o nome do autômato
            Pattern p = Pattern.compile("(?<=\\{)([^\\}]+)(?=\\})"); //Regex para extrair os estados, alfabeto e estados finais. Eles estão entre { }.
            Matcher m = p.matcher(Splitter[1]); //
            while(m.find()) {  //Será dividido 3 vezes, visto que são 3 conjuntos definidos no arquivo.

                    String[] Alocador = m.group(1).split(",");
                    int k = 0;
                    while(k < Alocador.length)
                    {
                        if(i==0) { //Na primeira vez serão os estados
                            AFD.Estados.add(Alocador[k]);
                            k++;
                        }
                        if(i==1){ //Na segunda, o alfabeto
                            AFD.Simbolo.add(Alocador[k]);
                            k++;
                        }
                        if(i==2){ //E por ultimo os estados finais
                            AFD.EstadoFinal.add(Alocador[k]);
                            k++;
                        }
                    }
                    i++;
                }
            Splitter = Splitter[1].split(",Prog,"); //Divide a linha de novo
            Splitter = Splitter[1].split(",\\{"); //E de novo, dessa vez Splitter[0] terá o estado inicial.
            AFD.EstadoInicial = Splitter[0];  //Assim, nosso autômato já está quase completo. Apenas falta o Programa.
        linha = automataReader.readLine(); //Lê a segunda linha do autômato. Inicia a leitura do programa.
        p = Pattern.compile("(?<=\\()([^\\)]+)(?=\\))"); //Agrupa o conjunto (estado,alfabeto) do programa

        while((linha = automataReader.readLine()) != null)
        {
            m = p.matcher(linha);
            m.find(); //Realiza o agrupamento
                String[] Alocador = m.group(1).split(","); //Divide o conjunto (estado,alfabeto) em dois
                Programa tempProg = new Programa();
                tempProg.estado = Alocador[0];
                tempProg.simbolo = Alocador[1];
                Splitter = linha.split("\\)=");
                tempProg.estadoDestino = Splitter[1];
                AFD.Programa.add(tempProg); //Adiciona a linha do programa no automato principal

            }
    return AFD; //Retorna o automato completo
    }
}
