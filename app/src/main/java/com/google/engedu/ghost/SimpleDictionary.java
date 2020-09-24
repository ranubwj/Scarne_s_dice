package com.google.engedu.ghost;

import android.net.IpPrefix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
           // {
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

        if (prefix.equalsIgnoreCase(""))
        {
            return words.get(new Random().nextInt(words.size()-1));
        }
        else {
            int min = 0, max = words.size() - 1, middle = (min + max) / 2;
            String middleWord = "";
            while (min != middle) {
                middleWord = words.get((max + min)/ 2);
                if (middleWord.compareToIgnoreCase(prefix) > 0) {
                    max = middle - 1;
                    middle = (min + max)/2;
                }
                if (middleWord.compareToIgnoreCase(prefix) < 0) {
                    min = middle + 1;
                    middle = (min + max)/2;
                }
                if (middleWord.startsWith(prefix)) {
                    return middleWord;
                }
            }
            return null;
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
