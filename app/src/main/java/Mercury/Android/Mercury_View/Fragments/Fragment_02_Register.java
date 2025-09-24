package Mercury.Android.Mercury_View.Fragments;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import Mercury.Android.Mercury_Model.Entitys.Entity_01_User;
import Mercury.Android.Mercury_Model.Services.AlertDialog;
import Mercury.Android.Mercury_View.Activities.Activity_02_Feed;
import Mercury.Android.Mercury_View.Dialogs.Dialog_01_Login_Credentials;
import Mercury.Android.R;

/// @author Ítalo Oliveira Gomes

@SuppressWarnings("SpellCheckingInspection")
public class Fragment_02_Register extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_02_register, container, false);
        ViewGroup rootView = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        //Buttons
        Button returnButton = view.findViewById(R.id.return_button);
        Button signupButton = view.findViewById(R.id.signup_button);

        //EditTextField
        EditText userNameTextfield = view.findViewById(R.id.user_name_textfield);
        EditText userEmailTextfield = view.findViewById(R.id.user_email_textfield);
        EditText userTelefoneNumberTextfield = view.findViewById(R.id.user_telefone_textfield);
        EditText userPasswordTextfield = view.findViewById(R.id.user_password_textfield);
        EditText confirmUserPasswordTextfield = view.findViewById(R.id.confirm_user_password_textfield);

        //Switchs
        SwitchCompat termosCondicoes = view.findViewById(R.id.termos_e_condicoes);
        SwitchCompat termosPrivacidade = view.findViewById(R.id.termos_e_privacidade);

        //LottieAnimations
        LottieAnimationView loadingAnimation = view.findViewById(R.id.lottie_loading_circle);
        LottieAnimationView registerConcluded  = view.findViewById(R.id.lottieAnimation2);

        returnButton.setOnClickListener(v->{

        });

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

                loadingAnimation.playAnimation();
                loadingAnimation.setVisibility(VISIBLE);

                registerConcluded.playAnimation();
                registerConcluded.setVisibility(VISIBLE);

                Intent intent = new Intent(getActivity(), Activity_02_Feed.class);
                startActivity(intent);

            }
            else {
                Dialog_01_Login_Credentials dialog = new Dialog_01_Login_Credentials(requireContext());
                dialog.show();
            }
        });

        return view;
    }
}