
package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance tridy {@code CommandJdi} representuje prikaz otevirajici predmety
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandOpen extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandOpen()
    {
        super("otevři", "Přikaz, dovolující hráčovi otevírat některé předměty");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        if (arguments.length < 2) {
            answer += "Nebyl zadán předmět,který se má otevřit.";
        } else {
            if (isInRoom(arguments[1])) {
                if (arguments[1].equalsIgnoreCase("Truhlice")) {
                    if (QuestManager.getInstance().isUnlockedChest()) {
                        for (Rooms room : Rooms.getAllPlaces()) {
                            if (room.getName().equalsIgnoreCase("Truhlice")) {
                                Rooms.getCurrentPlace().setCurrentPlace(room);
                                answer += "Otevřel jste truhlici"
                                        + " a teď máte přistup k jejimu obsahu";
                                break;
                            }
                        }
                    } else {
                        answer += "Truhlice je zamčená. Musíte ji nejdřív odemknout.";
                    }
                } else {
                    answer += "Tento předmět není zamčen. Není třeba ho odemíkat.";
                }
            } else {
                answer += "Takový předmět tady není.";
            }
        }
        return answer;
    }

//== INSTANCE GETTERS AND SETTERS ==============================================
 private boolean isInRoom(String name) {
        for (Things object : Rooms.getCurrentPlace().getObjects()) {
            if (object.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
