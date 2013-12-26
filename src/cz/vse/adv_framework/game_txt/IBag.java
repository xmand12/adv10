/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_txt;

import java.util.Collection;



/*******************************************************************************
 * Instance rozhraní {@code IBag} představují úložiště,
 * do nichž hráči ukládají objekty sebrané v jednotlivých prostorech,
 * aby je mohli přenést do jiných prostorů a/nebo použít.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public interface IBag extends IObjectContainer
{
//== CONSTANTS =================================================================
//== GETTERS AND SETTERS =======================================================

    /***************************************************************************
     * Vrátí kapacitu batohu, tj. součet vah objektů,
     * které je možno současně uložit do batohu.
     *
     * @return Kapacita batohu
     */
//    @Override
    public int getCapacity()
    ;


    /***************************************************************************
     * Vrátí kolekci objektů uložených v batohu.
     *
     * @return Kolekce objektů v batohu
     */
    @Override
    public Collection<? extends IObject> getObjects()
    ;



//== OTHER METHODS =============================================================
//== EMBEDDED DATA TYPES =======================================================
}
