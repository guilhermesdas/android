package com.example.calculadora;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Create buttons and edit text
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonComma;
    Button buttonEquals;
    Button buttonDiv;
    Button buttonErase;
    Button buttonPlus;
    Button buttonMinus;
    Button buttonMult;
    Button buttonPlusMinus;
    Button buttonEraseAll;
    EditText numbers;
    TextView txtExpressions;

    // Extra buttons
    Button btn10x;
    Button btnlog;
    Button btnFat;
    Button btnxey;
    Button btnLn;
    Button btnx2;
    Button btnyx;
    Button btnInv;
    Button btnMod;
    Button btnSqrt;
    Button btnPi;
    Button btnPerc;
    Button btne;
    Button btnExp;
    Button btnAbs;

    // Operações
    enum Operators {
        DIVISION("\u00F7"),
        PLUS("\u002B"),
        MULT("\u00D7"),
        ROOT(" yroot "),
        PERCENT("%"),
        POW("^"),
        MOD(" mod "),
        SQRT("\u221A"),
        EXP(".E"),
        MINUS("\u2212");

        private String character;

        Operators(String s) {
            this.character = s;
        }
    }

    // Expression
    String operator = "";
    double num1 = 0;
    double num2 = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associate buttons withs their corresponding views
        button0 = (Button) findViewById(R.id.btn0);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        button4 = (Button) findViewById(R.id.btn4);
        button5 = (Button) findViewById(R.id.btn5);
        button6 = (Button) findViewById(R.id.btn6);
        button7 = (Button) findViewById(R.id.btn7);
        button8 = (Button) findViewById(R.id.btn8);
        button9 = (Button) findViewById(R.id.btn9);
        buttonPlusMinus = findViewById(R.id.btnPlusMinus);
        buttonDiv = findViewById(R.id.btnDiv);
        buttonErase = findViewById(R.id.btnErase);
        buttonPlus = findViewById(R.id.btnPlus);
        buttonMinus = findViewById(R.id.btnMinus);
        buttonMult = findViewById(R.id.btnMult);
        buttonComma = findViewById(R.id.btnComma);
        buttonEquals = findViewById(R.id.btnEquals);
        buttonEraseAll = findViewById(R.id.btnEraseAll);
        numbers = (EditText) findViewById(R.id.editTextNumberDecimal);
        numbers.setShowSoftInputOnFocus(false);
        txtExpressions = findViewById(R.id.txtResult);

        // Extra buttons
        btn10x = findViewById(R.id.bnt10ex);
        btnlog = findViewById(R.id.btnLog);
        btnFat = findViewById(R.id.btnFat);
        btnxey = findViewById(R.id.btnxey);
        btnLn = findViewById(R.id.btnLn);
        btnx2 = findViewById(R.id.btnx2);
        btnyx = findViewById(R.id.btnyx);
        btnInv = findViewById(R.id.btnInv);
        btnMod = findViewById(R.id.btnMod);
        btnAbs = findViewById(R.id.btnAbs);
        btnSqrt = findViewById(R.id.btnSqrt);
        btnPi = findViewById(R.id.btnPi);
        btnPerc = findViewById(R.id.btnPercent);
        btne = findViewById(R.id.btne);
        btnExp = findViewById(R.id.btnExp);

        // Add onClik method to all buttons numbers
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonErase.setOnClickListener(this);
        buttonMult.setOnClickListener(this);
        buttonComma.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonPlusMinus.setOnClickListener(this);
        buttonEraseAll.setOnClickListener(this);
        btn10x.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        btnFat.setOnClickListener(this);
        btnxey.setOnClickListener(this);
        btnLn.setOnClickListener(this);
        btnx2.setOnClickListener(this);
        btnyx.setOnClickListener(this);
        btnInv.setOnClickListener(this);
        btnMod.setOnClickListener(this);
        btnAbs.setOnClickListener(this);
        btnSqrt.setOnClickListener(this);
        btnPi.setOnClickListener(this);
        btnPerc.setOnClickListener(this);
        btne.setOnClickListener(this);
        btnExp.setOnClickListener(this);

        // Long click in erase, clear all editText
        buttonErase.setLongClickable(true);
        buttonErase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                numbers.getText().clear();
                numbers.setSelection(0);
                return true;
            }
        });
    }

    double calcular(double num1, double num2, String operator){

        double resultado = 0;

        if ( operator.equals(Operators.PLUS.character) ){
            resultado = (num1 + num2);
        }
        else if ( operator.equals(Operators.MINUS.character) ){
            resultado =  num1 - num2;
        }
        else if ( operator.equals(Operators.MULT.character) ){
            resultado =  num1 * num2;
        }
        else if ( operator.equals(Operators.DIVISION.character) ) {
            resultado =  num1 / num2;
        }
        else if ( operator.equals(Operators.ROOT.character) )  {
            resultado = Math.pow(num1,1/num2);
        }
        else if ( operator.equals(Operators.POW.character) ){
            resultado =  Math.pow(num1,num2);
        }
        else if ( operator.equals(Operators.PERCENT.character) ){
            resultado =  num1 * num2 / 100.0f;
        }
        else if ( operator.equals(Operators.MOD.character)   ){
            resultado =  num1 % num2;
        }
        else if ( operator.equals(Operators.EXP.character)   ){

            resultado =  num1 * Math.pow(10,num2);
        }
        return round(resultado);
    }

    private static double round(double value) {

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(10, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

    String doubleToString(double n){
        if ( n % 1 == 0 ){
            return Long.toString((long)n);
        } else {
            return Double.toString(n);
        }
    }

    long fatorial(int num1){

        if ( num1 < 0 ){
            throw new IllegalArgumentException("Fatorial de número negativo");
        } else if ( num1 == 0 || num1 == 1 ){
            return 1;
        } else {
            long r = 1;
            for ( int  i = 2; i <= num1; i++ ){
                r = r * i;
            }
            return r;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
     public void onClick(View v) {
        // default method for handling onClick Events..
        // Is always a button
        Button btn = (Button) v;
        numbers.setError(null);

        // get selected text
        int start = numbers.getSelectionStart();
        int end = numbers.getSelectionEnd();
        // get string
        String numberStr = "";
        // Operator
        
        switch (btn.getId()){
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btnComma:
                numberStr = numbers.getText().toString() +  btn.getText().toString();
                break;
            case R.id.btnPlusMinus:
                // Se não tiver número, não faz nada
                if ( numbers.getText().toString().isEmpty() ){
                    return;
                }
                // Se já possui um - na expressão, remove
                if ( numbers.getText().toString().contains("-") ){
                    numberStr = numbers.getText().toString().substring(1);
                // Caso contrário, insere
                } else {
                    numberStr = "-" + numbers.getText().toString();
                }
                break;
            case R.id.bnt10ex:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText( "10^(" + doubleToString(num1) + ")");
                num1 = Math.pow(10,num1);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnLog:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText( "log(" + doubleToString(num1) + ")");
                num1 = Math.log10(num1);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnLn:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText( "ln(" + doubleToString(num1) + ")");
                num1 = Math.log(num1);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnyx:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText(doubleToString(num1)+Operators.ROOT.character);
                operator = Operators.ROOT.character;
                break;
            case R.id.btnSqrt:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    break;
                }
                txtExpressions.setText( Operators.SQRT.character + doubleToString(num1) );
                num1 = Math.pow(num1,1.0f/2.0f);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnx2:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    break;
                }
                if ( num1 >= 0 ){
                    txtExpressions.setText( doubleToString(num1) + "²" );
                } else {
                    txtExpressions.setText( "(" + doubleToString(num1) +")" + "²" );
                }
                num1 = Math.pow(num1,2.0f);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnInv:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    break;
                }
                txtExpressions.setText( "1/" + doubleToString(num1) );
                num1 = 1.0f/num1;
                numberStr = doubleToString(num1);
                break;
            case R.id.btnAbs:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    break;
                }
                txtExpressions.setText( "|" + doubleToString(num1) + "|" );
                num1 = Math.abs(num1);
                numberStr = doubleToString(num1);
                break;
            case R.id.btnPi:
                num1 = Math.PI;
                txtExpressions.setText( "\u03C0" );
                numberStr = doubleToString(num1);
                break;
            case R.id.btne:
                num1 = Math.exp(1);
                txtExpressions.setText( "e" );
                numberStr = doubleToString(num1);
                break;
            case R.id.btnFat:
                try{
                    num1 = Integer.parseUnsignedInt(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Fatorial de número negativo");
                    return;
                }
                txtExpressions.setText( doubleToString(num1) + "!");

                try {
                    num1 = fatorial((int)num1);
                } catch ( IllegalArgumentException e ){
                    numbers.setError("Fatorial de número negativo");
                    return;
                }
                numberStr = doubleToString(num1);
                break;
            case R.id.btnDiv:
            case R.id.btnPlus:
            case R.id.btnMinus:
            case R.id.btnMult:
            case R.id.btnxey:
            case R.id.btnMod:
            case R.id.btnPercent:
            case R.id.btnExp:
                try{
                    num1 = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText(doubleToString(num1)+btn.getText().toString());
                operator = btn.getText().toString();
                break;
            case R.id.btnEquals:
                double n;
                try{
                    n = Double.parseDouble(numbers.getText().toString());
                } catch ( NumberFormatException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }

                // Verifica se não há nenhuma operação ainda
                if ( operator.equals("") ){
                    return;
                }

                // Verifica se o igual já foi acionado alguma vez.
                // Se sim, permitir que número digitado seja o n1
                if ( !txtExpressions.getText().toString().contains("=") ){
                    num2 = n;
                } else {
                    num1 = n;
                }

                // Calcular resultado da expressão
                double result = 0;
                try {
                    result = calcular(num1,num2,operator);
                } catch ( IllegalArgumentException e ){
                    numbers.setError("Erro na expressão");
                    return;
                }
                txtExpressions.setText(doubleToString(num1) + operator + doubleToString(num2) + "=");
                numberStr = doubleToString(result);

                break;
            case R.id.btnErase:
                if ( start == end && start-1 >= 0 ){
                    numbers.getText().delete(start-1, end);
                    numbers.setSelection(start-1,start-1);
                } else {
                    numbers.getText().delete(start, end);
                    numbers.setSelection(start,start);
                }
                return;
            case R.id.btnEraseAll:
                num1 = 0;
                num2 = 0;
                txtExpressions.setText("");
                operator = "";
                numberStr = "";
                break;
            default:
                break;
        }

        // Set text and set cursor
        //lastCharacter = numberStr.charAt(numberStr.length()-1);
        numbers.setText(numberStr);
        numbers.setSelection(numbers.getText().length());

    }

}