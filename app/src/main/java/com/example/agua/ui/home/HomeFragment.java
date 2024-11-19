package com.example.agua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.agua.R;
import com.example.agua.databinding.FragmentHomeBinding;
/*
import com.example.agua.ui.datos.AtencionMedicaFragment;
import com.example.agua.ui.datos.Citas;
import com.example.salud.ui.datos.MolReportoFragment;
import com.example.salud.ui.datos.SintoMolestiFragment;
import com.example.salud.ui.datos.Vigilancia;
import com.example.salud.ui.datos.VigilanciaSalud;
*/

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private ImageView citas,vigilancia,atencionmedica,sitomas;
    /*
      public View onCreateView(@NonNull LayoutInflater inflater,
                               ViewGroup container, Bundle savedInstanceState) {
          HomeViewModel homeViewModel =
                  new ViewModelProvider(this).get(HomeViewModel.class);

          binding = FragmentHomeBinding.inflate(inflater, container, false);
          View root = binding.getRoot();*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        citas= view.findViewById(R.id.imageViewcitas);
        vigilancia= view.findViewById(R.id.imageViewvigilancia);
        atencionmedica= view.findViewById(R.id.imageViewatencionmedica);
        sitomas= view.findViewById(R.id.imageViewsintomas);


        /*
        citas= root.findViewById(R.id.imageViewcitas);
        vigilancia= root.findViewById(R.id.imageViewvigilancia);
        atencionmedica= root.findViewById(R.id.imageViewatencionmedica);
        sitomas= root.findViewById(R.id.imageViewsintomas);
*/

/*
        vigilancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new VigilanciaSalud();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(); // Remueve el fragmento anterior del backstack
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        atencionmedica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AtencionMedicaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(); // Remueve el fragmento anterior del backstack
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        sitomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SintoMolestiFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(); // Remueve el fragmento anterior del backstack
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        citas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Citas();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(); // Remueve el fragmento anterior del backstack
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menuprincipal, fragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
*/

        //  final TextView textView = binding.textHome;                                     Este es un fragmeto aca esta su valor                     -------------------->

        //Toast.makeText(getActivity(), "Hola papa"+Email.getText().toString(), Toast.LENGTH_SHORT).show();
        //  homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}