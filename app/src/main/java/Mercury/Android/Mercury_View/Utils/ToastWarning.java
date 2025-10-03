package Mercury.Android.Mercury_View.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import Mercury.Android.R;

public class ToastWarning {
    private Context context;

    public ToastWarning(Context context) {
        this.context = context;
    }

    private void showToast(int type, String text){
        // Inflar o layout customizado
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast, null);

        // Converter 300dp para pixels
        int widthInDp = 350;
        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPx = (int) (widthInDp * scale);

        // Forçar o tamanho mínimo
        layout.setMinimumWidth(widthInPx);

        // Obter o ViewGroup raiz (se necessário para manipular)
        ViewGroup viewGroup = null;
        if (context instanceof Activity) {
            viewGroup = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
        }

        // Encontrar o TextView no layout e definir o texto
        TextView textView = layout.findViewById(R.id.toast_text);
        if (textView != null) {
            textView.setText(text);
        }

        // Configurar a aparência baseada no tipo (opcional)
        switch(type) {
            case 0: // Info
                // Você pode mudar o background aqui se tiver diferentes estilos
                break;
            case 1: // Warning
                // layout.setBackgroundResource(R.drawable.toast_warning_bg);
                break;
            case 2: // Error
                // layout.setBackgroundResource(R.drawable.toast_error_bg);
                break;
        }

        // IMPORTANTE: Criar o toast e definir o layout customizado
        Toast toast = new Toast(context);
        toast.setDuration(type == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.setView(layout); // Aqui você usa o layout inflado!
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40);
        toast.show();
    }

    public void showInfo(String text) {
        showToast(0, text);
    }

    public void showWarning(String text) {
        showToast(1, text);
    }

    public void showError(String text) {
        showToast(2, text);
    }
}