package cz.vse.adv_framework.test_util.default_game.gameg;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandEnd} představuje příkaz k ukončení hry.
 * Zabezpečí, že budou vráceny všechny prostředky a nastaveny příznaky tak,
 * aby hra mohla být znovu odstartována.
 *
 * @author    Rudolf PECINOVSKY
 * @version   4.00,  17.3.2007
 */
public class CommandEnd extends ACommand
{
//== KONSTANTNI ATRIBUTY TRIDY =================================================
//== PROMENNE ATRIBUTY TRIDY ===================================================
//== KONSTANTNI ATRIBUTY INSTANCI ==============================================
//== PROMENNE ATRIBUTY INSTANCI ================================================
//== NESOUKROME METODY TRIDY ===================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

    /***************************************************************************
     * Vytrvoří příkaz ukončující hru.
     */
    CommandEnd()
    {
        super(pKONEC, //"Konec",
               "Ukončí celou hru a vrátí všechny prostředky.");
    }


//== ABSTRAKTNI METODY =========================================================
//== NESOUKROME METODY INSTANCI ================================================

    /***************************************************************************
     * Ukončí hru, uvolní všechny alokované prostředky a připraví vše pro to,
     * aby mohla být hra znovu spuštěna.
     *
     * @param  parametry Nepracuje s zadanými parametry
     * @return Zprava o ukončení hry
     */
    @Override
    public String execute(String... parametry)
    {
        DefaultGame.getInstance().stop();
        return zKONEC;
//        return "Konec hry. Děkujeme, že jste zkusil(a) naši hru.";
    }



//== SOUKROME A POMOCNE METODY TRIDY ===========================================
//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
