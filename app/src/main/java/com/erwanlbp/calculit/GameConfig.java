package com.erwanlbp.calculit;

import com.erwanlbp.calculit.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameConfig {
    private ArrayList<Integer> numbers;
    public static final String CONFIG_NUMBERS = MainActivity.APPNAME + "CONFIG_NUMBERS";

    private int timeToPrint;
    public static final String CONFIG_TIME_TO_PRINT = MainActivity.APPNAME + "CONFIG_TIME_TO_PRINT";
    public static final int CONFIG_DEFAULT_TIME_TO_PRINT = 1000;

    private int maxNumber;
    public static final String CONFIG_MAX_NUMBER = MainActivity.APPNAME + "CONFIG_MAX_NUMBER";
    public static final int CONFIG_DEFAULT_MAX_NUMBER = 5;

    public GameConfig(int n, int timeToPrint, int maxNumber) {
        this.maxNumber = maxNumber;
        this.numbers = new ArrayList<>();
        for (int i = 0; i < n; i++)
            this.numbers.add(generateRandomNumber(this.maxNumber));
        this.timeToPrint = timeToPrint;
    }

    private int generateRandomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(-max, max + 1);
    }

    public Map<String, Integer> getParamsMap() {
        Map<String, Integer> configMap = new HashMap<>();
        configMap.put(CONFIG_TIME_TO_PRINT, timeToPrint);
        configMap.put(CONFIG_MAX_NUMBER, maxNumber);
        return configMap;
    }


    public ArrayList<Integer> getNumbers() {
        return numbers;
    }
}

