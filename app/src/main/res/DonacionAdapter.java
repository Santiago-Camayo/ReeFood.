package com.example.refood;

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

import java.util.List;

// Adaptador para mostrar las donaciones en un RecyclerView
public class DonacionAdapter extends RecyclerView.Adapter<DonacionAdapter.ViewHolder> {

    private List<Donacion> donaciones;  // Lista de donaciones a mostrar
    private Context context;            // Contexto para acceder a recursos

    // Constructor que recibe la lista de donaciones y el contexto
    public DonacionAdapter(List<Donacion> donaciones, Context context) {
        this.donaciones = donaciones;
        this.context = context;
    }

    // Crea una nueva vista (invocado por el layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout publicacion_item.xml para cada elemento
        View view = LayoutInflater.from(context).inflate(R.layout.publicacion_item, parent, false);
        return new ViewHolder(view);
    }

    // Configura el contenido de cada vista con los datos de la donación
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener la donación en la posición actual
        Donacion donacion = donaciones.get(position);

        // Mostrar los datos básicos en la tarjeta
        holder.nombreDonante.setText(donacion.getNombreDonante());
        holder.descripcionDonacion.setText(donacion.getDescripcion());

        // Cargar imagen si está disponible
        if (donacion.getImagenUrl() != null && !donacion.getImagenUrl().isEmpty()) {
            Glide.with(context)
                    .load(donacion.getImagenUrl())
                    .apply(new RequestOptions().centerCrop())
                    .placeholder(R.drawable.noimagen)
                    .into(holder.imgAlimento);
        } else {
            // Si no hay imagen, mostrar imagen por defecto
            holder.imgAlimento.setImageResource(R.drawable.noimagen);
        }

        // Configurar el evento de clic para abrir la vista detallada
        holder.itemView.setOnClickListener(v -> {
            // Crear intent para abrir la actividad de detalles
            Intent intent = new Intent(context, VerDonacion.class);

            // Pasar todos los datos de la donación a la actividad de detalles
            intent.putExtra("nombre", donacion.getNombreDonante());
            intent.putExtra("contacto", donacion.getContacto());
            intent.putExtra("descripcion", donacion.getDescripcion());
            intent.putExtra("nota", donacion.getNota());
            intent.putExtra("metodo", donacion.getMetodoEntrega());



            // Iniciar la actividad de detalle
            context.startActivity(intent);
        });
    }

    // Retorna la cantidad de elementos en la lista
    @Override
    public int getItemCount() {
        return donaciones.size();
    }

    // Actualiza la lista de donaciones con nuevos datos
    public void actualizarDonaciones(List<Donacion> nuevasDonaciones) {
        this.donaciones = nuevasDonaciones;
        notifyDataSetChanged();
    }

    // Clase ViewHolder que contiene referencias a las vistas de cada elemento
    // Mantiene las referencias para evitar llamadas repetidas a findViewById
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias a las vistas del layout publicacion_item.xml
        TextView nombreDonante, descripcionDonacion;
        ImageView imgAlimento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Obtener referencias a las vistas
            nombreDonante = itemView.findViewById(R.id.nombreDonante);
            descripcionDonacion = itemView.findViewById(R.id.descripcionDonacion);
            imgAlimento = itemView.findViewById(R.id.imgalimento);
        }
    }
}