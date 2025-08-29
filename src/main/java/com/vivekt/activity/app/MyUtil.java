package com.vivekt.activity.app;

import javafx.concurrent.Worker;
import org.apache.commons.lang3.text.WordUtils;

public class MyUtil {

    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        return WordUtils.capitalize(word);
    }


}
