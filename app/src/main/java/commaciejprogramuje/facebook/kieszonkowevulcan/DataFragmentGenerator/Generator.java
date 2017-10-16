package commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator;

import android.view.View;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

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
                stringBuilder.append(" (").append(subjects.getAverage(i)).append(")\n");
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

                    if(newGradeDateCal.after(oldGradeDateCal)) {
                        oldGradeDateCal = newGradeDateCal;
                        newestGradeStringBuilder = new StringBuilder();
                        newestGradeStringBuilder
                                .append("   ").append(subjects.getGrades(i).get(j).getmGrade()).append(" (").append(subjects.getGrades(i).get(j).getmDate()).append(")\n")
                                .append("   ").append(subjects.getGrades(i).get(j).getmCode()).append(", ").append(subjects.getGrades(i).get(j).getmText()).append("\n");
                    } else if(newGradeDateCal.equals(oldGradeDateCal)) {
                        newestGradeStringBuilder
                                .append("   ").append(subjects.getGrades(i).get(j).getmGrade()).append(" (").append(subjects.getGrades(i).get(j).getmDate()).append(")\n")
                                .append("   ").append(subjects.getGrades(i).get(j).getmCode()).append(", ").append(subjects.getGrades(i).get(j).getmText()).append("\n");
                    }
                }
                stringBuilder.append(newestGradeStringBuilder).append("\n");
            } else {
                stringBuilder.append("\n   --- brak ocen ---\n\n");
            }
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

    public static String dataForTeachersFragment(MainActivity mainActivity) {
        //http://zastepstwa.g16-lublin.eu/
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();

            System.out.println(temp);

            stringBuilder.append(temp.substring(temp.indexOf("<nobr>") + 14, temp.indexOf("</nobr>"))).append("\n");

            Matcher rowMatcher = Pattern.compile("<td nowrap class=\"st1\"(.*?)</td>(.*?)dyżury").matcher(temp);
            while (rowMatcher.find()) {
                System.out.println("znaleziono");

                String masterTeacherString = rowMatcher.group();
                stringBuilder.append(masterTeacherString).append("\n").append("===============================\n");



            }


            /*<tr>
                <td nowrap class="st7" align="LEFT"> 3 </td>
                <td nowrap class="st8" align="LEFT"> 2 bg - 202 </td>
                <td nowrap class="st9" align="LEFT"> Magdalena Ostrokulska </td>
            </tr>
            <tr>
                <td nowrap class="st10" align="LEFT" bgcolor="#F7F3D9"> 4 </td>
                <td nowrap class="st11" align="LEFT" bgcolor="#F7F3D9"> 2 bg - 202 </td>
                <td nowrap class="st12" align="LEFT" bgcolor="#F7F3D9"> Magdalena Ostrokulska </td>
            </tr> */




            /*Matcher gradeMatcher = Pattern.compile("[1-6]{1}</span>").matcher(temp);
            Matcher dateMatcher = Pattern.compile("Data: \\d{2}\\.\\d{2}\\.\\d{4}").matcher(temp);
            Matcher textMatcher = Pattern.compile("Opis:(.*?)<br/>").matcher(temp);
            Matcher codeMatcher = Pattern.compile("Kod:(.*?)<br/>").matcher(temp);

            while (gradeMatcher.find()) {
                tempGrade = gradeMatcher.group().substring(0, 1);

                if(dateMatcher.find()) {
                    tempDate = dateMatcher.group().substring(6);
                }

                if(textMatcher.find()) {
                    tempText = textMatcher.group().substring(6);
                    tempText = tempText.replace("<br/>", "");
                    tempText = tempText.replaceAll("&quot;", "'");
                }

                if(codeMatcher.find()) {
                    tempCode = codeMatcher.group().substring(5);
                    tempCode = tempCode.replace("<br/>", "");
                }
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();
    }
}
