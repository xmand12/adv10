/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr911_pecinovsky;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.TypeOfScenario;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.AScenarioManager;

import cz.vse.adv_framework.test_util._Test_101;

import java.util.Calendar;
import java.util.GregorianCalendar;


import static cz.vse.adv_framework.scenario.TypeOfStep.*;



/*******************************************************************************
 * Instance třídy {@code ManagerWithLiterals} slouží jako správce scénářů,
 * které mají prověřit a/nebo demonstrovat správnou funkci plánované hry.
 * Jednotlivé scénáře jsou iterovatelné posloupností kroků specifikovaných
 * instancemi třídy <code>ScenarioStep</code> z rámce, do nějž se hra začleňuje.
 * <p>
 * Tato třída poskytuje definice používající přímé zadávání textů.
 * Slouží pouze k demonstraci rozdílu oproti třídě (správci scénářů)
 * používající konstanty a nejsou u ní proto průběžně upravovány detaily tak,
 * aby s její pomocí byla hra doopravdy testovatelná.
 * <p>
 * Správce scénářů je jedináček, který má na starosti všechny scénáře
 * týkající se s ním sdružené hry.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public class ManagerWithLiterals extends AScenarioManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Třída, jejíž instance jsou zde spravovány. */
    private final static Class<? extends IGame> CLASS = DemoGame.class;

    /** Jméno autora dané třídy. */
    private static final String AUTHOR = "PECINOVSKÝ Rudolf";

    /** Xname autora/autorky dané třídy. */
    private static final String XNAME = "XPECR911";

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
            "Vítáme vás ve služebním bytě. Jistě máte hlad." +
          "\nNajděte v bytě ledničku - tam vás čeká svačina." +
        "\n\nNacházíte se v místnosti: Předsíň" +
          "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna" +
          "\nV místnosti se nachází: Botník, Deštník" +
          "\nMáte v držení předměty:",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
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

        new ScenarioStep(tsMOVE, "jdi koupelna",
            "Přesunul(a) jste se do místnosti: Koupelna" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty:",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] {}
        ),

        new ScenarioStep(tsPICK_UP, "vezmi brýle",
            "Vzal(a) jste předmět: Brýle" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Brýle",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Brýle" }
        ),

        new ScenarioStep(tsPICK_UP, "vezmi časopis",
            "Vzal(a) jste předmět: Časopis" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Umyvadlo" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsMOVE, "jdi předsíň",
            "Přesunul(a) jste se do místnosti: Předsíň" +
        "\n\nNacházíte se v místnosti: Předsíň" +
          "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna" +
          "\nV místnosti se nachází: Botník, Deštník" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsMOVE, "jdi obývák",
            "Přesunul(a) jste se do místnosti: Obývák" +
        "\n\nNacházíte se v místnosti: Obývák" +
          "\nMůžete se přesunout do místností: Kuchyň, Předsíň" +
          "\nV místnosti se nachází: Televize" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Obývák",
            new String[] { "Kuchyň", "Předsíň" },
            new String[] { "Televize" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsMOVE, "jdi kuchyň",
            "Přesunul(a) jste se do místnosti: Kuchyň" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsNON_STANDARD, "otevři lednička",
            "Lednička nejde otevřít. Na ledničce leží nějaký popsaný papír." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsBAG_FULL, "vezmi papír",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět nemůžete vzít, máte už obě ruce plné." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty: Brýle, Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Brýle", "Časopis" }
        ),

        new ScenarioStep(tsPUT_DOWN, "polož časopis",
            "Položil(a) jste předmět: Časopis" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír, Časopis" +
          "\nMáte v držení předměty: Brýle",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír", "Časopis" },
            new String[] { "Brýle" }
        ),

        new ScenarioStep(tsPICK_UP, "vezmi papír",
            "Vzal(a) jste předmět: Papír" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Časopis" +
          "\nMáte v držení předměty: Brýle, Papír",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Časopis" },
            new String[] { "Brýle", "Papír" }
        ),

        new ScenarioStep(tsNON_STANDARD, "přečti papír",
            "Rozhodl(a) jste se přečíst vzkaz na papíře." +
          "\nJe ale psán příliš malým písmem, které je rozmazané" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Časopis" +
          "\nMáte v držení předměty: Brýle, Papír",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Časopis" },
            new String[] { "Brýle", "Papír" }
        ),

        new ScenarioStep(tsNON_STANDARD, "nasaď brýle",
            "Nasadil(a) jste si brýle." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Časopis" +
          "\nMáte v držení předměty: Papír",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Časopis" },
            new String[] { "Papír" }
        ),

        new ScenarioStep(tsNON_STANDARD, "přečti papír",
            "Na papíru je napsáno:" +
          "\nLednička stojí nakřivo, a proto jde špatně otevírat." +
          "\nBudete-li mít problémy, něčím ji podložte." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Časopis" +
          "\nMáte v držení předměty: Papír",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Časopis" },
            new String[] { "Papír" }
        ),

        new ScenarioStep(tsPICK_UP, "vezmi časopis",
            "Vzal(a) jste předmět: Časopis" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička" +
          "\nMáte v držení předměty: Papír, Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička" },
            new String[] { "Časopis", "Papír" }
        ),

        new ScenarioStep(tsNON_STANDARD, "podlož lednička časopis",
            "Rozhodl(a) jste se podložit předmět lednička předmětem časopis." +
          "\nBohužel máte obě ruce plné a nemáte ji čím nadzvednout." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička" +
          "\nMáte v držení předměty: Papír, Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička" },
            new String[] { "Časopis", "Papír" }
        ),

        new ScenarioStep(tsPUT_DOWN, "polož papír",
            "Položil(a) jste předmět: Papír" +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty: Časopis",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Časopis" }
        ),

        new ScenarioStep(tsNON_STANDARD, "podlož lednička časopis",
            "Rozhodl(a) jste se podložit předmět lednička předmětem časopis." +
          "\nLednička je úspěšně podložena - nyní by již měla jít otevřít." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty:",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] {}
        ),

        new ScenarioStep(tsNON_STANDARD, "otevři lednička",
            "Úspěšně jste otevřel(a) ledničku." +
        "\n\nNacházíte se v místnosti: Lednička" +
          "\nMůžete se přesunout do místností:" +
          "\nV místnosti se nachází: Pivo, Pivo, Pivo, " +
                                    "Salám, Houska, Víno, Rum" +
          "\nMáte v držení předměty:",

            "Lednička",
            new String[] {},
            new String[] { "Pivo",  "Pivo",   "Pivo",
                           "Salám", "Houska", "Víno", "Rum" },
            new String[] {}
        ),

        new ScenarioStep(tsUNMOVABLE, "vezmi pivo",
            "Pokoušíte si vzít z inteligentní ledničky Pivo." +
          "\nToto je inteligentní lednička, která neumožňuje " +
          "\npodávání alkoholických nápojů mladistvým." +
          "\nKolik je vám let?",

            "Lednička",
            new String[] {},
            new String[] { "Pivo", "Pivo", "Pivo",
                           "Salám", "Houska", "Víno", "Rum" },
            new String[] {}
        ),

        new ScenarioStep(tsDIALOG, ""+ROKŮ,
            "V kterém roce jste se narodil(a)?\n",

            "Lednička",
            new String[] {},
            new String[] { "Pivo", "Pivo", "Pivo",
                           "Salám", "Houska", "Víno", "Rum" },
            new String[] {}
        ),

        new ScenarioStep(tsDIALOG, "" + ROK_NAR,
            "Věřím vám a předávám vám požadovaný nápoj." +
          "\nOdebral(a) jste z ledničky: Pivo" +
          "\nDobrou chuť. Nezapomeňte zavřít ledničku." +
        "\n\nNacházíte se v místnosti: Lednička" +
          "\nMůžete se přesunout do místností:" +
          "\nV místnosti se nachází: Pivo, Pivo," +
                                    "Salám, Houska, Víno, Rum" +
          "\nMáte v držení předměty: Pivo",

            "Lednička",
            new String[] {},
            new String[] { "Pivo", "Pivo",
                           "Salám", "Houska", "Víno", "Rum" },
            new String[] { "Pivo" }
        ),

        new ScenarioStep(tsNON_STANDARD, "zavři lednička",
            "Úspěšně jste zavřel(a) ledničku." +
        "\n\nNacházíte se v místnosti: Kuchyň" +
          "\nMůžete se přesunout do místností: Obývák, Ložnice" +
          "\nV místnosti se nachází: Lednička, Papír" +
          "\nMáte v držení předměty: Pivo",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Pivo" }
        ),

        new ScenarioStep(tsEND, "konec",
            "Konec hry. \nDěkujeme, že jste zkusil(a) naši hru.",

            "Kuchyň",
            new String[] { "Obývák", "Ložnice" },
            new String[] { "Lednička", "Papír" },
            new String[] { "Pivo" }
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
            "Hra neběží, lze ji spustit pouze prázdným příkazem.",

            "",
            new String[] {},
            new String[] {},
            new String[] {}
        ),

        START_STEP,

        new ScenarioStep(tsUNKNOWN, "maso",
            "Tento příkaz neznám." +
          "\nChcete-li poradit, zadejte příkaz ?" +
        "\n\nNacházíte se v místnosti: Předsíň" +
          "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna" +
          "\nV místnosti se nachází: Botník, Deštník" +
          "\nMáte v držení předměty:",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
            new String[] {}
        ),

        new ScenarioStep(tsEMPTY, "",
            "Zadal(a) jste prázdný příkaz." +
        "\n\nNacházíte se v místnosti: Předsíň" +
          "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna" +
          "\nV místnosti se nachází: Botník, Deštník" +
          "\nMáte v držení předměty:",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník",  "Deštník" },
            new String[] {}
        ),

        new ScenarioStep(tsPICK_UP, "vezmi deštník",
            "Vzal(a) jste předmět: Deštník" +
        "\n\nNacházíte se v místnosti: Předsíň" +
          "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna" +
          "\nV místnosti se nachází: Botník" +
          "\nMáte v držení předměty: Deštník",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsMOVE, "jdi koupelna",
            "Přesunul(a) jste se do místnosti: Koupelna" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsBAD_NEIGHBOR, "jdi záchod",
            "Zadaná akce nebyla provedena\n" +
            "Do zadané místnosti se odsud nedá přejít." +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsBAD_OBJECT, "vezmi koupelna",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět v místnosti není: Koupelna" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsUNMOVABLE, "vezmi umyvadlo",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět nejde zvednout: Umyvadlo" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsNOT_IN_BAG, "polož papír",
            "Zadaná akce nebyla provedena\n" +
            "Předmět není v batohu: Papír" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Brýle, Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Brýle", "Umyvadlo", "Časopis" },
            new String[] { "Deštník" }
        ),

        new ScenarioStep(tsPICK_UP, "vezmi brýle",
            "Vzal(a) jste předmět: Brýle" +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník, Brýle",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsBAG_FULL, "vezmi Časopis",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět nemůžete vzít, máte už obě ruce plné." +
        "\n\nNacházíte se v místnosti: Koupelna" +
          "\nMůžete se přesunout do místností: Předsíň" +
          "\nV místnosti se nachází: Umyvadlo, Časopis" +
          "\nMáte v držení předměty: Deštník, Brýle",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsHELP, "?",
            "\nPříkazy, které je možno v průběhu hry zadat:" +
            "\n============================================",
            //Text pokračuje vyjmenováním příkazů a jejich popisů
            //a končí standardním popisem aktuální situace

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsPICK_UP_WA, "vezmi",
            "\nNebylo zadáno, co se má vzít.",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsPUT_DOWN_WA, "polož",
            "\nNebylo zadáno, co se má položit.",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsMOVE_WA, "jdi",
            "\nNebylo zadáno, kam se má jít.",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),

        new ScenarioStep(tsEND, "konec",
            "Konec hry. \nDěkujeme, že jste zkusil(a) naši hru.",

            "Koupelna",
            new String[] { "Předsíň" },
            new String[] { "Umyvadlo", "Časopis" },
            new String[] { "Deštník", "Brýle" }
        ),
    };


/*# Přidáno pro DU_09 #*/
    /***************************************************************************
     * Pomocný scénář pro ověření domácího úkolu č. 9.
     */
    private static final ScenarioStep[] SCENARIO_STEPS_HW_09 =
    {
        START_STEP,

        new ScenarioStep(tsEND, "konec",
            "Konec hry. \nDěkujeme, že jste zkusil(a) naši hru.",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
            new String[] {}
        ),
    };
/*##*/


/*# Přidáno pro DU_10 #*/
    /***************************************************************************
     * Pomocný scénář pro ověření domácího úkolu č. 10.
     */
    private static final ScenarioStep[] SCENARIO_STEPS_HW_10 =
    {
        START_STEP,

        new ScenarioStep(tsNON_STANDARD, "_TEST_10_",
            "Přesunul(a) jste se do testovacího prostoru",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsNOT_IN_BAG, "polož NOT_IN_BAG",
            "Zadaná akce nebyla provedena\n" +
            "Předmět není v batohu: NOT_IN_BAG",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsPUT_DOWN, "polož 1",
            "Položil(a) jste předmět: 1",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL", "1" },
            new String[] { "2" }
        ),

        new ScenarioStep(tsPICK_UP, "vezmi 1",
            "Vzal(a) jste předmět: 1",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsBAG_FULL, "vezmi BAG_FULL",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět nemůžete vzít, máte už obě ruce plné.",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsBAD_OBJECT, "vezmi BAD_OBJECT",
            "Zadaná akce nebyla provedena\n" +
            "Zadaný předmět v místnosti není: BAD_OBJECT",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsPICK_UP_WA, "vezmi",
            "\nNebylo zadáno, co se má vzít.",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsPUT_DOWN_WA, "polož",
            "\nNebylo zadáno, co se má položit.",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsMOVE_WA, "jdi",
            "\nNebylo zadáno, kam se má jít.",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsBAD_NEIGHBOR, "jdi _TEST_10_",
            "Zadaná akce nebyla provedena\n" +
            "Do zadané místnosti se odsud nedá přejít.",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsHELP, "?",
            "\nPříkazy, které je možno v průběhu hry zadat:" +
            "\n============================================" +
            "\n      -> Příkaz startující hru." +
            "\nJdi   -> Přesune hráče do zadané sousední místnosti" +
            "\nPolož -> Odebere objekt z batohu a uloží jej do aktuálního prostoru" +
            "\nKonec -> Příkaz předčasně ukončující hru" +
            "\nVezmi -> Odebere objekt z aktuálního prostoru a vloží jej do batohu" +
            "\n_TEST_10_ -> Příkaz pro realizaci 10. domácího úkolu" +
            "\n",

            "_TEST_10_",
            new String[] { "Předsíň" },
            new String[] { "BAG_FULL"},
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsMOVE, "jdi Předsíň",
            "Přesunul(a) jste se do místnosti: Předsíň",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
            new String[] { "1", "2" }
        ),

        new ScenarioStep(tsEND, "konec",
            "Konec hry.\n" +
            "Děkujeme, že jste zkusil(a) naši hru.",

            "Předsíň",
            new String[] { "Ložnice", "Obývák", "Koupelna" },
            new String[] { "Botník", "Deštník" },
            new String[] { "1", "2" }
        ),
    };
/*##*/


    /** Jediná instance této třídy. Spravuje všechny scénáře sdružené hry. */
    private static final ManagerWithLiterals MANAGER =
                                         new ManagerWithLiterals();



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
    public static ManagerWithLiterals getInstance()
    {
        return MANAGER;
    }


    /***************************************************************************
     * Vytvoří instanci představující správce scénářů hry.
     */
    private ManagerWithLiterals()
    {
        super(AUTHOR, XNAME, CLASS);

        addScenario("Úspěšný",
                    TypeOfScenario.scHAPPY,    HAPPY_SCENARIO_STEPS);
        addScenario("Chybový",
                    TypeOfScenario.scMISTAKES, MISTAKE_SCENARIO_STEPS);
///*# Přidáno pro DU_09 #*/
//        addScenario("DU_09",
//                    TypeOfScenario.scGENERAL,  SCENARIO_STEPS_HW_09);
///*##*/
/*# Přidáno pro DU_10 #*/
        addScenario("DU_10",
                    TypeOfScenario.scGENERAL,  SCENARIO_STEPS_HW_10);
/*##*/
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
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.testGame();
    }


/*# Přidáno pro DU_09 #*/
    /***************************************************************************
     * Nejprve otestuje daného správce scénářů
     * a pak otestuje hru podle scénáře se zadaným názvem.
     *
     * @param name Název scénáře, podle nějž bude hra otestována
     */
    public static void testGameByScenario(String name)
    {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.playGameByScenario(name);
    }
/*##*/


    /** @param args Parametry příkazového řádku - nepoužívané. */
    public static void main(String[] args)
    {
//        testMyScenarioManager();
//        simulateBasicScenarios();
///*# Přidáno pro DU_09 #*/
//        testGameByScenario("DU_09");
///*##*/
/*# Přidáno pro DU_10 #*/
        testGameByScenario("DU_10");
/*##*/
//        testMyGame();
        System.exit(0);
    }
}
