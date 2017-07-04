package com.erwanlbp.calculit.config;

public class ActivityCode {
    private static int valueRQ = 1;
    public final static int RQ_MAIN = valueRQ++;
    public final static int RQ_SHOW_RESULT = valueRQ++;
    public final static int RQ_GAME = valueRQ++;
    public final static int RQ_ANSWER = valueRQ++;
    public final static int RQ_SELECT_DIFFICULTY = valueRQ++;
    public final static int RQ_USER = valueRQ++;
    public final static int RQ_SHOW_HIGH_SCORE = valueRQ++;


    private static int valueRC = 2;
    public static final int RC_NEXT_LEVEL = valueRC++;
    public static final int RC_BACK_HOME = valueRC++;
}
