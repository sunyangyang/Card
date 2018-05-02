package com.sunyy.card;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

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
    }
}
