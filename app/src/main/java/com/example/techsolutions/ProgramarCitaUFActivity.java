package com.example.techsolutions;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProgramarCitaUFActivity extends AppCompatActivity {

    private EditText etMotivo, etFecha, etHora;
    private Button btnConfirmar;
    private ImageButton btnBack;

    private String fechaSeleccionada = "", horaSeleccionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programar_citauf);

        etMotivo = findViewById(R.id.etMotivo);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        btnConfirmar = findViewById(R.id.btnConfirmarCita);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        etFecha.setOnClickListener(v -> mostrarDatePicker());
        etHora.setOnClickListener(v -> mostrarTimePicker());

        btnConfirmar.setOnClickListener(v -> {
            String motivo = etMotivo.getText().toString().trim();
            if (motivo.isEmpty() || fechaSeleccionada.isEmpty() || horaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            guardarCitaEnFirestore(motivo, fechaSeleccionada + " " + horaSeleccionada);
        });
    }

    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    month++; // Los meses empiezan en 0
                    fechaSeleccionada = String.format("%04d-%02d-%02d", year, month, dayOfMonth);
                    etFecha.setText(fechaSeleccionada);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void mostrarTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                    etHora.setText(horaSeleccionada);
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void guardarCitaEnFirestore(String motivo, String fechaHora) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Error: usuario no logueado", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> cita = new HashMap<>();
        cita.put("uid", user.getUid());
        cita.put("fechaHora", fechaHora);
        cita.put("motivo", motivo);
        cita.put("estado", "Programada");

        db.collection("citas")
                .add(cita)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Cita solicitada correctamente", Toast.LENGTH_LONG).show();
                    finish(); // Volver atrás
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar cita: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
