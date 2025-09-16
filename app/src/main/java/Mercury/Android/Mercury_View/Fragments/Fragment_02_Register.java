package Mercury.Android.Mercury_View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Mercury.Android.Mercury_View.Activities.Activity_02_Main_Screen;
import Mercury.Android.R;

import com.airbnb.lottie.LottieAnimationView;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Fragment_02_Register extends Fragment {
    private LottieAnimationView lottieView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_02_register, container, false);
        BlurView blurView = view.findViewById(R.id.glass);
        ViewGroup rootView = requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Button signupButton = view.findViewById(R.id.signup);
        Button googleSignupButton = view.findViewById(R.id.google_signup);
        Button githubSignupButton = view.findViewById(R.id.github_signup);
        EditText userNameTextfield = view.findViewById(R.id.user_name_textfield);
        EditText userEmailTextfield = view.findViewById(R.id.user_email_textfield);
        EditText userPasswordTextfield = view.findViewById(R.id.user_password_textfield);
        EditText userCPFTextfield = view.findViewById(R.id.user_CPF_textfield);
        EditText userRegistroPRTextfield = view.findViewById(R.id.registro_PR_textfield);
        Switch termosDePrivacidade = view.findViewById(R.id.termos_e_condições);
        lottieView = view.findViewById(R.id.lottie_loading_circle);

        blurView.setupWith(rootView, new RenderScriptBlur(requireActivity()))
                .setFrameClearDrawable(requireActivity().getWindow().getDecorView().getBackground())
                .setBlurRadius(24f);;

        signupButton.setOnClickListener(v -> {

            String userName = userNameTextfield.getText().toString();
            String userEmail = userEmailTextfield.getText().toString();
            String userPassword = userPasswordTextfield.getText().toString();
            String userCPF = userCPFTextfield.getText().toString();
            String userRegistroPR = userRegistroPRTextfield.getText().toString();

            if (termosDePrivacidade.isActivated()) {

                lottieView.setVisibility(View.VISIBLE);
                signupButton.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(getActivity(), Activity_02_Main_Screen.class);
                startActivity(intent);
            } else {
            }
        });
        return view;
    }


    @Override
    public void onDestroyView () {
        super.onDestroyView();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
    }
}