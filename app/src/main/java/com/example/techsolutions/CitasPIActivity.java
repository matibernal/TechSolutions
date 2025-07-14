package com.example.techsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CitasPIActivity extends AppCompatActivity {

    private RecyclerView recyclerCitas;
    private CitaAdminAdapter adapter;
    private List<Cita> listaCitas = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_piactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(CitasPIActivity.this, MenuPIActivity.class);
            startActivity(intent);

            finish();
        });

        recyclerCitas = findViewById(R.id.recyclerCitasPI);
        recyclerCitas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitaAdminAdapter(listaCitas, this::onVerCita, this::onCancelarCita);
        recyclerCitas.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        cargarCitas();
    }

    private void cargarCitas() {
        db.collection("citas")
                .whereEqualTo("estado", "Programada")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaCitas.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Cita cita = doc.toObject(Cita.class);
                        if (cita != null) {
                            cita.setId(doc.getId());
                            listaCitas.add(cita);
                        }
                    }
                    adapter.setCitas(listaCitas);
                });
    }

    private void onVerCita(Cita cita) {
        // Podés mostrar un Dialog o una nueva Activity con los detalles
        new AlertDialog.Builder(this)
                .setTitle("Detalle de cita")
                .setMessage("Motivo: " + cita.getMotivo() +
                        "\nFecha: " + cita.getFechaHora())
                .setPositiveButton("OK", null)
                .show();
    }

    private void onCancelarCita(Cita cita) {
        db.collection("citas").document(cita.getId())
                .update("estado", "Cancelada")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cita cancelada", Toast.LENGTH_SHORT).show();
                    cargarCitas();
                });
    }
}
