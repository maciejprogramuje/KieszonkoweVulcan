package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Subjects;

/**
 * Created by m.szymczyk on 2017-10-23.
 */

public class SubjectsInOriginOrder {
    public static ArrayList<Subject> generate(Subjects subjects) {
        ArrayList<Subject> result = new ArrayList<>();
        int index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język polski")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język angielski")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język niemiecki")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Muzyka")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Historia")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Wiedza o społeczeństwie")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Geografia")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Biologia")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Chemia")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Fizyka")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Matematyka")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Informatyka")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Edukacja dla bezpieczeństwa")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Zajęcia techniczne")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Wychowanie do życia w rodzinie")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        index = 0;
        while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Etyka")) {
            index++;
        }
        result.add(subjects.getOneFromSubjects(index));

        return result;
    }
}
