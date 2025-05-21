package com.example.reefood.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reefood.R;
import com.example.reefood.model.Donaciones;

import java.util.List;

public class DonacionAdapter extends RecyclerView.Adapter<DonacionAdapter.ViewHolder> {

    private List<Donaciones> donaciones;
    private final Context context;
    private OnItemClickListener listener;

    // Interface for click listener
    public interface OnItemClickListener {
        void onItemClick(Donaciones donacion);
    }

    public DonacionAdapter(List<Donaciones> donaciones, Context context) {
        this.donaciones = donaciones;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.publicacion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donaciones donacion = donaciones.get(position);

        // Mostrar datos básicos
        holder.nombreDonante.setText(donacion.getNombre());
        holder.descripcionDonacion.setText(donacion.getDescripcion());

        // Configurar click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(donacion);
            } else {
                // Mantener compatibilidad con implementación anterior
                Intent intent = new Intent(context, VerDonacion.class);
                intent.putExtra("donacion", donacion);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donaciones != null ? donaciones.size() : 0;
    }

    public void actualizarDonaciones(List<Donaciones> nuevasDonaciones) {
        this.donaciones = nuevasDonaciones;
        notifyDataSetChanged();
    }

    // También añadir un setter para la implementación en Publicaciones.java
    public void setDonaciones(List<Donaciones> donaciones) {
        this.donaciones = donaciones;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreDonante;
        TextView descripcionDonacion;
        ImageView imgAlimento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreDonante = itemView.findViewById(R.id.nombreDonante);
            descripcionDonacion = itemView.findViewById(R.id.descripcionDonacion);
            imgAlimento = itemView.findViewById(R.id.imgalimento);
        }
    }
}