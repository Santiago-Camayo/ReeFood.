package com.example.reefood.controller;

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
import com.example.reefood.model.Registro_Donaciones;

import java.util.List;


public class DonacionAdapter extends RecyclerView.Adapter<DonacionAdapter.ViewHolder> {

    // Lista de objetos Registro_Donaciones que se mostrarán.
    private List<Registro_Donaciones> donaciones;
    // Contexto de la aplicación, necesario para inflar vistas y iniciar actividades.
    private final Context context;
    // Listener para manejar los clics en los ítems de la lista.
    private OnItemClickListener listener;



    public interface OnItemClickListener {

        void onItemClick(Registro_Donaciones donacion);
    }


    public DonacionAdapter(List<Registro_Donaciones> donaciones, Context context) {
        this.donaciones = donaciones;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout XML 'publicacion_item.xml' para crear la vista de un ítem.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.publicacion_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtiene la donación actual de la lista según la posición.
        Registro_Donaciones donacion = donaciones.get(position);

        // Asigna los datos de la donación a los TextViews del ViewHolder.
        holder.nombreDonante.setText(donacion.getNombre());
        holder.descripcionDonacion.setText(donacion.getDescripcion());

        // Configura el OnClickListener para el itemView (la vista completa del ítem).
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // Si se ha establecido un listener personalizado, se llama a su método onItemClick.
                listener.onItemClick(donacion);
            } else {
                // Comportamiento por defecto si no hay listener: abrir la actividad VerDonacion.
                // Esto mantiene la compatibilidad con implementaciones anteriores que no usaban el listener.
                Intent intent = new Intent(context, VerDonacion.class);
                // Pasa el objeto 'donacion' a la actividad VerDonacion.
                intent.putExtra("donacion", donacion);
                context.startActivity(intent);
            }
        });
    }

    @Override

    public int getItemCount() {
        return donaciones != null ? donaciones.size() : 0;
    }

    public void actualizarDonaciones(List<Registro_Donaciones> nuevasDonaciones) {
        this.donaciones = nuevasDonaciones;
        // Notifica al observador adjunto que el conjunto de datos subyacente ha cambiado.
        notifyDataSetChanged();
    }


    public void setDonaciones(List<Registro_Donaciones> donaciones) {
        this.donaciones = donaciones;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los componentes de la UI del layout 'publicacion_item.xml'.
        TextView nombreDonante;
        TextView descripcionDonacion;
        ImageView imgAlimento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las vistas encontrándolas por su ID dentro del itemView.
            nombreDonante = itemView.findViewById(R.id.nombreDonante);
            descripcionDonacion = itemView.findViewById(R.id.descripcionDonacion);
            imgAlimento = itemView.findViewById(R.id.imgalimento);
        }
    }
}