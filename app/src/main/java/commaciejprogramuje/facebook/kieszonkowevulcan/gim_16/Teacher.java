package commaciejprogramuje.facebook.kieszonkowevulcan.gim_16;

import java.io.Serializable;

/**
 * Created by 5742ZGPC on 2017-10-21.
 */

public class Teacher implements Serializable {
    private String nameTeacher;
    private String substituteTeacher;

    public Teacher(String nameTeacher, String substituteTeacher) {
        this.nameTeacher = nameTeacher;
        this.substituteTeacher = substituteTeacher;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public String getSubstituteTeacher() {
        return substituteTeacher;
    }
}
