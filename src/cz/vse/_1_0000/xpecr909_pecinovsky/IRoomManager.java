/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import java.util.Collection;



/*******************************************************************************
 * Instances of interface {@code IRoomManager} represent ...
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
interface IRoomManager
{
//== CONSTANTS =================================================================
//== GETTERS AND SETTERS =======================================================

    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
//    @Override
    public Collection<? extends IRoom> getAllPlaces()
    ;


    /***************************************************************************
     * Vrátí odkaz na aktuální prostor,
     * tj. na prostor, v němž se hráč pravé nachází.
     *
     * @return Prostor, v němž se hráč pravé nachází
     */
//    @Override
    public IRoom getCurrentPlace()
    ;


    /***************************************************************************
     * Vrátí odkaz na prostor se zadaným názvem.
     *
     * @param name
     * @return Odkaz na prostor se zadaným názvem
     */
//    @Override
    public IRoom getPlace(String name)
    ;



//== OTHER METHODS =============================================================

    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
//    @Override
    public void initialize()
    ;



//== EMBEDDED DATA TYPES =======================================================
}
