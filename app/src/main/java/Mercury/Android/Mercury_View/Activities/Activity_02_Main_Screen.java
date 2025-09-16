package Mercury.Android.Mercury_View.Activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import Mercury.Android.Mercury_View.Fragments.Fragment_03_AllChats;
import Mercury.Android.R;


public class Activity_02_Main_Screen extends AppCompatActivity {

    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_02_main_menu);

        Fragment fragment03 = new Fragment_03_AllChats();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment03) // ou .add() se quiser empilhar
                .commit();

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
