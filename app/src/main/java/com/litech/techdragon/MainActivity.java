package com.litech.techdragon;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    private int power = 1;
    private int enemyHP;
    private int myHP;

    private TextView powerTextView;
    private TextView enemyHPTextView;
    private TextView myHPTextView;
    private TextView damageMessage1TextView;
    private TextView damageMessage2TextView;


    private ImageView dragonImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerTextView = (TextView) findViewById(R.id.powerTextView);
        enemyHPTextView = (TextView) findViewById(R.id.enemyHPTextView);
        myHPTextView = (TextView) findViewById(R.id.myHPTextView);
        dragonImageView = (ImageView) findViewById(R.id.dragonImageView);
        damageMessage1TextView = (TextView) findViewById(R.id.damageMessage1TextView);
        damageMessage2TextView = (TextView) findViewById(R.id.damageMessage2TextView);

        enemyHP = 100;
        myHP = 100;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void powerUp(View v) {
        /*
         *
		 * 問題1 画面上のTextViewの数字が3ずつ増えていくボタンを作ろう。
		 * 問題2 数字が10以上30未満ならば文字の色が緑に、30以上50未満ならば青、
		 * 50以上ならば赤になるコードを、if文を使って問題1の関数の中に加えてみよう。
		 * 問題3 問題2の条件分岐コードをswitch文で書き換えてみよう。
		 */
        power += 3;
        if (power > 10 && power < 30) {
            powerTextView.setTextColor(Color.parseColor("#008000"));
        } else if (power >= 30 && power < 50) {
            powerTextView.setTextColor(Color.parseColor("#0000FF"));
        } else if (power >= 50) {
            powerTextView.setTextColor(Color.parseColor("#FF0000"));
        }
        //powerTextView.setText(String.valueOf(power));
        damageMessage1TextView.setText("攻撃なし");
        counterAttack();
        checkDeath();
    }

    public void attack(View v) {
		/*
		 * 
		 * 問題4 攻撃ボタンを作って、押すと相手にダメージを与えるボタンを作ってみよう。
		 * 問題5 「damageAnimation」メソッドを呼び出すメソッドを書き加えてみよう。
		 * 問題6 相手の体力が0以下になったときに「clearAnimation」メソッドを書き加えてみよう。
		 */
        damageAnimation();
        enemyHP -= power;
        damageMessage1TextView.setText(String.valueOf(power) + "攻撃");
        //enemyHPTextView.setText(String.valueOf(enemyHP));
        counterAttack();
        checkDeath();
    }

    //敵に0以上20以下のランダム値で攻撃される関数
    public void counterAttack() {
        Random randomGen = new Random();
        int randomDamage = randomGen.nextInt(20);
        myHP -= randomDamage;
        damageMessage2TextView.setText(String.valueOf(randomDamage) + "ダメージ");

    }

    //会心の一撃
    public void fxxk(View v) {
        Random randomGen = new Random();
        if (randomGen.nextInt(10) > 7) {
            myHP -= 50;
            myHPTextView.setText(String.valueOf(myHP));
        } else {
            enemyHP -= 50;
            enemyHPTextView.setText(String.valueOf(enemyHP));
        }
        checkDeath();
    }

    //自分または敵が死んだかどうかを確認し、それに応じて処理
    //相手からの攻撃を受けるより自分が攻撃する方が優先とした。
    public void checkDeath() {
        if (enemyHP < 0) {//相手が死んだ
            enemyHP = 0;
            damageMessage1TextView.setText("You Win");
            damageMessage2TextView.setText("Dragon was killed");
            //敵が消滅
            clearAnimation();
        } else if (myHP < 0) {//自分が死んだ
            myHP = 0;
            damageMessage1TextView.setText("You Lose");
            damageMessage2TextView.setText("You were killed");

        }

        /*if (power > 10) {
            damageMessageTextView.setText("大ダメージ");
            damageTextView.setText(String.valueOf(power));
        } else {
            damageMessageTextView.setText("ミス");
            damageTextView.setText("");
        }*/

        enemyHPTextView.setText(String.valueOf(enemyHP));
        myHPTextView.setText(String.valueOf(myHP));
        powerTextView.setText(String.valueOf(power));


    }


    public void retry(View v) {

		/*
		 * 
		 * 問題7 はじめからやり直す「リトライボタン」を作って、最初の状態に戻してみよう。
		 */
        // 相手のHP
        enemyHP = 100;
        enemyHPTextView.setText(String.valueOf(enemyHP));
        // 自分の攻撃力
        power = 1;
        powerTextView.setText(String.valueOf(power));
        //自分のHP
        myHP = 100;
        myHPTextView.setText(String.valueOf(myHP));

        // 相手モンスター復活
        dragonImageView.setVisibility(View.VISIBLE);
        // 攻撃力の文字の色を白に。(色が変わっている場合もあるので。)
        powerTextView.setTextColor(Color.parseColor("#FFFFFF"));
        damageMessage1TextView.setText("");
        damageMessage2TextView.setText("");
    }

    public void info(View v) {
		/* 問題極 画面を遷移 */
        Intent intent = new Intent(this, ExplainActiity.class);
        startActivity(intent);

    }


    // 攻撃アニメーション
    public void damageAnimation() {
        //atackanimation.java参照
		/*AttackAnimation attackAnim = new AttackAnimation(
				this.getApplicationContext(),
				dragonImageView, damageTextView) ;
		//
		dragonImageView.startAnimation(attackAnim) ;*/

    }


    // 消滅アニメーション
    public void clearAnimation() {
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        alphaAnim.setDuration(1500);
        alphaAnim.setStartOffset(1500);
        alphaAnim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation anim) {
            }

            @Override
            public void onAnimationRepeat(Animation anim) {
            }

            @Override
            public void onAnimationEnd(Animation anim) {
                dragonImageView.setVisibility(View.INVISIBLE);
            }
        });

        dragonImageView.startAnimation(alphaAnim);
    }

}
