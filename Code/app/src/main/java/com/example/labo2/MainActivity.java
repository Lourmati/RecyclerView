package com.example.labo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {
    ConstraintLayout layout;
    private Snackbar snackbar;
    private ArrayList<Client> list;
    private RecyclerView recyclerView;
    private ClientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Client clientUndo;
    private int clientEdite;


    Comparator<Client> compareById = new Comparator<Client>() {
        @Override
        public int compare(Client c1, Client c2) {
            return c1.getNom().compareTo(c2.getNom());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);

        loadData();
        buildRecycleView();

        snackbar = Snackbar.make(layout, R.string.title, Snackbar.LENGTH_LONG);
        snackbar.setAction("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo();
            }
        });
    }

    private void buildRecycleView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ClientAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ClientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                notifyItemSelected(position);
            }

            @Override
            public void onDeleteClick(int position) {
                notifyDeleteSelected(position);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (ClientAdapter.FONCTION.equals("EDIT")) {
                    Client clientEdite = (Client) data.getSerializableExtra("CLIENTEDIT");
                    list.get(this.clientEdite).setNomDeFamille(clientEdite.getNom());
                    list.get(this.clientEdite).setPrenom(clientEdite.getPrenom());
                    list.get(this.clientEdite).setIdImage(clientEdite.getImage());
                    ClientAdapter.FONCTION = "";

                } if(ClientAdapter.FONCTION.equals("ADD")) {
                    list.add((Client) data.getSerializableExtra("CLIENT"));
                }
            }
        }

        Collections.sort(list, compareById);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Collections.sort(list, compareById);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                openActivityAdd();
                ClientAdapter.FONCTION = "ADD";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Client>>(){}.getType();
        list = gson.fromJson(json, type);

        if(list == null){
            list = new ArrayList<>();
            list.add(new Client("Lannister", "Tyrion", R.drawable.tyrion));
            list.add(new Client("Snow", "Jon", R.drawable.jonsnow));
            list.add(new Client("The Night King", "The Night King", R.drawable.nightking));
            list.add(new Client("Stark", "Sansa", R.drawable.sansa));
            list.add(new Client("Targaryen", "Daenerys", R.drawable.daenerys));

            Collections.sort(list, compareById);
        }
    }

    private void openActivityAdd() {
        Intent intent = new Intent(this, AcitivityAddClient.class);
        startActivityForResult(intent, 1);
    }

    private void undo() {
        list.add(clientUndo);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Collections.sort(list, compareById);
    }

    public void notifyItemSelected(int position) {
        Log.v("RecyclerView", "" + position + " a été sélectionné: " + list.get(position).getNom());
        clientEdite = position;
        if (ClientAdapter.FONCTION.equals("EDIT")) {
            openActivityEdit(position);
        }

    }

    private void openActivityEdit(int position) {
        Intent i2 = new Intent(this, AcitivityAddClient.class);
        i2.putExtra("nomEdit", list.get(position).getNom());
        i2.putExtra("prenomEdit", list.get(position).getPrenom());
        i2.putExtra("imageEdit", list.get(position).getImage());
        startActivityForResult(i2, 1);
    }

    public void notifyDeleteSelected(int position) {
        Log.v("RecyclerView delete", "" + position + " a été sélectionné: " + list.get(position).getNom());
        removeItem(position);
    }

    public void removeItem(final int position) {

        AlertDialog alertDialog = new AlertDialog.Builder
                (this).create();
        alertDialog.setTitle
                ("Suppression");

        alertDialog.setMessage
                ("Voulez vous vraiment effacer cet utilisateur ?");
        alertDialog.setButton
                (AlertDialog.BUTTON_POSITIVE, ("Oui"),
                        new DialogInterface.OnClickListener() {
                            public void onClick
                                    (DialogInterface arg0, int arg1) {
                                clientUndo = list.get(position);
                                list.remove(position);
                                adapter.notifyItemRemoved(position);
                                snackbar.show();
                            }
                        }
                );
        alertDialog.setButton
                (AlertDialog.BUTTON_NEGATIVE, ("Non"),
                        new DialogInterface.OnClickListener() {
                            public void onClick
                                    (DialogInterface arg0, int arg1) {
                            }
                        }
                );
        alertDialog.show();
        Collections.sort(list, compareById);
    }


}