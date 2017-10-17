package commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;

/**
 * Created by m.szymczyk on 2017-10-13.
 */

public class Generator {
    private static int currentMonthInt;
    private static int previousMonthInt;

    public static ArrayList<String> dataForNewsFragment(Subjects subjects) {
        ArrayList<String> dataArray = new ArrayList<>();

        //generate one string with all subjects data
        for (int i = 0; i < subjects.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(subjects.getName(i).toUpperCase());
            if (!subjects.getAverage(i).equals("-")) {
                stringBuilder.append(" (").append(subjects.getAverage(i)).append(")");
            }

            if (subjects.getGrades(i).size() > 0) {
                StringBuilder newestGradeStringBuilder = new StringBuilder();
                Calendar oldGradeDateCal = Calendar.getInstance();
                try {
                    String dateString = subjects.getGrades(i).get(0).getmDate();
                    Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                    oldGradeDateCal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < subjects.getGrades(i).size(); j++) {
                    Calendar newGradeDateCal = Calendar.getInstance();
                    try {
                        String dateString = subjects.getGrades(i).get(j).getmDate();
                        Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                        newGradeDateCal.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (newGradeDateCal.after(oldGradeDateCal)) {
                        oldGradeDateCal = newGradeDateCal;
                        newestGradeStringBuilder = new StringBuilder();
                        newestGradeStringBuilder
                                .append("\n")
                                .append(subjects.getGrades(i).get(j).getmGrade()).append(" (").append(subjects.getGrades(i).get(j).getmDate()).append(")\n")
                                .append(subjects.getGrades(i).get(j).getmCode()).append(", ").append(subjects.getGrades(i).get(j).getmText());
                    } else if (newGradeDateCal.equals(oldGradeDateCal)) {
                        newestGradeStringBuilder
                                .append("\n")
                                .append(subjects.getGrades(i).get(j).getmGrade()).append(" (").append(subjects.getGrades(i).get(j).getmDate()).append(")\n")
                                .append(subjects.getGrades(i).get(j).getmCode()).append(", ").append(subjects.getGrades(i).get(j).getmText());
                    }
                }

                stringBuilder.append(newestGradeStringBuilder);
            } else {
                stringBuilder.append("\n--- brak ocen ---");
            }

            dataArray.add(stringBuilder.toString());
        }

        return dataArray;
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
                    Calendar gradeDateCal = Calendar.getInstance();
                    Calendar curDateCal = Calendar.getInstance();
                    currentMonthInt = curDateCal.get(Calendar.MONTH);
                    try {
                        String dateString = subjects.getGrades(i).get(j).getmDate();
                        Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                        gradeDateCal.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (gradeDateCal.get(Calendar.MONTH) == curDateCal.get(Calendar.MONTH)) {
                        if (subjects.getGrades(i).get(j).getmGrade().equals("1") || subjects.getGrades(i).get(j).getmGrade().equals("2")) {
                            extraMoneyCurrentMonth = 0;
                        } else if (subjects.getGrades(i).get(j).getmGrade().equals("3") && extraMoneyCurrentMonth > 0) {
                            extraMoneyCurrentMonth -= 10;
                        }
                    }

                    curDateCal.add(Calendar.MONTH, -1);
                    previousMonthInt = curDateCal.get(Calendar.MONTH);

                    if (gradeDateCal.get(Calendar.MONTH) == curDateCal.get(Calendar.MONTH)) {
                        if (subjects.getGrades(i).get(j).getmGrade().equals("1") || subjects.getGrades(i).get(j).getmGrade().equals("2")) {
                            extraMoneyPreviousMonth = 0;
                        } else if (subjects.getGrades(i).get(j).getmGrade().equals("3") && extraMoneyPreviousMonth > 0) {
                            extraMoneyPreviousMonth -= 10;
                        }
                    }
                }
            }
        }
        stringBuilder.append("\n===============================\n")
                .append("=== SUMA KWOT: ").append(money).append(" zł\n")
                .append("===============================\n")
                .append("=== PREMIA ZA ").append(getMonthName(currentMonthInt)).append(": ").append(extraMoneyCurrentMonth).append(" zł\n")
                .append("=== KIESZONKOWE Z PREMIĄ: ").append(money + extraMoneyCurrentMonth).append(" zł\n")
                .append("===============================\n")
                .append("=== PREMIA ZA ").append(getMonthName(previousMonthInt)).append(": ").append(extraMoneyPreviousMonth).append(" zł\n")
                .append("=== KIESZONKOWE Z PREMIĄ: ").append(money + extraMoneyPreviousMonth).append(" zł\n")
                .append("===============================\n");
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

    public static String dataForTeachersFragment() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();

            System.out.println(temp);

            Matcher rowMatcher = Pattern.compile("<tr>(.|\\n)*?</tr>").matcher(temp);
            while (rowMatcher.find()) {
                String row = rowMatcher.group();
                String element = "";

                if (row.contains("Zastępstwa")) {
                    element = row.substring(row.indexOf("<nobr>") + 14, row.indexOf("</nobr>"));
                    stringBuilder.append(element).append("\n");
                } else {
                    Matcher matcher = Pattern.compile("\">(.*?)</td>").matcher(row);
                    int index = 0;
                    while (matcher.find()) {
                        String tempElement = matcher.group().replace("\"> ", "").replace(" </td>", "").replace("&" + "nbsp;", "");

                        Log.w("UWAGA", "szczegóły: [" + tempElement + "]");

                        if (!tempElement.equals("lekcja")
                                && !tempElement.equals("opis")
                                && !tempElement.equals("zastępca")
                                && !tempElement.equals("po lekcji")
                                && !tempElement.equals("miejsce")) {
                            if (index == 0 && !tempElement.equals("")) {
                                    element = tempElement;

                            } else if (index == 1) {
                                if(!tempElement.equals("")) {
                                    element = element + " -> " + tempElement;
                                }
                            } else if (index == 2) {
                                if(tempElement.equals("")) {
                                    element += "\n";
                                } else {
                                    element = element + " -> " + tempElement + "\n";
                                }
                            }
                            index++;
                        }
                    }
                    if (index == 1) {
                        stringBuilder.append("\n");
                    }

                    if (!element.contains("->  ->")) {
                        stringBuilder.append(element);
                    }

                    if (index == 1) {
                        stringBuilder.append("\n");
                    }


                }
                //Log.w("UWAGA", "szczegóły: " + element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
