package com.example.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean playerX = true; // true = X, false = O
    private int[] board = new int[9]; // 0 = empty, 1 = X, 2 = O
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        status = findViewById(R.id.status);
        GridLayout gridLayout = findViewById(R.id.gridLayout);


        for (int i = 0; i < 9; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            board[i] = 0;

            int finalI = i;
            buttons[i].setOnClickListener(view -> makeMove(finalI));
        }
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(view -> resetGame());
    }
    private void makeMove(int index) {
        if (board[index] != 0) return; // Ignore already played buttons

        board[index] = playerX ? 1 : 2;
        buttons[index].setText(playerX ? "X" : "O");

        if (checkWin()) {
            status.setText((playerX ? "Player X" : "Player O") + " Wins!");
            disableButtons();
            Toast.makeText(MainActivity.this, "Congratulations Player ..."+(playerX? "X":"O"),Toast.LENGTH_SHORT).show();
            return;
        }
        boolean draw = true;
        for (int value : board) {
            if (value == 0) draw = false;
        }
        if (draw) {
            status.setText("It's a Draw!");
            return;
        }

        playerX = !playerX;
        status.setText(playerX ? "Player X's Turn" : "Player O's Turn");
    }

    private boolean checkWin() {
        int[][] winPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] pos : winPositions) {
            if (board[pos[0]] != 0 && board[pos[0]] == board[pos[1]] && board[pos[1]] == board[pos[2]]) {
                return true;
            }
        }
        return false;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = 0;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        playerX = true;
        status.setText("Player X's Turn");

    }
}