package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class DataFile {

    public static ArrayList<Subject> originOrder(Subjects subjects) {
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

    public static boolean isExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return !(file == null || !file.exists());
    }

    public static Subjects read(Context context, String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        return (Subjects) objectIn.readObject();
    }

    public static void write(Context context, Subjects mSubjects, String filename) {
        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(mSubjects);
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
