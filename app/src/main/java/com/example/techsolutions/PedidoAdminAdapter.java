package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class PedidoAdminAdapter extends RecyclerView.Adapter<PedidoAdminAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;
    private OnPedidoActionListener listener;

    public interface OnPedidoActionListener {
        void onAceptar(Pedido pedido);
        void onRechazar(Pedido pedido);
    }

    public PedidoAdminAdapter(List<Pedido> pedidos, OnPedidoActionListener listener) {
        this.pedidos = pedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido_admin, parent, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.tvDescripcion.setText(pedido.getDescripcion());
        holder.tvEstado.setText("Estado: " + pedido.getEstado());
        holder.tvFecha.setText(pedido.getFecha());

        // Los botones siempre son visibles
        holder.btnAceptar.setVisibility(View.VISIBLE);
        holder.btnRechazar.setVisibility(View.VISIBLE);

        // Deshabilita el boton clickeado anteriormente, osea si ya está en ese estado
        holder.btnAceptar.setEnabled(!"Aceptado".equalsIgnoreCase(pedido.getEstado()));
        holder.btnRechazar.setEnabled(!"Rechazado".equalsIgnoreCase(pedido.getEstado()));

        // Un cartelito de confirmación antes de hacer la accion
        holder.btnAceptar.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Confirmar acción")
                    .setMessage("¿Seguro que deseas marcar este pedido como ACEPTADO?")
                    .setPositiveButton("Sí", (dialog, which) -> listener.onAceptar(pedido))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        holder.btnRechazar.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Confirmar acción")
                    .setMessage("¿Seguro que deseas marcar este pedido como RECHAZADO?")
                    .setPositiveButton("Sí", (dialog, which) -> listener.onRechazar(pedido))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public void setPedidos(List<Pedido> nuevos) {
        pedidos = nuevos;
        notifyDataSetChanged();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripcion, tvEstado, tvFecha;
        Button btnAceptar, btnRechazar;
        PedidoViewHolder(View v) {
            super(v);
            tvDescripcion = v.findViewById(R.id.tvDescripcionPedido);
            tvEstado = v.findViewById(R.id.tvEstadoPedido);
            tvFecha = v.findViewById(R.id.tvFechaPedido);
            btnAceptar = v.findViewById(R.id.btnAceptar);
            btnRechazar = v.findViewById(R.id.btnRechazar);
        }
    }
}
