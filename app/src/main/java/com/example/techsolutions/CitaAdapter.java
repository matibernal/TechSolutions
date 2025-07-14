package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.CitaViewHolder> {
    private List<Cita> citas;
    private OnCitaCancelListener listener;

    public interface OnCitaCancelListener {
        void onCancelar(Cita cita);
    }

    public CitaAdapter(List<Cita> citas, OnCitaCancelListener listener) {
        this.citas = citas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita, parent, false);
        return new CitaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        Cita cita = citas.get(position);
        holder.tvMotivo.setText(cita.getMotivo());
        holder.tvFechaHora.setText(cita.getFechaHora());
        holder.tvEstado.setText("Estado: " + cita.getEstado());
        holder.btnCancelar.setOnClickListener(v -> listener.onCancelar(cita));
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
        Button btnCancelar;
        CitaViewHolder(View v) {
            super(v);
            tvMotivo = v.findViewById(R.id.tvMotivoCita);
            tvFechaHora = v.findViewById(R.id.tvFechaHoraCita);
            tvEstado = v.findViewById(R.id.tvEstadoCita);
            btnCancelar = v.findViewById(R.id.btnCancelarCita);
        }
    }
}
