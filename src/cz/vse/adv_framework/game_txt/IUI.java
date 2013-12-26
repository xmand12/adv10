/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_txt;



/*******************************************************************************
 * Rozhraní {@code IUI} deklaruje požadavky na třídu
 * definující uživatelské rozhraní (User Interface).
 * <p>
 * Zde deklarovaná metoda {@link #startGame(IGame)} slouží pouze k zadání hry,
 * která se má spustit a s níž má rozhraní komunikovat.
 * Vlastní komunikace s hrou pak probíhá prostřednictvím opakovaných volání
 * metody {@link IGame#executeCommand(String)},
 * které UI předá příkaz zadaný uživatelem a od níž pak převezme zprávu,
 * kterou následné vypíše uživateli.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   4.00
 */
public interface IUI
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Spustí komunikaci mezi implicitní hrou
     * a danou instancí uživatelského rozhraní.
     */
//    @Override
    public void startGame();


    /***************************************************************************
     * Spustí komunikaci mezi zadanou hrou a danou instancí
     * mající na starosti komunikaci s uživatelem.
     *
     * @param game Hra, kterou ma dané UI spustit
     */
//    @Override
    public void startGame(IGame game);



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
