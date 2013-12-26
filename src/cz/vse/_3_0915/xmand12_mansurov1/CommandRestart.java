
package cz.vse._3_0915.xmand12_mansurov1;



/*******************************************************************************
 * Instance tridy {@code CommandRestart} representuje přikaz restartující hru.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandRestart extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandRestart()
    {
        super("restart","přikaz restartující hru");
    }



//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        Bag bag = Bag.getInstance();
        Rooms room = Rooms.getCurrentPlace();
        ListINamed<Rooms> allRooms = Rooms.getAllPlaces();
        Rooms destination = allRooms.getINamed(arguments[0]);

        bag.initialize();
        Rooms.restartInitialize();
        QuestManager.initialize();
        room.setCurrentPlace(destination);


        return "Přesunul(a) jste do prostoru restartující hru";
    }
}
