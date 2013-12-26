/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;



/*******************************************************************************
 * Instance třídy {@code DBG_Formater} představují formátovače zpráv
 * zaznamenávaných příslušným loggerem.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class DBG_Formater extends Formatter
{
//== CONSTANT CLASS ATTRIBUTES =================================================
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
    public DBG_Formater()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Předá na výstup zformátovanou obdrženou zprávu
     * bez jakýchkoliv dodatečých textů.
     *
     * @param record Záznam se zprávou
     * @return Výsledný formátovaný text
     */
    @Override
    public String format(LogRecord record)
    {
        return formatMessage(record) + '\n';
    }



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
//        DBG_Formater inst = new DBG_Formater();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
