
package cz.vse._3_0915.xmand12_mansurov;

/*******************************************************************************
 * Instance tridy {@code CommandPutDown} representuje prikaz, ktery umoznuje
 * polozit predmet z batohu do daneho prostoru
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandPutDown extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instanci dane tridy
     */
    public CommandPutDown()
    {
        super("polož","Položí objekt do aktuálního prostoru");
    }

//== ABSTRACT METHODS ==========================================================

    @Override
    public String execute(String... arguments) {
        String answer;
        if (arguments.length < 2) {
            answer = "Nebyl zadán objekt, který se má položit.";
        }else {
            final Bag bag = Bag.getInstance();
            final Rooms room = Rooms.getCurrentPlace();
            final ListINamed<Things> bagObjects = bag.getObjects();
            final Things putDowm = bagObjects.getINamed(arguments[1]);
            if (putDowm == null) {
                answer= "Takový předmět nemáte v batohu.";
            } else {
                bag.removeObject(putDowm);
                room.add(putDowm);
                answer = "Položil(a) jste předmět: " + putDowm.getName();
            }
        }
        return answer;
    }
}
