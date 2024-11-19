package com.example.agua.ui.datos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.agua.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class datossec extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView imageView2;
    private TextView textViewId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datossec, container, false);

        imageView2 = view.findViewById(R.id.imageView2);
        textViewId = view.findViewById(R.id.textview_id);

        imageView2.setOnClickListener(v -> openGallery());

        textViewId.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                confirmAndSendData();
            } else {
                Toast.makeText(getContext(), "Por favor, selecciona una imagen primero", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageView2.setImageURI(selectedImageUri); // Muestra la imagen seleccionada en el ImageView
        }
    }

    private void confirmAndSendData() {
        // Muestra una confirmación al usuario
        textViewId.setText("¿Estás seguro de que quieres guardar?");
        textViewId.setOnClickListener(v -> {
            uploadImageToServer();
        });
    }

    private void uploadImageToServer() {
        String url = "https://aguapotable.app-organizadoor.com/api/guardar_datos.php";


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("imageUri", selectedImageUri.toString());// En este caso solo enviamos la URI como ejemplo

            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    response -> Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show(),
                    error -> {
                        Log.e("Error", "Error al enviar datos: " + error.getMessage());
                        if (error.networkResponse != null) {
                            Log.e("Error", "Código de respuesta: " + error.networkResponse.statusCode);
                            Log.e("Error", "Respuesta: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(getContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
            ));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void onResponse(JSONObject response) {
        try {
            int statusCode = response.getInt("statusCode"); // Si el servidor responde con un código de estado
            if (statusCode == 200) {
                Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
