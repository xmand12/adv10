
package cz.vse._3_0915.xmand12_mansurov;

/*******************************************************************************
 * Instance tridy {@code CommandHelp} representuje prikaz, kterým může
 * hráč zabít nějakou osobu ve hře
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandKill extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandKill()
    {
        super("zabij", "Přikaz, kterým může hráč zabít nějakou osobu ve hře");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        if (arguments.length < 2) {
            answer += "Nebyl zadán objekt, na který se má aplikovat zadaná akce: " + arguments[0];
        }
        Rooms room = Rooms.getCurrentPlace();
        ListINamed<Things> roomObjects = room.getObjects();
        Things person = roomObjects.getINamed(arguments[1]);
        if (person.isAlive()) {
            if (person.getName().equalsIgnoreCase("vrah")) {
                person.kill();
                person.setWeight(1);
                answer += "Zabil jste vraha a schoval jste jeho trup do keře";
            } else {
                answer += "Nemůžeš ho zabit";
            }
        } else {
            answer += "Tento předmět není živý";
        }
        return answer;
    }
}
