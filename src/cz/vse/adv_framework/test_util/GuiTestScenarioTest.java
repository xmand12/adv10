/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IPlaceG;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.ScenarioStep;

import cz.vse.adv_framework.utilities.CompareIgnoreCase;
import cz.vse.adv_framework.utilities.Util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;





/*******************************************************************************
 * Instance třídy {@code GuiTestScenarioTest} představují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class GuiTestScenarioTest implements IVerifier
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Identifikace jednotlivcýh příkazů ze základní sady povinných příkazů. */
    private static final int cMOVE = 0,  cPUT = 1,  cPICK = 2, cEND = 3;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Instance třídy, z níž byly tato třída vyčleněna
     *  a s níž sdílí některé informace. */
    private final GameGClassTest ggct;

    /** Správce scénářů, který spravuje scénáře umožňující testování hry. */
    private final AScenarioManager manager;

    /** Implementuje-li hra rozhraní {@link IGameG}, naplní se  další atribut
     *  přetypovanou instancí, aby potomci nemuseli přetypovávat. */
    private final IGameG gameG;

    /** Seznam příkazů scénáře pro testování GUI. */
    private List<String> fullCommands;

    /** Vektor zkrácenývh verzí příkazů, v nichž je mezi vlastním příkazem
     *  a jeho případným parametrem pouze jediná mezera. */
    private String[] shortCommands;

    /** Navštívené prostory pro kontrolu počtu navštívených. */
    private final Set<String> visited =
                              new TreeSet<>(CompareIgnoreCase.CIC);

    /** Množina prostorů, kde byly zvedány objekty, a v nichž se proto
     *  nezapočítává případné položení objektu. */
    private final Set<String> notForPut =
                              new TreeSet<>(CompareIgnoreCase.CIC);

    /** Množina všech prostorů v dané hřea (alespoň jak je hra hlásí). */
    private final Set<String> allPlaces =
                              new TreeSet<>(CompareIgnoreCase.CIC);

    /** Objekty zvednuté příkazy pro zvedání objektů. */
    private final Set<String> pickedObjects =
                              new TreeSet<>(CompareIgnoreCase.CIC);



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Stav hry po minulém kroku, tj. před zadáním příkazu. */
    ScenarioStep oldStep;

    /** Stav hry po zadání posledního příkazu. */
    ScenarioStep newStep;

    /** Pořadí posledního zadaného příkazu. */
    int ssIndex = 0;

    /** Přízhnak toho, zda test probíhá v pořádku. */
    boolean ok;

    /** Postuponě generovaná zpráva. */
    String msg;

    /** Formát pro výstup jednoho příkazu. */
    String format;

    /** Počet aktuálně zvednutých předmětů. */
    int numOfPicked = 0;

    /** Nejvyšší dosažený počet zvednutých předmětů. */
    int maxPicked = 0;

    /** Počet aktuálně položených předmětů. */
    int numOfPutted  = 0;

    /** Nejvyšší dosažený počet položených předmětů. */
    int maxPutted = 0;

    /** Název aktuálního prostoru = prostoru, v němž se hráč právě nachází. */
    String actualPlace;

    /** Collekce předmětů v batohu. */
    Collection<String> inBag;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @param ggct Instance třídy, z níž byly tato třída vyčleněna
     *             a s níž sdílí některé informace
     */
    GuiTestScenarioTest(GameGClassTest ggct)
    {
        this.ggct    = ggct;
        this.gameG   = ggct.getGameG();
        this.manager = ggct.manager;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Potvrdí, že správce scénářů vrací demonstrační scénář
     * s požadovanými vlastnostmi.
     *
     * @return Zpráva o výsledku testu
     */
    @Override
    public String verify()
    {
        initialization();

        StringBuilder sb = new StringBuilder(
              "  Výchozí stav: Zvednuto=0, Položeno=0, Prostorů=1, " +
                "Prostor=" + actualPlace + ",  Batoh =" + inBag +
            "\n  Zadávané kroky:\n");

        boolean first = true;
        boolean finished = false;

        for (String line : shortCommands) {
            if (first) {
                if ( "".equals(fullCommands.get(0))) {
                    first = false;
                    continue;
                }
                throw new RuntimeException(
                    "\nScénář pro test GUI nezačíná prázdným příkazem");
            }
            gameG.executeCommand(line);
            newStep = new ScenarioStep(ssIndex++, gameG, line, "-");
            actualPlace  = newStep.place;
            inBag   = Arrays.asList(newStep.bag);
                    //Util.colINamed2ssetString(gameBag.getObjects());

            String[] words = line.split("\\s+");
            if (words.length == 0) {
                sb.append("! ZADÁN PRÁZDNÝ PŘÍKAZ");
                ok = false;
                continue;
            }
            String command = words[0];
            Integer ID = ggct.command2ID.get(command);
            int     id = (ID == null)  ? -1  :  ID.intValue();
            msg = "";
            switch (id)
            {
                case cMOVE:
                    checkCorrectMove(words);
                    break;

                case cPICK:
                    checkCorrectPick(words);
                    break;

                case cPUT:
                    checkCorrectPut(words);
                    break;

                case cEND:
                    finished = checkCorrectEnd();
                    break;

                default:
            }
            sendMessageAboutStep(line, sb);
        }

        if (! finished) {
            sb.append("  ! TESTOVACÍ SCÉNÁŘ NEOBSAHUJE PŘÍKAZ " +
                          "PRO NESTANDARDNÍ UKONČENÍ HRY\n");
            ok = false;
        }
        ggct.stopGame();
        return ggct.insertSummary(sb, ok,
                             "+ Scénář pro test GUI se zdá být korektní",
                             "- SCÉNÁŘ PRO TEST GUI OBSAHUJE CHYBY");
    }


    /***************************************************************************
     * Inicializuje test tím, že hru spustí
     * a zjistí její stav po provedení úvodního kroku.
     */
    private void initialization()
    {
        ok = true;
        ggct.startGame();    //Startujeme hru, aby obdržené kolekce byly platné

        //Krok scénáře, který si pamatuje aktuální stav hry
        oldStep = new ScenarioStep(++ssIndex, gameG, "", "-");

        //Seznam všech příkazů zadávaných podle scénáře pro testování GUI
        fullCommands = manager.getGuiTestScenario().getListOfCommands();

        //Vektor příkazů zbavených zbytečných mezer
        shortCommands = new String[fullCommands.size()];

        format = prepareFormat(shortCommands);

        //Všechny deklarované prostory pro kontrolu, že nepůjdeme jinam
        for (IPlaceG iPlaceG : gameG.getAllPlaces()) {
            allPlaces.add(iPlaceG.getName());
        }

        inBag = Arrays.asList(oldStep.bag);
//        Set<String> inBag = Util.colINamed2ssetString(gameBag.getObjects());

        actualPlace = gameG.getCurrentPlace().getName();
        visited.add(actualPlace);
        msg = ggct.checkPlaceAnnounced(actualPlace, allPlaces);
        ok &= "".equals(msg);
    }


    /***************************************************************************
     *
     * @param line
     * @param sb
     */
    private void sendMessageAboutStep(String line, StringBuilder sb)
    {
        //Zkontroluje, že aktuální prostor je mezi ohlášenými
        msg += ggct.checkPlaceAnnounced(actualPlace, allPlaces);
        String message = String.format(format,
//                  "  ¤ %-" + maxLen + "s - Zvednuto=%d, Položeno=%d, " +
//                  "Batoh=%s\n%-" + (maxLen+7) +
//                  "sProstor=%s, Prostorů=%d, Navštívené=%s\n" +
//                  "Sousedé=%s%nObjekty=%s";
               line, numOfPicked, numOfPutted, inBag,
               "", actualPlace, visited.size(), visited,
               "", Util.arr2String(newStep.neighbors),
               "", Util.arr2String(newStep.objects));
        if (! "".equals(msg)) {
            message += String.format("%-20s%s\n", "", msg);
        }
        sb.append(message).append('\n');
        oldStep = newStep;
    }


    /***************************************************************************
     * Ověří správné provedení příkazu položení objektu z batohu.
     *
     * @return Pokud hra hláší ukončenost a tím i připravenost k dalšímu
     *         spuštění, vrátí {@code true}, jinak vrátí {@code false}
     */
    private boolean checkCorrectEnd()
    {
        boolean finished;
        finished = true;
        if (gameG.isAlive()) {
            msg += "! HRA SE PO UKONČOVACÍM PŘÍKAZU " +
                   "DÁLE TVÁŘÍ JAKO SPUŠTĚNÁ\n";
            ok = false;
        }
        return finished;
    }


    /***************************************************************************
     * Ověří správné provedení příkazu položení objektu z batohu.
     *
     * @param words Jednotivá slova zadaného příkazu
     */
    private void checkCorrectPut(String[] words)
    {
        if (notForPut.contains(actualPlace)) {
            msg+="! POLOŽENÍ OBJEKTU SE NEPOČÍTÁ, PROTOŽE " +
                 "V TOMTO PROSTORU BYLY OBJEKTY ZVEDÁNY\n";
        }
        else {
            numOfPutted += 1;
            if (numOfPutted > maxPutted) {
                maxPutted = numOfPutted;
            }
        }
        String argument = words[1];
        if (Util.differOnlyIn(argument, oldStep.bag, newStep.bag) &&
            Util.differOnlyIn(argument,
                              newStep.objects, oldStep.objects))
        {}
        else {
            msg += "! PŘI POKLÁDÁNÍ PŘEDMĚTU SE NESTALO, " +
                   "ŽE POKLÁDANÝ OBJEKT ZMIZÍ Z BATOHU "+
                   "A OBJEVÍ SE V AKTUÁLNÍM PROSTORU";
            ok = false;
        }
    }


    /***************************************************************************
     * Ověří správné provedení příkazu zvednutí objektu v prostoru.
     *
     * @param words Jednotivá slova zadaného příkazu
     */
    private void checkCorrectPick(String[] words)
    {
        //Zapamatuje si prostor, kde něco zvedal
        notForPut.add(actualPlace);
        numOfPicked += 1;
        if (numOfPicked > maxPicked) {
            maxPicked = numOfPicked;
        }
        String argument = words[1];
        if (Util.differOnlyIn(argument, newStep.bag, oldStep.bag) &&
            Util.differOnlyIn(argument,
                              oldStep.objects, newStep.objects))
        {}
        else {
            msg += "! PŘI ZVEDÁNÍ PŘEDMĚTU SE NESTALO, " +
                   "ŽE ZVEDANÝ OBJEKT ZMIZÍ Z AKTUÁLNÍHO PROSTORU "+
                   "A OBJEVÍ SE V BATOHO";
            ok = false;
        }
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Prověří, že příkaz pro přesun z prostoru do prostoru
     * byl proveden správně.
     *
     * @param words        Slova zadaného příkazu
     */
    private void checkCorrectMove(String[] words)
    {
        //Zapamatuje si navštívený prostor a ověří jeho korektnost
        String argument = words[1];
        visited.add(argument);
        if (! actualPlace.equalsIgnoreCase(argument)) {
            msg += "! HRÁČ SKONČIL V JINÉM NEŽ ZADANÉM PROSTORU\n";
            ok = false;
        }
        if (! Util.containsIgnoreCase(argument, oldStep.neighbors)){
            msg += "! CÍLOVÝ PROSTOR NEBYL MEZI OHLÁŠENÝMI " +
                   "SOUSEDY AKTUÁLNÍHO PROSTORU\n";
            ok = false;
        }
    }


    /***************************************************************************
     * Připraví formát pro tisk informací o každém jednom zadném příkazu.
     *
     * @param shortCommands Zahuštěný příkaz - mezi příkazem a případným
     *                      parametrem je jediná mezera
     * @return String použitelný jako formát
     */
    private String prepareFormat(String[] shortCommands)
    {
        //Délka nejdelšího příkazu pro účely formátování
        int maxLen =  0;
        for (int i=0;   i < fullCommands.size();   i++) {
            String s = fullCommands.get(i).trim().replaceAll("\\s+", " ");
            shortCommands[i] = s;
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }
        format = "  ¤ %-" + maxLen + "s - Zvednuto=%d, Položeno=%d, " +
                 "Batoh=%s%n%-" + (maxLen+7) +
                 "sProstor=%s, Prostorů=%d, Navštívené=%s%n%-" + (maxLen+7) +
                 "sSousedé=%s%n%-" + (maxLen+7) +
                 "sObjekty=%s%n";
        return format;
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        Sbl_Class instance = new Sbl_Class();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
