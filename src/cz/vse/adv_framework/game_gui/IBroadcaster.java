/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;



/*******************************************************************************
 * Instance rozhraní {@code IBroadcaster} představuji hlasatele
 * oznamujícího přihlášeným posluchačům výskyt události, na niž čekají.
 *
 * @param <Informant> Typ objektu obsahujícího informace o události,
 *                    kterou hlasatel oznamuje svým posluchačům
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IBroadcaster<Informant>
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které zpravuje o výskytu očekávané události.
     *
     * @param listener Přihlašovaný posluchač
     */
//    @Override
    public void addListener(IListener<Informant> listener);


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které zpravuje o výskytu očekávané události.
     *
     * @param listener Odhlašovaný posluchač
     */
//    @Override
    public void removeListener(IListener<Informant> listener);



//== INHERITED METHODS =========================================================
//== VNOŘENÉ TŘÍDY =============================================================
}
