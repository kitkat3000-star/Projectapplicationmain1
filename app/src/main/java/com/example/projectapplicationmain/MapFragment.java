package com.example.projectapplicationmain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MapFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    //    Meridian.configure(getActivity(), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsIjo1NjU2MDkwNTExNTQwMjI0LCJ0IjoxNjEwNzI5Nzk4fQ.zDLJQ2aV8JzEAikFjx_Q1iwDlj7-ee4q2ZoViV1qGBA");
      //  EditorKey mapkey = EditorKey.forApp("Basement1", appKey.getID())

         return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        ImageView userprofile = view.findViewById(R.id.userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_map_to_navigation_profile);
            }
        });


    }
}
