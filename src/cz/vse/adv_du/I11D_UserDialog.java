/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_du;



/*******************************************************************************
 * Instance interfejsu {@code IUserDialog} představují objekty
 * schopné zprostředkovat komunikaci s uživatelem hrajícím hru.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public interface I11D_UserDialog
{
//== CONSTANTS =================================================================
//== ABSTRACT GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Inicializuje objekt realizující komunikaci s uživatelem.
     */
//    @Override
    public void init();


    /***************************************************************************
     * Předá uživateli zadanou zprávu a převezme od něj binární odpověď
     * (Ano / Ne) na zadanou otázku.
     *
     * @param message Zpráva předávaná uživateli
     * @return Odpověď uživatele
     */
//    @Override
    public boolean confirm(String message);


    /***************************************************************************
     * Předá uživateli zadanou zprávu.
     *
     * @param message Zpráva předávaná uživateli
     */
//    @Override
    public void message(String message);


    /***************************************************************************
     * Předá uživateli zadanou zprávu a převezme od něj další příkaz.
     *
     * @param message Zpráva předávaná uživateli
     * @return Příkaz zadaný uživatelem po přečtení zprávy
     */
//    @Override
    public String input(String message);



//== OTHER ABSTRACT METHODS ====================================================
//== EMBEDDED DATA TYPES =======================================================
}
