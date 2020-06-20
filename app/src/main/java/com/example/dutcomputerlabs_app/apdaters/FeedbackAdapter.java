package com.example.dutcomputerlabs_app.apdaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.FeedbackForDetailed;

import java.text.SimpleDateFormat;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackViewHolder> {

    private Context mContext;
    private List<FeedbackForDetailed> list;

    public FeedbackAdapter(Context mContext, List<FeedbackForDetailed> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackForDetailed feedback = list.get(position);
        holder.text_feedbacker.setText(feedback.getUser().getName());
        holder.text_feedback_content.setText(feedback.getContent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.text_feedback_date.setText(dateFormat.format(feedback.getFeedbackDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class FeedbackViewHolder extends RecyclerView.ViewHolder{

    TextView text_feedbacker, text_feedback_content, text_feedback_date;

    public FeedbackViewHolder(@NonNull View itemView) {
        super(itemView);
        text_feedbacker = itemView.findViewById(R.id.text_feedbacker);
        text_feedback_content = itemView.findViewById(R.id.text_feedback_content);
        text_feedback_date = itemView.findViewById(R.id.text_feedback_date);
    }
}
