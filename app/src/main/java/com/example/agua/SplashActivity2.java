package com.example.agua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity2 extends AppCompatActivity {
    ProgressBar progresbar;
    String Usuario, password,  nom, ape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        progresbar=findViewById(R.id.progressBar);
        progresbar.setVisibility(View.VISIBLE);
        Persona persona = new Persona();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences= getSharedPreferences("prefereciaslogin", Context.MODE_PRIVATE);
                boolean session= preferences.getBoolean("sesion", false);
                if (session) {
                    Intent intent = new Intent(getApplicationContext(), Menuprincipal.class);
                    String usuario = preferences.getString("usuario", "usuario");
                    intent.putExtra("dato", usuario);
                    intent.putExtra("dato2", persona.getNombreCompleto());
                    startActivity(intent);
                    finish(); // Cierra la actividad actual (splash screen)
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish(); // Cierra la actividad actual (splash screen)
                }


                /*if(session){



                    Intent  intent= new Intent(getApplicationContext(), Menuprincipal.class);
                    //Intent intent = new Intent(getApplicationContext(),Segundo_activity.class);



                     String usuario= preferences.getString("usuario","usuario");

                //    Usuario=usuario;
                   // validar_usuario("https://admin.marcaancash.com/api/get_nomuser.php");
                      intent.putExtra("dato", usuario);

                    intent.putExtra("dato2", persona.getNombreCompleto());

                    startActivity(intent);
                }
                else{
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    //Intent intent = new Intent(getApplicationContext(),Segundo_activity.class);
                    startActivity(intent);


                } */
            }
        }, 500);


    }

    private  void  validar_usuario(String url){


        StringRequest stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse (String response) {

                if (!response.isEmpty()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ape= jsonObject.getString("usu_apellidos");
                        nom= jsonObject.getString("usu_nombres");
                        //     edtdatos.setText(ape+" "+nom);
                        //  datos=ape+" "+nom
                        ;

                        // edtdatos.setText(ape+" "+nom);
                        // Hacer algo con los valores obtenidos
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Persona persona = new Persona();
                    // Establecer el valor de nombreCompleto
                    persona.setNombreCompleto(ape+""+nom);

/*
                    Toast.makeText(SplashActivity2.this, "hola", Toast.LENGTH_SHORT).show();
                    String dato = usuario;
                    //Intent  intent= new Intent(getApplicationContext(), activity_principal.class);
                    // Intent  intent= new Intent(getApplicationContext(), Registro.class);
                    Intent intent = new Intent(getApplicationContext(),Menuprincipal.class);
                    intent.putExtra("dato", dato);

                    //intent.putExtra("dato2", ape+" "+nom);
                    startActivity(intent);*/

                    //  finish();

                }else{

                    Toast.makeText(SplashActivity2.this, "usuario o contraseÑA INCORRECTO",  Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse == null) {
                    if(error.getClass().equals(TimeoutError.class)){
                        Toast.makeText( SplashActivity2.this,
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();

                    }

                }
                Toast.makeText(SplashActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        }
        ){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                /*parametro antiguos de abajo
                parametros.put("usuario",edUsusaio.getText().toString());
                parametros.put("password",edPassword.getText().toString());

                */
                parametros.put("usuario",Usuario);
                parametros.put("password",password);
                // return super.getParams();
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


    public class Persona {
        private String nombreCompleto;

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }
    }

}