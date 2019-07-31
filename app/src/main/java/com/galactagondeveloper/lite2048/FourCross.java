package com.galactagondeveloper.lite2048;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import java.util.Random;

public class FourCross extends AppCompatActivity {

    int[][] number = new int[4][4];
    TextView[][] textView = new TextView[4][4];
    float x1, y1, x2, y2;
    int i,j,k;
    boolean changed=true, win=false;
    boolean[][] mergeChecker = new boolean[4][4];
    SharedPreferences saveState;
    SharedPreferences.Editor saver;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        saveState = this.getSharedPreferences("save_state_pref", MODE_PRIVATE);
        saver = saveState.edit();

        setContentView(R.layout.fourcross);
        setValues();
        loadGameState();
    }
    public void setRandom() {
        if(changed) {
            int x, y;
            Random random = new Random();
            x = random.nextInt(4);
            y = random.nextInt(4);
            if (number[x][y] == -1) {
                if (random.nextInt(5) < 3) {
                    number[x][y] = 2;
                } else {
                    number[x][y] = 4;
                }
            } else {
                setRandom();
            }
            changed=false;
        }
    }
    void checkEndGame() {
        for(i=0;i<4;i++) {
            for(j=0;j<4;j++) {
                if(number[i][j] == -1) {
                    return;
                }
            }
            for(j=0;j<3;j++) {
                if(number[i][j] == number[i][j+1]) {
                    return;
                }
            }
            for(j=0;j<3;j++) {
                if(number[j][i] == number[j+1][i]) {
                    return;
                }
            }
        }
        new AlertDialog.Builder(this)
                .setTitle("Game over")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(FourCross.this, FourCross.class));
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(FourCross.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }
    public void updateText() {
        for(i=0;i<4;i++) {
            for(j=0;j<4;j++) {
                if(number[i][j]<=-1) {
                    textView[i][j].setText("");
                } else {
                    textView[i][j].setText(Integer.toString(number[i][j]));
                }
            }
        }
    }
    public void onRight() {
        for(i=0;i<4;i++) {
            for(j=3;j>0;j--) {
                for(k=3;k>0;k--) {
                    if (number[i][k] == -1 && number[i][k-1] != -1) {
                        number[i][k] = number[i][k - 1];
                        number[i][k - 1] = -1;
                        changed = true;
                    } else {
                        if (number[i][k] == number[i][k-1] && number[i][k-1] != -1 && !mergeChecker[i][k] && !mergeChecker[i][k-1]) {
                            number[i][k] *= 2;
                            number[i][k-1] = -1;
                            changed = true;
                            mergeChecker[i][k] = true;
                            if(!win && number[i][k] == 2048) {
                                Toast.makeText(this, "You win", Toast.LENGTH_LONG).show();
                                win = true;
                            }
                        }
                    }
                }
            }
        }
    }
    public void onLeft() {
        for(i=0;i<4;i++) {
            for(j=0;j<3;j++) {
                for(k=0;k<3;k++) {
                    if (number[i][k] == -1 && number[i][k+1] != -1) {
                        number[i][k] = number[i][k+1];
                        number[i][k+1] = -1;
                        changed=true;
                    } else {
                        if (number[i][k] == number[i][k+1] && number[i][k+1] != -1 && !mergeChecker[i][k] && !mergeChecker[i][k+1]) {
                            number[i][k] *= 2;
                            number[i][k + 1] = -1;
                            changed=true;
                            mergeChecker[i][k] = true;
                            if(!win && number[i][k] == 2048) {
                                Toast.makeText(this, "You win", Toast.LENGTH_LONG).show();
                                win = true;
                            }
                        }
                    }
                }
            }
        }
    }
    public void onUp() {
        for(i=0;i<4;i++) {
            for(j=0;j<3;j++) {
                for(k=0;k<3;k++) {
                    if (number[k][i] == -1 && number[k+1][i] != -1) {
                        number[k][i] = number[k + 1][i];
                        number[k + 1][i] = -1;
                        changed=true;
                    } else {
                        if (number[k][i] == number[k + 1][i] && number[k+1][i] != -1 && !mergeChecker[k][i] && !mergeChecker[k+1][i]) {
                            number[k][i] *= 2;
                            number[k+1][i] = -1;
                            changed=true;
                            mergeChecker[k][i] = true;
                            if(!win && number[k][i] == 2048) {
                                Toast.makeText(this, "You win", Toast.LENGTH_LONG).show();
                                win = true;
                            }
                        }
                    }
                }
            }
        }
    }
    public void onDown() {
        for(i=0;i<4;i++) {
            for(j=3;j>0;j--) {
                for(k=3;k>0;k--) {
                    if (number[k][i] == -1 && number[k-1][i] != -1) {
                        number[k][i] = number[k - 1][i];
                        number[k-1][i] = -1;
                        changed=true;
                    } else {
                        if (number[k][i] == number[k-1][i] && number[k-1][i] != -1 && !mergeChecker[k][i] && !mergeChecker[k-1][i]) {
                            number[k][i] *= 2;
                            number[k-1][i] = -1;
                            changed=true;
                            mergeChecker[k][i] = true;
                            if(!win && number[k][i] == 2048) {
                                Toast.makeText(this, "You win", Toast.LENGTH_LONG).show();
                                win = true;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(Math.abs(x2-x1)>Math.abs(y2-y1)) {
                    //Horizontal
                    if(x2>x1) {
                        //Right
                        onRight();
                    } else {
                        //Left
                        onLeft();
                    }
                } else {
                    //Vertical
                    if(y2>y1) {
                        //down
                        onDown();
                    } else {
                        //up
                        onUp();
                    }
                }
                break;
        }
        setRandom();
        updateText();
        setBoolFalse();
        checkEndGame();
        return super.onTouchEvent(event);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Save and exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveGameState();
                        startActivity(new Intent(FourCross.this, MainActivity.class));
                        FourCross.this.finish();
                    }
                })
                .setNeutralButton("No", null)
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saver.putBoolean("savedLastTime", false).commit();
                        startActivity(new Intent(FourCross.this, MainActivity.class));
                        FourCross.this.finish();
                    }
                })
                .show();
    }
    void setValues() {
        int i,j;
        for(i=0;i<4; i++) {
            for(j=0;j<4;j++) {
                number[i][j] = -1;
            }
        }
        textView[0][0] = findViewById(R.id.t00);
        textView[0][1] = findViewById(R.id.t01);
        textView[0][2] = findViewById(R.id.t02);
        textView[0][3] = findViewById(R.id.t03);

        textView[1][0] = findViewById(R.id.t10);
        textView[1][1] = findViewById(R.id.t11);
        textView[1][2] = findViewById(R.id.t12);
        textView[1][3] = findViewById(R.id.t13);

        textView[2][0] = findViewById(R.id.t20);
        textView[2][1] = findViewById(R.id.t21);
        textView[2][2] = findViewById(R.id.t22);
        textView[2][3] = findViewById(R.id.t23);

        textView[3][0] = findViewById(R.id.t30);
        textView[3][1] = findViewById(R.id.t31);
        textView[3][2] = findViewById(R.id.t32);
        textView[3][3] = findViewById(R.id.t33);
    }

    void saveGameState() {
        for(i=0;i<4;i++) {
            for(j=0;j<4;j++) {
                String key = (Integer.toString(i) + Integer.toString(j));
                saver.putInt(key, number[i][j]).commit();
            }
        }
        saver.putBoolean("savedLastTime", true).commit();
    }
    void loadGameState() {
        if(saveState.getBoolean("savedLastTime", false)) {
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    String key = (Integer.toString(i) + Integer.toString(j));
                    number[i][j] = saveState.getInt(key, -1);
                }
            }
        } else {
            setRandom();
        }
        updateText();
    }
    void setBoolFalse() {
        for(i=0;i<4;i++) {
            for(j=0;j<4;j++) {
                mergeChecker[i][j] = false;
            }
        }
    }
}
