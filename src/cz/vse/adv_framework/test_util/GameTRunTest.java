/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.game_txt.IPlace;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.TypeOfStep;
import cz.vse.adv_framework.scenario.TypeOfScenario;

import cz.vse.adv_framework.test_util.ScenarioTest.Summary;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;


import static cz.vse.adv_framework.scenario.TypeOfStep.*;

import static cz.vse.adv_framework.utilities.FormatStrings.*;
import java.util.HashSet;
import java.util.Set;


/*******************************************************************************
 * Instance třídy {@code GameTRunTest} slouží k testování
 * her implementujících rozhraní {@code IGame} a ovladatelných podle scénářů
 * spravovaných instancí potomka třídy {@link AScenarioManager}.
 * Instance jsou schopny prověřit, zda při spuštění libovolného testovacího
 * scénáře hra reaguje tak, jak je ve scénáři naplánováno.  Za testovací scénář
 * se přitom považuje libovolný scénář, který není demonstrační.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
class GameTRunTest extends ATest
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Příznak práce v textovém (tj. ne okenním) režimu. */
    private static final boolean TEXT_IU = true;



//== VARIABLE CLASS ATTRIBUTES =================================================

    public static Set<String> notVisited;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na instanci testované hry. */
    private final IGame game;

    /** Úplný název třídy testované hry. */
    private final String gamename;

    /** Správce scénářů testované hry. */
    private final AScenarioManager manager;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Autor testované hry. */
    private String author;

    /** Xname autora testované hry. */
    private String xname;

    /** Proměnná udržující informaci o tom, je-li hra ve stavu před startem,
     *  tj. ještě nezačala, anebo již skončila a může znovu začít. */
    private boolean notRunning;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci, která bude testovat zadanou hru.
     *
     * @param hra Hra, která se bude testovat
     */
    GameTRunTest (IGame hra)
    {
        this.game     = hra;
        this.gamename = hra.getClass().getName();
        this.manager  = hra.getScenarioManager();
        this.author   = hra.getAuthorName();
        this.xname    = hra.getAuthorID();

        if (!(author.equals(manager.getAuthorName())  &&
              xname.equals(manager.getAuthorID())))
        {
            throw new RuntimeException(
                    "\nAutor hry není totožný s autorem správce scénářů");
        }

        if (game != manager.getGame()) {
            throw new RuntimeException(
                    "\nHra není totožá s hrou ohlašovnou správcem scénářů");
        }
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================




//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Souhrn akcí, které je potřeba provést před vlastním odstartováním hry.
     * V této třídě nemusí metoda dělat nic, ale potomci mohou mít požadavky.
     */
    protected void beforeStart()
    {
        GameStepTest.reset();
    }


    /***************************************************************************
     * Prověří správnou funkci hry v zadaném kroku testu.
     *
     * @param  step Prováděný krok scénáře
     * @throws IllegalStateException v případe, kdy stav hry neodpovídá
     *         stavu očekávanému podle hodnot dané instance kroku testu
     */
    protected void verifyScenarioStep(ScenarioStep step)
    {
        String command = step.getCommand();
        String message;
        try {
            message = game.executeCommand(command);
        }
        catch (Exception ex) {
            throw new RuntimeException(
                    "\nPři vykonávání příkazu: «" + command + "»" +
                    "\nvyhodila hra výjimku", ex);
        }
        String inspection = GameStepTest.verify(game, step, message);
        verboseMessage.append(inspection);
        DBG.info(inspection);
        if (! game.isAlive()) {
            notRunning = true;
        }
    }


    /***************************************************************************
     * Souhrn akcí, které je potřeba provést po skončení hry.
     * V této třídě nemusí metoda dělat nic, ale potomci mohou mít požadavky.
     */
    protected void afterEnd()
    {
    }


    /***************************************************************************
     * Souhrn akcí, které je potřeba provést po tisku zprávy o ukončení hry;
     * metoda vrací informaci o tom, byla-li v průběhu testu zjištěna
     * nějaká méně závažná chyba (závažné chyby končí vyhozením výjimky).
     * V této třídě nemusí metoda dělat nic, ale potomci mohou mít požadavky.
     *
     * @return Byly-li zjištěny nějaké chyby, vrátí text s jejich popisy,
     *         v opačném případě vrátí prázdný řetězec
     */
    public String finalSummary()
    {
        String text;
        if (foundErrors.isEmpty()) {
            text = "";
        }
        else {
            StringBuilder sb = new StringBuilder("\nNalezené chyby:" +
                                                 N_LINE_N);
            for (String s : foundErrors) {
                sb.append(s).append(N_LINE_N);
            }
            text = sb.toString();
        }
        return text;
    }


    /***************************************************************************
     * Spustí komunikaci mezi implicitní hrou a
     * hypotetickým uživatelem reprezentovaným instancí tohoto testu
     * řízenou nultým scénářem.
     *
     * @return Informace, zda test proběhl bez chyb
     */
    public boolean executeBasicScenario()
    {
        return executeScenario(0);
    }


    /***************************************************************************
     * Spustí hru řízenou jejím scénářem se zadaným indexem;
     * této hře bude postupně zadávat příkazy scénáře
     * a kontrolovat její odpovědi.
     *
     * @param index Index scénáře, kterým testujeme hru
     * @return Informace, zda test proběhl bez chyb
     */
    public boolean executeScenario(int index)
    {
        Scenario scenario = manager.getScenario(index);
        return executeScenario(scenario);
    }


    /***************************************************************************
     * Spustí hru řízenou zadaným scénářem; této hře bude postupně
     * zadávat příkazy scénáře a porovnávat její odpovědi s požadavky scénáře.
     *
     * @param scenario Scénář, kterým testujeme hru
     * @return Informace, zda test proběhl bez chyb
     */
    public boolean executeScenario(Scenario scenario)
    {
        if (scenario.getManager() != manager) {
            throw new RuntimeException(
                    "\nNeodpovídá správce scénářů");
        }
        initializeNonVisited(scenario);

        boolean errorOccured;
        Exception exception = null;
        foundErrors.clear();

        String text = header(scenario) +
                      "===== Test prováděných operací =====";
        DBG.info(text);

        try {
            beforeStart();

            notRunning = true;
            int index = 1;
            for (ScenarioStep ks : scenario) {
                ks.setNewIndex(index++);
                notRunning = verifyIsReady(notRunning);
                verifyScenarioStep(ks);        //Tiskne zprávu
            }
            if (game.isAlive()) {
                ERROR("Po ukončení scénáře hra tvrdí, že stále běží." +
                    "\nScénář musí odevzdat ukončenou hru.");
            }

            afterEnd();
        } catch(Exception e) {
            exception = e;
        } finally {
            errorOccured = ! foundErrors.isEmpty();
            DBG.info("\n===== Konec testu prováděných operací ====={0}",
                     header(scenario));
            if (exception != null) {
                errorOccured = true;
                StringWriter sw = new StringWriter();
                try (PrintWriter pw = new PrintWriter(sw)) {
                    exception.printStackTrace(pw);
                    DBG.severe("Ukončení bylo způsobeno vyhozením výjimky:\n{0}",
                               sw.toString());
                }
            }
            String chyby = finalSummary();
            errorOccured |= ! "".equals(chyby);
            StringBuilder message = new StringBuilder("\nPodle výsledků testu ");
            if (errorOccured) {
                message.append("PROGRAM OBSAHUJE JISTÉ CHYBY\n")
                       .append(chyby);
            }
            else {
                message.append(
                        "program pravděpodobně neobsahuje žádné závažné chyby");
                score += 1;
            }
            message.append(N_HASHES_N).append(HASHES_N);
//            String txt = "\nPodle výsledků testu " +
//                    (errorOccured
//                    ?  "PROGRAM OBSAHUJE JISTÉ CHYBY\n" + chyby
//                    :  "program pravděpodobně neobsahuje žádné závažné chyby") +
//                    N_HASHES_N + HASHES_N;
//                "\n===========================================" +
//                  "===========================================\n";
            String txt = message.toString();
            DBG.info(txt);
            verboseMessage.append(txt);
            shortMessage  .append(txt);
        }
        if (errorOccured) {
            game.stop();
        }
        return errorOccured;
    }


    /***************************************************************************
     * Provede všechny scénáře spravované správcem scénářů testované hry.
     *
     * @return Informace, zda testy proběhly bez chyb
     */
    public boolean  executeAllScenarios()
    {
        boolean errorOccured = false;
        for (Scenario scenario : manager) {
            errorOccured |= executeScenario(scenario);
        }
        return errorOccured;
    }


    /***************************************************************************
     * Spustí hru podle scénáře základního úspěšného, základního chybového
     * a vlastního rozšířeného.
     *
     * @return Informace, zda testy proběhly bez chyb
     */
    public boolean executeExtended()
    {
        //Otestuje základní úspěšný a základní chybový scénář
        ScenarioTest.Summary summary = ScenarioTest.testBasicScenarios(manager);

        //Vytvoří doplňující kontrolní scénář
        Scenario accessoryTests = scenarioWithAccessoryTests(summary);

        boolean errorOccured = false;
        errorOccured |= executeScenario(0);
        errorOccured |= executeScenario(1);
        errorOccured |= executeScenario(accessoryTests);

        //Vytvoří scénář pro vyřešení zadání obhajoby
//        Scenario obhajoba  =
////                scénářObhajoby_4(souhrn);
//                scénářObhajoby_6(souhrn);
//        sChybou |= executeScenario(obhajoba);

        String zpráva = N_HASHES_N +
            "Při testu programu prostřednictvím na počátku uvedených scénářů\n"+
            (errorOccured ? "BYLY NALEZENY JISTÉ CHYBY\n" +
                            "Zjištěne chyby jsou uvedeny vždy ve výpisu\n" +
                            "na konci zprávy o průběhu hry podle daného scénáře"
                          : "nebyla nalezena žádná chyba.") +
            N_HASHES_N;
        DBG.info(zpráva);
        return errorOccured;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Vrátí hlavičku testu zadaného scénáře.
     *
     * @param scenario
     * @return String s hlavičkou testu zadaného scénáře
     */
    private String header(Scenario scenario)
    {
        String text = N_HASHES_N +
                 "##### Čas:    " + new Date() +
               "\n##### Hra:    " + gamename   +
               "\n##### Autor:  " + xname      + " - " + author +
               "\n##### Scénář: " + scenario.getName() +
               N_HASHES_N;
        return text;
    }


    /***************************************************************************
     * Nastaví hodnotu atributu {@link #notVisited} podle informací získaných
     * z testu scénáře a dotazem na hru.
     *
     * @param scenario Scénář, kterým testujeme hru
     */
    private void initializeNonVisited(Scenario scenario)
    {
        ScenarioTest.Summary summary = ScenarioManagerTest.summary;
        notVisited = new HashSet<>(summary.mentionedPlaces);
        Collection<? extends IPlace> allPlaces = game.getAllPlaces();
        for (IPlace place : allPlaces) {
            String name = place.getName().toLowerCase();
            notVisited.add(name);
        }
    }


    /***************************************************************************
     * Prověří, jestli testovaná hra nehlásí před startem svoji spuštěnost
     * nebo před koncem svoji ukončenost.
     *
     * @param start {@code true} =Test spuštěnosti před zadáním prvního příkazu,
     *              {@code false}=Test ukončenosti v dalším průběhu hry
     * @return Aktuální stav hry &ndash;
     *         {@code true} = spustitelná, {@code false} = nespustitelná, běží
     */
    @SuppressWarnings("AssignmentToMethodParameter")
    private boolean verifyIsReady(boolean start)
    {
        if (start) {
            if (game.isAlive()) {
                ERROR_T("Hra se před spuštěním tváří jako spuštěná");
            }
            else {
                start = false;
            }
        }
        else if (! game.isAlive()) {
            ERROR("Předčasný konec hry");
        }
        return start;
    }


    /***************************************************************************
     * Přidá na konec dodaného seznamu kroků scénáře (předpokládáme prázdného)
     * testovací kroky, které odstartují hru, ukončí ji a opět odstartují.
     * Tím prověří reakci hry na pokus o její restart.
     *
     * @param summary Souhrn informací o hře získaný z dříve testovaných scénářů
     * @param initialSpace  Prostor, v němž hra začíná
     * @param initialStep   Nultý, tj. startovní (bezejmenný) krok scénáře
     * @param steps         Seznam kroků budoucího scénáře, na jehož konec
     *                      metoda přidá kroky vytvořené v této metodě
     * @return Vrátí krok scénáře, který explicitně ukončí hru,
     *         aby mohl být ještě jednou zařazen na konec scénáře.
     */
    private ScenarioStep addRestartOfGame(Summary summary, String initialSpace,
                            ScenarioStep initialStep, List<ScenarioStep> steps)
    {
        //Test restartu hry
        ScenarioStep finalStep = new ScenarioStep(99,
                     TypeOfStep.tsEND,
                     summary.basicCommands.get(TypeOfStep.tsEND),
                     ScenarioStep.IGNORED_MESSAGE,
                     initialSpace,
                     initialStep.neighbors,
                     initialStep.objects,
                     initialStep.bag);
        //Test restartovatelnosti hry
        steps.add(initialStep);
        steps.add(finalStep);
        steps.add(initialStep);
        return finalStep;
    }


    /***************************************************************************
     * Přidá na konec dodaného seznamu kroků scénáře testovací krok,
     * v němž prověří reakci hry na pokus o zadání základních příkazů
     * pro přechod do jiného prostoru a pro zvednutí a položení objektu,
     * kterým bude chybět příslušný parametr.
     *
     * @param summary Souhrn informací o hře získaný z dříve testovaných scénářů
     * @param initialSpace  Prostor, v němž hra začíná
     * @param initialStep   Nultý, tj. startovní (bezejmenný) krok scénáře
     * @param steps         Seznam kroků budoucího scénáře, na jehož konec
     *                      metoda přidá kroky vytvořené v této metodě
     */
    private void addArgumentLessStatement(Summary summary, String initialSpace,
                            ScenarioStep initialStep, List<ScenarioStep> steps)
    {
        TypeOfStep[] correctTypes = {tsMOVE,    tsPUT_DOWN,    tsPICK_UP};
        TypeOfStep[] enteredTypes = {tsMOVE_WA, tsPUT_DOWN_WA, tsPICK_UP_WA};
        //Kroky s bezparametrickými příkazy
        for (int i=0;   i < correctTypes.length;   i++) {
            String     command    =summary.basicCommands.get(correctTypes[i]);
            TypeOfStep typeOfStep = enteredTypes[i];

            ScenarioStep step = new ScenarioStep(99,
                                                 typeOfStep,
                                                 command,
                                                 ScenarioStep.IGNORED_MESSAGE,
                                                 initialSpace,
                                                 initialStep.neighbors,
                                                 initialStep.objects,
                                                 initialStep.bag);
            steps.add(step);
        }
    }


    /***************************************************************************
     * Přidá na konec dodaného seznamu kroků scénáře testovací krok,
     * v němž prověří reakci hry na pokus o přechod do aktuální prostoru.
     *
     * @param summary Souhrn informací o hře získaný z dříve testovaných scénářů
     * @param initialSpace  Prostor, v němž hra začíná
     * @param initialStep   Nultý, tj. startovní (bezejmenný) krok scénáře
     * @param steps         Seznam kroků budoucího scénáře, na jehož konec
     *                      metoda přidá kroky vytvořené v této metodě
     */
    private void addEnteringActualSpace(Summary summary, String initialSpace,
                            ScenarioStep initialStep, List<ScenarioStep> steps)
    {
        //Přechod do aktuální místnosti
        String přejdiDo = summary.basicCommands.get(TypeOfStep.tsMOVE) + " ";
        ScenarioStep krok = new ScenarioStep(99,
               TypeOfStep.tsBAD_NEIGHBOR,
               přejdiDo + initialSpace,
               ScenarioStep.IGNORED_MESSAGE,
               initialSpace,
               initialStep.neighbors,
               initialStep.objects,
               initialStep.bag);
        steps.add(krok);
    }


    /***************************************************************************
     * Přidá na konec dodaného seznamu kroků scénáře testovací krok,
     * v němž prověří reakci hry na pokus o přechod do prostoru,
     * který není sousedním prostorem aktuálního prostoru.
     *
     * @param summary Souhrn informací o hře získaný z dříve testovaných scénářů
     * @param initialSpace  Prostor, v němž hra začíná
     * @param initialStep   Nultý, tj. startovní (bezejmenný) krok scénáře
     * @param steps         Seznam kroků budoucího scénáře, na jehož konec
     *                      metoda přidá kroky vytvořené v této metodě
     */
    private void addEnteringAFarSpace(Summary summary, String initialSpace,
                            ScenarioStep initialStep, List<ScenarioStep> steps)
    {
        //Přechod do nesousedního prostoru
        for (int i=0;   i < initialStep.neighbors.length;   i++) {
            initialStep.neighbors[i] = initialStep.neighbors[i].toLowerCase();
        }
        String destination = null;
        Collection<String> neighbors = Arrays.asList(initialStep.neighbors);
        for (String place : summary.mentionedPlaces) {
            if (! place.equalsIgnoreCase(initialStep.place)  &&
                ! neighbors.contains(place.toLowerCase()))
            {
                destination = place;
            }
        }
        if (destination != null) {
            String goInto = summary.basicCommands.get(TypeOfStep.tsMOVE) + " ";
            ScenarioStep step = new ScenarioStep(99,
                   TypeOfStep.tsBAD_NEIGHBOR,
                   goInto + destination,
                   ScenarioStep.IGNORED_MESSAGE,
                   initialSpace,
                   initialStep.neighbors,
                   initialStep.objects,
                   initialStep.bag);
            steps.add(step);
        }
    }


    /***************************************************************************
     * Definuje pro hru doplňovací scénář, který prověří:
     * <ul>
     *   <li>Hra je schopna po ukončení opět nastartovat (restart hry).</li>
     *   <li>Hra korektně zpracuje zadání základních příkazů
     *       pro přesun, položení a zvednutí objektu
     *       v nichž nebudou zadány parametry.</li>
     *   <li>Hráč se chce přesunout do aktuálního prostoru.</li>
     *   <li>Hráč se chce přesunout do prostoru, který není sousedem
     *       aktuálního prostoru</li>
     * </ul>
     *
     * @param summary Souhrn informací o hře získaný z dříve testovaných scénářů
     * @return Požadovaný doplňovací scénář
     */
    private Scenario scenarioWithAccessoryTests(ScenarioTest.Summary summary)
    {
        List<ScenarioStep> steps  = new ArrayList<>();

        ScenarioStep initialStep = summary.startStep;
        String       startPlace  = initialStep.place;

        ScenarioStep finalStep = addRestartOfGame
                                (summary, startPlace, initialStep, steps);
        addArgumentLessStatement(summary, startPlace, initialStep, steps);
        addEnteringActualSpace  (summary, startPlace, initialStep, steps);
        addEnteringAFarSpace    (summary, startPlace, initialStep, steps);

        steps.add(finalStep);

        Scenario scenario = new Scenario("Kontrolní", TypeOfScenario.scGENERAL,
                       manager, steps.toArray(new ScenarioStep[steps.size()]));

        ScenarioTest test = new ScenarioTest();
        test.test(scenario);

        return scenario;
    }



//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        GameTRunTest x = new GameTRunTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
