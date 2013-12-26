/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework;

import java.awt.Point;

/*
import static cz.vse.adv_framework.Common.*;
*/
/*******************************************************************************
 * Knihovní třída {@code Common} obsahuje konstanty a metody
 * sdílené různými třídami napříč aplikací.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class Common
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Výchozí pozice dotazovacího okna. */
    public static final Point CONTROL_WINDOW_POSITION = new Point(1000, 000);

    /** Pozice aplikačního okna
     *  připravená práci na počítači s více monitory. */
    public static final Point START_WINDOW_POSITION = new Point(0, -00);

    /** Jméno autora zapsané předepsaným způsobem,
     *  tj. příjmení velkými následované křestním s pouze prvním velkým. */
    public static final String AUTHOR = "PECINOVSKÝ Rudolf";

    /** XNAME autora zapsaný velkými písmeny. */
    public static final String XNAME = "XPECR999";

    /** ClassLoader definující umístění kořenového balíčku. */
    public static final ClassLoader CLASS_LOADER =
                                    Common.class.getClassLoader();

    /** Adresa složky se zdroji relativně vůči složce s kořenovým balíčkem. */
    public static final String CESTA_K_DATŮM;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        Package balíček = Common.class.getPackage();
        String cesta = balíček.getName().replace(".", "/");
        CESTA_K_DATŮM = cesta + "/data/";
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Soukromý konstruktor bránící vytvoření instance
     */
    private Common() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        Common inst = new Common();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
