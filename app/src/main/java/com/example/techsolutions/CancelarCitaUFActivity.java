package com.example.techsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import java.util.*;

public class CancelarCitaUFActivity extends AppCompatActivity {

    private RecyclerView recyclerCitas;
    private CitaAdapter citaAdapter;
    private List<Cita> listaCitas = new ArrayList<>();
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_cita_uf);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(CancelarCitaUFActivity.this, ReservasUFActivity.class);
            startActivity(intent);

            finish();
        });

        recyclerCitas = findViewById(R.id.recyclerCitas);
        recyclerCitas.setLayoutManager(new LinearLayoutManager(this));

        citaAdapter = new CitaAdapter(listaCitas, cita -> cancelarCita(cita));
        recyclerCitas.setAdapter(citaAdapter);

        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = (user != null) ? user.getUid() : null;

        cargarCitas();
    }

    private void cargarCitas() {
        if (uid == null) return;
        db.collection("citas")
                .whereEqualTo("uid", uid)
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
                    citaAdapter.setCitas(listaCitas);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar citas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void cancelarCita(Cita cita) {
        db.collection("citas").document(cita.getId())
                .update("estado", "Cancelada")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cita cancelada", Toast.LENGTH_SHORT).show();
                    cargarCitas(); // Refrescar la lista
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cancelar cita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
