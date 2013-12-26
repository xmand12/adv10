/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;

import cz.vse.adv_framework.game_txt.INamed;

import java.util.ArrayList;



/*******************************************************************************
 * Instance třídy {@code ListINamed} představují seznamy pojmenovaných objektů,
 * které je možno vyhledávat i podle jejich názvu.
 *
 * @param <E> Skutečný typ uložených objektů
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
@SuppressWarnings("serial")
class ListINamed<E extends INamed> extends ArrayList<E>
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
     * Vytvoří nový, prázdný seznam.
     */
    ListINamed()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Zjistí, jestli se v seznamu nachází objekt se zadaným názvem,
     * a vrátí odkaz na něj nebo {@code null}.
     * Při testu na název hledaného objektu se nehledí na velikost písmen.
     *
     * @param name Název hledaného objektu
     * @return Najde-li v seznamu objekt se zadaným názvem, vrátí odkaz na něj,
     *         v opačném případě vrátí {@code null}
     */
    E getINamed(String name)
    {
        for (E element : this) {
            String n = element.getName();
            if (n.equalsIgnoreCase(name)) {
                return element;
            }
        }
        return null;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
