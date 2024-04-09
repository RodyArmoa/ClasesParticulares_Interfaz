package com.rodyandroid.clasesparticulares;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rodyandroid.clasesparticulares.Interfaces.FavoritosAPI;
import com.rodyandroid.clasesparticulares.Interfaces.ProfesorAPI;
import com.rodyandroid.clasesparticulares.Login.LoginActivity;
import com.rodyandroid.clasesparticulares.Model.Profesor;

import java.util.ArrayList;
import java.util.List;

import BasesDeDatosLocal.DBHelper;
import Utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritosActivity extends AppCompatActivity {

    BottomNavigationView mbottonnavigation;

    List<Profesor> listaProfesor;
    ListaProfesorAdapter listaProfesorAdapter;

    RetrofitClient retrofitClient;
    private Long alumnoId;

    ListaProfeActivity.RetrofitClientfavorito retrofitClientfavorito;

    private DBHelper dbHelper;


    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        View listElementView = getLayoutInflater().inflate(R.layout.list_element, null);

        //Obtenemos el ID del alumno que ha iniciado session
        alumnoId = obtenerIdAlumno();

        listaProfesor = new ArrayList<>();
        listaProfesorAdapter = new ListaProfesorAdapter(listaProfesor, this);
        RecyclerView recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaProfesorAdapter);


        //Inicio de menu navegaciion

        mbottonnavigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        mbottonnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.menu_nav_perfil){

                }
                if (menuItem.getItemId() == R.id.menu_nav_favorito){

                }
                if (menuItem.getItemId() == R.id.menu_nav_inicio){

                    Intent intent = new Intent(FavoritosActivity.this, ListaProfeActivity.class);
                    startActivity(intent);
                    finish();

                }


                return true;
            }
        });
        obtenerProfesoresFavoritos(alumnoId);
        //Fin de menu navegaciion
    }

    //Inicio de la llamada de listar a profesoresfavoritos

    private void obtenerProfesoresFavoritos(Long alumnoId) {
        // Inicializa Retrofit y crea la interfaz de la API
        RetrofitClient retrofitClient = new RetrofitClient();
        FavoritosAPI apiInterface = retrofitClient.getRetrofitInstance().create(FavoritosAPI.class);

        // Realiza la solicitud GET para obtener los profesores favoritos
        Call<List<Profesor>> call = apiInterface.obtenerProfesoresFavoritos(alumnoId);
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProfesor.clear();
                    listaProfesor.addAll(response.body());
                    listaProfesorAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FavoritosActivity.this, "Error al obtener los profesores favoritos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Toast.makeText(FavoritosActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    public class RetrofitClient {
        private static final String BASE_URL = "http://192.168.1.130:8082/";

        private Retrofit retrofit = null;

        public Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    private Long obtenerIdAlumno() {
        return SharedPreferencesUtils.obtenerIdAlumno(this);
    }



}