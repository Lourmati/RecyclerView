package com.example.labo2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    private ArrayList<Client> list;
    private OnItemClickListener listener;
    public static String FONCTION = "";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNom;
        public TextView tvPrenom;
        public ImageView ivPhoto;
        public ImageView ivEtoile;
        public ImageView ivEdit;
        public ImageView ivEffacer;
        public boolean isAppreciated = false;


        public ClientViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            tvNom = view.findViewById(R.id.nomDeFamille);
            tvPrenom = view.findViewById(R.id.prenom);
            ivPhoto = view.findViewById(R.id.imageView);
            ivEtoile = view.findViewById(R.id.appreciation);
            ivEdit = view.findViewById(R.id.edit);
            ivEffacer = view.findViewById(R.id.delete);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // vérifier que le listener n'est pas null
                    if (listener != null) {
                        int position = getAdapterPosition();
                        // la position est valide?
                        if (position != RecyclerView.NO_POSITION) {
                            //listener.onItemClick(position);
                        }
                    }
                }
            });
            ivEffacer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // vérifier que le listener n'est pas null
                    if (listener != null) {
                        int position = getAdapterPosition();
                        // la position est valide?
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // vérifier que le listener n'est pas null
                    if (listener != null) {
                        int position = getAdapterPosition();
                        // la position est valide?
                        if (position != RecyclerView.NO_POSITION) {
                            FONCTION = "EDIT";
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            ivEtoile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isAppreciated = !isAppreciated;
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (isAppreciated) {
                                ivEtoile.setImageResource(R.drawable.ic_star);

                            }
                            else {
                                ivEtoile.setImageResource(R.drawable.ic_star_border);
                            }
                        }
                    }

                }
            });
        }
    }

    public ClientAdapter(ArrayList<Client> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ClientViewHolder ivh = new ClientViewHolder(v, listener);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = list.get(position);
        holder.tvNom.setText(client.getNom());
        holder.tvPrenom.setText(client.getPrenom());
        holder.ivPhoto.setImageResource(client.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
