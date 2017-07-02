package com.erwanlbp.calculit.enums;

public enum Difficulty {

    EASY(2000),
    MEDIUM(1300),
    HARD(800),
    GOD(400);

    private final int timeToPrint;

    Difficulty(int timeToPrint) {
        this.timeToPrint = timeToPrint;
    }

    public int getTimeToPrint() {
        return this.timeToPrint;
    }

    public static Difficulty parse(final String difficulty) {
        return Difficulty.valueOf(difficulty);
    }

    public static Difficulty parse(final int difficulty) {
        switch (difficulty) {
            case 2000:
                return EASY;
            case 1300:
                return MEDIUM;
            case 800:
                return HARD;
            case 400:
                return GOD;
            default:
                return EASY;
        }
    }
}


