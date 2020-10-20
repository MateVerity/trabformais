package org.trabalho.finais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{  //Código de inicialização padrão do programa
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Trabalho Final");
        primaryStage.setScene(new Scene(root, 1080, 600));
        primaryStage.getIcons().add(new Image("appicon.png"));
        primaryStage.show();
        //Inicio das operações
        File AutomataFile = Operacoes.GetAutomataFile(primaryStage); //Variável que recebe o arquivo contendo o AFD
        Automata PREULA = Operacoes.AutomataReader(AutomataFile);



    }


    public static void main(String[] args) {
        launch(args);
    }
}
