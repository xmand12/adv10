/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.util.Comparator;

/*
import static cz.vse.adv_framework.
              utilities.CompareIgnoreCase.CIC;
*/
/*******************************************************************************
 * Instance třídy {@code CompareIgnoreCase} přestavují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CompareIgnoreCase implements Comparator<String>
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Instance, kterou mohou všechny třídy sdílet. */
    public static final CompareIgnoreCase CIC = new CompareIgnoreCase();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    public CompareIgnoreCase()
    {
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /** {@inheritDoc} */
    @Override
    public int compare(String o1, String o2)
    {
        return o1.compareToIgnoreCase(o2);
    }


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
//        CompareIgnoreCase inst = new CompareIgnoreCase();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
