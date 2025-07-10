package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuPIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_piactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(MenuPIActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        });

        Button btnPedidosPI = findViewById(R.id.btnPedidosPI);
        btnPedidosPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPIActivity.this, PedidosPIActivity.class);
                startActivity(intent);
            }
        });

        Button btnCitas = findViewById(R.id.btnCitas);
        btnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPIActivity.this, CitasPIActivity.class);
                startActivity(intent);
            }
        });

        Button btnSolicitudes = findViewById(R.id.btnSolicitudes);
        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPIActivity.this, SolicitudesPIActivity.class);
                startActivity(intent);
            }
        });

    }
}