package Mercury.Android.Mercury_View.Activities;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Mercury.Android.Mercury_Model.Entitys.Entity_03_Message;
import Mercury.Android.Mercury_View.RecyclerView.RView_Chat_01_Msg_Adp;
import Mercury.Android.R;
import eightbitlab.com.blurview.BlurView;

@SuppressWarnings("SpellCheckingInspection")
public class Activity_03_Chat extends AppCompatActivity {

    private BlurView blurView;


    private ConstraintLayout bottomBar;

    private RView_Chat_01_Msg_Adp adapter;

    private boolean isKeyboardVisible;
    private List<Entity_03_Message> messageList;

    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {

        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_03_chat);

        RecyclerView recyclerView = findViewById(R.id.rv_message);
        EditText editMessage = findViewById(R.id.messageTextfield);
        ImageButton buttonSend = findViewById(R.id.send);
        button = findViewById(R.id.clipButton);

        messageList = new ArrayList<>();
        adapter = new RView_Chat_01_Msg_Adp(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        View rootLayout = findViewById(R.id.chat);
        int navigationBarHeight = getNavigationBarHeight();

        bottomBar = findViewById(R.id.bottom_bar);
        setupKeyboardListener();

        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisamos usar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Se houver texto, esconde o ícone; se estiver vazio, mostra
                if (s.length() > 0) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editMessage.getText().toString().trim();
                if (!text.isEmpty()) {
                    // Cria nova mensagem
                    Entity_03_Message newMessage = new Entity_03_Message();
                    newMessage.setMessage(text);
                    newMessage.setDateTimeMessage(new Date());
                    newMessage.setWasVisualized(false);

                    // Adiciona na lista
                    messageList.add(newMessage);
                    adapter.notifyItemInserted(messageList.size() - 1);

                    // Rola para a última mensagem
                    recyclerView.scrollToPosition(messageList.size() - 1);

                    // Limpa campo de texto
                    editMessage.setText("");
                }
            }
        });

        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#99202020"));

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        blurView = findViewById(R.id.blurview);


        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(decorView).setFrameClearDrawable(windowBackground).setBlurRadius(12f);

        ImageButton popupMenu = findViewById(R.id.menu);

        popupMenu.setOnClickListener(v -> {
            int offsetDp = 20;
            int offsetY = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, offsetDp, getResources().getDisplayMetrics());
            offsetY = -offsetY;

            PopupMenu popup = new PopupMenu(this, v, Gravity.NO_GRAVITY, 0, offsetY);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            try {
                Field mPopup = popup.getClass().getDeclaredField("mPopup");
                mPopup.setAccessible(true);
                Object menuPopupHelper = mPopup.get(popup);
                Method setBackgroundDrawable = menuPopupHelper.getClass()
                        .getDeclaredMethod("setBackgroundDrawable", Drawable.class);
                setBackgroundDrawable.invoke(menuPopupHelper,
                        ContextCompat.getDrawable(this, R.drawable.bg_popup_menu));
            } catch (Exception e) {
                e.printStackTrace();
            }

            popup.setOnMenuItemClickListener(item -> true);
            popup.show();
        });

        ImageButton camera = findViewById(R.id.camera);
        camera.setOnClickListener(v -> {
            int offsetDp = 20;
            int offsetY = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, offsetDp, getResources().getDisplayMetrics());
            offsetY = -offsetY;

            PopupMenu camera1 = new PopupMenu(this, v, Gravity.NO_GRAVITY, 0, offsetY);
            camera1.getMenuInflater().inflate(R.menu.camera_pop_up, camera1.getMenu());

            try {
                Field mPopup = camera1.getClass().getDeclaredField("mPopup");
                mPopup.setAccessible(true);
                Object menuPopupHelper = mPopup.get(camera1);
                Method setBackgroundDrawable = menuPopupHelper.getClass()
                        .getDeclaredMethod("setBackgroundDrawable", Drawable.class);
                setBackgroundDrawable.invoke(menuPopupHelper,
                        ContextCompat.getDrawable(this, R.drawable.bg_popup_menu));
            } catch (Exception e) {
                e.printStackTrace();
            }

            camera1.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case 1:
                        return true;
                    case 2:
                        return true;
                    default:
                        return false;
                }
            });

            camera1.show();
        });

    }
    private void setupKeyboardListener() {
        View rootView = findViewById(android.R.id.content);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);

                int screenHeight = rootView.getHeight();
                int keypadHeight = screenHeight - rect.bottom;

                ConstraintLayout.LayoutParams layoutParams =
                        (ConstraintLayout.LayoutParams) bottomBar.getLayoutParams();

                if (keypadHeight > screenHeight * 0.15) {
                    layoutParams.bottomMargin = keypadHeight;
                } else {
                    layoutParams.bottomMargin = dpToPx(0);
                }

                bottomBar.setLayoutParams(layoutParams);
            }
        });
    }

    private int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("onPause", "TESTE");

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e("onResume", "TESTE");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e("LyfeCycle","");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.e("LyfeCycle","");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("LyfeCycle","");

    }

}

