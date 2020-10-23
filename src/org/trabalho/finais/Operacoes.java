package org.trabalho.finais;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Nessa classe se encontram os métodos responsáveis pelas operações do programa.
 */

public class Operacoes {

    public static File GetAutomataFile(Stage mainStage) { //Retorna o "ponteiro" contendo o arquivo que contém o AFD

        FileChooser AutomataFileGetter = new FileChooser();
        Alert AutomataMsg = CreateAlert("Selecione o arquivo contendo o AFD.", "Selecione o arquivo", "Info");
        assert AutomataMsg != null;
        AutomataMsg.showAndWait();
        return AutomataFileGetter.showOpenDialog(mainStage);

    }

    public static Alert CreateAlert(String AlertMessage, String AlertTitle, String Tipo) //Cria um alerta
    {
        if (Tipo.equals("Info")) {
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle(AlertTitle);
            newAlert.setHeaderText(AlertMessage);
            return newAlert;


        }
        return null;
    }


    public static String ParsedWordList(Stage mainStage) throws IOException { //Lê a lista de palavras, Retorna o conjunto ACEITA e REJEITA

         class Conjunto{ //Desnecessário, mas por que não?
             ArrayList<String> ACEITA = new ArrayList<>();
             ArrayList<String> REJEITA = new ArrayList<>();

         }



        Conjunto parsedGroup = new Conjunto();
        FileChooser WordListFileChooser = new FileChooser();
        File WordList = WordListFileChooser.showOpenDialog(mainStage);
        BufferedReader WordListReader = new BufferedReader(new InputStreamReader(new FileInputStream(WordList), StandardCharsets.UTF_8));
        String linha = WordListReader.readLine();
        String[] Words = linha.split(",");
        //Basicamente tirou as virgulas do arquivo e inseriu as palavras no array
        int i;
        for(i=0;i<Words.length;i++) //Para cada palavra
        {
            Words[i] = Words[i].trim();

            if(CheckWord(Words[i], Gramatica.self(), Gramatica.self().S, "", true).equals("ACEITA")) //Se ela é aceita
            {
                parsedGroup.ACEITA.add(Words[i]); //Adiciona na lista de aceita
            }
            else
            {
                parsedGroup.REJEITA.add(Words[i]); //ou na lista de rejeita
            }

        }

        return "ACEITA = {" + parsedGroup.ACEITA.toString() +"}\nREJEITA = {" + parsedGroup.REJEITA.toString() + "}"; //Retorna os conjuntos



    }



    public static void AutomataReader(File AutomataFile) throws IOException //Parser para o AFD do arquivo, preenche o singleton com o automato no arquivo.
    {

        int i = 0;

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
            Automata.self().Programa.add(tempProg); //Adiciona a linha do programa no singleton

        }
    }


    /*Método mais importante:
    Se IsWordList = true, significa que estamos acessando o método à partir da lista de palavras. Se for falso, estamos testando apenas uma.
    Isso é necessário pois se for apenas uma palavra, precisamos mostrar as derivações.
    Essa função é definida recursivamente. A cada chamada, estamos entrando com <palavra> menos <primeira letra>.
    Por exemplo : abaab -> chamada recursiva -> baab
    Se entrarmos com uma palavra vazia, ou seja, a palavra já percorreu todos as transições, testamos se o estado atual é final. Se for, a palavra é aceita,
    caso contrário, ela é rejeitada.
    Se a palavra inicialmente entrada for vazia ou algum símbolo não pertença à gramática, ela é rejeitada na mesma hora.
    Se a palavra não é vazia e não tem transições possíveis, ela é rejeitada. (Provavelmente o AFD entrado não era válido)
    A cada recursão, devolvemos as derivações que ocorreram, no final temos uma grande string contendo tudo, e se a palavra é aceita ou não.

     */
    public static String CheckWord(String palavra, Gramatica gramatica, String Estado, String PalavraAtual, Boolean IsWordList) {

        StringBuilder derivacoes = new StringBuilder(); //Conterá as derivações caso a palavra pertença

        if(palavra.length()  != 0) //Se a palavra NÃO for vazia
        {
            Character w = palavra.charAt(0);

            if(!gramatica.T.contains(w.toString())) //Se a palavra contém simbolo inválido
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
                            if(IsWordList)
                            {
                                return CheckWord(palavra.substring(1), gramatica, transicao.estado, PalavraAtual + w.toString(), true);
                            }


                            else {
                                if (PalavraAtual.isEmpty()) {
                                    derivacoes.append(Estado).append("=>").append(w).append(" ").append(transicao.estado);
                                } else {

                                    derivacoes.append("=>").append(PalavraAtual).append(w).append(" ").append(transicao.estado);
                                }
                                return derivacoes.toString() + CheckWord(palavra.substring(1), gramatica, transicao.estado, PalavraAtual + w.toString(), false);
                            }
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
                            if(IsWordList)
                            {
                                return "ACEITA";
                            }
                            else {
                                derivacoes.append(Estado).append("=>").append(PalavraAtual).append("\nPalavra w pertence à GERA(G).");
                                return derivacoes.toString();
                            }
                        }

                    }

            if(IsWordList)
            {
                return "REJEITA";
            }
            else {
                derivacoes.delete(0, derivacoes.length());
                derivacoes.append("\nPalavra w não pertence à GERA(G).");
                return derivacoes.toString();
            }
        }


        derivacoes.delete(0,derivacoes.length());
        derivacoes.append("\nPalavra w não pertence à GERA(G).");
        return derivacoes.toString();


    }



}