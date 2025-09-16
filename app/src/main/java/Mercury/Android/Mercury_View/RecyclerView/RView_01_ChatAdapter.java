package Mercury.Android.Mercury_View.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Mercury.Android.Mercury_Model.Entitys.Entity_02_Chat_Session;
import Mercury.Android.Mercury_View.Activities.Activity_03_Msg_Screen;
import Mercury.Android.R;

public class RView_01_ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Entity_02_Chat_Session> chats;
    private List<Entity_02_Chat_Session> filteredChats;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM   = 1;

    private AdapterView.OnItemClickListener clickListener;

    public RView_01_ChatAdapter(List<Entity_02_Chat_Session> chats) {
        this.chats = chats;
        this.filteredChats = new ArrayList<>(chats);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_05_search_layout, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_01_chat, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            setupSearchEditText(headerHolder.searchEditText);
        } else if (holder instanceof MessageViewHolder) {
            MessageViewHolder messageHolder = (MessageViewHolder) holder;
            int chatPosition = position - 1;

            if (chatPosition >= 0 && chatPosition < filteredChats.size()) {
                Entity_02_Chat_Session chatSession = filteredChats.get(chatPosition);
                bindChatData(messageHolder, chatSession);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Activity_03_Msg_Screen.class);
            v.getContext().startActivity(intent);
        });
    }

    private void bindChatData(MessageViewHolder holder, Entity_02_Chat_Session chatSession) {
        // Set last message
        String lastMessage = chatSession.getLastMessage();
        holder.lastText.setText(lastMessage != null ? lastMessage : "No messages");

        // Set date
        if (chatSession.getChatDate() != null && !chatSession.getChatDate().isEmpty()) {
            List<Date> dates = chatSession.getChatDate();
            Date lastDate = dates.get(dates.size() - 1);
            holder.textDate.setText(dateFormat.format(lastDate));
        } else {
            holder.textDate.setText("--:--");
        }
    }

    private void setupSearchEditText(EditText searchEditText) {
        if (searchEditText != null) {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Não precisa implementar
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterChats(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void filterChats(String query) {
        filteredChats.clear();

        if (query == null || query.trim().isEmpty()) {
            filteredChats.addAll(chats);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Entity_02_Chat_Session chat : chats) {
                String lastMessage = chat.getLastMessage();
                if (lastMessage != null && lastMessage.toLowerCase().contains(lowerCaseQuery)) {
                    filteredChats.add(chat);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // +1 for header
        return filteredChats.size() + 1;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView lastText, textDate;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            lastText = itemView.findViewById(R.id.lastText);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        EditText searchEditText;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            searchEditText = itemView.findViewById(R.id.searchGlass);

            if (searchEditText == null) {
                android.util.Log.e("ChatAdapter", "EditText with ID 'searchGlass' not found in search_layout");
            }
        }
    }
}