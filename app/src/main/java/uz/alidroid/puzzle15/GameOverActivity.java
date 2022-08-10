package uz.alidroid.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {



    private Button  buttonRestart,buttonExit;
    private TextView textInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        textInfo=findViewById(R.id.textResult);
        buttonRestart=findViewById(R.id.buttonRestart);
        buttonExit=findViewById(R.id.buttonExit);

        String player=getIntent().getStringExtra("player");
        int moves=getIntent().getIntExtra("moves",0);
        textInfo.setText(player+" You won the game with "+moves+" moves");

        buttonRestart.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });
        buttonExit.setOnClickListener(v -> {
            finish();
        });
    }
}