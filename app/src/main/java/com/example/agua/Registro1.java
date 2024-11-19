package com.example.agua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Registro1 extends AppCompatActivity {

        Button btnagregar;
        PieChart pieChart;
        private TextInputEditText textInputEditText;


        List<PieEntry> pietEntryList;

        EditText edUsusaio,edPassword,edPass2, nombre,apellido, reemail;
        String pw1,pw2,midni,minombre,miape,miemail;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro1);
            edPassword=findViewById(R.id.redpasw1);
            edPass2=findViewById(R.id.redpasw2);
            // dni=findViewById(R.id.redtDNI);
            nombre=findViewById(R.id.redtnombre);
            apellido=findViewById(R.id.redtapellido);
            reemail=findViewById(R.id.redemail);
            textInputEditText = findViewById(R.id.contenidodni);
            btnagregar=(Button)findViewById(R.id.rbtnreg2);
            pietEntryList=new ArrayList<>();



            pieChart=findViewById(R.id.pastel1);
            serValue();
            setUpchart();


            try{
                btnagregar.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        pw1=edPassword.getText().toString();
                        pw2=edPass2.getText().toString();
                        midni=textInputEditText.getText().toString();
                        minombre=nombre.getText().toString();
                        miape=apellido.getText().toString();
                        miemail=reemail.getText().toString();
                        boolean contieneA = miemail.contains("@");
                        ColorStateList colorStateList2 = ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.design_default_color_error));

                        if(pw1.equals(pw2)){
                            if(!midni.isEmpty() && !minombre.isEmpty()&& !miape.isEmpty()&& !miemail.isEmpty()&& !pw2.isEmpty()){
                                if(contieneA){
                                    ejecutar("https://aguapotable.app-organizadoor.com/usuario.php");

                                }


                                else{
                                    Toast.makeText(Registro1.this, "error en el correo", Toast.LENGTH_SHORT).show();
                                    reemail.setBackgroundTintList(colorStateList2);
                                    reemail.requestFocus();
                                }

                            }else
                            {Toast.makeText(Registro1.this, "no se aceptan campos vacios", Toast.LENGTH_SHORT).show();}

                        }else{


                            Toast.makeText(Registro1.this, "Las contraseñas son no coenciden", Toast.LENGTH_SHORT).show();
                            edPassword.requestFocus();
                            //ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary10));

                            edPassword.setBackgroundTintList(colorStateList2);
                            edPass2.setBackgroundTintList(colorStateList2);

                        }

                    }

                });
            }catch (Exception e) { Log.i("errror",e.toString()); }

        }
        private  void setUpchart(){
            PieDataSet pieDataSet= new PieDataSet(pietEntryList,"Pastel");
            PieData pieData=new PieData(pieDataSet);
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueLineColor(getResources().getColor(R.color.white));
            pieDataSet.setValueTextSize(12f);

            pieChart.setData(pieData);
            pieChart.invalidate();

        }
        private void  serValue(){


            pietEntryList.add(new PieEntry(200, "Food"));
            pietEntryList.add(new PieEntry(500, "Hemogoblina"));
            pietEntryList.add(new PieEntry(300, "Alimewnot"));
        }

        private  void  ejecutar(String url){


            StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "operacion exitosa", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    //Intent intent = new Intent(getApplicationContext(),Segundo_activity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse == null) {
                        if(error.getClass().equals(TimeoutError.class)){
                            Toast.makeText( Registro1.this,
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                    Toast.makeText(Registro1.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Nullable
                @Override

                protected Map <String, String> getParams() throws AuthFailureError {
                    Map<String, String>parameros=new HashMap<String, String>();
                    parameros.put("DNI", textInputEditText.getText().toString());
                    parameros.put("usu_password", edPass2.getText().toString());
                    parameros.put("usu_nombres", nombre.getText().toString());
                    parameros.put("usu_apellidos", apellido.getText().toString());
                    parameros.put("usu_usuario", reemail.getText().toString());

                    return parameros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }