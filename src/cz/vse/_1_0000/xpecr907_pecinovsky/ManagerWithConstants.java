/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse._1_0000.xpecr907_pecinovsky;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.TypeOfStep;
import cz.vse.adv_framework.scenario.TypeOfScenario;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.AScenarioManager;

import cz.vse.adv_framework.test_util._Test_101;

import java.util.Calendar;
import java.util.GregorianCalendar;


import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;

import static  cz.vse.adv_framework.scenario.TypeOfStep.*;



/*******************************************************************************
 * Instance třídy {@code ManagerWithConstants} slouží jako správce scénářů,
 * které mají prověřit a/nebo demonstrovat správnou funkci plánované hry.
 * Jednotlivé scénáře jsou iterovatelné posloupností kroků specifikovaných
 * instancemi třídy <code>ScenarioStep</code> z rámce, do nějž se hra začleňuje.
 * <p>
 * Tato třída poskytuje definice využívající globálních textových konstant.
 * <p>
 * Správce scénářů je jedináček, který má na starosti všechny scénáře
 * týkající se s ním sdružené hry.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public class ManagerWithConstants extends AScenarioManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Třída, jejíž instance jsou zde spravovány. */
    private final static Class<? extends IGame> CLASS = null;

    /** Jméno autora dané třídy. */
    private static final String AUTHOR = "PECINOVSKÝ Rudolf";

    /** Xname autora/autorky dané třídy. */
    private static final String XNAME = "XPECR999";

    /** Pomocné konstanty pro rozhovor s ledničkou. */
    private static final int ROKŮ = 20;
    private static final int LETOS;
    private static final int ROK_NAR;
    static {
        Calendar cal = new GregorianCalendar();
        LETOS   = cal.get(Calendar.YEAR);
        ROK_NAR = LETOS - ROKŮ;
    }



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
        new ScenarioStep(tsSTART,  "", //Název startovního příkazu
            zCELÉ_UVÍTÁNÍ,

            PŘEDSÍŇ,
            new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
            new String[] { BOTNÍK, DEŠTNÍK },
            new String[] {}
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

        new ScenarioStep(tsMOVE, pJDI + " " + KOUPELNA,
            zPŘESUN + KOUPELNA +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS), cm()),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] {}
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + BRÝLE,
            zZVEDNUTO + BRÝLE +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(UMYVADLO, ČASOPIS), cm(BRÝLE)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO, ČASOPIS },
            new String[] { BRÝLE }
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + ČASOPIS,
            zZVEDNUTO + ČASOPIS +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(UMYVADLO), cm(BRÝLE, ČASOPIS)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsMOVE, pJDI + " " + PŘEDSÍŇ,
            zPŘESUN   + PŘEDSÍŇ +
            String.format(FORMÁT_INFORMACE,
                PŘEDSÍŇ,
                cm(LOŽNICE, OBÝVÁK, KOUPELNA),
                cm(BOTNÍK,  DEŠTNÍK), cm(BRÝLE, ČASOPIS)),

            PŘEDSÍŇ,
            new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
            new String[] { BOTNÍK, DEŠTNÍK },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsMOVE, pJDI + " " + OBÝVÁK,
            zPŘESUN   + OBÝVÁK   +
            String.format(FORMÁT_INFORMACE,
                OBÝVÁK,
                cm(KUCHYŇ, PŘEDSÍŇ),
                cm(TELEVIZE), cm(BRÝLE, ČASOPIS)),

            OBÝVÁK,
            new String[] { KUCHYŇ, PŘEDSÍŇ },
            new String[] { TELEVIZE },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsMOVE, pJDI + " " + KUCHYŇ,
            zPŘESUN   + KUCHYŇ +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK,   LOŽNICE),
                cm(LEDNIČKA, PAPÍR), cm(BRÝLE, ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsNON_STANDARD, pOTEVŘI + " " + LEDNIČKA,
            LEDNICE_NEJDE_OTEVŘÍT +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK,   LOŽNICE),
                cm(LEDNIČKA, PAPÍR), cm(BRÝLE, ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsBAG_FULL, pVEZMI + " " + PAPÍR,
            zANP + zBATOH_PLNÝ +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK,   LOŽNICE),
                cm(LEDNIČKA, PAPÍR),
                cm(BRÝLE, ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR },
            new String[] { BRÝLE, ČASOPIS }
        ),

        new ScenarioStep(tsPUT_DOWN, pPOLOŽ + " " + ČASOPIS,
            zPOLOŽENO + ČASOPIS +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA, PAPÍR, ČASOPIS), cm(BRÝLE)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR, ČASOPIS },
            new String[] { BRÝLE }
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + PAPÍR,
            zZVEDNUTO + PAPÍR +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA, ČASOPIS), cm(BRÝLE, PAPÍR)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, ČASOPIS },
            new String[] { BRÝLE, PAPÍR }
        ),

        new ScenarioStep(tsNON_STANDARD, pPŘEČTI + " " + PAPÍR,
            CHCE_PŘEČÍST_VZKAZ + NEMÁ_BRÝLE +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA, ČASOPIS),
                cm(BRÝLE, PAPÍR)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, ČASOPIS },
            new String[] { BRÝLE, PAPÍR }
        ),

        new ScenarioStep(tsNON_STANDARD, pNASAĎ + " " + BRÝLE,
            NASADIL_BRÝLE +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA, ČASOPIS), cm(PAPÍR)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, ČASOPIS },
            new String[] { PAPÍR }
        ),

        new ScenarioStep(tsNON_STANDARD, pPŘEČTI + " " + PAPÍR,
            NAPSÁNO
            +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA, ČASOPIS),
                cm(PAPÍR)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, ČASOPIS },
            new String[] { PAPÍR }
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + ČASOPIS,
            zZVEDNUTO + ČASOPIS +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA), cm(PAPÍR, ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA },
            new String[] { PAPÍR, ČASOPIS }
        ),

        new ScenarioStep(tsNON_STANDARD, pPODLOŽ + " " + LEDNIČKA + " " + ČASOPIS,
            CHCE_PODLOŽIT  + LEDNIČKA + PŘEDMĚTEM + ČASOPIS + "." +
            NELZE_NADZVEDNOUT +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(LEDNIČKA), cm(PAPÍR, ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA },
            new String[] { PAPÍR, ČASOPIS  }
        ),

        new ScenarioStep(tsPUT_DOWN, pPOLOŽ + " " + PAPÍR,
            zPOLOŽENO + PAPÍR +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(PAPÍR, LEDNIČKA), cm(ČASOPIS)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { PAPÍR, LEDNIČKA },
            new String[] { ČASOPIS }
        ),

        new ScenarioStep(tsNON_STANDARD, pPODLOŽ + " " + LEDNIČKA + " " + ČASOPIS,
            CHCE_PODLOŽIT  + LEDNIČKA + PŘEDMĚTEM + ČASOPIS + "." +
            LEDNIČKA_PODLOŽENA +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(PAPÍR, LEDNIČKA), cm()),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { PAPÍR, LEDNIČKA },
            new String[] {}
        ),

        new ScenarioStep(tsNON_STANDARD, pOTEVŘI + " " + LEDNIČKA,
            OTEVŘEL_LEDNIČKU +
            String.format(FORMÁT_INFORMACE,
                LEDNIČKA, cm(),
                cm(PIVO, PIVO, PIVO,
                   SALÁM, HOUSKA, VÍNO, RUM),
                cm()),

            LEDNIČKA,
            new String[] {},
            new String[] { PIVO,  PIVO,   PIVO,
                           SALÁM, HOUSKA, VÍNO, RUM },
            new String[] {}
        ),

        new ScenarioStep(tsUNMOVABLE, pVEZMI + " " + PIVO,
            BERE_ALKOHOL +
            PIVO + "." +
            KOLIK_LET
                ,

            LEDNIČKA,
            new String[] {},
            new String[] { PIVO,  PIVO,   PIVO,
                           SALÁM, HOUSKA, VÍNO, RUM },
            new String[] {}
        ),

        new ScenarioStep(tsDIALOG, ""+ROKŮ,
            NAROZEN,

            LEDNIČKA,
            new String[] {},
            new String[] { PIVO,  PIVO,   PIVO,
                           SALÁM, HOUSKA, VÍNO, RUM },
            new String[] {}
        ),

        new ScenarioStep(tsDIALOG, "" + ROK_NAR,
            ODEBRAL + PIVO,

            LEDNIČKA,
            new String[] {},
            new String[] { PIVO,  PIVO,
                           SALÁM, HOUSKA, VÍNO, RUM },
            new String[] { PIVO }
        ),

        new ScenarioStep(tsNON_STANDARD, pZAVŘI + " " + LEDNIČKA,
            ZAVŘEL_LEDNIČKU +
            String.format(FORMÁT_INFORMACE,
                KUCHYŇ,
                cm(OBÝVÁK, LOŽNICE),
                cm(PAPÍR, LEDNIČKA), cm(PIVO)),

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR },
            new String[] { PIVO }
        ),

        new ScenarioStep(tsEND, pKONEC,
            zKONEC,

            KUCHYŇ,
            new String[] { OBÝVÁK, LOŽNICE },
            new String[] { LEDNIČKA, PAPÍR },
            new String[] { PIVO }
        )

    };


    static { ScenarioStep.setIndex(2); }


    /***************************************************************************
     * Základní chybový scénář definující reakce
     * na povinnou sadu chybových stavů.
     */
    private static final ScenarioStep[] MISTAKE_SCENARIO_STEPS =
    {
        new ScenarioStep(tsNOT_START, "Start",
            zNENÍ_START,

            "",
            new String[] {},
            new String[] {},
            new String[] {}
        ),

        START_STEP,

        new ScenarioStep(tsUNKNOWN, "maso",
            zNEZNÁMÝ_PŘÍKAZ +
            String.format(FORMÁT_INFORMACE,
                PŘEDSÍŇ,
                cm(LOŽNICE, OBÝVÁK, KOUPELNA),
                cm(BOTNÍK,  DEŠTNÍK),
                cm()
            ),

            PŘEDSÍŇ,
            new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
            new String[] { BOTNÍK,  DEŠTNÍK },
            new String[] {}
        ),

        new ScenarioStep(tsEMPTY, "",
            zPRÁZDNÝ_PŘÍKAZ +
            String.format(FORMÁT_INFORMACE,
                PŘEDSÍŇ,
                cm(LOŽNICE, OBÝVÁK, KOUPELNA),
                cm(BOTNÍK,  DEŠTNÍK), cm()),

            PŘEDSÍŇ,
            new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
            new String[] { BOTNÍK,  DEŠTNÍK },
            new String[] {}
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + DEŠTNÍK,
            zZVEDNUTO + DEŠTNÍK +
            String.format(FORMÁT_INFORMACE,
                PŘEDSÍŇ,
                cm(LOŽNICE, OBÝVÁK, KOUPELNA),
                cm(BOTNÍK), cm(DEŠTNÍK)),

            PŘEDSÍŇ,
            new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
            new String[] { BOTNÍK },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsMOVE, pJDI + " " + KOUPELNA,
            zPŘESUN + KOUPELNA +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS), cm(DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsBAD_NEIGHBOR, pJDI + " záchod",
            zANP + zNENÍ_CIL +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS),
                cm(DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsBAD_OBJECT, pVEZMI + " " + KOUPELNA,
            zANP + zNENÍ_PŘEDMĚT + KOUPELNA +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS),
                cm(DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsUNMOVABLE, pVEZMI + " " + UMYVADLO,
            zANP + zTĚŽKÝ_PŘEDMĚT + UMYVADLO +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS),
                cm(DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsNOT_IN_BAG, pPOLOŽ + " " + PAPÍR,
            zANP + zNENÍ_V_BATOHU + PAPÍR +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(BRÝLE, UMYVADLO, ČASOPIS),
                cm(DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { BRÝLE, UMYVADLO, ČASOPIS },
            new String[] { DEŠTNÍK }
        ),

        new ScenarioStep(tsPICK_UP, pVEZMI + " " + BRÝLE,
            zZVEDNUTO + BRÝLE +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(UMYVADLO, ČASOPIS), cm(BRÝLE, DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO, ČASOPIS },
            new String[] { BRÝLE, DEŠTNÍK }
        ),

        new ScenarioStep(tsBAG_FULL, pVEZMI + " " + ČASOPIS,
            zANP + zBATOH_PLNÝ +
            String.format(FORMÁT_INFORMACE,
                KOUPELNA,
                cm(PŘEDSÍŇ),
                cm(UMYVADLO, ČASOPIS),
                cm(BRÝLE, DEŠTNÍK)),

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO, ČASOPIS },
            new String[] { BRÝLE, DEŠTNÍK }
        ),

        new ScenarioStep(tsHELP, pHELP,
            zNÁPOVĚDA,
            //Text pokračuje vyjmenováním příkazů a jejich popisů
            //a končí standardním popisem aktuální situace

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO, ČASOPIS },
            new String[] { BRÝLE, DEŠTNÍK }
        ),

        new ScenarioStep(tsEND, pKONEC,
            zKONEC,

            KOUPELNA,
            new String[] { PŘEDSÍŇ },
            new String[] { UMYVADLO, ČASOPIS },
            new String[] { BRÝLE, DEŠTNÍK }
        ),
    };


    /** Jediná instance této třídy. Spravuje všechny scénáře sdružené hry. */
    private static final ManagerWithConstants MANAGER =
                                          new ManagerWithConstants();



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
    public static ManagerWithConstants getInstance()
    {
        return MANAGER;
    }


    /***************************************************************************
     * Vytvoří instanci představující správce scénářů hry.
     */
    private ManagerWithConstants()
    {
        super(AUTHOR, XNAME, CLASS);

        addScenario("Úspěšný",
                    TypeOfScenario.scHAPPY,    HAPPY_SCENARIO_STEPS);
        addScenario("Chybový",
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
     *       <li>Startovní příkaz, jehož název je prázdný řetězec</li>
     *       <li>Minimální požadovaný počet kroků</li>
     *       <li>Minimální počet navštívených místností</li>
     *       <li>Minimální počet "zahlédnutých" místností</li>
     *       <li>Použití příkazů pro přechod z prostoru do prostoru
     *         zvednutí nějakého objektu a položení nějakého objektu</li>
     *     </ul>
     *   </li>
     *   <li>Základní chybový scénář má následující vlastnosti:
     *     <ul>
     *       <li>Startovní příkaz, jehož název je prázdný řetězec</li>
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
        _Test_101.version = 4;
        test.testGame();
    }


    /** @param args Parametry příkazového řádku - nepoužívané. */
    public static void main(String[] args)
    {
//        testMyScenarioManager();
//        simulateBasicScenarios();
        testMyGame();
    }
}
