/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;



/*
import static cz.vse.adv_framework.
              utilities.FormatStrings.*;
*/
/*******************************************************************************
 * Instance třídy {@code FormatStrings} představují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class FormatStrings
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    public static final char NL = '\n';

    public static final String

    LINE =
"-----------------------------------------------------------------------------",

    DOUBLELINE =
"=============================================================================",

    HASHES =
"#############################################################################",

    CIRCUMFLEXES =
"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^",

    /** Oddělovač vypisovaný před vlastní chybovou zprávou. */
    BERFORE =
"VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV",

    /** Oddělovač vypisovaný za vlastní chybovou zprávou. */
    AFTER =
"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",

    LINE_N   =        LINE + '\n',
    N_LINE   = '\n' + LINE,
    N_LINE_N = '\n' + LINE + '\n',
    LINE_0   =        LINE + "{0}",

    DOUBLELINE_N    =          DOUBLELINE + '\n',
    N_DOUBLELINE    =   '\n' + DOUBLELINE,
    N_DOUBLELINE_N  =   '\n' + DOUBLELINE + '\n',
    NN_DOUBLELINE   = "\n\n" + DOUBLELINE,
    NN_DOUBLELINE_NN= "\n\n" + DOUBLELINE + "\n\n",

    HASHES_N   =        HASHES + '\n',
    N_HASHES   = '\n' + HASHES,
    N_HASHES_N = '\n' + HASHES + '\n',

    CIRCUMFLEXES_N   =        CIRCUMFLEXES + '\n',
    N_CIRCUMFLEXES   = '\n' + CIRCUMFLEXES,
    N_CIRCUMFLEXES_N = '\n' + CIRCUMFLEXES + '\n',

    BERFORE_N   =        BERFORE + '\n',
    N_BERFORE   = '\n' + BERFORE,
    N_BERFORE_N = '\n' + BERFORE + '\n',

    AFTER_N   =        AFTER + '\n',
    N_AFTER   = '\n' + AFTER,
    N_AFTER_N = '\n' + AFTER + '\n',

    EMPTY_STRING=""; //Abych nemusel přemýšlet nad čárkami



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor zabraňující vytvoření instance.*/
    private FormatStrings() {}



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
//        FormatStrings inst = new FormatStrings();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
