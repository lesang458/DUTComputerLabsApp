package com.example.dutcomputerlabs_app.apdaters;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.BookingForDetailed;
import com.example.dutcomputerlabs_app.models.FeedbackForDetailed;
import com.example.dutcomputerlabs_app.models.FeedbackForInsert;
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
                        Navigation.findNavController((Activity)mContext,R.id.nav_host_fragment).navigate(R.id.nav_booking);
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
                                        if(response.isSuccessful()) {
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
                        dialog.dismiss();
                    }
                });

                final AlertDialog  alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(mContext.getDrawable(R.drawable.border_button));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
            }
        });

        holder.btn_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView text_booking_room, text_booking_date, text_booking_time;
                final EditText feedback;
                View view = LayoutInflater.from(mContext).inflate(R.layout.send_feedback_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Gửi phản hồi");

                text_booking_room = view.findViewById(R.id.text_booking_room);
                text_booking_date = view.findViewById(R.id.text_booking_date);
                text_booking_time = view.findViewById(R.id.text_booking_time);
                feedback = view.findViewById(R.id.feedback);

                text_booking_room.setText(booking.getLab().getName());
                text_booking_date.setText(dateFormat.format(booking.getBookingDate()));
                text_booking_time.setText(booking.getStartAt()+" - "+booking.getEndAt());

                builder.setView(view);

                builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button send = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button cancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                send.setBackground(mContext.getDrawable(R.drawable.border_button));
                send.setTextSize(20);
                send.setTextColor(Color.BLACK);

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(feedback.getText().toString().trim().equals("")){
                            Toast.makeText(mContext,"Vui lòng nhập phản hồi",Toast.LENGTH_SHORT).show();
                        }else{
                            String token = mContext.getSharedPreferences("PREF",Context.MODE_PRIVATE).getString("token","");
                            FeedbackForInsert feedbackForInsert = new FeedbackForInsert(booking.getId(),feedback.getText().toString().trim());
                            ApiUtils.getFeedbackService().addFeedback(token,feedbackForInsert)
                                    .enqueue(new Callback<FeedbackForDetailed>() {
                                        @Override
                                        public void onResponse(Call<FeedbackForDetailed> call, Response<FeedbackForDetailed> response) {
                                            if(response.isSuccessful()) {
                                                alertDialog.dismiss();
                                                DialogUtils.showDialog("Gửi phản hồi thành công","Thông báo",mContext);
                                                booking.setFeedback(response.body());
                                                notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<FeedbackForDetailed> call, Throwable t) {
                                            alertDialog.dismiss();
                                            DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",mContext);
                                        }
                                    });
                        }
                    }
                });

                cancel.setBackground(mContext.getDrawable(R.drawable.border_button));
                cancel.setTextSize(20);
                cancel.setTextColor(Color.BLACK);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cancel.getLayoutParams();
                params.setMargins(0, 0, 30, 0); //left, top, right, bottom
                cancel.setLayoutParams(params);

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
