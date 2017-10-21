package commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils;

import java.io.Serializable;

/**
 * Created by 5742ZGPC on 2017-10-21.
 */

public class Teacher implements Serializable {
    private String nameTeacher;
    private String substituteTeacher;
    private String onDutyTeacher;

    public Teacher(String nameTeacher, String substituteTeacher, String onDutyTeacher) {
        this.nameTeacher = nameTeacher;
        this.substituteTeacher = substituteTeacher;
        this.onDutyTeacher = onDutyTeacher;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public String getSubstituteTeacher() {
        return substituteTeacher;
    }

    public void setSubstituteTeacher(String substituteTeacher) {
        this.substituteTeacher = substituteTeacher;
    }

    public String getOnDutyTeacher() {
        return onDutyTeacher;
    }

    public void setOnDutyTeacher(String onDutyTeacher) {
        this.onDutyTeacher = onDutyTeacher;
    }
}
