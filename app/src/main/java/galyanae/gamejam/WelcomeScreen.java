package galyanae.gamejam;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    View view;
    MediaPlayer welcomeMusic;
    MediaPlayer playButtonSound;
    android.support.v7.app.AlertDialog show;

    TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        welcomeMusic = MediaPlayer.create(this,R.raw.pirates_intro);
        welcomeMusic.setVolume(5,5);
        welcomeMusic.setLooping(true);
        welcomeMusic.start();
    }

    public void toPlayScreen(View view) {
        playButtonSound = MediaPlayer.create(this,R.raw.arrr);
        playButtonSound.setVolume(10,10);
        playButtonSound.setLooping(false);
        playButtonSound.start();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void exitGame(View view) {
        welcomeMusic.stop();
        finish();
    }

    public void aboutUs(View view) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.about_us_dialog, null, false);

        cancel= (TextView) view.findViewById(R.id.cancel);

        builder.setView(view);
        show = builder.show();
        show.setView(view);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cancel(arg0);
            }}
        );}
    public void cancel(View v) {
        show.dismiss();
    }

    public void onFinish() {
        welcomeMusic.stop();
    }

    @Override
    protected void onStop() {
        if (playButtonSound != null | welcomeMusic!= null) {
            try {
                playButtonSound.stop();
                welcomeMusic.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }}
        finish();
        super.onStop();
    }
}


