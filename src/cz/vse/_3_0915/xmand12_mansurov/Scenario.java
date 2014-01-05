package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.TypeOfScenario;
import cz.vse.adv_framework.scenario.TypeOfStep;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsBAD_NEIGHBOR;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsBAD_OBJECT;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsBAG_FULL;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsEND;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsHELP;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsMOVE_WA;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsNON_STANDARD;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsNOT_IN_BAG;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsPICK_UP;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsPICK_UP_WA;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsPUT_DOWN;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsPUT_DOWN_WA;
import static cz.vse.adv_framework.scenario.TypeOfStep.tsSTART;
import cz.vse.adv_framework.test_util._Test_101;

/*******************************************************************************
 * Instance třídy {@literal Scenario} slouží jako správce scénářů,
 * které mají prověřit a/nebo demonstrovat správnou funkci plánované hry.
 * Jednotlivé scénáře jsou iterovatelné posloupností kroků specifikovaných
 * instancemi třídy <code>ScenarioStep</code> z rámce, do nějž se hra začleňuje.
 * <p>
 * Správce scénářů je jedináček, který má na starosti všechny scénáře
 * týkající se s ním sdružené hry.
 *
 * @author  Daulet Mansurov
 * @version 7.4
 */
public final class Scenario extends AScenarioManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Třída, jejíž instance jsou zde spravovány.
     *  Dokud neexistuje třída hry, je v atributu prázdný odkaz.
     *  Jakmile je třída hry definována, je třeba do atributu umístit
     *  odkaz na class-soubor příslušné třídy hry. */
    private final static Class<? extends IGame> CLASS = Game.class;

    /** Jméno autora dané třídy. */
    private static final String AUTHOR_NAME = "MANSUROV Daulet";

    /** Xname autora/autorky dané třídy. */
    private static final String AUTHOR_ID = "XMAND12";



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
     private static final ScenarioStep START_STEP
            = new ScenarioStep(tsSTART,
                    "",
                    "Vy jste známý španělský korzár. Jednou k Vám přišel duch vašeho otce, \n"
                    + "a řekl, jdete do jeskyně, která se nachází v blízkosti města. \n"
                    + "Když jste přišel do jeskyně, váš otec ti dal mapu, v které se ukazuje, \n"
                    + "kde je jeho poklad a dal Vám klíč od truhlice.",
                    "Jeskyně",
                    new String[]{"Džungle"},
                    new String[]{"Lopata", "Provaz", "Láhev"},
                    new String[]{"Klíč", "Mapa"}
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

                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi lopata",
                        "Lopata je teď v vašem batohu.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi džungle",
                        "Přestunul jste se do: džungle.\nNěkdo k vám podešel a dal rozkaz\n"
                        + ", abyste mu předal(a) všechno, co máte.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "zabij vrah",
                        "Zabil jste vraha a schoval jste jeho trup do keře",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi město",
                        "Přestunul jste se do: město",
                        "Město",
                        new String[]{"Džungle", "Taverna", "Rezidence", "Dok", "Přistav"},
                        new String[]{},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi rezidence",
                        "Přestunul jste se do: rezidence",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "10000"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsDIALOG,
                        "mluv gubernátor",
                        "Zeptal jste gubernátora, nemá-li nějaké zadání. Gubernátor řekl,\n"
                        + "že se ve džunglích vyskituje vrah, který terorizuje lidé.\n"
                        + " Rǐkáte, že zabil jste ho. Ale gubernátor tomu neveří.\n"
                        + "Ptá se po důkazech. Odpověděl jste, že nějaké dostanete.",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "10000"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi město",
                        "Přestunul jste se do: město",
                        "Město",
                        new String[]{"Džungle", "Taverna", "Rezidence", "Dok", "Přistav"},
                        new String[]{},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi džungle",
                        "Přestunul jste se do: džungle",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi vrah",
                        "Vrah je teď v vašem batohu.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Vrah"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi město",
                        "Přestunul jste se do: město",
                        "Město",
                        new String[]{"Džungle", "Taverna", "Rezidence", "Dok", "Přistav"},
                        new String[]{},
                        new String[]{"Klíč", "Mapa", "Lopata", "Vrah"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi rezidence",
                        "Přestunul jste se do: rezidence",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "10000"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Vrah"}
                ),
                new ScenarioStep(TypeOfStep.tsPUT_DOWN,
                        "polož vrah",
                        "Položil(a) jste předmět: vrah",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "10000", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsDIALOG,
                        "mluv gubernátor",
                        "Gubernátor poděkoval vám a řekl,že vaše odměna na vás už"
                        + "\ndávno čeká na jeho stole, a že teď můžete ji vzít.",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "Vrah", "10000"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi 10000",
                        "10000 je teď v vašem batohu.",
                        "Rezidence",
                        new String[]{"Město"},
                        new String[]{"Gubernátor", "Soška", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "10000"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi město",
                        "Přestunul jste se do: město",
                        "Město",
                        new String[]{"Džungle", "Taverna", "Rezidence", "Dok", "Přistav"},
                        new String[]{},
                        new String[]{"Klíč", "Mapa", "Lopata", "10000"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi dok",
                        "Přestunul jste se do: dok",
                        "Dok",
                        new String[]{"Město"},
                        new String[]{"Prodavač", "Květiny"},
                        new String[]{"Klíč", "Mapa", "Lopata", "10000"}
                ),
                new ScenarioStep(TypeOfStep.tsDIALOG,
                        "mluv prodavač",
                        "Zeptal jste prodavači, kolik stojí loď. Prodavač odpověděl,\n"
                        + "že loď stojí 10000 pesso. Koupil jste loď.",
                        "Dok",
                        new String[]{"Město"},
                        new String[]{"Prodavač", "Květiny", "10000"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi město",
                        "Přestunul jste se do: město",
                        "Město",
                        new String[]{"Džungle", "Taverna", "Rezidence", "Dok", "Přistav"},
                        new String[]{},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi přistav",
                        "Přestunul jste se do: přistav",
                        "Přistav",
                        new String[]{"Loď", "Město"},
                        new String[]{"Provaz", "Veslo"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi loď",
                        "Přestunul jste se do: loď",
                        "Loď",
                        new String[]{"Přistav", "Břeh"},
                        new String[]{"Sud", "Rum", "Dělo", "sirka"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi Břeh",
                        "Přestunul jste se do: břeh",
                        "Břeh",
                        new String[]{"Loď", "Vodopád"},
                        new String[]{"Krab", "Ulita"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi Vodopád",
                        "Přestunul jste se do: vodopád",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "použi lopata",
                        "Začal jste rýt a našel jste truhlici.",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl", "Truhlice"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "použi klíč",
                        "Použil jste klíč a odemknul jste truhlice.",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl", "Truhlice"},
                        new String[]{"Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "otevři truhlice",
                        "Otevřel jste truhlici a teď máte přistup k jejimu obsahu",
                        "Truhlice",
                        new String[]{},
                        new String[]{"Deník"},
                        new String[]{"Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi deník",
                        "Deník je teď v vašem batohu.",
                        "Truhlice",
                        new String[]{},
                        new String[]{},
                        new String[]{"Mapa", "Deník"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "zavři  truhlice",
                        "Zavřel jste truhlici.",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl", "Truhlice"},
                        new String[]{"Mapa", "Deník"}
                ),
                new ScenarioStep(TypeOfStep.tsNON_STANDARD,
                        "přečti deník",
                        "Přečetl jste deník. V deníku je napsano, jak se můžete dostat k pramenu večného mládí.",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl", "Truhlice"},
                        new String[]{"Mapa", "Deník"}
                ),
                new ScenarioStep(TypeOfStep.tsEND,
                        "konec",
                        "Děkuju, že jste zkusil mou hru!",
                        "Vodopád",
                        new String[]{"Břeh"},
                        new String[]{"Hůl", "Truhlice"},
                        new String[]{"Mapa", "Deník"}
                )
            };

    static {
        ScenarioStep.setIndex(2);
    }

    /**
     * *************************************************************************
     * Základní chybový scénář definující reakce na povinnou sadu chybových
     * stavů.
     */
    private static final ScenarioStep[] MISTAKE_SCENARIO_STEPS
            = {
                START_STEP,
                new ScenarioStep(TypeOfStep.tsNOT_START,
                        "start",
                        "Tento příkaz neznám.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsUNKNOWN,
                        "zemři",
                        "Tento příkaz neznám.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsEMPTY,
                        "",
                        "Zadal(a) jste prázdný příkaz.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsBAD_NEIGHBOR,
                        "jdi kosmos",
                        "Odsud se tam nedá přejít.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsBAD_OBJECT,
                        "vezmi okurek",
                        "Takový předmět tady není.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi lopata",
                        "Lopata je teď v vašem batohu.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Provaz", "Láhev"},
                        new String[]{"Klíč", "Mapa", "Lopata"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP,
                        "vezmi láhev",
                        "Láhev je teď v vašem batohu.",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Provaz"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsBAG_FULL,
                        "vezmi provaz",
                        "Váš batoh je plný",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Provaz"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi džungle",
                        "Přestunul jste se do: džungle.\nNěkdo k vám podešel a dal rozkaz\n"
                        + ", abyste mu předal(a) všechno, co máte.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsUNMOVABLE,
                        "vezmi palma",
                        "Tenhle předmět se nedá zvednout.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsNOT_IN_BAG,
                        "polož palma",
                        "Takový předmět nemáte v batohu.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsPICK_UP_WA,
                        "vezmi",
                        "Nebyl zadán objekt, který se má zvednout.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsPUT_DOWN_WA,
                        "polož",
                        "Nebyl zadán objekt, který se má položit.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE_WA,
                        "jdi",
                        "Nebyl zadán cílový prostor přesunu.",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(TypeOfStep.tsHELP,
                        "?",
                        "\nPříkazy, které je možno v průběhu hry zadat:"
                        + "\n============================================================================\n"
                        + "           ->  Příkaz startující hru.\n"
                        + "    použi  ->  Přikaz,který umožňuje hráče použit předmť v batohu\n"
                        + "   otevři  ->  Přikaz, dovolující hráčovi otevírat některé předměty\n"
                        + "      jdi  ->  Přesune uživatele do zadaného sousedního prostoru\n"
                        + "     mluv  ->  Přikaz,který umožňuje hráčovi vest dialog s herními postavámi\n"
                        + "    polož  ->  Položí objekt do aktuálního prostoru\n"
                        + "   přečti  ->  Přikaz, dovolující hráčovi číst některé předměty\n"
                        + "    zavři  ->  Přikaz, dovolující hráčovi zavírat některé předměty\n"
                        + "    zabij  ->  Přikaz, kterým může hráč zabít nějakou osobu ve hře\n"
                        + "_TEST_10_  ->  Příkaz pro realizaci 10. domácího úkolu\n"
                        + "    konec  ->  příkaz ukončující hru\n"
                        + "    vezmi  ->  Vezmi objekt v aktuálním prostoru\n"
                        + "        ?  ->  Napověda\n",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                ),
                new ScenarioStep(tsEND,
                        "konec",
                        "Děkuju, že jste zkusil mou hru!",
                        "Džungle",
                        new String[]{"Město", "Jeskyně"},
                        new String[]{"Palma", "Kamen", "Vrah"},
                        new String[]{"Klíč", "Mapa", "Lopata", "Láhev"}
                )
            };

    private static final ScenarioStep[] SCENARIO_STEPS_HW_10
            = {
                START_STEP,
                new ScenarioStep(tsNON_STANDARD, "_TEST_10_",
                        "Přesunul(a) jste se do testovacího prostoru",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsNOT_IN_BAG, "polož NOT_IN_BAG",
                        "Takový předmět nemáte v batohu.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsPUT_DOWN, "polož 1",
                        "Položil(a) jste předmět: 1",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL", "1"},
                        new String[]{"2", "3", "4"}
                ),
                new ScenarioStep(tsPICK_UP, "vezmi 1",
                        "1 je teď v vašém batohu.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsBAG_FULL, "vezmi BAG_FULL",
                        "Váš batoh je plný",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsBAD_OBJECT, "vezmi BAD_OBJECT",
                        "Takový předmět tady není.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsPICK_UP_WA, "vezmi",
                        "Nebyl zadán objekt, který se má zvednout.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsPUT_DOWN_WA, "polož",
                        "Nebyl zadán objekt, který se má položit.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsMOVE_WA, "jdi",
                        "Nebyl zadán cílový prostor přesunu.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsBAD_NEIGHBOR, "jdi _TEST_10_",
                        "Odsud se tam nedá přejít.",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(tsHELP, "?",
                        "\nPříkazy, které je možno v průběhu hry zadat:"
                        + "\n============================================================================\n"
                        + "           ->  Příkaz startující hru.\n"
                        + "    použi  ->  Přikaz,který umožňuje hráče použit předmť v batohu\n"
                        + "   otevři  ->  Přikaz, dovolující hráčovi otevírat některé předměty\n"
                        + "      jdi  ->  Přesune uživatele do zadaného sousedního prostoru\n"
                        + "     mluv  ->  Přikaz,který umožňuje hráčovi vest dialog s herními postavámi\n"
                        + "    polož  ->  Položí objekt do aktuálního prostoru\n"
                        + "   přečti  ->  Přikaz, dovolující hráčovi číst některé předměty\n"
                        + "    zavři  ->  Přikaz, dovolující hráčovi zavírat některé předměty\n"
                        + "    zabij  ->  Přikaz, kterým může hráč zabít nějakou osobu ve hře\n"
                        + "_TEST_10_  ->  Příkaz pro realizaci 10. domácího úkolu\n"
                        + "    konec  ->  příkaz ukončující hru\n"
                        + "    vezmi  ->  Vezmi objekt v aktuálním prostoru\n"
                        + "        ?  ->  Napověda\n",
                        "_TEST_10_",
                        new String[]{"Jeskyně"},
                        new String[]{"BAG_FULL"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(TypeOfStep.tsMOVE,
                        "jdi jeskyně",
                        "Přestunul jste se do jeskyně",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"1", "2", "3", "4"}
                ),
                new ScenarioStep(TypeOfStep.tsEND,
                        "konec",
                        "Děkuju, že jste zkusil mou hru!",
                        "Jeskyně",
                        new String[]{"Džungle"},
                        new String[]{"Lopata", "Provaz", "Láhev"},
                        new String[]{"1", "2", "3", "4"}
                ),};

    /**
     * Jediná instance této třídy. Spravuje všechny scénáře sdružené hry.
     */
    private static final Scenario MANAGER = new Scenario();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================
//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================
    /**
     * *************************************************************************
     * Vrátí správce scénářů - jedinou instanci této třídy.
     *
     * @return Správce scénářů
     */
    public static Scenario getInstance() {
        return MANAGER;
    }

    /**
     * *************************************************************************
     * Vytvoří instanci představující správce scénářů hry.
     */
    private Scenario() {
        super(AUTHOR_NAME, AUTHOR_ID, CLASS);

        addScenario("Happy",
                TypeOfScenario.scHAPPY, HAPPY_SCENARIO_STEPS);
        addScenario("Mistake",
                TypeOfScenario.scMISTAKES, MISTAKE_SCENARIO_STEPS);
        addScenario("DU_10",
                TypeOfScenario.scGENERAL, SCENARIO_STEPS_HW_10);
        seal();
    }

//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
    /**
     * *************************************************************************
     * Základní test ověřující, jestli správce scénářů vyhovuje zadaným
     * okrajovým podmínkám, tj. jestli:
     * <ul>
     * <li>Umí vrátit správně naformátované jméno autora/autorky hry a jeho/její
     * xname.</li>
     * <li>Definuje základní úspěšný a základní chybový scénář.</li>
     * <li>Základní chybový scénář má následující vlastnosti:
     * <ul>
     * <li>Startovací příkaz, jehož název je prázdný řetězec</li>
     * <li>Minimální požadovaný počet kroků</li>
     * <li>Minimální počet navštívených místností</li>
     * <li>Minimální počet "zahlédnutých" místností</li>
     * <li>Použití příkazů pro přechod z prostoru do prostoru zvednutí nějakého
     * objektu a položení nějakého objektu</li>
     * </ul>
     * </li>
     * <li>Základní chybový scénář má následující vlastnosti:
     * <ul>
     * <li>Pokus o odstartování hry jiným než startovacím příkazem</li>
     * <li>Startovací příkaz, jehož název je prázdný řetězec</li>
     * <li>Použití všech povinných chybových typů kroku definovaných ve třídě
     * {@link cz.vse.adv_framework.scenario.TypeOfStep}</li>
     * <li>Vyvolání nápovědy</li>
     * <li>Ukončení příkazem pro nestandardní ukončení hry</li>
     * </ul>
     * </li>
     * </ul>
     */
    public static void testMyScenarioManager() {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.testScenarioManager();
    }

    /**
     * *************************************************************************
     * Simuluje hraní hry podle základního úspěšného a základního chybového
     * scénáře.
     */
    public static void simulateBasicScenarios() {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.simulateScenario(MANAGER.getScenario(0), false);
        test.simulateScenario(MANAGER.getScenario(1), false);
        test.simulateScenario(MANAGER.getScenario(2), false);
    }

    /**
     * *************************************************************************
     * Testování funkce hry prováděné postupně prostřednictvím všech scénářů
     * daného správce
     */
    public static void testMyGame() {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.testGame();
//        IGame     hra  = Game.getInstance();
//        _Test_101 test = _Test_101.getInstance(hra);
//        test.testGame();
    }

    /**
     * *************************************************************************
     * Nejprve otestuje daného správce scénářů a pak otestuje hru podle scénáře
     * se zadaným názvem.
     *
     * @param name Název scénáře, podle nějž bude hra otestována
     */
    public static void testGameByScenario(String name) {
        _Test_101 test = _Test_101.getInstance(MANAGER);
        test.playGameByScenario(name);
    }

    /**
     * @param args Parametry příkazového řádku - nepoužívané.
     */
    public static void main(String[] args) {
        testGameByScenario(
                //                  "_HAPPY_"
                "_MISTAKE_"
        );
//        testMyScenarioManager();
//        simulateBasicScenarios();
//        testMyGame();
        System.exit(0);
    }
}
