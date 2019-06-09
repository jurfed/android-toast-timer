package com.example.toast;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private CheckBox mSingleShotCheckBox;
    private Button mStartButton, mCancelButton;
    private TextView mCounterTextView;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//таймер выводы текущего времени
        mSingleShotCheckBox = (CheckBox) findViewById(R.id.checkBoxSingleShot);
        mStartButton = (Button) findViewById(R.id.buttonStart);
        mCancelButton = (Button) findViewById(R.id.buttonCancel);
        mCounterTextView = (TextView) findViewById(R.id.textViewCounter);
        mStartButton.setOnClickListener((vew) -> {
            if (mTimer != null) {
                mTimer.cancel();
            }


            mTimer = new Timer();
            mMyTimerTask = new MyTimerTask();
            if (mSingleShotCheckBox.isChecked()) {
                mTimer.schedule(mMyTimerTask, 1000);
            } else {
                mTimer.schedule(mMyTimerTask, 5000, 1000);
            }

        });

    }

    /**
     * таймер для вывода текущего времени
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Calendar calendat = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a", Locale.getDefault());
            final String strDate = simpleDateFormat.format(calendat.getTime());

            runOnUiThread(() -> {
                mCounterTextView.setText(strDate);
            });
        }
    }



    /**
     * кнопка для вывода стандартного всплывающего сообщения с картинкой
     *
     * @param view
     */
    public void showMessage(View view) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Toast toast = Toast.makeText(getApplicationContext(), "Пора покормить кота !!!!!!!!!!!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);

//добавим картинку к всплывающему сообщению
                ImageView catImageView = new ImageView(getApplicationContext());
                catImageView.setImageResource(R.mipmap.s800);

                LinearLayout toastContainer = (LinearLayout) toast.getView();
                toastContainer.addView(catImageView);

//вывод сообщения с картинкой
                toast.show();
            }
        });
    }



    /**
     * кастомное сообщение с собственной разметкой из custom_layout.xml
     * @param view
     */
    public void customMessage(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_layout, (ViewGroup) findViewById(R.id.toast_layout));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
