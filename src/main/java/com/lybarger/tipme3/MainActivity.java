package com.lybarger.tipme3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private static SeekBar bar;
    private static TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar();
    }

    public void seekBar(){
        bar = findViewById(R.id.seekBar);
        view = findViewById(R.id.textView);
        view.setText(bar.getProgress() + "%");


        bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        progressValue = i;
                        view.setText(progressValue + "%");

                       /* TextView view2 = (TextView) findViewById(R.id.textView5);
                        BigDecimal tip = displayTip();
                        view2.setText(progressValue + "% tip is: " + tip);*/
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        view.setText(progressValue + "%");
                    }
                }
        );
    }

    public void displayTip(View v){
        EditText password = findViewById(R.id.editText);
        EditText people = findViewById(R.id.people);
        String cost = String.valueOf(password.getText());
        String numPeople = String.valueOf(people.getText());
        bar = findViewById(R.id.seekBar);

        BigDecimal mealCost;
        if(cost.equals("")){
            mealCost = BigDecimal.ZERO;
        }
        else{
            mealCost = new BigDecimal(cost);
        }
        BigDecimal splitPeople;
        if(numPeople.equals("")){
            splitPeople = BigDecimal.ONE;
        }
        else{
            splitPeople = new BigDecimal(numPeople);
        }

        BigDecimal value = new BigDecimal(bar.getProgress());
        BigDecimal oneHunned = new BigDecimal(100);
        BigDecimal percent = value.divide(oneHunned);
        BigDecimal tip = percent.multiply(mealCost);
        tip.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal total = tip.add(mealCost);

        BigDecimal splitTotal = tip.divide(splitPeople);
        BigDecimal billSplit = total.divide(splitPeople);

        TextView tipText = view.getRootView().findViewById(R.id.tip_amount);
        tipText.setText("Tip is: $" + tip);

        TextView splitTip = view.getRootView().findViewById(R.id.tip_per_person);
        splitTip.setText("Tip per person: $" + splitTotal);

        TextView totalSplit = view.getRootView().findViewById(R.id.total_split);
        totalSplit.setText("Total per person: $" + billSplit);

        TextView totalText = view.getRootView().findViewById(R.id.total_amount);
        totalText.setText("Total bill(tip included): $" + total);


    }
}
