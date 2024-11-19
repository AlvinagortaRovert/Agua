package com.example.agua.ui.datos;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agua.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Reportes extends Fragment {

    LineChart lineChart;
    HorizontalBarChart horizontalBarChart;
    Description description = new Description();
    BarChart chart;
    Spinner spinner, spinner2;
    public TextView txtemail;
    public String valorSeleccionado2, email;
    private  String url_control="https://app-organizadoor.com/api/listarcontrol.php?usuario=",
            urlreport="https://app-organizadoor.com/api/listarvigilante.php?usuario=";
    ProgressDialog pDialog;
    List<PieEntry> pietEntryList;
    private List<Controle> controlList = new ArrayList<>();
    private List<Reportevigilante> controlList1 = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);

        pietEntryList=new ArrayList<>();
        //    LineDataSet lineDataSet = new LineDataSet();
        LineData lineData = new LineData();
        txtemail = getActivity().findViewById(R.id.txtemail);
        email = txtemail.getText().toString(); // obniene correo
        spinner = view.findViewById(R.id.spinnerreport);
        horizontalBarChart = view.findViewById(R.id.chart11);
        lineChart=view.findViewById(R.id.pastel24);
        spinner2 = view.findViewById(R.id.spinnerreport2);
        String valorEnviado = null;
        if (getArguments() != null) {
            valorEnviado = getArguments().getString("clave_valor");
        }

        if (valorEnviado != null) {
            Toast.makeText(getActivity(), "valor recibido"+valorEnviado, Toast.LENGTH_SHORT).show();
            //  seleccionarValorEnSpinner(spinner, valorEnviado);
        }
        //    serValue();                            descativado
        chart = view.findViewById(R.id.bar_chart);

        List<String> monthsList = generatePastSixMonths();

        // Crear un adaptador para el Spinner y asignarle la lista de fechas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, monthsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinner2.setAdapter(adapter);

        Presion();
        new Getfrutas().execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Casos(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
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

    private String getMonthAbbreviation(int month) {
        return new DateFormatSymbols(Locale.getDefault()).getShortMonths()[month];
    }

    private class Getfrutas extends AsyncTask<Void, Void, Void> {
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
            String json = jsonParser.makeServiceCall(url_control+email, ServiceHandler.GET);
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
                                    catObj.getString("private")
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




    private class Getreportes extends AsyncTask<Void, Void, Void> {
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
            String json = jsonParser.makeServiceCall(url_control+email, ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj
                                .getJSONArray("vigilante");
                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);

                            Reportevigilante cat = new Reportevigilante(

                                    catObj.getString("idvigilancia"),
                                    catObj.getString("fecha"),
                                    catObj.getString("valor"),
                                    catObj.getString("control"),
                                    catObj.getString("unidades"),
                                    catObj.getString("valormin"),
                                    catObj.getString("valormax")
                            );
                            controlList1.add(cat);
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
            // populateSpinner();
        }
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
        spinner.setAdapter(spinnerAdapter);
        // Toast.makeText(getActivity(), "cantid de datos"+controlList.size(), Toast.LENGTH_SHORT).show();
    }

    public Reportes() {
        // Required empty public constructor
    }
    public void  Casos(String variable){
        String Valor=variable;

        switch (variable) {
            case "Presion Arterial":
                // Acciones a realizar si opcion es igual a 1
                description.setText("Presion Arterial");
                //  Toast.makeText(getActivity(),"Selecciono la opcion "+variable, Toast.LENGTH_SHORT).show();
                chart.setDescription(description);
                Presion();
                break;
            case "Trigliceridos":
                // Acciones a realizar si opcion es igual a 2
                //  Toast.makeText(getActivity(),"Selecciono la opcion "+variable, Toast.LENGTH_SHORT).show();
                description.setText("Trigliceridos");
                chart.setDescription(description);
                Trigliceridos();
                break;
            case "Glucosa":
                // Acciones a realizar si opcion es igual a 3
                // Toast.makeText(getActivity(),"Selecciono la opcion "+variable, Toast.LENGTH_SHORT).show();
                description.setText("Glucosa");
                chart.setDescription(description);
                Glucosa();
                break;

            case "Colesterol":
                // Acciones a realizar si opcion es igual a 1
                Colesterol();
                description.setText("Colesterol");
                chart.setDescription(description);
                break;
            case "Hemoglobina":
                // Acciones a realizar si opcion es igual a 2
                description.setText("Hemoglobina");
                chart.setDescription(description);
                Frecuencia();
                break;
            case "Frecuencia cardiaca":
                description.setText("Frecuencia cardiaca");
                chart.setDescription(description);
                Hemoglobina();
                // Acciones a realizar si opcion es igual a 2
                break;
            default:
                description.setText(variable);
                chart.setDescription(description);
                otros();
                // Acciones a realizar si opcion no coincide con ningún caso anterior
                break;
        }

    }

    private void Presion(){
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
        BarDataSet gastosDataSet = new BarDataSet(gastosEntries, "Sistole");



// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Diastole");
        dataSet.setColor(Color.BLUE);
        BarData data = new BarData(dataSet);
        chart.setData(data);

        gastosDataSet.setColor(Color.RED);
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

    private void Hemoglobina(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Hemoglobina");
        dataSet.setColor(Color.RED);
        BarData data = new BarData(dataSet);
        chart.setData(data);



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

    private void otros(){

        new Getreportes().execute();
        Toast.makeText(getActivity(), "taamño "+controlList1.size(), Toast.LENGTH_SHORT).show();
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, spinner.getSelectedItem().toString());
        dataSet.setColor(Color.BLUE);
        BarData data = new BarData(dataSet);
        chart.setData(data);



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
    private void Trigliceridos(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 111));
        entries.add(new BarEntry(3, 121));
        entries.add(new BarEntry(5, 510));


        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Trigliceridos");
        dataSet.setColor(Color.GREEN);
        BarData data = new BarData(dataSet);
        chart.setData(data);



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

    private void Frecuencia(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        entries.add(new BarEntry(5, 121));
        entries.add(new BarEntry(4, 115));
        entries.add(new BarEntry(6, 128));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Frecuencia cardiaca");
        dataSet.setColor(Color.rgb	(255, 250, 205));
        BarData data = new BarData(dataSet);
        chart.setData(data);



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
    private void Glucosa(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        entries.add(new BarEntry(5, 130));
        entries.add(new BarEntry(6, 105));
        entries.add(new BarEntry(7, 118));
        entries.add(new BarEntry(8, 122));
        entries.add(new BarEntry(4, 112));
        entries.add(new BarEntry(9, 120));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Glucosa");
        dataSet.setColor(Color.rgb	(128, 0, 128));
        BarData data = new BarData(dataSet);
        chart.setData(data);



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
    private void Colesterol(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 111));
        entries.add(new BarEntry(1, 121));
        entries.add(new BarEntry(2, 115));
        entries.add(new BarEntry(3, 128));
        entries.add(new BarEntry(4, 115));
        entries.add(new BarEntry(5, 128));
        // Crea los2da tos  datos del gráfico en forma de entradas de BarEntry




// Configura los datos en el conjunto de datos de BarData
        BarDataSet dataSet = new BarDataSet(entries, "Colesterol");
        dataSet.setColor(Color.rgb(255, 128, 0));
        BarData data = new BarData(dataSet);
        chart.setData(data);



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
    private void  serValue(){



        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 2));
        entries.add(new Entry(2, 3));
        entries.add(new Entry(3, 4));
        entries.add(new Entry(4, 5));
        entries.add(new Entry(5, 3));
        entries.add(new Entry(6, 4));
        entries.add(new Entry(7, 5));

        LineDataSet dataSet = new LineDataSet(entries, "Estadisiticas");
        dataSet.setColor(Color.BLUE);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();



        horizontalBarChart.getDescription().setEnabled(false);

// Configurar la animación
        horizontalBarChart.animateY(1000);
        // Configurar los datos
        List<BarEntry> entries1 = new ArrayList<>();
        entries.add(new BarEntry(1, 10));
        entries.add(new BarEntry(2, 20));
        BarDataSet dataSet1 = new BarDataSet(entries1, "Datos de ejemplo");
        BarData data = new BarData(dataSet1);
        horizontalBarChart.setData(data);

// Configurar el eje X
        XAxis xAxis =   horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

// Configurar el eje Y
        YAxis yAxis =   horizontalBarChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setGranularity(1f);
        yAxis.setLabelCount(5);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
    }
    public static Reportes newInstance(String valorSeleccionado) {
        Reportes fragment = new Reportes();
        Bundle args = new Bundle();
        args.putString("clave_valor", valorSeleccionado); // "clave_valor" es una clave que usarás para recuperar el valor seleccionado más tarde
        fragment.setArguments(args);
        return fragment;
    }

}