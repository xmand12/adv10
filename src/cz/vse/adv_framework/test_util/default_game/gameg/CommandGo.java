package cz.vse.adv_framework.test_util.default_game.gameg;

import  cz.vse.adv_framework.game_txt.IPlace;
import java.util.Collection;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandGo} představuje příkaz
 * k přesunu z aktuální mistosti do zadané místnosti. Aby byl příkaz proveden,
 * musí byt zadaná místnost z aktuální místnosti dosažitelná.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public class CommandGo extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final String NEPROVEDENO = zNENÍ_CIL;
//        "Akce nebyla provedena. Nebyla zadána místnost, do níž se má přejít.";

    private static final String NELZE = zNENÍ_CIL;
//            "Do zadané místnosti se odsud nedá přejít.";

    private static final String PRESUN = zPŘESUN;
//            "Přesunul(a) jste se do místnosti: ";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci mající na starosti reakci na příkaz <b>jdi</b>,
     * po jehož zadaní se hráč přesune do místnosti,
     * jejíž název bude uveden za příkazem.
     */
    public CommandGo()
    {
        super(pJDI, //"Jdi",
               "Přesune hráče do zadané místnosti.\n" +
               "Tato místnost musí být dostupná z aktuální místnosti.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přesune hráče do zadané cílové místnosti;
     * před tím však zkontroluje, zda byla zadána cílová místnost a
     * zda je do ní možno se v daném okamžiku z aktuální místnosti přesunout.
     *
     * @param parametry Místnost, kam se hráč chce přesunout
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0) {
            return NEPROVEDENO;     //==========>
        }
        Room aktuální = Room.getCurrentRoom();
        Collection<? extends IPlace> sousedé = aktuální.getNeighbors();
        Room cil = Room.getRoom(parametry[0]);
        if ((cil == null)  ||  (! sousedé.contains(cil))){
            return NELZE;           //==========>
        }
        Room.setCurrentRoom(cil);
        return PRESUN + cil;        //==========>
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
