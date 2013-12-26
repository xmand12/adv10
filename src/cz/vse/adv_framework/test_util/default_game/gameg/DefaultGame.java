package cz.vse.adv_framework.test_util.default_game.gameg;

import cz.vse.adv_framework.test_util.default_game.
       data.DataPkg;

import cz.vse.adv_framework.game_gui.IBagG;
import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IListener;
import cz.vse.adv_framework.game_gui.IPlaceG;

import cz.vse.adv_framework.game_txt.Commands;

import cz.vse.adv_framework.scenario.AScenarioManager;

import java.net.URL;

import java.util.Collection;

import javax.swing.ImageIcon;


import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code GameRUP} je jedináček, jenž má na starosti logiku hry,
 * při níž se hráč ocitne v malém bytě, který je třeba projít,
 * najít v něm ledničku a vzít si z ní svačinu.
 * Je schopna akceptovat jednotlivé příkazy a poskytovat informace
 * o průběžném stavu hry a jejích součástí.
 * <p>
 * Třída hry musí být navíc definována jako jedináček (singleton)
 * a kromě metod deklarovaných v tomto rozhraní musí definovat
 * statickou tovární metodu {@code getInstance()}.
 * Splnění této podmínky nemůže prověřit překladač,
 * ale prověří ji až následné testy hry.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public class DefaultGame implements IGameG
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Zda bude používán správce scénářů s konstantami nebo literály. */
    private static final boolean USING_CONSTANTS = false;

    /** Přepravka s názvy pěti povinných příkazů. */
    private static final Commands BASIC_COMMANDS =
                     new Commands(pJDI, pPOLOŽ, pVEZMI, pHELP, pKONEC);

    /** Objekt představující hráče, který je ale používán pouze k získání
     *  obrázku hráče označujícího na plánku pozici hráče. */
    private static final Thing PLAYER = new Thing("Player");

    /** Jediná instance hry. */
    private static final DefaultGame SINGLETON = new DefaultGame();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================


//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí odkaz na jedináčka - jedinou existující instanci třídy.
     * Metoda slouží k tomu, aby mohli některé metody získat odkaz
     * na aktuální hru a od ní pak získat jinak nedostupné informace.
     *
     * @return Odkaz na jedináčka
     */
    public static DefaultGame getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří instanci jedináčka.
     */
    private DefaultGame()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí informaci o tom, je-li hra aktuálně spuštěná.
     * Spuštěnou hru není možno pustit znovu.
     * Chceme-li hru spustit znovu, musíme ji nejprve ukončit.
     *
     * @return Je-li hra spuštěná, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    @Override
    public boolean isAlive()
    {
        return ACommand.isAlive();
    }


    /***************************************************************************
     * Vrátí jméno autora/autorky programu
     * ve formátu zadaném v rozhraní {@link cz.vse.IAuthor}.
     *
     * @return Jméno autora/autorky programu v požadovaném formátu
     */
    @Override
    public String getAuthorName()
    {
        return getScenarioManager().getAuthorName();
    }


    /***************************************************************************
     * Vrátí xname autora/autorky programu
     * ve formátu zadaném v rozhraní {@link cz.vse.IAuthor}
     *
     * @return Xname autora/autorky programu v požadovaném formátu
     */
    @Override
    public String getAuthorID()
    {
        return getScenarioManager().getAuthorID();
    }


    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané objekty.
     *
     * @return Batoh, do nějž hráč ukládá sebrané objekty
     */
    @Override
    public Bag getBag()
    {
        return Bag.getInstance();
    }


    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    @Override
    public Collection<ACommand> getAllCommands()
    {
        return ACommand.getCommands();
    }


    /***************************************************************************
     * Vrátí odkaz na přepravku s názvy povinných příkazů, tj. příkazů pro
     * <ul>
     *   <li>přesun hráče do jiného prostoru,</li>
     *   <li>zvednutí objektu (odebrání z prostoru a vložení do batohu),</li>
     *   <li>položení objektu (odebrání z batohu a vložení do prostoru),</li>
     *   <li>ukončení hry.</li>
     * </ul>
     *
     * @return Přepravka s názvy povinných příkazů
     */
    @Override
    public Commands getBasicCommands()
    {
        return BASIC_COMMANDS;
    }


    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public Collection<Room> getAllPlaces()
    {
        return Room.getRooms();
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální prostor,
     * tj. na prostor, v němž se hráč pravé nachází.
     *
     * @return Prostor, v němž se hráč pravé nachází
     */
    @Override
    public Room getCurrentPlace()
    {
        return Room.getCurrentRoom();
    }


    /***************************************************************************
     * Vrátí správce scénářů specifikujících požadované chování hry
     * v různých situacích.
     * Scénáře slouží k automatizovanému ověření funkčnosti hry
     * a/nebo demonstraci jejího možného průběhu.
     *
     * @return Správce scénářů dané hry
     */
    @Override
    public AScenarioManager getScenarioManager()
    {
        return (USING_CONSTANTS  ?  ManagerWithConstants.getInstance()
                                 :  ManagerWithLiterals .getInstance());
    }



//+++ Přidáno pro rozšířené zadnání v předmětu 4IT115 ++++++++++++++++++++++++++

    /***************************************************************************
     * Vrátí URL adresu stránky s nápovědou.
     *
     * @return URL adresa stránky s nápovědou
     */
    @Override
    public URL getHelpURL()
    {
        Class<DataPkg> dataPkgCls = DataPkg.class;
        URL            url        = dataPkgCls.getResource("Help.htm");
        return url;
    }


    /***************************************************************************
     * Vrátí obrázek s plánkem prostoru, v němž se hraje.
     *
     * @return Obrázek s plánkem prostoru
     */
    @Override
    public ImageIcon getMap()
    {
        URL url = DataPkg.class.getResource("Plánek.png");
        ImageIcon obr   = new ImageIcon(url);
        return obr;
    }


    /***************************************************************************
     * Vrátí obrázek hráče, který bude zobrazován na plánku hry,
     * aby uživatel věděl, ve kterém prostoru se jeho hráč aktuálně nachází.
     *
     * @return Obrázek hráče
     */
    @Override
    public ImageIcon getPlayer()
    {
        return (ImageIcon)PLAYER.getPicture();
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param command Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    @Override
    public String executeCommand(String command)
    {
        return ACommand.executeCommand(command);
    }


    /***************************************************************************
     * Ukončí celou hru a vrátí alokované prostředky.
     */
    @Override
    public void stop()
    {
        ACommand.stop();
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny objektů v batohu.
     *
     * @param listener Přihlašovaný posluchač
     */
    @Override
    public void addBagListener(IListener<IBagG> listener)
    {
        Bag.getInstance().addBagListener(listener);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny objektů v batohu.
     *
     * @param listener Odhlašovaný posluchač
     */
    @Override
    public void removeBagListener(IListener<IBagG> listener)
    {
        Bag.getInstance().removeBagListener(listener);
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny objektů v aktuální prostoru.
     *
     * @param listener Přihlašovaný posluchač
     */
    @Override
    public void addObjectListener(IListener<IPlaceG> listener)
    {
        Room.addObjectListener(listener);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny objektů v aktuálním prostoru.
     *
     * @param listener Odhlašovaný posluchač
     */
    @Override
    public void removeObjectListener(IListener<IPlaceG> listener)
    {
        Room.removeObjectListener(listener);
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny sousedů aktuálního prostoru.
     *
     * @param listener Přihlašovaný posluchač
     */
    @Override
    public void addNeighborsListener(IListener<IPlaceG> listener)
    {
        Room.addNeighborsListener(listener);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny sousedů aktuálního prostoru.
     *
     * @param listener Odhlašovaný posluchač
     */
    @Override
    public void removeNeighborsListener(IListener<IPlaceG> listener)
    {
        Room.removeNeighborsListener(listener);
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * kterým hra oznamuje začátek a stop rozhovoru.
     *
     * @param listener Přihlašovaný posluchač
     */
    @Override
    public void addDialogListener(IListener<Boolean> listener)
    {
        ACommand.addDialogListener(listener);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * kterým hra oznamuje začátek a stop rozhovoru.
     *
     * @param listener Odhlašovaný posluchač
     */
    @Override
    public void removeDialogListener(IListener<Boolean> listener)
    {
        ACommand.removeDialogListener(listener);
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
