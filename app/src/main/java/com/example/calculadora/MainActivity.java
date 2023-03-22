package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int idBotones[]={R.id.button0,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
            R.id.button6,R.id.button7,R.id.button8,R.id.button9};
    private Button botones[]=new Button[10];
    private Button borrar, comprobar, reiniciar;
    private TextView tvAciertos,tvFallos,tvOperacion,tvResultado,tvPorcentaje,tvRecord;
    private int res,input,aciertos=0, fallos=0;
    private double porcentaje=0.0, record=0.0;
    private String stringRes, stringInput;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignación de vistas a controles
        tvOperacion=findViewById(R.id.textViewOp);
        tvResultado=findViewById(R.id.textViewRes);
        tvAciertos=findViewById(R.id.textViewAciertos);
        tvFallos=findViewById(R.id.textViewFallos);
        tvPorcentaje=findViewById(R.id.textViewPorcentaje);
        tvRecord=findViewById(R.id.textViewRecord);
        borrar=findViewById(R.id.buttonBorrar);
        comprobar=findViewById(R.id.buttonComprobar);
        reiniciar=findViewById(R.id.buttonReiniciar);


        tvAciertos.setText("Aciertos: " + aciertos);
        tvFallos.setText("Fallos: " + fallos);
        tvPorcentaje.setText("Porcentaje: " + porcentaje + "%");
        tvRecord.setText("Record: " + record + "%");

        //Listeners

        borrar.setOnClickListener(view -> {
            tvResultado.setText("");
        });


        for (int i=0; i<10;i++) {
            botones[i]=findViewById(idBotones[i]);
            botones[i].setOnClickListener(view -> tvResultado.setText(tvResultado.getText().toString()+((Button)view).getText()));
            botones[i].setOnClickListener(view -> {
                tvResultado.setText(tvResultado.getText().toString() + ((Button) view).getText());
            });
        }

        //Lógica
        generaOperacion();
        borrar.setEnabled(false);
        comprobar.setEnabled(false);

        reiniciar.setOnClickListener(view -> {

            DecimalFormat formato = new DecimalFormat("#.00");

            if(porcentaje>record){
                record=porcentaje;
            }

            aciertos=0;
            fallos=0;
            porcentaje=0.0;
            tvAciertos.setText("Aciertos: " + aciertos);
            tvFallos.setText("Fallos: " + fallos);
            tvPorcentaje.setText("Porcentaje: " + porcentaje + "%");
            tvRecord.setText("Record: " + formato.format(record) + "%");
        });
        tvResultado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(tvResultado.length() > 0){
                    borrar.setEnabled(true);
                    comprobar.setEnabled(true);
                } else {
                    borrar.setEnabled(false);
                    comprobar.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        comprobar.setOnClickListener(view -> {
            if(stringRes.equals(tvResultado.getText().toString())){
                tvAciertos.setText("Aciertos: "+ ++aciertos);
                generaOperacion();
            }
            else {
                tvFallos.setText("Fallos: "+ ++fallos);
                generaOperacion();
            }
            tvResultado.setText("");

            DecimalFormat formato = new DecimalFormat("#.00");
            porcentaje=(Double.valueOf(aciertos))*100/(Double.valueOf(aciertos) + Double.valueOf(fallos));
            tvPorcentaje.setText("Porcentaje: " + formato.format(porcentaje) + "%");

        });


    }
    private void generaOperacion(){
        Random r=new Random();
        int op=r.nextInt(4);
        if(op==0){
            int op1=r.nextInt(10);
            int op2=r.nextInt(10);
            tvOperacion.setText(op1 +" + "+ op2 + " = ");
            res=op1+op2;
        } else if (op==1) {
            int op1=r.nextInt(10);
            int op2=r.nextInt(10);
            if(op2>op1){
                int aux=op1;
                op1=op2;
                op2=aux;
            }
            tvOperacion.setText(op1 +" - "+ op2 + " = ");
            res=op1-op2;
        } else if (op==2){
            int op1=r.nextInt(10);
            int op2=r.nextInt(10);
            tvOperacion.setText(op1 +" * "+ op2 + " = ");
            res=op1*op2;
        } else {
            int op1=r.nextInt(10);
            int op2=r.nextInt(10);
            if(op2>op1){
                int aux=op1;
                op1=op2;
                op2=aux;
            }

            while (op2 == 0 || op1 % op2 != 0) {
                op1=r.nextInt(10);
                op2=r.nextInt(10);
            }
            tvOperacion.setText(op1 +" / "+ op2 + " = ");
            res = op1 / op2;
        }
        stringRes = String.valueOf(res);
    }
}