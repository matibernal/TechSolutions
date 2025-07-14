package com.example.techsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class SolicitudesPIActivity extends AppCompatActivity {

    private RecyclerView recyclerSolicitudes;
    private SolicitudAdminAdapter adapter;
    private List<Solicitud> solicitudes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_piactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(SolicitudesPIActivity.this, MenuPIActivity.class);
            startActivity(intent);

            finish();
        });

        recyclerSolicitudes = findViewById(R.id.recyclerSolicitudes);
        recyclerSolicitudes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SolicitudAdminAdapter(solicitudes, this::mostrarDetalleSolicitud);
        recyclerSolicitudes.setAdapter(adapter);

        cargarSolicitudes();
    }

    private void cargarSolicitudes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consultas")
                .orderBy("fecha", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    solicitudes.clear();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String id = doc.getId();
                        String nombre = doc.getString("nombre");
                        String apellido = doc.getString("apellido");
                        String email = doc.getString("email");
                        String mensaje = doc.getString("mensaje");
                        Date fecha = doc.getDate("fecha");
                        String fechaTexto = fecha != null ? sdf.format(fecha) : "";
                        solicitudes.add(new Solicitud(id, nombre, apellido, email, mensaje, fechaTexto));
                    }
                    adapter.setSolicitudes(solicitudes);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar solicitudes: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void mostrarDetalleSolicitud(Solicitud s) {
        new AlertDialog.Builder(this)
                .setTitle("Mensaje de " + s.getNombre() + " " + s.getApellido())
                .setMessage(
                        "Email: " + s.getEmail() +
                                "\nFecha: " + s.getFechaTexto() +
                                "\n\nMensaje:\n" + s.getMensaje())
                .setPositiveButton("Cerrar", null)
                .show();
    }
}
