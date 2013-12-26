package cz.vse.adv_framework.test_util.default_game.gamet;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance tridy {@code CommandPut} predstavuje prikaz
 * k vyjmuti predmetu z batohu a jeho umisteni v aktualni mistnosti.
 * Aby byl prikaz proveden, musi byt zadan nazev predmetu
 * a predmet zadaneho nazvu musi byt aktualne v batohu.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class CommandPut extends ACommand
{
//== KONSTANTNI ATRIBUTY TRIDY =================================================
//== PROMENNE ATRIBUTY TRIDY ===================================================
//== KONSTANTNI ATRIBUTY INSTANCI ==============================================
//== PROMENNE ATRIBUTY INSTANCI ================================================
//== NESOUKROME METODY TRIDY ===================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

    /***************************************************************************
     * Vytvori prikaz, po jehoz zadani se hrac pokusí položit zadaný předmět.
     * Tento předmět musí být v daném okamžiku v batohu.
     */
    CommandPut()
    {
        super(pPOLOŽ, //"Polož",
           "Příkaz vyjme předmět z batohu a odloží jej v aktuální místnosti.\n"+
           "Aby byl příkaz proveden, musí byt zadán název předmětu\n"+
           "a předmět zadaného názvu musí byt aktuálně v batohu.");
    }


//== ABSTRAKTNI METODY =========================================================
//== NESOUKROME METODY INSTANCI ================================================

    /***************************************************************************
     * Pokusi se vyjmout z batohu a polozit v mistnosti predmet zadaneho nazvu;
     * před tím však zkontroluje, je-li zadán předmět, který se má položit,
     * a je-li tento předmět v batohu.
     *
     * @param  parametry Nazev pokladaneho predmetu
     * @return Text zpravy vypsane po porovedeni prikazu
     */
    @Override
    public String execute(String... parametry)
    {
        if ((parametry != null)      &&
            (parametry.length > 0))
        {
            String   nazev   = parametry[0];
            DefaultGame      hra     = DefaultGame.getInstance();
            Bag    batoh   = hra.getBag();
            Something  predmet = batoh.vyjmi(nazev);
            if (predmet == null) {
                return zNENÍ_V_BATOHU + nazev;              //==========>
//                return "Předmět není v batohu: " + nazev;   //==========>
            }
            Room am = Room.getAktuálníMístnost();
            am.polož(predmet);
            return zPOLOŽENO + predmet.getName();
//            return "Položil(a) jste předmět: " + predmet.getName();
        }                                                   //==========>
        return zPŘEDMĚT_NEZADAN;
//        return "Je třeba zadat název pokládaného předmětu"; //==========>
    }


//== SOUKROME A POMOCNE METODY TRIDY ===========================================
//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
