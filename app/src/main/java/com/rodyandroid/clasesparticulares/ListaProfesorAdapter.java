package com.rodyandroid.clasesparticulares;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodyandroid.clasesparticulares.Model.Profesor;
import com.rodyandroid.clasesparticulares.R;

import java.util.List;

public class ListaProfesorAdapter extends RecyclerView.Adapter<ListaProfesorAdapter.ViewHolder> {
    private List<Profesor> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mListener;

    public ListaProfesorAdapter(List<Profesor> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profesor item = mData.get(position);
        holder.bindData(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION && clickedPosition < mData.size()) {
                    // Verificar si el listener no es nulo y la posición es válida
                    if (mListener != null) {
                        mListener.onItemClick(mData.get(clickedPosition));
                    }
                }
            }
        });

        holder.imageButtonFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION && clickedPosition < mData.size()) {
                    // Verificar si el listener de favoritos no es nulo y la posición es válida
                    if (mFavoritosListener != null) {
                        mFavoritosListener.onFavoritosClick(mData.get(clickedPosition));
                    }
                }
            }
        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Obtener el profesor correspondiente a esta posición
                Profesor profesor = mData.get(holder.getAdapterPosition());

                // Crear un Intent para abrir la actividad del perfil del profesor
                Intent intent = new Intent(mContext, PerfilProfesorActivity.class);

                // Pasar información adicional sobre el profesor si es necesario
                intent.putExtra("profesor_id", profesor.getId());
                intent.putExtra("profesor_nombre", profesor.getNombre());
                // Agregar más datos según sea necesario

                // Iniciar la actividad del perfil del profesor
                mContext.startActivity(intent);

                // Devolver true para indicar que se ha manejado el evento de long click
                return true;
            }
        });
    }

    // Método para actualizar la lista de profesores
    public void setProfesores(List<Profesor> profesores) {
        mData = profesores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnProfesorFavoritoClickListener(ListaProfeActivity listaProfeActivity) {

    }



    public interface OnItemClickListener {
        void onItemClick(Profesor profesor);
    }
    public interface OnFavoritosClickListener {
        void onFavoritosClick(Profesor profesor);
    }
    private OnFavoritosClickListener mFavoritosListener;

    public void setOnFavoritosClickListener(OnFavoritosClickListener listener) {
        this.mFavoritosListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nombre, ubicacion, asignatura, contacto;
        ImageButton imageButtonFavoritos;

        ViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombreTextView);
            ubicacion = itemView.findViewById(R.id.ubicacionTextView);
            asignatura = itemView.findViewById(R.id.asignaturaTexView);
            contacto = itemView.findViewById(R.id.contactoTextView);
            imageButtonFavoritos = itemView.findViewById(R.id.imageButtonFavoritos);

        }

        void bindData(final Profesor item) {
            // Bind data to views
            nombre.setText(item.getNombre());
            ubicacion.setText(item.getUbicacion());
            asignatura.setText(item.getAsignaturas());
            contacto.setText(item.getTelefono());
        }
    }
}
