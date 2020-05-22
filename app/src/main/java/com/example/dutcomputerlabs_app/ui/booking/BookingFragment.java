package com.example.dutcomputerlabs_app.ui.booking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.apdaters.ComputerLabAdapter;
import com.example.dutcomputerlabs_app.models.ComputerLab;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {

    private TextView session;
    private Spinner spinner_session;
    private RecyclerView recyclerView;

    private ComputerLabAdapter computerLabAdapter;
    private ArrayAdapter<String> spinner_session_adapter;
    private List<String> list_sessions;
    private List<ComputerLab> computerLabList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_sessions = new ArrayList<>();
        list_sessions.add("Buổi sáng");
        list_sessions.add("Buổi chiều");

        computerLabList = new ArrayList<>();
        computerLabList.add(new ComputerLab(1,"C101","Tốt",40,5,3));
        computerLabList.add(new ComputerLab(2,"C102","Tốt",41,6,4));
        computerLabList.add(new ComputerLab(3,"C103","Tốt",42,7,5));
        computerLabList.add(new ComputerLab(4,"C104","Tốt",43,8,4));
        computerLabList.add(new ComputerLab(5,"C105","Tốt",44,9,5));
        computerLabList.add(new ComputerLab(6,"C106","Tốt",45,5,3));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        spinner_session = root.findViewById(R.id.spinner_session);
        session = root.findViewById(R.id.session);
        recyclerView = root.findViewById(R.id.recyclerview_labs);

        computerLabAdapter = new ComputerLabAdapter(computerLabList,getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(computerLabAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner_session_adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_item,list_sessions);
        spinner_session_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_session.setAdapter(spinner_session_adapter);
        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session.setText(spinner_session.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
