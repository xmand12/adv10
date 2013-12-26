
package cz.vse._3_0915.xmand12_mansurov;

/*******************************************************************************
 * Instance tridy {@code CommandGo} representuje prikaz, pomoci ktereho
 * hrac muze presunout do sousedne tridy
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandGo extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandGo()
    {
        super("jdi", "Přesune uživatele do zadaného sousedního prostoru");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer;
        if (arguments.length < 2) {
            answer = "Nebyl zadán cílový prostor přesunu.";
        } else {
            Rooms room = Rooms.getCurrentPlace();
            ListINamed<Rooms> neighbors = room.getNeighbors();
            Rooms destination = neighbors.getINamed(arguments[1]);
            answer = firstStep(destination, room);
        }
        return answer;
    }

    private String firstStep(Rooms destination, Rooms room) {
        String answer = "";
        if (destination == null) {
            answer = "Odsud se tam nedá přejít.";
        } else {
            room.setCurrentPlace(destination);
            answer = "Přestunul jste se do: "
                    + destination.getName().toLowerCase();

            if ((destination.getName().equalsIgnoreCase("džungle")) && (!QuestManager.getInstance().isFirstDzungle())) {
                QuestManager.getInstance().setFirstDzungle(true);
                answer += ".\nNěkdo k vám podešel a dal rozkaz\n"
                        + ", abyste mu předal(a) všechno, co máte.";
            }
        }
        return answer;
    }

}
