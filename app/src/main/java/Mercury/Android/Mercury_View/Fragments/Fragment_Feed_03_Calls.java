package Mercury.Android.Mercury_View.Fragments;

import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Mercury.Android.Mercury_Model.Entitys.Entity_05_call;
import Mercury.Android.Mercury_View.RecyclerView.RV_Feed_03_Calls_Adapter;
import Mercury.Android.R;

@SuppressWarnings("SpellCheckingInspection")

public class Fragment_Feed_03_Calls extends Fragment {

    ImageButton button;
    private RecyclerView recyclerView;
    private RV_Feed_03_Calls_Adapter adapter;
    private List<Entity_05_call> callSessions;

    public Fragment_Feed_03_Calls() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_05_call_history, container, false);

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.SEPTEMBER, 25, 10, 30, 0); // ano, mês, dia, hora, minuto, segundo
        Date dataFicticia = cal.getTime();

        callSessions = new ArrayList<>();

        List<String> users2 = Arrays.asList("userC", "userD");
        List<Date> dates2 = Arrays.asList(new Date());

        callSessions.add(new Entity_05_call(
                "Nome de Contato",
                dataFicticia,
                true
        ));
        callSessions.add(new Entity_05_call(
                "Nome de Contato",
                dataFicticia,
                true
        ));callSessions.add(new Entity_05_call(
                "Nome de Contato",
                dataFicticia,
                true
        ));callSessions.add(new Entity_05_call(
                "Nome de Contato",
                dataFicticia,
                true
        ));callSessions.add(new Entity_05_call(
                "Nome de Contato",
                dataFicticia,
                true
        ));


        recyclerView = view.findViewById(R.id.callRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RV_Feed_03_Calls_Adapter(callSessions);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
