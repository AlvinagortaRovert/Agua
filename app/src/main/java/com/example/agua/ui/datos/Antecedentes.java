package com.example.agua.ui.datos;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agua.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class Antecedentes extends Fragment {

    private CheckBox tipoSaneamientoCheckbox;
    private boolean[] checkedItems;
    /*antlabrcheckBox */
    private String[] items = {"Letrina", "Baño ecológico", "Tanque Bio", "Desagüe", "Ninguno"};
    private boolean[] checkedItemssap;
    private String[] itemssap = {"Colapsado", "Minima deficiencia", "Varias deficiencias", "Buen estado"};

    private TextView textView, registar, reporte;
    private DatePicker dpfecha;
    private int valores;
    private String descripcionpat = "",descripcionalerg = "",descripcionhosp = "",descripcionope = "", descripcionpgeni= "",descripcionphlab = "",descripcionedpi = "",descripcioninmu = "",
            descripcionmed = "",  descripcionperi = "",descripcionhfam = "";

    AtomicReference<String> fechaRef = new AtomicReference<>("Valor inicial de la fecha");


    AtomicReference<String> fechaRefpat = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefale = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefope = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefhosp = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefgeni = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefhlab = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefepi = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefinmu = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefmedi = new AtomicReference<>("Valor inicial de la fecha");
    AtomicReference<String> fechaRefperi = new AtomicReference<>("Valor inicial de la fecha");
    private String  fech_pat, fech_aler,fech_hosp,fech_oper,fech_geni,fech_hlab,fech_edpi,fech_inmu,fech_med, fech_peri, fech_hfam, email;
    public TextView  txtemail;



    private CheckBox checkBoxpatologico, checkBoxalergico,medicamentos,perinatales, histfamiliar, operaciones,hospiltalizaciones,gemitourinarios, histlaboral, edpidemologicos, inmunizaciones ;
    public Antecedentes() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_antecedentes, container, false);




        textView = view.findViewById(R.id.textview_id);
        registar=view.findViewById(R.id.textview_reporte);
        // reporte=view.findViewById(R.id.textview_report);
        //btnSiguiente= (Button) view.findViewById(R.id.cforgbtnregiorgano);

        // Referencia al checkbox del layout
        tipoSaneamientoCheckbox = view.findViewById(R.id.checkbox_tipo_saneamiento);

        // Inicializar el arreglo de elementos seleccionados
        checkedItems = new boolean[items.length];


        checkBoxpatologico = view.findViewById(R.id.antpatcheckBox);
        checkBoxalergico = view.findViewById(R.id.antalergicocheckBox);
        operaciones  = view.findViewById(R.id.antoperacheckBox);
        hospiltalizaciones= view.findViewById(R.id.anthospcheckBox);
        gemitourinarios  = view.findViewById(R.id.antgeniocheckBox);
        histlaboral= view.findViewById(R.id.antlabrcheckBox);

        checkedItemssap =new boolean[itemssap.length];
        edpidemologicos  = view.findViewById(R.id.antepidemocheckBox);
        inmunizaciones= view.findViewById(R.id.antinmucheckBox);
        medicamentos  = view.findViewById(R.id.antmedicamentocheckBox);
        perinatales= view.findViewById(R.id.antneonatalescheckBox);
        histfamiliar= view.findViewById(R.id.antfamiliarcheckBox);
        txtemail = getActivity().findViewById(R.id.txtemail);
        email = txtemail.getText().toString(); // obniene correo

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
             /*   Fragment fragment = new Reporte_de_antecedentes ();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true); // Agregar esta línea
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();  */
            }
        });







        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                validar();
            }
        });

        edpidemologicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(edpidemologicos,fechaRefpat, descripcionpat );
                //   popuptexto(fechaRefpat);
            }
        });
        inmunizaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup( inmunizaciones,fechaRefinmu, descripcioninmu );
            }
        });
        medicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(medicamentos,fechaRefmedi,descripcionmed );
            }
        });
        perinatales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(perinatales,fechaRefperi, descripcionperi);
            }
        });

        histfamiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(histfamiliar,fechaRef, descripcionhfam );
            }
        });

        checkBoxalergico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(checkBoxalergico,fechaRefale, descripcionalerg );
            }
        });

        hospiltalizaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(hospiltalizaciones,fechaRefhosp, descripcionhosp );
            }
        });
        operaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(operaciones,fechaRefope, descripcionope );
            }
        });
        gemitourinarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(gemitourinarios, fechaRefgeni, descripcionpgeni );
            }
        });
        histlaboral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mostrarPopupestadoSAP();
            }
        });


        checkBoxpatologico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxpatologico.isChecked()) {
                    // Si el CheckBox está seleccionado, realiza una acción

                    popup(checkBoxpatologico, fechaRef, descripcionpat );

                }
            }
        });


        tipoSaneamientoCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para mostrar el popup
                mostrarPopupSaneamiento();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    private void mostrarPopupestadoSAP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione el esytado del SAP");

        // Configurar los checkboxes en el AlertDialog
        builder.setMultiChoiceItems(itemssap, checkedItemssap, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Actualizar el estado de selección
                checkedItemssap[which] = isChecked;
            }
        });

        // Botón para confirmar la selección
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aquí puedes manejar la selección
                StringBuilder seleccionados = new StringBuilder();
                for (int i = 0; i < itemssap.length; i++) {
                    if (checkedItemssap[i]) {
                        seleccionados.append(itemssap[i]).append(", ");
                    }
                }
                // Mostrar los elementos seleccionados en un Toast
                if (seleccionados.length() > 0) {
                    Toast.makeText(getActivity(), "Seleccionaste: " + seleccionados.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No seleccionaste ningún tipo de saneamiento.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Botón para cancelar la selección
        builder.setNegativeButton("Cancelar", null);

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void mostrarPopupSaneamiento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione el tipo de saneamiento");

        // Configurar los checkboxes en el AlertDialog
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Actualizar el estado de selección
                checkedItems[which] = isChecked;
            }
        });

        // Botón para confirmar la selección
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aquí puedes manejar la selección
                StringBuilder seleccionados = new StringBuilder();
                for (int i = 0; i < items.length; i++) {
                    if (checkedItems[i]) {
                        seleccionados.append(items[i]).append(", ");
                    }
                }
                // Mostrar los elementos seleccionados en un Toast
                if (seleccionados.length() > 0) {
                    Toast.makeText(getActivity(), "Seleccionaste: " + seleccionados.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No seleccionaste ningún tipo de saneamiento.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Botón para cancelar la selección
        builder.setNegativeButton("Cancelar", null);

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void popup(CheckBox checkBox, final AtomicReference<String> fechaRef1, String Descro) {
        if (checkBox.isChecked()) {


            try {

                // Si el CheckBox está seleccionado, muestra el diálogo de selección de fecha
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setTitle("Registre su fecha:");
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkBox.setChecked(false);
                        dialog.dismiss();


                    }
                });

                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Obtén la fecha seleccionada y guárdala en el formato deseado
                        String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        fechaRef1.set(formattedDate); // Actualizar el valor del AtomicReference
                        Log.d("Fecha seleccionada", fechaRef1.get());
                        CheckBox checkBox1 = checkBox;
                        if (checkBox1 == checkBoxpatologico) {


                            fech_pat = fechaRef1.get();

                        }

                        if (checkBox1 == checkBoxalergico) {
                            fech_aler = fechaRef1.get();

                        }
                        if (checkBox1 == medicamentos) {
                            fech_med = fechaRef1.get();

                        }
                        if (checkBox1 == perinatales) {
                            fech_peri = fechaRef1.get();

                        }
                        if (checkBox1 == histfamiliar) {
                            fech_hfam = fechaRef1.get();

                        }
                        if (checkBox1 == operaciones) {
                            fech_oper = fechaRef1.get();

                        }

                        if (checkBox1 == hospiltalizaciones) {
                            fech_hosp = fechaRef1.get();

                        }
                        if (checkBox1 == histlaboral) {
                            fech_hlab = fechaRef1.get();

                        }
                        if (checkBox1 == edpidemologicos) {
                            fech_edpi = fechaRef1.get();

                        }
                        if (checkBox1 == inmunizaciones) {
                            fech_inmu = fechaRef1.get();

                        }

                        if (checkBox1 == gemitourinarios) {
                            fech_geni = fechaRef1.get();
                        }


                        popuptexto(checkBox, fechaRef1, Descro);
                    }


                });

                datePickerDialog.show();

                Log.d("CheckBox", "Seleccionado con: " + fechaRef1.get()); // Mostrar el valor actual de la fecha
            } catch (Exception e){
                Log.d("Hay un  error", "popup: "+e);
            }


        } else {
            // Si el CheckBox no está seleccionado, realiza otra acción
            Log.d("CheckBox", "No seleccionado");
        }
    }



    public void popuptexto(CheckBox checkBox,final AtomicReference<String> textoRef, String Descro) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Describa su antecedente:");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtén el texto ingresado por el usuario y guárdalo en el AtomicReference
                String enteredText = input.getText().toString();

                textoRef.set(enteredText);

                CheckBox checkBox1=checkBox;
                if (checkBox1==checkBoxpatologico){     descripcionpat=textoRef.get();

                }

                if (checkBox1==checkBoxalergico){descripcionalerg=textoRef.get();

                }
                if (checkBox1==medicamentos){descripcionmed=textoRef.get();

                }
                if (checkBox1==perinatales){descripcionperi=textoRef.get();

                }if (checkBox1==histfamiliar){descripcionhfam=textoRef.get();


                }if (checkBox1==operaciones){descripcionope=textoRef.get();

                }

                if (checkBox1==hospiltalizaciones){descripcionhosp=textoRef.get();

                }
                if (checkBox1==histlaboral){descripcionphlab=textoRef.get();

                }if (checkBox1==edpidemologicos){descripcionedpi=textoRef.get();

                }
                if (checkBox1==inmunizaciones ){descripcioninmu=textoRef.get();

                }
                if (checkBox1==gemitourinarios){descripcionpgeni=textoRef.get();

                }










                Log.d("Texto ingresado", textoRef.get());

                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela, no actualices el AtomicReference
                checkBox.setChecked(false);
                dialog.dismiss();

            }
        });

        alertDialogBuilder.show();
    }
    public  void  validar(){

        if(histfamiliar.isChecked()||perinatales.isChecked()|| checkBoxpatologico.isChecked()||  checkBoxalergico.isChecked()||  inmunizaciones.isChecked()||  operaciones.isChecked()||  hospiltalizaciones.isChecked()||  histlaboral.isChecked()||   gemitourinarios.isChecked()||     edpidemologicos.isChecked()||  medicamentos.isChecked()  ){
            fech_hfam=fechaRef.get();
            Toast.makeText(getActivity(), "historia familar"+fech_aler, Toast.LENGTH_SHORT).show();
            ejecutar("https://admin.marcaancash.com/antecedente.php");
        }

        else{
            Toast.makeText(getActivity(), "No se seleccionó ningun atecedente", Toast.LENGTH_SHORT).show();
        }
    }

    private  void  ejecutar(String url){


        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "operacion exitosa", Toast.LENGTH_SHORT).show();
/*
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(); // Remueve el fragmento anterior del backstack
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if(error.getClass().equals(TimeoutError.class)){
                        Toast.makeText( getActivity(),
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();

                    }

                }
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Nullable
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                String pat, alergico, ope, hospital,geni, hlab, epi, inmu, med,peri, hfam;


                if(histfamiliar.isChecked()){
                    // fech_hfam=fechaRef.get();
                    hfam="Si";
                }else{
                    fech_hfam="";
                    hfam="";
                }

                if(inmunizaciones.isChecked()){
                    // fech_inmu=fechaRefinmu.get();
                    inmu="Si";
                }else{
                    fech_inmu="";
                    inmu="";
                }
                if(medicamentos.isChecked()){
                    // fech_med=fechaRefmedi.get();
                    med="Si";
                }else{
                    fech_med="";
                    med="";
                }
                if(perinatales.isChecked()){
                    //  fech_peri=fechaRefperi.get();
                    peri="Si";
                }else{
                    fech_peri="";
                    peri="";
                }


                if(histlaboral.isChecked()){
                    //  fech_hlab=fechaRefhlab.get();
                    hlab="Si";
                }else{
                    fech_hlab="";
                    hlab="";
                }
                if(edpidemologicos.isChecked()){
                    //  fech_edpi=fechaRefepi.get();
                    epi="Si";
                }else{
                    fech_edpi="";
                    epi="";
                }
                if(checkBoxpatologico.isChecked()){
                    //  fech_pat=fechaRefpat.get();
                    pat="Si";
                }else{
                    fech_pat="";
                    pat="";
                }

                if(checkBoxalergico.isChecked()){
                    //    fech_aler=fechaRefale.get();
                    alergico="Si";
                }else{
                    fech_aler="";
                    alergico="";
                }
                if(operaciones.isChecked()){
                    // fech_oper=fechaRefope.get();
                    ope="Si";
                }else{
                    fech_oper="";
                    ope="";
                }


                if(hospiltalizaciones.isChecked()){
                    //  fech_hosp=fechaRefhosp.get();
                    hospital="Si";
                }else{
                    fech_hosp="";
                    hospital="";
                }
                if(gemitourinarios.isChecked()){
                    //  fech_geni=fechaRefgeni.get();
                    geni="Si";
                }else{
                    fech_geni="";
                    geni="";
                }

                Map<String, String>parameros=new HashMap<>();
                try {
                    parameros.put("usu_usuario", email.toString());
                    parameros.put("pato", pat);
                    parameros.put("depato", descripcionpat.toString());
                    parameros.put("dat_pato", fech_pat);
                    parameros.put("aler", alergico);
                    parameros.put("dealer", descripcionalerg.toString());
                    parameros.put("dat_aler", fech_aler);
                    parameros.put("deoperac", descripcionope.toString());
                    parameros.put("operac", ope);
                    parameros.put("dat_ope", fech_oper);
                    parameros.put("dehospital", descripcionhosp.toString());
                    parameros.put("hospital", hospital);
                    parameros.put("dat_hospital", fech_hosp);
                    parameros.put("degenital", descripcionpgeni.toString());
                    parameros.put("genital", geni);
                    parameros.put("dat_genital", fech_geni);
                    parameros.put("dehistlabotal", descripcionphlab.toString());
                    parameros.put("histlabotal", hlab);
                    parameros.put("dat_histlabotal", fech_hlab);
                    parameros.put("dehepi", descripcionedpi.toString());
                    parameros.put("epi", epi);
                    parameros.put("dat_epi", fech_edpi);
                    parameros.put("deinmune", descripcioninmu.toString());
                    parameros.put("inmune", inmu);
                    parameros.put("dat_inmune", fech_inmu);
                    parameros.put("demedicamento", descripcionmed.toString());
                    parameros.put("medicamento", med);
                    parameros.put("dat_medicamento", fech_med);
                    parameros.put("deperinatal", descripcionperi.toString());
                    parameros.put("perinatal", peri);
                    parameros.put("dat_perinatal", fech_peri);
                    parameros.put("defamiliar", descripcionhfam.toString());
                    parameros.put("familiar", hfam);
                    parameros.put("dat_familiar", fech_hfam);

                } catch ( Exception e){
                    Log.d("error en aqyu", "getParams: "+e);
                }
                return parameros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



}