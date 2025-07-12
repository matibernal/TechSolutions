package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText etEmail, etPassword;
    private Button loginBtn, registroBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        // ViewBinding manual
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        loginBtn    = findViewById(R.id.loginbtn);
        registroBtn = findViewById(R.id.registroBtn);

        loginBtn.setOnClickListener(v -> {
            String email    = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,
                        "Por favor ingresa correo electrónico y contraseña",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Login con FirebaseAuth
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                // Busca si hay usuarios en Firebase
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("usuarios").document(user.getUid())
                                        .get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            String nombre = "";
                                            if (documentSnapshot.exists()) {
                                                nombre = documentSnapshot.getString("nombre");
                                                if (nombre == null) nombre = "";
                                                // Buscamos el rol del usuario, si encuentra es un admin
                                                String rol = documentSnapshot.getString("rol");
                                                if ("admin".equals(rol)) {
                                                    Toast.makeText(this,
                                                            "Bienvenido, admin " + nombre + "!",
                                                            Toast.LENGTH_LONG).show();
                                                    // Va al menú admin
                                                    startActivity(new Intent(this, MenuPIActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(this,
                                                            "Bienvenido, " + nombre + "!",
                                                            Toast.LENGTH_LONG).show();
                                                    // Va al menú normal
                                                    startActivity(new Intent(this, MenuUsuarioActivity.class));
                                                    finish();
                                                }
                                            } else {
                                                // Si el nombre no existe igual lo dejamos pasar como usuario común
                                                Toast.makeText(this,
                                                        "Bienvenido!",
                                                        Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(this, MenuUsuarioActivity.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this,
                                                    "Bienvenido!",
                                                    Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(this, MenuUsuarioActivity.class));
                                            finish();
                                        });
                            } else {
                                Toast.makeText(this,
                                        "Verifica tu correo electrónico antes de iniciar sesión.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this,
                                    "Error de autenticación: " +
                                            task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        registroBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistroActivity.class));
        });
    }
}
