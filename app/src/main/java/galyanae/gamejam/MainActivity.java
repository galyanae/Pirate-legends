package galyanae.gamejam;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static galyanae.gamejam.R.id.cancel;

public class MainActivity extends AppCompatActivity
{

    private GameManager mGameManager;
    android.support.v7.app.AlertDialog show;

    RelativeLayout backgroundScreen;

    View view;
    ImageView close;

    float initialX;
    float initialY;

    float finalX;
    float finalY;

    boolean isUp;
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        backgroundMusic = MediaPlayer.create(this, R.raw.pirates);
        backgroundMusic.setVolume(5, 5);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        backgroundScreen = (RelativeLayout) findViewById(R.id.backgroundScreen);
        backgroundScreen.setOnTouchListener(shipMovement);

        initActivity();

    }

    View.OnTouchListener shipMovement = new View.OnTouchListener()
    {
        public boolean onTouch(View v, MotionEvent event)
        {

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    initialX = event.getX();
                    initialY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("MOOVED");
                    break;
                case MotionEvent.ACTION_UP:
                    finalX = event.getX();
                    finalY = event.getY();
                    System.out.println("MOOVement ENDED");
                    if (initialY < finalY)
                    {
                        System.out.println("Up to Down");
                        isUp = false;
                    }

                    if (initialY > finalY)
                    {
                        System.out.println("Down to Up");
                        isUp = true;
                    }
                    if (!mGameManager.mPlayer.isAnimative)
                    {
                        mGameManager.mPlayer.shipMoove(isUp);
                    }
                    else
                    {
                        System.out.println("Not Animated");
                    }
                case MotionEvent.ACTION_CANCEL:
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    System.out.println("Movement occurred outside bounds of current screen element");
                    break;
            }

            return true;
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        backgroundMusic.start();
        mGameManager.startGame();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        backgroundMusic.stop();
        mGameManager.stopGame();
    }
    @Override
    protected void onStop() {
        if (backgroundMusic!= null) {
            try {
                backgroundMusic.stop();
            }
            catch (Exception e) {
                e.printStackTrace();
            }}
        finish();
        super.onStop();
    }

    private void initActivity()
    {
        mGameManager = new GameManager(this, backgroundScreen, new GameManager.GameManagerListener()
        {
            @Override
            public void onTreasureCollected(int treasureType)
            {
                String message = "";
                switch (treasureType)
                {
                    case R.drawable.pirates_coin330x330:
                    {
                        message = "Got COIN";
                        break;
                    }
                    case R.drawable.pirates_rum330x330:
                    {
                        message = "Got RUM";
                        break;
                    }
                    case R.drawable.pirates_treasure330x330:
                    {
                        mGameManager.stopGame();
                        Toast.makeText(MainActivity.this, "YOU WON!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEnemyHitPlayer(int enemyType)
            {
                String message = "";
                switch (enemyType)
                {
                    case R.drawable.pirates_kraken330x330:
                    {
                        message = "Hit by KRAKEN";
                        break;
                    }
                    case R.drawable.pirates_rocks330x330:
                    {
                        message = "Hit by ROCK";
                        break;
                    }
                    default:
                    {
                        message = "Hit by SCREEN";
                        break;
                    }
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                mGameManager.stopGame();

            }
        });
        initWaves();
    }

    private void initWaves()
    {
        LinearLayout wavesContainer = (LinearLayout) findViewById(R.id.WavesContainer);
        for (int index = 0; index < wavesContainer.getChildCount(); index++)
        {
            LinearLayout nextWaveLinear = (LinearLayout) wavesContainer.getChildAt(index);
            ImageView nextWave = (ImageView) nextWaveLinear.getChildAt(0);
            switch (index)
            {
                case 0:
                {
                    Glide.with(this).load(R.drawable.wave_left).asGif().into(nextWave);
                    break;
                }
                case 1:
                {
                    Glide.with(this).load(R.drawable.wave_right).asGif().into(nextWave);
                    break;
                }
                case 2:
                {
                    Glide.with(this).load(R.drawable.wave_left).asGif().into(nextWave);
                    break;
                }
                case 3:
                {
                    Glide.with(this).load(R.drawable.wave_left).asGif().into(nextWave);
                    break;
                }
                case 4:
                {
                    Glide.with(this).load(R.drawable.wave_right).asGif().into(nextWave);
                    break;
                }
                case 5:
                {
                    Glide.with(this).load(R.drawable.wave_left).asGif().into(nextWave);
                    break;
                }
            }
        }
    }
}


