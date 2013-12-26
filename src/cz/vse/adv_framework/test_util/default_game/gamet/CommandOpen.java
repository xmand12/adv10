package cz.vse.adv_framework.test_util.default_game.gamet;



/*******************************************************************************
 * Instance třídy {@code CommandOpen} představuje příkaz používaný
 * k otevření nějakého předmětu. V dané hře je témto předmětem vždy lednička
 * v kuchyni, ale obecně lze hru rozšířit o možnost otevření dalších
 * předmětů v dalších místnostech.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CommandOpen extends ACommand
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
    public CommandOpen()
    {
        super("Otevři",
               "Otevře zadaný předmět.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Otevře zadaný předmět; před tím však zkontroluje,
     * zda je zadán otevíraný předmět, zda tímto předmětem je lednička v kuchyni
     * a zda je lednička již podloženqa, aby šla otevřít.
     *
     * @param parametry Jediným povoleným parametrem je zatím lednička
     * @return Text zpravy vypsane po porovedeni prikazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0)  {
            return "Je třeba říct, který předmět se má otevřít.";//==========>
        }
        if ((!parametry[0].equalsIgnoreCase("lednička"))  ||
            Room.getAktuálníMístnost() != Room.Kuchyň)
        {
            return "Otevřít je možno pouze ledničku v kuchyni"; //==========>
        }
        if (! State.LEDNICKA_PODLOZENA) {
            return "Lednička nejde otevřít. " +
                    "Na ledničce leží nějaký popsaný papír.";   //==========>
        }
        Room.setAktuálníMístnost(Room.Lednička);
        return "Úspěšně jste otevřel(a) ledničku.";
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        PrikazJdi x = new PrikazJdi();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
