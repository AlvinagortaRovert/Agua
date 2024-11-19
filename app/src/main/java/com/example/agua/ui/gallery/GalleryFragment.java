package com.example.agua.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agua.R;
import com.example.agua.databinding.FragmentGalleryBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Map;

public class GalleryFragment extends Fragment {

    EditText codigo, nombre, apellido, especialiad, hospital;
    Button agregar,boorar, buscar, modificar;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
/*
        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

 */
        codigo= (EditText) view.findViewById(R.id.doccodigo);
        nombre= (EditText) view.findViewById(R.id.docedtnombre);
        apellido= (EditText) view.findViewById(R.id.docedtapellido);
        especialiad= (EditText) view.findViewById(R.id.docedespecialidad);
        hospital= (EditText) view.findViewById(R.id.docedthospital);
        agregar =(Button) view.findViewById(R.id.docbtnagregar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutar("https://admin.marcaancash.com/api/doctor.php");
            }
        });



        return view;
    }


    private void ejecutar(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getActivity(), "operacion exitosa", Toast.LENGTH_SHORT).show();
                codigo.setText("");
                apellido.setText("");
                nombre.setText("");
                especialiad.setText("");
                hospital.setText("");
                codigo.setFocusable(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        } ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("Codigo",codigo.getText().toString());
                parametros.put("Nombre",nombre.getText().toString());
                parametros.put("Apellidos",apellido.getText().toString());
                parametros.put("Especialidad",especialiad.getText().toString());
                parametros.put("Clinhospital",hospital.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
