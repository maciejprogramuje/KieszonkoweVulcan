package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class DataFile {
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
