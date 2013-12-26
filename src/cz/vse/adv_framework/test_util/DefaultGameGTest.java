/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_txt.IGame;



/*******************************************************************************
 * Třída {@code DefaultGameGTest} slouží k testování implicitní hry
 * a současně i k prověřování testů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class DefaultGameGTest
{
    private static final String T_115 = "115";



    /***************************************************************************
     * Otestuje zadanou hru základním, chybovým a kontrolním scénářem
     * (kontrolní scénář si test_101 vygeneruje sám).
     */
    public static void test_115()
    {
        IGameG    game = Triumvirate.getDefaultGame();
        _Test_115 test = _Test_115.getInstance(game);
        boolean verified = test.verifyGame();
        if (verified) {
            test.playGameByTestScenario();
        }
    }


    /***************************************************************************
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        test_115();
    }

    /** Soukromý konstruktor zabraňující vytvoření instance.*/
    private DefaultGameGTest() {}
}
