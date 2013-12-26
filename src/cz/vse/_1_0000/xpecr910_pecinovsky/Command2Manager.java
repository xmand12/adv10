/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;

import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@code Command2Manager} představují správce příkazů.
 * <p>
 * Třída je implementací přístupu, kdy správce má být instancí
 * samostatné, k tomu účelu vytvořené třídy.
 * Aby bylo zaručeno, že všechny vytvořené příkazy mají jediného správce,
 * je instance třídy definována jako jedináček.
 * </p><p>
  * Dvojka v názvu třídy má symbolizovat, že pro definice jednotlivých prostorů
 * a definici jejich správce potřebujeme dvě třídy, dva zdrojové kódy.
 * </p>
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Command2Manager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jediná instance dané třídy &ndash; jedináček. */
    private static final Command2Manager SINGLETON =
                     new Command2Manager();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//
//    //Verze využívající mapu
//    /** Mapa mapující názvy příkazů na příkazy. */
//    private final Map<String, ACommand> name2command = new HashMap<>();

    //Verze využívající seznam pojmenovaných
    /** Seznam pojmenovaných příkazů, který pro malé počty položek
     *  může efektivně nahradit mapu. */
    private final ListINamed<ACommand> commands = new ListINamed<>();



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
        add(new CommandJdi());
        add(new CommandStart());
        add(new CommandStop());
        add(new CommandVezmi());
        add(new Command_TEST_10_());
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    Collection<ACommand> getAllCommands()
    {
//        //Verze při používání mapy
//        return name2command.values();

        //Verze při používání seznamu pojmenovaných
        return commands;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param commandLine Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    String executeCommand(String commandLine)
    {
        String[]  words  = commandLine.trim().split("\\s+");

//        //Verze při používání mapy
//        ACommand command = name2command.get(words[0].toUpperCase());

        //Verze při používání seznamu pojmenovaných
        ACommand command = commands.getINamed(words[0]);

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
     * Inicializuje všechna pomocná nastavení hry do jejich počátečního stavu.
     */
    void initialize()
    {

    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Přidá zadaný příkaz mezi spravované příkazy.
     *
     * @param command   Přidávaný příkaz
     */
    private void add(ACommand command)
    {
//        //Verze při používání mapy
//        //Převádím název na velké znaky, aby při hlednání příkazu
//        //se zadaným názvem nezáleželo na velikosti znaků
//        String name = command.getName().toUpperCase();
//        name2command.put(name, command);

        //Verze při používání seznamu pojmenovaných
        commands.add(command);
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
