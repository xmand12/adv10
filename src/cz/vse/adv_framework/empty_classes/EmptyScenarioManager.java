/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adv_framework.empty_classes;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.TypeOfScenario;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.AScenarioManager;

import cz.vse.adv_framework.test_util._Test_101;


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
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public class EmptyScenarioManager extends AScenarioManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Třída, jejíž instance jsou zde spravovány.
     *  Dokud neexistuje třída hry, je v atributu prázdný odkaz.
     *  Jakmile je třída hry definována, je třeba do atributu umístit
     *  odkaz na class-soubor příslušné třídy hry. */
    private final static Class<? extends IGame> CLASS = null;

    /** Jméno autora dané třídy. */
    private static final String AUTHOR_NAME = "PECINOVSKÝ Rudolf";

    /** Xname autora/autorky dané třídy. */
    private static final String AUTHOR_ID = "XPECR999";



    /*==========================================================================
     * Konstruktor plnohodnotné instance třídy {@link ScenarioStep}
     * vyžaduje následující parametry:
     *   TypeOfStep typeOfStep; //Typ daného kroku scénáře
     *   String     command;    //Příkaz realizující tento krok scénáře
     *   String     message;    //Zpráva vypsaná po zadání příkazu
     *   String     place;      //Prostor, v němž skončí hráč po zadání příkazu
     *   String[]   neighbors;  //Sousedé aktuálního prostoru (= východy)
     *   String[]   objects;    //Objekty vyskytující se v daném prostoru
     *   String[]   bag;        //Aktuální obsah batohu
     =========================================================================*/


    /***************************************************************************
     * Počáteční krok hry, který je pro všechny scénáře shodný.
     */
     private static final ScenarioStep START_STEP =
        new ScenarioStep(tsSTART,
            "", //Název spuštěcího příkazu - musí být prázdný řetězec
            "Uvítání",
            "Výchozí_prostor",
            new String[] { "Soused1", "Soused2" },
            new String[] { "Objekt1", "Objekt2" },
            new String[] { "ObjektA", "ObjektB" }
        );


    /***************************************************************************
     * Kroky základního úspěšného scénáře
     * popisující očekávatelný úspěšný průběh hry.
     * Z těchto kroků sestavený scénář nemusí být nutně nejkratším možným
     * (takže to vlastně ani nemusí být základní úspěšný scénář),
     * ale musí vyhovovat všem okrajovým podmínkám zadání,
     * tj. musí obsahovat minimální počet kroků,
     * projít požadovaný.minimální počet prostorů
     * a demonstrovat použití všech požadovaných příkazů.
     */
    private static final ScenarioStep[] HAPPY_SCENARIO_STEPS =
    {
        START_STEP,

        new ScenarioStep(tsNOT_SET,
            "příkaz",
            "Zpráva_vypsaná_v_reakci_na_příkaz",
            "Aktuální_prostor_po_vykonání_příkazu",
            new String[] { "Soused1", "Soused2" },
            new String[] { "Objekt_v_prostoru", "Objekt_v_prostoru" },
            new String[] {  }
        ),

    };


    static { ScenarioStep.setIndex(2); }


    /***************************************************************************
     * Základní chybový scénář definující reakce
     * na povinnou sadu chybových stavů.
     */
    private static final ScenarioStep[] MISTAKE_SCENARIO_STEPS =
    {
        START_STEP,

        new ScenarioStep(tsNOT_SET,
            "příkaz",
            "Zpráva_vypsaná_v_reakci_na_příkaz",
            "Aktuální_prostor_po_vykonání_příkazu",
            new String[] { "Soused1", "Soused2" },
            new String[] { "Objekt_v_prostoru", "Objekt_v_prostoru" },
            new String[] {  }
        ),

        new ScenarioStep(tsEND,
            "konec",
            "Zpráva_o_předčasném_ukončení_hry.",
            "Prostor",
            new String[] { "Sousedé" },
            new String[] { "Objekty" },
            new String[] { "Batoh"   }
        )
    };


    /** Jediná instance této třídy. Spravuje všechny scénáře sdružené hry. */
    private static final EmptyScenarioManager MANAGER =
                                              new EmptyScenarioManager();



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
    public static EmptyScenarioManager getInstance()
    {
        return MANAGER;
    }


    /***************************************************************************
     * Vytvoří instanci představující správce scénářů hry.
     */
    private EmptyScenarioManager()
    {
        super(AUTHOR_NAME, AUTHOR_ID, CLASS);

        addScenario("Happy",
                    TypeOfScenario.scHAPPY,    HAPPY_SCENARIO_STEPS);
        addScenario("Mistake",
                    TypeOfScenario.scMISTAKES, MISTAKE_SCENARIO_STEPS);
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
     * jestli správce scénářů vyhovuje zadaným okrajovým podmínkám, tj. jestli:
     * <ul>
     *   <li>Umí vrátit správně naformátované jméno autora/autorky hry
     *       a jeho/její xname.</li>
     *   <li>Definuje základní úspěšný a základní chybový scénář.</li>
     *   <li>Základní chybový scénář má následující vlastnosti:
     *     <ul>
     *       <li>Startovací příkaz, jehož název je prázdný řetězec</li>
     *       <li>Minimální požadovaný počet kroků</li>
     *       <li>Minimální počet navštívených místností</li>
     *       <li>Minimální počet "zahlédnutých" místností</li>
     *       <li>Použití příkazů pro přechod z prostoru do prostoru
     *         zvednutí nějakého objektu a položení nějakého objektu</li>
     *     </ul>
     *   </li>
     *   <li>Základní chybový scénář má následující vlastnosti:
     *     <ul>
     *       <li>Pokus o odstartování hry jiným než startovacím příkazem</li>
     *       <li>Startovací příkaz, jehož název je prázdný řetězec</li>
     *       <li>Použití všech povinných chybových typů kroku
     *         definovaných ve třídě
     *         {@link cz.vse.adv_framework.scenario.TypeOfStep}</li>
     *       <li>Vyvolání nápovědy</li>
     *       <li>Ukončení příkazem pro nestandardní ukončení hry</li>
     *     </ul>
     *   </li>
     * </ul>
     */
    public static void testMyScenarioManager()
    {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.testScenarioManager();
    }


    /***************************************************************************
     * Simuluje hraní hry podle základního úspěšného
     * a základního chybového scénáře.
     */
    public static void simulateBasicScenarios()
    {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.simulateScenario(MANAGER.getScenario(0), false);
        test.simulateScenario(MANAGER.getScenario(1), false);
    }


    /***************************************************************************
     * Testování funkce hry prováděné postupně
     * prostřednictvím všech scénářů daného správce
     */
    public static void testMyGame()
    {
        IGame     hra  = MANAGER.getGame();
        _Test_101 test = _Test_101.getInstance(hra);
        test.testGame();
    }


    /** @param args Parametry příkazového řádku - nepoužívané. */
    public static void main(String[] args)
    {
        testMyScenarioManager();
        simulateBasicScenarios();
//        testMyGame();
    }
}
