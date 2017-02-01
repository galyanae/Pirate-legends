package galyanae.gamejam;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by RonyBrosh on 1/20/2017.
 */

public class GameManager
{
    public static final int ROW_DIRECTION_LEFT = 0;
    public static final int ROW_DIRECTION_RIGHT = 1;
    public static final int NUMBER_OF_ROWS = 6;

    private RelativeLayout mGameContainer;
    private HashMap<Integer, ArrayList<GameObject>> mGameObjectArray;

    private final int NUMBER_OF_COLUMNS = 10;

    private float mScreenWidth;
    private float mScreenHeight;
    private float mGameViewWidth;
    private float mGameViewHeight;
    private float mRowHeight;
    private float mColumnWidth;
    private float mPadding;

    private Thread mGameThread;
    private boolean mIsRunning;

    public Ship mPlayer;
    private GameManagerListener mGameManagerListener;

    public GameManager(Context context, RelativeLayout gameContainer, GameManagerListener gameManagerListener)
    {
        mGameManagerListener = gameManagerListener;
        mGameContainer = gameContainer;
        initSizes(context);
        initGameObjectArray(context);
        initPlayer(context);
    }

    private void initPlayer(Context context)
    {
        mPlayer = new Ship(context, (int) mRowHeight);
        mPlayer.getLayoutParams().width = (int) (mRowHeight * 1.5f);
        mPlayer.getLayoutParams().height = mPlayer.getLayoutParams().width;
        mPlayer.reset();
        mGameContainer.addView(mPlayer);
    }

    private void initSizes(Context context)
    {
        mPadding = Utils.dpTOpx(context, 16);
        mScreenWidth = Utils.getScreenSize(context, true);
        mScreenHeight = Utils.getScreenSize(context, false);
        mGameViewWidth = mScreenWidth - 2 * mPadding;
        mGameViewHeight = mScreenHeight - 2 * mPadding;
        mRowHeight = mGameViewHeight / NUMBER_OF_ROWS;
        mColumnWidth = mGameViewWidth / NUMBER_OF_COLUMNS;
    }

    private void initGameObjectArray(Context context)
    {
        mGameObjectArray = new HashMap<>();
        ArrayList<GameObject> rowGameObjectArray;
        for (int rowIndex = 0; rowIndex < NUMBER_OF_ROWS; rowIndex++)
        {
            rowGameObjectArray = new ArrayList<>();
            for (int columnIndex = 0; columnIndex < NUMBER_OF_COLUMNS; columnIndex++)
            {
                int objectType = GameLevel.LEVEL_ONE[rowIndex * NUMBER_OF_COLUMNS + columnIndex];
                float locationX = mPadding + columnIndex * mColumnWidth;
                float locationY = mPadding + rowIndex * mRowHeight;
                switch (objectType)
                {
                    case GameLevel.COIN:
                    {
                        float gameObjectHeight = getGameObjectHeight(R.drawable.pirates_coin330x330);
                        locationY += (Math.abs(mRowHeight - gameObjectHeight) / 2f);
                        Enemy enemy = Enemy.createEnemy(context, R.drawable.pirates_coin330x330, locationX, locationY);
                        rowGameObjectArray.add(enemy);
                        mGameContainer.addView(enemy);
                        break;
                    }
                    case GameLevel.RUM:
                    {
                        float gameObjectHeight = getGameObjectHeight(R.drawable.pirates_rum330x330);
                        locationY += (Math.abs(mRowHeight - gameObjectHeight) / 2f);
                        Enemy enemy = Enemy.createEnemy(context, R.drawable.pirates_rum330x330, locationX, locationY);
                        rowGameObjectArray.add(enemy);
                        mGameContainer.addView(enemy);
                        break;
                    }
                    case GameLevel.ROCKS:
                    {
                        float gameObjectHeight = getGameObjectHeight(R.drawable.pirates_rocks330x330);
                        locationY -= (gameObjectHeight - mRowHeight);
                        Enemy enemy = Enemy.createEnemy(context, R.drawable.pirates_rocks330x330, locationX, locationY);
                        rowGameObjectArray.add(enemy);
                        mGameContainer.addView(enemy);
                        break;
                    }
                    case GameLevel.KRACKEN:
                    {
                        float gameObjectHeight = getGameObjectHeight(R.drawable.pirates_kraken330x330);
                        locationY -= (gameObjectHeight - mRowHeight);
                        Enemy enemy = Enemy.createEnemy(context, R.drawable.pirates_kraken330x330, locationX, locationY);
                        rowGameObjectArray.add(enemy);
                        mGameContainer.addView(enemy);
                        AnimationUtil.animateKraken(enemy, mColumnWidth * 3, null);
                        break;
                    }
                    case GameLevel.MAIN_TREASURE:
                    {
                        float gameObjectHeight = getGameObjectHeight(R.drawable.pirates_treasure330x330);
                        locationY -= (gameObjectHeight - mRowHeight);
                        Enemy enemy = Enemy.createEnemy(context, R.drawable.pirates_treasure330x330, locationX, locationY);
                        rowGameObjectArray.add(enemy);
                        mGameContainer.addView(enemy);
                        break;
                    }
                }
            }
            mGameObjectArray.put(rowIndex, rowGameObjectArray);
        }
    }

    private float getGameObjectHeight(int resourceID)
    {
        float result = 0;
        switch (resourceID)
        {
            case R.drawable.pirates_rocks330x330:
            {
                result = mScreenHeight * 286f / 1080f;
                break;
            }
            case R.drawable.pirates_coin330x330:
            {
                result = mScreenHeight * 104f / 1080f;
                break;
            }
            case R.drawable.pirates_rum330x330:
            {
                result = mScreenHeight * 154f / 1080f;
                break;
            }
            case R.drawable.pirates_kraken330x330:
            {
                result = mScreenHeight * 213f / 1080f;
                break;
            }
            case R.drawable.pirates_treasure330x330:
            {
                result = mScreenHeight * 274f / 1080f;
                break;
            }
        }
        return result;
    }

    public float getRowHeight()
    {
        return mRowHeight;
    }

    public int getRowDirection(int rowIndex)
    {
        return rowIndex % 2;
    }

    public void startGame()
    {
        mIsRunning = true;
        mGameThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (mIsRunning == true)
                {
                    try
                    {
                        mPlayer.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (mPlayer.isAnimative == false)
                                {
                                    checkForCollision();
                                    switch (mPlayer.getCurrentRowIndex())
                                    {
                                        case 0:
                                        {
                                            mPlayer.slide(1, 666);
                                            break;
                                        }
                                        case 1:
                                        {
                                            mPlayer.slide(0, 1000);
                                            break;
                                        }
                                        case 2:
                                        {
                                            mPlayer.slide(1, 666);
                                            break;
                                        }
                                        case 3:
                                        {
                                            mPlayer.slide(1, 1000);
                                            break;
                                        }
                                        case 4:
                                        {
                                            mPlayer.slide(0, 666);
                                            break;
                                        }
                                        case 5:
                                        {
                                            mPlayer.slide(1, 2000);
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                        Thread.sleep(50);
                    }
                    catch (Exception ex)
                    {

                    }
                }
            }
        });
        mGameThread.start();
    }

    public void checkForCollision()
    {
        ArrayList<GameObject> rowGameObjects = mGameObjectArray.get(mPlayer.getCurrentRowIndex());
        if (rowGameObjects != null)
        {
            for (int index = 0; index < rowGameObjects.size(); index++)
            {
                GameObject nextGameObject = rowGameObjects.get(index);
                int playerLeft = (int) mPlayer.getX();
                int playerRight = playerLeft + mPlayer.getLayoutParams().width;
                int nextGameObjectLeft = (int) nextGameObject.getX();
                int nextGameObjectRight = nextGameObjectLeft + nextGameObject.getLayoutParams().width;

                if ((playerLeft >= nextGameObjectLeft && playerLeft < nextGameObjectRight) || (playerRight >= nextGameObjectLeft && playerRight < nextGameObjectRight))
                {
                    switch (nextGameObject.mResourceID)
                    {
                        case R.drawable.pirates_rocks330x330:
                        {

                        }
                        case R.drawable.pirates_kraken330x330:
                        {
                            mGameManagerListener.onEnemyHitPlayer(nextGameObject.mResourceID);
                            break;
                        }
                        case R.drawable.pirates_coin330x330:
                        {

                        }
                        case R.drawable.pirates_rum330x330:
                        {

                        }
                        case R.drawable.pirates_treasure330x330:
                        {
                            mGameContainer.removeView(rowGameObjects.remove(index));
                            mGameObjectArray.put(mPlayer.getCurrentRowIndex(), rowGameObjects);
                            mGameContainer.invalidate();
                            mGameContainer.requestLayout();
                            mGameManagerListener.onTreasureCollected(nextGameObject.mResourceID);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void stopGame()
    {
        mIsRunning = false;
        mPlayer.clearAnimation();
    }

    public interface GameManagerListener
    {
        void onTreasureCollected(int treasureType);

        void onEnemyHitPlayer(int enemyType);
    }
}
