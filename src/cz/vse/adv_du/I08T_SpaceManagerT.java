/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_du;

import cz.vse.adv_framework.game_txt.IPlace;

import java.util.Collection;



/*******************************************************************************
 * Instance interfejsu {@code I08T_SpaceManagerT} představují správce prostorů
 * v textové konverzační hře.
 *
 * <b>Třídy implementující tento interfejs musejí definovat svoji instanci
 * jako jedináčka, kterého lze získat zavoláním tovární metody
 * {@code getInstnace()}.</b>
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public interface I08T_SpaceManagerT extends I08_SpaceManager
{
//== CONSTANTS =================================================================
//== ABSTRACT GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí odkaz na prostor se zadaným názvem.
     *
     * @param name Název prostoru, na nějž požadujeme odkaz
     * @return Odkaz na prostor se zadaným názvem
     */
//    @Override
    public IPlace getPlace(String name);



//== OTHER ABSTRACT METHODS ====================================================
//== EMBEDDED DATA TYPES =======================================================
}
