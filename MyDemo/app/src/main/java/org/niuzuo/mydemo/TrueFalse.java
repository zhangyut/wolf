package org.niuzuo.mydemo;


/**
 * Created by zdns on 16/4/25.
 */
public class TrueFalse {
    private boolean mTrueQuestion;
    private int mQuestion;

    public TrueFalse(int question, boolean trueQuestion) {
        this.mTrueQuestion = trueQuestion;
        this.mQuestion = question;
    }

    public boolean ismTrueQuestion() {
        return this.mTrueQuestion;
    }

    public void setmTrueQuestion(boolean mTrueQuestion) {
        this.mTrueQuestion = mTrueQuestion;
    }

    public int getmQuestion() {
        return this.mQuestion;
    }

    public void setmQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }
}
