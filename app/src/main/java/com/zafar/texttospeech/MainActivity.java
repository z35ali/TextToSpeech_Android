package com.zafar.texttospeech;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button speakButton;

    TextToSpeech textToSpeech;
    SeekBar pitchSeek;
    SeekBar speedSeek;
    Spinner languageSpinner;
    float pitchValue = 0.0f;
    float speedValue = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.speakButton);
        editText = findViewById(R.id.editText);
        pitchSeek = findViewById(R.id.pitchSeek);
        speedSeek = findViewById(R.id.speedSeek);
        languageSpinner = findViewById(R.id.languageSpinner);


        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }

        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // Change language based on Spinner Selection
                if (position == 0){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }else if(position == 1){
                    textToSpeech.setLanguage(Locale.CANADA_FRENCH);

                }else if(position == 2){
                    textToSpeech.setLanguage(Locale.CHINESE);

                }else if(position == 3){
                    textToSpeech.setLanguage(Locale.FRENCH);

                }else if(position == 4){
                    textToSpeech.setLanguage(Locale.GERMAN);

                }else if(position == 5){
                    textToSpeech.setLanguage(Locale.ITALIAN);

                }else if(position == 6){
                    textToSpeech.setLanguage(Locale.JAPANESE);

                }else if(position == 7) {
                    textToSpeech.setLanguage(Locale.KOREAN);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });        pitchSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pitchValue = getConvertedValue(seekBar.getProgress());
                Toast.makeText(seekBar.getContext(), "Pitch: "+(pitchValue+1)+" x ", Toast.LENGTH_SHORT).show();

                // +1 since seek bar starts at 0
                textToSpeech.setPitch(pitchValue+1.0f);
            }
        });

        speedSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speedValue = getConvertedValueSpeed(seekBar.getProgress());
                Toast.makeText(seekBar.getContext(), "Speed: "+(speedValue+1)+" x ", Toast.LENGTH_SHORT).show();

                // +1 since seek bar starts at 0
                textToSpeech.setSpeechRate(speedValue+1.0f);
            }
        });

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                // If there is no error set language
                if(status!= TextToSpeech.ERROR){

                    // Default Language
                    textToSpeech.setLanguage(Locale.ENGLISH);

                }


            }
        });



        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get text from editText field
                String text = editText.getText().toString();

                // Convert Text to Speech Queue_Flush flushes resources once used

                    textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);


            }
        });



    }



    public float getConvertedValue(int ratio){

        pitchValue = .5f * ratio;
        return pitchValue;
    }

    public float getConvertedValueSpeed(int ratio){

        pitchValue = .5f * ratio;
        return pitchValue;
    }
    @Override
    protected void onPause() {

        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
