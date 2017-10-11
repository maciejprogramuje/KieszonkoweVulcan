package commaciejprogramuje.facebook.kieszonkowevulcan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by m.szymczyk on 2017-10-11.
 */

public class WaitMessages {
    private final List<String> waitMessages;

    public WaitMessages() {
        waitMessages = new ArrayList<>();

        waitMessages.add("'Dies diem docet'\nDzień uczy dzień");
        waitMessages.add("'In vino veritas'\nW winie prawda");
        waitMessages.add("'Carpe diem'\nChwytaj dzień\nHoracy");
        waitMessages.add("'Repetitio est mater studiorum'\nPowtarzanie jest matką wiedzy");
        waitMessages.add("'Absens carens'\nNieobecny traci");
        waitMessages.add("'Vae victis!'\nBiada zwyciężonym!\nsłowa władcy Galów, Brennusa");
        waitMessages.add("'Veni, vidi, vici'\nPrzybyłem, zobaczyłem, zwyciężyłem\nJuliusz Cezar");
        waitMessages.add("'Qualis artifex pereo!'\nJakiż artysta ginie!\nostatnie słowa Nerona");
        waitMessages.add("'Eventus stultorum magister'\nRezultat nauczycielem głupich");
        waitMessages.add("'Varium et mutabile semper femina'\nKobieta zmienną jest");
        waitMessages.add("'Historia vitae magistra est'\nHistoria nauczycielką życia\nCyceron");
        waitMessages.add("'Duobus litigantibus tertius gaudet'\nGdzie dwóch się bije, tam trzeci korzysta");
        waitMessages.add("'Pecunia non olet'\nPieniądze nie śmierdzą");
        waitMessages.add("'In vino veritas in aqua sanitas'\nW winie prawda, w wodzie zdrowie");
        waitMessages.add("'Vanitas vanitatum et omnia vanitas'\nMarność nad marnościami i wszystko marność");
        waitMessages.add("'Alea iacta est'\nKości zostały rzucone\nJuliusz Cezar");
        waitMessages.add("'Ora et labora'\nMódl się i pracuj\nśw. Benedykt");
        waitMessages.add("'Non scholae sed vitae discimus'\nNie dla szkoły, lecz dla życia uczymy się");
        waitMessages.add("'Dura lex, sed lex'\nTwarde prawo, ale prawo");
        waitMessages.add("'Imperare sibi maximum imperium est'\nRozkazywać sobie jest najwyższą władzą");
        waitMessages.add("'Dum spiro, spero'\nDopóki oddycham, nie tracę nadziei");
        waitMessages.add("'Mali amici malus fructus ferun't\nŹli przyjaciele przynoszą złe owoce");
        waitMessages.add("'Amicus certus in re incerta cernitur'\nPrawdziwego przyjaciela poznasz w biedzie");
        waitMessages.add("'Cogito ergo sum'\nMyślę więc jestem\nKartezjusz");
        waitMessages.add("'Errare humanum est'\nMylić się jest rzeczą ludzką");
        waitMessages.add("'Scio me nihil scire'\nWiem, że nic nie wiem\nSokrates");
        waitMessages.add("'Per aspera ad astra'\nPrzez ciernie do gwiazd");
        waitMessages.add("'O tempora! O mores!'\nO czasy! O obyczaje!\nCyceron");
        waitMessages.add("'De gustibus non disputandum est'\nGust nie podlega dyskusji");
        waitMessages.add("'Fame est peius quam dolor'\nGłód jest gorszy od bólu");
        waitMessages.add("'Et tu Brute, contra me?'\nI ty Brutusie, przeciwko mnie?\nCezar do zamachowcy, przyjaciela");
        waitMessages.add("'Manus manum lavat'\nRęka rękę myje");
        waitMessages.add("'Homo sum, humani nihil a me alienum puto'\nCzłowiekiem jestem i nic, co ludzkie, nie jest mi obce\nTerencjusz");
        waitMessages.add("'Deus vult'\nBóg tak chce");
    }

    public String getRandomText() {
        Random random = new Random();
        return waitMessages.get(random.nextInt(waitMessages.size()));
    }
}
