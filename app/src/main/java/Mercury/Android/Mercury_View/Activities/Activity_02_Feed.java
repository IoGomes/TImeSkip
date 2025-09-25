package Mercury.Android.Mercury_View.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import Mercury.Android.Mercury_View.Fragments.Fragment_Feed_01_Inbox;
import Mercury.Android.Mercury_View.Fragments.Fragment_Feed_03_Calls;
import Mercury.Android.Mercury_View.Fragments.Fragment_Feed_02_Contacts;
import Mercury.Android.Mercury_View.Fragments.Fragment_Feed_04_Archived;
import Mercury.Android.Mercury_View.View_Observer;
import Mercury.Android.R;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Activity_02_Feed extends AppCompatActivity implements View_Observer {

    private int corNormal;
    private Map<Integer, Integer> coresSelecionadas;

    //Instancias dos Fragments
    Fragment fragment03 = new Fragment_Feed_01_Inbox();
    Fragment fragment04 = new Fragment_Feed_03_Calls();
    Fragment fragment05 = new Fragment_Feed_02_Contacts();
    Fragment fragment06 = new Fragment_Feed_04_Archived();


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_02_main_menu);

        TextView textView2 = findViewById(R.id.text);

        View rootLayout = findViewById(R.id.root);
        int navigationBarHeight = getNavigationBarHeight();
        rootLayout.setPadding(0, 0, 0, navigationBarHeight);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment03)
                .commit();

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }

        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.parseColor("#101010"));

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        changeButtonBg();
    }
    private int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void update() {

    }

    public void changeButtonBg() {
        int[] botoesIds = {
                R.id.inbox,
                R.id.contact,
                R.id.calls,
                R.id.archived,
        };

        // Mapeamento: cada botão -> fragment correspondente
        Map<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.inbox, fragment03);
        fragmentMap.put(R.id.contact, fragment04);
        fragmentMap.put(R.id.calls, fragment05);
        fragmentMap.put(R.id.archived, fragment06);

        for (int id : botoesIds) {
            ImageButton btn = findViewById(id);
            btn.setOnClickListener(v -> {
                selecionarBotao((ImageButton) v);
                trocarFragment(fragmentMap.get(v.getId())); // troca fragment no LinearLayout
            });
        }

        // Exibe fragment inicial (inbox)
        ImageButton inboxBtn = findViewById(R.id.inbox);
        selecionarBotao(inboxBtn);
        trocarFragment(fragmentMap.get(R.id.inbox));
    }

    private ImageButton ultimoClicado = null;

    private void selecionarBotao(ImageButton botaoAtual) {
        if (ultimoClicado != null && ultimoClicado != botaoAtual) {
            ultimoClicado.setBackground(null);
        }

        botaoAtual.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_selected_highlight));
        ultimoClicado = botaoAtual;
    }

    private void trocarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment) // usa o LinearLayout como container
                .commit();
    }

}
