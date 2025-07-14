package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AtencionUFActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etEmail, etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion_ufactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AtencionUFActivity.this, MenuUsuarioActivity.class);
            startActivity(intent);
            finish();
        });

        etNombre = findViewById(R.id.name);
        etApellido = findViewById(R.id.surname);
        etEmail = findViewById(R.id.Email);
        etMensaje = findViewById(R.id.msj);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> enviarConsulta());
    }

    private void enviarConsulta() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mensaje = etMensaje.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(mensaje)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> consulta = new HashMap<>();
        consulta.put("nombre", nombre);
        consulta.put("apellido", apellido);
        consulta.put("email", email);
        consulta.put("mensaje", mensaje);
        consulta.put("fecha", FieldValue.serverTimestamp());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consultas")
                .add(consulta)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Consulta enviada correctamente", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al enviar consulta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
