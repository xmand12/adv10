package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.INamed;
import java.util.ArrayList;

/*******************************************************************************
 * Instance třídy {@code ListINamed} představují seznamy pojmenovaných objektů,
 * které je možno vyhledávat i podle jejich názvu.
 *
 * @param <E> Skutečný typ uložených objektů
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
class ListINamed<E extends INamed> extends ArrayList<E>
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový, prázdný seznam.
     */
    public ListINamed()
    {
    }

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
public E getINamed(String name)
    {
        for (E element : this) {
            String n = element.getName();
            if (n.equalsIgnoreCase(name)) {
                return element;
            }
        }
        return null;
    }
}
