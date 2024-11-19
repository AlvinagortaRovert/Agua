package com.example.agua;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agua.databinding.ActivityMenuprincipalBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Menuprincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuprincipalBinding binding;
    private NavController navController;


    public TextView correo,usuarioTextView, correoTextView ;
    String Usuario, password,  nom, ape;
    Button btpregunta;
    private static final int REQUEST_IMAGE_CAPTURE = 1;



    Switch simpleSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuprincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setContentView(binding.getRoot());

        CargarDato();

        setSupportActionBar(binding.appBarMenuprincipal.toolbar);
        binding.appBarMenuprincipal.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.link/6e2li0"));
                startActivity(intent);

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.vigilancia_agua, R.id.nav_gallery, R.id.nav_slideshow,R.id.salir, R.id.acerca,R.id.datossec,R.id.antecedentes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menuprincipal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    public void saveChanges() {
        // getSupportFragmentManager().beginTransaction().commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null) {
                Bundle savedState = new Bundle();
                fragment.onSaveInstanceState(savedState); // guardar el estado actual del fragmento
                fragmentTransaction.addToBackStack(null); // agregar a la pila de retroceso
                fragmentTransaction.commit(); // guardar los cambios
            }
        }
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    /*    switch (item.getItemId()) {


            case R.id.action_exit:
                AlertDialog.Builder mensaje = new AlertDialog.Builder(Menuprincipal.this);
                mensaje.setMessage("Realmente quiere salir").setCancelable(false);
                mensaje.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity(); // Cierra todas las actividades abiertas
                        System.exit(0);
                    }
                });
                mensaje.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = mensaje.create();
                alert.setTitle("Aviso del sistema");
                alert.show();

                return true;
            case R.id.action_about:
                navController.navigate(R.id.acerca);


                return  true;

        }
*/        return super.onOptionsItemSelected(item);
    }


    private void CargarDato(){
        Persona persona = new Persona();

        // Obtener el Intent que inició este Activity
        Intent intent = getIntent();

// Obtener el dato del Intent
        String dato = intent.getStringExtra("dato");
        NavigationView  navigationView2 = findViewById(R.id.nav_view);
        View headerView = navigationView2.getHeaderView(0);
        usuarioTextView = headerView.findViewById(R.id.txtemail);
        TextView correoTextView = headerView.findViewById(R.id.txtnombre);

        usuarioTextView.setText(dato);
        String dato2 = intent.getStringExtra("dato2");
        try {
            if (dato2 != null && !dato2.isEmpty()) {

                correoTextView.setText(dato2);

            } else {


                //   Toast.makeText(Menuprincipal.this, "No hay datos en el nombre",  Toast.LENGTH_SHORT).show();
                Usuario=dato;
                validar_usuario("https://admin.marcaancash.com/api/get_nomuser.php");
                correoTextView.setText(persona.getNombreCompleto());

                //  intent.putExtra("dato2", persona.getNombreCompleto());
            }
        } catch (Exception e) {
            Log.e("Menuprincipal", "Ha ocurrido un error: " + e.getMessage(), e);
        }






    }



    public void salir(View view){

        AlertDialog.Builder mensaje = new AlertDialog.Builder(Menuprincipal.this);
        mensaje.setMessage("Realmente quiere salir").setCancelable(false);
        mensaje.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity(); // Cierra todas las actividades abiertas
                System.exit(0);
            }
        });
        mensaje.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = mensaje.create();
        alert.setTitle("Aviso del sistema");
        alert.show();

    }
    public void logout(){
        AlertDialog.Builder alerta =new AlertDialog.Builder(Menuprincipal.this);
        alerta.setTitle("Aviso");
        alerta.setMessage("deseas cerrar sesión ")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // this.overridePendingTransition(R.anim);
    }


    public void onclicked(View view){
        if (simpleSwitch.isChecked()) {
            Boolean switchState = simpleSwitch.isChecked();
            Toast.makeText(Menuprincipal.this, "no se aceptan campos vacios1", Toast.LENGTH_SHORT).show();
        }
        else
        { Toast.makeText(Menuprincipal.this, "no se aceptan campos vacios", Toast.LENGTH_SHORT).show();}


    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menuprincipal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("usuario obtendio", "usuario obtenido" +ape+" "+nom);


                    NavigationView  navigationView2 = findViewById(R.id.nav_view);
                    View headerView = navigationView2.getHeaderView(0);

                    TextView correoTextView = headerView.findViewById(R.id.txtnombre);
                    correoTextView.setText(ape+" "+nom);



                    Persona persona = new Persona();

                    persona.setNombreCompleto(ape+" "+nom);



                }else{

                    //  Toast.makeText(SplashActivity2.this, "usuario o contraseÑA INCORRECTO",  Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse == null) {
                    if(error.getClass().equals(TimeoutError.class)){
                        Toast.makeText( Menuprincipal.this,
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();

                    }

                }
                Toast.makeText(Menuprincipal.this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        }
        ){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("usuario",Usuario);

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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menuprincipal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    */
}