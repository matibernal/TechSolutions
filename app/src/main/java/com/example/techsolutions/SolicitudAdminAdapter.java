package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SolicitudAdminAdapter extends RecyclerView.Adapter<SolicitudAdminAdapter.SolicitudViewHolder> {

    private List<Solicitud> solicitudes;
    private OnSolicitudClickListener listener;

    public interface OnSolicitudClickListener {
        void onClick(Solicitud solicitud);
    }

    public SolicitudAdminAdapter(List<Solicitud> solicitudes, OnSolicitudClickListener listener) {
        this.solicitudes = solicitudes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SolicitudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitud_admin, parent, false);
        return new SolicitudViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudViewHolder holder, int position) {
        Solicitud s = solicitudes.get(position);
        holder.tvNombreCompleto.setText(s.getNombre() + " " + s.getApellido());
        holder.tvEmail.setText(s.getEmail());
        holder.tvFecha.setText(s.getFechaTexto());
        holder.tvMensajePreview.setText(s.getMensaje());
        holder.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    @Override
    public int getItemCount() { return solicitudes.size(); }

    public void setSolicitudes(List<Solicitud> nuevas) {
        this.solicitudes = nuevas;
        notifyDataSetChanged();
    }

    static class SolicitudViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCompleto, tvEmail, tvFecha, tvMensajePreview;

        public SolicitudViewHolder(@NonNull View v) {
            super(v);
            tvNombreCompleto = v.findViewById(R.id.tvNombreCompleto);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvFecha = v.findViewById(R.id.tvFecha);
            tvMensajePreview = v.findViewById(R.id.tvMensajePreview);
        }
    }
}
