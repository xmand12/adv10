/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.gui_stage;

import cz.vse.adv_framework.game_gui.IGameG;



/*******************************************************************************
 * Instance tříd implementujících značkovací rozhraní {@code GUI_04}
 * představují jednoduché grafické uživatelské rozhraní umožňující
 * hrát hry vyhovující podmínkám rámce (frameworku) definovaného v balíčku
 * {@code cz.vse.adv_framework.game_gui}.
 * <p>
 * Třída implementující toto rozhraní se zavazuje k následujícímu:
 * <ul>
 *   <li>Třídy, jejichž instance zobrazují panely se soused aktuálního prostoru,
 *     objekty v aktuálním prostoru a objekty v batohu budou mít
 *     společného abstraktního rodiče.
 *   </li>
 *   <li>Místo návěští budou ve výše zmíněných panelech tlačítka,
 *    jejichž stisk vyvolá následující akci:
 *     <ul>
 *       <li>Stisk tlačítka v panelu objektů v batohu
 *         vyvolá položení příslušného předmětu.
 *       </li>
 *       <li>Stisk tlačítka v panelu objektů v aktuálním prostoru
 *         vyvolá zvednutí příslušného předmětu.
 *       </li>
 *       <li>Stisk tlačítka v panelu sousedů aktuálního prostoru
 *         vyvolá přesun hráče do daného prostoru.
 *       </li>
 *     </ul>
 *   </li>
 *   <li>Třídy nabídek budou mít jednoparametrický konstruktor
 *     jehož parametr bude instancí rozhraní {@link IGUI_04}.
 *     V tomto parametru získají odkaz na GUI, pro něž pracují.
 *   </li>
 * </ul>
 *
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IGUI_04 extends IGUI_03
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================
//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
