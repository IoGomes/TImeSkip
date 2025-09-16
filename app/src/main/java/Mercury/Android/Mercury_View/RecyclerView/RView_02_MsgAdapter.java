package Mercury.Android.Mercury_View.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Mercury.Android.Mercury_Model.Entitys.Entity_03_Message;
import Mercury.Android.R;

public class RView_02_MsgAdapter extends RecyclerView.Adapter<RView_02_MsgAdapter.MessageViewHolder> {

    private final List<Entity_03_Message> messages;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public RView_02_MsgAdapter(List<Entity_03_Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_02_msg_send, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Entity_03_Message message = messages.get(position);

        holder.textMessage.setText(message.getMessage());

        if (message.getDateTimeMessage() != null) {
            holder.textDate.setText(dateFormat.format(message.getDateTimeMessage()));
        } else {
            holder.textDate.setText("--:--");
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage, textDate;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}