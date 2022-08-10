package uz.alidroid.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        Button orqaga=findViewById(R.id.orqaga);

        orqaga.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(intent);
            // finish();

        });

    }
}