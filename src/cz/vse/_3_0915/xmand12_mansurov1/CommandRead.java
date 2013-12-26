
package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance tridy {@code CommandJdi} representuje přikaz,
 * dovolující hráčovi číst některé předměty
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandRead extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandRead()
    {
        super("přečti", "Přikaz, dovolující hráčovi číst některé předměty");
    }

//== ABSTRACT METHODS ==========================================================

     /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        if (arguments.length < 2) {
            answer += "Nebyl zadán předmět,který se má zavřit.";
        } else {
            if (isInBag(arguments[1])) {
                if (arguments[1].equalsIgnoreCase("Deník")) {
                    answer += "Přečetl jste deník. V deníku je napsano,"
                            + " jak se můžete dostat k pramenu večného mládí.";
                } else {
                    answer += "Tento předmět se přečíst nedá.";
                }
            } else {
                answer += "Takový předmět není v batohu.";
            }
        }
        return answer;
    }

     private boolean isInBag(String name) {
        for (Things object : Bag.getInstance().getObjects()) {
            if (object.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

}