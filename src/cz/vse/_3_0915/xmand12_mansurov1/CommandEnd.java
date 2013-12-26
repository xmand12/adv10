
package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance tridy {@code CommandEnd} representuje prikaz ukoncujici hru.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandEnd extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandEnd()
    {
        super("konec","Příkaz ukončující hru");
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments)
    {
        String answer = "Děkuju, že jste zkusil mou hru!";
        Game.getInstance().stop();
        return answer;
    }

}
