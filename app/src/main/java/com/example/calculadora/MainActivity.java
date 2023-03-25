package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int idBotones[]={R.id.button0,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
            R.id.button6,R.id.button7,R.id.button8,R.id.button9};
    private Button botones[]=new Button[10];
    private Button borrar, comprobar, reiniciar, iniciar;
    private TextView tvAciertos,tvFallos,tvOperacion,tvResultado,tvPorcentaje,tvRecord;
    private int res,input,aciertos=0, fallos=0, record=0;
    private double porcentaje=0.0;
    private String stringRes, stringInput;
    Chronometer cronometro;

    private CountDownTimer cuentaAtras;
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
        iniciar=findViewById(R.id.buttonInicio);
        cronometro=findViewById(R.id.chronometer);


        tvAciertos.setText("Aciertos: " + aciertos);
        tvFallos.setText("Fallos: " + fallos);
        tvPorcentaje.setText("Porcentaje: " + porcentaje + "%");
        tvRecord.setText("Record de aciertos: " + record);
        tvOperacion.setText("");

        long currentTime = SystemClock.elapsedRealtime();
        long tenSecondsLater = currentTime - 30000; // 30 segundos en milisegundos
        cronometro.setBase(tenSecondsLater);

        //Listeners

        borrar.setOnClickListener(view -> {
            tvResultado.setText("");
        });

        iniciar.setOnClickListener(view -> {
            iniciar.setVisibility(View.GONE);
            reiniciar.setVisibility(View.VISIBLE);
            activarBotones();
            generaOperacion();
            if(cuentaAtras!=null){
                cuentaAtras.cancel();
            }
            arrancarCronometro();
        });

        for (int i=0; i<10;i++) {
            botones[i]=findViewById(idBotones[i]);
            botones[i].setOnClickListener(view -> tvResultado.setText(tvResultado.getText().toString()+((Button)view).getText()));
            botones[i].setOnClickListener(view -> {
                tvResultado.setText(tvResultado.getText().toString() + ((Button) view).getText());
            });
        }

        //Lógica
        //generaOperacion();
        desactivarBotones();
        borrar.setEnabled(false);
        comprobar.setEnabled(false);

        reiniciar.setOnClickListener(view -> {
            reinicio();
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

    private void activarBotones() {
        for (int i=0; i<10;i++) {
            botones[i]=findViewById(idBotones[i]);
            botones[i].setEnabled(true);
        }
    }

    private void arrancarCronometro() {
        cuentaAtras=new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<10000){
                    cronometro.setText("00" + ":" + "0" + millisUntilFinished / 1000);
                } else{
                    cronometro.setText("00" + ":" + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                cronometro.setText("00:00");
                Toast.makeText(MainActivity.this, "Se acabó el tiempo, has conseguido " + aciertos + " aciertos", Toast.LENGTH_SHORT).show();
                actualizar();
                tvOperacion.setText("");
                iniciar.setVisibility(View.VISIBLE);
                reiniciar.setVisibility(View.GONE);
                desactivarBotones();
            }
        }.start();
    }

    private void desactivarBotones() {
        for (int i=0; i<10;i++) {
            botones[i]=findViewById(idBotones[i]);
            botones[i].setEnabled(false);
        }
    }

    private void reinicio() {
        Toast.makeText(MainActivity.this, "Has reiniciado partida, has consegido " + aciertos + " aciertos", Toast.LENGTH_SHORT).show();
        iniciar.setVisibility(View.VISIBLE);
        reiniciar.setVisibility(View.GONE);
        tvResultado.setText("");
        tvOperacion.setText("");
        desactivarBotones();
        cuentaAtras.cancel();

        cronometro.setText("00" + ":" + "30");
        borrar.setEnabled(false);
        comprobar.setEnabled(false);
        actualizar();
        //generaOperacion();
    }

    private void actualizar() {


        if(aciertos>record){
            record=aciertos;
        }

        aciertos=0;
        fallos=0;
        porcentaje=0.0;
        tvAciertos.setText("Aciertos: " + aciertos);
        tvFallos.setText("Fallos: " + fallos);
        tvPorcentaje.setText("Porcentaje: " + porcentaje + "%");
        tvRecord.setText("Record de aciertos: " + record);
    }

    /**
     * Genera la operación de forma aleatoria
     */
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