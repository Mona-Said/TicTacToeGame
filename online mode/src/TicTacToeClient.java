/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TicTacToeClient extends Application {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Button[][] buttons = new Button[3][3];
    private char currentPlayer = 'X';

    @Override
    public void start(Stage primaryStage) {
        try {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            showAlert("Error", "Connection error", "Failed to connect to the server.");
            Platform.exit();
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button("");
                button.setMinSize(50, 50);
                button.setOnAction(e -> {
                    int row = GridPane.getRowIndex(button);
                    int col = GridPane.getColumnIndex(button);
                    makeMove(row, col);
                });
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        Scene scene = new Scene(gridPane, 200, 200);
        primaryStage.setTitle("Tic-Tac-Toe Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(this::listenForServerMessages).start();
    }

    private void makeMove(int row, int col) {
        if (!buttons[row][col].getText().isEmpty()) {
            return; // Cell already occupied
        }

        buttons[row][col].setText(String.valueOf(currentPlayer));
        out.println(row + "," + col);

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player
    }

    private void listenForServerMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + message);
                // Handle messages from the server (e.g., game state updates)
            }
        } catch (IOException e) {
            System.err.println("Error reading from server: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}