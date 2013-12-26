package cz.vse.adv_framework.test_util.default_game.gameg;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandClose} představuje příkaz používaný
 * k zavření nějakého předmětu. V dané hře je tímto předmětem vždy lednička
 * v kuchyni, ale obecně lze hru rozšířit o možnost zavření dalších
 * předmětů v dalších místnostech.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CommandClose extends ACommand
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
    public CommandClose()
    {
        super(pZAVŘI, //"Zavři",
               "Zavře zadaný předmět.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zavře zadaný předmět; před tím však zkontroluje,
     * zda je zadán zavíraný předmět, zda hráč právě prohlíží útroby tohoto
     * předmětu (zda je v místnosti představované zavíraným předmětem)
     * a zda tímto předmětem je lednička v kuchyni.
     *
     * @param parametry Jediným povoleným parametrem je zatím lednička
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0)  {
            return "Je třeba říct, který předmět se má zavřít.";//==========>
        }
        if ((!parametry[0].equalsIgnoreCase("lednička"))  ||
            Room.getCurrentRoom() != Room.Lednička)
        {
            return "Zavřít je možno pouze otevřenou ledničku";  //==========>
        }
        Room.setCurrentRoom(Room.Kuchyň);
        return ZAVŘEL_LEDNIČKU;
//        return "Úspěšně jste zavřel(a) ledničku.";              //==========>
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
