package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance třídy {@code EmptyCommand} realizuje příkaz, který
 * <p>
 * Instance třídy je efektivně jedináček,
 * ale není třeba to v definici třídy explicitně zabezpečovat, protože vytvoření
 * a následnou správu všech příkazů má starosti k tomu definovaný správce,
 * který zabezpečí, aby od každé třídy příkazu vznikla pouze jediná instance.
 * </p>
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class Command_TEST_10_ extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public Command_TEST_10_()
    {
        super ("_TEST_10_", "Příkaz pro realizaci 10. domácího úkolu");
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

        room.setCurrentPlace(destination);
        bag.removeAllObject();
        bag.putInto(new Things("1"));
        bag.putInto(new Things("2"));
        bag.putInto(new Things("3"));
        bag.putInto(new Things("4"));
        return "Přesunul(a) jste se do testovacího prostoru";
    }
}
