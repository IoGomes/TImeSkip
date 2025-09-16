package Mercury.Android.Mercury_View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Mercury.Android.Mercury_View.RecyclerView.RView_01_ChatAdapter;
import Mercury.Android.Mercury_Model.Entitys.Entity_02_Chat_Session;
import Mercury.Android.R;

public class Fragment_06_Archived extends Fragment {

    private RecyclerView recyclerView;
    private RView_01_ChatAdapter adapter;
    private List<Entity_02_Chat_Session> chatSessions;

    @Nullable
    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_06_archived, container, false);


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

        Log.d("FragmentDebug", "Itens na lista: " + chatSessions.size());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RView_01_ChatAdapter(chatSessions);
        recyclerView.setAdapter(adapter);

        return view;
    }
}