package com.example.projectapplicationmain.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapplicationmain.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ScanBarcodeFragment extends Fragment {

    FirebaseFirestore firenode = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userID =  mAuth.getCurrentUser().getUid();
    DocumentReference dREF;
//   docREF = firenode.collection("users").document(userID);
String OCR_License_INPUt;
String Verified = "Z 12345";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanbarcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OCR_License_INPUt = "Z 12345";
        NavController navController = Navigation.findNavController(view);

        dREF = firenode.collection("users").document(userID);
        dREF.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Verified =  value.getString("License_Num");
            }
        });



        Button button = view.findViewById(R.id.scanbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Verified.equals(OCR_License_INPUt)) {
                    Toast.makeText(getActivity(), "Verification successful", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_navigation_scanbarcode_to_navigation_parking);
                } else {
                    Toast.makeText(getActivity(), "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ImageView imageView = view.findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_scanbarcode_to_navigation_home);
            }
        });

        ImageView userprofile = view.findViewById(R.id.userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_scanbarcode_to_navigation_profile);
            }
        });


    }
}
