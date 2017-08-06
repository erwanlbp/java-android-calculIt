package com.erwanlbp.calculit.config;

import com.erwanlbp.calculit.enums.Difficulty;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameConfig {


    // ----- Default config -----
    public static final int CONFIG_DEFAULT_SIZE = 3;
    public static final int CONFIG_DEFAULT_MAX_NUMBER = 5;
    public static final int CONFIG_DEFAULT_LEVEL = 0;

    // ----- Config -----
    private ArrayList<Integer> numbers;
    private int timeToPrint;
    private Difficulty difficulty;
    private int level;

    // ----- Singleton -----
    private static GameConfig gameConfig;

    public static GameConfig getConfig() {
        if (gameConfig == null)
            gameConfig = new GameConfig();
        return gameConfig;
    }

    // ----- Constructors -----

    private GameConfig() {
        this(Difficulty.EASY);
    }

    private GameConfig(final Difficulty difficulty) {
        this(CONFIG_DEFAULT_SIZE, CONFIG_DEFAULT_MAX_NUMBER, difficulty, CONFIG_DEFAULT_LEVEL);
    }

    private GameConfig(final Difficulty difficulty, final int level) {
        this(CONFIG_DEFAULT_SIZE + (level / 4), CONFIG_DEFAULT_MAX_NUMBER + (level / 5), difficulty, level);
    }

    private GameConfig(final int size, final int maxNumber, Difficulty difficulty, int level) {
        this.level = level;
        this.numbers = new ArrayList<>();
        this.numbers.add(0);
        for (int i = 0; i < size; i++)
            this.numbers.add(generateRandomNumber(maxNumber));
        this.difficulty = difficulty;
        this.timeToPrint = this.difficulty.getTimeToPrint();
    }

    private int generateRandomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(-max, max + 1);
    }

    // ----- Getters -----

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public int getCorrectResult() {
        int sum = 0;
        for (int i : numbers)
            sum += i;
        return sum;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLevel() {
        return level;
    }

    public int getTimeToPrint() {
        return timeToPrint;
    }

    public static boolean hasGameInProgress() {
        return gameConfig != null && gameConfig.getLevel() > CONFIG_DEFAULT_LEVEL;
    }

    // ----- Setters -----

    public void nextLevel() {
        gameConfig = new GameConfig(this.difficulty, ++this.level);
    }

    public static void loadConfig(Difficulty difficulty, int level) {
        gameConfig = new GameConfig(difficulty, level);
    }

    public static void loadConfig(Difficulty difficulty) {
        gameConfig = new GameConfig(difficulty);
    }

    public void reset() {
        gameConfig = new GameConfig(this.getDifficulty());
    }
}

