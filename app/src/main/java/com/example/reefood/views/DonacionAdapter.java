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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.reefood.R;
import com.example.reefood.model.Donacion;

import java.util.List;


public class DonacionAdapter extends RecyclerView.Adapter<DonacionAdapter.ViewHolder> {

    private List<Donacion> donaciones;
    private Context context;


    public DonacionAdapter(List<Donacion> donaciones, Context context) {
        this.donaciones = donaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publicacion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donacion donacion = donaciones.get(position);

        holder.nombreDonante.setText(donacion.getNombreDonante());
        holder.descripcionDonacion.setText(donacion.getDescripcion());

        if (donacion.getImagenUrl() != null && !donacion.getImagenUrl().isEmpty()) {
            Glide.with(context)
                    .load(donacion.getImagenUrl())
                    .apply(new RequestOptions().centerCrop())
                    .placeholder(R.drawable.noimagen)
                    .into(holder.imgAlimento);
        } else {
            holder.imgAlimento.setImageResource(R.drawable.noimagen);
        }


        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, VerDonacion.class);

            intent.putExtra("nombre", donacion.getNombreDonante());
            intent.putExtra("contacto", donacion.getContacto());
            intent.putExtra("descripcion", donacion.getDescripcion());
            intent.putExtra("nota", donacion.getNota());
            intent.putExtra("metodo", donacion.getMetodoEntrega());

            if (donacion.getId() != null) {
                intent.putExtra("id", donacion.getId());
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return donaciones.size();
    }

    public void actualizarDonaciones(List<Donacion> nuevasDonaciones) {
        this.donaciones = nuevasDonaciones;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreDonante, descripcionDonacion;
        ImageView imgAlimento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreDonante = itemView.findViewById(R.id.nombreDonante);
            descripcionDonacion = itemView.findViewById(R.id.descripcionDonacion);
            imgAlimento = itemView.findViewById(R.id.imgalimento);
        }
    }
}