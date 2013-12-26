
package cz.vse._3_0915.xmand12_mansurov;




/*******************************************************************************
 * Instance tridy {@code CommandJdi} representuje prikaz uzavirajici predmety.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandClose extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandClose()
    {
        super("zavři", "Přikaz, dovolující hráčovi zavírat některé předměty");
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
            if(Rooms.getCurrentPlace().getName().equalsIgnoreCase("Truhlice")) {
                for (Rooms room : Rooms.getAllPlaces()) {
                    if (room.getName().equalsIgnoreCase("vodopád")){
                        Rooms.getCurrentPlace().setCurrentPlace(room);
                        answer += "Zavřel jste truhlici.";
                        break;
                    }
                }
            } else {
                answer += "Tento předmět se zavřit nedá.";
            }
        }
        return answer;
    }

}