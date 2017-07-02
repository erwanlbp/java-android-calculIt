package com.erwanlbp.calculit.config;

import com.erwanlbp.calculit.enums.Difficulty;
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
    public static final int CONFIG_DEFAULT_SIZE = 3;

    private int timeToPrint;
    public static final String CONFIG_TIME_TO_PRINT = MainActivity.APPNAME + "CONFIG_TIME_TO_PRINT";
    public static final int CONFIG_DEFAULT_TIME_TO_PRINT = 1000;

    private int maxNumber;
    public static final String CONFIG_MAX_NUMBER = MainActivity.APPNAME + "CONFIG_MAX_NUMBER";
    public static final int CONFIG_DEFAULT_MAX_NUMBER = 5;

    private Difficulty difficulty;
    public static final String CONFIG_DIFFICULTY = MainActivity.APPNAME + "CONFIG_DIFFICULTY";
    public static final int CONFIG_DEFAULT_DIFFICULTY = Difficulty.EASY.getTimeToPrint();

    private int level;
    public static final int CONFIG_DEFAULT_LEVEL = 1;
    public static final String CONFIG_LEVEL = MainActivity.APPNAME + "CONFIG_LEVEL";

    public GameConfig() {
        this(Difficulty.EASY);
    }

    public GameConfig(final Difficulty difficulty) {
        this(CONFIG_DEFAULT_SIZE, CONFIG_DEFAULT_MAX_NUMBER, difficulty, CONFIG_DEFAULT_LEVEL);
    }

    public GameConfig(final Difficulty difficulty, final int level) {
        this(CONFIG_DEFAULT_SIZE + (level / 4), CONFIG_DEFAULT_MAX_NUMBER + (level / 5), difficulty, level);
    }

    private GameConfig(final int size, final int maxNumber, Difficulty difficulty, int level) {
        this.maxNumber = maxNumber;
        this.level = level;
        this.size = size;
        this.numbers = new ArrayList<>();
        for (int i = 0; i < this.size; i++)
            this.numbers.add(generateRandomNumber(this.maxNumber));
        this.difficulty = difficulty;
        this.timeToPrint = this.difficulty.getTimeToPrint();
    }

    private int generateRandomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(-max, max + 1);
    }

    public Map<String, Integer> getParamsMap() {
        Map<String, Integer> configMap = new HashMap<>();
        configMap.put(CONFIG_TIME_TO_PRINT, timeToPrint);
        configMap.put(CONFIG_MAX_NUMBER, maxNumber);
        configMap.put(CONFIG_LEVEL, level);
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

    public GameConfig nextLevel() {
        return new GameConfig(this.difficulty, ++this.level);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLevel() {
        return level;
    }
}

