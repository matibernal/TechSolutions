package com.example.techsolutions;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.*;

public class CambiarCitaUFActivity extends AppCompatActivity {

    private RecyclerView recyclerCitasCambiar;
    private CambiarCitaAdapter citaAdapter;
    private List<Cita> listaCitas = new ArrayList<>();
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_cita_uf);

        recyclerCitasCambiar = findViewById(R.id.recyclerCitasCambiar);
        recyclerCitasCambiar.setLayoutManager(new LinearLayoutManager(this));

        citaAdapter = new CambiarCitaAdapter(listaCitas, cita -> mostrarDialogoCambiarCita(cita));
        recyclerCitasCambiar.setAdapter(citaAdapter);

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

    private void mostrarDialogoCambiarCita(Cita cita) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    month++;
                    String fecha = String.format("%04d-%02d-%02d", year, month, dayOfMonth);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (timeView, hourOfDay, minute) -> {
                                String hora = String.format("%02d:%02d", hourOfDay, minute);
                                String nuevaFechaHora = fecha + " " + hora;
                                cambiarCitaEnFirestore(cita, nuevaFechaHora);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void cambiarCitaEnFirestore(Cita cita, String nuevaFechaHora) {
        db.collection("citas").document(cita.getId())
                .update("fechaHora", nuevaFechaHora)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cita reprogramada", Toast.LENGTH_SHORT).show();
                    cargarCitas();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al reprogramar cita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
