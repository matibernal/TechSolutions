package com.example.techsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class PedidosUFActivity extends AppCompatActivity {

    private EditText etDescripcionPedido;
    private Button btnEnviarPedido;
    private RecyclerView rvPedidos;
    private PedidoAdapter pedidoAdapter;
    private List<Pedido> pedidoList = new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_ufactivity);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {

            Intent intent = new Intent(PedidosUFActivity.this, MenuUsuarioActivity.class);
            startActivity(intent);

            finish();
        });

        etDescripcionPedido = findViewById(R.id.etDescripcionPedido);
        btnEnviarPedido     = findViewById(R.id.btnEnviarPedido);
        rvPedidos           = findViewById(R.id.rvPedidos);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        pedidoAdapter = new PedidoAdapter(pedidoList);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        rvPedidos.setAdapter(pedidoAdapter);

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String uid = user.getUid();

        // Enviar pedido
        btnEnviarPedido.setOnClickListener(v -> {
            String descripcion = etDescripcionPedido.getText().toString().trim();
            if (TextUtils.isEmpty(descripcion)) {
                Toast.makeText(this, "Ingrese una descripción", Toast.LENGTH_SHORT).show();
                return;
            }
            String id = db.collection("pedidos").document().getId();
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            Pedido pedido = new Pedido(id, descripcion, fecha, "Pendiente", uid);

            db.collection("pedidos").document(id)
                    .set(pedido)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Pedido enviado", Toast.LENGTH_SHORT).show();
                        etDescripcionPedido.setText("");
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error al enviar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        // Trae solo los pedidos de este usuario
        db.collection("pedidos")
                .whereEqualTo("uid", uid)
                .orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Pedido> nuevos = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots) {
                        Pedido pedido = doc.toObject(Pedido.class);
                        nuevos.add(pedido);
                    }
                    pedidoAdapter.setPedidos(nuevos);
                });
    }
}
