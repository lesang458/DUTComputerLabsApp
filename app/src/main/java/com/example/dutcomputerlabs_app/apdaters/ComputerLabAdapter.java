package com.example.dutcomputerlabs_app.apdaters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.BookingForInsert;
import com.example.dutcomputerlabs_app.models.ComputerLabForList;
import com.example.dutcomputerlabs_app.network.services.BookingService;
import com.example.dutcomputerlabs_app.ui.BookingHistory.BookingHistoryFragment;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComputerLabAdapter extends RecyclerView.Adapter<ComputerLabViewHolder> {
    private List<ComputerLabForList> list;
    private Context mContext;
    private String bookingDate, startAt, endAt;
    private BookingService bookingService;

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public ComputerLabAdapter(List<ComputerLabForList> list, Context mContext){
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ComputerLabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.computerlab_item,parent,false);
        return new ComputerLabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComputerLabViewHolder holder, int position) {
        ComputerLabForList computerLab = list.get(position);
        holder.room.setText(computerLab.getName());
        holder.computers.setText(computerLab.getComputers()+"");
        holder.damaged_computer.setText(computerLab.getDamagedComputers()+"");
        holder.air_conditions.setText(computerLab.getAircons()+"");
        if(computerLab.getEditMode() == 1) {
            holder.btn_update_booking.setVisibility(View.VISIBLE);
            holder.btn_booking.setVisibility(View.INVISIBLE);
        }else {
            holder.btn_update_booking.setVisibility(View.INVISIBLE);
            holder.btn_booking.setVisibility(View.VISIBLE);
        }

        holder.btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn đặt phòng này không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingService = ApiUtils.getBookingService();
                        Date date = new Date();
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            date = dateFormat.parse(bookingDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String token = mContext.getSharedPreferences("PREF",Context.MODE_PRIVATE).getString("token","");
                        bookingService.addBooking(token,new BookingForInsert(computerLab, date, Integer.parseInt(startAt),Integer.parseInt(endAt),holder.description.getText().toString().trim()))
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            DialogUtils.showDialog("Đặt phòng thành công","Thông báo",mContext);
                                            clearData();
                                        }else {
                                            DialogUtils.showDialog(response.toString(),"Lỗi",mContext);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",mContext);
                                    }
                                });
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.btn_update_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn thay đổi?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingService = ApiUtils.getBookingService();
                        Date date = new Date();
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            date = dateFormat.parse(bookingDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences pref = mContext.getSharedPreferences("PREF", Context.MODE_PRIVATE);
                        String token = pref.getString("token","");
                        int id = pref.getInt("bookingId",0);
                        bookingService.updateBooking(token, id, new BookingForInsert(computerLab, date, Integer.parseInt(startAt),Integer.parseInt(endAt),holder.description.getText().toString().trim()))
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            DialogUtils.showDialog("Cập nhật thành công","Thông báo",mContext);
                                            clearData();
                                            pref.edit().remove("editMode").apply();
                                            Fragment fragment = new BookingHistoryFragment();
                                            AppCompatActivity activity = (AppCompatActivity) mContext;
                                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                                        }else {
                                            DialogUtils.showDialog(response.toString(),"Lỗi",mContext);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",mContext);
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }
}
class ComputerLabViewHolder extends RecyclerView.ViewHolder{

    TextView room, computers, damaged_computer, air_conditions;
    EditText description;
    Button btn_booking, btn_update_booking, btn_feedback;

    ComputerLabViewHolder(@NonNull View itemView) {
        super(itemView);
        room = itemView.findViewById(R.id.room_name);
        computers = itemView.findViewById(R.id.computers);
        damaged_computer = itemView.findViewById(R.id.damaged_computers);
        air_conditions = itemView.findViewById(R.id.air_conditions);
        description = itemView.findViewById(R.id.description);
        btn_booking = itemView.findViewById(R.id.btn_booking);
        btn_update_booking = itemView.findViewById(R.id.btn_update_booking);
        btn_feedback = itemView.findViewById(R.id.btn_feedback);
    }
}