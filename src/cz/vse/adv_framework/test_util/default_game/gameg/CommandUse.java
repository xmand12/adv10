package cz.vse.adv_framework.test_util.default_game.gameg;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandUse} představuje jednoúčelový příkaz
 * k nasazení brýlí. Příkaz má redundantní parametr, který určuje,
 * co (si) má hráč nasadit. Mohli bychom jej považovat za otevřenou možnost
 * pro budoucí rozšíření.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CommandUse extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci mající na strosti reakci na příkaz <b>jdi</b>,
     * po jehož zadaní se hráč přesune do místnosti,
     * jejíž název bude uveden za příkazem.
     */
    public CommandUse()
    {
        super(pNASAĎ, //"Nasaď",
               "Jednoúčelový příkaz sloužící k nasazení brýlí.\n" +
               "Brýle je však třeba zadat jako parametr a držet je v ruce.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Nasadí zadaný předmět; před tím však zkontroluje,
     * že byl zadán předmět, který má nasadit, že tento předmět hráč drží
     * a že tímto předmětem jsou brýle.
     *
     * @param parametry Jadiným současným povoleným parametrem jsou brýle
     * @return Text zpravy vypsane po provedeni prikazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0) {
            return "Není zadán předmět, který se má nasadit.";  //==========>
        }
        String predmet = parametry[0];
        if (! DefaultGame.getInstance().getBag().contains(predmet)) {
            return "Zadaný předmět nelze nasadit, protože jej nedržíte: " +
                   predmet;                                     //==========>
        }
        if (! predmet.equalsIgnoreCase("brýle")) {
             return "Předmět " + predmet + " není možno nasadit.";//==========>
        }
        State.BRYLE_NASAZENY = true;
        DefaultGame.getInstance().getBag().remove("brýle");
        return "Nasadil(a) jste si brýle.";                     //==========>
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
