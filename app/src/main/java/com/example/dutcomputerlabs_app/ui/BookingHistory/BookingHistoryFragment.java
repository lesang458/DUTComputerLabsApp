package com.example.dutcomputerlabs_app.ui.BookingHistory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.apdaters.BookingHistoryAdapter;
import com.example.dutcomputerlabs_app.models.BookingForDetailed;
import com.example.dutcomputerlabs_app.network.services.BookingService;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryFragment extends Fragment {

    private TextView text_page;
    private Spinner spinner_page;
    private RecyclerView recyclerView;

    private BookingService bookingService;
    private ArrayAdapter<String> spinner_page_adapter;
    private List<String> list_pages;
    private BookingHistoryAdapter bookingHistoryAdapter;
    private List<BookingForDetailed> list_bookings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_pages = new ArrayList<>();
        list_bookings = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_booking_history, container, false);
        text_page = root.findViewById(R.id.text_page);
        spinner_page = root.findViewById(R.id.spinner_page);
        recyclerView = root.findViewById(R.id.recyclerview_history);

        bookingHistoryAdapter = new BookingHistoryAdapter(list_bookings,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(bookingHistoryAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("PREF",Context.MODE_PRIVATE);
        String token = pref.getString("token","");
        bookingService = ApiUtils.getBookingService();
        bookingService.getTotalPages(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body().string());
                        int page = object.getInt("totalPages");
                        if(page == 1){
                            text_page.setVisibility(View.INVISIBLE);
                            spinner_page.setVisibility(View.INVISIBLE);
                            bookingService.getBookings(token,page).enqueue(new Callback<List<BookingForDetailed>>() {
                                @Override
                                public void onResponse(Call<List<BookingForDetailed>> call, Response<List<BookingForDetailed>> response) {
                                    if(response.isSuccessful()){
                                        list_bookings.clear();
                                        list_bookings.addAll(response.body());
                                        bookingHistoryAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<BookingForDetailed>> call, Throwable t) {
                                    DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
                                }
                            });
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
            }
        });

    }
}
