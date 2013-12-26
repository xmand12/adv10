
package cz.vse._3_0915.xmand12_mansurov1;



/*******************************************************************************
 * Instance tridy {@code QuestManager} representuje spravce uloh hry
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class QuestManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
/** Odkaz na jedinou instanci (jedináčka) této hry. */
private static final QuestManager QUESTMANAGER = new QuestManager();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

/*Staticka promenna reprezentujici stav ulohy: "zabij vraha" */
private static boolean killKiller = false;
/*Staticka promenna reprezentujici stav ulohy: "poprve v dzunglich" */
private static boolean firstDzungle = false;
/*Staticka promenna reprezentujici stav ulohy: "prvni dialog s gubernatorem" */
private static boolean frstDlgWthGbr = false;
/*Staticka promenna reprezentujici stav ulohy: "prvni dialog s prodavacem" */
private static boolean frstDlgWthSlr = false;
/*Staticka promenna reprezentujici stav ulohy: "kupovani lodi" */
private static boolean buyBoat = false;
/*Staticka promenna reprezentujici stav ulohy: "odemkni truhlici" */
private static boolean unlockedChest = false;
/*Staticka promenna reprezentujici stav ulohy: "poprve v jezkyni" */
private static boolean firstJeskyne = true;

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /**
     * Tovární metoda vracející odkaz na jedninou existující instanci dané hry.
     *
     * @return Instance dané hry
     */
    public static QuestManager getInstance()
    {
        return QUESTMANAGER;
    }

    /***************************************************************************
     * Soukromý konstruktor definující jedinou instanci.
     * Protože je soukromý, musí být definován, i když má prázdné tělo.
     */
    private  QuestManager()
    {
    }

//== INSTANCE GETTERS AND SETTERS ==============================================

    /**
     * Metoda vracejici aktualni stav ulohy: "zabij vraha".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isKillKiller() {
        return killKiller;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setKillKiller(boolean state) {
        this.killKiller = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "poprve v dzunglich".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isFirstDzungle() {
        return firstDzungle;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setFirstDzungle(boolean state) {
        this.firstDzungle = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "prvni dialog s gubernatorem".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isFrstDlgWthGbr() {
        return frstDlgWthGbr;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setFrstDlgWthGbr(boolean state) {
        this.frstDlgWthGbr = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "prvni dialog s prodavacem".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isFrstDlgWthSlr()
    {
        return frstDlgWthSlr;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setFrstDlgWthSlr(boolean state) {
        this.frstDlgWthSlr = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "kupovani lodi".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isBuyBoat() {
        return buyBoat;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setBuyBoat(boolean state) {
        this.buyBoat = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "odemknout truhlici".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isUnlockedChest() {
        return unlockedChest;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setUnlockedChest(boolean state) {
        this.unlockedChest = state;
    }

    /**
     * Metoda vracejici aktualni stav ulohy: "poprve v jezkyni".
     *
     * @return vraci {@code true} pokud uloha je splnena,
     *         jinak vraci{@code false}
     */
    public boolean isFirstJeskyne() {
        return firstJeskyne;
    }

    /**
     * Metoda zmeni stav ulohy na stav zadany v parametru
     *
     * @param state zadany stav
     */
    public void setFirstJeskyne(boolean firstJezkyne) {
        this.firstJeskyne = firstJezkyne;
    }

    /**
     * Metoda initializujici vsechny promenne dane metody
     *
     */
    public static void initialize(){
        frstDlgWthGbr = false;
        frstDlgWthSlr = false;
        killKiller = false;
        unlockedChest = false;
        buyBoat = false;
        firstDzungle = false;
        firstJeskyne = false;
    }

}
