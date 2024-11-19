package com.example.agua;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.agua.ui.home.HomeFragment;


public class Salir extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_salir, container, false);
        salir();

        return  view;
    }

    void salir(){

// Crear una instancia de AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// Configurar el título y el mensaje del diálogo
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que deseas cerrar la sesión?");

// Agregar un botón "Sí" al diálogo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Agregar aquí el código para cerrar la sesión


                SharedPreferences preferences = getContext().getSharedPreferences("prefereciaslogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);


            }
        });

// Agregar un botón "No" al diálogo
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Agregar aquí el código para cancelar la operación


                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment, "HomeFragment");
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

// Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}