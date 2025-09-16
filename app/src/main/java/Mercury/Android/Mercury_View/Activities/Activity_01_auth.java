package Mercury.Android.Mercury_View.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import Mercury.Android.Mercury_View.Fragments.Fragment_01_Login;
import Mercury.Android.Mercury_View.Fragments.Fragment_02_Register;
import Mercury.Android.R;
import Mercury.Android.databinding.Activity01AuthBinding;


@SuppressWarnings("SpellCheckingInspection")
public class Activity_01_auth extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private final Fragment fragment01 = new Fragment();
    private final Fragment fragment02 = new Fragment();

    Fragment fragmentAtual, proximoFragment;

    Button btnAlternar;

    private boolean isFragment01Visible = true;

    private View mRevealLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fragment fragment01 = new Fragment_01_Login();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motionLayout, fragment01)
                .commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Display.Mode[] modes = getDisplay().getSupportedModes();
            Display.Mode highestMode = modes[0];

            for (Display.Mode mode : modes) {
                if (mode.getRefreshRate() > highestMode.getRefreshRate()) {
                    highestMode = mode;
                }
            }

            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.preferredDisplayModeId = highestMode.getModeId();
            getWindow().setAttributes(layoutParams);
        }

        Activity01AuthBinding binding = Activity01AuthBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        TextView textView = findViewById(R.id.signup);
        textView.setText(Html.fromHtml("<u>SignUp!</u>"));

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        window.setStatusBarColor(Color.TRANSPARENT);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.motionLayout, fragment01)
                    .commit();
        }

        binding.signup.setOnClickListener(v -> alternarFragment());
    }


    private void alternarFragment() {
        String textoProximoBotao;
        Fragment proximoFragment;

        if (isFragment01Visible) {
            proximoFragment = new Fragment_02_Register(); // Sempre novo
            textoProximoBotao = "SignIn";
            TextView textView = findViewById(R.id.signup);
            textView.setText(Html.fromHtml("<u>SignIn!</u>"));
            isFragment01Visible = false;
        } else {
            proximoFragment = new Fragment_01_Login(); // Sempre novo
            TextView textView = findViewById(R.id.signup);
            textView.setText(Html.fromHtml("<u>SignUp!</u>"));
            textoProximoBotao = "SignUp";
            isFragment01Visible = true;
        }

        btnAlternar.setText(textoProximoBotao);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motionLayout, proximoFragment)
                .commit();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFragment01Visible", isFragment01Visible);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isFragment01Visible = savedInstanceState.getBoolean("isFragment01Visible", true);
        btnAlternar.setText(isFragment01Visible ? "SingUp" : "SignIn");

        Fragment fragmentParaMostrar = isFragment01Visible ? fragment01 : fragment02;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motionLayout, fragmentParaMostrar)
                .commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

                Log.w("GoogleAuth", "Google sign in failed");
                findViewById(R.id.lottie_loading_circle).setVisibility(View.INVISIBLE);
                findViewById(R.id.login_button).setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
        }

        }
}