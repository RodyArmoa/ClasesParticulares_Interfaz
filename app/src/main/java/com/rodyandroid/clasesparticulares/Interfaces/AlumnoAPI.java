package com.rodyandroid.clasesparticulares.Interfaces;

import com.rodyandroid.clasesparticulares.Login.AlumnoLoginRequest;
import com.rodyandroid.clasesparticulares.Model.Alumno;

import retrofit2.http.Body;
import retrofit2.Call;
import retrofit2.http.POST;

public interface AlumnoAPI {

    @POST("/alumnos")
    public Call<Alumno>  crearAlumno(@Body Alumno alumno);

    @POST("/alumnos/login") // Endpoint para iniciar sesión
    Call<Long> iniciarSesion(@Body AlumnoLoginRequest alumno);
}
