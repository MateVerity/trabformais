package org.trabalho.finais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    private static Stage pstage;

    @Override
    public void start(Stage primaryStage) throws Exception{  //Código de inicialização padrão do programa


        /*Basicamente, assim que o programa é inicializado, ele vai pedir o arquivo contendo o AFD. Esse arquivo passa pelo parser e é
        inserido no objeto Automata (definido na classe Automata). Utilizaremos um Singleton (que é basicamente uma variável global) tanto para
        o autômato quanto para a gramática. Assim que o autômato é lido, a gramática é criada. Assim que essas operações ocorrem, o programa aguarda
        o input do usuário, seja para testar alguma palavra ou alimentar o programa com uma lista de palavras.
         */


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Trabalho Final");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.getIcons().add(new Image("appicon.png"));
        Controller controller = loader.getController();
        primaryStage.show();
        pstage = primaryStage;


        /*As operações que necessitam do input do usuário estão na classe Controller*/


    }

    public static Stage getPrimaryStage()
    {
        return pstage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
