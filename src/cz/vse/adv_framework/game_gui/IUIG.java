/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;

import cz.vse.IAuthor;

import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.game_txt.IUI;



/*******************************************************************************
 * Rozhraní {@code IUIG} deklaruje požadavky na třídu definující uživatelské
 * rozhraní (User Interface); vedlejším požadavkem je,
 * <b>ABY TŘÍDA MĚLA IMPLICITNÍ, TJ. VEŘEJNÝ BEZPARAMETRICKÝ KONSTRUKTOR</b>.
 * Tento požadavek sice nemůže být kontrolován překladačem,
 * ale je kontrolován testovacími programy.
 * <p>
 * Metody {@link #startGame(IGame)} a {@link #startGame()} deklarované v tomto
 * rozhraní slouží k zadání a spouštění hry,
 * s níž má grafické uživatelské rozhraní komunikovat.
 * <p>
 * Vlastní komunikace s touto hrou může probíhat nejenom
 * přímým zadáváním příkazů prostřednictvím daného GUI,
 * ale pro účely testování také nepřímo prostřednictvím programu
 * opakovaně volajícího metodu {@link IGame#executeCommand(String)},
 * které program předá text příkazu simulovaně zadaného uživatelem
 * a od niž pak převezme zprávu, kterou následné vypíše testerovi.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IUIG extends IUI, IAuthor
{
//== CONSTANTS =================================================================
//== POŽADOVANÉ METODY INSTANCI ================================================

    /***************************************************************************
     * Vrátí aktuálně hranou hru.
     *
     * @return Aktuálně hraná hra
     */
    public IGame getGame();


    /***************************************************************************
     * Zpracuje příkaz předaný v parametru jako by byl zadán standardním
     * postupem z klávesnice. Metoda umožňuje testovat reakce daného
     * uživatelského rozhraní na příkazy zadávané z klávesnice.
     *
     * @param command Zadávaný příkaz
     */
//    @Override
    public void executeCommand(String command);



//== METODY ZDĚDĚNÉ OD PŘEDKU ==================================================

    /***************************************************************************
     * Spustí komunikaci mezi zadanou hrou a danou instancí GUI
     * mající na starosti komunikaci s uživatelem;
     * nejprve však zkontroluje, že zadaná hra je ve skutečnosti
     * instancí rozhraní {@link IGameG},
     * protože jiné neposkytují metody, které grafické UI očekává.
     *
     * @param game Hra, kterou ma dané UI spustit
     * @throws IllegalArgumentException Hra není instancí {@link IGameG}
     */
    @Override
    public void startGame(IGame game);



//== VNOŘENÉ TŘÍDY =============================================================
}
