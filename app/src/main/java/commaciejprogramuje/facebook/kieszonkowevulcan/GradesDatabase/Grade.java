package commaciejprogramuje.facebook.kieszonkowevulcan.GradesDatabase;

import java.io.Serializable;

/**
 * Created by m.szymczyk on 2017-10-11.
 */

public class Grade implements Serializable {
    private String mGrade;
    private String mDate;
    private String mText;
    private String mCode;

    public Grade(String mGrade, String mDate, String mText, String mCode) {
        this.mGrade = mGrade;
        this.mDate = mDate;
        this.mText = mText;
        this.mCode = mCode;
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
}