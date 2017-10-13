package commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesDatabase.Subjects;

/**
 * Created by m.szymczyk on 2017-10-13.
 */

public class Generator {
    private static int currentMonthInt;
    private static int previousMonthInt;

    public static String dataForNewsFragment(Subjects subjects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===== NEWS =====\n\n");

        for (int i = 0; i < subjects.size(); i++) {
            stringBuilder.append(subjects.getName(i).toUpperCase());
            if (!subjects.getAverage(i).equals("-")) {
                stringBuilder.append(" (").append(subjects.getAverage(i)).append(")");
            }
            stringBuilder.append("\n");
            if (subjects.getGrades(i).size() > 0) {
                for (int j = 0; j < subjects.getGrades(i).size(); j++) {
                    stringBuilder.append("   ")
                            .append(subjects.getGrades(i).get(j).getmGrade())
                            .append(" (")
                            .append(subjects.getGrades(i).get(j).getmDate())
                            .append(", ")
                            .append(subjects.getGrades(i).get(j).getmCode())
                            .append(", ")
                            .append(subjects.getGrades(i).get(j).getmText())
                            .append(")\n");
                }
            } else {
                stringBuilder.append("   --- brak ocen ---\n");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String dataForGradesFragment(Subjects subjects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===== OCENY =====\n\n");
        for (int i = 0; i < subjects.size(); i++) {
            stringBuilder.append(subjects.getName(i).toUpperCase());
            if (!subjects.getAverage(i).equals("-")) {
                stringBuilder.append(" (").append(subjects.getAverage(i)).append(")");
            }
            stringBuilder.append("\n");
            if (subjects.getGrades(i).size() > 0) {
                for (int j = 0; j < subjects.getGrades(i).size(); j++) {
                    stringBuilder.append("   ")
                            .append(subjects.getGrades(i).get(j).getmGrade())
                            .append(" (")
                            .append(subjects.getGrades(i).get(j).getmDate())
                            .append(", ")
                            .append(subjects.getGrades(i).get(j).getmCode())
                            .append(", ")
                            .append(subjects.getGrades(i).get(j).getmText())
                            .append(")\n");
                }
            } else {
                stringBuilder.append("   --- brak ocen ---\n");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String dataForMoneyFragment(Subjects subjects) {
        int money = 0;
        int extraMoneyCurrentMonth = 30;
        int extraMoneyPreviousMonth = 30;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===== KIESZONKOWE =====\n\n");

        for (int i = 0; i < subjects.size(); i++) {
            stringBuilder.append(subjects.getName(i).toUpperCase()).append(" -> ");

            if (!subjects.getAverage(i).equals("-")) {
                stringBuilder.append(subjects.getAverage(i)).append(" -> ");

                double subjectAvg = Double.valueOf(subjects.getAverage(i).replace(",", "."));
                if (subjectAvg > 4.5) {
                    money += 15;
                    stringBuilder.append(" +15 zł\n");
                } else if (subjectAvg > 3.75) {
                    money += 10;
                    stringBuilder.append(" +10 zł\n");
                } else if (subjectAvg > 3.5) {
                    money += 0;
                    stringBuilder.append(" 0 zł\n");
                } else if (subjectAvg >= 3) {
                    money -= 5;
                    stringBuilder.append(" -5 zł\n");
                } else if (subjectAvg < 3) {
                    money -= 10;
                    stringBuilder.append(" -10 zł\n");
                }
            } else {
                stringBuilder.append("b.d. -> 0 zł\n");
            }

            if (subjects.getGrades(i).size() > 0) {
                for (int j = 0; j < subjects.getGrades(i).size(); j++) {
                    Calendar gradesCalendar = Calendar.getInstance();
                    Calendar currentCallendar = Calendar.getInstance();
                    currentMonthInt = currentCallendar.get(Calendar.MONTH);
                    try {
                        String dateString = subjects.getGrades(i).get(j).getmDate();
                        Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                        gradesCalendar.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (gradesCalendar.get(Calendar.MONTH) == currentCallendar.get(Calendar.MONTH)) {
                        if (subjects.getGrades(i).get(j).getmGrade().equals("1") || subjects.getGrades(i).get(j).getmGrade().equals("2")) {
                            extraMoneyCurrentMonth = 0;
                        } else if (subjects.getGrades(i).get(j).getmGrade().equals("3") && extraMoneyCurrentMonth > 0) {
                            extraMoneyCurrentMonth -= 10;
                        }
                    }

                    currentCallendar.add(Calendar.MONTH, -1);
                    previousMonthInt = currentCallendar.get(Calendar.MONTH);

                    if (gradesCalendar.get(Calendar.MONTH) == currentCallendar.get(Calendar.MONTH)) {
                        if (subjects.getGrades(i).get(j).getmGrade().equals("1") || subjects.getGrades(i).get(j).getmGrade().equals("2")) {
                            extraMoneyPreviousMonth = 0;
                        } else if (subjects.getGrades(i).get(j).getmGrade().equals("3") && extraMoneyPreviousMonth > 0) {
                            extraMoneyPreviousMonth -= 10;
                        }
                    }
                }
            }
        }
        stringBuilder.append("==============================\n")
                .append("=== SUMA KWOT: ").append(money).append(" zł\n")
                .append("==============================\n")
                .append("=== PREMIA ZA ").append(getMonthName(currentMonthInt)).append(": ").append(extraMoneyCurrentMonth).append(" zł\n")
                .append("=== KIESZONKOWE Z PREMIĄ: ").append(money + extraMoneyCurrentMonth).append(" zł\n")
                .append("==============================\n")
                .append("=== PREMIA ZA ").append(getMonthName(previousMonthInt)).append(": ").append(extraMoneyPreviousMonth).append(" zł\n")
                .append("=== KIESZONKOWE Z PREMIĄ: ").append(money + extraMoneyPreviousMonth).append(" zł\n")
                .append("==============================\n");
        return stringBuilder.toString();
    }

    private static String getMonthName(int num) {
        switch (num) {
            case 0:
                return "STYCZEŃ";
            case 1:
                return "LUTY";
            case 2:
                return "MARZEC";
            case 3:
                return "KWIECIEŃ";
            case 4:
                return "MAJ";
            case 5:
                return "CZERWIEC";
            case 6:
                return "LIPIEC";
            case 7:
                return "SIERPIEŃ";
            case 8:
                return "WRZESIEŃ";
            case 9:
                return "PAŹDZIERNIK";
            case 10:
                return "LISTOPAD";
            case 11:
                return "GRUDZIEŃ";
        }
        return null;
    }
}
