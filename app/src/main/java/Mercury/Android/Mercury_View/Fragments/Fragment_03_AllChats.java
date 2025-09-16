package Mercury.Android.Mercury_View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import androidx.annotation.LayoutRes;
import android.content.Context;
import androidx.fragment.app.FragmentActivity;

import Mercury.Android.Mercury_View.Activities.Activity_02_Main_Screen;
import Mercury.Android.Mercury_View.Activities.Activity_03_Msg_Screen;
import Mercury.Android.Mercury_View.RecyclerView.RView_01_ChatAdapter;
import Mercury.Android.Mercury_Model.Entitys.Entity_02_Chat_Session;
import Mercury.Android.R;

public class Fragment_03_AllChats extends Fragment {

    ImageButton button;
    private RecyclerView recyclerView;
    private RView_01_ChatAdapter adapter;
    private List<Entity_02_Chat_Session> chatSessions;

    public Fragment_03_AllChats() {
        super();
    }

    @Nullable
    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        // inflar o layout do fragment
        View view = inflater.inflate(R.layout.fragment_03_chats, container, false);

        // inicializar lista de dados
        chatSessions = new ArrayList<>();

        List<String> users2 = Arrays.asList("userC", "userD");
        List<Date> dates2 = Arrays.asList(new Date());

        // (opcional) adicionar dados de teste
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));
        chatSessions.add(new Entity_02_Chat_Session(
                "chat2",
                users2,
                dates2,
                "Reunião marcada para amanhã."
        ));

        Log.d("FragmentDebug", "Itens na lista: " + chatSessions.size());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setOnClickListener(v -> {
            Log.d("RecyclerClick", "RecyclerView container clicado");
            Intent intent = new Intent(getActivity(), Activity_03_Msg_Screen.class);
            startActivity(intent);

        });

        adapter = new RView_01_ChatAdapter(chatSessions);
        recyclerView.setAdapter(adapter);

        button = view.findViewById(R.id.start_chat);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_03_Msg_Screen.class);
            startActivity(intent);
        });

        return view;


    }
}

