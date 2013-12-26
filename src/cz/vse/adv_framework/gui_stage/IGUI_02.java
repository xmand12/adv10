/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.gui_stage;

import cz.vse.adv_framework.game_gui.IUIG;



/*******************************************************************************
 * Instance tříd implementujících značkovací rozhraní {@code GUI_02}
 * představují jednoduché grafické uživatelské rozhraní umožňující
 * hrát hry vyhovující podmínkám rámce (frameworku) definovaného v balíčku
 * {@code cz.vse.adv_framework.game_txt},
 * tj. textové hry vyhovující požadavkům minulého semestru.
 * <p>
 * Třída implementující toto rozhraní se zavazuje k následujícímu:
 * <ul>
 *   <li>Třídy sice musí formálně implementovat rozhraní
 *     {@link cz.vse.adv_framework.game_gui.IGameG},
 *     ale stále ještě nemusí být jeho plnohodntou implementací,
 *     protože mají být schopny a ochotny obsluhovat i hry implementující
 *     rozhraní {@link cz.vse.adv_framework.game_txt.IGame}.
 *   </li>
 *   <li>GUI musí používat správce rozvržení {@link java.awt.BorderLayout},
 *     přičemž jeho jednotlivá pole budou uspořádána následovně:.
 *     <ul>
 *       <li>V severní oblasti bude panel batohu typu {@link javax.swing.Box}
 *         uspořádaný vodorovně a zobrazující objekty v batohu.
 *         Obsah panelu bude uvozen popiskem.
 *       </li>
 *       <li>V západní oblasti bude panel typu {@link javax.swing.Box}
 *         uspořádaný svisle a zobrahující sousedy aktuálního prostoru.
 *         Obsah panelu bude uvozen popiskem.
 *       </li>
 *       <li>Ve východní oblasti bude panel typu {@link javax.swing.Box}
 *         uspořádaný svisle a zobrahující objekty v aktuálním prostoru.
 *         Obsah panelu bude uvozen popiskem.
 *       </li>
 *       <li>Ve jižní oblasti bude panel typu {@link javax.swing.JPanel}
 *         zobrahující vstupní pole příkazů s příslušným popiskem.
 *       </li>
 *       <li>V centrální oblasti bude panel výstupu tvořený oblastí typu
 *         {@link javax.swing.JTextArea} zabalenou do rolovače typu
 *         {@link javax.swing.JScrollPane}.
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IGUI_02 extends IUIG
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================
//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
