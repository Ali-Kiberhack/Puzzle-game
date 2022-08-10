package uz.alidroid.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.stream.Stream;

public class MenuActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

      preferences =getSharedPreferences( "app_settings",MODE_PRIVATE);
       editor =preferences.edit();

        Button instruction=findViewById(R.id.instruction);
        Button button= findViewById(R.id.btnPlay);
        editText =findViewById(R.id.editPlayer);
        String lastPlayer= preferences.getString("player","");
        editText.setText(lastPlayer);
        button.setOnClickListener(v -> {
          if (!editText.getText().toString().isEmpty()){
              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
              Constants.player=editText.getText().toString();
              startActivity(intent);
              finish();
          }
        });

        instruction.setOnClickListener(v -> {

            Intent intent= new Intent(getApplicationContext(),Instruction.class);
            startActivity(intent);
           //finish();
        });




    }

    @Override
    protected void onStop() {

        if (editText.getText().toString().length()>0) {
            editor.putString("player", editText.getText().toString());
            editor.commit();
        }
        super.onStop();
    }
}