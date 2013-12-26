/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IGame;



/*******************************************************************************
 * Třída {@code DefaultGameTest} slouží k testování implicitní hry
 * a současně i k prověřování testů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class DefaultGameTest
{
    static final String T_101 = "101";



    /***************************************************************************
     * Otestuje implicitní hru základním, chybovým a kontrolním scénářem
     * (kontrolní scénář si test_101 vygeneruje sám).
     */
    public static void test_101()
    {
        IGame game = Triumvirate.getDefaultGame();
        _Test_101 test = _Test_101.getInstance(game);
        test.testGame();
    }


    /***************************************************************************
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        test_101();
    }

    /** Soukromý konstruktor zabraňující vytvoření instance.*/
    private DefaultGameTest() {}
}
