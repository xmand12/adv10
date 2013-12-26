/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instance příkazu {@code Command2Manager} představují správce příkazů.
 * Třída je implementací přístupu, kdy správce má být instancí
 * samostatné, k tomu účelu vytvořené třídy.
 * <p>
  * Dvojka v názvu třídy má symbolizovat, že pro definice jednotlivých prostorů
 * a definici jejich správce potřebujeme dvě třídy, dva zdrojové kódy.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Command2Manager implements ICommandManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jediná instance dané třídy &ndash; jedináček. */
    private static final Command2Manager SINGLETON =
                     new Command2Manager();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    private final Map<String, ACommand> name2command = new HashMap<>();



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
    public static Command2Manager getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří (jedinou) instanci dané třídy (jedináčka).
     * Jako součást její tvorby vytvoří i instance všech příkazů,
     * které bude následně spravovat.
     */
    public Command2Manager()
    {
        add(new CommandStart());
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
    Collection<ACommand> getAllCommands()
    {
        return name2command.values();
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
        String[]  words  = commandLine.trim().split("\\s+");
        ACommand command = name2command.get(words[0]);

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
    @Override
    public
    void initialize()
    {

    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     *
     * @param command
     */
    private void add(ACommand command)
    {
        name2command.put(command.getName(), command);
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
