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
    private boolean playerXTurn = true; // Player X starts the game

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

    private void handleButtonClick(Button button, int row, int col) {
        if (button.getText().isEmpty() && playerXTurn) {
            button.setText("X");
            if (checkForWin()) {
                showAlert("X");
                resetGame();
            } else if (isBoardFull()) {
                showAlert("Draw");
                resetGame();
            } else {
                computerMove();
                if (checkForWin()) {
                    showAlert("O");
                    resetGame();
                } else if (isBoardFull()) {
                    showAlert("Draw");
                    resetGame();
                }
            }
        }
    }

    private void computerMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().isEmpty());
        buttons[row][col].setText("O");
        playerXTurn = true;
    }

    private boolean checkForWin() {
        String[][] board = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = buttons[row][col].getText();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true; // Check rows
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].isEmpty()) {
                return true; // Check columns
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true; // Check main diagonal
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true; // Check secondary diagonal
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if (winner.equals("Draw")) {
            alert.setContentText("It's a draw!");
        } else {
            alert.setContentText("Player " + winner + " wins!");
        }
        alert.showAndWait();
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        playerXTurn = true; // Player X starts again
    }

    public static void main(String[] args) {
        launch(args);
    }
}
