package cz.vse.adv_framework.test_util.default_game.gameg;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandPick} představuje příkaz
 * k odebrání předmětu z aktuální místnosti a jeho umístění do batohu.
 * Aby byl příkaz proveden, musí být zadán název předmětu,
 * který se v aktuální místnosti vyskytuje, předmět musí být zvednutelný
 * a v batohu musí být ještě volné místo.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class CommandPick extends ACommand
{
//== KONSTANTNI ATRIBUTY TRIDY =================================================
//== PROMENNE ATRIBUTY TRIDY ===================================================
//== KONSTANTNI ATRIBUTY INSTANCI ==============================================
//== PROMENNE ATRIBUTY INSTANCI ================================================
//== NESOUKROME METODY TRIDY ===================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

    /***************************************************************************
     * Vytvoří příkaz <b>vezmi</b>.
     */
    CommandPick()
    {
        super(pVEZMI, //"Vezmi",
           "Příkaz odebere předmět zadaného názvu z aktuální místnosti\n" +
           "a umístí jej do batohu.\n" +
           "Aby byl příkaz proveden, musí byt\n" +
           "- zadán název předmětu, který se v místnosti vyskytuje,\n" +
           "- předmět musí byt zvednutelný a\n" +
           "- v batohu musí byt ještě volné místo.");
    }


//== ABSTRAKTNI METODY =========================================================
//== NESOUKROME METODY INSTANCI ================================================

    /***************************************************************************
     * Vezme z místnosti předmět zadaného názvu a umístí je do batohu;
     * nejprve však zkontroluje, zda byl vůbec zadán název předmětu,
     * který se má zvednout, zda je tento předmět zvednutelný a zda se další
     * předmět ještě vejde do batohu.
     *
     * @param  parametry Název zvedaného předmětu
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0) {
            return zPŘEDMĚT_NEZADAN;
//            return "Je třeba zadat název zvedaného předmětu";   //==========>
        }
        String   nazev   = parametry[0];
        DefaultGame      hra     = DefaultGame.getInstance();
        Room am      = hra.getCurrentPlace();
        Thing  predmet = am.zvedni(nazev);
        if (predmet == null) {
            return zNENÍ_PŘEDMĚT + nazev;
//            return "Zadaný předmět v místnosti není: " + nazev; //==========>
        }
        if (predmet == Thing.MASS) {
            return zTĚŽKÝ_PŘEDMĚT + nazev;
//            return "Zadaný předmět nejde zvednout: " + nazev;   //==========>
        }
        Bag batoh = hra.getBag();
        if (! batoh.add(predmet)) {
            am.polož(predmet);
            return zBATOH_PLNÝ;
//            return "Zadaný předmět nemůžete vzít, máte už obě ruce plné.";
        }                                                       //==========>
        if (am.equals(Room.Lednička)  &&  predmet.isAlkoholic()) {
            am.polož(predmet);
            batoh.remove(nazev);
            return Dialog.start(predmet);
        }
        return zZVEDNUTO + predmet.getName();
//        return "Vzal(a) jste předmět: " + predmet.getName();   //==========>
    }


//== SOUKROME A POMOCNE METODY TRIDY ===========================================
//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
