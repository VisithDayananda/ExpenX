package com.expenx.expenx.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.expenx.expenx.R;

public class LoginActivity extends AppCompatActivity {

    ImageView mCircleAroundE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setDuration(6000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.start();

        mCircleAroundE = (ImageView) findViewById(R.id.circleAroundE);
        mCircleAroundE.setAnimation(rotate);
    }
}
