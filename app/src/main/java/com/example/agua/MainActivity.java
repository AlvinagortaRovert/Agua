package com.example.agua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/* private TextView  */
public class MainActivity extends AppCompatActivity {

    EditText edUsusaio,edPassword, edtdatos;
    TextView RecuperarC,iniciarsesion,registrarse;
    Button btlogin, btnregsitro;
    String usuario, password,  nom, ape, datos;
    CheckBox checkBox;
    public ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //CREAR LOS CONTROLES DEL LAYOT
        edUsusaio=findViewById(R.id.edtUsuario);
        edPassword=findViewById(R.id.edtPassword);
       // btlogin=findViewById(R.id.btnLogin);
       // btnregsitro=findViewById(R.id.btnLogin2);
        RecuperarC=findViewById(R.id.textView12);
        imageView2 = findViewById(R.id.imageView);
          iniciarsesion=(TextView)findViewById(R.id.textview_iniciar_sesion);
        registrarse=(TextView)findViewById(R.id.textview_crear_cuenta);

        ape="";
        nom="";

        datos="";
        RecuperarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(MainActivity.this, "Ingresando a recuperar contraseña",  Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(getApplicationContext(), RecuperaContra.class);
                //Intent intent = new Intent(getApplicationContext(),Segundo_activity.class);
                startActivity(intent);

            }
        });
        //  edtdatos.setText(datos.toString());
        checkBox= findViewById(R.id.checkBoxrecuedame);
        recuperarprerferencias();
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), Registro1.class);
                //Intent intent = new Intent(getApplicationContext(),Segundo_activity.class);
                startActivity(intent);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Aviso");
                    builder.setMessage("Usted  ah marcado:\n guardar inicio de sesion");
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        });
        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario=edUsusaio.getText().toString();
                password= edPassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()){

                    validar_usuario("https://aguapotable.app-organizadoor.com/validar_usuario.php");
                    //   validar_usuario("http://10.21.1.15:8080/developeru/validar_usuario.php");

                }else
                {Toast.makeText(MainActivity.this, "no se apatan campos vacios", Toast.LENGTH_SHORT).show();}

            }
        });



    }

    public void onImageLoaded(Bitmap imageBitmap) {
        if (imageView2 != null && imageBitmap != null) {
            imageView2.setImageBitmap(imageBitmap);
        }
    }






    public void updateImageView(Bitmap selectedImageBitmap) {
        if (selectedImageBitmap != null) {
            imageView2.setImageBitmap(selectedImageBitmap);
        }
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

                    datos=ape+""+nom;
                    //  edtdatos.setText(datos);
                    //edtdatos.setText(ape+" "+nom);
                    if(checkBox.isChecked()){

                        guardar();

                    }

                    Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                    String dato = usuario;
                    //Intent  intent= new Intent(getApplicationContext(), activity_principal.class);
                    // Intent  intent= new Intent(getApplicationContext(), Registro.class);
                    Intent intent = new Intent(getApplicationContext(),Menuprincipal.class);
                    intent.putExtra("dato", dato);
                    intent.putExtra("dato2", ape+" "+nom);
                    startActivity(intent);

                    //  finish();

                }else{
                    ColorStateList colorStateList2 = ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                    Toast.makeText(MainActivity.this, "usuario o contraseña incorecto",  Toast.LENGTH_SHORT).show();
                    RecuperarC.setTextColor(colorStateList2);

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse == null) {
                    if(error.getClass().equals(TimeoutError.class)){
                        Toast.makeText( MainActivity.this,
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();

                    }

                }
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        }
        ){
            @Override

            protected  Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                /*parametro antiguos de abajo
                parametros.put("usuario",edUsusaio.getText().toString());
                parametros.put("password",edPassword.getText().toString());

                */
                parametros.put("usuario",usuario);
                parametros.put("password",password);
                // return super.getParams();
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    public void siguiente(View view){

        //   Intent siguiente = new Intent( this,Segundo_activity.class );
        //    startActivity(siguiente);

        validar_usuario("https://aguapotable.app-organizadoor.com/validar_usuario.php");

    }




    private void guardar(){
        SharedPreferences preferencias=getSharedPreferences("prefereciaslogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("datos", datos);
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.putBoolean("sesion", true);
        editor.commit();

    }

    private void recuperarprerferencias(){

        SharedPreferences preferences =getSharedPreferences("preferecniasLogin", Context.MODE_PRIVATE);

        edUsusaio.setText(preferences.getString("usuario", ""));
        edPassword.setText(preferences.getString("password", ""));

    }
}