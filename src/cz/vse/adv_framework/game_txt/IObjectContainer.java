/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_txt;

import java.util.Collection;



/*******************************************************************************
 * Instance rozhraní {@code IObjectContainer} představují kontejnery,
 * které mohou obsahovat objekty vystupující ve hře.
 * Speciálními případy těchto kontejnerů jsou prostory a batoh.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public interface IObjectContainer
{
//== CONSTANTS =================================================================
//== GETTERS AND SETTERS =======================================================

    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném kontejneru.
     *
     * @return Kolekce objektů nacházejících se v daném kontejneru
     */
//    @Override
    public Collection<? extends IObject> getObjects();



//== OTHER METHODS =============================================================
//== EMBEDDED DATA TYPES =======================================================
}
