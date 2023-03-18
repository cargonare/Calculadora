package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

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
    private Button borrar;
    private TextView tvAciertos,tvFallos,tvOperacion,tvResultado,tvPorcentaje;
    private int res,input,aciertos=0, fallos=0;
    private double porcentaje=0.0;
    private String stringRes, stringInput;
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
        borrar=findViewById(R.id.buttonBorrar);

        //Listeners

        borrar.setOnClickListener(view -> {
            String texto = tvResultado.getText().toString();
            if (texto.length() > 0) {
                tvResultado.setText(texto.substring(0, texto.length() - 1));
            }
        });


        for (int i=0; i<10;i++) {
            botones[i]=findViewById(idBotones[i]);
            botones[i].setOnClickListener(view -> tvResultado.setText(tvResultado.getText().toString()+((Button)view).getText()));
        }

        //Lógica
        generaOperacion();
        tvResultado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                stringInput=charSequence.toString();
                stringRes=Integer.toString(res);
                if(stringInput.length()==stringRes.length()){
                    if(stringRes.equals(stringInput)){
                        tvAciertos.setText("Aciertos: "+ ++aciertos);
                        generaOperacion();
                    }else{
                        tvFallos.setText("Fallos: "+ ++fallos);
                    }
                    tvResultado.setText("");

                    DecimalFormat formato = new DecimalFormat("#.00");
                    porcentaje=(Double.valueOf(aciertos))*100/(Double.valueOf(aciertos) + Double.valueOf(fallos));
                    tvPorcentaje.setText("Porcentaje: " + formato.format(porcentaje) + "%");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





    }
    private void generaOperacion(){
        Random r=new Random();
        int op1=r.nextInt(10);
        int op2=r.nextInt(10);
        tvOperacion.setText(op1 +" + "+ op2);
        res=op1+op2;

    }
}