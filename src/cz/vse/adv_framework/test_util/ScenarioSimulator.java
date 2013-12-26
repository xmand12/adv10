/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.ScenarioStep;

import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.scenario.AScenarioManager;

import static cz.vse.adv_framework.utilities.FormatStrings.*;



/*******************************************************************************
 * Třída <b>{@code ScenarioSimulator}</b> je knihovní třídou obsahující testy,
 * které jsou schopny:
 * <ul>
 *   <li>otestovat scénář hry, zda vyhovuje požadavkům,
 *   <li>simulovat spuštění scénáře hry, aby bylo názorně vidět,
 *      jak by měla hra podle daného scénáře probíhat,
 *   <li>otestovat hru podle zadaného scénáře, tj. nakolik reakce hry
 *      na příkazy ze scénáře odpovídají požadavkům scénáře.
 * </ul>
 *
 * @author    Rudolf PECINOVSKY
 * @version   3.00,  22.11.2006
 */
class ScenarioSimulator extends ATest
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
//== NESOUKROMÉ METODY TŘÍDY ===================================================

    /***************************************************************************
     * Simuluje spuštění hry podle zadaného scénáře,
     * přičemž umožňuje zadat podrobnost výpisu.
     *
     * @param scenario  Simulovaný scénář
     * @param inDetail  Má-li se vypisovat stručný či podrobný podpis kroků
     */
    public static void simulateScenario(Scenario scenario, boolean inDetail)
    {
        AScenarioManager ss = scenario.getManager();
        DBG.info(N_HASHES_N +
                    "########## S{0}imulace scénáře"
                + "\n########## Autor správce scénářů:      {1}"
                + "\n########## Název simulovaného scénáře: {2}"
                + N_LINE_N,
                inDetail ?  ""  :  "tručná s",
                ss.getAuthorName(), scenario.getName());
        for (ScenarioStep krok : scenario)
        {
            if (inDetail) {
                DBG.info(LINE_0, krok);
            }
            else {
                DBG.info(LINE_0, krok.commandAndMessage());
            }
        }
        DBG.info(LINE_N+
                    "########## Konec {0}simulace scénáře:"
                + "\n########## Autor správce scénářů:      {1}"
                + "\n########## Název simulovaného scénáře: {2}"
                + N_HASHES_N,
                inDetail ?  ""  :  "stručné ",
                ss.getAuthorName(), scenario.getName());
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Knihovní třída. */
    private ScenarioSimulator() {}



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================
//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTY =====================================================================
//
//     /***************************************************************************
//      *
//      */
//     public static void test()
//     {
//         ScenarioSimulator instance = new ScenarioSimulator();
//     }
//    /** @param args Parametry příkazového řádku - nepoužité. */
//     public static void main(String... args) { test(); }
}
