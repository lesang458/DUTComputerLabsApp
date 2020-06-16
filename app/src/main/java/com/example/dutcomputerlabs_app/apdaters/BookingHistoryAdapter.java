package com.example.dutcomputerlabs_app.apdaters;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.BookingForDetailed;
import com.example.dutcomputerlabs_app.ui.Booking.BookingFragment;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingViewHolder> {

    private List<BookingForDetailed> list;
    private Context mContext;
    private SimpleDateFormat dateFormat;

    public BookingHistoryAdapter(List<BookingForDetailed> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_item,parent,false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingForDetailed booking = list.get(position);
        holder.booking_room_name.setText(booking.getLab().getName());
        holder.booking_date.setText(dateFormat.format(booking.getBookingDate()));
        holder.booking_time.setText(booking.getStartAt()+" - "+booking.getEndAt());
        holder.booking_status.setText(booking.getStatus());
        if (booking.getFeedback() == null && booking.getStatus().equals("Đã hoàn thành")) {
            holder.btn_send_feedback.setVisibility(View.VISIBLE);
        }else holder.btn_send_feedback.setVisibility(View.INVISIBLE);

        holder.btn_cancel.setVisibility(View.INVISIBLE);
        holder.btn_edit.setVisibility(View.INVISIBLE);

        Date date = booking.getBookingDate();
        Date now = new Date();
        if(now.compareTo(date) < 0 && (booking.getStatus().equals("Đã đặt") || booking.getStatus().equals("Đã cập nhật"))) {
            holder.btn_cancel.setVisibility(View.VISIBLE);
            holder.btn_edit.setVisibility(View.VISIBLE);
        }

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn thay đổi không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = mContext.getSharedPreferences("PREF",Context.MODE_PRIVATE);
                        pref.edit().putInt("editMode",1).apply();
                        pref.edit().putInt("bookingId",booking.getId()).apply();
                        Fragment bookingFragment = new BookingFragment();
                        AppCompatActivity activity = (AppCompatActivity) mContext;
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,bookingFragment).commit();
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

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn hủy lịch đặt này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String token = mContext.getSharedPreferences("PREF",Context.MODE_PRIVATE).getString("token","");
                        ApiUtils.getBookingService().cancelBooking(token,booking.getId())
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            DialogUtils.showDialog("Hủy đặt phòng thành công","Thông báo",mContext);
                                            booking.setStatus("Đã hủy");
                                            notifyDataSetChanged();
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

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView text_date, text_time, text_lab_owner, text_email, text_phone_number, text_computers, text_damaged_computers, text_aircons;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(booking.getLab().getName());

                View view = LayoutInflater.from(mContext).inflate(R.layout.booking_detail_dialog, null);

                text_date = view.findViewById(R.id.text_date);
                text_time = view.findViewById(R.id.text_time);
                text_lab_owner = view.findViewById(R.id.text_lab_owner);
                text_email = view.findViewById(R.id.text_email);
                text_phone_number = view.findViewById(R.id.text_phone_number);
                text_computers = view.findViewById(R.id.text_computers);
                text_damaged_computers = view.findViewById(R.id.text_damaged_computers);
                text_aircons = view.findViewById(R.id.text_aircons);

                text_date.setText(dateFormat.format(booking.getBookingDate()));
                text_time.setText(booking.getStartAt()+" - "+booking.getEndAt());
                text_lab_owner.setText(booking.getLab().getOwner());
                text_email.setText(booking.getLab().getOwnerEmail());
                text_phone_number.setText(booking.getLab().getOwnerPhoneNumber());
                text_computers.setText(booking.getLab().getComputers()+"");
                text_damaged_computers.setText(booking.getLab().getDamagedComputers()+"");
                text_aircons.setText(booking.getLab().getAircons()+"");

                builder.setView(view);

                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final AlertDialog  alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(mContext.getDrawable(R.drawable.border_button));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class BookingViewHolder extends RecyclerView.ViewHolder {

    TextView booking_room_name, booking_date, booking_time, booking_status;
    Button btn_detail;
    ImageButton btn_edit, btn_cancel, btn_send_feedback;
     BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        booking_room_name = itemView.findViewById(R.id.history_booking_room_name);
        booking_date = itemView.findViewById(R.id.history_booking_date);
        booking_time = itemView.findViewById(R.id.history_booking_time);
        booking_status = itemView.findViewById(R.id.history_booking_status);
        btn_detail = itemView.findViewById(R.id.btn_detail);
        btn_edit = itemView.findViewById(R.id.btn_edit);
        btn_cancel = itemView.findViewById(R.id.btn_cancel);
        btn_send_feedback = itemView.findViewById(R.id.btn_send_feedback);
    }
}

