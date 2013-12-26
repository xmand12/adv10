/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IGame;



/*******************************************************************************
 * Třída {@code TestOfGivenGame} je hlavní třídou projektu,
 * sloužící k zadání testované hry přímo do zdrojového kódu
 * a k jejímu následnému otestování.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class TestOfGivenGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor zabraňující vytvoření instance. */
    private TestOfGivenGame() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Otestuje zadanou hru základním, chybovým a kontrolním scénářem
     * (kontrolní scénář si test vygeneruje sám).
     * Zavolání testu je definováno jako samostatná metoda,
     * aby je bylo možno v {@code BlueJ} přímo zavolat.
     *
     * @param hra Testovaná hra
     */
    public static void test(IGame hra)
    {
        _Test_101 test = _Test_101.getInstance(hra);
        test.testGame();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================

    /***************************************************************************
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        IGame hra;
        if (args.length == 0) {
            //Zde můžete dosadit příkaz pro získání instance vaší hry
            hra = null;
        }
        else {
            //Bude se testovat hra, jejíž název program je v příkazovém řádku
            hra = _Test_101.getInstanceOfGame(args[0]);
        }
        test(hra);
    }

}
