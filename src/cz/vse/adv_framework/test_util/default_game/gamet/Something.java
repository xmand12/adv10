package cz.vse.adv_framework.test_util.default_game.gamet;

import cz.vse.adv_framework.game_txt.IObject;



/*******************************************************************************
 * Instance třídy {@code Something} představuji předměty v místnostech.
 * Některé z nich jsou přenositelné, jiné ne.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class Something implements IObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Prefix určující, že předmět nepůjde zvednout. */
    public static final char HEAVY = '#';

    /** Prefix určující, že se jedná o alkoholický nápoj. */
    public static final char ALCOHOL = '@';

    /** Univerzální předmět zastupující všechny nezvednutelné předměty. */
    public static final Something MASS = new Something(HEAVY + "MASS");



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    private final boolean   transportable;
    private final String    name;
    private final boolean   alcoholic;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový předmět se zadaným názvem, "vahou"
     * a informací o tom, zda se jedná a alkoholický nápoj.
     * Je-li prvním znakem názvu předmětu znak # označující nezvednutelné
     * předměty, bude tento znak z názvu odebrán
     * a předmětu bude označen za nezvednutelný -- dostane váhu -1.
     * Je-li prvním znakem názvu předmětu znak @ označující alkoholické nápoje,
     * bude tento znak z názvu odebrán
     * a předmětu bude označen za alkoholický nápoj s vahou 1.
     *
     * @param name Název předmětu <br>
     *             - začíná-li znakem #, není předmět zvednutelný,<br>
     *             - začíná-li znakem @, jedná se o alkoholický nápoj
     */
    public Something(String name)
    {
        boolean a = false,
                t = true;

        switch (name.charAt(0))
        {
            case HEAVY:
                name = name.substring(1);
                t = false;
                break;

            case ALCOHOL:
                name = name.substring(1);
                a = true;
                break;
        }
        this.name          = name;
        this.alcoholic     = a;
        this.transportable = t;
    }



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Název instance
     */
    @Override
    public String getName()
    {
        return name;
    }


    /***************************************************************************
     * Vrátí hypotetickou váhu předmětu.
     * Všechny zvednutelné předměty mají váhu 1, nezvednutelné mají váhu -1.
     *
     * @return Hypotetická váha předmětu
     */
    @Override
    public int getWeight()
    {
        return transportable ? 1 : -1;
    }


    /***************************************************************************
     * Vrátí informaci o tom, je-li daný předmět alkoholickým nápojem.
     *
     * @return Jedná-li se o alkoholický nápoj, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    public boolean isAlkoholic() {
        return alcoholic;
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Název instance
     */
    @Override
    public String toString()
    {
        return name;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//          Something instance = new Something();
//     }
//     /** @param args Parametry příkazového řádku - nepoužité. */
//     public static void main(String... args) { test(); }
}
