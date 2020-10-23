package org.trabalho.finais;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Operacoes {

    public static File GetAutomataFile(Stage mainStage) { //Retorna um ponteiro para o arquivo que contém o AFD

        FileChooser AutomataFileGetter = new FileChooser();
        Alert AutomataMsg = CreateAlert("Selecione o arquivo contendo o AFD.", "Selecione o arquivo", "Info");
        assert AutomataMsg != null;
        AutomataMsg.showAndWait();
        return AutomataFileGetter.showOpenDialog(mainStage);

    }

    public static Alert CreateAlert(String AlertMessage, String AlertTitle, String Tipo) //Cria um alerta para o usuário
    {
        if (Tipo.equals("Info")) {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle(AlertTitle);
            newAlert.setHeaderText(AlertMessage);
            return newAlert;


        }
        return null;
    }


    public static Automata AutomataReader(File AutomataFile) throws IOException //Parser para o AFD do arquivo
    {

        int i = 0;
        Automata AFD = new Automata(null, null); //Cria autômato vazio

        BufferedReader automataReader = new BufferedReader(new InputStreamReader(new FileInputStream(AutomataFile), StandardCharsets.UTF_8));
        String linha;
        linha = automataReader.readLine(); //Lê a primeira linha do autômato
        String[] Splitter = linha.split("="); //Divide a linha, onde o nome do autômato será a primeira string, e o resto a segunda
        Automata.self().Nome = Splitter[0]; //Recebe o nome do autômato
        Pattern p = Pattern.compile("(?<=\\{)([^}]+)(?=})"); //Regex para extrair os estados, alfabeto e estados finais. Eles estão entre { }.
        Matcher m = p.matcher(Splitter[1]); //
        while (m.find()) {  //Será dividido 3 vezes, visto que são 3 conjuntos definidos no arquivo.

            String[] Alocador = m.group(1).split(",");
            int k = 0;
            while (k < Alocador.length) {
                if (i == 0) { //Na primeira vez serão os estados
                    Automata.self().Estados.add(Alocador[k]);
                    k++;
                }
                if (i == 1) { //Na segunda, o alfabeto
                    Automata.self().Simbolo.add(Alocador[k]);
                    k++;
                }
                if (i == 2) { //E por ultimo os estados finais
                    Automata.self().EstadoFinal.add(Alocador[k]);
                    k++;
                }
            }
            i++;
        }
        Splitter = Splitter[1].split(",Prog,"); //Divide a linha de novo
        Splitter = Splitter[1].split(",\\{"); //E de novo, dessa vez Splitter[0] terá o estado inicial.
        Automata.self().EstadoInicial = Splitter[0];  //Assim, nosso autômato já está quase completo. Apenas falta o Programa.
        automataReader.readLine(); //Lê a segunda linha do autômato. Inicia a leitura do programa.
        p = Pattern.compile("(?<=\\()([^)]+)(?=\\))"); //Agrupa o conjunto (estado,alfabeto) do programa

        while ((linha = automataReader.readLine()) != null) {
            m = p.matcher(linha);
            m.find(); //Realiza o agrupamento
            String[] Alocador = m.group(1).split(","); //Divide o conjunto (estado,alfabeto) em dois
            Programa tempProg = new Programa();
            tempProg.estado = Alocador[0];
            tempProg.simbolo = Alocador[1];
            Splitter = linha.split("\\)=");
            tempProg.estadoDestino = Splitter[1];
            Automata.self().Programa.add(tempProg); //Adiciona a linha do programa no automato principal

        }
        return AFD; //Retorna o automato completo
    }


    public static String CheckWord(String palavra, Gramatica gramatica, String Estado, String PalavraAtual, Boolean WordList) {


        if(!WordList)
        {StringBuilder derivacoes = new StringBuilder(); //Conterá as derivações caso a palavra pertença





        if(palavra.length()  != 0)
        {
            Character w = palavra.charAt(0);

            if(!gramatica.T.contains(w.toString()))
            {
                derivacoes.append(w).append(" não pertence ao conjunto T de G");
                return derivacoes.toString();
            }



            for(GProgram Deriv : gramatica.P)
            {
                if(Deriv.estadoPartida.equals(Estado))
                {
                    for(Transicoes transicao : Deriv.transicoesDestino)
                    {
                        if(transicao.simbolo.equals((w.toString())))
                        {
                            if(PalavraAtual.isEmpty()){
                                derivacoes.append(Estado).append("=>").append(w).append(" ").append(transicao.estado).append("\n");}
                            else{

                            derivacoes.append(Estado).append("=>").append(PalavraAtual).append(w).append(" ").append(transicao.estado).append("\n");}
                            return derivacoes.toString() + CheckWord(palavra.substring(1), gramatica, transicao.estado, PalavraAtual + w.toString(), false);
                        }

                    }

                }

            }
        }

        if(palavra.length() == 0)
        {
            for(GProgram Deriv : gramatica.P)
                if(Deriv.estadoPartida.equals(Estado))
                    for(Transicoes transicao : Deriv.transicoesDestino)
                    {
                        if(transicao.simbolo.equals("ε"))
                        {
                            derivacoes.append(Estado).append("=>").append(PalavraAtual).append("\nPalavra w pertence à GERA(G).");
                            return derivacoes.toString();
                        }

                    }

            derivacoes.delete(0,derivacoes.length());
            derivacoes.append("\nPalavra w não pertence à GERA(G).");
            return derivacoes.toString();

        }


        derivacoes.delete(0,derivacoes.length());
        derivacoes.append("\nPalavra w não pertence à GERA(G).");
        return derivacoes.toString();
    }
    return null;


    }



}