package Mercury.Android.Mercury_View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import Mercury.Android.Mercury_Model.Services.AlertDialog;
import Mercury.Android.R;

public class Dialog_Feed_01_Profile_Image extends Dialog {
    public Dialog_Feed_01_Profile_Image(@NonNull Context context) {
        super(context);

        setContentView(R.layout.dialog_profile_image);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(); params.copyFrom(getWindow().getAttributes()); params.width = 200; params.height = WindowManager.LayoutParams.WRAP_CONTENT; // altura automática getWindow().setAttributes(params); }

        init(context);
        setOnCancelListener(d -> {
            AlertDialog.clearListMesage();
        });
    }

    private void init(Context context) {

    }
}
