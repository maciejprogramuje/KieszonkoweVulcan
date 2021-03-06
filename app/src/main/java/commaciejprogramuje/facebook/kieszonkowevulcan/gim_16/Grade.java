package commaciejprogramuje.facebook.kieszonkowevulcan.gim_16;

import java.io.Serializable;

public class Grade implements Serializable {
    private String mGrade;
    private String mDate;
    private String mText;
    private String mCode;
    private String mWeight;

    public Grade(String mGrade, String mDate, String mText, String mCode, String mWeight) {
        this.mGrade = mGrade;
        this.mDate = mDate;
        this.mText = mText;
        this.mCode = mCode;
        this.mWeight = mWeight;
    }

    public String getmGrade() {
        return mGrade;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmText() {
        return mText;
    }

    public String getmCode() {
        return mCode;
    }

    public String getmWeight() {
        return mWeight;
    }
}