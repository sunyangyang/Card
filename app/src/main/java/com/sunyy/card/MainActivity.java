package com.sunyy.card;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout layout = findViewById(R.id.content_view);
        final CardView cardView = new CardView(this, "");
        layout.addView(cardView);
        Button button = findViewById(R.id.restart_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getAnimator() != null)
                    cardView.getAnimator().start();
            }
        });

        mEdit = findViewById(R.id.edit);

        findViewById(R.id.duration_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long time = Long.valueOf(mEdit.getText().toString());
                    if (cardView.getAnimator() != null) {
                        cardView.getAnimator().setDuration(time);
                        cardView.getAnimator().start();
                    }
                } catch (Exception e) {
                    cardView.getAnimator().setDuration(1000);
                    cardView.getAnimator().start();

                }

            }
        });
    }
}
