/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IBagG;
import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IPlaceG;

import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.ScenarioStep;
import cz.vse.adv_framework.scenario.TypeOfStep;

import cz.vse.adv_framework.utilities.Util;

import java.util.Arrays;



import static cz.vse.adv_framework.
              utilities.CompareIgnoreCase.CIC;
import static cz.vse.adv_framework.
              utilities.FormatStrings.*;



/*******************************************************************************
 * Instance třídy {@code GameGListenerUseTest} slouží k testování
 * komunikace hry s přihlášenými posluchači.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
class GameGListenerUseTest
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Příznak práce v textovém (tj. ne okenním) režimu. */
    private static final boolean TEXT_IU = true;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Posluchač informovaný o změně množiny objektů v batohu. */
    final Listener<IBagG> bagListener = new Listener<IBagG>("Bag");

    /** Posluchač informovaný o změně množiny objektů v aktuální prostoru. */
    final Listener<IPlaceG> objectListener = new Listener<IPlaceG>("Object");

    /** Posluchač informovaný o změně množiny sousedů aktuálního prostoru. */
    final Listener<IPlaceG> neighborsListener =
                                          new Listener<IPlaceG>("Neighbors");

    /** Posluchač informovaný o začátku a konci rozhovoru. */
    final Listener<Boolean> dialogListener = new Listener<Boolean>("Dialog");

    /** Odkaz na instanci testovane hry. */
    private final IGameG gameG;


//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Počet opomenutých volání posluchačů, tj. situací,
     *  kdy se něco změnilo, a nebyl zavolán příslušný posluchač. */
    private int ommitedNoticeCount = 0;

    /** Počet zbytečných volání posluchačů, tj. situací,
     *  kdy byl zavolán posluchač, i když se nic nezměnilo. */
    private int uselessNoticeCount = 0;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci, která bude testovat zadanou hru.
     *
     * @param gameG Hra, která se bude testovat
     */
    GameGListenerUseTest (IGameG gameG)
    {
        this.gameG = gameG;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Ověří, že hra je (alespoň formálně) schopna přihlásit a zase odhlásit
     * všechny požadované posluchače.
     *
     * @return Zpráva o výsledku testu
     */
    String verifyAddRemoveListeners()
    {
        String message =
               "+ Přihlašování a odhlašování posluchačů proběhlo bez problémů";
        try {
            addListeners();
            removeListeners();
        }
        catch (Exception e) {
            message =
               "- Přihlašování a odhlašování posluchačů nefunguje \n  " +
               e.getMessage();
        }
        return message;
    }


    /***************************************************************************
     * Za pomoci testovacího scénářp pro GUI ověři, zda hra správně oslovuje
     * případné přihzlášené posluchače.
     *
     * @return Zpráva o výsledku testu
     */
    String verifyListenersUse()
    {
        Scenario scenario = gameG.getScenarioManager().getGuiTestScenario();
        StringBuilder  sb = new StringBuilder();

        String[] empty = {};
        ScenarioStep lastStep = null;
//        = new ScenarioStep(0, "PŘED-START",
//                        "¤", "¤", empty, empty, empty, TypeOfStep.tsNOT_SET);
        boolean firstStep = true;
        for(ScenarioStep step : scenario)
        {
            sb.append("  = ").append(step.command).append(NL);
            String answer = gameG.executeCommand(step.command);
            ScenarioStep state = new ScenarioStep(step.command, answer,  gameG);
            if (firstStep) {
                addListeners();
                firstStep = false;
            }
            else {
                verifyPossibleNotices(sb, lastStep, state);
            }
            lastStep = state;
        }

        if (ommitedNoticeCount == 0) {
            sb.insert(0, "+ Při hraní prostřednictvím testovacího scénáře " +
               "nebyla odhalena žádná opomenutá oznámení posluchačům\n");
        }
        else {
            sb.insert(0, "- Při hraní prostřednictvím testovacího scénáře " +
                         "BYLA ODHALENA OPOMENUTÁ OZNÁMENÍ POSLUCHAČŮM\n");
        }
        return sb.toString();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Přihlásí u hry všechny posluchače.
     */
    private void addListeners()
    {
        Listener<?> listener = null;
        try {
            listener = bagListener;
            gameG.   addBagListener(bagListener);

            listener = objectListener;
            gameG.   addObjectListener(objectListener);

            listener = neighborsListener;
            gameG.   addNeighborsListener(neighborsListener);

            listener = dialogListener;
            gameG.   addDialogListener(dialogListener);
        }
        catch(Exception e) {
            throw new RuntimeException(
                "\nHra nezpracovává přihlašování posluchače " + listener, e);
        }
    }


    /***************************************************************************
     * Odhlásí u hry všechny posluchače.
     */
    private void removeListeners()
    {
        Listener<?> listener = null;
        try {
            listener = bagListener;
            gameG.removeBagListener(bagListener);

            listener = objectListener;
            gameG.removeObjectListener(objectListener);

            listener = neighborsListener;
            gameG.removeNeighborsListener(neighborsListener);

            listener = dialogListener;
            gameG.removeDialogListener(dialogListener);
        }
        catch(Exception e) {
            throw new RuntimeException(
                "\nHra nezpracovává odhlašování posluchače " + listener, e);
        }
    }


    /***************************************************************************
     * Ověří, že případná změna obsahu batohu, předmětů v aktuální místnosti
     * či jejich sousedů byla nahlášena příslušnému posluchači.
     *
     * @param sb           {@link StringBuilder} s kumulovanou zprávou
     * @param previous     Minulý stav pole objektů
     * @param present      Současný stav pole objektů
     */
    private void verifyPossibleNotices(StringBuilder sb,
                 ScenarioStep previous, ScenarioStep present)
    {
        verifyPossibleNotice(sb, "předmětů v batohu", bagListener,
                             previous.bag, present.bag);
        verifyPossibleNotice(sb, "předmětů v místnosti", objectListener,
                             previous.objects, present.objects);
        verifyPossibleNotice(sb, "sousedů místnosti", neighborsListener,
                             previous.neighbors, present.neighbors);
        bagListener      .clearQueue();
        objectListener   .clearQueue();
        neighborsListener.clearQueue();
    }


    /***************************************************************************
     * Ověří, zda změna ve sledovaném poli objektů byla nahlášena posluchači
     * a naopak zda posluchač nebyl oslovován, pokud k žádné změně nedošlo.
     *
     * @param sb           {@link StringBuilder} s kumulovanou zprávou
     * @param description  Textová charakteristika posluchače
     * @param listener     Posluchač, jehož použití testujeme
     * @param previous     Minulý stav pole objektů
     * @param present      Současný stav pole objektů
     */
    private void verifyPossibleNotice(StringBuilder sb,
            String description, Listener<?> listener,
            String[] previous,  String[] present)
    {
        boolean change = previous.length != present.length;
        if (!change) {
            Arrays.sort(previous,   CIC);
            Arrays.sort(present, CIC);
            for (int i=0;   i < previous.length;   i++) {
                if (! Util.strArrEqualsIgnoreCase(previous, present)) {
                    change = true;
                    break;
                }
            }
        }
        boolean notice = (listener.getQueueSize() > 0);
        if (notice != change) {
            if (notice) {
                uselessNoticeCount++;
                sb.append("    - Byl osloven posluhač ").append(description).
                   append(" aniž by se cokoliv změnilo").append(NL);
            }
            else {
                ommitedNoticeCount++;
                sb.append("    - Přestože došlo ke změně, nebyl osloven " +
                          "posluchač ").append(description).append(NL);
            }
        }
    }



//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        TestHry x = new TestHry();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
