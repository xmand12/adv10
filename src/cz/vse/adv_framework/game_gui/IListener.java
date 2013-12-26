/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;



/*******************************************************************************
 * Instance rozhraní {@code IListener} představují posluchače
 * očekávající nějakou událost.
 *
 * @param <Informant> Typ objektu poskytujícího informace o události,
 *                    na kterou posluchač čeká
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IListener<Informant>
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Hlášeni o výskytu očekávané události.
     *
     * @param informant Objekt, který je schopen poskytnout informace 
     *                  o události, kterou zavolání dané metody ohlašuje
     */
//    @Override
    public void notice(Informant informant);



//== ZDĚDĚNÉ METODY ============================================================
//== VNOŘENÉ TŘÍDY =============================================================
}
