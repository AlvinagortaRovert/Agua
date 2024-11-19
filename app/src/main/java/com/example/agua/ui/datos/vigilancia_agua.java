package com.example.agua.ui.datos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.data.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import com.example.agua.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link vigilancia_agua#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vigilancia_agua extends Fragment {
    public vigilancia_agua() {

    }
  boolean vari1=false,vari2=false,vari3=false,vari4=false,vari5=false;
    int va1=0,va2=0,va3=0,va4=0,va5=0;
    private List<Reportevigilante> datosServidor = new ArrayList<>();
    private String[] items = {"null", "Baño ecológico"};
    private CheckBox tipoSaneamientoCheckbox;
    private boolean[] checkedItems = new boolean[items.length];
    /*antlabrcheckBox */


    private RadioButton Rbtn;
    private RadioGroup radiografico;
    private RadioButton radiobarras;
    private RadioButton radiolineal;

    private Button btnSiguiente;
    private CheckBox checkBox0, checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    BarChart chart;
    private  ImageView  imagefecha;
    private ImageButton imgsistole, imgdiastole, imgcolesterol, imghemoglobina, imgfrecuencia;
    public SeekBar hemoglobina, colesterol, triglicerido, sistole, diastole, viv3,viv4,viv5;
    public TextView txtemail, presion, v1,v3,nombreunidad, diast, nombreunidad2,nombreunidad3,nombreunidad4,nombreunidad5, hemo, colest, trigli, frecu, fecha, siguiente, reporte, aniadir,
            titulodias, titulohemo, titulocolest, titulotrigli, titulofreuc, nombrecontrol;
    public String valorSeleccionado2, valorSeleccionado3, email, resulpresion, fechaord, Control, unidad, valmax, valmin, publico, Valor, unidadguardada, unidadguardada2, control = "";
    public int maximo ,multiploguardado;
    public  double valormedio;



    public  LineChart lineChart;
    private LinearLayout nuevoLinearLayout;
    public Spinner spinner1, spinner2;
    ProgressDialog pDialog;
    //private List<Organo> controlList = new ArrayList<>();
    private List<Controle> controlList = new ArrayList<>();
   // private List<Reportevigilante> controlList1 = new ArrayList<>();
   // private List<Pesotallapresion> controlList2 = new ArrayList<>();
  //  private List<Monitoreoagua> controlList3 = new ArrayList<>();

    List<PieEntry> pietEntryList;
    private int currentIndex = 0; // Índice inicial
    private static final int MAX_VISIBLE = 4;

    private String url_control = "https://aguapotable.app-organizadoor.com/api/listarcontrol.php?usuario=",
            url_reporte = "https://aguapotable.app-organizadoor.com/api/vigilanter.php?usuario=";
           // url_reporte2 = "https://aguapotable.app-organizadoor.com/api/vigilanter.php?usuario=";

    AtomicReference<String> Nvalor = new AtomicReference<>("Nombre de control");
    int valor1, valor2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vigilancia_agua, container, false);
        sistole = (SeekBar) view.findViewById(R.id.stikviv2);
        Valor = "presion";
        diastole = (SeekBar) view.findViewById(R.id.stikviv1);
        viv3= (SeekBar) view.findViewById(R.id.stikviv3);
        viv4= (SeekBar) view.findViewById(R.id.stikviv4);
        viv5= (SeekBar) view.findViewById(R.id.stikviv5);
        aniadir = view.findViewById(R.id.addvigilancia);
        spinner1 = (Spinner) view.findViewById(R.id.spinner);
        //spinner2 = (Spinner)view.findViewById(R.id.)

        diast = view.findViewById(R.id.txtv1);

        TextView btnAtras =(TextView) view.findViewById(R.id.btnAtras);
        TextView btnAdelante = (TextView) view.findViewById(R.id.btnAdelante);

        txtemail = getActivity().findViewById(R.id.txtemail);
        fecha = (TextView) view.findViewById(R.id.vitextid);
        checkBox0=(CheckBox) view.findViewById(R.id.checkBox0);
        checkBox1=(CheckBox) view.findViewById(R.id.checkBox);
        checkBox2=(CheckBox) view.findViewById(R.id.checkBox2);
        checkBox3=(CheckBox) view.findViewById(R.id.checkBox3);
        checkBox4=(CheckBox) view.findViewById(R.id.checkBox4);
        checkBox5=(CheckBox) view.findViewById(R.id.checkBox5);


        fechaord = fecha.getText().toString();
        siguiente = view.findViewById(R.id.textview_recuperar);
        chart = view.findViewById(R.id.bar_chart);
        lineChart = view.findViewById(R.id.chart);

        List<String> monthsList = generatePastSixMonths();

        // Crear un adaptador para el Spinner y asignarle la lista de fechas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, monthsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        email = txtemail.getText().toString(); // obniene correo
        imgsistole = view.findViewById(R.id.imageButtonsistole);
        imgdiastole = view.findViewById(R.id.imageButtondiastole);
        imagefecha = (ImageView) view.findViewById(R.id.imagefecha);
        //titulodias = view.findViewById(R.id.vitext11);
        v1= view.findViewById(R.id.txtv1);

        v3= view.findViewById(R.id.txtv3);

        nombreunidad = view.findViewById(R.id.txtsistole);
        nombreunidad2 = view.findViewById(R.id.txtv1);
        nombreunidad3 = view.findViewById(R.id.txtv3);
        nombreunidad4 = view.findViewById(R.id.txtsistole4);
        nombreunidad5 = view.findViewById(R.id.txtsistole5);

      //  nombrecontrol = view.findViewById(R.id.vitext);
        valor1 = sistole.getProgress();
        valor2 = diastole.getProgress();
        radiografico = view.findViewById(R.id.opciones_sexo);
        radiobarras = view.findViewById(R.id.radio_masculino);
        radiolineal = view.findViewById(R.id.radio_femenino);
        ViewGroup.LayoutParams params1 = lineChart.getLayoutParams();
        params1.width = 0; // Establecer el ancho a cero
        lineChart.setLayoutParams(params1);
        lineChart.setVisibility(View.INVISIBLE);
        lineal();
        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                popuptexto(Nvalor);
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaActual = dateFormat.format(new Date());
        fecha.setText(fechaActual);


        // Agregar un OnCheckedChangeListener al RadioGroup

        imagefecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFechasUnicas();
            }
        });
        radiografico.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Obtener el RadioButton seleccionado
                RadioButton radioButton = group.findViewById(checkedId);

                // Mostrar un Toast con el valor seleccionado
                String mensaje = radioButton.getText().toString();

                if(mensaje.equals("Barras")){

                    // textView.setText("Siguiente");

                    chart.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = chart.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT; // Restaurar el ancho a match_parent si es necesario
                    chart.setLayoutParams(params);

                    ViewGroup.LayoutParams params1 = lineChart.getLayoutParams();
                    params1.width = 0; // Establecer el ancho a cero
                    lineChart.setLayoutParams(params1);
                    lineChart.setVisibility(View.INVISIBLE);



                }else{

                    ViewGroup.LayoutParams params3 = chart.getLayoutParams();
                    params3.width = 0; // Establecer el ancho a cero
                    chart.setLayoutParams(params3);
                    chart.setVisibility(View.INVISIBLE);

                    lineChart.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params2 = lineChart.getLayoutParams();
                    params2.width = ViewGroup.LayoutParams.MATCH_PARENT; // Restaurar el ancho a match_parent si es necesario
                    lineChart.setLayoutParams(params2);



                }
                //Toast.makeText(requireContext(), "Selecciono la opcion "+mensaje, Toast.LENGTH_SHORT).show();
            }
        });





        checkBox0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                if (checkBox0.isChecked()) {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                    checkBox4.setChecked(true);
                    checkBox5.setChecked(true);
                         vari1=true;
                         vari2=true;
                    vari3=true;
                    vari4=true;
                    vari5=true;

                }
             else {
                // Si no está marcado, deseleccionar todos los check boxes
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                    vari1=false;
                    vari2=false;
                    vari3=false;
                    vari4=false;
                    vari5=false;
            }}
        });



        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                if (checkBox1.isChecked()) {
                 //   checkBox1.setChecked(true);
                     va1=1;
                    vari1=true;
                 validar();

                }
                else {
                    // Si no está marcado, deseleccionar todos los check boxes
                    checkBox0.setChecked(false);
                    va1=0;
                    vari1=false;
                    validar();
                }}
        });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                if (checkBox2.isChecked()) {


                    vari2=true;
                    validar();

                }
                else {
                    // Si no está marcado, deseleccionar todos los check boxes


                    vari2=false;
                    validar();
                }}
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String valorSeleccionado = spinner.getSelectedItem().toString();
                if (checkBox3.isChecked()) {


                    vari3=true;
                    validar();

                }
                else {
                    // Si no está marcado, deseleccionar todos los check boxes


                    vari3=false;
                    validar();
                }}
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = spinner1.getSelectedItem().toString();


                if (selectedItem.equals("Presion_Arterial")) {/*

                    diastole.setVisibility(View.VISIBLE);
                    unidadguardada = "mmhg";
                    control = selectedItem;
                    unidadguardada2 = "mmhg";
                    diast.setVisibility(View.VISIBLE);
                    imgdiastole.setVisibility(View.VISIBLE);
                  //  titulodias.setVisibility(View.VISIBLE);
                    Valor = "presion";
                 //   nombrecontrol.setText("Sistole");
                  //  titulodias.setText("Diastole");
                    nombreunidad.setText("120 mmhg");
                    diast.setText("80 mmhg");
                    sistole.setMax(180);
                    sistole.setProgress(90);
                    diastole.setMax(120);
                    diastole.setProgress(60);
                    new GetPesoPresion().execute();
*/
                } else {


                    if (selectedItem.equals("Peso/talla")) {


                    } else {
                        Valor = "otro";
                        control = selectedItem;

                        diastole.setVisibility(View.VISIBLE);
                        diast.setVisibility(View.VISIBLE);
                        imgdiastole.setVisibility(View.VISIBLE);


                        Controle selectedControle = controlList.get(position);


                        double a, b, c,t;
                        int multiplier = 1;
                        a = Double.parseDouble(selectedControle.getValormax());
                        b = Double.parseDouble(selectedControle.getValormin());
                       multiplier=multiplo(a,b);
                        int intA = (int) (a * multiplier);
                        int intB = (int) (b * multiplier);
                        c = (intA - intB) / 2;

                        int vmax = 1000, Vmedia = 500;
                        vmax = (int) Math.ceil((double) (intA));
                        Vmedia = (int) Math.ceil((double) (c));
                           valormedio=(a-b)/2;
                        // Actualizar los TextViews con los valores seleccionados
                       // Toast.makeText(getActivity(), ""+a+"  - "+b+ " -"+ c+" - x "+vmax +" -&- "+Vmedia, Toast.LENGTH_SHORT).show();
                     //   Log.d(null, "onItemSelected: "+a+"  - "+b+ " -"+ c+" - x "+vmax +" -&- "+Vmedia );
                        sistole.setMax(vmax);
                        sistole.setProgress(Vmedia);

                        unidadguardada = selectedControle.getUnidad();
                        unidadguardada2= selectedControle.getUnidad();
                        multiploguardado=multiplo(a,b);



                        t= (a-b)/2;

                        if (t == (int) t) {



                            // Si es entero, mostrar como entero
                            v1.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                            v3.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                            nombreunidad2.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                            nombreunidad.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                          //  nombreunidad3.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                            nombreunidad4.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                            nombreunidad5.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());

                          //  nombreunidad2.setText(String.format("%d", (int) t) +" -2"+ selectedControle.getUnidad());                          //
                            diast.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                        } else {

                            // Si tiene decimales, mostrar con 3 decimales
                            v1.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                            v3.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                          //         v1.setText(String.format("%d", (int) t) +" "+ selectedControle.getUnidad());
                        //    nombreunidad3.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                            nombreunidad4.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                           nombreunidad5.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                            nombreunidad2.setText(String.format("%.3f", t) +" -2"+ selectedControle.getUnidad());
                           diast.setText(String.format("%.3f", t) +" "+ selectedControle.getUnidad());
                        }




                        maximo = vmax;
                         diastole.setMax(vmax);
                        diastole.setProgress(Vmedia);

                        viv3.setMax(vmax);
                        viv3.setProgress(Vmedia);
                        viv4.setMax(vmax);
                        viv4.setProgress(Vmedia);
                        viv5.setMax(vmax);
                        viv5.setProgress(Vmedia);
                        //   Colesterol();
                       new GetReportes().execute();
                      //  new GetPesoPresion().execute();

                    }

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes agregar un evento para cuando no se ha seleccionado ningún elemento
            }
        });

        ColorStateList colorStateList2 = ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.design_default_color_error));

        viv3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (Valor.equals("Presion_Arterial")) {

                } else {


                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sistole.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (Valor.equals("Presion_Arterial")) {


                } else {

               //     nombreunidad.setText((progress) + " " + unidadguardada);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        imgsistole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Valor.equals("presion")) {

                    popup(imgsistole, sistole, nombreunidad, " mm/HG", 181);
                } else {

                    popup(imgsistole, sistole, nombreunidad, unidadguardada, maximo);
                }

            }
        });


        imgdiastole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Valor.equals("presion")) {

                    popup(imgdiastole, diastole, nombreunidad, " mm/HG", 121);
                } else {

                    popup(imgdiastole, diastole, nombreunidad, unidadguardada, maximo);
                }

                // popup(imgdiastole,diastole,diast, " mm/HG", 121);
            }
        });


        diastole.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                diast.setText((progress)+unidadguardada);

                /*
                if (progress>80){

                    seekBar.setProgressTintList(colorStateList2);
                    seekBar.setThumbTintList(colorStateList2);


                }
                if (progress<80){
                    seekBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(11, 252, 219)));
                    seekBar.setThumbTintList(ColorStateList.valueOf(Color.rgb(11, 252, 219 )));
                    seekBar.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE ));
                    seekBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

                }

                valor2=diastole.getProgress();
 */

             //   nombreunidad2.setText((progress) + " " + unidadguardada2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        viv3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                nombreunidad3.setText((progress)+" "+unidadguardada);}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        viv4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                nombreunidad4.setText((progress)+" "+unidadguardada);}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        viv5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                nombreunidad5.setText((progress)+" "+unidadguardada);}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sistole.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                nombreunidad.setText((progress)+unidadguardada);

                /*
                if (progress>80){

                    seekBar.setProgressTintList(colorStateList2);
                    seekBar.setThumbTintList(colorStateList2);


                }
                if (progress<80){
                    seekBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(11, 252, 219)));
                    seekBar.setThumbTintList(ColorStateList.valueOf(Color.rgb(11, 252, 219 )));
                    seekBar.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE ));
                    seekBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

                }

                valor2=diastole.getProgress();
 */

                //   nombreunidad2.setText((progress) + " " + unidadguardada2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                androidx.appcompat.app.AlertDialog.Builder mensaje = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                mensaje.setMessage("Es correcto el valor de :" + sistole.getProgress() + " " + unidadguardada).setCancelable(false);
                mensaje.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Valor.equals("Presion_Arterial")) {

                           // ejecutar_vigilante2("https://aguapotable.app-organizadoor.com/api/presion.php");

                        } else {
                            if (Valor.equals("pesotalla")) {

                             //   ejecutar_vigilante2("https://aguapotable.app-organizadoor.com/api/pesotalla.php");
                               // valorSeleccionado2 = spinner1.getSelectedItem().toString();


                            } else {

                                valorSeleccionado2 = spinner1.getSelectedItem().toString();
                                ejecutar_vigilante("https://aguapotable.app-organizadoor.com/api/addvigilancia.php");

                            }

                        }

                        ;

                    }
                });
                mensaje.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        try {


                            //Menuprincipal menu = new Menuprincipal();


                            //Toast.makeText(getActivity(), ""+fecha.getText().toString(), Toast.LENGTH_SHORT).show();
                            // Log.d("eror en codigo", "onClick: "+email);

                        } catch (Exception e) {
                            Log.d("eror", "onClick: " + e);
                        }
                    }
                });
                androidx.appcompat.app.AlertDialog alert = mensaje.create();
                alert.setTitle("Aviso del sistema");
                alert.show();


                //     ejecutar("https://admin.marcaancash.com/vigilancia.php");


            }
        });

        //  Presion();
        new GetControles().execute();
        return view;
        // return inflater.inflate(R.layout.fragment_vigilancia_salud, container, false);
    }
  public int multiplo( double a,  double b){
      int caseKey;  // Variable para almacenar el caso

      // Definir la clave del caso con base en los valores de a y b
      if (a >= 1 || b >= 1) {
          caseKey = 1;  // Caso para números con al menos 1 decimal
      } else if (a < 1 && b < 1) {
          caseKey = 2;  // Caso para números con más decimales
      } else {
          caseKey = 0;  // Caso por defecto o cualquier otro caso
      }

      int multiplier;

      // Usar switch para manejar los diferentes casos
      switch (caseKey) {
          case 1:
              multiplier = 100;  // Si hay al menos un decimal
              break;
          case 2:
              multiplier = 1000;  // Si hay tres decimales
              break;
          default:
              multiplier = 1;  // Si no se ajusta a ninguna de las condiciones
              break;
      }

      return multiplier;  // Devolver el multiplicado

  }
    public void popuptexto(final AtomicReference<String> textoRef) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Nombre del nuevo parámetro a evaluar:");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtén el texto ingresado por el usuario y guárdalo en el AtomicReference
                String enteredText = input.getText().toString();
                textoRef.set(enteredText);
                Control = enteredText;

                Log.d("Texto ingresado", textoRef.get());
                popupunidad(textoRef);
                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela, no actualices el AtomicReference
                //  checkBox.setChecked(false);
                dialog.dismiss();

            }
        });

        alertDialogBuilder.show();
    }

    public void popupunidad(final AtomicReference<String> textoRef) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("ingrese sus unidades de medida (mg, cm, vol):");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtén el texto ingresado por el usuario y guárdalo en el AtomicReference
                String enteredText = input.getText().toString();
                textoRef.set(enteredText);
                Log.d("Texto ingresado", textoRef.get());
                unidad = enteredText;

                publico();
                //  popupvalobajo(textoRef);
                //
                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela, no actualices el AtomicReference
                //  checkBox.setChecked(false);
                dialog.dismiss();

            }
        });

        alertDialogBuilder.show();
    }

    public void publico() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmación");
        builder.setMessage("¿Este parámetro es visible por todos?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Agregar aquí el código para la opción "Sí"
                publico = "SI";
                popupvalobajo(Nvalor);
            }
        });

        // Agregar un botón "No" al diálogo
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Agregar aquí el código para la opción "No"
                publico = "NO";
                popupvalobajo(Nvalor);
            }
        });

        // Mostrar el diálogo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void popupvalobajo(final AtomicReference<String> textoRef) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Ingrese su valor mínimo:");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); // Configura el tipo de entrada aquí
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredText = input.getText().toString();
         /*       try {
                    double number = Double.parseDouble(enteredText);
                    // El valor ingresado es un número válido
                    textoRef.set(enteredText);
                    Log.d("Número ingresado", textoRef.get());
                    valmin = enteredText;
                    popupvaloralto(textoRef);
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    // El valor ingresado no es un número válido, muestra un mensaje de error
                    Toast.makeText(getContext(), "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
                    // No cierras el diálogo para permitir al usuario volver a ingresar el valor
                }
*/
                if (isValidNumber(enteredText)) {
                    // El valor ingresado es un número válido
                    textoRef.set(enteredText);
                    Log.d("Número ingresado", textoRef.get());
                    valmin = enteredText;
                    popupvaloralto(textoRef);
                    dialog.dismiss();
                } else {
                    // El valor ingresado no es un número válido, muestra un mensaje de error
                    Toast.makeText(getContext(), "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
                    // No cierras el diálogo para permitir al usuario volver a ingresar el valor
                }
            }
        });


        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario cancela, no actualices el AtomicReference
                dialog.dismiss();
            }
        });

        //  AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private boolean isValidNumber(String text) {
        try {
            // Intenta analizar el texto como un número (puede ser entero o decimal)
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            // El texto no es un número válido
            return false;
        }
    }

    public void popupvaloralto(final AtomicReference<String> textoRef) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Ingrese sus valor maximo");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredText = input.getText().toString();
                if (isValidNumber(enteredText)) {

                    Double a, b;
                    a = Double.parseDouble(valmin);
                    b = Double.parseDouble(enteredText);

                    if (a < b) {
                        valmax = enteredText;

                 //       nombrecontrol.setText(Control);
                        nombreunidad.setText(unidad);

                        ejecutarcontrol("https://aguapotable.app-organizadoor.com/api/agregarcontrol.php");

                     //   titulodias.setVisibility(View.GONE);
                        diastole.setVisibility(View.GONE);
                        diast.setVisibility(View.GONE);
                        imgdiastole.setVisibility(View.GONE);
                        dialog.dismiss();

                    } else {

                        Toast.makeText(getContext(), "Por favor, ingrese un número mayor al valor minimo : " + a, Toast.LENGTH_SHORT).show();
                        // No cierras el diálogo para permitir al usuario volver a ingresar el valor

                    }


                } else {
                    // El valor ingresado no es un número válido, muestra un mensaje de error
                    Toast.makeText(getContext(), "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
                    // No cierras el diálogo para permitir al usuario volver a ingresar el valor
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela, no actualices el AtomicReference
                //  checkBox.setChecked(false);
                dialog.dismiss();

            }
        });

        alertDialogBuilder.show();
    }


    private void ejecutarcontrol(String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "operacion exitosa", Toast.LENGTH_SHORT).show();

                //Toast.makeText(getActivity(), Control+ "- "+ publico+valmax+" - "+valmin+" - "+unidad, Toast.LENGTH_SHORT).show();
                spinner1.setAdapter(null);
                controlList = new ArrayList<>();
                new GetControles().execute();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getActivity(),
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

                Map<String, String> parameros = new HashMap<>();
                try {

                    parameros.put("usu_usuario", email.toString());
                    parameros.put("control", Control.toString());
                    parameros.put("unidades", unidad.toString());
                    parameros.put("valormin", valmin);
                    parameros.put("valormax", valmax);
                    parameros.put("Private", publico.toString());


                } catch (Exception e) {
                    Log.d("error en aqyu", "getParams: " + e);
                }
                return parameros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    public static Reportes newInstance(String valorSeleccionado2) {
        Reportes fragment = new Reportes();
        Bundle args = new Bundle();
        args.putString("clave", valorSeleccionado2); // "clave" es un identificador para el argumento
        fragment.setArguments(args);
        return fragment;
    }

    private class GetControles extends AsyncTask<Void, Void, Void> {
        @Override


        protected void onPreExecute() {


            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            //  pDialog.setMessage("Obtencion de las frutas..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url_control + email, ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj
                                .getJSONArray("control");
                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);

                            Controle cat = new Controle(
                                    catObj.getInt("idcontrol"),
                                    catObj.getString("control"),
                                    catObj.getString("unidades"),
                                    catObj.getString("valormin"),
                                    catObj.getString("valormax"),
                                    catObj.getString("Private")
                            );
                            controlList.add(cat);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }
    }


    public void elegir() {


    }


    private void popup(ImageButton img, SeekBar barra, TextView texto, String unidad, int limite) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ingresa un texto");

        // Crear un EditText para la entrada de texto
        final EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        int valor = barra.getProgress();
        editText.setText(String.valueOf(valor));
        builder.setView(editText);

// Agregar los botones de "Aceptar" y "Cancelar"
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    String textoIngresado = editText.getText().toString();

                    // Realizar las operaciones necesarias con el texto ingresado
                    int valor1 = Integer.parseInt(textoIngresado);
                    if (valor1 < limite) {
                        texto.setText(textoIngresado + unidad);
                        barra.setProgress(valor1);
                    } else {
                        dialog.dismiss();

                    }
                } catch (Exception e) {
                    Log.e("error en progreso", "onClick: " + e);
                }
                // ...
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private View cloneView(View view) {
        // Clona una vista creando una nueva instancia de la misma clase
        try {
            Class<?> viewClass = view.getClass();
            Constructor constructor = viewClass.getConstructor(Context.class);
            View copiedView = (View) constructor.newInstance(requireContext());

            // Copia los atributos de la vista original a la vista clonada
            copiedView.setLayoutParams(view.getLayoutParams());
            copiedView.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());

            // Copia cualquier otro atributo específico de la vista si es necesario

            return copiedView;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        //  txtAgregar.setText("");
        for (int i = 0; i < controlList.size(); i++) {
            //  lables.add(controlList.get(i).getOrgano());
            lables.add(controlList.get(i).getControl());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter);
        // Toast.makeText(getActivity(), "cantid de datos"+controlList.size(), Toast.LENGTH_SHORT).show();
    }


    private void Pesotalla() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry
        ArrayList<BarEntry> gastosEntries = new ArrayList<>();
        gastosEntries.add(new BarEntry(0, 88));
        gastosEntries.add(new BarEntry(1, 93));
        gastosEntries.add(new BarEntry(2, 91));
        gastosEntries.add(new BarEntry(3, 95));
        BarDataSet gastosDataSet = new BarDataSet(gastosEntries, "peso");


// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "talla");
        dataSet.setColor(Color.rgb(0, 255, 0));
        BarData data = new BarData(dataSet);
        chart.setData(data);

        gastosDataSet.setColor(Color.rgb(255, 128, 0));
        BarData data2 = new BarData(dataSet, gastosDataSet);
        chart.setData(data2);

// Configura la apariencia del gráfico
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.animateY(1000);
        chart.invalidate();


        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
        legend.setFormSize(14f);


    }

    private void lineal() {
        ArrayList<Entry> v1 = new ArrayList<>();
        v1.add(new Entry(0, 111));
        v1.add(new Entry(1, 121));
        v1.add(new Entry(2, 115));
        v1.add(new Entry(3, 128));

        ArrayList<Entry> v2 = new ArrayList<>();
        v2.add(new Entry(0, 11));
        v2.add(new Entry(1, 101));
        v2.add(new Entry(2, 150));
        v2.add(new Entry(3, 180));
        ArrayList<Entry> v3 = new ArrayList<>();
        v3.add(new Entry(0, 11));
        v3.add(new Entry(1, 101));
        v3.add(new Entry(2, 150));
        v3.add(new Entry(3, 180));
        ArrayList<Entry> v4 = new ArrayList<>();
        v4.add(new Entry(0, 188));
        v4.add(new Entry(1, 193));
        v4.add(new Entry(2, 81));
        v4.add(new Entry(3, 65));
        // Crear los datos del gráfico en forma de entradas de Entry
        ArrayList<Entry> v5 = new ArrayList<>();
        v5.add(new Entry(0, 88));
        v5.add(new Entry(1, 93));
        v5.add(new Entry(2, 91));
        v5.add(new Entry(3, 95));

        LineDataSet dataSet = new LineDataSet(v1, "Viv. 1");
        dataSet.setColor(Color.BLUE);
        LineDataSet dataSet2 = new LineDataSet(v2, "Viv.  2");
        dataSet2.setColor(Color.rgb(138, 43, 226));
        LineDataSet dataSet3 = new LineDataSet(v3, "Viv. 3");
        dataSet3.setColor(Color.YELLOW);
        LineDataSet dataSet4 = new LineDataSet(v4, "Viv.4");
        dataSet4.setColor(Color.rgb(255, 105, 180)); // Rosado
        LineDataSet gastosDataSet = new LineDataSet(v5, "Viv. 5");
        gastosDataSet.setColor(Color.RED);

        LineData data = new LineData();
        data.addDataSet(dataSet);
        data.addDataSet(gastosDataSet);
        data.addDataSet(dataSet3);
        data.addDataSet(dataSet2);
        data.addDataSet(dataSet4);
        lineChart.setData(data);

        // Configurar la apariencia del gráfico
        lineChart.getDescription().setEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.animateY(1000);
        lineChart.invalidate();

        Legend legend =   lineChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
        legend.setFormSize(14f);
    }




    private void lineal2(List<Reportevigilante> controlList) {
        try {
            // Transformar los datos de controlList en entradas para líneas
            ArrayList<Entry> v1 = new ArrayList<>();
            ArrayList<Entry> v2 = new ArrayList<>();
            ArrayList<Entry> v3 = new ArrayList<>();
            ArrayList<Entry> v4 = new ArrayList<>();
            ArrayList<Entry> v5 = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            for (int i = 0; i < controlList.size(); i++) {
                try {
                    float valor = Float.parseFloat(controlList.get(i).getValor());
                    float control = Float.parseFloat(controlList.get(i).getControl());
                    float valormin = Float.parseFloat(controlList.get(i).getValormin());
                    float valormax = Float.parseFloat(controlList.get(i).getValormax());
                    v2.add(new Entry(i, valor));
                    v3.add(new Entry(i, control));
                    v4.add(new Entry(i, valormin));
                    v5.add(new Entry(i, valormax));
                    labels.add(controlList.get(i).getFecha());
                } catch (NumberFormatException e) {
                    Log.e("LineChart", "Error al convertir valores: " + e.getMessage());
                }
            }

            // Crear conjuntos de datos para líneas
            LineDataSet dataSetv2 = new LineDataSet(v2, "Viv. 2");
            dataSetv2.setColor(Color.rgb(255, 128, 0));
            dataSetv2.setCircleColor(Color.rgb(255, 128, 0));

            LineDataSet dataSetv3 = new LineDataSet(v3, "Viv. 3");
            dataSetv3.setColor(Color.YELLOW);
            dataSetv3.setCircleColor(Color.YELLOW);

            LineDataSet dataSetv4 = new LineDataSet(v4, "Viv. 4");
            dataSetv4.setColor(Color.RED);
            dataSetv4.setCircleColor(Color.RED);

            LineDataSet dataSetv5 = new LineDataSet(v5, "Viv. 5");
            dataSetv5.setColor(Color.DKGRAY);
            dataSetv5.setCircleColor(Color.DKGRAY);

            // Agregar conjuntos al gráfico
            LineData data = new LineData(dataSetv2, dataSetv3, dataSetv4, dataSetv5);
            lineChart.setData(data);

            // Configuración del eje X
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.BLACK);

            // Configurar apariencia general del gráfico
            lineChart.getDescription().setEnabled(false);
            lineChart.setPinchZoom(false);
            lineChart.setDrawGridBackground(false);
            lineChart.animateY(1000);
            lineChart.invalidate();

            // Configuración de la leyenda
            Legend legend = lineChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setTextColor(Color.BLACK);
            legend.setXEntrySpace(10f);
            legend.setYEntrySpace(5f);
            legend.setFormSize(14f);
        } catch (Exception e) {
            Log.e("LineChart", "Error al cargar el gráfico: " + e.getMessage());
        }
    }


    private void cargarGrafico(List<Reportevigilante> controlList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < controlList.size(); i++) {
            entries.add(new BarEntry(i, Float.parseFloat(controlList.get(i).getValor())));
            entries2.add(new BarEntry(i, Float.parseFloat(controlList.get(i).getValormax())));
            labels.add(controlList.get(i).getFecha());
        }

        BarDataSet dataSet = new BarDataSet(entries, spinner1.getSelectedItem().toString());
        dataSet.setColor(Color.rgb(255, 128, 0));
        BarData data = new BarData(dataSet);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Coloca las etiquetas en la parte inferior de las barras
        xAxis.setGranularity(1f); // Intervalo de 1 unidad (en tu caso, un día)
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(labels.size()); // Define cuántas etiquetas se mostrarán
        xAxis.setTextSize(10f); // Tamaño de fuente de las etiquetas
        xAxis.setTextColor(Color.BLACK); // Color del texto de las etiquetas
        //  xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.blue)); // Color de texto (opcional)

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.animateY(1000);
        chart.invalidate();

        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
        legend.setFormSize(14f);
    }


    private void cargarGrafico2(List<Reportevigilante> controlList) {

        try {
            ArrayList<BarEntry> v1 = new ArrayList<>();
            ArrayList<BarEntry> v2 = new ArrayList<>();
            ArrayList<BarEntry> v3 = new ArrayList<>();
            ArrayList<BarEntry> v4 = new ArrayList<>();
            ArrayList<BarEntry> v5 = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            for (int i = 0; i < controlList.size(); i++) {
                // Validación de datos antes de agregarlos
                try {
                    float valor = Float.parseFloat(controlList.get(i).getValor());

                    float control = Float.parseFloat(controlList.get(i).getControl());
                    float valor2 = Float.parseFloat(controlList.get(i).getUnidades());
                    float valormin = Float.parseFloat(controlList.get(i).getValormin());
                    float valormax = Float.parseFloat(controlList.get(i).getValormax());
                    if(vari1){
                        v1.add(new BarEntry(i, valor2));
                    }
                    if(vari2){
                    v2.add(new BarEntry(i, valor));
                    }
                    v3.add(new BarEntry(i, control));
                    v4.add(new BarEntry(i, valormin));
                    v5.add(new BarEntry(i, valormax));
                    labels.add(controlList.get(i).getFecha());
                } catch (NumberFormatException e) {
                    Log.e("Graficos", "Error al convertir valores: " + e.getMessage());
                }
            }

            BarDataSet dataSetv1 = new BarDataSet(v1, "Viv. 1");
            if(vari1){
                dataSetv1.setColor(Color.rgb(0, 255, 0));

            }else{


            }


            BarDataSet dataSetv2 = new BarDataSet(v2, "Viv. 2");
            dataSetv2.setColor(Color.rgb(255, 128, 0));

            BarDataSet dataSetv3 = new BarDataSet(v3, "Viv. 3");
            dataSetv3.setColor(Color.YELLOW);

            BarDataSet dataSetv4 = new BarDataSet(v4, "Viv. 4");
            dataSetv4.setColor(Color.RED);

            BarDataSet dataSetv5 = new BarDataSet(v5, "Viv. 5");
            dataSetv5.setColor(Color.DKGRAY);

            BarData data = new BarData(dataSetv1, dataSetv2, dataSetv3, dataSetv4, dataSetv5);

            // Configuración de agrupación de barras
            float groupSpace = 0.2f; // Espacio entre grupos
            float barSpace = 0.05f; // Espacio entre barras dentro del mismo grupo
            float barWidth = 0.15f; // Ancho de cada barra
            data.setBarWidth(barWidth);
            chart.setData(data);
            chart.getXAxis().setAxisMinimum(0); // Ajustar el inicio en el eje X
            chart.groupBars(0, groupSpace, barSpace); // Agrupar las barras

            // Configuración del eje X
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.BLACK);

            // Configuración del gráfico
            chart.setDrawGridBackground(false);
            chart.setDrawBarShadow(false);
            chart.setDrawValueAboveBar(true);
            chart.getDescription().setEnabled(false);
            chart.setPinchZoom(false);
            chart.animateY(1000);
            chart.invalidate();

            // Configuración de la leyenda
            Legend legend = chart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setTextColor(Color.BLACK);
            legend.setXEntrySpace(10f);
            legend.setYEntrySpace(5f);
            legend.setFormSize(14f);
        } catch (Exception e) {
            Log.e("Graficos", "Error al cargar el gráfico: " + e.getMessage());
        }

    }
  public  void validar(){


      if (datosServidor != null && !datosServidor.isEmpty()) {
          // Hay datos en la lista
          new GetReportes().execute();
      } else {
          // No hay datos o la lista es nula
          System.out.println("La lista está vacía o no se ha inicializado.");


      }


  }

    private class GetReportes extends AsyncTask<Void, Void, List<Reportevigilante>> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando datos...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected List<Reportevigilante> doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url_reporte + email+"&control=" + control, ServiceHandler.GET);
            ArrayList<Reportevigilante> controlList1 = new ArrayList<>();
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray frutas = jsonObj.getJSONArray("control");
                    for (int i = 0; i < frutas.length(); i++) {
                        JSONObject catObj = (JSONObject) frutas.get(i);
                        Reportevigilante cat = new Reportevigilante(
                                catObj.getString("id"),
                                catObj.getString("fecha"),
                                catObj.getString("Viv1"),
                                catObj.getString("Viv2"),
                                catObj.getString("Viv3"),
                                catObj.getString("Viv4"),
                                catObj.getString("Viv5")
                                // Assumed to be a String
                        );
                        controlList1.add(cat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return controlList1;
        }

        @Override
        protected void onPostExecute(List<Reportevigilante> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            datosServidor.clear();
            datosServidor.addAll(result);

            cargarGrafico2(result);
            lineal2(result);

            if (datosServidor != null && !datosServidor.isEmpty()) {
                // Hay datos en la lista





 if( checkBox1.isChecked() && checkBox2.isChecked() && checkBox4.isChecked() && checkBox3.isChecked()  && checkBox5.isChecked() ){

     checkBox0.setChecked(true);
 }else{


 }

          //   checkBox0.setChecked(false);

            } else {
                // No hay datos o la lista es nula
                System.out.println("La lista está vacía o no se ha inicializado.");



                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
            }


        }
    }




    private class GetPesoPresion extends AsyncTask<Void, Void, List<Monitoreoagua>> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando datos...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected List<Monitoreoagua> doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url_reporte + email+"&control=" + control, ServiceHandler.GET);
            ArrayList<Monitoreoagua> controlList2 = new ArrayList<>();
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray frutas = jsonObj.getJSONArray("control");


                    for (int i = 0; i < frutas.length(); i++) {
                        JSONObject catObj = (JSONObject) frutas.get(i);

/*
                        if(Valor.equals("presion")){ */
                        Monitoreoagua cat = new Monitoreoagua (


                                    catObj.getString("id"),
                                    catObj.getString("fecha"),
                                    catObj.getString("Viv1"),
                                catObj.getString("Viv2"),
                                catObj.getString("Viv3"),
                                catObj.getString("Viv4"),

                                    catObj.getString("Viv5")



                            );
                            controlList2.add(cat);
                     /*   }

                        else{
                            Pesotallapresion cat = new Pesotallapresion(


                                    catObj.getString("id"),
                                    catObj.getString("fecha"),
                                    catObj.getString("Peso"),
                                    catObj.getString("talla")
                                    // Assumed to be a String
                            );
                            controlList2.add(cat);
                        }*/

                        Log.d("erase", "doInBackground: "+frutas.length());



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return controlList2;
        }

        @Override
        protected void onPostExecute(List<Monitoreoagua> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
           // cargarGrafico2(result);
        }
    }


    private  void  ejecutar_vigilante(String url){


        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "El valor seleccionado es " +fecha.getText()+"\n"+String.valueOf(sistole.getProgress()), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getActivity(), "El valor seleccionado es " + email+fecha+valorSeleccionado2+String.valueOf(sistole.getProgress()), Toast.LENGTH_SHORT);
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

                Map<String, String>parameros=new HashMap<>();
                try {
                    parameros.put("usu_usuario", email.toString());
                    parameros.put("fecha", fecha.getText().toString());
                    parameros.put("control", valorSeleccionado2.toString());
                    parameros.put("descrip", " Dato vacio");
                    parameros.put("v1",String.valueOf(sistole.getProgress()) );
                    parameros.put("v2",String.valueOf(diastole.getProgress()) );
                    parameros.put("v3",String.valueOf(viv3.getProgress()) );

                    parameros.put("v4",String.valueOf(viv4.getProgress()) );
                    parameros.put("v5",String.valueOf(viv5.getProgress()) );


                } catch ( Exception e){
                    Log.d("error en aqyu", "getParams: "+e);
                }
                return parameros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private  void  ejecutar_vigilante2(String url){


        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "El valor seleccionado es " +fecha.getText()+"\n"+String.valueOf(sistole.getProgress()), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getActivity(), "El valor seleccionado es " + email+fecha+valorSeleccionado2+String.valueOf(sistole.getProgress()), Toast.LENGTH_SHORT);
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

                Map<String, String>parameros=new HashMap<>();
                try {

                    if(Valor.equals("presion")){

                        parameros.put("usu_usuario", email.toString());
                        parameros.put("fecha", fecha.getText().toString());

                        parameros.put("sistole", String.valueOf(sistole.getProgress()));
                        parameros.put("diastole",String.valueOf(diastole.getProgress()) );


                    }
                    else{

                        parameros.put("usu_usuario", email.toString());
                        parameros.put("fecha", fecha.getText().toString());
                        parameros.put("Peso", String.valueOf(sistole.getProgress()));
                        parameros.put("talla",String.valueOf(diastole.getProgress()) );

                    }

                } catch ( Exception e){
                    Log.d("error en aqyu", "getParams: "+e);
                }
                return parameros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    public class GraphXAxisValueFormatter extends IndexAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // Aquí debes convertir el valor numérico (índice) en una fecha legible
            // Puedes usar SimpleDateFormat u otras técnicas para formatear la fecha
            // Ejemplo: return formatDateFromTimestamp(value);
            return "";
        }
    }


    private List<String> generatePastSixMonths() {
        List<String> monthsList = new ArrayList<>();

        // Obtener el calendario y establecer la fecha inicial como 6 meses antes de la fecha actual

        // Obtener el año actual

        Calendar calendar = Calendar.getInstance();
        // int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Generar las fechas desde 2020 hasta el año actual y los últimos seis meses por cada año
        for (int year = 2020; year <= currentYear; year++) {
            // Para el año actual, limitar los meses a los actuales
            int maxMonth = (year == currentYear) ? currentMonth : 11;

            for (int month = 0; month <= maxMonth; month += 6) {
                String formattedDate = year + "-" + getMonthAbbreviation(month);
                monthsList.add(formattedDate);
            }
        }
        return monthsList;
    }

    private void mostrarGraficoLineal(int startIndex,List<Reportevigilante> controlList) {
        // Ajustar el índice inicial dentro de límites válidos
        if (startIndex < 0) {
            startIndex = 0;
        } else if (startIndex > controlList.size() - MAX_VISIBLE) {
            startIndex = controlList.size() - MAX_VISIBLE;
        }

        currentIndex = startIndex;

        try {
            // Transformar los datos de controlList en entradas para líneas
            ArrayList<Entry> v2 = new ArrayList<>();
            ArrayList<Entry> v3 = new ArrayList<>();
            ArrayList<Entry> v4 = new ArrayList<>();
            ArrayList<Entry> v5 = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            for (int i = startIndex; i < startIndex + MAX_VISIBLE && i < controlList.size(); i++) {
                try {
                    float valor = Float.parseFloat(controlList.get(i).getValor());
                    float control = Float.parseFloat(controlList.get(i).getControl());
                    float valormin = Float.parseFloat(controlList.get(i).getValormin());
                    float valormax = Float.parseFloat(controlList.get(i).getValormax());
                    v2.add(new Entry(i - startIndex, valor));
                    v3.add(new Entry(i - startIndex, control));
                    v4.add(new Entry(i - startIndex, valormin));
                    v5.add(new Entry(i - startIndex, valormax));
                    labels.add(controlList.get(i).getFecha());
                } catch (NumberFormatException e) {
                    Log.e("LineChart", "Error al convertir valores: " + e.getMessage());
                }
            }

            // Crear conjuntos de datos para líneas
            LineDataSet dataSetv2 = new LineDataSet(v2, "Viv. 2");
            dataSetv2.setColor(Color.rgb(255, 128, 0));
            dataSetv2.setCircleColor(Color.rgb(255, 128, 0));

            LineDataSet dataSetv3 = new LineDataSet(v3, "Viv. 3");
            dataSetv3.setColor(Color.YELLOW);
            dataSetv3.setCircleColor(Color.YELLOW);

            LineDataSet dataSetv4 = new LineDataSet(v4, "Viv. 4");
            dataSetv4.setColor(Color.RED);
            dataSetv4.setCircleColor(Color.RED);

            LineDataSet dataSetv5 = new LineDataSet(v5, "Viv. 5");
            dataSetv5.setColor(Color.DKGRAY);
            dataSetv5.setCircleColor(Color.DKGRAY);

            // Agregar conjuntos al gráfico
            LineData data = new LineData(dataSetv2, dataSetv3, dataSetv4, dataSetv5);
            lineChart.setData(data);

            // Configuración del eje X
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.BLACK);

            // Configurar apariencia general del gráfico
            lineChart.getDescription().setEnabled(false);
            lineChart.setPinchZoom(false);
            lineChart.setDrawGridBackground(false);
            lineChart.animateY(1000);
            lineChart.invalidate();

            // Configuración de la leyenda
            Legend legend = lineChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setTextColor(Color.BLACK);
            legend.setXEntrySpace(10f);
            legend.setYEntrySpace(5f);
            legend.setFormSize(14f);
        } catch (Exception e) {
            Log.e("LineChart", "Error al cargar el gráfico: " + e.getMessage());
        }
    }

    private String getMonthAbbreviation(int month) {
        return new DateFormatSymbols(Locale.getDefault()).getShortMonths()[month];
    }

    private void mostrarPopupFechas(String[] fechas) {
        final int[] fechaSeleccionada = {-1};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleccione una fecha");
        builder.setSingleChoiceItems(fechas, fechaSeleccionada[0], (dialog, which) -> {
            fechaSeleccionada[0] = which;
        });
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            if (fechaSeleccionada[0] != -1) {
                String fechaElegida = fechas[fechaSeleccionada[0]];
                Toast.makeText(requireContext(), "Fecha seleccionada: " + fechaElegida, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "No seleccionaste ninguna fecha.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }
    private void cargarFechasUnicas() {
        if (datosServidor.isEmpty()) {
            Toast.makeText(requireContext(), "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<String> fechasUnicas = new HashSet<>();
        for (Reportevigilante reporte : datosServidor) {
            fechasUnicas.add(reporte.getFecha());
        }

        // Convertir Set a arreglo
        String[] fechasArray = fechasUnicas.toArray(new String[0]);

        // Mostrar el Popup con las fechas
        mostrarPopupFechas(fechasArray);
    }



}