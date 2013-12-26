/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IListener;

import java.util.ArrayDeque;
import java.util.Queue;



/*******************************************************************************
 * Instance třídy {@code Listener&lt;T&gt;} představují posluchače,
 * kteří pomáhají prověřovat, nakolik testovaná hra implementuje
 * požadované operace prostřednictvím návrhového vzoru <b>Posluchač</b>.
 *
 * @param <T> Typ objektu, od nějž posluchač získává další informace
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
class Listener<T> implements IListener<T>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** String specifikující typ posluchače z pohledu hry. */
    final String  typeOfListener;

    /** Seznam objektů s informacemi od hlasatele. Při každém zavolání
     *  oznamovací metody posluchače se do seznamu přidá další objekt. */
    final Queue<T> infomants = new ArrayDeque<T>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nového posluchače zadaného typu.
     *
     * @param typeOfListener String specifikující typ posluchače z pohledu hry
     */
    Listener(String typeOfListener)
    {
        this.typeOfListener = typeOfListener;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí informační objekt, který je právě na řadě
     * a odebere jej z fronty čekajících informačních objektů.
     *
     * @return Informační objekt, který je právě na řadě
     */
    public T getInformant()
    {
        return infomants.element();
    }


    /***************************************************************************
     * Vrátí počet inrofmačních objektů ve frontě.
     *
     * @return Počet inrofmačních objektů ve frontě
     */
    public int getQueueSize()
    {
        return infomants.size();
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vyprázdní frontu informačních objektů.
     */
    public void clearQueue()
    {
        infomants.clear();
    }


    /***************************************************************************
     * Hlášeni o změně stavu objektu předaného jako parametr.
     *
     * @param object Parametry hlášeni obsahující většinou objekt,
     *               o jehož změně stavu je posluchač zpravován,
     *               připadne další informace
     */
    @Override
    public void notice(T object)
    {
        infomants.add(object);
    }


    /***************************************************************************
     * Vrátí informační objekt, který je právě na řadě
     * aniž by jej odebral z fronty čekajících informačních objektů.
     *
     * @return Informační objekt, který je právě na řadě
     */
    public T showInformant()
    {
        return infomants.peek();
    }


    /***************************************************************************
     * Podpis posluchače vypisující svůj typ z hlediska hry.
     *
     * @return Text reprezentující podpis
     */
    @Override
    public String toString()
    {
        return typeOfListener + "Listener";
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        Listener inst = new Listener();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
