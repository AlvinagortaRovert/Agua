package com.example.agua;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecuperaContra extends AppCompatActivity {

    TextView btnaceptar;

    TextInputLayout aviso1;
    TextInputEditText caja;

    String valor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_contra);


        btnaceptar=findViewById(R.id.Textviewrecuperar);

        aviso1=( TextInputLayout )findViewById(R.id.aviso);
        caja=findViewById(R.id.contenidoemail);



        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = String.valueOf(caja.getText());

                boolean esCorreoValido = validarCorreo(valor);
                //  Toast.makeText(RecuperaContra.this, "Correo "+valor,  Toast.LENGTH_SHORT).show();
                if(valor.isEmpty()  ){

                    Toast.makeText(RecuperaContra.this, "Correo vacio o  incorecto",  Toast.LENGTH_SHORT).show();
                    caja.findFocus();

                    ColorStateList colorStateList3 =    ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                    aviso1.setHelperText("Ingrese su correo");
                    aviso1.setHelperTextColor(colorStateList3);
                }

                else {
                    if (esCorreoValido) {

                        ColorStateList colorStateList2 = ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.material_blue_grey_950));
                        aviso1.setHelperText("Se ha enviado un mensaje de recuperion a su correo");
                        aviso1.setHelperTextColor(colorStateList2);
                        caja.setText("");

                    }else{


                        Toast.makeText(RecuperaContra.this, "Correo vacio o  incorecto",  Toast.LENGTH_SHORT).show();
                        caja.findFocus();

                        ColorStateList colorStateList3 =    ColorStateList.valueOf(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                        aviso1.setHelperText("Ingrese su correo");
                        aviso1.setHelperTextColor(colorStateList3);
                    }


                }
            }
        });







    }

    public static boolean validarCorreo(String email) {
        // Patrón de expresión regular para validar direcciones de correo electrónico
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compila el patrón
        Pattern pattern = Pattern.compile(regex);

        // Crea un objeto Matcher
        Matcher matcher = pattern.matcher(email);

        // Verifica si el correo coincide con el patrón
        return matcher.matches();
    }
}