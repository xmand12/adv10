/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_framework.game_txt.ICommand;

import java.util.Collection;



/*******************************************************************************
 * Instance příkazu {@code ICommandManager} představují správce příkazů.
 * Třída je definována jako adaptér, jejíž instance zabalují objekt třídy
 * {@link AACommand}, který slouží jako správce svých instancí.
 * Slouží-li jako správce instancí objekt jejich mateřské třídy,
 * nelze jej uložit do proměnné. Adaptér umožňuje tuto nedokonalost obejít.
 * <p>
 * Jednička v názvu třídy má symbolizovat, že definice jednotlivých prostorů
 * i definice jejich správce jsou součástí jediného zdrojového kódu.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Command1ManagerAdapter implements ICommandManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jediná instance dané třídy &ndash; jedináček. */
    private static final Command1ManagerAdapter SINGLETON =
                     new Command1ManagerAdapter();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Jednoduchá, statická tovární metoda vracející
     * jedinou instanci dané třídy &ndash; jedináčka.
     *
     * @return Jediná instance třídy
     */
    static Command1ManagerAdapter getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří (jedinou) instanci dané třídy (jedináčka).
     */
    private Command1ManagerAdapter()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    @Override
    public
    Collection<? extends ICommand> getAllCommands()
    {
        return AACommand.getAllCommands();
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param commandLine Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    @Override
    public
    String executeCommand(String commandLine)
    {
        return AACommand.executeCommand(commandLine);
    }


    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
    @Override
    public
    void initialize()
    {
        ACommand.initialize();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
