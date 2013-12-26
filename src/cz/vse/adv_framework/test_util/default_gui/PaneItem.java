/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IObjectG;
import cz.vse.adv_framework.game_txt.INamed;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



/*******************************************************************************
 * Instance třídy {@code PaneItem} představují komponenty
 * sloužící k zobrazení jim zadaného objektu.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
public class PaneItem extends JLabel
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název zobrazovaného prvku. */
    private final String caption;


//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří komponentu zobrazující název zadaného předmětu
     * spolu s jeho obrázkem.
     *
     * @param object Zobrazovaný předmět
     */
    public PaneItem(IObjectG object)
    {
        super(object.getName(), object.getPicture(), SwingConstants.CENTER);
        caption = inicalize(object);
    }


    /***************************************************************************
     * Vytvoří komponentu zobrazující název zadaného pojemnovaného objektu
     * (většinou místnosti).
     *
     * @param object Zobrazovaný objekt
     */
    public PaneItem(INamed object)
    {
        super(object.getName(), SwingConstants.CENTER);
        caption = inicalize(object);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název zobrazovaného objetku.
     *
     * @return Název zobrazovaného objetku
     */
    public String getName()
    {
        return caption;
    }


    /***************************************************************************
     * Inicializuje vytvářenou komponentu a vrátí název zobrazovaného objektu.
     * Přestavuje společnou část kódu obou verzí konstruktoru.
     *
     * @param object Zobrazovaný objekt
     * @return Název zobrazovaného objetku
     */
    private String inicalize(INamed object)
    {
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.TOP);
        return object.getName();
    }



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
//        PaneItem inst = new PaneItem();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
