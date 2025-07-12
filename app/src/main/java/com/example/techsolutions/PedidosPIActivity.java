package com.example.techsolutions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class PedidosPIActivity extends AppCompatActivity {

    private RecyclerView rvPedidosAdmin;
    private PedidoAdminAdapter adapter;
    private List<Pedido> pedidoList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_piactivity);

        // Boton de volver atras
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(PedidosPIActivity.this, MenuPIActivity.class);
            startActivity(intent);
            finish();
        });

        // Setup RecyclerView
        rvPedidosAdmin = findViewById(R.id.rvPedidosAdmin);
        rvPedidosAdmin.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PedidoAdminAdapter(pedidoList, new PedidoAdminAdapter.OnPedidoActionListener() {
            @Override
            public void onAceptar(Pedido pedido) {
                cambiarEstadoPedido(pedido, "Aceptado");
            }

            @Override
            public void onRechazar(Pedido pedido) {
                cambiarEstadoPedido(pedido, "Rechazado");
            }
        });
        rvPedidosAdmin.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("pedidos")
                .orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Pedido> nuevos = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots) {
                        Pedido pedido = doc.toObject(Pedido.class);
                        // Aseguramos que el id este seteado x las dudas
                        if (pedido != null) pedido.setId(doc.getId());
                        nuevos.add(pedido);
                    }
                    adapter.setPedidos(nuevos);
                });
    }

    private void cambiarEstadoPedido(Pedido pedido, String nuevoEstado) {
        db.collection("pedidos").document(pedido.getId())
                .update("estado", nuevoEstado)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Pedido " + nuevoEstado.toLowerCase(), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
