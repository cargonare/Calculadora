package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Estadisticas extends AppCompatActivity {

    private ArrayList<Resultado> listaResultados;
    private Button volver;
    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        volver=findViewById(R.id.buttonVolver);

        volver.setOnClickListener(view -> {
            onBackPressed();
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listaResultados = bundle.getParcelableArrayList("listaResultados");
        }

        System.out.println("la lista es esta: " + listaResultados);

        ArrayAdapter<Resultado> adapter = new ArrayAdapter<Resultado>(
                this,
                R.layout.list_view,
                R.id.text_index,
                listaResultados) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(R.id.text_index);
                TextView text2 = view.findViewById(R.id.text_aciertos);
                TextView text3 = view.findViewById(R.id.text_fallos);
                TextView text4 = view.findViewById(R.id.text_porcentaje);

                Resultado resultado = getItem(position);

                text1.setText("Intento: " + String.valueOf(resultado.getIndex()));
                text2.setText("Aciertos: " + String.valueOf(resultado.getAciertos()));
                text3.setText("Fallos: " + String.valueOf(resultado.getFallos()));
                DecimalFormat formato = new DecimalFormat("#.00");
                text4.setText("Porcentaje: " + String.valueOf(formato.format(resultado.getPorcentaje()) + " %"));

                return view;
            }

            public boolean isEnabled(int position) {
                return false;
            }
        };

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    };
}