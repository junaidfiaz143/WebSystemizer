package com.inventors.jd.keeper.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.inventors.jd.keeper.R;

public class SplashActivity extends AppCompatActivity {


    private TextView txtAppName, txtSlogan;

    private Typeface clanBookTypeFace, billabongTypeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        billabongTypeFace = Typeface.createFromAsset(getAssets(),
                "billabong.ttf");

        clanBookTypeFace = Typeface.createFromAsset(getAssets(),
                "clan_book.ttf");

        txtAppName = (TextView) findViewById(R.id.txtAppName);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);

        txtAppName.setTypeface(billabongTypeFace);
        txtSlogan.setTypeface(clanBookTypeFace);

//        if (Build.VERSION.SDK_INT >= 25) {
//            createShortcut();
//        } else {
//            removeShortcuts();
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class).putExtra("url", "https://www.vinqel.com");
                ;


                startActivity(intent);
                finish();
            }
        }, 3000);
    }

//    @TargetApi(25)
//    private void createShorcut() {
//        ShortcutManager sM = getSystemService(ShortcutManager.class);
//
//        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
//        intent1.setAction(Intent.ACTION_VIEW);
//
//        ShortcutInfo shortcut1 = new ShortcutInfo.Builder(this, "shortcut1")
//                .setIntent(intent1)
//                .setShortLabel("Test")
//                .setLongLabel("Shortcut 1")
//                .setShortLabel("This is the shortcut 1")
//                .setDisabledMessage("Login to open this")
//                .setIcon(Icon.createWithResource(this, R.drawable.ic_add))
//                .build();
//
//        sM.setDynamicShortcuts(Arrays.asList(shortcut1));
//    }


}
