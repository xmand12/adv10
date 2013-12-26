/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IUIG;

import java.awt.Component;

import java.awt.Rectangle;



/*******************************************************************************
 * Instance rozhraní {@code IMyGUI} představují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IMyGUI extends IUIG
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí odkaz na aktuálně ovládanou hru.
     *
     * @return Odkaz na aktuálně ovládanou hru
     */
//    @Override
    public IGameG getGame();


    /***************************************************************************
     * Vrátí aktuální stav zobrazování plánku hry.
     *
     * @return Aktuální stav zobrazování plánku hry
     */
//    @Override
    public boolean isGameMapVisible();


    /***************************************************************************
     * Nastaví, zda se bude během hry zobrazovat plánek hry.
     *
     * @param zobrazovat {@code true} = bude se zobrazovat plánek hry
     */
//    @Override
    public void setGameMapVisible(boolean zobrazovat);


    /***************************************************************************
     * Vrátí aktuální rozměr okna hry.
     *
     * @return Aktuální rozměr okna hry
     */
//    @Override
    public Rectangle getArea();


    /***************************************************************************
     * Vrátí komponentu, která může sloužit jako rodič
     * otevíraných dialogových oken.
     *
     * @return Rodičovská komponenta
     */
//    @Override
    public Component getParents();



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
