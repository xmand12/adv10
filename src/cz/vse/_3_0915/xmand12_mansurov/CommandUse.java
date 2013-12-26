package cz.vse._3_0915.xmand12_mansurov;

/*******************************************************************************
 * Instance tridy {@code CommandPoloz} representuje přikaz,
 * který umožňuje hráče použit předmet v batohu
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandUse extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandUse()
    {
        super("použi","Přikaz,který umožňuje hráče použit předmět v batohu");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        if (arguments.length < 2) {
            answer = "Nebyl zadán předmět, který se má použit.";
        }
        Things item = isInBag(arguments[1]);
        if (item == null) {
            answer = "Takový předmět není ve vašem bátohu";
        } else {
            answer = use(item);
        }
        return answer;
    }

    private String use(Things item) {
        String answer = "";
        if (item.getName().equalsIgnoreCase("lopata")) {
            if (Rooms.getCurrentPlace().getName().equalsIgnoreCase("vodopád")) {
                Bag.getInstance().removeObject(item);
                Rooms.getCurrentPlace().add(new Things("#Truhlice"));
                answer = "Začal jste rýt a našel jste truhlici.";
            } else {
                answer = "Rýl jste dlouho, ale jste níc nenašel";
            }
        } else if (item.getName().equalsIgnoreCase("klíč")) {
            if (isInRoom("Truhlice")) {
                QuestManager.getInstance().setUnlockedChest(true);
                Bag.getInstance().removeObject(item);
                answer = "Použil jste klíč a odemknul jste truhlice.";
            }
        } else {
            answer = "Tenhle předmět nemůžete použit";
        }
        return answer;
    }

    private Things isInBag(String name) {
        for (Things item : Bag.getInstance().getObjects()) {
            if (item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    private boolean isInRoom(String name) {
        for (Things object : Rooms.getCurrentPlace().getObjects()) {
            if (object.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}

