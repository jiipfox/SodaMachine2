package com.example.kettu.sodamachine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends Activity {

    BottleDispenser dispenser = BottleDispenser.getInstance();

    TextView textMain;
    TextView textPrice;
    Spinner bottleSpinner;
    SeekBar moneySliders;
    EditText moneyDispley;
    CheckBox checkBox;

    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textMain = findViewById(R.id.textMain);
        textPrice = findViewById(R.id.textPrice);
        bottleSpinner = findViewById(R.id.spinner);
        moneySliders = findViewById(R.id.slideri);
        moneyDispley = findViewById(R.id.moneys);
        checkBox = findViewById(R.id.checkPlease);
        this.fillBottles();
        context = MainActivity.this;

        moneySliders.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            double inEuros = 0.0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                double d = new Double(progressChangedValue);
                inEuros = progressChangedValue/1000; // slider from 0-5000 -> 0.1 -> 5.0, hopefully
                moneyDispley.setText(Double.toString(inEuros));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                moneyDispley.setText(Double.toString(inEuros));
                String s = "Moneys sliders are in :" + inEuros;
                System.out.println("MONEEEEEEY slidet = " + s);
            }


        });
    }

    public void buyBottle(View v){
        String someAdds = "";
        int chosen_one = bottleSpinner.getSelectedItemPosition();
        System.out.println("chosen id = " + chosen_one);

        ArrayList bottleList = dispenser.getBottles();

        System.out.println("Bottle count = " + bottleList.size());

        try {
            // if we are picking the last one from the list or last one, index -1?
            if (chosen_one == (bottleList.size()-1) && bottleList.size() > 1){
                bottleSpinner.setSelection(chosen_one - 1);
            }
            else {
                bottleSpinner.setSelection(chosen_one + 1);
            }

            // Small bug is that check is stored eventhough the buy doesn't succeed, TODO!
            if (checkBox.isChecked()){
                String temp = bottleList.get(chosen_one).toString();
                System.out.println(v);

                System.out.println("Save to file: " + temp);
                System.out.println("Directory: " + context.getFilesDir());
                try{
                    OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput("kuitti.txt",
                            Context.MODE_PRIVATE));

                    out.write(temp);
                    out.close();
                } catch (IOException e){
                    System.out.println(e.toString());
                }

                someAdds = "\nReceipt printed!";
            }
            String text = dispenser.buyBottle(chosen_one);
            text = text.concat(someAdds);

            textMain.setText(text);

        } catch (IndexOutOfBoundsException e){
            System.out.println("Ups, something went wrong during buy operation!\n");
        }
        System.out.println("Bottle bought\n");
    }

    public void insertCoin(View v){
        Double moneys = Double.parseDouble(moneyDispley.getText().toString());
        System.out.println("MONEYS == = == === " + moneys);
        textMain.setText(dispenser.addMoney(moneys));
        moneySliders.setProgress(1);
    }

    public void returnMoney(View v){
        textMain.setText(dispenser.returnMoney());
    }

    public void fillBottles(){
        dispenser.addBottle("Pepsi-Cola", "CCC", 0.5, 2.0);
        dispenser.addBottle("Coca-Cola", "CCC", 0.3, 1.5);
        dispenser.addBottle("Pepsi-Cola", "CCC", 0.3, 2.0);
        dispenser.addBottle("JaksuFanta", "CCC", 0.3, 1.5);

        ArrayAdapter adapter = new ArrayAdapter(this,
                                                android.R.layout.simple_spinner_item,
                                                dispenser.getBottles());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bottleSpinner.setAdapter(adapter);
    }

}
