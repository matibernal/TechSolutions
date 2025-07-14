package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CambiarCitaAdapter extends RecyclerView.Adapter<CambiarCitaAdapter.CitaViewHolder> {
    private List<Cita> citas;
    private OnCitaChangeListener listener;

    public interface OnCitaChangeListener {
        void onCambiar(Cita cita);
    }

    public CambiarCitaAdapter(List<Cita> citas, OnCitaChangeListener listener) {
        this.citas = citas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita_cambiar, parent, false);
        return new CitaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        Cita cita = citas.get(position);
        holder.tvMotivo.setText(cita.getMotivo());
        holder.tvFechaHora.setText(cita.getFechaHora());
        holder.tvEstado.setText("Estado: " + cita.getEstado());
        holder.btnCambiar.setOnClickListener(v -> listener.onCambiar(cita));
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public void setCitas(List<Cita> nuevas) {
        citas = nuevas;
        notifyDataSetChanged();
    }

    static class CitaViewHolder extends RecyclerView.ViewHolder {
        TextView tvMotivo, tvFechaHora, tvEstado;
        Button btnCambiar;
        CitaViewHolder(View v) {
            super(v);
            tvMotivo = v.findViewById(R.id.tvMotivoCita);
            tvFechaHora = v.findViewById(R.id.tvFechaHoraCita);
            tvEstado = v.findViewById(R.id.tvEstadoCita);
            btnCambiar = v.findViewById(R.id.btnCambiarCita);
        }
    }
}
