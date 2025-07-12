package com.example.techsolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;

    public PedidoAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.tvDescripcion.setText(pedido.getDescripcion());
        holder.tvEstado.setText("Estado: " + pedido.getEstado());
        holder.tvFecha.setText(pedido.getFecha());
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
        PedidoViewHolder(View v) {
            super(v);
            tvDescripcion = v.findViewById(R.id.tvDescripcionPedido);
            tvEstado = v.findViewById(R.id.tvEstadoPedido);
            tvFecha = v.findViewById(R.id.tvFechaPedido);
        }
    }
}
