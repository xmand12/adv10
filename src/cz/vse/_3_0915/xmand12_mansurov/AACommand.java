package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.ICommand;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * Instance tridy {@code AACommand} representuji spravce instance všech příkazů
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
abstract class AACommand implements ICommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================

   /*Staticka konstanta, obsahujici odkaz na mapu vsech prikazu*/
    private final static  Map<String, AACommand> NAME_to_COMMAND =
                                                new HashMap<>();

//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

     /* Jako součást tvorby správce vytvoří i instance všech příkazů,
      * které bude daný správce následně spravovat. */
    static {
        new CommandGo();
        new CommandStart();
        new CommandEnd();
        new CommandTakeUp();
        new Command_TEST_10_();
        new CommandPutDown();
        new CommandHelp();
        new CommandKill();
        new CommandTalk();
        new CommandUse();
        new CommandOpen();
        new CommandClose();
        new CommandRead();
        new CommandSave();
        new CommandLoad();
    }

//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    static
    Collection<AACommand> getAllCommands()
    {
        return NAME_to_COMMAND.values();
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
        String[]  words   = commandLine.trim().split("\\s+");
        AACommand command = NAME_to_COMMAND.get(words[0]);

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
    static void initialize()
    {
         Rooms.initialize();
    }

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor,ktery vytvarejici instanci dane tridy podle zadaneho jmena a
     * nasledne ji prida do mapy vsech prikazu.
     *
     * @param name jmeno instanci
     */
    AACommand(String name)
    {
        NAME_to_COMMAND.put(name, this);
    }

}
