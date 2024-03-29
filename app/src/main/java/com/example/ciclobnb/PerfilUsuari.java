package com.example.ciclobnb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ciclobnb.BBDD.Connexions.OfereixConnection;
import com.example.ciclobnb.Objectes.Adapter.AdapterCiclo;
import com.example.ciclobnb.Objectes.Adapter.AdapterMeuCiclo;
import com.example.ciclobnb.Objectes.Ofereix;
import com.example.ciclobnb.Objectes.Usser;

import java.sql.SQLException;
import java.util.ArrayList;

public class PerfilUsuari extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Ofereix> ofereixes =  new ArrayList<>();
    Button edita, garatge,xatButton;
    TextView loginText,nomCognomsText,direccioLlogerText;
    RatingBar qualiRatingBar;
    Usser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuari);
        Bundle b =getIntent().getExtras();
        user = b.getParcelable("User");
        findComponents();
        iniciarTextView();
       /* LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, // ancho
                80 // altura en píxeles
        );
        edita.setLayoutParams(params);*/
        OfereixConnection ofereixConnection = new OfereixConnection();
        ofereixes = ofereixConnection.SearchMeues(user.getIdUser());
        RecyclerView vista = findViewById(R.id.recyclerView);
        vista.setAdapter(new AdapterMeuCiclo(ofereixes,this,user));
        vista.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findComponents(){
        loginText = findViewById(R.id.nomLogin);
        nomCognomsText = findViewById(R.id.nomCognoms);
        direccioLlogerText = findViewById(R.id.direccioLloger);
        qualiRatingBar = findViewById(R.id.cualificacio);
        xatButton=findViewById(R.id.xatsButton);
        edita=findViewById(R.id.edit);
        garatge=findViewById(R.id.botoGaratge);
    }

    @SuppressLint("SetTextI18n")
    private void iniciarTextView() {
        loginText.setText(user.getLogin());
        nomCognomsText.setText(user.getNom()+" "+user.getCognom1()+" "+user.getCognom2());
        direccioLlogerText.setText(user.getDireccio().getTipusVia() + " " + user.getDireccio().getNomCarrer() + " " + user.getDireccio().getNumero() + "-" + user.getDireccio().getPis());

        qualiRatingBar.setIsIndicator(true);
        qualiRatingBar.setRating(3.7f);
        xatButton.setOnClickListener(this);
        edita.setOnClickListener(this);
        garatge.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home :
                Intent i=new Intent(this,PrimeraPantalla.class);
                i.putExtra("User",user);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==edita){
            Intent i= new Intent(PerfilUsuari.this,CrearCompte.class);
            i.putExtra("Nueva", false);
            i.putExtra("User",user);
            startActivity(i);
        }else if(v.equals(garatge)){
            /*
            Intent i= new Intent(PerfilUsuari.this,Garatge.class);
            i.putExtra("User",user);
            startActivity(i);*/
            String missatge="Actualment sols es podrà afegir bicicletes des de un ordenador";
            AlertDialog.Builder build=new AlertDialog.Builder(this);
            build.setTitle(R.string.atencio);
            build.setMessage(missatge);
            build.setPositiveButton("Dacord", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            build.show();
        }else if(v==xatButton){
            Intent intent = new Intent(PerfilUsuari.this, XatsAmbPersones.class);
            intent.putExtra("User",user);
            startActivity(intent);
        }

    }
}