package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.IObject;

/*******************************************************************************
 * Instance třídy {@code EmptyThing} přestavují objekty v místnostech.
 * Objekty mohou být jak věci, tak i osoby či jiné skutečnosti (vůně,
 * světlo, fluidum, ...).
 * <p>
 * Některé z objektů mohou charakterizovat stav prostoru (např. je rozsvíceno),
 * jiné mohou být určeny k tomu, aby je hráč "zvednul" a získal tím nějakou
 * schopnost či možnost projít nějakým kritickým místem hry
 * (např. klíč k odemknutí dveří).
 * <p>
 * Rozhodnete-li se použít ve hře objekty, které budou obsahovat jiné objekty,
 * (truhla, stůl, ...), můžete je definovat paralelně jako zvláštní druh
 * prostoru, do kterého se "vstupuje" např. příkazem "prozkoumej truhlu"
 * a který se opouští např. příkazem "zavři truhlu".
 *
 * @author    Daulet Mansurov
 * @version   7.4
 */
public class Things implements IObject
{

//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /*Konstanta, ktera obsahuje jmeno predmetu*/
    private final String name;
    /*Promenna, ktera obsahuje vahu predmetu*/
    private int weight;
    /*Promenna reprezentujici stav predmetu(t.j. zda se jedna o osobu ci predmet)*/
    private boolean alive = false;
    /*Konstanta reprezentujici predmet "Klic"*/
    public static final Things KEY = new Things("Klíč");
    /*Konstanta reprezentujici predmet "Mapa"*/
    public static final Things MAP = new Things("Mapa");

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor, ktery dava vahu predmetu a ozivuje predmetu
     * @param name jmeno predmetu
     * @param weight vaha predmetu
     */
    public Things(String name)
    {
        if ((name.charAt(0) == '#') || (name.charAt(0) == '@')){
            this.weight = 99;
            this.name = name.substring(1);
        }
        else {
            this.name = name;
            this.weight = 1;
        }
        if (name.charAt(0) == '@'){
            this.alive = true;

        }
    }

//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název prostoru.
     *
     * @return Název prostoru
     */
    @Override
    public String getName()
    {
        return name;
    }


    /***************************************************************************
     * Vrátí váhu předmětu, resp. charakteristiku jí odpovídající.
     * Objekty, které není možno zvednout, vrací -1.
     *
     * @return váha objektu nebo hodnota -1 charakterizující,
     *         že daný objekt není možno zvednou a umístit do batohu.
     */
    @Override
    public int getWeight()
    {
        return weight;
    }

    /**
     * Metoda vracejici stav osoby t.j. zda je nazivu ci nikoliv.
     *
     * @return vraci {@code true} pokud objekt je zivy,
     *         jinak vraci {@code false}
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * Metoda, ktera zabije danou osobu
     */
    public void kill(){
        alive = false;
    }

    /**
     * Metoda, ktera nastavuje vahu objektu.
     *
     * @param weight vaha objekta
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getName();
    }
}
