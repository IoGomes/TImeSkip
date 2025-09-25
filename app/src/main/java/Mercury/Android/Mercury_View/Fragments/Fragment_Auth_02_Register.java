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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import Mercury.Android.Mercury_Model.Entitys.Entity_01_User;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_Auth_01_Login_Credentials;
import Mercury.Android.R;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Fragment_Auth_02_Register extends Fragment {

    // Variáveis globais para LifeCycle
    Button signupButton;
    TextView termsAndConditions;
    TextView privacyPolicy;
    LottieAnimationView loadAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_02_register, container, false);
        ViewGroup rootView = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        //Buttons
        signupButton = view.findViewById(R.id.signup_button);

        //EditTextField
        EditText userNameTextfield = view.findViewById(R.id.user_name_textfield);
        EditText userEmailTextfield = view.findViewById(R.id.user_email_textfield);
        EditText userTelefoneNumberTextfield = view.findViewById(R.id.user_telefone_textfield);
        EditText userPasswordTextfield = view.findViewById(R.id.user_password_textfield);
        EditText confirmUserPasswordTextfield = view.findViewById(R.id.confirm_user_password_textfield);

        //Switchs
        SwitchCompat termosCondicoes = view.findViewById(R.id.termos_e_condicoes);
        SwitchCompat termosPrivacidade = view.findViewById(R.id.termos_e_privacidade);

        //TextViews
        termsAndConditions = view.findViewById(R.id.terms_and_conditions);
        privacyPolicy = view.findViewById(R.id.privacy_policy);

        //LottieAnimations
        loadAnimation = view.findViewById(R.id.lottie_loading);

        //Sublinhando Textviews
        String htmlTermsAndConditions = "I agree with the <u><font color='#E68AD8'>Terms and Conditions</font></u>";
        String htmlPrivacyPolicy = "I agree with the <u><font color='#E68AD8'>Privacy Policy</font></u>";

        termsAndConditions.setText(Html.fromHtml(htmlTermsAndConditions, Html.FROM_HTML_MODE_LEGACY));
        privacyPolicy.setText(Html.fromHtml(htmlPrivacyPolicy, Html.FROM_HTML_MODE_LEGACY));

        signupButton.setOnClickListener(v -> {

            String userName = userNameTextfield.getText().toString();
            String userEmail = userEmailTextfield.getText().toString();
            String userTelefoneNumber = userTelefoneNumberTextfield.getText().toString();
            String userPassword = userPasswordTextfield.getText().toString();
            String confirmUserPassword = confirmUserPasswordTextfield.getText().toString();
            Boolean isTermosCondicoes = termosCondicoes.isActivated();
            Boolean isTermosPrivacidade = termosPrivacidade.isActivated();

            Entity_01_User newRegistry = new Entity_01_User(
                    userName,
                    userEmail,
                    userTelefoneNumber,
                    userPassword,
                    confirmUserPassword,
                    isTermosCondicoes,
                    isTermosPrivacidade
            );

            if (newRegistry.isUserEnabled()) {

                signupButton.setText("");

                loadAnimation.playAnimation();
                loadAnimation.setVisibility(VISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent intent = new Intent(getActivity(), Activity_02_Feed.class);
                    startActivity(intent);
                }, 1500);

            } else {
                Dialog_Auth_01_Login_Credentials dialog = new Dialog_Auth_01_Login_Credentials(requireContext());
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        signupButton.setText("SignUp");
        loadAnimation.cancelAnimation();
        loadAnimation.setVisibility(GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        signupButton = null;
    }
}