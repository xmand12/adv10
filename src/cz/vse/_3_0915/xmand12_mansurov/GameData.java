
package cz.vse._3_0915.xmand12_mansurov;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instance tridy {@code GameData} representuje uložení daného stavu hry
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class GameData implements Serializable
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /*Mapa sdružující aktualni stav vsech zadani*/
    private final Map<String, QuestManager> qmMap;
    /*Konstanta, ktera obsahuje predmety v batohu*/
    private final Bag bag;
    /*Konstanta, ktera obsahuje aktualni mistnost*/
    private final Rooms currRoom;
    /*Mapa sdružující název prostoru s příslušným prostorem.*/
    private final Map<String, Rooms> ALL_ROOMS = new HashMap<>();

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Kontruktor vytvarijici instanci dane tridy,
     * do ktere zapisuje aktualni stav nejdulezitejsich soucasti hry.
     * @param qmMap Aktualni stav vsech zadani hry
     * @param bag Aktualni stav batohu
     * @param currRoom Aktualni mistnost
     */
    public GameData( Map<String, QuestManager> qmMap, Bag bag, Rooms currRoom)
    {
        this.qmMap = qmMap;
        this.bag = bag;
        this.currRoom = currRoom;
        for (Rooms room : Rooms.getAllPlaces()) {
            ALL_ROOMS.put( room.getName(), room);
        }

    }

//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí mapu vsech aktualnich zadani.
     *
     * @return mapa vsech aktalnich zadani
     */
    public Map<String, QuestManager> getQm() {
        return qmMap;
    }

    /***************************************************************************
     * Vrátí aktualni stav batohu
     *
     * @return Aktualni stav batohu
     */
    public Bag getBag() {
        return bag;
    }

    /***************************************************************************
     * Vrátí aktualni mistnost
     *
     * @return Aktualni mistnost
     */
    public Rooms getCurrRoom() {
        return currRoom;
    }

    /***************************************************************************
     * Vrati mapu sdružující název prostoru s příslušným prostorem.
     *
     * @return Mapa sdružující název prostoru s příslušným prostorem.
     */
    public Map<String, Rooms> getALL_ROOMS() {
        return ALL_ROOMS;
    }
}
