/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_framework.game_txt.ICommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instances of class {@code AACommand} represent ...
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
abstract class AACommand implements ICommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    static
    private final Map<String, ACommand> NAME_2_COMMAND = new HashMap<>();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

     /* Jako součást tvorby správce vytvoří i instance všech příkazů,
      * které bude daný správce následně spravovat. */
    static {
        new CommandStart();
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    static
    Collection<ACommand> getAllCommands()
    {
        return NAME_2_COMMAND.values();
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param commandLine Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    static
    String executeCommand(String commandLine)
    {
        String[]  words  = commandLine.trim().split("\\s+");
        ACommand command = NAME_2_COMMAND.get(words[0]);

        String   answer;
        if (command == null) {
            answer = "Tento příkaz neznám." +
                   "\nChcete-li poradit, zadejte příkaz ?";
        }
        else {
            answer = command.execute(words);
        }
        return answer;
    }


    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
    static
    void initialize()
    {

    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @param name
     */
    AACommand(String name)
    {
        NAME_2_COMMAND.put(name, (ACommand)this);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
