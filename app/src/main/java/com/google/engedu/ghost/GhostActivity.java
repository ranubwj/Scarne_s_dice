package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    TextView text,label;
    String wordFrag="";
    String currentWord="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        text=(TextView)findViewById(R.id.ghostText);
        label = (TextView)findViewById(R.id.gameStatus) ;

        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//... for options
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode>=29 && keyCode<=54){
            char c=(char)event.getUnicodeChar();
            wordFrag=(String)text.getText();
            wordFrag=currentWord+ Character.toString(c);
            text.setText(wordFrag);
            computerTurn();
            return true;

        }
        else {


            return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */

    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
       // TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again


        if(wordFrag.length()>=4 && dictionary.isWord(wordFrag))
        {

        label.setText("Computer wins....");
    }
        else
        {
            String wordFromDictionary = dictionary.getAnyWordStartingWith(wordFrag);
            if (wordFromDictionary!=null){

                int a=wordFrag.length();
                char ch = wordFromDictionary.charAt(a);
                wordFrag=wordFrag.concat(String.valueOf(ch));
                        //string.valueOf converts character into string
                text.setText(wordFrag);
                currentWord=wordFrag;
                userTurn=true;

            }
            else {
                label.setText("invalid input ,,no word can be formed");
                text.setText("match draw..");
            }

        }
    }


    public void chall(View view) {
        if (dictionary.isWord(wordFrag))
        {
            label.setText("you win..");
            onStart();
        }
        else{
            String wordFromDictionary = dictionary.getAnyWordStartingWith(wordFrag);
            if(wordFromDictionary!=null)
            {
                text.setText("the word was "+wordFromDictionary);
                label.setText("you lose");
                onStart();
                userTurn=true;
            }
            else
            {
                label.setText("no word can be formed using this prefix");
                onStart();
            }
        }
    }

    public void restart(View view) {
        userTurn = random.nextBoolean();
        text.setText("");
        if(userTurn){
            label.setText(USER_TURN);
        }
        else{
            label.setText(COMPUTER_TURN);
        }
        wordFrag="";
        currentWord="";
        onStart(null);
    }


}
