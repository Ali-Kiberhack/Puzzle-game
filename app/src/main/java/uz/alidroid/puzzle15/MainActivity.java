package uz.alidroid.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Integer> numbers = new ArrayList<>();
    private GridLayout gridContainer;

    private int x = 3;
    private int y = 3;

    private  Button emptyButton, pauseButton,resumeButton;
    private TextView textPlayer,textMoves;
    private ImageView iconShuffle;
    private Chronometer chronometer;
    private LinearLayout pauseOverlay;

    private  long timeWhenPaused=0;
    private boolean isGameNotLaunched=true;

    int moves=0;

    private  long onBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNumbers();
        initializeGame();
        generateNumbers();

         ImageView home=findViewById(R.id.home);

        home.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(intent);
            finish();

        });

        iconShuffle.setOnClickListener(v -> {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            isGameNotLaunched=true;
            restartUi();
            moves=0;
            updateMovementUi();
            generateNumbers();

        });

        pauseButton.setOnClickListener(v -> {
            timeWhenPaused=chronometer.getBase()- SystemClock.elapsedRealtime();
            chronometer.stop();
            pauseOverlay.setFocusable(true);
            pauseOverlay.setClickable(true);
           pauseOverlay.setVisibility(View.VISIBLE);
           pauseButton.setVisibility(View.INVISIBLE);
        });

        resumeButton.setOnClickListener(v -> {
            chronometer.setBase(SystemClock.elapsedRealtime()+timeWhenPaused);
           chronometer.start();
            pauseOverlay.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);

        });

    }

    private void restartUi(){

        for (int i = 0; i < gridContainer.getChildCount(); i++) {
            gridContainer.getChildAt(i).setVisibility(View.VISIBLE);

        }
    }

       private  void initializeGame() {
           gridContainer = findViewById(R.id.grid_container);
           pauseOverlay=findViewById(R.id.pauseOverlay);
           textPlayer=findViewById(R.id.textPlayer);
           textMoves=findViewById(R.id.textMoves);
           iconShuffle=findViewById(R.id.iconShuflle);
           chronometer=findViewById(R.id.chronometer);
           pauseButton=findViewById(R.id.btnPause);
           resumeButton=findViewById(R.id.btnResume);
           textPlayer.setText(Constants.player);
           updateMovementUi();
       }

       private void loadNumbers(){
           for (int i = 1; i <= 16; i++) {
               numbers.add(i);
           }
       }

       private void generateNumbers() {
         do {
          Collections.shuffle(numbers);
       } while (!isSolvable(numbers));

           for (int i = 0; i < gridContainer.getChildCount() ; i++) {
              if (numbers.get(i)==16){
                  String tag =gridContainer.getChildAt(i).getTag().toString();
                  x=tag.charAt(0)-'0';
                  y=tag.charAt(1)-'0';
                  emptyButton=(Button) gridContainer.getChildAt(i);
                  emptyButton.setVisibility(View.INVISIBLE);

              }
              else{
                  ((Button) gridContainer.getChildAt(i)).setText(String.valueOf(numbers.get(i)));
              }
           }
       }
       private boolean isSolvable(List<Integer> numbers){
        int counter=0;
           for (int i = 0; i < numbers.size(); i++) {
               if (numbers.get(i)==16){
                   counter += i / 4 + 1 ;
                   continue;
               }
               for (int j = i+1; j <numbers.size() ; j++) {
                   if (numbers.get(i)>numbers.get(j)){
                       counter++;
                   }
               }
           }
           return counter % 2 == 0;
       }

    public void click(View view) {
        if (isGameNotLaunched){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isGameNotLaunched=false;
        }
        Button clicked=(Button) view;
        String tag = view.getTag().toString();
        int clickedX =  tag.charAt(0)-'0';
        int clickedY = tag.charAt(1)-'0';
        if(canMove(clickedX,clickedY)){
            moves++;
            updateMovementUi();
             swap(clicked,clickedX,clickedY);
            if (isGameOver()) {
                Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                intent.putExtra("player",Constants.player);
                intent.putExtra("moves",moves);
                startActivity(intent);
                finish();
            }
        }
    }

    private void swap(Button clicked,int clickedX,int clickedY){
        String text = clicked.getText().toString();
        clicked.setText("");
        clicked.setVisibility(View.INVISIBLE);
        emptyButton.setText(text);
        emptyButton.setVisibility(View.VISIBLE);
        emptyButton=clicked;
        x=clickedX;
        y=clickedY;
    }

    private boolean canMove(int clickedX,int clickedY){
       return Math.abs(clickedX+clickedY-(x+y))==1 &&  Math.abs(clickedX-x)!=2 && Math.abs(clickedY-y)!=2;
    }

    private void updateMovementUi(){

        textMoves.setText(String.valueOf(moves));
    }

       private boolean isGameOver(){
           int counter = 1;
           for (int i =0;i < 15 ; i++){
               Button checkerButton=(Button) gridContainer.getChildAt(i);
               String text=checkerButton.getText().toString();
               if(text.isEmpty()) return false;
               if(Integer.parseInt(checkerButton.getText().toString())==counter)
               counter++;
           }
          return counter == 16;

       }


    @Override
    public void onBackPressed() {
        if(onBackPressedTime + 2000 > System.currentTimeMillis()){
        super.onBackPressed();
        }
        else {
            Toast.makeText(getApplicationContext(),"Please press back again to exit!",Toast.LENGTH_SHORT).show();
            onBackPressedTime = System.currentTimeMillis();
        }
    }
}

