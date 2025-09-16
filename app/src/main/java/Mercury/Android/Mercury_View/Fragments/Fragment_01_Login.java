package Mercury.Android.Mercury_View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import Mercury.Android.Mercury_View.Activities.Activity_02_Main_Screen;
import Mercury.Android.R;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Fragment_01_Login extends Fragment {

    private ImageButton buttonGoogleLogin;
    private LottieAnimationView lottieView;
    private Button loginButton;
    private BlurView blurView;
    private EditText emailTextField;
    private EditText passwordTextField;
    private TextView textView2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_01_login, container, false);

        ViewGroup rootView = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        blurView = view.findViewById(R.id.glass);

        blurView.setupWith(rootView, new RenderScriptBlur(requireActivity()))
                .setFrameClearDrawable(requireActivity().getWindow().getDecorView().getBackground())
                .setBlurRadius(24f);

        buttonGoogleLogin = view.findViewById(R.id.google);

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {

            loginButton.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(getActivity(), Activity_02_Main_Screen.class);
            startActivity(intent);
        });

        buttonGoogleLogin.setOnClickListener(v -> {


            loginButton.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(getActivity(), Activity_02_Main_Screen.class);
            startActivity(intent);

        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (buttonGoogleLogin != null) {
            buttonGoogleLogin.setOnClickListener(null);
        }

        if (lottieView != null) {
            lottieView.cancelAnimation();
            lottieView.setVisibility(View.GONE);
        }

        lottieView = null;
        loginButton = null;
        buttonGoogleLogin = null;
        blurView = null;
        emailTextField = null;
        passwordTextField = null;
        textView2 = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
