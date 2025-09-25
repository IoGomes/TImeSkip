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

import Mercury.Android.Mercury_Model.Entitys.Entity_01_User;
import Mercury.Android.Mercury_View.Activities.Activity_01_Auth;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_01_Login_Credentials;
import Mercury.Android.R;

public class Fragment_Auth_01_Login extends Fragment {

    private ImageButton buttonGoogleLogin;
    private LottieAnimationView lottieView;
    private Button loginButton;
    private EditText userEmailTextfield;
    private EditText userPasswordTextfield;

    private Activity_01_Auth activity01Auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_01_login, container, false);
        ViewGroup rootView = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        TextView textView = view.findViewById(R.id.textView);
        TextView forgot1 = view.findViewById(R.id.textView2);

        String htmlText = "<u><font color='#ffffff'>Nebula</font></u>";
        textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));

        String forgot = "<u><font color='#E68AD8'>Forgot Password?</u>";
        forgot1.setText(Html.fromHtml(forgot, Html.FROM_HTML_MODE_LEGACY));

        lottieView = view.findViewById(R.id.lottie_loading_circle);

        buttonGoogleLogin = view.findViewById(R.id.googleLogin);

        loginButton = view.findViewById(R.id.loginButton);

        userEmailTextfield = view.findViewById(R.id.email_textfield);
        userPasswordTextfield = view.findViewById(R.id.user_password_textfield);

        loginButton.setOnClickListener(v -> {
            String userEmail = userEmailTextfield.getText().toString();
            String userPassword = userPasswordTextfield.getText().toString();

            Entity_01_User entity01User = new Entity_01_User(userEmail, "ioliveiragomes@gmail.com",
                    "991126575",
                    userPassword,
                    "12345",
                    true, true);

            if (entity01User.isUserEnabled()) {
                loginButton.setText("");
                lottieView.setVisibility(VISIBLE);
                lottieView.playAnimation();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent intent = new Intent(getActivity(), Activity_02_Feed.class);
                    startActivity(intent);
                }, 1500); // 3000 ms = 3 segundos
            }
            else{
                Dialog_01_Login_Credentials dialog = new Dialog_01_Login_Credentials(requireContext());
                dialog.show();
            }
        });

        buttonGoogleLogin.setOnClickListener(v -> {

            loginButton.setText("");
            lottieView.setVisibility(VISIBLE);
            lottieView.playAnimation();
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

        if (lottieView != null) {
            lottieView.cancelAnimation();
            lottieView.setVisibility(View.GONE);
        }

        lottieView = null;
        loginButton = null;
        buttonGoogleLogin = null;
    }

    @Override
    public void onPause() {
        super.onPause(
        );
        loginButton.setText("Login");
        lottieView.cancelAnimation();
        lottieView.setVisibility(INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
