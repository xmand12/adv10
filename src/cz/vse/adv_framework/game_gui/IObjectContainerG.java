/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;

import cz.vse.adv_framework.game_txt.*;

import java.util.Collection;



/*******************************************************************************
 * Instance rozhraní {@code IObjectContainerG} představují kontejnery,
 * které mohou obsahovat objekty vystupující ve hře.
 * Speciálními případy těchto kontejnerů jsou prostory a batohy.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IObjectContainerG extends IObjectContainer
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném kontejneru.
     * Oproti rodičovské metodě vrací kolekci objektů implementujících
     * rozhraní {@link IObjectG}.
     *
     * @return Kolekce objektů nacházejících se v daném kontejneru
     */
    @Override
    public Collection<? extends IObjectG> getObjects();



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
