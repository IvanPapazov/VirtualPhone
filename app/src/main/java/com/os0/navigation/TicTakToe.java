package com.os0.navigation;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTakToe extends AppCompatActivity {

    Button TopR;
    Button TopM;
    Button TopL;
    Button MidR;
    Button MidM;
    Button MidL;
    Button BotR;
    Button BotM;
    Button BotL;
    Button Restart;
    TextView txt;

    int counter = 1;

    int x1=0,x2=0,x3=0,x4=0,x5=0,x6=0,x7=0,x8=0,x9=0,o1=0,o2=0,o3=0,o4=0,o5=0,o6=0,o7=0,o8=0,o9=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tak_toe);

        TopR = (Button) findViewById(R.id.TR);
        TopM = (Button) findViewById(R.id.TM);
        TopL = (Button) findViewById(R.id.TL);
        MidR = (Button) findViewById(R.id.MR);
        MidM = (Button) findViewById(R.id.MM);
        MidL = (Button) findViewById(R.id.ML);
        BotR = (Button) findViewById(R.id.BR);
        BotM = (Button) findViewById(R.id.BM);
        BotL = (Button) findViewById(R.id.BL);
        Restart = (Button) findViewById(R.id.restart);
        txt = findViewById(R.id.wintxt);

        TopL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    TopL.setText("X");
                    TopL.setTextColor(Color.RED);
                    counter++;
                    TopL.setClickable(false);
                    x1=1;
                    winnerx();
                } else {
                    TopL.setText("O");
                    TopL.setTextColor(Color.BLUE);
                    counter++;
                    TopL.setClickable(false);
                    o1=1;
                    winnero();
                }
            }
        });

        TopM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    TopM.setText("X");
                    TopM.setTextColor(Color.RED);
                    counter++;
                    TopM.setClickable(false);
                    x2=1;
                    winnerx();
                } else {
                    TopM.setText("O");
                    TopM.setTextColor(Color.BLUE);
                    counter++;
                    TopM.setClickable(false);
                    o2=1;
                    winnero();
                }
            }
        });

        TopR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    TopR.setText("X");
                    TopR.setTextColor(Color.RED);
                    counter++;
                    TopR.setClickable(false);
                    x3=1;
                    winnerx();
                } else {
                    TopR.setText("O");
                    TopR.setTextColor(Color.BLUE);
                    counter++;
                    TopR.setClickable(false);
                    o3=1;
                    winnero();
                }
            }
        });

        MidL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    MidL.setText("X");
                    MidL.setTextColor(Color.RED);
                    counter++;
                    MidL.setClickable(false);
                    x4=1;
                    winnerx();
                } else {
                    MidL.setText("O");
                    MidL.setTextColor(Color.BLUE);
                    counter++;
                    MidL.setClickable(false);
                    o4=1;
                    winnero();
                }
            }
        });

        MidM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    MidM.setText("X");
                    MidM.setTextColor(Color.RED);
                    counter++;
                    MidM.setClickable(false);
                    x5=1;
                    winnerx();
                } else {
                    MidM.setText("O");
                    MidM.setTextColor(Color.BLUE);
                    counter++;
                    MidM.setClickable(false);
                    o5=1;
                    winnero();
                }
            }
        });

        MidR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    MidR.setText("X");
                    MidR.setTextColor(Color.RED);
                    counter++;
                    MidR.setClickable(false);
                    x6=1;
                    winnerx();
                } else {
                    MidR.setText("O");
                    MidR.setTextColor(Color.BLUE);
                    counter++;
                    MidR.setClickable(false);
                    o6=1;
                    winnero();
                }
            }
        });

        BotL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    BotL.setText("X");
                    BotL.setTextColor(Color.RED);
                    counter++;
                    BotL.setClickable(false);
                    x7=1;
                    winnerx();
                } else {
                    BotL.setText("O");
                    BotL.setTextColor(Color.BLUE);
                    counter++;
                    BotL.setClickable(false);
                    o7=1;
                    winnero();
                }
            }
        });

        BotM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    BotM.setText("X");
                    BotM.setTextColor(Color.RED);
                    counter++;
                    BotM.setClickable(false);
                    x8=1;
                    winnerx();
                } else {
                    BotM.setText("O");
                    BotM.setTextColor(Color.BLUE);
                    counter++;
                    BotM.setClickable(false);
                    o8=1;
                    winnero();
                }
            }
        });

        BotR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter % 2 == 0) {
                    BotR.setText("X");
                    BotR.setTextColor(Color.RED);
                    counter++;
                    BotR.setClickable(false);
                    x9=1;
                    winnerx();
                } else {
                    BotR.setText("O");
                    BotR.setTextColor(Color.BLUE);
                    counter++;
                    BotR.setClickable(false);
                    o9=1;
                    winnero();
                }
            }
        });

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TicTakToe.this,TicTakToe.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void winnerx(){
        if(x1==1 && x2==1 && x3==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x4==1 && x5==1 && x6==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x7==1 && x8==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x1==1 && x4==1 && x7==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x2==1 && x5==1 && x8==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x3==1 && x6==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x1==1 && x5==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(x3==1 && x5==1 && x7==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if((x1==1||o1==1)&&(x2==1||o2==1)&&(x3==1||o3==1)&&(x4==1||o4==1)&&(x5==1||o5==1)&&(x6==1||o6==1)&&(x7==1||o7==1)&&(x8==1||o8==1)&&(x9==1||o9==1)){
        if((x1==1||o1==1)&&(x2==1||o2==1)&&(x3==1||o3==1)&&(x4==1||o4==1)&&(x5==1||o5==1)&&(x6==1||o6==1)&&(x7==1||o7==1)&&(x8==1||o8==1)&&(x9==1||o9==1)){
            if(x1==1 && x2==1 && x3==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x4==1 && x5==1 && x6==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x7==1 && x8==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x1==1 && x4==1 && x7==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x2==1 && x5==1 && x8==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x3==1 && x6==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x1==1 && x5==1 && x9==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(x3==1 && x5==1 && x7==1){txt.setText("X win"); txt.setTextColor(Color.RED);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);}
            else{txt.setText("Equal"); txt.setTextColor(Color.GREEN);}
        }
        }
    }

    public void winnero(){
        if(o1==1 && o2==1 && o3==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o4==1 && o5==1 && o6==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o7==1 && o8==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o1==1 && o4==1 && o7==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o2==1 && o5==1 && o8==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o3==1 && o6==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o1==1 && o5==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if(o3==1 && o5==1 && o7==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
        if((x1==1||o1==1)&&(x2==1||o2==1)&&(x3==1||o3==1)&&(x4==1||o4==1)&&(x5==1||o5==1)&&(x6==1||o6==1)&&(x7==1||o7==1)&&(x8==1||o8==1)&&(x9==1||o9==1)){
        if((x1==1||o1==1)&&(x2==1||o2==1)&&(x3==1||o3==1)&&(x4==1||o4==1)&&(x5==1||o5==1)&&(x6==1||o6==1)&&(x7==1||o7==1)&&(x8==1||o8==1)&&(x9==1||o9==1)){
            if(o1==1 && o2==1 && o3==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o4==1 && o5==1 && o6==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o7==1 && o8==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o1==1 && o4==1 && o7==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o2==1 && o5==1 && o8==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o3==1 && o6==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o1==1 && o5==1 && o9==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);};
            if(o3==1 && o5==1 && o7==1){txt.setText("O win"); txt.setTextColor(Color.BLUE);TopL.setClickable(false);TopM.setClickable(false);TopR.setClickable(false);MidL.setClickable(false);MidM.setClickable(false);MidR.setClickable(false);BotL.setClickable(false);BotM.setClickable(false);BotR.setClickable(false);}
            else{txt.setText("Equal"); txt.setTextColor(Color.GREEN);}
        }
        }
    }
}
