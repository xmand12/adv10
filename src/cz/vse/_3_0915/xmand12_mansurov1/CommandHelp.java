package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance tridy {@code CommandHelp} representuje prikaz, ktery otevira
 * seznam vsech prikazu v hre
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandHelp extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandHelp()
    {
        super("?", "Napověda");
    }



//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc }
     */
    @Override
    public String execute(String... arguments) {
        StringBuilder sb = new StringBuilder();

        sb.append("\nPříkazy, které je možno v průběhu hry zadat:\n============================================================================\n");
        for (AACommand command : AACommand.getAllCommands()) {
            sb.append(String.format("%9s", command.getName()));
            sb.append("  ->  ");
            sb.append(command.getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }
}
