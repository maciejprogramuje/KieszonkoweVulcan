package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.DataFile;


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

    ArrayList<TextView> avgsTextViewArray = new ArrayList<>();
    ArrayList<TextView> moneyTextViewArray = new ArrayList<>();

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        ButterKnife.inject(this, view);

        MainActivity.showFab();

        generateAvgsTextViewArray();
        generateMoneyTextViewArray();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int money = 0;
        int extraMoneyCurrentMonth = 30;
        int extraMoneyPreviousMonth = 30;

        if (getArguments() != null) {
            Subjects tempSubjects = (Subjects) getArguments().getSerializable(GRADES_KEY);

            ArrayList<Subject> sortedSubjectsArray = DataFile.originOrder(tempSubjects);

            assert tempSubjects != null;
            for (int i = 0; i < tempSubjects.size(); i++) {
                int currentMonthInt = 0;
                int previousMonthInt = 0;

                String tempAvg = sortedSubjectsArray.get(i).getSubjectAverage();
                if (!tempAvg.equals("")) {
                    if (tempAvg.length() == 1) {
                        tempAvg += ".00";
                    } else if (tempAvg.length() == 3) {
                        tempAvg += "0";
                    }
                    avgsTextViewArray.get(i).setText(tempAvg);

                    double subjectAvg = Double.valueOf(tempAvg.replace(",", "."));
                    if (subjectAvg > 4.5) {
                        money += 15;
                        moneyTextViewArray.get(i).setText(R.string.zl15);
                    } else if (subjectAvg > 3.75) {
                        money += 10;
                        moneyTextViewArray.get(i).setText(R.string.zl10);
                    } else if (subjectAvg > 3.5) {
                        money += 0;
                        moneyTextViewArray.get(i).setText(R.string.zl0);
                    } else if (subjectAvg >= 3) {
                        money -= 5;
                        moneyTextViewArray.get(i).setText(R.string.zl5m);
                    } else if (subjectAvg < 3) {
                        money -= 10;
                        moneyTextViewArray.get(i).setText(R.string.zl10m);
                    }
                } else {
                    moneyTextViewArray.get(i).setText(R.string.zl0);
                }


                for (int j = 0; j <  sortedSubjectsArray.get(i).getSubjectGrades().size(); j++) {
                    Calendar gradeDateCal = Calendar.getInstance();
                    Calendar curDateCal = Calendar.getInstance();
                    currentMonthInt = curDateCal.get(Calendar.MONTH);
                    try {
                        String dateString = sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmDate();
                        Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                        gradeDateCal.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (gradeDateCal.get(Calendar.MONTH) == curDateCal.get(Calendar.MONTH)) {
                        if ((sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("1")
                                || sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("2"))
                                && !sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmWeight().equals("0")) {
                            extraMoneyCurrentMonth = 0;
                        } else if (sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("3")
                                && !sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmWeight().equals("0")
                                && extraMoneyCurrentMonth > 0) {
                            extraMoneyCurrentMonth -= 10;
                        }
                    }

                    curDateCal.add(Calendar.MONTH, -1);
                    previousMonthInt = curDateCal.get(Calendar.MONTH);

                    if (gradeDateCal.get(Calendar.MONTH) == curDateCal.get(Calendar.MONTH)) {
                        if ((sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("1")
                                || sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("2"))
                                && !sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmWeight().equals("0")) {
                            extraMoneyPreviousMonth = 0;
                        } else if (sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade().equals("3")
                                && !sortedSubjectsArray.get(i).getSubjectGrades().get(j).getmWeight().equals("0")
                                && extraMoneyPreviousMonth > 0) {
                            extraMoneyPreviousMonth -= 10;
                        }
                    }
                }

                String tempMoneyExtraCurrentString = "z premią za " + getMonthName(currentMonthInt);
                String tempMoneyExtraPreviousString = "z premią za " + getMonthName(previousMonthInt);


                moneyExtraCurrent.setText(tempMoneyExtraCurrentString);
                moneyExtraPrevious.setText(tempMoneyExtraPreviousString);

                String tempMoneySumCurrentString = (money + extraMoneyCurrentMonth) + " zł";
                String tempMoneySumPreviousString = (money + extraMoneyPreviousMonth) + " zł";

                moneySumCurrent.setText(tempMoneySumCurrentString);
                moneySumPrevious.setText(tempMoneySumPreviousString);
            }
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
        avgsTextViewArray.add(moneyJezykPolskiAvg);
        avgsTextViewArray.add(moneyJezykAngielskiAvg);
        avgsTextViewArray.add(moneyJezykNiemieckiAvg);
        avgsTextViewArray.add(moneyMuzykaAvg);
        avgsTextViewArray.add(moneyHistoriaAvg);
        avgsTextViewArray.add(moneyWiedzaOSpoleczenstwieAvg);
        avgsTextViewArray.add(moneyGeografiaAvg);
        avgsTextViewArray.add(moneyBiologiaAvg);
        avgsTextViewArray.add(moneyChemiaAvg);
        avgsTextViewArray.add(moneyFizykaAvg);
        avgsTextViewArray.add(moneyMatematykaAvg);
        avgsTextViewArray.add(moneyInformatykaAvg);
        avgsTextViewArray.add(moneyEdukacjaDlaBezpieczenstwaAvg);
        avgsTextViewArray.add(moneyZajeciaTechniczneAvg);
        avgsTextViewArray.add(moneyWychowanieDoZyciaAvg);
        avgsTextViewArray.add(moneyEtykaAvg);
    }

    private void generateMoneyTextViewArray() {
        moneyTextViewArray.add(moneyJezykPolskiMoney);
        moneyTextViewArray.add(moneyJezykAngielskiMoney);
        moneyTextViewArray.add(moneyJezykNiemieckiMoney);
        moneyTextViewArray.add(moneyMuzykaMoney);
        moneyTextViewArray.add(moneyHistoriaMoney);
        moneyTextViewArray.add(moneyWiedzaOSpoleczenstwieMoney);
        moneyTextViewArray.add(moneyGeografiaMoney);
        moneyTextViewArray.add(moneyBiologiaMoney);
        moneyTextViewArray.add(moneyChemiaMoney);
        moneyTextViewArray.add(moneyFizykaMoney);
        moneyTextViewArray.add(moneyMatematykaMoney);
        moneyTextViewArray.add(moneyInformatykaMoney);
        moneyTextViewArray.add(moneyEdukacjaDlaBezpieczenstwaMoney);
        moneyTextViewArray.add(moneyZajeciaTechniczneMoney);
        moneyTextViewArray.add(moneyWychowanieDoZyciaMoney);
        moneyTextViewArray.add(moneyEtykaMoney);
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

