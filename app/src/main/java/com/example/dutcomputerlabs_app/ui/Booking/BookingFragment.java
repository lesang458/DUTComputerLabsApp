package com.example.dutcomputerlabs_app.ui.Booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.apdaters.ComputerLabAdapter;
import com.example.dutcomputerlabs_app.models.ComputerLabForList;
import com.example.dutcomputerlabs_app.network.services.BookingService;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingFragment extends Fragment {

    private TextView booking_date,session,start,end;
    private Button btn_search;
    private ImageButton btn_booking_date;
    private Spinner spinner_session,spinner_start, spinner_end;
    private RecyclerView recyclerView;

    private BookingService bookingService;
    private ComputerLabAdapter computerLabAdapter;
    private ArrayAdapter<String> spinner_session_adapter, spinner_time_adapter;
    private List<String> list_sessions,list_time;
    private List<ComputerLabForList> computerLabList;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int day,month,year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_sessions = new ArrayList<>();
        list_sessions.add("Buổi sáng");
        list_sessions.add("Buổi chiều");

        list_time = new ArrayList<>();

        computerLabList = new ArrayList<>();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        booking_date = root.findViewById(R.id.booking_date);
        session = root.findViewById(R.id.session);
        start = root.findViewById(R.id.start_time);
        end = root.findViewById(R.id.end_time);
        btn_search = root.findViewById(R.id.btn_search);
        btn_booking_date = root.findViewById(R.id.btn_booking_date);
        spinner_session = root.findViewById(R.id.spinner_session);
        spinner_start = root.findViewById(R.id.spinner_start);
        spinner_end = root.findViewById(R.id.spinner_end);
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

        booking_date.setText(dateFormat.format(new Date()).toString());

        spinner_session_adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_item,list_sessions);
        spinner_session_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_session.setAdapter(spinner_session_adapter);
        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session.setText(spinner_session.getSelectedItem().toString());
                try {
                    setTimeList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Date date = dateFormat.parse(year+"-"+(month+1)+"-"+dayOfMonth);
                            booking_date.setText(dateFormat.format(date));
                            setTimeList();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startAt = start.getText().toString();
                String endAt = end.getText().toString();
                if(startAt.equals("")) {
                    DialogUtils.showDialog("Ngày không hợp lệ. Vui lòng chọn ngày khác.","Lỗi",getActivity());
                    computerLabList.clear();
                    computerLabAdapter.notifyDataSetChanged();
                } else {
                    if(Integer.parseInt(startAt) > Integer.parseInt(endAt)) {
                        DialogUtils.showDialog("Tiết bắt đầu lớn hơn tiết kết thúc. Vui lòng chọn lại.","Lỗi",getActivity());
                        computerLabList.clear();
                        computerLabAdapter.notifyDataSetChanged();
                    } else {
                        SharedPreferences pref = getContext().getSharedPreferences("PREF", Context.MODE_PRIVATE);
                        int editMode = pref.getInt("editMode",0);
                        String token = pref.getString("token","");
                        bookingService = ApiUtils.getBookingService();
                        String bookingDate = booking_date.getText().toString();
                        bookingService.search(token,bookingDate,Integer.parseInt(startAt),Integer.parseInt(endAt),editMode)
                                .enqueue(new Callback<List<ComputerLabForList>>() {
                                @Override
                                public void onResponse(Call<List<ComputerLabForList>> call, Response<List<ComputerLabForList>> response) {
                                    if(response.isSuccessful()) {
                                        computerLabList.clear();
                                        computerLabList.addAll(response.body());
                                        computerLabAdapter.setBookingDate(bookingDate);
                                        computerLabAdapter.setStartAt(startAt);
                                        computerLabAdapter.setEndAt(endAt);
                                        computerLabAdapter.notifyDataSetChanged();
                                    }else {
                                        DialogUtils.showDialog(response.toString(),"Lỗi",getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ComputerLabForList>> call, Throwable t) {
                                    DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
                                }
                            });

                        }
                    }
                }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getSharedPreferences("PREF",Context.MODE_PRIVATE).edit().remove("editMode").apply();
    }

    public void setSpinnerAdapter(){
        start.setText("");
        end.setText("");
        spinner_time_adapter= new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,list_time);
        spinner_time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_start.setAdapter(spinner_time_adapter);
        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start.setText(spinner_start.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_end.setAdapter(spinner_time_adapter);
        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end.setText(spinner_end.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setTimeList() throws ParseException {
        if(list_time!=null) {
            list_time.clear();
        }
        Date date = dateFormat.parse(booking_date.getText().toString());
        Date now = dateFormat.parse(dateFormat.format(new Date()));
        if(date.compareTo(now) > 0) {
            if(session.getText().toString().equals("Buổi sáng")) {
                for(int i = 1; i <= 5; i++) {
                    list_time.add(""+i);
                }
            }else {
                for(int i = 6; i <=10; i++) {
                    list_time.add(i+"");
                }
            }
        }else if(date.compareTo(now) == 0) {
            calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(session.getText().toString().equals("Buổi sáng")) {
                if(hour >= 6 && hour < 11) {
                    for (int i = 1; i < 12 - hour; i++) {
                        list_time.add((hour - 6 + i) + "");
                    }
                }else if(hour < 6) {
                    for(int i = 1; i <= 5; i++) {
                        list_time.add(""+i);
                    }
                }
            }else {
                if(hour >= 12 && hour < 17) {
                    for (int i = 1; i < 18 - hour; i++) {
                        list_time.add((hour - 7 + i) + "");
                    }
                }else if(hour < 12){
                    for(int i = 6; i <=10; i++) {
                        list_time.add(i+"");
                    }
                }
            }
        }

        setSpinnerAdapter();
    }
}
