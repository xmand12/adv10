/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;

import cz.vse.IAuthor;

import cz.vse.adv_framework.game_txt.IGame;

import java.net.URL;

import java.util.Collection;

import javax.swing.ImageIcon;



/*******************************************************************************
 * Instance rozhraní {@code IGameG} mají na starosti logiku hry.
 * Jsou schopny akceptovat jednotlivé příkazy a poskytovat informace
 * o průběžném stavu hry a jejích součástech.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IGameG extends IGame
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí URL adresu stránky s nápovědou.
     *
     * @return URL adresa stránky s nápovědou
     */
//    @Override
    public URL getHelpURL();


    /***************************************************************************
     * Vrátí obrázek s plánkem prostoru, v němž se hraje.
     *
     * @return Obrázek s plánkem prostoru
     */
//    @Override
    public ImageIcon getMap();


    /***************************************************************************
     * Vrátí obrázek hráče, který bude zobrazován na plánku hry,
     * aby uživatel věděl, ve kterém prostoru se jeho hráč aktuálně nachází.
     *
     * @return Obrázek hráče
     */
//    @Override
    public ImageIcon getPlayer();


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny objektů v batohu.
     *
     * @param bagListener Přihlašovaný posluchač
     */
//    @Override
    public void addBagListener(IListener<IBagG> bagListener);


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny objektů v batohu.
     *
     * @param bagListener Odhlašovaný posluchač
     */
//    @Override
    public void removeBagListener(IListener<IBagG> bagListener);


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny objektů v aktuální prostoru.
     *
     * @param objectListener Přihlašovaný posluchač
     */
//    @Override
    public void addObjectListener(IListener<IPlaceG> objectListener);


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny objektů v aktuálním prostoru.
     *
     * @param objectListener Odhlašovaný posluchač
     */
//    @Override
    public void removeObjectListener(IListener<IPlaceG> objectListener);


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny sousedů aktuálního prostoru.
     *
     * @param neighborsListener Přihlašovaný posluchač
     */
//    @Override
    public void addNeighborsListener(IListener<IPlaceG> neighborsListener);


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny sousedů aktuálního prostoru.
     *
     * @param neighborsListener Odhlašovaný posluchač
     */
//    @Override
    public void removeNeighborsListener(IListener<IPlaceG> neighborsListener);


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * kterým hra oznamuje začátek a konec rozhovoru.
     *
     * @param dialogListener Přihlašovaný posluchač
     */
//    @Override
    public void addDialogListener(IListener<Boolean> dialogListener);


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * kterým hra oznamuje začátek a konec rozhovoru.
     *
     * @param dialogListener Odhlašovaný posluchač
     */
//    @Override
    public void removeDialogListener(IListener<Boolean> dialogListener);



//== ZDĚDĚNÉ METODY ============================================================

    //Metody her implementujících toto rozhraní musejí vracet instance,
    //resp. kolekce instancí implementujících rozhraní v tomto balíčku
    //Proto je třeba je tu deklarovat znovu. protože deklarované
    //návratové hodnoty jsou potomky návratových hodnot metod rodiče

    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v niž se pravé nachází hráč.
     *
     * @return Požadovaný odkaz
     */
    @Override
    public IPlaceG getCurrentPlace();


    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané předměty.
     *
     * @return Požadovaný odkaz
     */
    @Override
    public IBagG getBag();


    /***************************************************************************
     * Vrátí kolekci odkazů na jednotlivé místnosti.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<? extends IPlaceG> getAllPlaces();



//== VNOŘENÉ TŘÍDY =============================================================
}
