/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adv_framework.empty_classes;

import cz.vse.adv_framework.game_gui.IGameG;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.TypeOfScenario;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.AScenarioManager;

import cz.vse.adv_framework.test_util._Test_101;
import cz.vse.adv_framework.test_util._Test_115;


import static  cz.vse.adv_framework.scenario.TypeOfStep.*;



/*******************************************************************************
 * Instance třídy {@code EmptyScenarioManager} slouží jako správce scénářů,
 * které mají prověřit a/nebo demonstrovat správnou funkci plánované hry.
 * Jednotlivé scénáře jsou iterovatelné posloupností kroků specifikovaných
 * instancemi třídy <code>ScenarioStep</code> z rámce, do nějž se hra začleňuje.
 * <p>
 * Správce scénářů je jedináček, který má na starosti všechny scénáře
 * týkající se s ním sdružené hry.
 *
 * @author Rudolf PECINOVSKÝ
 * @version 3.00,  22.11.2006
 */
public class EmptyGUIScenarioManager extends AScenarioManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Třída, jejíž instance jsou zde spravovány. */
    private final static Class<? extends IGame> CLASS = null;

    /** Jméno autora dané třídy. */
    private static final String author = "PECINOVSKÝ Rudolf";

    /** Xname autora/autorky dané třídy. */
    private static final String xname = "XPECR999";


    /***************************************************************************
     * Konstruktor plnohodnotné instance třídy {@link ScenarioStep}
     * vyžaduje následující parametry:
     *      String   příkaz;    //Příkaz realizující tento krok scénáře
     *      String   zpráva;    //Zpráva vypsaná po zadání příkazu
     *      String   prostor;   //Prostor, v něž skončí hráč po zadání příkazu
     *      String[] východy;   //Aktuální východy z aktuálního prostoru
     *      String[] objekty;   //Objekty vyskytující se v daném prostoru
     *      String[] batoh;     //Aktuální obsah batohu
     *      TypeOfStep typTestu;  //Typ daného kroku testu
     */


    /***************************************************************************
     * Základní scénář popisující možný úspěšný průběh hry.
     */
    private static final ScenarioStep[] HAPPY_SCENARIO_STEPS =
    {
        new ScenarioStep(tsSTART,
            "", //Název prvního příkazu musí být prázdný řetězec
            "Uvítání",
            "VýchozíProstor",
            new String[] { "Soused1", "Soused2", "Soused3" },
            new String[] { "Objekt1", "Objekt2", "Objekt3" },
            new String[] { }),

        new ScenarioStep(tsNOT_SET,
            "příkaz",
            "Zpráva_vypsaná_v_reakci_na_příkaz",
            "Prostor",
            new String[] { "Soused1", "Soused2" },
            new String[] { "Objekt1", "Objekt2" },
            new String[] { "Objekt1", "Objekt2" }),

        new ScenarioStep(tsEND,
            "konec",
            "Ukončení_hry_a_gratulace.",
            "Prostor",
            new String[] { "Sousedé" },
            new String[] { "Objekty" },
            new String[] { "Batoh"   })
        //V úspěsném scénáři nemusí být typ posledního příkazu tsEND
        //Hra může končit i prostým dosažením požadovaného cíle
    };


    /***************************************************************************
     * Základní chybový scénář definující reakce
     * na povinnou sadu chybových stavů.
     */
    private static final ScenarioStep[] MISTAKE_SCENARIO_STEPS =
    {
        new ScenarioStep(tsSTART,
            "", //Název prvního příkazu musí být prázdný řetězec
            "Uvítání",
            "Prostor",
            new String[] { "Sousedé" },
            new String[] { "Objekty" },
            new String[] { "Batoh"   }),

        new ScenarioStep(tsNOT_SET,
            "příkaz",
            "Zpráva_vypsaná_v_reakci_na_příkaz",
            "Prostor",
            new String[] { "Soused1", "Soused2" },
            new String[] { "Objekt1", "Objekt2" },
            new String[] { "Objekt1", "Objekt2" }),


        new ScenarioStep( tsEND,
            "konec",
            "Zpráva_o_předčasném_ukončení_hry.",
            "Prostor",
            new String[] { "Sousedé" },
            new String[] { "Objekty" },
            new String[] { "Batoh"   })
    };


    /***************************************************************************
     * Příkazy pro DEMO_SCENARIO scénář sloužící k analýze funkce
     * grafického uživatelského rozhraní.
     * Scénář má za úkol projít jeméně třemi místnostmi,
     * zvednout nejméně dva objekty a položit je v jiné místnosti,
     * než ve které je zvedal.
     * Příkazy pro DEMO_SCENARIO scénář neobsahuje úvodní prázdný příkaz,
     * ten si konstruktor scénáře doplní sám.
     */
    private static final String[] DEMO_SCENARIO = {
    //Následující příkazy jsou pouze ukázkou, jak by takový scénář mohl vypadat
             "jdi   koupelna",
             "vezmi brýle",
             "vezmi časopis",
             "jdi   předsíň",
             "jdi   obývák",
             "polož časopis",
             "polož brýle",
             "konec"
    };


    /** Jediná instance této třídy. Spravuje všechny scénáře sdružené hry. */
    private static final EmptyGUIScenarioManager SINGLETON =
                                              new EmptyGUIScenarioManager();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí správce scénářů - jedinou instanci této třídy.
     *
     * @return Správce scénářů
     */
    public static EmptyGUIScenarioManager getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří instanci představující správce scénářů hry.
     */
    private EmptyGUIScenarioManager()
    {
        super(author, xname, CLASS);

        addScenario("Základní",
                    TypeOfScenario.scHAPPY,    HAPPY_SCENARIO_STEPS);
        addScenario("Chybový",
                    TypeOfScenario.scMISTAKES, MISTAKE_SCENARIO_STEPS);
        addScenario("Demo for GUI", DEMO_SCENARIO);
        seal();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================

    /***************************************************************************
     * Základní test ověřující,
     * jestli správce scénářů vyhovuje zadným okrajovým podmínkám, tj. jestli:
     * <ul>
     *   <li>Umí vrátit správně naformátované jméno autora/autorky hry
     *       a jeho/její xname.</li>
     *   <li>Definuje základní úspěšný a základní chybový scénář.</li>
     *   <li>Základní chybový scénář má následující vlastnosti:
     *     <ul>
     *       <li>Startovní příkaz, jehož název je prázdný řetězec</li>
     *       <li>Minimální požadovaný počet kroků</li>
     *       <li>Minimální počet navštívených místností</li>
     *       <li>Minimální počet "zahlédnutých" místností</li>
     *       <li>Použití příkazů pro přechod z prostoru do prostoru
     *         zvednití nějakého objektu a položení nějakého objektu</li>
     *     </ul>
     *   </li>
     *   <li>Základní chybový scénář má následující vlastnosti:
     *     <ul>
     *       <li>Startovní příkaz, jehož název je prázdný řetězec</li>
     *       <li>Použití všech povinných chybových typů kroku
     *         definovaných ve třídě
     *         {@link cz.vse.adv_framework.scenario.TypeOfStep}</li>
     *       <li>Ukončení příkazem pro nestandardní ukončení hry</li>
     *     </ul>
     *   </li>
     * </ul>
     */
    public static void testMyScenarioManager()
    {
        _Test_101 test = _Test_101.getInstance(SINGLETON);
        test.testScenarioManager();
    }


    /***************************************************************************
     * Simuluje hraní hry podle základního úspěšného
     * a základního chybového scénáře.
     */
    public static void simulateBasicScenarios()
    {
        _Test_101 test = _Test_101.getInstance(SINGLETON);
        test.simulateScenario(SINGLETON.getScenario(0), false);
        test.simulateScenario(SINGLETON.getScenario(1), false);
    }


    /***************************************************************************
     * Testování funkce hry prováděné postupně
     * prostřednictvím všech scénářů daného správce
     */
    public static void testMyGame()
    {
        IGame     hra  = SINGLETON.getGame();
        _Test_101 test = _Test_101.getInstance(hra);
        test.testGame();
    }


    /***************************************************************************
     * Test ověřujícífunkci textovou verzi hry,
     * jejíž scénáře daný správce spravuje.
     */
    public static void testMyTextVersion()
    {
        IGame     hra  = SINGLETON.getGame();
        _Test_101 test = _Test_101.getInstance(hra);
        test.playGameByScenario(INDEX_GUI_TEST);
    }


    /***************************************************************************
     * Test demonstrující hraní hry na implicitním GUI.
     */
    public static void testMyGUIVersion()
    {
        IGameG    hra  = (IGameG)SINGLETON.getGame();
        _Test_115 test = _Test_115.getInstance(hra);
        test.playGameByScenario(INDEX_GUI_TEST);
    }


    /** @param args Parametry příkazového řádku - nepoužívané. */
    public static void main( String[] args )
    {
        testMyScenarioManager();
        simulateBasicScenarios();
    }
}
