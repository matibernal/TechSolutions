package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText etUsername, etPassword;
    private Button loginBtn, registroBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        // ViewBinding manual
        etUsername  = findViewById(R.id.Username);
        etPassword  = findViewById(R.id.Password);
        loginBtn    = findViewById(R.id.loginbtn);
        registroBtn = findViewById(R.id.registroBtn);

        loginBtn.setOnClickListener(v -> {
            String email    = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,
                        "Por favor ingresa email y contraseña",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamada a FirebaseAuth
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            // Verifico que el email esté validado
                            if (user != null && user.isEmailVerified()) {
                                // Vuelve a la pantalla principal
                                startActivity(new Intent(this, MenuUsuarioActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this,
                                        "Verifica tu email antes de iniciar sesión",
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
