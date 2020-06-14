package com.example.dutcomputerlabs_app.apdaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.BookingForDetailed;

import java.text.SimpleDateFormat;
import java.util.List;

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
        if (booking.getFeedback() != null) {
            holder.btn_send_feedback.setVisibility(View.INVISIBLE);
        }
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

