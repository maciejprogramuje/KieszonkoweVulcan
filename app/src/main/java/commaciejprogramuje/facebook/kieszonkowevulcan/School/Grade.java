package commaciejprogramuje.facebook.kieszonkowevulcan.School;

import java.io.Serializable;

/**
 * Created by m.szymczyk on 2017-10-11.
 */

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

    public void setmGrade(String mGrade) {
        this.mGrade = mGrade;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }
}