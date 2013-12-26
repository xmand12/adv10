/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.gui_stage;

import cz.vse.adv_framework.game_gui.IGameG;



/*******************************************************************************
 * Instance tříd implementujících značkovací rozhraní {@code GUI_03}
 * představují jednoduché grafické uživatelské rozhraní umožňující
 * hrát hry vyhovující podmínkám rámce (frameworku) definovaného v balíčku
 * {@code cz.vse.adv_framework.game_gui}.
 * <p>
 * Třída implementující toto rozhraní se zavazuje k následujícímu:
 * <ul>
 *   <li>Výstupní textové pole ve střední části aplikačního okna
 *     změní oproti požadavkům rozhrnaí {@link IGUI_02} svůj typ
 *     z {@code JTextArea} na {@code JEditorPane}.
 *   </li>
 *   <li>Panely sousedů aktuálního prostoru, objetků v aktuálním prostoru
 *     a objektů v batohu budou definovány prostřednictvím samostatných tříd,
 *     které budou potomky třídy {@link javax.swing.Box}
 *     a které budou implementovat rozhraní {@code IGUI_03_Box}.
 *   </li>
 *   <li>Gui bude mít hlavní nabídku, která bude instancí třídy
 *     {@link javax.swing.JMenuBar}.
 *   </li>
 *   <li>Tato nabídka bude mít tři položky, přičemž každá z nich bude definována
 *     v samostatné třídě, jež bude potomkem třídy {@link javax.swing.JMenu}.
 *     Tyto nabídky budou obsahovat následující položky:
 *     <ul>
 *       <li>Nabídka <b>Hra</b>:
 *         <ul>
 *           <li><b>Nová hra</b> otevře podnabídku dostupných heru
 *             a umožní hráči vybrat si v ní hru, kterou následně spustí.
 *           </li>
 *           <li><b>Restart hry</b> ukončí aktuální hru a spustí ji znovu
 *           </li>
 *           <li><b>Konec hry</b>
 *             Ukončí aktivní hru, aniž by ukončoval celou aplikaci.
 *           </li>
 *           <li><b>Konec</b> ukončí celou aplikaci.
 *           </li>
 *         </ul>
 *       </li>
 *       <li>Nabídka <b>Záznam</b> bude prozatím neaktivní
 *       </li>
 *       <li>Nabídka <b>Nápověda</b>:
 *         <ul>
 *           <li><b>Uživatelská příručka</b> otevře v novém okně nápovědu,
 *             jejíž URL získá od aktuální hry.
 *           </li>
 *           <li><b>O aplikaci</b> zobrazí jednoduché dialogové okno,
 *             v němž uživatele oznámí název hry a jejího autora.
 *           </li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 *   <li>Třídy nabídek budou mít jednoparametrický konstruktor
 *     jehož parametr bude instancí rozhraní {@link IGUI_03}.
 *     V tomto kontruktoru získají odkaz na GUI, pro něž pracují.
 *   </li>
 * </ul>
 *
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IGUI_03 extends IGUI_02
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí naposledy zadaný příkaz.
     *
     * @return Text napsoledy zadnaého příkazu
     */
//    @Override
    public String getCommand();


    /***************************************************************************
     * Vrátí odkaz na naposledy/právě hranou hru.
     *
     * @return Odkaz na naposledy/právě hranou hru
     */
//    @Override
    public IGameG getGame();



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
