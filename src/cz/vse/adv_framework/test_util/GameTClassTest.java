/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.scenario.AScenarioManager;


/*******************************************************************************
 * Instance třídy {@code GameTClassTest} slouží k testování
 * her implementujících rozhraní {@code IGame} a ovladatelných podle scénářů
 * spravovaných instancí potomka třídy {@link AScenarioManager}.
 * Instance však pouze prověřují základní povinné vlastnosti hry, tj.
 * správnou implementaci rozhraní {@code IGame} včetně dodatečných kontraktů,
 * aniž by se snažily spustit některý z definovaných scénářů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class GameTClassTest
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zjistí, jestli třída zadaná svým class-objektem definuje statickou
     * tovární metodu {@code getInstance} nebo {@code getGame} nebo
     * {@code getHra} požádá nalezenou metodu o instanci dané třídy.
     *
     * @param cls Class-objekt třídy, jejíž instanci chceme získat
     * @return Instance zadané třídy
     * @throws IllegalArgumentException
     *         Třída nevyhovuje požadavkům na třídu definující jedináčka
     *         nebo se nepodaří získat požadovanou instanci
     *         anebo na druhý dotaz vrátí jinou instanci
     * @throws SecurityException
     *         Nemáme právo přehrabovat se v zadaném class-objektu
     */
    public static IGame getInstanceOfGame(Class<?> cls)
           throws IllegalArgumentException
    {
        TestUtilitiy.verifyPrivateConstructor(cls);
        return (IGame)TestUtilitiy.verifyFactoryMethod(cls);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor zabraňující vytvoření instance. */
    private GameTClassTest() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
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
//        GameTClassTest inst = new GameTClassTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
