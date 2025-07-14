package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CitaAdminAdapter extends RecyclerView.Adapter<CitaAdminAdapter.ViewHolder> {

    private List<Cita> citas;
    private OnCitaListener onVerListener, onCancelarListener;

    public interface OnCitaListener {
        void onClick(Cita cita);
    }

    public CitaAdminAdapter(List<Cita> citas, OnCitaListener onVer, OnCitaListener onCancelar) {
        this.citas = citas;
        this.onVerListener = onVer;
        this.onCancelarListener = onCancelar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cita_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cita cita = citas.get(position);

        // Por defecto muestra esto:
        holder.tvUsuario.setText("Usuario: Cargando...");

        // Busca el nombre en Firestore usando el UID del usuario
        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(cita.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String nombre = documentSnapshot.getString("nombre");
                    if (nombre != null && !nombre.isEmpty()) {
                        holder.tvUsuario.setText(nombre);
                    } else {
                        holder.tvUsuario.setText("Usuario desconocido");
                    }
                })
                .addOnFailureListener(e -> {
                    holder.tvUsuario.setText("Usuario desconocido");
                });

        holder.btnVerCita.setOnClickListener(v -> onVerListener.onClick(cita));
        holder.btnCancelarCita.setOnClickListener(v -> onCancelarListener.onClick(cita));
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public void setCitas(List<Cita> nuevas) {
        citas = nuevas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsuario;
        Button btnVerCita, btnCancelarCita;

        ViewHolder(View v) {
            super(v);
            tvUsuario = v.findViewById(R.id.tvUsuario);
            btnVerCita = v.findViewById(R.id.btnVerCita);
            btnCancelarCita = v.findViewById(R.id.btnCancelarCita);
        }
    }
}


