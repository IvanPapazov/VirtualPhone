package com.os0.navigation;

import android.content.Intent;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class calc_MainActivity extends AppCompatActivity {

    private Button percent;
    private Button delete;
    private Button reverse;
    private Button power;
    private Button sq;
    private Button devision;
    private Button times;
    private Button minus;
    private Button plus;
    private Button negative;
    private Button comma;
    private Button equal;

    private Button press9;
    private Button press8;
    private Button press7;
    private Button press6;
    private Button press5;
    private Button press4;
    private Button press3;
    private Button press2;
    private Button press1;
    private Button press0;

    private Double number=0.0;
    private String s="";
    private String LA="";

    private TextView window;
    private TextView miniwindow;
    private TextView symbol;

    private boolean fcomma=false;
    private boolean fplus=false;
    private boolean fminus=false;
    private boolean ftimes=false;
    private boolean fdev=false;
    private boolean fdisable=false;
    private boolean fngtv=false;
    private boolean fpr=false;
    private boolean frev=false;
    private boolean fpwr=false;
    private boolean fsq=false;
    private boolean feq=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.calc_activity_main);
        percent=findViewById(R.id.btnPR);
        Button c = findViewById(R.id.btnC);
        Button ce = findViewById(R.id.btnCE);
        delete=findViewById(R.id.btnDl);
        reverse=findViewById(R.id.btnREV);
        power=findViewById(R.id.btnPower);
        sq=findViewById(R.id.btnSQ);
        devision=findViewById(R.id.btnDev);
        delete=findViewById(R.id.btnDl);
        times=findViewById(R.id.btnTimes);
        minus=findViewById(R.id.btnMinus);
        plus=findViewById(R.id.btnPlus);
        negative=findViewById(R.id.btnNegative);
        comma=findViewById(R.id.btnComma);
        equal=findViewById(R.id.btnEquals);
        Button goBack = findViewById(R.id.btnCalcToMain);


        press9=findViewById(R.id.btn9);
        press8=findViewById(R.id.btn8);
        press7=findViewById(R.id.btn7);
        press6=findViewById(R.id.btn6);
        press5=findViewById(R.id.btn5);
        press4=findViewById(R.id.btn4);
        press3=findViewById(R.id.btn3);
        press2=findViewById(R.id.btn2);
        press1=findViewById(R.id.btn1);
        press0=findViewById(R.id.btn0);


        window=findViewById(R.id.windowCalc);
        window.setMovementMethod(new ScrollingMovementMethod());
        miniwindow=findViewById(R.id.txtMD);
        symbol=findViewById(R.id.txtSymbol);


        goBack.setOnClickListener(view -> finish());



        press9.setOnClickListener(view -> got9());

        press8.setOnClickListener(view -> got8());

        press7.setOnClickListener(view -> got7());

        press6.setOnClickListener(view -> got6());

        press5.setOnClickListener(view -> got5());

        press4.setOnClickListener(view -> got4());

        press3.setOnClickListener(view -> got3());

        press2.setOnClickListener(view -> got2());

        press1.setOnClickListener(view -> got1());

        press0.setOnClickListener(view -> got0());



        c.setOnClickListener(view -> clean());

        ce.setOnClickListener(view -> clearEntryAction());

        delete.setOnClickListener(view -> del());

        comma.setOnClickListener(view -> coma());

        plus.setOnClickListener(view -> sum());

        minus.setOnClickListener(view -> mn());

        times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tms();
            }
        });

        devision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dv();
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eq();
            }
        });

        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngtv();
            }
        });

        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pr();
            }
        });

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwr();
            }
        });

        sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square();
            }
        });

    }

    private boolean znak(String s){
        String s1=symbol.getText().toString();
        return s1.equals(s);
    }

    private void sum(){
        fcomma=false;
        ftimes=false;
        fminus=false;
        fdev=false;
        fngtv=false;
        fpr=false;
        frev=false;
        fpwr=false;
        fsq=false;
        feq=false;

        if(!fplus) {
            lastAction();
            symbol.setText("+");
            fplus = true;
            if(!s.equals("")) readLastAction();
            s="";
            miniwindow.setText(String.format("%.2f", number));
        }

        if(znak("+")) {
            if(!s.equals("")) {
                number += Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fplus=false;
            s="";
        }

        else if(znak("-")) {
            if(!s.equals("")) {
                number-=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fplus=false;
            s="";
        }

        else if(znak("*")) {
            if(!s.equals("")) {
                number*=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fplus=false;
            s="";
        }

        else if(znak("/")) {
            if(!s.equals("")) {
                if(Double.parseDouble(s)==0) devByZero();
                else {
                    number /= Double.parseDouble(s);
                    miniwindow.setText(String.format("%.2f", number));
                }
            }
            fplus=false;
            s="";
        }


    }

    private void mn(){
        fcomma=false;
        ftimes=false;
        fplus=false;
        fdev=false;
        fngtv=false;
        fpr=false;
        frev=false;
        fpwr=false;
        fsq=false;
        feq=false;

        if(!fminus) {
            lastAction();
            symbol.setText("-");
            fminus = true;
            if(!s.equals("")) readLastAction();
            miniwindow.setText(String.format("%.2f", number));
            s="";

        }

        if(znak("+")) {
            if(!s.equals("")) {
                number += Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fminus=false;
            s="";
        }

        else if(znak("-")) {
            if(!s.equals("")) {
                number-=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fminus=false;
            s="";
        }

        else if(znak("*")) {
            if(!s.equals("")) {
                number*=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fminus=false;
            s="";
        }

        else if(znak("/")) {
            if(!s.equals("")) {
                if(Double.parseDouble(s)==0) devByZero();
                else {
                    number /= Double.parseDouble(s);
                    miniwindow.setText(String.format("%.2f", number));
                }
            }
            fminus=false;
            s="";
        }


    }

    private void tms(){
        fcomma=false;
        fplus=false;
        fminus=false;
        fdev=false;
        fngtv=false;
        fpr=false;
        frev=false;
        fpwr=false;
        fsq=false;
        feq=false;


        if(!ftimes) {
            lastAction();
            symbol.setText("*");
            ftimes = true;
            if(!s.equals("")) readLastAction();
            s="";
            miniwindow.setText(String.format("%.2f", number));
        }

        if(znak("+")) {
            if(!s.equals("")) {
                number += Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            ftimes=false;
            s="";
        }

        else if(znak("-")) {
            if(!s.equals("")) {
                number-=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            ftimes=false;
            s="";
        }

        else if(znak("*")) {
            if(!s.equals("")) {
                number*=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            ftimes=false;
            s="";
        }

        else if(znak("/")) {
            if(!s.equals("")) {
                if(Double.parseDouble(s)==0) devByZero();
                else {
                    number /= Double.parseDouble(s);
                    miniwindow.setText(String.format("%.2f", number));
                }
            }
            ftimes=false;;
            s="";
        }


    }

    private void dv(){
        fcomma=false;
        fplus=false;
        fminus=false;
        ftimes=false;
        fngtv=false;
        fpr=false;
        frev=false;
        fpwr=false;
        fsq=false;
        feq=false;

        if(!fdev) {
            lastAction();
            symbol.setText("/");
            ftimes = true;
            if(!s.equals("")) {
                if(Double.parseDouble(s)!=0) readLastAction();
                else devByZero();
            }
            s="";
            miniwindow.setText(String.format("%.2f", number));
        }

        if(znak("+")) {
            if(!s.equals("")) {
                number += Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fdev=false;
            s="";
        }

        else if(znak("-")) {
            if(!s.equals("")) {
                number-=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fdev=false;
            s="";
        }

        else if(znak("*")) {
            if(!s.equals("")) {
                number*=Double.parseDouble(s);
                miniwindow.setText(String.format("%.2f", number));
            }
            fdev=false;
            s="";
        }

        else if(znak("/")) {
            if(!s.equals("")) {
                if(Double.parseDouble(s)==0) devByZero();
                else {
                    number /= Double.parseDouble(s);
                    miniwindow.setText(String.format("%.2f", number));
                }
            }
            fdev=false;
            s="";
        }
    }

    private void coma(){
        if(!fcomma) {
            fcomma = true;
            s += ".";
            window.setText(s);
        }

    }

    private void clean(){
        symbol.setText("");
        number=0.0;
        s="";
        window.setText("0");
        miniwindow.setText(("0.00"));
        if(fdisable) {
            fdisable=false;
            percent.setEnabled(true);
            delete.setEnabled(true);
            reverse.setEnabled(true);
            power.setEnabled(true);
            sq.setEnabled(true);
            devision.setEnabled(true);
            delete.setEnabled(true);
            times.setEnabled(true);
            minus.setEnabled(true);
            plus.setEnabled(true);
            negative.setEnabled(true);
            comma.setEnabled(true);
            equal.setEnabled(true);
            press9.setEnabled(true);
            press8.setEnabled(true);
            press7.setEnabled(true);
            press6.setEnabled(true);
            press5.setEnabled(true);
            press4.setEnabled(true);
            press3.setEnabled(true);
            press2.setEnabled(true);
            press1.setEnabled(true);
            press0.setEnabled(true);
            fngtv=false;
            fpr=false;
            fdev=false;
            fminus=false;
            fplus=false;
            fcomma=false;
            ftimes=false;
            frev=false;
            fpwr=false;
            fsq=false;
            feq=false;
        }
    }

    private void clearEntryAction(){
        if(fdisable){
            clean();
        }
        else {
            s = "";
            window.setText("0");
        }
    }

    private void del(){
        if(s.length()<=1) clean();
        else {
            s = s.substring(0, s.length() - 1);
            window.setText(s);
        }
    }

    private void rv(){
        fngtv=false;
        fpr=false;
        fpwr=false;
        fsq=false;
        feq=false;

        if(!s.equals("")) {
            s=String.valueOf(1/Double.parseDouble(s));
            window.setText(s);
        }
        else if(number!=0.0) {
            number=1/number;
            miniwindow.setText(String.format("%.2f", number));
            window.setText(String.format("%.2f", number));
        }
        else devByZero();
        frev=true;
    }

    private void pr(){
        fngtv=false;
        frev=false;
        fpwr=false;
        fsq=false;
        feq=false;

        if(!znak("")) {
            if(!s.equals("")) {
                s=String.valueOf(Double.parseDouble(s)/100);
                window.setText(s);
            }
            /*else {
                s=window.getText().toString();
                s=String.valueOf(Double.parseDouble(s)/100);
                window.setText(s);
            }*/
        }
        else number=number/100;
        miniwindow.setText(String.format("%.2f", number));
        window.setText(String.format("%.2f", number));
        fpr=true;
    }

    private void ngtv(){
        frev=false;
        fpwr=false;
        fsq=false;
        fpr=false;
        feq=false;

        if(!znak("")) {
            if(!s.equals("")) {
                if (s.charAt(0) == '-') s = s.substring(1);
                else s="-"+s;
                window.setText(s);
            }
            else {
                s=window.getText().toString();
                if (s.charAt(0) == '-') s = s.substring(1);
                else s="-"+s;
                window.setText(s);
            }
        }
        else number=-number;
        miniwindow.setText(String.format("%.2f", number));
        window.setText(String.format("%.2f", number));
        fngtv=true;
    }

    private void pwr(){
        frev=false;
        fngtv=false;
        fsq=false;
        fpr=false;
        feq=false;

        if(!znak("")) {
            if(!s.equals("")) {
                s=String.valueOf(Math.pow((Double.parseDouble(s)),2));
                window.setText(s);
            }
            /*else {
                s=window.getText().toString();
                s=String.valueOf(Math.pow((Double.parseDouble(s)),2));
                window.setText(s);
            }*/
        }
        else number=Math.pow(number,2);
        miniwindow.setText(String.format("%.2f", number));
        window.setText(String.format("%.2f", number));
        fpwr=true;
    }

    private void square(){
        frev=false;
        fngtv=false;
        fpr=false;
        fpwr=false;
        feq=false;

        if(!znak("")) {
            if(!s.equals("")) {
                s=String.valueOf(Math.sqrt(Double.parseDouble(s)));
                window.setText(s);
            }
            /*else {
                s=window.getText().toString();
                s=String.valueOf(Math.pow((Double.parseDouble(s)),2));
                window.setText(s);
            }*/
        }
        else number=Math.sqrt(number);
        miniwindow.setText(String.format("%.2f", number));
        window.setText(String.format("%.2f", number));
        fsq=true;
    }

    private void devByZero(){
        window.setText("ERROR: CAN'T DEVIDE BY 0");
        miniwindow.setText("PLEASE PRESS C OR CE");
        fdisable=true;
        percent.setEnabled(false);
        delete.setEnabled(false);
        reverse.setEnabled(false);
        power.setEnabled(false);
        sq.setEnabled(false);
        devision.setEnabled(false);
        delete.setEnabled(false);
        times.setEnabled(false);
        minus.setEnabled(false);
        plus.setEnabled(false);
        negative.setEnabled(false);
        comma.setEnabled(false);
        equal.setEnabled(false);
        press9.setEnabled(false);
        press8.setEnabled(false);
        press7.setEnabled(false);
        press6.setEnabled(false);
        press5.setEnabled(false);
        press4.setEnabled(false);
        press3.setEnabled(false);
        press2.setEnabled(false);
        press1.setEnabled(false);
        press0.setEnabled(false);
    }

    private void lastAction(){
        if(!znak("")) LA=symbol.getText().toString();
        else LA="+";

        if(!s.equals("")){
            if(Double.parseDouble(s)!=0) LA+=s;
            else LA+="0";
        }
        else LA+="0";
        System.out.println(LA);
    }

    private void readLastAction(){

        if(fsq) square();

        else if(fpwr) pwr();

        else if(frev) rv();

        else if(fpr) pr();

        else if(fngtv) ngtv();

        else if(LA.charAt(0)=='+') {
            number += Double.parseDouble(LA.substring(1));
            miniwindow.setText(String.valueOf(number));
            window.setText(String.valueOf(number));
            s="";
        }

        else if(LA.charAt(0)=='-') {
            number-=Double.parseDouble(LA.substring(1));
            miniwindow.setText(String.format("%.2f", number));
            window.setText(String.format("%.2f", number));
            s="";
        }

        else if(LA.charAt(0)=='*') {
            number*=Double.parseDouble(LA.substring(1));
            miniwindow.setText(String.format("%.2f", number));
            window.setText(String.format("%.2f", number));
            s="";
        }

        else if(LA.charAt(0)=='/') {
            number/=Double.parseDouble(LA.substring(1));
            miniwindow.setText(String.format("%.2f", number));
            window.setText(String.format("%.2f", number));
            s="";
        }
    }

    private void eq(){
        System.out.println(number+" "+symbol.getText()+" "+s);
        if(!feq) {
            lastAction();
            readLastAction();
        }
        else {
            if(znak("")) {
                if(!s.equals("")){
                    number=Double.parseDouble(s);
                } else number=number;
            }
            else readLastAction();
        }
        feq=true;
        if(number==0.0 && !s.equals("")) {
            number=Double.parseDouble(s);
            window.setText(String.format("%.2f", number));
        }
        window.setText(String.format("%.2f", number));
        miniwindow.setText(String.format("%.2f", number));
        symbol.setText("");
        System.out.println(number+" "+s);
        s="";
    }

    private void got9(){
        s+="9";
        window.setText(s);

    }

    private void got8(){
        s+="8";
        window.setText(s);

    }

    private void got7(){
        s+="7";
        window.setText(s);

    }

    private void got6(){
        s+="6";
        window.setText(s);

    }

    private void got5(){
        s+="5";
        window.setText(s);

    }

    private void got4(){
        s+="4";
        window.setText(s);

    }

    private void got3(){
        s+="3";
        window.setText(s);

    }

    private void got2(){
        s+="2";
        window.setText(s);

    }

    private void got1(){
        s+="1";
        window.setText(s);

    }

    private void got0(){
        s+="0";
        window.setText(s);

    }

    private void previous(){
        Intent intent=new Intent(this, calc_MainActivity.class);
        startActivity(intent);
    }





}