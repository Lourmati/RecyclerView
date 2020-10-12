package com.example.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AcitivityAddClient extends AppCompatActivity {
    private Button buttonTermine;
    private ArrayList<CheckBox> listC;
    private CheckBox checkBoxJon;
    private CheckBox checkBoxTyrion;
    private CheckBox checkBoxSansa;
    private CheckBox checkBoxDany;
    private CheckBox checkBoxTnk;
    private EditText edittext1;
    private EditText edittext2;
    private int idImage;
    private Client clientB;
    Intent i = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        buttonTermine = findViewById(R.id.buttonTermine);
        buttonTermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        edittext1 = (EditText) findViewById(R.id.editText1);
        edittext2 = (EditText) findViewById(R.id.editText2);

        checkBoxJon = (CheckBox) findViewById(R.id.checkBoxJon);
        checkBoxTyrion = (CheckBox) findViewById(R.id.checkBoxTyrion);
        checkBoxSansa = (CheckBox) findViewById(R.id.checkBoxSansa);
        checkBoxDany = (CheckBox) findViewById(R.id.checkBoxDany);
        checkBoxTnk = (CheckBox) findViewById(R.id.checkBoxTnk);

        listC = new ArrayList<>();
        listC.add(checkBoxDany);
        listC.add(checkBoxJon);
        listC.add(checkBoxTyrion);
        listC.add(checkBoxSansa);
        listC.add(checkBoxTnk);

        if(ClientAdapter.FONCTION.equals("EDIT")){
            i = getIntent();

            edittext1.setText(i.getStringExtra("prenomEdit"));
            edittext2.setText(i.getStringExtra("nomEdit"));
            idImage = i.getIntExtra("imageEdit",0);

            switch (idImage) {
                case R.drawable.jonsnow:
                    checkBoxJon.setChecked(true);
                    break;
                case R.drawable.tyrion:
                    checkBoxTyrion.setChecked(true);
                    break;
                case R.drawable.sansa:
                    checkBoxSansa.setChecked(true);
                    break;
                case R.drawable.daenerys:
                    checkBoxDany.setChecked(true);
                    break;
                case R.drawable.nightking:
                    checkBoxTnk.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        CheckBoxManagment();
    }

    private void CheckBoxManagment() {
        for (int i = 0; i < listC.size(); i++) {
            listC.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();

                    switch (v.getId()) {
                        case R.id.checkBoxJon:
                            if (checked) {
                                idImage = R.drawable.jonsnow;
                                desactivateCheckBox(checkBoxJon);
                            }
                            break;
                        case R.id.checkBoxTyrion:
                            if (checked) {
                                idImage = R.drawable.tyrion;
                                desactivateCheckBox(checkBoxTyrion);
                            }
                            break;
                        case R.id.checkBoxSansa:
                            if (checked) {
                                idImage = R.drawable.sansa;
                                desactivateCheckBox(checkBoxSansa);
                            }
                            break;
                        case R.id.checkBoxDany:
                            if (checked) {
                                idImage = R.drawable.daenerys;
                                desactivateCheckBox(checkBoxDany);
                            }
                            break;
                        case R.id.checkBoxTnk:
                            if (checked) {
                                idImage = R.drawable.nightking;
                                desactivateCheckBox(checkBoxTnk);
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    private void openMainActivity() {

        if (edittext1.getText().toString().trim().isEmpty() || edittext2.getText().toString().trim().isEmpty()
        || verificateCheckBox()==false) {
            Toast.makeText(this, "Veuillez remplir le formulaire", Toast.LENGTH_LONG).show();
        } else {

            if(ClientAdapter.FONCTION.equals("EDIT")){
                i.putExtra("CLIENTEDIT", new Client(edittext2.getText().toString(), edittext1.getText().toString(), idImage));
            } else if(ClientAdapter.FONCTION.equals("ADD")){
                i.putExtra("CLIENT", new Client(edittext2.getText().toString(), edittext1.getText().toString(), idImage));
            }

            setResult(RESULT_OK, i);
            finish();
        }


    }

    private void desactivateCheckBox(CheckBox checkBox) {
        for (int i = 0; i < listC.size(); i++) {
            if(checkBox != listC.get(i)){
                listC.get(i).setChecked(false);
            }
        }
    }

    private boolean verificateCheckBox() {
        boolean isChecked = false;
        for (int i = 0; i < listC.size(); i++) {
            if(listC.get(i).isChecked() == true){
                isChecked = true;
            }
        }
         return isChecked;
    }
}