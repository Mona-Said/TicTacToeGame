/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class TicTacToeGUI extends Application {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true; 

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

       for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        Button button = new Button();
        button.setMinSize(100, 100);
        int currentRow = row;
        int currentCol = col;
        button.setOnAction(event -> handleButtonClick(button, currentRow, currentCol));
        buttons[row][col] = button;
        gridPane.add(button, col, row);
    }
}

        Scene scene = new Scene(gridPane, 320, 320);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
