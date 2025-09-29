package Mercury.Android.Mercury_View.Fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
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

import Mercury.Android.Mercury_Model.UseCases.UseCase_01_Login;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_Auth_01_Login_Credentials;
import Mercury.Android.R;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Fragment_Auth_01_Login extends Fragment {

    private TextView remainConected;
    private TextView forgotPassword;
    private ImageButton buttonGoogleLogin;
    private LottieAnimationView loadingAnimation;
    private Button loginButton;
    private EditText userEmailTextfield;
    private EditText userPasswordTextfield;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_01_login, container, false);
        ViewGroup rootView = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        //TextViews
        remainConected = view.findViewById(R.id.textView);
        forgotPassword = view.findViewById(R.id.textView2);

        String forgot = "<u><font color='#E68AD8'>Forgot Password?</u>";
        String htmlText = "<u><font color='#ffffff'>Nebula</font></u>";

        remainConected.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        forgotPassword.setText(Html.fromHtml(forgot, Html.FROM_HTML_MODE_LEGACY));

        //LottieAnimations
        loadingAnimation = view.findViewById(R.id.lottie_loading_circle);

        //Buttons
        buttonGoogleLogin = view.findViewById(R.id.googleLogin);
        loginButton = view.findViewById(R.id.loginButton);

        //EditTextField
        userEmailTextfield = view.findViewById(R.id.email_textfield);
        userPasswordTextfield = view.findViewById(R.id.user_password_textfield);

        loginButton.setOnClickListener(v -> {

            loginButton.setClickable(false);
            buttonGoogleLogin.setClickable(false);

            String userEmail = userEmailTextfield.getText().toString();
            String userPassword = userPasswordTextfield.getText().toString();

            UseCase_01_Login login = new UseCase_01_Login(userEmail, userPassword);

            if (login.isLoginEnabled()) {

                loginButton.setText(null);
                loadingAnimation.setVisibility(VISIBLE);
                loadingAnimation.playAnimation();

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent intent = new Intent(getActivity(), Activity_02_Feed.class);
                    startActivity(intent);
                }, 1500);
            }

            Dialog_Auth_01_Login_Credentials dialog = new Dialog_Auth_01_Login_Credentials(requireContext());
            dialog.show();
            loginButton.setClickable(true);

        });

        buttonGoogleLogin.setOnClickListener(v -> {

            buttonGoogleLogin.setClickable(false);

            loginButton.setText(null);

            loadingAnimation.setVisibility(VISIBLE);
            loadingAnimation.playAnimation();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(getActivity(), Activity_02_Feed.class);
                startActivity(intent);
            }, 1500);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (buttonGoogleLogin != null) {
            buttonGoogleLogin.setOnClickListener(null);
        }

        if (loadingAnimation != null) {
            loadingAnimation.cancelAnimation();
            loadingAnimation.setVisibility(View.GONE);
        }

        loadingAnimation = null;
        loginButton = null;
        buttonGoogleLogin = null;
        remainConected = null;
        forgotPassword = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        buttonGoogleLogin.setClickable(true);
        loginButton.setClickable(true);
        loginButton.setText("Login");
        loadingAnimation.cancelAnimation();
        loadingAnimation.setVisibility(INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
