package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class ReservasUFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_ufactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuUsuarioActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnProgramar = findViewById(R.id.btnProgramarCita);
        Button btnCancelar = findViewById(R.id.btnCancelarCita);
        Button btnCambiar = findViewById(R.id.btnCambiarCita);

        btnProgramar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProgramarCitaUFActivity.class);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CancelarCitaUFActivity.class);
            startActivity(intent);
        });

        btnCambiar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CambiarCitaUFActivity.class);
            startActivity(intent);
        });
    }
}
