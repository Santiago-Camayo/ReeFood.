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

/**
 * Adaptador para mostrar una lista de donaciones en un RecyclerView.
 * Esta clase se encarga de crear las vistas para cada ítem de la lista (donación)
 * y de vincular los datos de cada donación a su vista correspondiente.
 * También gestiona los clics en los ítems, permitiendo una acción personalizada
 * a través de la interfaz {@link OnItemClickListener}.
 */
public class DonacionAdapter extends RecyclerView.Adapter<DonacionAdapter.ViewHolder> {

    // Lista de objetos Registro_Donaciones que se mostrarán.
    private List<Registro_Donaciones> donaciones;
    // Contexto de la aplicación, necesario para inflar vistas y iniciar actividades.
    private final Context context;
    // Listener para manejar los clics en los ítems de la lista.
    private OnItemClickListener listener;

    /**
     * Interfaz para definir el comportamiento cuando se hace clic en un ítem de la lista.
     * La actividad o fragmento que utilice este adaptador deberá implementar esta interfaz.
     */
    public interface OnItemClickListener {
        /**
         * Método llamado cuando se hace clic en un ítem.
         * @param donacion El objeto {@link Registro_Donaciones} correspondiente al ítem clickeado.
         */
        void onItemClick(Registro_Donaciones donacion);
    }

    /**
     * Constructor del adaptador.
     * @param donaciones Lista inicial de donaciones a mostrar.
     * @param context Contexto de la aplicación.
     */
    public DonacionAdapter(List<Registro_Donaciones> donaciones, Context context) {
        this.donaciones = donaciones;
        this.context = context;
    }

    /**
     * Establece el listener que se activará cuando se haga clic en un ítem.
     * @param listener La implementación de {@link OnItemClickListener}.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Se llama cuando RecyclerView necesita crear un nuevo {@link ViewHolder} para representar un ítem.
     * Infla el layout del ítem (publicacion_item.xml) y crea una instancia de ViewHolder.
     * @param parent El ViewGroup al que se añadirá la nueva vista después de ser vinculada a una posición del adaptador.
     * @param viewType El tipo de vista del nuevo View.
     * @return Un nuevo ViewHolder que contiene la Vista para cada ítem.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout XML 'publicacion_item.xml' para crear la vista de un ítem.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.publicacion_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Se llama para mostrar los datos en la posición especificada.
     * Este método actualiza el contenido del {@link ViewHolder#itemView} para reflejar el ítem en la posición dada.
     * @param holder El ViewHolder que debe ser actualizado para representar el contenido del ítem en la posición dada en el conjunto de datos.
     * @param position La posición del ítem dentro del conjunto de datos del adaptador.
     */
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
    /**
     * Devuelve el número total de ítems en el conjunto de datos que tiene el adaptador.
     * @return El número total de ítems. Si la lista 'donaciones' es null, devuelve 0.
     */
    public int getItemCount() {
        return donaciones != null ? donaciones.size() : 0;
    }

    /**
     * Actualiza la lista de donaciones del adaptador y notifica al RecyclerView
     * que los datos han cambiado, para que pueda redibujar la lista.
     * @param nuevasDonaciones La nueva lista de donaciones.
     */
    public void actualizarDonaciones(List<Registro_Donaciones> nuevasDonaciones) {
        this.donaciones = nuevasDonaciones;
        // Notifica al observador adjunto que el conjunto de datos subyacente ha cambiado.
        notifyDataSetChanged();
    }

    /**
     * Setter para la lista de donaciones. Similar a {@link #actualizarDonaciones(List)},
     * pero con un nombre más genérico de setter.
     * Útil para la implementación en clases como Publicaciones.java.
     * @param donaciones La nueva lista de donaciones.
     */
    public void setDonaciones(List<Registro_Donaciones> donaciones) {
        this.donaciones = donaciones;
        notifyDataSetChanged();
    }

    /**
     * Clase interna ViewHolder que describe la vista de un ítem y los metadatos sobre su lugar dentro del RecyclerView.
     * Contiene las referencias a los elementos de la interfaz de usuario (TextViews, ImageView) dentro de cada ítem.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los componentes de la UI del layout 'publicacion_item.xml'.
        TextView nombreDonante;
        TextView descripcionDonacion;
        ImageView imgAlimento;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista raíz del ítem (inflada desde publicacion_item.xml).
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las vistas encontrándolas por su ID dentro del itemView.
            nombreDonante = itemView.findViewById(R.id.nombreDonante);
            descripcionDonacion = itemView.findViewById(R.id.descripcionDonacion);
            imgAlimento = itemView.findViewById(R.id.imgalimento);
        }
    }
}