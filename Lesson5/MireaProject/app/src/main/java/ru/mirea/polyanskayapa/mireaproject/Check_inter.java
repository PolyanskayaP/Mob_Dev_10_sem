package ru.mirea.polyanskayapa.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Check_inter#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.widget.Button;
import android.widget.EditText;

public class Check_inter extends Fragment {

    public Check_inter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_Check_inter, container, false);

        Button button = view.findViewById(R.id.button3);
        EditText editText = view.findViewById(R.id.editTextText3);

        button.setOnClickListener(v -> {
            if (isCheck_interAvailable()) {
                editText.setText("Интернет работает");
                startMy_calish();
            } else {
                editText.setText("Интернет отсутствует");
            }
        });

        return view;
    }

    private boolean isCheck_interAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void startMy_calish() {
        WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(My_calish.class).build();
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest);
    }
}