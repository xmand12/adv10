/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_txt;

import java.util.EnumMap;
import java.util.Map;



/*******************************************************************************
 * Instance třídy {@code Commands} přestavují přepravky
 * uchovávající názvy povinných příkazů v dané hře.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public class Commands
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název příkazu pro přesun z místnosti do místnosti. */
    public final String MOVE_CMD_NAME;

    /** Název příkazu pro položení předmětu. */
    public final String PUT_DOWN_CMD_NAME;

    /** Název příkazu pro zvednutí předmětu. */
    public final String PICK_UP_CMD_NAME;

    /** Název příkazu pro ukončení hry. */
    public final String END_CMD_NAME;

    /** Název příkazu pro získání nápovědy. */
    public final String HELP_CMD_NAME;

    /** Mapa umožňující získání příkazu zadáním názvu jeho typu. */
    private final Map<Type, String> type2name = new EnumMap<>(Type.class);



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Podpis instance generovaný metodou toString() -
     *  vytváří se až při prvním požadavku. */
    private String toString;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří přepravku uchovávající názvy příkazů,
     * které musí být implementovány ve všech hrách.
     * Názvy těchto příkazů musí být jednoslovné
     * stejně jako jejich případné parametry.
     *
     * @param move     Název příkazu pro přesun z místnosti do místnosti
     * @param putDown  Název příkazu pro položení předmětu
     * @param pickUp   Název příkazu pro zvednutí předmětu
     * @param help     Název příkazu pro získání nápovědy
     * @param end      Název příkazu pro ukončení hry
     */
    public Commands(String move, String putDown, String pickUp,
                    String help, String end)
    {
        this.MOVE_CMD_NAME     = move;
        this.PUT_DOWN_CMD_NAME = putDown;
        this.PICK_UP_CMD_NAME  = pickUp;
        this.HELP_CMD_NAME     = help;
        this.END_CMD_NAME      = end;

        type2name.put(Type.MOVE,     move);
        type2name.put(Type.PUT_DOWN, putDown);
        type2name.put(Type.PICK_UP,  pickUp);
        type2name.put(Type.HELP,     help);
        type2name.put(Type.END,      end);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název příkazu zadaného typu;
     *
     * @param typeOfCommand Typ příkazu, jehož název zjišťujeme
     * @return Název příkazu zadaného typu
     */
    public String getCommandName(Type typeOfCommand)
    {
        return type2name.get(typeOfCommand);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vrací podpis instance sestávající z názvu třídy následovaného
     * výčtem hodnot atributů uzavřeným v hranatých závorkách.
     *
     * @return Podpis instance
     */
    @Override
    public String toString()
    {
        if (toString == null) {
            toString = "BasicCommands[MOVE=" + MOVE_CMD_NAME     +
                                   ", PUT="  + PUT_DOWN_CMD_NAME +
                                   ", PICK=" + PICK_UP_CMD_NAME  +
                                   ", HELP=" + HELP_CMD_NAME     +
                                   ", END="  + END_CMD_NAME      + "]";
        }
        return toString;
    }


//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================

    /** Názvy typů příkazů. */
    public static enum Type{ MOVE, PUT_DOWN, PICK_UP, HELP, END; }



//== TESTING CLASSES AND METHODS ===============================================
}
