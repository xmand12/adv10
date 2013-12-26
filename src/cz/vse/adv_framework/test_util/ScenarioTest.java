/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.TypeOfStep;
import cz.vse.adv_framework.scenario.TypeOfScenario;

import cz.vse.adv_framework.utilities.CompareIgnoreCase;

import java.text.Normalizer;

import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import static cz.vse.adv_framework.scenario.TypeOfStep.*;

import static cz.vse.adv_framework.utilities.FormatStrings.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/*******************************************************************************
 * Instance třídy <b>{@code ScenarioTest}</b> testují scénáře hry,
 * tj. prověřují, nakolik scénář odpovídá požadavkům na něj kladeným.
 * Testují však pouze obsah scénáře,
 * tj. nepokouší se jej aplikovat na hru a testovat tak hru.
 * To mají na starosti instance třídy {@link ScenarioSimulator}.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000,  0.0.2008
 */
public class ScenarioTest extends ATest implements ITest<Scenario>
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Minimální požadovaný počet kroků základního úspěšného scénáře. */
    private static final int REQUESTED_STEPS_NUM = 10;

    /** Minimální počet definovaných akcí.
     *  Akce lišící se pouze v parametrech (např. "jdi sem", "jdi tam")
     *  jsou považovány za jednu akci. */
    private static final int REQUESTED_COMMANDS_NUM = 9;//10;   //PŘÍKAZY.size() + 4;

    /** Minimální počet navštívených prostorů. */
    private static final int REQUESTED_VISITED_NUM = 4;

    /** Minimální počet prostorů ve hře. */
    private static final int REQUESTED_SEEN_NUM  = 6;

    /** Komparátor porovnávající dva řetězce a ignorující velikost znaků. */
    private static final CompareIgnoreCase CIC = CompareIgnoreCase.CIC;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    //-- Následující konstanty si instance pamatuje mezi jednotlivými testy
    //   scénářů a používá je kke kontrole duplicit ----------------------------

    /** Mapa uchovávající typ každého zadaného příkazu.
     *  Slouží ke kontrole konzistentnosti používání příkazů,
     *  tj. že nebude stejný příkaz omylem použit v různých typech kroků.
     *  Názvy příkazů jsou převedeny na velká písmena. */
    private Map<String, TypeOfStep> command2type = new HashMap<>();


    /** Mapa převádějící typ kroku na název akce, kterou krok realizuje.
     *  Slouží ke kontrole konzistentnosti používání akcí,
     *  tj. že pro daný typ standardního kroku nebudou použité různé příkazy.
     *  Názvy příkazů jsou převedeny na velká písmena. */
    private Map<TypeOfStep,String> type2command =
                                   new EnumMap<>(TypeOfStep.class);



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    //-- Texty zapamatované ze všech scénářů testovaných touto instancí --------

        /** Množina akcí použitých v dosud prověřených scénářích. */
        private Set<String> allEnteredCommands = new TreeSet<>();

        /** Množina názvů prostorů zmíněných v dosud prověřených scénářích
         *  (např. jako sousedé). */
        private Set<String> allMentionedPlaces = new TreeSet<>();

        /** Množina názvů objektů zmíněných v navštívených či
         * zmíněných prostorech v dosud prověřených scénářích. */
        private Set<String> allDiscoveredObjects = new TreeSet<>();

        /** Množina názvů objektů zmíněných v navštívených či
         * zmíněných prostorech v dosud prověřených scénářích. */
        private Set<String> allSeenObjects = new TreeSet<>();


    //-- Proměnné zapamatované pro testovaný scénář ----------------------------

        /** Testovaný scénář. */
        private Scenario scenarioInTest;

        /** Označení testu zapisované do preambule a postambule. */
        private String descriptonOfScenarioInTest;

        /** Množina akcí použitých v testu. */
        private Set<String> enteredCommands = new TreeSet<>();

        /** Množina názvů navštívených prostorů. */
        private Set<String> visitedPlaces = new TreeSet<>();

        /** Množina názvů prostorů zmíněných ve scénáři (např. jako sousedé). */
        private Set<String> mentionedPlaces = new TreeSet<>();

        /** Množina použitých objektů. */
        private Set<String> seenObjects = new TreeSet<>();

        /** Doposud nezrealizované typy akcí.*/
        private Set<TypeOfStep> notUsedCommands;

        /** Počet doposud prověřených kroků scénáře. */
        private int numOfSteps;

        /** Atribut, v němž si metody předávají informaci o tom,
         *  zda daný scénář vyhovuje požadavkům.
         *  Při testu každého scénáře je atribut inicializován v metodě
         *  {@link #initialization()}. */
        private boolean scenarioOK;


    //-- Proměnné použité pro test jednoho kroku -------------------------------

//        /** Příznak toho, že byl zadán startovací prázdný příkaz. */
//        private boolean startovacíAkce;

        /** {@code true} před startem prvního kroku, anebo byl-li již vydán
         *  příkaz k ukončení hry a nebyla ještě odstartována hra další. */
        private boolean finished;

        /** Posledně vyhodnocovaný krok scénáře. */
        private ScenarioStep previousStep;

        /** Aktuálně vyhodnocovaný krok scénáře. */
        private ScenarioStep currentStep;

        /** Jednotlivá slova příkazu v testovaném kroku scénáře. */
        private String[] wordsInCommand;



//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Otestuje zadané scénáře zadaného správce a vrátí přepravku
     * se souhrnnou informaci o základních charakteristikách hry
     * odvozenou z testovaných scénářů.
     *
     * @param manager Správce, jehož scénáře se budou testovat
     * @return Přepravka se souhrnnou informaci o charakteristikách hry
     */
    public static Summary testAllScenarios(AScenarioManager manager)
    {
        int[] indexes = new int[manager.getSize()];
        for (int i=0;   i < indexes.length;   i++) {
            indexes[i] = i;
        }
        return testGivenScenarios(manager, indexes);
    }


    /***************************************************************************
     * Otestuje základní úspěšný a základní chybový scénář
     * zadaného správce a vrátí přepravku se souhrnnou informací
     * o základních charakteristikách hry odvozenou z testovaných scénářů.
     *
     * @param manager Správce, jehož scénáře se budou testovat
     * @return Přepravka se souhrnnou informaci o charakteristikách hry
     */
    public static Summary testBasicScenarios(AScenarioManager manager)
    {
        return testGivenScenarios(manager, 0, 1);
    }


    /***************************************************************************
     * Otestuje zadané scénáře zadaného správce a vrátí přepravku
     * se souhrnnou informaci o základních charakteristikách hry
     * odvozenou z testovaných scénářů.
     *
     * @param manager  Správce, jehož scénáře se budou testovat
     * @param indexes  Indexy scénářů, které se mají otestovat
     * @return Přepravka se souhrnnou informaci o charakteristikách hry
     */
    @SuppressWarnings("empty-statement")
    public static Summary testGivenScenarios(AScenarioManager manager,
                                             int... indexes)
    {
        ScenarioTest tester = new ScenarioTest();
        boolean      ok     = true;

//        ScenarioManagerTest.writeInvitation(manager);

        for (int i : indexes) {
            Scenario scenario = manager.getScenario(i);
            ok &= tester.verify(scenario);
        }
        Scenario happy = manager.getHappyScenario();

        ScenarioStep start = happy.iterator().next();
        ScenarioStep stop  = start;
        for (ScenarioStep ss : happy) {
            stop = ss;
        }

        return new Summary(ok,
                           tester.allMentionedPlaces,
                           tester.allEnteredCommands,
                           tester.allSeenObjects,
                           start, stop,
                           tester.type2command);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Třída vystačí s implicitním konstruktorem.
     */
    public ScenarioTest()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================

    /***************************************************************************
     * Vrátí mapu se základními příkazy indexovanými svým typem.
     *
     * @return Mapa se základními příkazy indexovanými svým typem
     */
    public Map<TypeOfStep, String> getBasicCommands()
    {
        if ((type2command.get(TypeOfStep.tsMOVE   ) == null)  ||
            (type2command.get(TypeOfStep.tsPUT_DOWN) == null)  ||
            (type2command.get(TypeOfStep.tsPICK_UP) == null)  ||
            (type2command.get(TypeOfStep.tsEND    ) == null) )
        {
            ERROR_T("Doposud otestované scénáře ještě neposkytly " +
                    "kompletení sadu základních příkazů");
        }
        return type2command;
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Vytiskne chybové hlášení a označí celý scénář za nevyhovující.
     *
     * @param format    Formát tisku chybového hlášení. Bude ještě doplněn
     *                  společnou úvodní a závěrečnou sekvencí.
     * @param arguments Případné další parametry k tisku
     */
    @Override
    protected void ERROR(String format, Object... arguments)
    {
        scenarioOK = false;
        super.ERROR(format, arguments);
    }


    /***************************************************************************
     * Otestuje zadaný scénář a zapíše výsledek na standardní výstup.
     *
     * @param scenario Prověřovaný scénář
     */
    @Override
    public void test(Scenario scenario)
    {
        verify(scenario);
    }


    /***************************************************************************
     * Prověří zadaný scénář, zapíše výsledek na standardní výstup
     * a vrátí logickou hodnotu oznamující, zda scénář úspěšně prošel testem.
     *
     * @param scenario  Prověřovaný scénář
     * @return Informace, zda scénář úspěšně prošel testem
     */
    public boolean verify(Scenario scenario)
    {
        if (TypeOfScenario.scDEMO.equals(scenario.getType())) {
            throw new IllegalArgumentException(
                    "\nDemonstrační scénář není možno testovat");
        }

        scenarioInTest = scenario;
        initialization();
        verifyAuthor();

        boolean start = true;
        for (ScenarioStep step : scenario) {
            currentStep = step;
            if (start) {
                start = false;
                if (TypeOfScenario.scMISTAKES.equals(scenario.getType())  &&
                    (step.typeOfStep != TypeOfStep.tsNOT_START))
                {
                    ERROR("Chybový scénář musí začínat testem definujícím " +
                          "reakci na \nnestartovní (= neprázdný) příkaz " +
                          "zadaný neběžící hře: %s",
                          currentStep);
                }
            }
            if (finished  &&
                ! (TypeOfStep.tsSTART    .equals(currentStep.typeOfStep) ||
                   TypeOfStep.tsNOT_START.equals(currentStep.typeOfStep)) )
            {
                    ERROR("Zadaný krok scénáře je před začátkem nebo " +
                          "za koncem hry\ntj. hra ještě není odstartovaná " +
                          "anebo je již ukončená %s",
                          currentStep);
                    break;
            }
            processStep();
            previousStep = currentStep;
        }
        if (!finished   &&
            TypeOfScenario.scMISTAKES.equals(scenario.getType()))
        {
            ERROR("Chybový scénář hru explicitně neukončil.");
        }
        switch (scenario.getName()) {
            case AScenarioManager.HAPPY_SCENARIO:
                verifyCompleteness(scenario, TypeOfStep.REGULAR_COMMANDS);
                break;
            case AScenarioManager.MISTAKE_SCENARIO:
                verifyCompleteness(scenario, TypeOfStep.MISTAKE_COMMAND);
                break;
        }
        finalization();
        return scenarioOK;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Vrátí parametr zadaného příkazu.
     *
     * @return Parametr zadaného příkazu
     */
    private String getCommandArgument()
    {
        if (wordsInCommand.length < 2) {
            ERROR("Příkaz «%s» vyžadující parametr byl zadán bez parametrů",
                  wordsInCommand[0]);
            return "";
        }
        return wordsInCommand[1];
    }


    /***************************************************************************
     * Inicializuje atributy používané v průběhu testu
     * a vytiskne zprávu o zahájení testu daného scénáře.
     */
    private void initialization()
    {
        AScenarioManager ss = scenarioInTest.getManager();
        descriptonOfScenarioInTest =
               "Autor:         " + ss.getAuthorName() +
             "\nTřída správce: " + ss.getClass() +
             "\nScénář:        " + scenarioInTest.getName();
        enteredCommands.clear();
        visitedPlaces  .clear();
        mentionedPlaces.clear();
        seenObjects    .clear();
        notUsedCommands = EnumSet.allOf(TypeOfStep.class);
        numOfSteps   = 0;
        scenarioOK   = true;
        finished     = true;
        previousStep = null;
        DBG.info("{0}\n===== Start testu ===== {1,date} - {1,time}",
                 N_HASHES_N + descriptonOfScenarioInTest, new Date());
    }


    /***************************************************************************
     * Připraví a vytiskne závěrečnou zprávu o testu zadaného scénáře.
     */
    private void finalization()
    {
        allEnteredCommands.addAll(enteredCommands);
        allMentionedPlaces.addAll(mentionedPlaces);
        allSeenObjects    .addAll(seenObjects);

        //Zjistí zmíněné, ale nenavštívené prostory
        Set<String> notVisited = discoverNotVisitedPlaces();

        //Zjistí, které typy akcí nebyly použity a které měly být použity
        Set<String> notUsed = discoverNotUsedCommands();

        showResults(notVisited, notUsed);
    }


    /***************************************************************************
     * Ověří, že udávané jméno autora odpovídá zadaným konvencím,
     * tj. že obsahuje nejméně dvě slova, první z nich je velkými písmeny
     * a druhé začíná velkým písmenem.
     *
     * @return Vektor stringů s jednotlivými slovy jména autora
     *         zbavenými diakritiky
     */
    private String[] verifyAuthor()
    {
        //Jméno zbavíme diakritiky pro snazší následnou kontrolu
        String authorASCII = Normalizer.normalize( //Java 6+
               scenarioInTest.getManager().getAuthorName(),
               Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        String[] wordsInName = authorASCII.split(" ");
        String[] check       = authorASCII.split("\\s+");
        if (wordsInName.length != check.length) {
            ERROR("Špatně použité bílé znaky ve jméně autora.");
        }
        if ((wordsInName   .length    < 2)  ||
            (wordsInName[0].length() == 0)  ||
            (wordsInName[1].length() == 0))
        {
            ERROR("Autor nemá uvedeno příjmení + křestní jméno");
        }
        String surname   = wordsInName[0];
        String firstName = wordsInName[1];
        if (! surname.matches("[A-Z]+")) {
            ERROR("Prvním slovem jména autora není příjmení " +
                  "zapsané velkými písmeny");
        }
        if (! firstName.matches("[A-Z][a-z]+")) {
            ERROR("Druhé slovo jména autora nemá " +
                  "první písmeno velké a ostatní malá");
        }
        return wordsInName;
    }


    /***************************************************************************
     * Ověří, zda zadaný scénář obsahuje všechny typy akcí
     * obsažené v zadané množině.
     *
     * @param scenario Prověřovaný scénář
     * @param required Požadované typy testů
     */
    private void verifyCompleteness(Scenario scenario, Set<TypeOfStep> required)
    {
        EnumSet<TypeOfStep> notUsed = EnumSet.copyOf(required);
        for (ScenarioStep step : scenario) {
            TypeOfStep typKroku = step.typeOfStep;
            notUsed.remove(typKroku);
        }
        if (notUsed.size() > 0) {
            scenarioOK = false;
            DBG.info("Nepokryté typy akcí: {0}", notUsed);
        }
    }


    /***************************************************************************
     * Ohlásí chybu, protože demonstrační typ kroku se v testovacím scénáři
     * nesmí objevit.
     */
    private void verifyType_DEMO()
    {
        ERROR("Demonstrační typ kroku nepatří do testovacího scénáře\n%s",
              currentStep);
    }


    /***************************************************************************
     * Nastaví příznak konce, aby bylo možno ověřit,
     * že zadaný krok krok testu je skutečně poslední
     * (každý další krok by způsobil chybu),
     * a současně ověří, že se zadáním konce hry nezmění ostatní stavy hry.
     */
    private void verifyType_END()
    {
        verifyCommandName(tsEND);
        verifyEqualityOfAllFields();
        finished = true;
    }


    /***************************************************************************
     * Ověří, že stav před zadáním příkazu a po jeho vykonání je shodný.
     */
    private void verifyType_HELP()
    {
        verifyCommandName(tsHELP);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří, že byl zadána příkaz pro položení objektu
     * a že zadaný objekt v batohu doopravdy není.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty tomto v prostoru a ani obsah batohu.
     */
    private void verifyType_NOT_IN_BAG()
    {
        verifyCommandName(tsPUT_DOWN);
        verifyEqualityOfAllFields();
        if (verifyArrayContent(currentStep.bag, getCommandArgument(), true)) {
            ERROR("Zadaný objekt v batohu je\n%s", currentStep);
        }
    }


    /***************************************************************************
     * Ověří, že byl zadána příkaz pro zvednutí objektu
     * a že v aktuálním prostoru zadaný objekt doopravdy není.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objects v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_BAD_OBJECT()
    {
        verifyCommandName(tsPICK_UP);
        verifyEqualityOfAllFields();
        if (verifyArrayContent(currentStep.objects, getCommandArgument(),true)){
            ERROR("Zadaný předmět v aktuálním prostoru je\n%s", currentStep);
        }
    }


    /***************************************************************************
     * Ověří reakci hry na pokus přejít do prostoru,
     * který v daném okamžiku není sousedem aktuálního prostoru.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_BAD_NEIGHBOR()
    {
        verifyCommandName(tsMOVE);
        verifyEqualityOfAllFields();
        if (verifyArrayContent(currentStep.neighbors, getCommandArgument(),true)){
            ERROR("Zadaného souseda místnost má\n%s", currentStep);
        }
    }


    /***************************************************************************
     * Ověří, že tuto akci lze konzistentně zařadit mezi nestandardní.
     * Jiné požadavky na tento druh příkazu kladeny nejsou.
     */
    private void verifyType_NON_STANDARD()
    {
        verifyCommandName(tsNON_STANDARD);
    }


    /***************************************************************************
     * Ověří reakci hry na neznámý příkaz.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_UNKNOWN()
    {
        verifyCommandName(tsUNKNOWN);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří reakci hry na zadaný prázdný text.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_EMPTY()
    {
        verifyCommandName(tsEMPTY);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří reakci na pokus zvednout předmět v situaci, kdy je batoh již plný.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_BAG_FULL() {
        verifyCommandName(tsPICK_UP);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří reakci na položení předmětu.
     * Při zpracování příkazu se nesmí změnit aktuální prostor.
     */
    private void verifyType_PUT_DOWN() {
        verifyCommandName(tsPUT_DOWN);
        verifySameSpace();
        verifyFieldEquality(Pair.NEIGHBORS);
        String položený = getCommandArgument();
        if (položený.length() > 0) { //Byl zadán parametr
            verifyAbsence(Pair.OBJECTS, položený, false);
            verifyAbsence(Pair.BAG,   položený, true);
        }
    }


    /***************************************************************************
     * Ověří reakci na příkaz k položení předmětu zadaný bez parametru.
     * Při zpracování příkazu se nesmí nic změnit.
     */
    private void verifyType_PUT_DOWN_WA()
    {
        verifyCommandName(tsPUT_DOWN);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří reakci na přesun hráče z prostoru do prostoru.
     * Při zpracování příkazu se nesmí změnit obsah batohu.
     */
    private void verifyType_MOVE() {
        verifyCommandName(tsMOVE);
        verifyPlaceReached();
        verifyFieldEquality(Pair.BAG);
    }


    /***************************************************************************
     * Ověří reakci na příkaz k přesunu do jiného prostoru zadaný bez parametru.
     * Při zpracování příkazu se nesmí nic změnit.
     */
    private void verifyType_MOVE_WA()
    {
        verifyCommandName(tsMOVE);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Všechny kontroly tohoto typu kroku již byl provedeny v metodě
     * {@link #test(Scenario)}
     */
    private void verifyType_DIALOG()
    {
    }


    /***************************************************************************
     * Ověří, že tento příkaz byl zadán jako před spuštěním hry
     * a že jeho název není prázdný.
     */
    private void verifyType_NOT_START()
    {
        if (! finished) {
            ERROR("Předchozí hra ještě nebyla ukončena.\n" +
                  "Tento typ testovacího kroku se vkládá před start hry.");
        }
        else if ("".equals(currentStep.command))  {
            ERROR("Příkaz v testu špatného odstartování hry nesmí být prázdný"
                  + ".");
        }
    }


    /***************************************************************************
     * Ověří, že tento příkaz byl zadán jako první a že jeho název je prázdný.
     */
    private void verifyType_START()
    {
        if (! finished) {
            ERROR("Předchozí hra ještě nebyla ukončena.\n" +
                  "Nová hra může odstartovat až po ukončení té předchozí.");
        }
        else if (! "".equals(currentStep.command))  {
            ERROR("Úvodní akce každého scénáře musí mít prázdný název \n" +
                  "a musí definovat zprávu a stav hry po odstartování,"
                  + ".");
        }
        else {
            finished = false;
        }
    }


    /***************************************************************************
     * Ověří reakci na pokus zvednout nezvednutelný objekt.
     * Při zpracování příkazu se nesmí změnit
     * aktuální prostor ani objekty v tomto prostoru a ani obsah batohu.
     */
    private void verifyType_UNMOVABLE()
    {
        verifyCommandName(tsPICK_UP);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří reakci na příkaz ke zvednutí objektu.
     * Při zpracování příkazu se nesmí změnit aktuální prostor.
     */
    private void verifyType_PICK_U()
    {
        verifyCommandName(tsPICK_UP);
        verifySameSpace();
        verifyFieldEquality(Pair.NEIGHBORS);
        String zvednutý = getCommandArgument();
        if (zvednutý.length() > 0) { //Byl zadán parametr
            verifyAbsence(Pair.OBJECTS, zvednutý, true);
            verifyAbsence(Pair.BAG,    zvednutý, false);
        }
    }


    /***************************************************************************
     * Ověří reakci na příkaz k zvednutí předmětu zadaný bez parametru.
     * Při zpracování příkazu se nesmí nic změnit.
     */
    private void verifyType_PICK_UP_WA()
    {
        verifyCommandName(tsPICK_UP);
        verifyEqualityOfAllFields();
    }


    /***************************************************************************
     * Ověří, že po vykonání příkazu hráč skončil v prostoru,
     * který byl zadán jako parametr.
     */
    private void verifyPlaceReached()
    {
        String place = getCommandArgument();
        if ((place.length() > 0)   && //Byl zadán parametr
            (! place.equalsIgnoreCase(currentStep.place)))
        {
            ERROR("Hráč se nepřesunul do prostoru zadaného v příkazu");
        }
    }


    /***************************************************************************
     * Ověří, že název právě ohlášené akce je konzistentní
     * s doposud zadanými akcemi a přidá jej do seznamu názvů
     * spolu s typem kroku pro kontroly konzistence akcí zadaných v budoucnu.
     *
     * @param typeOfStep Typ korektně provedeného kroku odpovídající zadanému
     *                   typu kroku (např. chybovému typu {@code tsNOT_IN_BAG}
     *                   odpovídá "řádný" typ kroku {@code tsPUT_DOWN}).
     */
    private void verifyCommandName(TypeOfStep typeOfStep)
    {
        String name;
        if (wordsInCommand.length >0) {
            name = wordsInCommand[0];
        }
        else {
            name = "";
        }
        String upperName = name.toUpperCase();
        TypeOfStep type  = command2type.get(upperName);

        if (type == null)  {
            command2type.put(upperName,   typeOfStep);
            if ((type2command.get(typeOfStep) != null) &&
                (typeOfStep != TypeOfStep.tsNON_STANDARD)  &&
                (typeOfStep != TypeOfStep.tsUNKNOWN) )
            {
                String rival = type2command.get(typeOfStep);
                ERROR("Pro akci typu %s byl již použit příkaz %s\n" +
                      "    nyní se pokoušíte použít příkaz %s",
                      typeOfStep, rival, name);
                return;                                         //==========>
            }
            type2command.put(typeOfStep, upperName);
        }
        else if (type != typeOfStep)  {
            ERROR("Příkaz «%s» byl již použit pro akci typu %s", name, type);
        }
    }


    /***************************************************************************
     * Zjistí, zda zadané pole textů obsahuje/neobsahuje zadaný text.
     *
     * @param array          Prověřované pole textů
     * @param text           Text, jehož přítomnost zjišťujeme
     * @param shouldContain  {@code true} ptáme-li se, zda pole text obsahuje,
     *                       {@code false} zjišťujeme-li, zda text neobsahuje
     * @return Odpověď na zadanou otázku
     */
    private boolean verifyArrayContent(String[] array, String text,
                                       boolean  shouldContain)
    {
        boolean found = false;
        for (String s : array)  {
            if (s.equalsIgnoreCase(text)) {
                found = true;
                break;
            }
        }
        return (found == shouldContain);
    }


    /***************************************************************************
     * Ověří, že se texty v zadaných polích (tj. názvy pojmenovaných objektů)
     * v minulém a tomto testovacím kroku shodují
     * (např. že se při přechodu do jiného prostoru nezměnil stav batohu).
     *
     * @param pairs Přepravky s porovnávanými poli textů
     *              v minulém a tomto kroku testu
     */
    private void verifyFieldEquality(Pair... pairs)
    {
        for (Pair pair : pairs) {
            verifyFieldEquality(pair);
        }
    }


    /***************************************************************************
     * Ověří, že se seznamy názvů v zadané přepravce
     * v minulém a tomto kroku testu shodují.
     *
     * @param pair Přepravka s porovnávanými poli názvů
     *             v minulém a tomto kroku testu
     */
    private void verifyFieldEquality(Pair pair)
    {
        Names    names  = pair.pair(this);
        String[] last   = names.last;
        String[] actual = names.current;
        Arrays.sort(last, CIC);
        Arrays.sort(actual,   CIC);

        boolean equals = true;
        if (last.length != actual.length) {
            equals = false;
        }
        else {
            for (int i=0;   i < actual.length;   i++) {
                if (! last[i].equalsIgnoreCase(actual[i])) {
                    equals = false;
                    break;
                }
            }
        }
        if (equals)  { return; }

        ERROR("Seznamy objektů typu %s v minulém a tomto kroku " +
              "si neodpovídají.\nMinule: %s\nNyní:   %s",
              pair.name(), Arrays.asList(last), Arrays.asList(actual));
    }


    /***************************************************************************
     * Ověří, že se v minulém a tomto testovacím kroku shoduje
     * aktuální prostor a s ním i názvy v polích reprezentujících
     * sousedy prostoru, předměty v prostoru a obsah batohu.
     */
    private void verifyEqualityOfAllFields()
    {
        verifySameSpace();
        verifyFieldEquality(Pair.NEIGHBORS, Pair.OBJECTS, Pair.BAG);
    }


    /***************************************************************************
     * Ověří, že se nezměnil aktuální prostor.
     */
    private void verifySameSpace()
    {
        if (! previousStep.place.equalsIgnoreCase(currentStep.place)) {
            ERROR("Při vykonvání tohoto příkazu se nesmí změnit " +
                  "aktuální prostor.");
        }
    }


    /***************************************************************************
     * Ověří, že se texty v zadaných polích v minulém a tomto zkušebním
     * kroku shodují s výjimkou zadaného názvu, který v jedné verzi chybí.
     *
     * @param pair    Přepravka s dvojicí polí s názvy entit platnými
     *                v minulém a současném kroku scénáře
     * @param absent  Text, který má v jednom z polí chybět
     * @param nowLess {@code true} má-li chybět v současném kroku,
     *                {@code false} má-li chybět v minulém kroku
     */
    private void verifyAbsence(Pair pair, String absent, boolean nowLess)
    {
        Names    names  = pair.pair(this);
        String[] last   = names.last;
        String[] actual = names.current;
        Arrays.sort(last, CIC);
        Arrays.sort(actual,  CIC);

        boolean agree = true;
        int difference = nowLess ? -1 : 1;
        try {
            if ((actual.length - last.length)  !=  difference) {
                agree = false;
            }
            else if ((actual.length == 0)  ||  (last.length == 0)) {
                agree = absent.equalsIgnoreCase(nowLess  ?  last[0]
                                                          :  actual[0]);
            }
            else {
                for (int m=0, n=0;
                    (n < actual.length)  &&  (m < last.length);
                    m++, n++)
                {
                    if (! last[m].equalsIgnoreCase(actual[n])) {
                        String divergence;
                        if (nowLess) {
                            divergence = last[m];
                            n--;
                        }
                        else {
                            divergence = actual[n];
                            m--;
                        }
                        agree = absent.equalsIgnoreCase(divergence);
                    }
                }
            }
            if (agree)  {
                return;                                 //==========>
            }
        } catch(Exception e) {
            //Nepotřebuje ošetřit - prostě ohlásí chybu
        }
        ERROR("Rozdíly v seznamech objektů typu %s mezi minulým a tímto " +
              "krokem neodpovídají akci typu %s\nMá chybět: %s\n" +
              "Minule: %s\nNyní:%s",
              pair.name(), this.currentStep.typeOfStep,
              absent, Arrays.asList(last), Arrays.asList(actual));
    }


    /***************************************************************************
     * Vytiskne informaci o splnění požadovaného počtu objektů daného typu.
     * U scénáře zjišťuje počet kroků, použitých příkazů,
     * navštívených místností, zmíněných místností apod.
     *
     * @param anounced Počet objektů vyskytujících se ve scénáři
     * @param required Požadovaný minimální počet objektů
     * @return Text zprávy oznamující nalezený počet objektů doplněný v případě
     *         nesplnění podmínky minimálním požadovaným počtem objektů
     */
    private String satisfy(int anounced, int required)
    {
        if (TypeOfScenario.scHAPPY.equals(scenarioInTest.getType())) {
            boolean yes = (anounced >= required);
            scenarioOK = (scenarioOK && yes);
            return  yes  ?   "vyhovuje"
                         :  ("NEVYHOVUJE  (min = " + required + ")");
        }
        return "";
    }


    /***************************************************************************
     * Převede číslo na dvouznakový textový řetězec.
     *
     * @param number Převáděné číslo
     * @return Výsledný textový řetězec
     */
     private String z2(int number)
    {
        if (number < 10)  {
            return " " + number;
        }
        else {
            return Integer.toString(number);
        }

    }


    /***************************************************************************
     * Zjistí, které typy akcí ještě nebyly použity ačkoliv být použity měly.
     * Základní úspěšný scénář by totiž měl prověřit reakce na všechny typy
     * základních úspěšných akcí (přechody mezi prostory, zvedání a pokládání
     * objektů), základní chybový scénář by měl prověřit reakce na všechny typy
     * chybových akcí.
     *
     * @return Množina názvů neprověřených povinných typů akcí
     */
    private Set<String> discoverNotUsedCommands()
    {
        Set<String> absent = new HashSet<>();
        TypeOfStep.Subtype required;
        if (TypeOfScenario.scHAPPY.equals(scenarioInTest.getType())) {
            required = TypeOfStep.Subtype.stCORRECT;
        }
        else if (TypeOfScenario.scMISTAKES.equals(scenarioInTest.getType())) {
            required = TypeOfStep.Subtype.stMISTAKE;
        }
        else {
            return absent;    //Nejsou povinné akce     //==========>
        }

        for (TypeOfStep typeOfStep : notUsedCommands) {
            if (required.equals(typeOfStep.getSubtype())) {
                absent.add(typeOfStep.name());
            }
        }
        if (absent.size() > 0)  {
            scenarioOK = false;  //Nebyly vyzkoušeny všechny povinné akce
        }
        return absent;
    }


    /***************************************************************************
     * Vrátí množinu názvů zmíněných, avšak nenavštívených místností.
     *
     * @return Množina názvů nenavštívených místností
     */
    @SuppressWarnings("AssignmentToForLoopParameter")
    private Set<String> discoverNotVisitedPlaces()
    {
        Set<String> notVisited = new LinkedHashSet<>();
        for (String s : mentionedPlaces) {
            s = s.toLowerCase();
            if (!visitedPlaces.contains(s)) {
                notVisited.add(s);
            }
        }
        return notVisited;
    }


    /***************************************************************************
     * Zobrazí výsledky provedeného testu spolu se základní charakteristikou
     * testovaného scénáře.
     *
     * @param notVisited Zmíněné, avšak nenavštívené prostory
     * @param absent     Neprovedené akce, které však měly být provedeny
     */
    private void showResults(Set<String> notVisited, Set<String> absent)
    {
        int numCommands  = enteredCommands .size();
        int numVisited   = visitedPlaces   .size();
        int numMentioned = mentionedPlaces .size();
        int numEntered   = enteredCommands .size();
        int numNotUsed   = notUsedCommands .size();
        int numAbsent    = absent          .size();

        String s =
             "===== Konec testu =====" +
           "\nKroků testu:             " + z2(numOfSteps) + " - " +
                                  satisfy(numOfSteps, REQUESTED_STEPS_NUM) +
           "\nAkcí:                    " + z2(numCommands) + " - " +
                                  satisfy(numCommands, REQUESTED_COMMANDS_NUM) +
           "\nZmíněno místností:       " + z2(numMentioned) + " - " +
                                  satisfy(numMentioned, REQUESTED_SEEN_NUM) +
           "\nZ toho navštíveno:       " + z2(numVisited) + " - " +
                                  satisfy(numVisited, REQUESTED_VISITED_NUM) +
           "\nZadané akce:             " + z2(numEntered) + " - " +
                                           enteredCommands +
           "\nNeprovedených typů akcí: " + z2(numNotUsed) + " - " +
                                           notUsedCommands +
           "\nZ toho povinných:        " + z2(numAbsent) + " - " +
                                           absent +
           "\nNavštívené prostory:     " + visitedPlaces +
           "\nNenavštívené prostory:   " + notVisited +
           "\nZmíněné předměty:        " + seenObjects +
           "\n===== Test ukončen\n"      + descriptonOfScenarioInTest +
           "\n===== Scénář " + (scenarioOK ? "vyhověl" : "NEVYHOVĚL") +
           N_HASHES_N;
        DBG.info(s);
    }


    /***************************************************************************
     * Vytvoří seznam obsahující všechny názvy ze zadaného pole (vektoru)
     * převedené na malá písmena.
     *
     * @param names
     * @return Seznam se zadanými názvy v malých písmenech
     */
    private Collection<? extends String> arr2lstInLC(String[] names)
    {
        List<String> list = new ArrayList<>(names.length);
        for (String name : names) {
            list.add(name.toLowerCase());
        }
        return list;
    }


    /***************************************************************************
     * Zpracuje aktuální krok scénáře.
     * Odkaz na něj je uložen v atributu {@link #currentStep}.
     */
    private void processStep()
    {
        try {
            numOfSteps++;
            wordsInCommand = currentStep.command.split("\\s+");
            if ((currentStep.typeOfStep != tsDIALOG)     &&
                (currentStep.typeOfStep != tsUNKNOWN)  &&
                (wordsInCommand.length > 0))
            {
                enteredCommands.add(wordsInCommand[0].toLowerCase());
            }
            visitedPlaces   .add(currentStep.place.toLowerCase());
            mentionedPlaces .add(currentStep.place.toLowerCase());
            mentionedPlaces .addAll(arr2lstInLC(currentStep.neighbors));
            seenObjects     .addAll(arr2lstInLC(currentStep.objects));
            notUsedCommands .remove(currentStep.typeOfStep);
            String message = String.format("%2d. %14s - %s", numOfSteps,
                             currentStep.typeOfStep, currentStep.command);
            DBG.info(message);
            switch (currentStep.typeOfStep)
            {
                case tsNOT_START:       verifyType_NOT_START    (); break;

                case tsSTART:           verifyType_START        (); break;

                case tsEMPTY:           verifyType_EMPTY        (); break;
                case tsHELP:            verifyType_HELP         (); break;
                case tsUNKNOWN:         verifyType_UNKNOWN      (); break;

                case tsMOVE:            verifyType_MOVE         (); break;
                case tsMOVE_WA:         verifyType_MOVE_WA      (); break;
                case tsBAD_NEIGHBOR:    verifyType_BAD_NEIGHBOR (); break;

                case tsPICK_UP:         verifyType_PICK_U       (); break;
                case tsPICK_UP_WA:      verifyType_PICK_UP_WA   (); break;
                case tsBAD_OBJECT:      verifyType_BAD_OBJECT   (); break;
                case tsUNMOVABLE:       verifyType_UNMOVABLE    (); break;
                case tsBAG_FULL:        verifyType_BAG_FULL     (); break;

                case tsPUT_DOWN:        verifyType_PUT_DOWN     (); break;
                case tsPUT_DOWN_WA:     verifyType_PUT_DOWN_WA  (); break;
                case tsNOT_IN_BAG:      verifyType_NOT_IN_BAG   (); break;

                case tsDIALOG:          verifyType_DIALOG       (); break;
                case tsNON_STANDARD:    verifyType_NON_STANDARD (); break;

                case tsDEMO:            verifyType_DEMO         (); break;
                case tsEND:             verifyType_END          (); break;

                default:
                    String txt = "\nTyp testu " + currentStep.typeOfStep +
                                 " nesmí být používán v testovaných scénářích.";
                    ERROR(txt);
                    throw new UnsupportedOperationException();
            }
            previousStep = currentStep;
        }catch(Throwable thr) {
            DBG.severe(thr);

        }
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//
//    /***************************************************************************
//     * Instance třídy představuje komparátor, který při porovnávání
//     * textových řetězců ignoruje velikost znaků.
//     */
//    private static class CompareIgnoreCase implements Comparator<String>
//    {
//        @Override
//        public int compare(String s1, String s2) {
//            return s1.compareToIgnoreCase(s2);
//        }
//    }
//

    /***************************************************************************
     * Přepravka pro uchování vektoru názvů některého z objektů
     * v minulém a aktuálním kroku testu.
     */
    private static class Names
    {
        final String[] last;
        final String[] current;

        Names(String[] last, String[] actual) {
            this.last   = last;
            this.current = actual;
        }
    }


    /***************************************************************************
     * Instance třídy {@code Pair} jsou schopny vrátit odpovídající dvojice
     * polí s názvy příslušných objektů (sousedé místnoti, objekty v místnosti,
     * objekty v batohu).
     */
    @SuppressWarnings("PackageVisibleInnerClass")
    enum Pair
    {
        NEIGHBORS {
            @Override
            @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
            Names pair(ScenarioTest test) {
                return new Names(test.previousStep.neighbors,
                                 test.currentStep.neighbors);
            }
        },

        OBJECTS {
            @Override
            @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
            Names pair(ScenarioTest test) {
                return new Names(test.previousStep.objects,
                                 test.currentStep.objects);
            }
        },

        BAG {
            @Override
            @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
            Names pair(ScenarioTest test) {
                return new Names(test.previousStep.bag,
                                 test.currentStep.bag);
            }
        };

        abstract Names pair(ScenarioTest test);

    }


    /***************************************************************************
     * Přepravka pro uchování informacích charakterizujících svět hry
     * na základě vyhodnocení kroků scénářů.
     */
    public static class Summary
    {
        public final boolean      ok;
        public final Set<String>  mentionedPlaces;
        public final Set<String>  enteredCommands;
        public final Set<String>  seenObjects;
        public final ScenarioStep startStep;
        public final ScenarioStep endStep;
        public final Map<TypeOfStep, String> basicCommands;


        /***********************************************************************
         * Konstruktor inicializující atributy přepravky.
         *
         * @param ok              Informace, zda je scénář bez chyb
         * @param mentionedPlaces Množina názvů všech dosud zmíněných prostorů
         * @param enteredCommands Množina názvů všech doposud zadaných příkazů
         * @param seenObjects     Množina názvů všech dosud zahlédnutých objektů
         * @param startStep       Počáteční krok v základním úspěšném scénáři
         * @param endStep         Poslední krok základního úspěšného scénáře
         * @param basicCommands   Mapa mapující typy příkazů na jejich názvy
         */
        public Summary(boolean     ok,
                      Set<String>  mentionedPlaces,
                      Set<String>  enteredCommands,
                      Set<String>  seenObjects,
                      ScenarioStep startStep,
                      ScenarioStep endStep,
                      Map<TypeOfStep, String> basicCommands)
        {
            this.ok              = ok;
            this.mentionedPlaces = mentionedPlaces;
            this.enteredCommands = enteredCommands;
            this.seenObjects     = seenObjects;
            this.startStep       = startStep;
            this.endStep         = endStep;
            this.basicCommands   = basicCommands;
        }
    }



//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//    }
//    /** @param args Parametry příkazového řádku - nepoužité. */
//    public static void main(String... args) { test(); }
}
