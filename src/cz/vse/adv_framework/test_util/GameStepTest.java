/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IBag;
import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.game_txt.IObject;
import cz.vse.adv_framework.game_txt.INamed;
import cz.vse.adv_framework.game_txt.IPlace;

import java.util.Arrays;
import java.util.Collection;

import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.TypeOfStep;

import cz.vse.adv_framework.utilities.DBG_Logger;
import cz.vse.adv_framework.utilities.Util;

import java.util.LinkedHashSet;
import java.util.Set;

import static cz.vse.adv_framework.utilities.CompareIgnoreCase.CIC;
import static cz.vse.adv_framework.utilities.Util.*;
import static cz.vse.adv_framework.utilities.FormatStrings.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



/*******************************************************************************
 * Knihovní třída {@code GameStepTest} je schopna otestovat,
 * zda je hra po předchozím příkazu ve stavu
 * definovaném příslušným krokem scénáře.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
class GameStepTest
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Logger, prostřednictvím nějž zanamenáváme veškeré informace. */
    protected final static DBG_Logger DBG = DBG_Logger.getInstance();

    /** Maximální prověřovaná délka zprávy. */
    private static final int MAX = 80;

    /** Obhajoba */
    private static final Set<INamed> remembered = new LinkedHashSet<>();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Ověří, ze aktuální stav hry odpovídá informacím zapamatovaným
     * v atributech daného kroku testu a vrátí řetězec s popisem tohoto stavu.
     * Nebude-li aktuální stav odpovídat očekávanému, vyhodí výjimku
     * {@code IllegalStateException}.
     *
     * @param game    Hra, jejiž stav se prověřuje.
     * @param step    Testovaný krok scénáře
     * @param message Zpráva vrácená hrou po zadaní posledního příkazu
     *
     * @return Řetězcová reprezentace dané instance určena pro kontrolní tisk
     */
    public static String verify(final IGame game, final ScenarioStep step,
                                String message)
    {
        IPlace        actSpace = game.getCurrentPlace();
        StringBuilder sb       = new StringBuilder();

        if (demoStep(game, step, message, actSpace, sb)) {
            //U demonstračních kroků vrátí v sb popis daného kroku
            return sb.toString();
        }

        //sb jse stále prázdný - připraví popis kroku
        sb.append(step)
          .append("Obdržená zpráva:")
          .append(N_CIRCUMFLEXES_N)
          .append(message)
          .append(N_LINE_N);

        //Nesedí-li živost s očekáváním, doplní zprávu
        checkAliveness(game, step, sb);

        //Nesedí-li očekávaný a zjištěný stav, vyhodí výjimku
        checkState(game, step, message, actSpace);

        return sb.toString();
    }


    /***************************************************************************
     * Prověří, že zjištěný aktuální stav hry odpovídá stavu
     * očekávanému podle scénáře.
     *
     * @param game      Testovaná hra
     * @param step      Testovaný krok
     * @param message   Obdržená zpráva
     * @param actSpace  Aktuální prostor
     * @throws TestException v případě, že očekávaný stav neodpovídá zjištěnému
     */
    private static void checkState(final IGame game, final ScenarioStep step,
                                   String message, IPlace actSpace)
            throws TestException
    {
        StringBuilder sb = new StringBuilder();
        try {
            verifyMessage (sb, step, message);

            if (step.typeOfStep == TypeOfStep.tsNOT_START) {
                //U kroků prověřujících reakci na zadání příkazu mrtvé hře
                //prověřujeme pouze vrácenou zprávu
                return;
            }
            verifyCurrentPlace(sb, step, actSpace);
            verifyEquality(sb, step, actSpace    .getNeighbors(),step.neighbors,
                                                                 "východů");
            verifyEquality(sb, step, actSpace     .getObjects(), step.objects,
                                                                 "objektů");
            verifyEquality(sb, step, game.getBag().getObjects(), step.bag,
                                                                 "objektů v batohu");
        } catch(Exception ise) {
            if (step.typeOfStep != TypeOfStep.tsNOT_START) {
                describeError(ise, sb, game, step, actSpace, message);
            }
            throw new TestException(
                    "Předčasné ukončení testu scénáře v důsledku chyby:\n" +
                    ise.getMessage(), ise);
        }
    }


    /***************************************************************************
     * Prověří, že zjištěná živost hry odpovídá očekávané, a pokud ne,
     * přidá na konec stringbuilderu příslušnou zprávu.
     *
     * @param game  Testovaná hra
     * @param step  Testovaný krok
     * @param sb    {@link StringBuilder} pro případnou zprávu
     */
    private static void checkAliveness(final IGame game, final ScenarioStep step,
                                       StringBuilder sb)
    {
        if (game.isAlive()  ==  (step.typeOfStep == TypeOfStep.tsEND)) {
            String text = (step.typeOfStep == TypeOfStep.tsEND)
                          ? "Hra tvrdí, že běží, přestože byla ukončena"
                          : "Hra má běžet, a přitom tvrdí, že je ukončena";
            sb.append("\n##### ").append(text).append(N_DOUBLELINE_N);
        }
    }


    /***************************************************************************
     * Jedná-li se o demonstrační krok, přidá na konec obdrženého stringbuilderu
     * popis očekávaného stavu a vrátí informaci o tom,
     * byl-li krok demonstrační.
     *
     * @param game      Testovaná hra
     * @param step      Testovaný krok
     * @param message   Obdržená zpráva
     * @param actSpace  Aktuální prostor
     * @param sb        {@link StringBuilder} pro případnou zprávu
     * @return Informace o typu kroku – byl-li demonstrační, vrátí {@code true},
     *         v opačném případě vrátí {@code false}
     */
    private static boolean demoStep(final IGame game, final ScenarioStep step,
                           String message, IPlace actSpace, StringBuilder sb)
    {
        if (TypeOfStep.tsDEMO.equals(step.typeOfStep)) {
            //U demonstračních kroků se stav hry neověřuje, ale pouze zjišťuje
            ScenarioStep dStep = new ScenarioStep(
                    step.index, TypeOfStep.tsDEMO, step.command,
                    message,    actSpace.getName(),
                    Util.colINamed2arrString(actSpace.getNeighbors()),
                    Util.colINamed2arrString(actSpace.getObjects()),
                    Util.colINamed2arrString(game.getBag().getObjects()));
            return true;
        }
        return false;
    }


    private static final String DNM = "Dosud nenavštívené místnosti: [";
    private static final int    LEN = DNM.length();
    /***************************************************************************
     *
     * @param game
     * @param message
     */
    @SuppressWarnings("unchecked")
    private static void verifyNonVisited(IGame game, String message)
    {
        Set<String> notVisited = GameTRunTest.notVisited;
        if (! message.contains(DNM)) {
            throw new IllegalStateException(
                    "Zpráva neobsahuje hledaný text:" +
                    "\ntext: "    +  DNM +
                    "\nzpráva:\n" +  message);
        }
        message = message.trim();
        int start = message.indexOf(DNM) + LEN;
        int stop  = message.lastIndexOf(']');
        String announced = message.substring(start, stop);

        Set<String> namesSet;
        if (announced.isEmpty()) {
            namesSet = Collections.EMPTY_SET;
        }
        else {
            String[] namesArr = announced.split(", ");
            namesSet = new HashSet<>(namesArr.length);
            for (String name : namesArr) {
                namesSet.add(name.toLowerCase());
            }
        }
        String current = game.getCurrentPlace().getName().toLowerCase();
        notVisited.remove(current);
        GameTRunTest.notVisited = notVisited;
        if ( namesSet.equals(notVisited)  ||
            (namesSet.isEmpty() &&  notVisited.isEmpty()))
        {
            return;
        }
        List<String> expected = new ArrayList<>(notVisited);
        List<String> obtained = new ArrayList<>(namesSet);
        Collections.sort(expected);
        Collections.sort(obtained);
        throw new IllegalStateException(
                "Seznam nenavštívených místností nesouhlasí" +
                "\nOčekáváno: " +  expected +
                "\nObdrženo:  " +  obtained);
    }


    /***************************************************************************
     * Vyčistí všechny pamatované údaje,
     * takže od příštího testovaného kroku začne ukládat od začátku.
     */
    static void reset()
    {
        remembered.clear();
    }

//
//    //OBHAJOBA_5
//    /***************************************************************************
//     * Metoda se uplatní při obhajobě upravené aplikace, kdy ověří,
//     * že aplikace správě vypisuje doposud viděné objekty.
//     *
//     * @param sb       StringBuilder s dosavadní zprávou
//     * @param message  Zpráva vrácená hrou po zadaní posledního příkazu
//     * @param objects  Doposud "zahlédnuté" objekty
//     */
//    private static void verifySeen(StringBuilder sb, String message,
//                                   Collection<? extends INamed> objects)
//    {
//        addNewlySeen(objects);
//
//        String[] rows = message.split("\n");
//        int      last = rows.length - 1;
//        if (!"Zahlédnuté objekty:".equals(rows[last-1].trim())) {
//            String str = "\nPředposlední řádek zprávy neodpovídá požadavkům\n";
//            sb.append(str);
//            throw new IllegalStateException(str);
//        }
//        String list = rows[last];
//        int length = list.length();
//        if ((length > 0)   &&   (list.charAt(0) == '[')) {
//            list = list.substring(1);
//            length--;
//        }
//        if ((length > 0)   &&   (list.charAt(length-1) == ']')) {
//            list = list.substring(0, length-1);
//        }
//        String[] words = list.split(", ");
//        verifyEquality(sb, null, remembered, words, "viděných předmětů");
//    }
//
//
//    //OBHAJOBA_5
//    /***************************************************************************
//     * Metoda se uplatní při obhajobě upravené aplikace,
//     * kdy vrátí kolekci objektů vyskytujících se v právě navštíveném prostoru
//     *
//     * @param seen
//     */
//    private static void addNewlySeen(Collection<? extends INamed> seen)
//    {
////        Collection<String> velkými =
////                          new ArrayList<String>(nové.size());
////        for (INamed io : nové) {
////            velkými.add(io.getName().toUpperCase());
////        }
//    Outer:
//        for (INamed ipn : seen) {
//            String name = ipn.getName();
//            if ("koš".equalsIgnoreCase(name)) {
//                System.out.println("TADY");
//            }
//            for (INamed ipp : remembered) {
//                if (name.equalsIgnoreCase(ipp.getName())) {
//                    continue Outer;
//                }
//            }
//            remembered.add(ipn);
//        }
//    }
//



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @param krok Testovaný krok scénáře
     */
    private GameStepTest(ScenarioStep krok)
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Ověří, jestli zadaný parametr není prázdným odkazem,
     * a pokud ano, vyhodí výjimku
     *
     * @param object    Parametr, jehož "nullovost" testujeme
     * @param message   Zpráva, kterou vypíše příslušná výjimka
     * @throws IllegalArgumentException Je-li testovaný parametr {@code null}
     */
    private static void verifyNull(Object object, String message)
    {
        if (object == null) {
            throw new IllegalArgumentException(
                  NN_DOUBLELINE_NN + message + NN_DOUBLELINE_NN);
        }
    }


    /***************************************************************************
     * Ověří shodu názvu očekávaného a zadaného prostoru.
     *
     * @param sb   {@link StringBuilder}, v němž metoda předává chybové hlášení
     * @param step Prověřovaný krok scénáře
     * @param placeInTest Prostor, jehož název testujeme
     * @throws IllegalStateException
     *         Je-li aktuální prostor jiný než očekávaný
     * @throws NullPointerException
     *         Je-li odkaz na prostor k prověření je prázdný
     */
    private static void verifyCurrentPlace(StringBuilder sb,
                        final ScenarioStep step, IPlace placeInTest)
    {
        String str;
        if (placeInTest == null) {
            str = DOUBLELINE_N + "Odkaz na prostor k prověření je prázdný" +
                  N_DOUBLELINE_N;
            sb.append(str);
            throw new NullPointerException(str);

        }
        if (! placeInTest.getName().equalsIgnoreCase(step.place)) {
            str = DOUBLELINE +
                  "\nAktuální prostor je jiný než očekávaný" +
                  "\n   Očekáváno: " + A_ + step.place + _Z +
                  "\n   Obdrženo:  " + A_ + placeInTest.getName() + _Z +
                  N_DOUBLELINE_N;
            sb.append(str);
            throw new IllegalStateException(str);
        }
    }


    /***************************************************************************
     * Ověří shodu očekávaných a zjištěných pojmenovaných instanci;
     * neshoduji-li se, vyhodí výjimku {@code IllegalStateException}.
     *
     * @param sb   {@link StringBuilder}, v němž metoda předává chybové hlášení
     * @param step      Prověřovaný krok scénáře
     * @param instance  Kolekce obdržených instancí
     * @param expected  Pole názvů očekávaných instancí
     * @param kind      Druh testovaných instancí - pouze pro chybovou zprávu
     * @throws IllegalStateException
     *         Názvy obdržených instancí neodpovídají očekávaným názvům
     */

    private static void verifyEquality(StringBuilder sb,
            final ScenarioStep step, Collection<? extends INamed> instance,
            String[] expected, String kind)
    {
        String[] names = colINamed2arrString(instance);
        Arrays.sort(names, CIC);
        Arrays.sort(expected, CIC);
        int L = expected.length;
        try {
            if (L != names.length) {
                throw new IllegalStateException();
            }
            for (int v=0;   v < L;   v++) {
                if (! expected[v].equalsIgnoreCase(names[v])) {
                    throw new IllegalStateException();
                }
            }
        } catch(IllegalStateException ise) {
            String str = DOUBLELINE_N +
                  "Seznam očekávaných a obddržených " + kind + " nesouhlasí" +
                "\n   Čekám:   " + arr2String(expected) +
                "\n   Obrdžel: " + arr2String(names) +
                N_DOUBLELINE;
            sb.append(str);
            throw new IllegalStateException(str);
        }
    }


    /***************************************************************************
     * Ověří shodu očekávané a obdržené zprávy;
     * neshoduji-li se, vyhodí výjimku {@code IllegalStateException}
     * která vypíše shodující se počátek zprávy a u neshodujícího se znaku
     * kódy očekávaného a obdrženého znaku.
     *
     * @param sb     {@link StringBuilder}, v němž metoda předává chybové hlášení
     * @param step    Prověřovaný krok scénáře
     * @param obtainedMessage
     *                Zpráva, jejíž obsah je porovnáván s příslušným atributem
     * @throws IllegalStateException
     *         Pokud se očekávaná a obdržená zpráva neshodují
     */
    private static void verifyMessage(StringBuilder sb,
                        final ScenarioStep step, String obtainedMessage)
    {
        if (ScenarioStep.IGNORED_MESSAGE.equals(step.message)) { return; }
        String expected = checkedPart(step.message);
        String obtained = checkedPart(obtainedMessage);

        //Prověření počátku zprávy
        if (obtained.equalsIgnoreCase(expected)) {
            return;                             //==========>
        }
        //Text skládám před přidáním do StringBuilderu, aby byl přehlednější
        String txt =
               DOUBLELINE_N +
               "Začátky očekávané a obdržené zprávy nesouhlasí." +
             "\n   Čekám:  " + A_ + expected  + _Z +
             "\n   Přišlo: " + A_ + obtained + _Z ;
        sb.append(txt);
        int expectedLength  = expected.length();
        int obtainedLength = obtained.length();
        if (expectedLength > obtainedLength) {
            sb.append("\nOčekávaná zpráva je delší než obdržená");
        }
        int numOfChar = Math.min(expectedLength, obtainedLength);
        for (int i=0;   i < numOfChar;   i++) {
            if (Character.toLowerCase(obtained.charAt(i))  !=
                Character.toLowerCase(expected .charAt(i)) )
            {
                char c = expected .charAt(i);
                char p = obtained.charAt(i);
                txt = "\nOvěřený počátek zprávy: \n           " + A_ +
                            expected.substring(0, i) + _Z +
                      "\nOdchylka na pozici " + i +
                      "\n   očekávám kód     "  + (int) c +
                      ((c > ' ')  ?  (" znak " + A_ + c + _Z)  :  "") +
                      "\n   obdržel jsem kód "  + (int) p +
                      ((p > ' ')  ?  (" znak " + A_ + p + _Z)  :  "");
                sb.append(txt);
                break;
            }
        }
        sb.append(N_DOUBLELINE_N);
        txt = sb.toString();
//        DBG.info(txt);
        throw new IllegalStateException(txt);
    }


    /***************************************************************************
     * Zjistí, zda příčinou chyby nebyl prázdný odkaz, a pokud ano, tak jaký,
     * a potom vypíše očekávaný a obdržený stav hry po daném kroku scénáře.
     *
     * @param exc       Vyhozená výjimka
     * @param sb        StringBuilder s doposud zjištěnými informacemi o chybě
     * @param game      Testovaná hra
     * @param step      Prováděný krok scénáře
     * @param actPlace  Prostor, v němž se hra nachází
     *                  po provedení zadaného kroku scénáře
     * @param message   Zpráva, kterou hra předala
     *                  po provedení zadaného kroku scénáře
     */
    private static void describeError(Exception exc, StringBuilder sb,
            IGame game, ScenarioStep step, IPlace actPlace, String message)
    {
        IBag                          gameBag;
        Collection<? extends IObject> bagContent;
        Collection<? extends IPlace>  neighbors;
        Collection<? extends IObject> placeContent;

        try {
            verifyNull(actPlace, "Hra nevrátila aktuální prostor");

            gameBag = game.getBag();
            verifyNull(gameBag, "Hra nevrátila batoh");

            bagContent = gameBag.getObjects();
            verifyNull(bagContent, "Batoh nevrátil svůj obsah");

            neighbors = actPlace.getNeighbors();
            verifyNull(neighbors, "Prostor nevrátil sousedy");

            placeContent = actPlace.getObjects();
            verifyNull(placeContent, "Prostor nevrátil objekty");
        }
        catch (Exception ex) {
            throw new RuntimeException(
                  NN_DOUBLELINE_NN + "Po zadání příkazu «" + step.command +
                  "» hra odpověděla:" + N_LINE_N + message + N_LINE_N +
                  "Při vyhodnocování dopovědy se objevil problém:" +
                  ex.getMessage(),
                  ex);
        }

        String obtained;
        try {
            ScenarioStep gameReturns = new ScenarioStep(step.index,
                TypeOfStep.tsNOT_SET,
                step.command,
                message,
                actPlace.getName(),
                colINamed2arrString(neighbors),
                colINamed2arrString(placeContent),
                colINamed2arrString(bagContent));
            obtained = gameReturns.toString();
        }
        catch (Exception ex) {
            obtained = "Některý z údajů vrácených hrou byl nepovolený";
        }
        String text = NN_DOUBLELINE +
               "\nCHYBA: " + exc.getStackTrace()[0].getMethodName() +"\n"+ sb +
               "\n\n===== Očekáváno =====" + step +
               "\n\n===== Obdrženo ====="  +  obtained +
               DOUBLELINE_N;

        DBG.severe(text);
        DBG.severe(exc);
    }


    /***************************************************************************
     * Vrátí první neprázdný řádek zadaného textu.
     * <p>
     * Dříve:
     * Vrátí začátek zadaného textu až po první
     * tečku, dvojtečku, otazník, vykřičník nebo konec řádku.
     *
     * @param text Text, jehož začátek je zjišťován
     * @return Začátek zadaného textu
     */
    private static String checkedPart(String text)
    {
        String věta = text.trim();
        if (věta.length() > 0) {
            String části[] = věta.split("\\n");  //("[\\.\\n!\\?\\:]");
            for (String s : části) {
                String st = s.trim();
                if (st.length() > 0) {
                    return st;           //==========>
                }
            }
        }
        throw new IllegalStateException("\nŠpatně zadaná zpráva");
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        GameStepTest inst = new GameStepTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
