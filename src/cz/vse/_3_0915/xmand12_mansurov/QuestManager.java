
package cz.vse._3_0915.xmand12_mansurov;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instance tridy {@code QuestManager} representuje spravce uloh hry
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class QuestManager implements Serializable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
/** Odkaz na jedinou instanci (jedináčka) této hry. */

/** Mapa sdružující Aktualni stav vsech zadani. */
private static final Map<String, QuestManager> qmMap = new HashMap<>();
/*Staticka konstanta reprezentujici stav ulohy: "zabij vraha" */
private static final QuestManager killKiller    = new QuestManager("killKiller", false);
/*Staticka konstanta reprezentujici stav ulohy: "poprve v dzunglich" */
private static final QuestManager firstDzungle  = new QuestManager("frstDzungle", false);
/*Staticka konstanta reprezentujici stav ulohy: "prvni dialog s gubernatorem" */
private static final QuestManager frstDlgWthGbr = new QuestManager("frstDlgWthGbr", false);
/*Staticka konstanta reprezentujici stav ulohy: "prvni dialog s prodavacem" */
private static final QuestManager frstDlgWthSlr = new QuestManager("frstDlgWthSlr", false);
/*Staticka konstanta reprezentujici stav ulohy: "kupovani lodi" */
private static final QuestManager buyBoat       = new QuestManager("buyBoat", false);
/*Staticka konstanta reprezentujici stav ulohy: "odemkni truhlici" */
private static final QuestManager unlockedChest = new QuestManager("unlockedChest", false);

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

private final String name;
private boolean state;

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Soukromý konstruktor definující jedinou instanci.
     * Protože je soukromý, musí být definován, i když má prázdné tělo.
     */
    private  QuestManager(String name, boolean state)
    {
        this.name = name;
        this.state = state;

        qmMap.put(this.name, this);
    }

//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrati aktualni stav daneho zadani
     *
     * @return {@code  true} pokud splneno,
     * jinak {@code false}
     */
    private boolean getState() {
        return this.state;
    }

    /***************************************************************************
     * Nastavi aktualni stav pro dane zadani
     */
    private void setState(boolean newState) {
        this.state = newState;
    }

    /***************************************************************************
     * Vrati aktualni stav vsech zadani
     *
     * @return aktualni stav vsech zadani
     */
    public static Map<String, QuestManager> getQmMap() {
        return qmMap;
    }

    /***************************************************************************
     * Vrati booleanovskou hodnotu reprezentujici stav daneho zadani
     *
     * @param questName klic k danemu zadani
     * @return {@code true} pokud splneno, jinak {@code false}
     */
    public static boolean getStateOf(String questName) {
       return qmMap.get(questName).getState();
    }

    /***************************************************************************
     * Nastaveni booleanovske hodnoty pomoci klice
     *
     * @param questName klic k danemu zadani
     * @param newState stav daneho zadani
     * @return {@code true} pokud splneno, jinak {@code false}
     */
    public static void setStateTo(String questName, boolean newState) {
        qmMap.get(questName).setState(newState);
    }

    /***************************************************************************
     * Aktualizuje stavy hodnot z dane mapu na stavy hodnot mapy, zadane jako parametr
     *
     * @param newQmMap Mapa, zadana jako parametr.
     */
    public static void loadQmMapFrom(Map<String, QuestManager> newQmMap) {
        for (String key : qmMap.keySet()) {
            qmMap.get(key).setState(newQmMap.get(key).getState());
        }
    }

    /**
     * Metoda initializujici vsechny promenne dane metody
     *
     */
    public static void initialize(){
        for (String key : qmMap.keySet()) {
            qmMap.get(key).setState(false);
        }
    }

}
