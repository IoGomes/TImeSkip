package Mercury.Android.Mercury_View.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
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
import java.util.Objects;

import Mercury.Android.Mercury_Model.Entitys.Entity_03_Message;
import Mercury.Android.Mercury_View.RecyclerView.RV_Chat_01_Msg_Adapter;
import Mercury.Android.R;
import Mercury.Android.databinding.Activity03ChatBinding;

/// @author Ítalo Oliveira Gomes

public class Activity_03_Chat extends AppCompatActivity {

    private ConstraintLayout bottomBar;
    private RV_Chat_01_Msg_Adapter adapter;
    private List<Entity_03_Message> messageList;
    private ImageButton button;
    Activity03ChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {

        super.onCreate(savedInstanceBundle);

        binding = Activity03ChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.parseColor("#101010"));

        Objects.requireNonNull(getSupportActionBar()).hide();

        RecyclerView recyclerView = findViewById(R.id.rv_message);
        EditText editMessage = findViewById(R.id.messageTextfield);
        ImageButton buttonSend = findViewById(R.id.send);
        button = findViewById(R.id.clipButton);

        messageList = new ArrayList<>();
        adapter = new RV_Chat_01_Msg_Adapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getNavigationBarHeight();

        bottomBar = findViewById(R.id.bottom_bar);
        setupKeyboardListener();

        binding.videoCall.setOnClickListener(v -> {
            Intent intent = new Intent(this, Activity_05_Video_Call.class);
            startActivity(intent);
        });

        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }
            }

        });

        buttonSend.setOnClickListener(v -> {

            String text = editMessage.getText().toString().trim();

            if (!text.isEmpty()) {

                Entity_03_Message newMessage = new Entity_03_Message();
                newMessage.setMessage(text);
                newMessage.setDateTimeMessage(new Date());
                newMessage.setWasVisualized(false);

                messageList.add(newMessage);
                adapter.notifyItemInserted(messageList.size() - 1);

                recyclerView.scrollToPosition(messageList.size() - 1);

                editMessage.setText("");
            }
        });

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
                assert menuPopupHelper != null;
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

}

