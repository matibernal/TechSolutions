package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actvity);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etApellido = findViewById(R.id.etApellido);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnAceptar = findViewById(R.id.btnAceptar);
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnAceptar.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString().trim();
            String apellido = etApellido.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || password.length() < 6) {
                Toast.makeText(this, "Completa todos los campos y una contraseña de al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crea usuario en Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Usuario creado. Envia el mail de verificación
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnSuccessListener(aVoid -> {
                                            // Guardar nombre y apellido en Firestore
                                            Map<String, Object> datosUsuario = new HashMap<>();
                                            datosUsuario.put("nombre", nombre);
                                            datosUsuario.put("apellido", apellido);
                                            datosUsuario.put("email", email);

                                            db.collection("usuarios").document(user.getUid())
                                                    .set(datosUsuario)
                                                    .addOnSuccessListener(unused -> {
                                                        Toast.makeText(this, "Registrado. Revisa tu correo para verificar la cuenta.", Toast.LENGTH_LONG).show();
                                                        auth.signOut(); // Forzar logout tras registro
                                                        startActivity(new Intent(this, MainActivity.class));
                                                        finish();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(this, "Usuario creado pero error al guardar perfil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "No se pudo enviar el mail de verificación.", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
