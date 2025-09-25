package Mercury.Android.Mercury_View.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import Mercury.Android.Mercury_View.Activities.Activity_03_Chat;
import Mercury.Android.Mercury_View.Dialogs.Dialog_03_ProfileImage;
import Mercury.Android.R;

public class RV_Feed_01_Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Entity_02_Chat_Session> chats;
    private List<Entity_02_Chat_Session> filteredChats;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_CATEGORY = 1;
    private static final int VIEW_TYPE_ITEM = 2;

    private AdapterView.OnItemClickListener clickListener;

    public RV_Feed_01_Chat_Adapter(List<Entity_02_Chat_Session> chats) {
        this.chats = chats;
        this.filteredChats = new ArrayList<>(chats);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == 1) {
            return VIEW_TYPE_CATEGORY;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_05_search_layout, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == VIEW_TYPE_CATEGORY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_header_category, parent, false);
            return new SecondViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_01_chat, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            setupSearchEditText(headerHolder.searchEditText);
        } else if (holder instanceof SecondViewHolder) {
            SecondViewHolder categoryHolder = (SecondViewHolder) holder;
        } else if (holder instanceof MessageViewHolder) {
            MessageViewHolder messageHolder = (MessageViewHolder) holder;
            int chatPosition = position - 2; // -2 porque agora temos dois headers

            if (chatPosition >= 0 && chatPosition < filteredChats.size()) {
                Entity_02_Chat_Session chatSession = filteredChats.get(chatPosition);
                bindChatData(messageHolder, chatSession);
            }

            // Configurar click listener para o profileImage
            messageHolder.profileImage.setOnClickListener(v -> {
                Dialog_03_ProfileImage dialog = new Dialog_03_ProfileImage(v.getContext());
                dialog.show();
            });
        }

        // Configurar click listener apenas para itens de chat (itemView completo)
        if (holder instanceof MessageViewHolder) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), Activity_03_Chat.class);
                v.getContext().startActivity(intent);
            });
        }
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
        // +2 for header and category header
        return filteredChats.size() + 2;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView lastText, textDate;
        ImageButton profileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            lastText = itemView.findViewById(R.id.lastText);
            textDate = itemView.findViewById(R.id.textDate);
            profileImage = itemView.findViewById(R.id.imageButton);
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

    static class SecondViewHolder extends RecyclerView.ViewHolder {
        Button textView;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category);
        }
    }
}