package com.example.dutcomputerlabs_app.apdaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.NotificationForDetailed;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private Context mContext;
    private List<NotificationForDetailed> list;

    public NotificationAdapter(Context mContext, List<NotificationForDetailed> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationForDetailed notification = list.get(position);
        holder.text_notification_content.setText(notification.getContent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.text_notice_date.setText(dateFormat.format(notification.getNoticeDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NotificationViewHolder extends RecyclerView.ViewHolder{

    TextView text_notice_date, text_notification_content;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        text_notice_date = itemView.findViewById(R.id.text_notice_date);
        text_notification_content = itemView.findViewById(R.id.text_notification_content);
    }
}
