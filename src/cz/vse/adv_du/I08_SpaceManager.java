/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_du;

import cz.vse.adv_framework.game_txt.IPlace;

import java.util.Collection;



/*******************************************************************************
 * Instance interfejsu {@code I08_SpaceManager} představují správce prostorů
 * v textové konverzační hře.
 * 
 * <b>Třídy implementující tento interfejs musejí mít definován
 * veřejný bezparametrický konstruktor. </p>
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public interface I08_SpaceManager
{
//== CONSTANTS =================================================================
//== ABSTRACT GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
//    @Override
    public Collection<? extends IPlace> getAllPlaces()
    ;



//== OTHER ABSTRACT METHODS ====================================================

    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
//    @Override
    public void initialize()
    ;



//== EMBEDDED DATA TYPES =======================================================
}
