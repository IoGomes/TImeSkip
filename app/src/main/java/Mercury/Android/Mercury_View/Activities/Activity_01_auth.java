package Mercury.Android.Mercury_View.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import Mercury.Android.Mercury_View.Fragments.Fragment_Auth_01_Login;
import Mercury.Android.Mercury_View.Fragments.Fragment_Auth_02_Register;
import Mercury.Android.R;
import Mercury.Android.databinding.Activity01AuthBinding;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Activity_01_Auth extends AppCompatActivity {

    private boolean isFragment01Visible = true;
    private Activity01AuthBinding binding;
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String[] REQUIRED_PERMISSIONS = {

            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity01AuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        allPermissionsGranted();
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);

        Objects.requireNonNull(getSupportActionBar()).hide();

        View rootLayout = findViewById(R.id.root);

        int navigationBarHeight = getNavigationBarHeight();
        int left = rootLayout.getPaddingLeft();
        int top = rootLayout.getPaddingTop();
        int right = rootLayout.getPaddingRight();
        int bottom = rootLayout.getPaddingBottom();

        rootLayout.setPadding(left, top, right, bottom + navigationBarHeight);

        Fragment fragmentAuth01Login = new Fragment_Auth_01_Login();
        replaceFragment(fragmentAuth01Login);

        binding.returnButton.setOnClickListener(v -> alternarFragment());
        binding.text3.setOnClickListener(v -> alternarFragment());
        binding.text3.setText(Html.fromHtml("<u>SignUp!</u>"));
    }

    private void alternarFragment() {
        Fragment proximoFragment;

        if (isFragment01Visible) {
            binding.returnButton.setVisibility(VISIBLE);
            proximoFragment = new Fragment_Auth_02_Register();
            binding.text3.setText(Html.fromHtml("<u>SignIn!</u>"));
            isFragment01Visible = false;
        } else {
            binding.returnButton.setVisibility(GONE);
            proximoFragment = new Fragment_Auth_01_Login();
            binding.text3.setText(Html.fromHtml("<u>SignUp!</u>"));
            isFragment01Visible = true;
        }

        replaceFragment(proximoFragment);
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

        binding.text3.setText(Html.fromHtml(isFragment01Visible ? "<u>SignUp!</u>" : "<u>SignIn!</u>"));

        Fragment fragmentParaMostrar = isFragment01Visible ? new Fragment_Auth_01_Login() : new Fragment_Auth_02_Register();
        replaceFragment(fragmentParaMostrar);
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

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motionLayout, fragment)
                .commit();
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

