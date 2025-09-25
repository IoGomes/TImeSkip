package Mercury.Android.Mercury_View.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import Mercury.Android.Mercury_View.Fragments.Fragment_Auth_01_Login;
import Mercury.Android.Mercury_View.Fragments.Fragment_Auth_02_Register;
import Mercury.Android.Mercury_View.UI_Observer;
import Mercury.Android.R;
import Mercury.Android.databinding.Activity01AuthBinding;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Activity_01_Auth extends AppCompatActivity implements UI_Observer {

    private static final int RC_SIGN_IN = 9001;

    private boolean isFragment01Visible = true;

    ImageButton returnButton;

    private Activity01AuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity01AuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View rootLayout = findViewById(R.id.root);
        int navigationBarHeight = getNavigationBarHeight();

        int left = rootLayout.getPaddingLeft();
        int top = rootLayout.getPaddingTop();
        int right = rootLayout.getPaddingRight();
        int bottom = rootLayout.getPaddingBottom();

        rootLayout.setPadding(left, top, right, bottom + navigationBarHeight);

        returnButton = findViewById(R.id.returnButton);

        Window window = getWindow();
        window.setStatusBarColor(Color.BLACK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        returnButton.setOnClickListener(v-> {
            alternarFragment();
        });

        Fragment fragmentInicial = new Fragment_Auth_01_Login();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motionLayout, fragmentInicial)
                .commit();

        binding.text3.setText(Html.fromHtml("<u>SignUp!</u>"));

        binding.text3.setOnClickListener(v -> {
            alternarFragment();
            returnButton.setVisibility(VISIBLE);
        });

        // Ajuste de taxa de atualização se API >= R
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
    }

    private void alternarFragment() {
        Fragment proximoFragment;

        if (isFragment01Visible) {
            proximoFragment = new Fragment_Auth_02_Register();
            binding.text3.setText(Html.fromHtml("<u>SignIn!</u>"));
            isFragment01Visible = false;
        } else {
            returnButton.setVisibility(GONE);
            proximoFragment = new Fragment_Auth_01_Login();
            binding.text3.setText(Html.fromHtml("<u>SignUp!</u>"));
            isFragment01Visible = true;
        }

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

        // Atualiza o TextView com base no fragment visível
        binding.text3.setText(Html.fromHtml(isFragment01Visible ? "<u>SignUp!</u>" : "<u>SignIn!</u>"));

        Fragment fragmentParaMostrar = isFragment01Visible ? new Fragment_Auth_01_Login() : new Fragment_Auth_02_Register();
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
        }
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
}
