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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        EditText Username = findViewById(R.id.Username);
        EditText Password = findViewById(R.id.Password);


        Button loginbtn = findViewById(R.id.loginbtn);
        Button registroBtn = findViewById(R.id.registroBtn);

        loginbtn.setOnClickListener(v -> {
            String username = Username.getText().toString();
            String password = Password.getText().toString();

            if (username.equals("usuarioFinal") && password.equals("1234")) {
                // Iniciar sesión como usuario final
                Intent intent = new Intent(this, MenuUsuarioActivity.class);
                startActivity(intent);
            } else if (username.equals("usuarioInterno") && password.equals("2345")) {
                // Iniciar sesión como personal interno
                Intent intent = new Intent(this, MenuPIActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        registroBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}
