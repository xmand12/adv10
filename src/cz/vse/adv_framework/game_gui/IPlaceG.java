/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;

import cz.vse.adv_framework.game_txt.IPlace;

import java.awt.Point;

import java.util.Collection;


/*******************************************************************************
 * Instance rozhraní {@code IPlaceG} přestavují místnosti či jejich
 * ekvivalenty. Dosažení místnosti si můžeme představovat jako částečný cíl,
 * kterého se hráč ve hře snaží dosáhnout.
 * Místnosti mohou obsahovat různé předměty,
 * které mohou hráči pomoci v dosažení cíle hry.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IPlaceG extends IPlace, IObjectContainerG
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí pozici místnosti na plánu hry,
     * presněji pozici hráče nacházejícícho se v dané místnosti.
     * Na této pozici bude hráč při pobytu v dané místnosti zobrazen.
     *
     * @return Požadovaná pozice
     */
//    @Override
    public Point getPosition();



//== ZDĚDĚNÉ METODY ============================================================

    //Metody místností implementujících toto rozhraní musejí vracet
    //kolekce instancí implementujících rozhraní v tomto balíčku
    //Proto je třeba je tu deklarovat znovu. protože deklarované
    //návratové hodnoty jsou potomky návratových hodnot metod rodiče


    /***************************************************************************
     * Vrátí kolekci prostorů, do nichž je možno se z tohoto prostoru přesunout.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<? extends IPlaceG> getNeighbors();



//== VNOŘENÉ TŘÍDY =============================================================
}
