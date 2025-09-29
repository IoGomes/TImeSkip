package Mercury.Android.Mercury_View.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Mercury.Android.Mercury_Model.Entitys.Entity_05_call;
import Mercury.Android.Mercury_View.Dialogs.Dialog_Feed_01_Profile_Image;
import Mercury.Android.R;

public class RV_Feed_03_Calls_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Entity_05_call> calls;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    // Tipos de view
    private static final int HEADER_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    public RV_Feed_03_Calls_Adapter(List<Entity_05_call> calls){
        this.calls = calls;
    }

    @Override
    public int getItemViewType(int position) {
        // Posição 0 é header, resto são itens
        return (position == 0) ? HEADER_VIEW : ITEM_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == HEADER_VIEW) {
            View view = inflater.inflate(R.layout.rv_calls_header_title, parent, false);
            return new HeaderView(view);
        } else {
            View view = inflater.inflate(R.layout.rv_item_04_call, parent, false);
            return new MeuViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderView) {
            HeaderView headerHolder = (HeaderView) holder;
            headerHolder.contactHeader.setText("Histórico de Chamadas"); // título fixo ou dinâmico
        } else if (holder instanceof MeuViewHolder) {
            // Ajusta o índice, porque o header ocupa a posição 0
            Entity_05_call call = calls.get(position - 1);

            MeuViewHolder callHolder = (MeuViewHolder) holder;
            callHolder.callContactName.setText(call.getNomeDeContato());
            callHolder.callTextDate.setText(dateFormat.format(call.getDateTimeCall()));

            callHolder.profileImage.setOnClickListener(v -> {
                Dialog_Feed_01_Profile_Image dialog = new Dialog_Feed_01_Profile_Image(v.getContext());
                dialog.show();
            });
        }
    }

    @Override
    public int getItemCount() {

        return calls.size() + 1;
    }

    static class MeuViewHolder extends RecyclerView.ViewHolder {
        TextView callContactName;
        TextView callTextDate;
        ImageButton profileImage;

        public MeuViewHolder(@NonNull View itemView) {
            super(itemView);
            callContactName = itemView.findViewById(R.id.call_contact_name);
            callTextDate = itemView.findViewById(R.id.call_text_date);
            profileImage = itemView.findViewById(R.id.call_profile_image);
        }
    }


    static class HeaderView extends RecyclerView.ViewHolder {
        TextView contactHeader;
        public HeaderView(@NonNull View itemView) {
            super(itemView);
            contactHeader = itemView.findViewById(R.id.calls_title);
        }
    }
}

