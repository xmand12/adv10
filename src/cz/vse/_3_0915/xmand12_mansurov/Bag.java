package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.IBag;

/*******************************************************************************
 * Instance třídy {@code Bag} představuje úložiště, do nichž si hráči
 * odkládají objekty sebrané v jednotlivých prostorech.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class Bag implements IBag
{
//== CONSTANT CLASS ATTRIBUTES =================================================

/*Staticka konstanta obsahujici cislo, reprezentujici kapacitu batohu*/
private static final int CAPACITY = 4;
/*Staticka konstanta obsahujici odkaz na jedinou instanci dane tridy*/
private static final Bag BAG = new Bag();
/*Konstanta obsahujici odkaz na seznam vsech predmetu*/
private final ListINamed<Things> allObjects = new ListINamed<>();

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /**
     * Staticka metoda vracejici jedinou instanci tridy
     */

     public static final Bag getInstance()
    {
        return BAG;
    }

     /**
      * Privatni konstruktor,zabranujici vytvareni dalsich instanci dane tridy
      */
    private Bag()
    {
        initialize();
    }

//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kapacitu batohu, tj. maximální povolený součet vah objektů,
     * které se do něj umístí.
     *
     * @return Kapacita batohu
     */
    @Override
    public int getCapacity()
    {
        return CAPACITY;
    }

    /**
     * Metoda, ktera ma za ukol vlozit zadany predmet do batohu.
     * Metoda vraci booleanovskou hodnotu na zaklade toho, zda
     * zadany predmet vejde do batohu.
     *
     * @param item predmet
     * @return pokud se vlozeni predmetu podari, tak vrati {@code true},
     *         jinak vrati {@code false}
     */
    public boolean putInto(Things item)
    {
        if (allObjects.size() < getCapacity()) {
            if (item.getWeight() == 1 ) {
                allObjects.add(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda, ktera odebere zadany predmet z batohu
     *
     * @param object predmet
     */
    public void removeObject(Things object){
        allObjects.remove(object);
    }

    public void removeAllObject(){
        allObjects.clear();
    }

    /***************************************************************************
     * Vrátí kolekci objektů uložených v batohu.
     *
     * @return Kolekce objektů v batohu
     */
    @Override
    public ListINamed<Things> getObjects()
    {
        return allObjects;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /**
     * Metoda, ktera inicializuje batoh.
     * Maze seznam objektu v batohu a doplnuje potrebne predmety
     *
     */
    public void initialize() {
        allObjects.clear();
        allObjects.add(new Things("Klíč"));
        allObjects.add(new Things("Mapa"));
    }

}
