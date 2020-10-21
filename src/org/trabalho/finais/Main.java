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

        //File AutomataFile = Operacoes.GetAutomataFile(primaryStage); //Variável que recebe o arquivo contendo o AFD
        //Automata AFD = Operacoes.AutomataReader(AutomataFile);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Trabalho Final");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.getIcons().add(new Image("appicon.png"));
        Controller controller = loader.getController();
        primaryStage.show();
        controller.initialize(primaryStage);





        //Inicio das operações





    }


    public static void main(String[] args) {
        launch(args);
    }
}
