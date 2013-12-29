
package cz.vse._3_0915.xmand12_mansurov;



/*******************************************************************************
 * Instance tridy {@code CommandTakeUp} representuje prikaz,
 * ktery bere predmet z aktualniho prostoru do vaseho batohu
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
class CommandTakeUp extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandTakeUp()
    {
        super("vezmi","Vezmi objekt v aktuálním prostoru");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer;
        if (arguments.length < 2) {
            answer = "Nebyl zadán objekt, který se má zvednout.";
        } else {
            final Things item = isInRoom(arguments[1]);
            if (item == null) {
                answer = "Takový předmět tady není.";
            } else {
                if (Bag.getInstance().putInto(item)) {
                    Rooms.getCurrentPlace().removeObject(item);
                    answer = item.getName() + " je teď v vašem batohu.";
                } else {
                    if (item.getWeight() > 1) {
                        answer = "Tenhle předmět se nedá zvednout.";
                    } else {
                        answer = "Váš batoh je plný";
                    }
                }
            }
        }
        return answer;
    }

    private Things isInRoom(String name) {
        for (Things object : Rooms.getCurrentPlace().getObjects()) {
            if (object.getName().equalsIgnoreCase(name)){
                return object;
            }
        }
        return null;
    }
}
