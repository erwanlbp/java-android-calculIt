package com.erwanlbp.calculit;

import com.erwanlbp.calculit.activity.Enum.Difficulty;
import com.erwanlbp.calculit.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameConfig {
    public static final String CONFIG_CORRECT_RESULT = MainActivity.APPNAME + "CONFIG_CORRECT_RESULT";

    private ArrayList<Integer> numbers;
    public static final String CONFIG_NUMBERS = MainActivity.APPNAME + "CONFIG_NUMBERS";

    private int size;
    public static final int CONFIG_DEFAULT_SIZE = 5;

    private int timeToPrint;
    public static final String CONFIG_TIME_TO_PRINT = MainActivity.APPNAME + "CONFIG_TIME_TO_PRINT";
    public static final int CONFIG_DEFAULT_TIME_TO_PRINT = 1000;

    private int maxNumber;
    public static final String CONFIG_MAX_NUMBER = MainActivity.APPNAME + "CONFIG_MAX_NUMBER";
    public static final int CONFIG_DEFAULT_MAX_NUMBER = 5;

    private Difficulty difficulty;
    public static final String CONFIG_DIFFICULTY = MainActivity.APPNAME + "CONFIG_DIFFICULTY";
    public static final int CONFIG_DEFAULT_DIFFICULTY = 1000;

    public GameConfig() {
        this(Difficulty.EASY);
    }

    public GameConfig(final Difficulty difficulty) {
        this(CONFIG_DEFAULT_SIZE, difficulty.getValue(), CONFIG_DEFAULT_MAX_NUMBER, difficulty);
    }

    private GameConfig(final int n, final int timeToPrint, final int maxNumber, Difficulty difficulty) {
        this.maxNumber = maxNumber;
        this.numbers = new ArrayList<>();
        for (int i = 0; i < n; i++)
            this.numbers.add(generateRandomNumber(this.maxNumber));
        this.timeToPrint = timeToPrint;
        this.difficulty = difficulty;
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

    public int getCorrectResult() {
        int sum = 0;
        for (int i : numbers)
            sum += i;
        return sum;
    }
}

