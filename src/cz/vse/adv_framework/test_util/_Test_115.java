/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IUIG;

import cz.vse.adv_framework.scenario.Scenario;



/*******************************************************************************
 * Instance třídy {@code _Test_115} testují dvojici objektů
 * představujících hru a grafické uživatelské rozhraní.
 * Umožňují je otestovat jak samostatně, tak ve vzájemné souhře.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class _Test_115 extends Triumvirate
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Testované GUI. */
    private final IUIG thisGUI;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Prověří, jestli zadaná hra vyhovuje požadavkům rozhraní {@code IGameG},
     * a oznámí výsledek.
     *
     * @param game Prověřovaná hra
     * @return Dopadnou-li dobře všechny testy, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    public static boolean verifyGame(IGameG game)
    {
        _Test_115 test = _Test_115.getInstance(game);
        return test.verifyGame();
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří testovací instanci pro testování možnosti
     * spouštět zadanou hru na implicitním GUI.
     *
     * @param gameClass Testovaná třída
     * @return Vrátí požadovanou testovací instanci
     */
    public static _Test_115 getInstance(Class<? extends IGameG> gameClass)
    {
        IGameG    game = (IGameG) _Test_101.getInstanceOfGame(gameClass);
        _Test_115 test = new _Test_115(game, getDefaultGUI());
        return test;
    }


    /***************************************************************************
     * Vytvoří testovací instanci pro testování možnosti
     * spouštět zadanou hru na zadaném GUI.
     *
     * @param game Testovaná hra
     * @param gui  Testované GUI
     * @return Vrátí požadovanou testovací instanci
     */
    public static _Test_115 getInstance(IGameG game, IUIG gui)
    {
        return new _Test_115(game, gui);
    }


    /***************************************************************************
     * Vytvoří testovací instanci pro testování možnosti
     * spouštět zadanou hru na implicitním GUI.
     *
     * @param game Testovaná hra
     * @return Vrátí požadovanou testovací instanci
     */
    public static _Test_115 getInstance(IGameG game)
    {
        return new _Test_115(game, getDefaultGUI());
    }


    /***************************************************************************
     * Vytvoří testovací instanci pro testování možnosti
     * spouštět implicitní hru na zadaném GUI.
     *
     * @param gui  Testované GUI
     * @return Vrátí požadovanou testovací instanci
     */
    public static _Test_115 getInstance(IUIG gui)
    {
        return new _Test_115(getDefaultGame(), gui);
    }


    /***************************************************************************
     * Vytvoří testovací instanci pro testování možnosti
     * spouštět zadanou hru na zadaném GUI.
     *
     * @param game Testovaná hra
     * @param gui  Testované GUI
     */
    private _Test_115(IGameG game, IUIG gui)
    {
        super(fromGame(game));
        verifyImplementsIGameG();
        this.thisGUI = gui;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zahraje na daném GUI danou hru
     * podle scénáře určeného pro testování GUI.
     */
    public void playGameByTestScenario()
    {
        playGameGByScenario(gameG, manager.getGuiTestScenario(), thisGUI);
    }


    /***************************************************************************
     * Zahraje na daném GUI danou hru
     * podle scénáře se zadaným indexem.
     *
     * @param i  Index scénáře, podle nějž se hra bude hrát
     */
    public void playGameByScenario(int i)
    {
        playGameGByScenario(gameG, manager.getScenario(i), thisGUI);
    }


    /***************************************************************************
     * Zahraje na daném GUI danou hru
     * podle scénáře se zadaným názvem.
     *
     * @param name Název scénáře, podle nějž se hra bude hrát
     */
    public void playGameByScenario(String name)
    {
        playGameGByScenario(gameG, manager.getScenario(name), thisGUI);
    }


    /***************************************************************************
     * Zahraje na daném GUI danou hru
     * podle zadaného scénáře.
     *
     * @param scenario Scénář, podle nějž se hra bude hrát
     */
    public void playGameByScenario(Scenario scenario)
    {
       playGameGByScenario(gameG, scenario, thisGUI);
    }


    /***************************************************************************
     * Prověří, zda daná hra vyhovuje požadavkům rozhraní {@code IGameG},
     * a vrátí informaci o výsledku provedených testů.
     *
     * @return Dopadnou-li dobře všechny testy, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    public boolean verifyGame()
    {
        GameGClassTest gameClassTest = new GameGClassTest(getCrate());
        return gameClassTest.verifyGame();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        _Test_115 inst = new _Test_115();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
