package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(MenuUsuarioActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        });

        Button btnPedidos = findViewById(R.id.btnPedidos);
        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsuarioActivity.this, PedidosUFActivity.class);
                startActivity(intent);
            }
        });


        Button btnReserva = findViewById(R.id.btnReserva);
        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsuarioActivity.this, ReservasUFActivity.class);
                startActivity(intent);
            }
        });


        Button btnAtencion = findViewById(R.id.btnAtencion);
        btnAtencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsuarioActivity.this, AtencionUFActivity.class);
                startActivity(intent);
            }
        });


    }
}