package Mercury.Android.Mercury_View.Fragments;

import static android.view.View.GONE;
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

import Mercury.Android.Mercury_Model.Services.Network_Checker;
import Mercury.Android.Mercury_Model.Services.Service_Permission;
import Mercury.Android.Mercury_Model.UseCases.UseCase_02_Register;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_Auth_01_Login_Credentials;
import Mercury.Android.Mercury_View.Utils.ToastWarning;
import Mercury.Android.R;
import Mercury.Android.databinding.Fragment02RegisterBinding;

/// @author Ítalo Oliveira Gomes

public class Fragment_Auth_02_Register extends Fragment {

    Fragment02RegisterBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bind = Fragment02RegisterBinding.inflate(inflater, container, false);

        String htmlTermsAndConditions = "I agree with the <u><font color='#E68AD8'>Terms and Conditions</font></u>";
        String htmlPrivacyPolicy = "I agree with the <u><font color='#E68AD8'>Privacy Policy</font></u>";

        bind.termsAndConditions.setText(Html.fromHtml(htmlTermsAndConditions, Html.FROM_HTML_MODE_LEGACY));
        bind.privacyPolicy.setText(Html.fromHtml(htmlPrivacyPolicy, Html.FROM_HTML_MODE_LEGACY));

        bind.signupButton.setOnClickListener(v -> {

            v.setClickable(false);

            UseCase_02_Register register = new UseCase_02_Register(
                    bind.userNameTextfield.getText().toString(),
                    bind.userEmailTextfield.getText().toString(),
                    bind.userTelefoneTextfield.getText().toString(),
                    bind.userPasswordTextfield.getText().toString(),
                    bind.confirmUserPasswordTextfield.getText().toString(),
                    bind.termosECondicoes.isChecked(),
                    bind.termosEPrivacidade.isChecked(),
                    new Network_Checker(requireContext()),
                    new Service_Permission(requireContext())
            );

            if (register.isEnabled()) {

                bind.signupButton.setText("");
                bind.loadAnimation.playAnimation();
                bind.loadAnimation.setVisibility(VISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    startActivity(new Intent(getActivity(), Activity_02_Feed.class));
                    v.setClickable(true);}, 1500);

            } else {
                new Dialog_Auth_01_Login_Credentials(requireContext()).show();
                bind.signupButton.setClickable(true);
            }
        });

        bind.googleLogin.setOnClickListener(v ->
                new ToastWarning(requireContext()).showInfo("Google Sign-in is still under development."));

        bind.gitAuth.setOnClickListener(view ->
                new ToastWarning(requireContext()).showInfo("Github Sign-in is still under development."));

        return bind.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        bind.signupButton.setText(R.string.signup_fragment);
        bind.loadAnimation.cancelAnimation();
        bind.loadAnimation.setVisibility(GONE);
        bind.signupButton.setClickable(true);
        bind.userNameTextfield.setText("");
        bind.userEmailTextfield.setText("");
        bind.userTelefoneTextfield.setText("");
        bind.userPasswordTextfield.setText("");
        bind.confirmUserPasswordTextfield.setText("");
        bind.termosECondicoes.setChecked(false);
        bind.termosEPrivacidade.setChecked(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}