/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.gui_stage;



/*******************************************************************************
 * Instance rozhraní {@code IGUI_03_Pane} představují panely,
 * které jsou potomky třídy {@link javax.swing.Box}
 * a vystupují jako panely prostorů, resp. objektů v aplikacích
 * implementujících rozhraní {@link IGUI_03}
 * <p>
 * Třída musí navíc definovat konstruktor,
 * jehož parametr bude instancí rozhraní {@link IGUI_03}. V tomto konstruktoru
 * získají vytvářené instance odkaz na GUI, pro něž pracují.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IGUI_03_Pane
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Inicializuje danou oblast.
     */
//    @Override
    public void initialize();


    /***************************************************************************
     * Aktualizuje danou oblast.
     */
//    @Override
    public void update();



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}


