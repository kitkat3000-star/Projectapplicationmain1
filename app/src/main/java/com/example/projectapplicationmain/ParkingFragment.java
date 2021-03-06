package com.example.projectapplicationmain;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ParkingFragment extends Fragment {

    private Spinner zonespinner;
    final int Zone_A = 5;
    final  int Zone_B = 5;
    final int Zone_C = 5;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String Verified;
    int i;
    boolean statusCheck;
    DocumentReference dREF;
    String userID = mAuth.getCurrentUser().getUid();

    FirebaseFirestore Fstore = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ZONE SPINNER
        zonespinner = view.findViewById(R.id.zonespinner);
        NavController navController = Navigation.findNavController(view);
        Button button = view.findViewById(R.id.parkingbutton);

        //END OF CODE

        Button button1 = view.findViewById(R.id.yesvalet);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Valet option selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = view.findViewById(R.id.novalet);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Valet option deselected", Toast.LENGTH_SHORT).show();
                dREF = Fstore.collection("Users").document(userID);
                dREF.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Verified = value.getString("licensePlate");
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (zonespinner.getSelectedItemPosition()) {
                            case 0:
                             //Toast.makeText(getActivity(), zonespinner.getSelectedItem().toString() + " Allocated spot " , Toast.LENGTH_LONG).show();
                                fillAvailableSlotZoneA();
                                navController.navigate(R.id.action_navigation_parking_to_navigation_map);
                                break;
                            case 1:
//                                Toast.makeText(getActivity(), zonespinner.getSelectedItem().toString() + " Allocated spot" + fillAvailableSlotHeriott(licensePlateNumber) , Toast.LENGTH_LONG).show();
//                                navController.navigate(R.id.action_navigation_parking_to_navigation_map);
                                break;

                        }
                    }

                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please select yes/no for Valet", Toast.LENGTH_SHORT).show();
            }
        });



        ImageView imageView = view.findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_parking_to_navigation_scanbarcode);
            }
        });

        ImageView userprofile = view.findViewById(R.id.userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_parking_to_navigation_profile);
            }
        });

    }


    public void fillAvailableSlotZoneA()
    {
        Fstore.collection("Parking Lot").document("UOWD").collection("Zone A")
                .whereEqualTo("status", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getActivity(), "Spot Allocated " + document.getId(), Toast.LENGTH_SHORT).show();
                                String parkingspot = document.getId();
                                Fstore.collection("Parking Lot").document("UOWD").collection("Zone A").document(parkingspot)
                                        .update("status", false);
                                Fstore.collection("Parking Lot").document("UOWD").collection("Zone A").document(parkingspot)
                                        .update("reservationId", Verified);

                                break;
                            }
                        } else {
                            Log.d("ff", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



}
