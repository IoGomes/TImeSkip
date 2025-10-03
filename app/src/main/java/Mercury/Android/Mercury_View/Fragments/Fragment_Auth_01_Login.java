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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Mercury.Android.Mercury_Model.UseCases.UseCase_01_Login;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_Auth_01_Login_Credentials;
import Mercury.Android.Mercury_View.Utils.ToastWarning;
import Mercury.Android.databinding.Fragment01LoginBinding;

/// @author Ítalo Oliveira Gomes

public class Fragment_Auth_01_Login extends Fragment {

    private Fragment01LoginBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = Fragment01LoginBinding.inflate(inflater, container, false);

        String forgot = "<u><font color='#E68AD8'>Forgot Password?</u>";
        String htmlText = "<u><font color='#ffffff'>Nebula</font></u>";

        bind.textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        bind.textView2.setText(Html.fromHtml(forgot, Html.FROM_HTML_MODE_LEGACY));

        bind.login.setOnClickListener(v -> {

            v.setClickable(false);
            bind.googleLogin.setClickable(false);

            new UseCase_01_Login(
                    bind.emailTextfield.getText().toString(),
                    bind.userPasswordTextfield.getText().toString()
            );

            bind.login.setText(null);
            bind.loadAnimation.setVisibility(VISIBLE);
            bind.loadAnimation.playAnimation();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(getActivity(), Activity_02_Feed.class));
                v.setClickable(true);}, 1500);

            new Dialog_Auth_01_Login_Credentials(requireContext()).show();
        });

        bind.googleLogin.setOnClickListener(v ->
                new ToastWarning(requireContext()).showInfo("Google Sign-in is still under development."));

        bind.gitAuth.setOnClickListener(view ->
                new ToastWarning(requireContext()).showInfo("Github Sign-in is still under development."));

        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        bind.googleLogin.setClickable(true);
        bind.loginButton.setClickable(true);
        bind.login.setText("Login");
        bind.loadAnimation.cancelAnimation();
        bind.loadAnimation.setVisibility(INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
