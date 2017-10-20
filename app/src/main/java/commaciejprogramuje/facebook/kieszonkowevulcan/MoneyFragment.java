package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;


public class MoneyFragment extends Fragment {
    public static final String GRADES_KEY = "grades";

    @InjectView(R.id.money_extra_current)
    TextView moneyExtraCurrent;
    @InjectView(R.id.money_sum_current)
    TextView moneySumCurrent;
    @InjectView(R.id.money_extra_previous)
    TextView moneyExtraPrevious;
    @InjectView(R.id.money_sum_previous)
    TextView moneySumPrevious;
    @InjectView(R.id.money_jezyk_polski_avg)
    TextView moneyJezykPolskiAvg;
    @InjectView(R.id.money_jezyk_polski_money)
    TextView moneyJezykPolskiMoney;
    @InjectView(R.id.money_jezyk_angielski_avg)
    TextView moneyJezykAngielskiAvg;
    @InjectView(R.id.money_jezyk_angielski_money)
    TextView moneyJezykAngielskiMoney;
    @InjectView(R.id.money_jezyk_niemiecki_avg)
    TextView moneyJezykNiemieckiAvg;
    @InjectView(R.id.money_jezyk_niemiecki_money)
    TextView moneyJezykNiemieckiMoney;
    @InjectView(R.id.money_muzyka_avg)
    TextView moneyMuzykaAvg;
    @InjectView(R.id.money_muzyka_money)
    TextView moneyMuzykaMoney;
    @InjectView(R.id.money_historia_avg)
    TextView moneyHistoriaAvg;
    @InjectView(R.id.money_historia_money)
    TextView moneyHistoriaMoney;
    @InjectView(R.id.money_wiedza_o_spoleczenstwie_avg)
    TextView moneyWiedzaOSpoleczenstwieAvg;
    @InjectView(R.id.money_wiedza_o_spoleczenstwie_money)
    TextView moneyWiedzaOSpoleczenstwieMoney;
    @InjectView(R.id.money_geografia_avg)
    TextView moneyGeografiaAvg;
    @InjectView(R.id.money_geografia_money)
    TextView moneyGeografiaMoney;
    @InjectView(R.id.money_biologia_avg)
    TextView moneyBiologiaAvg;
    @InjectView(R.id.money_biologia_money)
    TextView moneyBiologiaMoney;
    @InjectView(R.id.money_chemia_avg)
    TextView moneyChemiaAvg;
    @InjectView(R.id.money_chemia_money)
    TextView moneyChemiaMoney;
    @InjectView(R.id.money_fizyka_avg)
    TextView moneyFizykaAvg;
    @InjectView(R.id.money_fizyka_money)
    TextView moneyFizykaMoney;
    @InjectView(R.id.money_matematyka_avg)
    TextView moneyMatematykaAvg;
    @InjectView(R.id.money_matematyka_money)
    TextView moneyMatematykaMoney;
    @InjectView(R.id.money_informatyka_avg)
    TextView moneyInformatykaAvg;
    @InjectView(R.id.money_informatyka_money)
    TextView moneyInformatykaMoney;
    @InjectView(R.id.money_edukacja_dla_bezpieczenstwa_avg)
    TextView moneyEdukacjaDlaBezpieczenstwaAvg;
    @InjectView(R.id.money_edukacja_dla_bezpieczenstwa_money)
    TextView moneyEdukacjaDlaBezpieczenstwaMoney;
    @InjectView(R.id.money_zajecia_techniczne_avg)
    TextView moneyZajeciaTechniczneAvg;
    @InjectView(R.id.money_zajecia_techniczne_money)
    TextView moneyZajeciaTechniczneMoney;
    @InjectView(R.id.money_wychowanie_do_zycia_avg)
    TextView moneyWychowanieDoZyciaAvg;
    @InjectView(R.id.money_wychowanie_do_zycia_money)
    TextView moneyWychowanieDoZyciaMoney;
    @InjectView(R.id.money_etyka_avg)
    TextView moneyEtykaAvg;
    @InjectView(R.id.money_etyka_money)
    TextView moneyEtykaMoney;

    ArrayList<TextView> avgsTextView = new ArrayList<>();
    ArrayList<TextView> moneyTextView = new ArrayList<>();

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        ButterKnife.inject(this, view);

        generateAvgsTextViewArray();
        generateMoneyTextViewArray();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Subjects subjects = (Subjects) getArguments().getSerializable(GRADES_KEY);

            ArrayList<Subject> sortedSubjects = new ArrayList<>();

            int index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język polski")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język angielski")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Język niemiecki")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Muzyka")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Historia")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Wiedza o społeczeństwie")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Geografia")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Biologia")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Chemia")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Fizyka")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Matematyka")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Informatyka")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Edukacja dla bezpieczeństwa")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Zajęcia techniczne")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Wychowanie do życia w rodzinie")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));

            index = 0;
            while(!subjects.getOneFromSubjects(index).getSubjectName().equals("Etyka")) {
                index++;
            }
            sortedSubjects.add(subjects.getOneFromSubjects(index));


            for (int i = 0; i < subjects.size(); i++) {
                String tempAvg = sortedSubjects.get(i).getSubjectAverage();
                if (!tempAvg.equals("")) {
                    if (tempAvg.length() == 1) {
                        tempAvg += ".00";
                    } else if (tempAvg.length() == 3) {
                        tempAvg += "0";
                    }
                    avgsTextView.get(i).setText(tempAvg);
                }

            }



            /*for (int i = 0; i < subjects.size(); i++) {
                if (!subjects.getAverage(i).equals("")) {

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
                }*/






            /*int money = 0;
            int extraMoneyCurrentMonth = 30;
            int extraMoneyPreviousMonth = 30;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("===== KIESZONKOWE =====\n\n");

            for (int i = 0; i < subjects.size(); i++) {
                stringBuilder.append(subjects.getName(i).toUpperCase()).append(" -> ");

                if (!subjects.getAverage(i).equals("")) {
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
            return stringBuilder.toString();*/

        }
    }

    public static MoneyFragment newInstance(Subjects subjects) {
        MoneyFragment fragment = new MoneyFragment();
        Bundle args = new Bundle();
        args.putSerializable(GRADES_KEY, subjects);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Kieszonkowe");
    }

    private void generateAvgsTextViewArray() {
        avgsTextView.add(moneyJezykPolskiAvg);
        avgsTextView.add(moneyJezykAngielskiAvg);
        avgsTextView.add(moneyJezykNiemieckiAvg);
        avgsTextView.add(moneyMuzykaAvg);
        avgsTextView.add(moneyHistoriaAvg);
        avgsTextView.add(moneyWiedzaOSpoleczenstwieAvg);
        avgsTextView.add(moneyGeografiaAvg);
        avgsTextView.add(moneyBiologiaAvg);
        avgsTextView.add(moneyChemiaAvg);
        avgsTextView.add(moneyFizykaAvg);
        avgsTextView.add(moneyMatematykaAvg);
        avgsTextView.add(moneyInformatykaAvg);
        avgsTextView.add(moneyEdukacjaDlaBezpieczenstwaAvg);
        avgsTextView.add(moneyZajeciaTechniczneAvg);
        avgsTextView.add(moneyWychowanieDoZyciaAvg);
        avgsTextView.add(moneyEtykaAvg);
    }

    private void generateMoneyTextViewArray() {
        moneyTextView.add(moneyJezykPolskiMoney);
        moneyTextView.add(moneyJezykAngielskiMoney);
        moneyTextView.add(moneyJezykNiemieckiMoney);
        moneyTextView.add(moneyMuzykaMoney);
        moneyTextView.add(moneyHistoriaMoney);
        moneyTextView.add(moneyWiedzaOSpoleczenstwieMoney);
        moneyTextView.add(moneyGeografiaMoney);
        moneyTextView.add(moneyBiologiaMoney);
        moneyTextView.add(moneyChemiaMoney);
        moneyTextView.add(moneyFizykaMoney);
        moneyTextView.add(moneyMatematykaMoney);
        moneyTextView.add(moneyInformatykaMoney);
        moneyTextView.add(moneyEdukacjaDlaBezpieczenstwaMoney);
        moneyTextView.add(moneyZajeciaTechniczneMoney);
        moneyTextView.add(moneyWychowanieDoZyciaMoney);
        moneyTextView.add(moneyEtykaMoney);
    }
}

