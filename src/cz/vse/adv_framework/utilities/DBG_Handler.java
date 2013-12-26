/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.io.OutputStream;
import java.util.logging.StreamHandler;



/*******************************************************************************
 * Instance třídy {@code DBG_Handler} představují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class DBG_Handler extends StreamHandler
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================

    //Výstupní proud, kam bude logger zapisovat. */
    private static OutputStream outputStream = System.out;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Nastavý implicitní výstupní proud, který bude od této chvíle
     * nastavován všem instancím.
     * Dříve vytvořené instance nové nastavení neovlivní.
     *
     * @param output Nastavovaný výstupní proud
     */
    public static void setDefaultOutputStream(OutputStream output)
    {
        outputStream = output;
    }


//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    public DBG_Handler()
    {
        super(outputStream, new DBG_Formater());
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        DBG_Handler inst = new DBG_Handler();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
