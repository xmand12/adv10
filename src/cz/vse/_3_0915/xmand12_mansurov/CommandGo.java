
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
            final Rooms room = Rooms.getCurrentPlace();
            final ListINamed<Rooms> neighbors = room.getNeighbors();
            final Rooms destination = neighbors.getINamed(arguments[1]);
            answer = Step(destination, room);
        }
        return answer;
    }

    /**
     * Metoda vraci odpoved na dany prikaz v zavislosti na nekolika kriteriich:
     * jestli jste byli v dzunglich
     * jestli jste zadali prazdny prikaz
     * @param destination mistnost, kam jste se chcete presunout
     * @param room mistnost, kde jste se nachazite
     * @return otpoed na dany prikaz
     */
    private String Step(Rooms destination, Rooms room) {
        String answer = "";
        if (destination == null) {
            answer = "Odsud se tam nedá přejít.";
        } else if ((room.getName().equalsIgnoreCase("džungle"))
                  && (QuestManager.getStateOf("frstDzungle"))
                  && (!QuestManager.getStateOf("killKiller"))) {
                answer = "Bohužel, ale vy jste prohral. Byl jste zabit vrahem. Děkujeme.";
                Game.getInstance().stop();
            }
        else {
            room.setCurrentPlace(destination);
            answer = "Přestunul jste se do: "
                    + destination.getName().toLowerCase();

            if ((destination.getName().equalsIgnoreCase("džungle")) && (!QuestManager.getStateOf("frstDzungle"))) {
                QuestManager.setStateTo("frstDzungle", true);
                answer = "Přestunul jste se do: "
                        + destination.getName().toLowerCase()
                        +".\nNěkdo k vám podešel a dal rozkaz\n"
                        + ", abyste mu předal(a) všechno, co máte.";
            }
        }
        return answer;
    }

}
