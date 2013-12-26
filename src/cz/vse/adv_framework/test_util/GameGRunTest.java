/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IUIG;
import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.ScenarioStep;

import cz.vse.adv_framework.utilities.IO;


import static cz.vse.adv_framework.Common.*;



/*******************************************************************************
 * Instance třídy {@code GameGRunTest} slouží k testování
 * her implementujících rozhraní {@code IGameG} a ovladatelných podle scénářů
 * spravovaných instancí potomka třídy {@link AScenarioManager}.
 * Instance však pouze prověřují základní povinné vlastnosti hry, tj.
 * správnou implementaci rozhraní {@code IGameG} včetně dodatečných kontraktů,
 * aniž by se snažily spustit některý z definovaných scénářů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
class GameGRunTest
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final Converter converter = new Converter();

    private static final String SEPARATOR = "\n\n=========================\n\n";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        IO.placeWindowsAt(CONTROL_WINDOW_POSITION.x, CONTROL_WINDOW_POSITION.y);
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zahraje zadanou hru podle zadaného scénáře, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param game     Hra, která se bude hrát podle zadaného scénáře
     * @param scenario Scénář, podle nějž se hra bude hrát
     * @param gui      Instance GUI, která bude zobrazovat průběh hry
     *                 a vůči níž bude testovací program vystupovat v roli hráče
     */
    static void playGameByScenario(IGame game, Scenario scenario, IUIG gui)
    {
        AScenarioManager       sm = scenario.getManager();
        Class<? extends IGame> gc = game.getClass();
        Triumvirate.verifyTriumvirate(sm, gc, game);

        if (game.isAlive()) {
            IO.message("Hra hlásí, že právě běží.\n\n"   +
                       "Před novým spuštěním je třeba\n" +
                       "nejprve ukončit minulý běh hry.");
        }

        String last   = "";
        String prolog =
            "Hra: " + converter.full2shortName(game.getClass().getName()) +
          "\nGUI: " + converter.full2shortName( gui.getClass().getName()) +
        "\n\nAutor(ka) hry: " + game.getAuthorName() +
          "\nAutor(ka) GUI: " + gui .getAuthorName() + SEPARATOR +
            "Minulý příkaz: «";

        for(;;) {
            gui.startGame(game);
            boolean first = true;
            for (ScenarioStep step : scenario) {
                if (first) {
                    first = false;
                    continue;
                }
                String question = prolog + last +
                                 "»\n\nNyní zadávám:  «" + step.command + "»";
                if(finish(question)) {
                    break;
                }
//                if (! IO.agree(command)) { break; }
                gui.executeCommand(step.command);
                last = step.command;
            }
            boolean answer;
            if (! game.isAlive()) {
                answer = IO.agree("Hra je ukončena - mám ji spustit znovu?");
            }
            else {
                answer = IO.agree("Hra není ukončena"
                                + "\nmám ukončit a spustit znovu?");
            }
            if (answer) {
                game.stop();
                continue;
            }
            break;
        }
    }


//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor zabraňující vytvoření instance. */
    private GameGRunTest() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Metoda zobrazí dialogové okno se zadanou zprávou a umožni testeru,
     * aby rozhodl o pokračování nebo ukončení testu.
     *
     * @param message Zpráva vypisované v otevřeném dialogovém okně
     * @return {@code true} má-li se končit s testováním,
     *         {@code false} má-li se zkusit další test
     */
    private static boolean finish(String message) {
        String  prompt = message + SEPARATOR + "Mám pokračovat?";
        boolean answer = IO.agree(prompt);
        return  ! answer;
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Instance třídy představuje konvertor, který z úplného názvu třídy odvodí
     * název, kterým se bude třída prezentovat v seznamu v dialogovém okně.
     */
    private static class Converter implements ClassCollector.Convertor
    {
        /***********************************************************************
         * Za zadaného úplného názvu třídy odvodí zkrácenou verzi,
         * která danou třídu dostatečně identifikuje.
         * Tato implementace odstraňuje z názvu vrchní vrstvy balíčků
         * až po balíček specifikující daného autora, tj. po balíček,
         * jehož název začíná autorovým sname. Umožňuje tak identifikovat
         * třídu i v případě, kdy ji různí autoři pojmenují shodně.
         *
         * @param fullName Plný název třídy
         * @return Název zkráceny k balíčku s xname
         */
        @Override
        public String full2shortName(String fullName) {
            int index = fullName.indexOf(".x") + 1;
            if (index <= 0) {
                index = fullName.indexOf("advent");
            }
            if (index < 0) {
                index = 0;
            }
            String shortName = fullName.substring(index);
            return shortName;
        }
    }



//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        GameTxtClassTest inst = new GameTxtClassTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
