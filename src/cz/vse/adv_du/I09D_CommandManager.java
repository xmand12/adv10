/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_du;

import cz.vse.adv_framework.game_txt.ICommand;
import cz.vse.adv_framework.game_txt.IObject;
import cz.vse.adv_framework.game_txt.IObjectContainer;

import java.util.Collection;



/*******************************************************************************
 * Instance interfejsu {@literal I09D_ObjectContainer} představují
 * správce příkazů hry.
 * Mohou být definovány buď jako instance třídy správců,
 * anebo jako instance adaptéru zabalující objekt třídy,
 * který má roli správce svých instancí.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public interface I09D_CommandManager
{
//== CONSTANTS =================================================================
//== GETTERS AND SETTERS =======================================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
//    @Override
    public Collection<? extends ICommand> getAllCommands()
    ;



//== OTHER METHODS =============================================================

    /***************************************************************************
     * Zpracuje zadaný příkaz uživatele a vrátí text zprávy pro uživatele.
     *
     * @param commandLine Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
//    @Override
    public String executeCommand(String commandLine)
    ;


    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
//    @Override
    public void initialize()
    ;



//== EMBEDDED DATA TYPES =======================================================
}
