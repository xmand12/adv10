/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.Commands;
import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IObjectG;
import cz.vse.adv_framework.game_gui.IPlaceG;
import cz.vse.adv_framework.game_txt.ICommand;

import cz.vse.adv_framework.scenario.AScenarioManager;

import cz.vse.adv_framework.utilities.CompareIgnoreCase;
import cz.vse.adv_framework.utilities.DBG_Logger;
import cz.vse.adv_framework.utilities.ToASCII;

import java.awt.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;


import static
       cz.vse.adv_framework.utilities.FormatStrings.*;



/*******************************************************************************
 * Instance třídy {@code GameGuiRunTest} slouží k testování
 * her implementujících rozhraní {@code IGameG} a ovladatelných podle scénářů
 * spravovaných instancí potomka třídy {@link AScenarioManager}.
 * Instance však pouze prověřují základní povinné vlastnosti hry, tj.
 * správnou implementaci rozhraní {@code IGameG} včetně dodatečných kontraktů,
 * aniž by se snažily spustit některý z definovaných scénářů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
class GameGClassTest extends Triumvirate
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Povinný balíček, do nějž musejí studenti ukládat své podbalíčky. */
    private static final String ROOT_PKG = "cz.vse.advent_115.ddHHmm";

    private static final String GROUP_ID = "ut(0000|0915|1100|1245)";

    /** Logger, prostřednictvím nějž zanamenáváme veškeré informace. */
    private final static DBG_Logger DBG = DBG_Logger.getInstance();

    /** Zpráva oznamující úspěšné průchod testu. */
    private static final String OK = "OK";

    /** Minimální počet prostorů ve hře. */
    private static final int MIN_PLACES = 4;

    /** Identifikace jednotlivcýh příkazů ze základní sady povinných příkazů. */
    private static final int cMOVE = 0,  cPUT = 1,  cPICK = 2, cEND = 3;


//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Mapa převádějící název příkazu na jeho identifikační kód. */
    final SortedMap<String, Integer> command2ID =
                                     new TreeMap<>(CompareIgnoreCase.CIC);


    /***************************************************************************
     * Vektor testovacích objektů,
     * které jsou postupně aplikovány na testovanou hru.
     */
    IVerifier[] tests =
    {
        new IVerifier() {
            @Override public String verify() {
                return verifyFormallyImplements();
        }   },
        new IVerifier() {
            @Override public String verify() {
                return verifyCollectionCorrectness();
        }   },
        new IVerifier() {
            @Override public String verify() {
                return verifyReturnsCommands();
        }   },
        new IVerifier() {
            @Override public String verify() {
                return verifyCommandNames();
        }   },
        new GuiTestScenarioTest(this),

        new IVerifier() {
            @Override public String verify() {
                return verifyLocalizablePlaces();
        }   },
         new IVerifier() {
            @Override public String verify() {
                return verifyHelpURL();
        }   },
        new IVerifier() {
            @Override public String verify() {
                return verifyMapImage();
        }   },
         new IVerifier() {
            @Override public String verify() {
                return verifyObjectImages();
        }   },
       new IVerifier() {
            @Override public String verify() {
                return verifyAddRemoveListeners();
        }   },
        new IVerifier() {
            @Override public String verify() {
                return verifyListenersUse();
        }   },
    };

    GuiTestScenarioTest guiTestScenarioTest;


//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    //Následující parametry jsou deklarovány jako proměnné pouze proto,
    //aby je bylo možno inicializovat až poté, co testy zaručí,
    //že testovaná hra umožní incializaci bez zhavarování programu

    /** Textová podoba url x-namového balíčku aplikace. */
    private String xnamePackagePath;

    /** Textová podoba url balíčku, kde mají být uložena všechna data. */
    private String dataPackagePath;

    /** Přepravka s názvy základních, poviných příkazů. */
    Commands commandNames;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci testovací třídy napojenou na zadaného správce scénářů.
     * Správce scénářů je klíčovým bodem celé hry, protože jeho prostřednictvím
     * se je možno dostat ke všem ostatním dostupným informacím o hře.
     *
     * @param manager Správce scénářů prověřované aplikace
     * @return Instance schopná testovat zadého správce scénářů
     *         včetně případné hry, jejíž scénáře spravuje
     */
    static GameGClassTest getInstance(AScenarioManager manager)
    {
        return new GameGClassTest(fromManager(manager));
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat
     * instance zadané třídy hry.
     *
     * @param gameClass Třída testované hry
     * @return Instance schopná testovat zadanou třídu hry a její hru
     */
    static GameGClassTest getInstance(Class<? extends IGameG>gameClass)
    {
        return new GameGClassTest(fromGameClass(gameClass));
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat zadanou hru.
     *
     * @param game Testovaná hra
     * @return Instance schopná testovat zadanou hru
     */
    static GameGClassTest getInstance(IGameG game)
    {
        return new GameGClassTest(fromGame(game));
    }


    /***************************************************************************
     * Vytvoří instnaci, která bude schopna prověřit,
     * nakolik její hra vyhovuje požadavkům grafického rámce.
     *
     * @param crate  Přepravka se správcem scénářů prověřované aplikace,
     *               class-objektem třídy hry a prověřovanou hrou
     * @throws IllegalArgumentException Nevyhovují-li parametry podmínkám
     */
    GameGClassTest(Crate crate)
    {
        super(crate);
        verifyImplementsIGameG();
        guiTestScenarioTest = new GuiTestScenarioTest(this);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda postupně ověřuje splnění jednotlivých kontraktů testovanou hrou
     * a vrátí informaci o výsledku provedených testů.
     *
     * @return {@code true} nebyla-li nalezena žádná chyba.
     */
    boolean verifyGame()
    {
        DBG.severe(N_HASHES+N_HASHES+ "\nAutor: {0}\nTřída: {1}" + N_HASHES,
                   manager.getAuthorName(), gameClass.getName());
        String summary = "Všechny testy proběhly OK - " +
                         "hra pravděpodobně splňuje všechny požadavky.";
        boolean verified = true;

        for (int i=0;   i < tests.length;   i++) {
            String message = tests[i].verify();
            DBG.severe("\n{0}", message);

            if (message.charAt(0) != '+') {
                summary  = "HRA NESPLŇUJE NĚKTERÉ POŽADAVKY.";
                verified = false;
                break;
            }
        }
        DBG.severe(N_HASHES_N +
                   "Konec testů verifkované hry" +
                   "\nAutor: {0}" +
                   "\nTřída: {1}" +
                   "\n\n{2}" +
                   N_HASHES+N_HASHES_N,
                   manager.getAuthorName(), gameClass.getName(), summary);
        return verified;
    }


    /***************************************************************************
     * Spustí testovanou hru.
     */
    void startGame()
    {
        if (gameG.isAlive()) {
            gameG.stop();
        }
        gameG.executeCommand("");
        if (! gameG.isAlive()) {
            throw new RuntimeException("\nHru se nepodařilo spustit: " +
                                       gameG.getClass().getName());
        }
    }


    /***************************************************************************
     * Ukončí testovanou hru.
     */
     void stopGame()
    {
        gameG.stop();
        if (gameG.isAlive()) {
            throw new RuntimeException("\nHra se odmítá ukončit: " +
                                       gameG.getClass().getName());
        }
    }


    /***************************************************************************
     * Ověří, že zadaný prostor je v seznamu všech prostorů dané hry.
     *
     * @param actual Název aktuálního prostoru
     * @param all    Kolekce názvů všech ohlášených prostorů
     * @return Není-li prostor v seznamu, vrátí oznámení,
     *         Je-li vše v pořádku, vrátí prázdný řetězec
     */
    String checkPlaceAnnounced(String actual, Set<String> all)
    {
        if (all.contains(actual)) {
            return "";
        }
        return "    ! AKTUÁLNÍ PROSTOR NEBYL ZVEŘEJNĚN V SEZNAMU " +
                    "VŠECH PROSTORŮ HRY: " + actual + NL;
    }


    /***************************************************************************
     * Na začátek zadané instance třídy {@link StringBuilder} vloží jeden
     * ze zadaných řetězců; který to bude záleží na zadané logické hodnotě.
     *
     * @param sb    {@link StringBuilder}, do nějž vkládáme řetězec
     * @param ok    Hodnota ovlivňující výběr vkládaného řetězce
     * @param right Řetězec, který se vloží je-li {@code ok == true}
     * @param wrong Řetězec, který se vloží je-li {@code ok == false}
     * @return Upravený {@link StringBuilder} převedený na řetězec
     */
    String insertSummary(StringBuilder sb, boolean ok,
                               String right, String wrong)
    {
        String prolog = (ok  ?  right  :  wrong) + NL;
        sb.insert(0, prolog);
        return sb.toString();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    //Verifikační metody nejsou seřazeny podle abecedy,
    //ale podle toho, v jakém pořadí budou volány -
    //některé totiž používají data připravená jejich předchůdkyněmi

    /***************************************************************************
     * Potvrdí, že třída hry formálně implementuje rozhraní {@link IGameG}.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyFormallyImplements()
    {
        String author = AuthorTest.verifyContract(manager);
        boolean ok = (author.charAt(0) == '+');

        String pkg = checkGamePackage();
        ok &= (pkg.charAt(0) == '+');

        String zpráva = (ok  ?  '+'  :  '-') +
               " Formální implementace rozhraní IGameG " +
               (ok  ?  "je pravděpodobně OK"  :  "není zcela v pořádku") +
               "\n  " + author + "\n  " + pkg + NL;
        return zpráva;
    }


    /***************************************************************************
     * Zkontroluje, zda je hra umístěna ve správném balíčku.
     *
     * @return Zpráva o výsledku testu
     */
    private String checkGamePackage()
    {
        String url = getXnamePackagePath();

        if (url.charAt(0) == '-') {
            return url;
        }
        xnamePackagePath = url;
        dataPackagePath  = url + "data/";

        if (gameG == Triumvirate.getDefaultGame()) {
            return "+ U implicitní hry se balíček netestuje";
        }
        else {
            return "+ Třída hry je ve správném balíčku: " +
                   gameG.getClass().getCanonicalName();
        }
   }


    /***************************************************************************
     * Vrátí textovou podobu URL x-namového balíčku hry.
     *
     * @return Zpráva o výsledku testu
     */
    private String getXnamePackagePath()
    {
        String xnamePkgName;

        if (gameG == Triumvirate.getDefaultGame()) {
            xnamePkgName = "default_game";
        }
        else {
            String author  = gameG.getAuthorName();
            String surname = author.substring(0, author.lastIndexOf(' '));
            String xname   = gameG.getAuthorID();
            xnamePkgName   = (xname + '_' + ToASCII.text(surname)).
                             toLowerCase();

            Package pkg = gameClass.getPackage();
            String  gamePkgName = pkg.getName();
            int gamePkgIndex = gamePkgName.indexOf(xnamePkgName);
            if (gamePkgIndex < 0 ) {
                return "- TŘÍDA JE VE ŠPATNĚ POJMENOVANÉM BALÍČKU:" +
                     "\n    Očekávám: " + xnamePkgName +
                     "\n    Obdrženo (celá cesta): " + gamePkgName;
            }
//            String requiredPackageName = ROOT_PKG +
//            if (gamePkgIndex != ROOT_PKG.length()) {
//                return "- TŘÍDA JE VE ŠPATNĚ UMÍSTĚNÉM BALÍČKU: " +
//                     "\n    Očekávám: " + ROOT_PKG + '.' + xnamePkgName + ".game" +
//                     "\n    Obdrženo: " + gamePkgName;
//            }
            if (! gamePkgName.contains(xnamePkgName + ".game")) {
                return "- TŘÍDA HRY NENÍ UMÍSTĚNA V PODBALÍČKU GAME: " +
                       gamePkgName;
            }
        }
        URL classUrl = gameClass.getResource(
                                 gameClass.getSimpleName() + ".class");
        String urlPath = classUrl.toString();
        int xnameIndex = urlPath.indexOf(xnamePkgName);
        String result  = urlPath.substring(0,         //+1 kvůli závěrečnému "/"
                                 xnameIndex+xnamePkgName.length() + 1);
        return result;
    }


    /***************************************************************************
     * Potvrdí, že kolekce, vracené hrou a jejími součástmi,
     * neumožňují zvenku ovlivnit chování hry ani jejich součástí.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyCollectionCorrectness()
    {
        String        message;
        StringBuilder sb = new StringBuilder();
        boolean       ok = true;

        startGame();

        //Test kolekce příkazů vracené hrou
        message = checkCollection("příkazů", new IGetCollection() {
            @Override public Collection<?> getCollection() {
                return gameG.getAllCommands();
            }
        });
        ok &= (message.charAt(0) == '+');
        sb.append("  ").append(message).append(NL);

        //Test kolekce prostorů vracené hrou
        Collection<? extends IPlaceG> places =
                                      new ArrayList<>(gameG.getAllPlaces());
        message = checkCollection("prostorů", new IGetCollection() {
            @Override public Collection<?> getCollection() {
                return gameG.getAllPlaces();
            }
        });
        ok &= (message.charAt(0) == '+');
        sb.append("  ").append(message).append(NL);

        //Test kolekcí sousedů a objektů vracených jednotlivými prostory
        for (IPlaceG ipg : places) {
            final IPlaceG place = ipg;
            sb.append("  = Prostor ").append(place.getName()).append(NL);
            message = checkCollection("sousedů", new IGetCollection() {
                          @Override public Collection<?> getCollection() {
                              return place.getNeighbors();
                          }
                      });
            ok &= (message.charAt(0) == '+');
            sb.append("    ").append(message).append(NL);

            message = checkCollection("objektů", new IGetCollection() {
                          @Override public Collection<?> getCollection() {
                              return place.getObjects();
                          }
                      });
            ok &= (message.charAt(0) == '+');
            sb.append("    ").append(message).append(NL);
        }
        stopGame();
        return insertSummary(sb, ok,
                             "+ Generované kolekce se zdají korektní",
                             "- GENEROVANÉ KOLEKCE UMOŽŇUJÍ OVLIVNĚNÍ ZVENKU");
    }


    /***************************************************************************
     * Potvrdí, že kolekce dodaná zadaným objektem,
     * neumožňuje zvenku ovlivnit chování hry ani jejich součástí.
     *
     * @param objects   Název (označení) testovné kolekce
     * @param generator Objekt, který vrací testovanou kolekci
     * @return Zpráva o výsledku testu
     */
    private String checkCollection(String objects, IGetCollection generator)
    {
        String ko = " Kolekce " + objects;
        Collection<?> obtained = generator.getCollection();
        Collection<?> copy     = new ArrayList<>(obtained);
        try {
            obtained.clear();
        }
        catch(Exception e) {
            return '+' + ko + " je neměnná: " + copy;
        }
        if (copy.isEmpty()) {
            return '+' + ko + " je prázdná a tudíž těžko testovatelná";
        }
        obtained = generator.getCollection();
        if (obtained.containsAll(copy)  &&  copy.containsAll(obtained)) {
            return '+' + ko + " je pravděpodobně vracená v kopii: " + copy;
        }
        else {
            return '-' + ko + " umožňuje ovlivnění zvenku: " + copy;
        }
    }


    /***************************************************************************
     * Ověří, že hra umí vrátit přepravku s názvy základních příkazů.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyReturnsCommands()
    {
        try {
            commandNames = gameG.getBasicCommands();
            if (commandNames == null) {
                throw new Exception();
            }
            command2ID.put(commandNames.MOVE_CMD_NAME,     cMOVE);
            command2ID.put(commandNames.PUT_DOWN_CMD_NAME, cPUT );
            command2ID.put(commandNames.PICK_UP_CMD_NAME,  cPICK);
            command2ID.put(commandNames.END_CMD_NAME,      cEND );
            return "+ Základní příkazy hry: " + commandNames;
        }
        catch (Exception e) {
            return "- HRA NEVRACÍ PŘEPRAVKU S NÁZVY ZÁKLADNÍCH PŘÍKAZŮ";
        }
    }


    /***************************************************************************
     * Prověří, že názvy příkazů zadané v přepravce,
     * se shodují s názvy příkazů v kolekci příkazů.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyCommandNames()
    {
        Collection <? extends ICommand> cmdCollection = gameG.getAllCommands();
        Collection<String> allCmdNames = new HashSet<>();
        for (ICommand cmd : cmdCollection) {
            String cmdName = cmd.getName().toLowerCase();
            allCmdNames.add(cmdName);
        }
        if ((allCmdNames.contains(commandNames.END_CMD_NAME     .toLowerCase()))  &&
            (allCmdNames.contains(commandNames.MOVE_CMD_NAME    .toLowerCase()))  &&
            (allCmdNames.contains(commandNames.PICK_UP_CMD_NAME .toLowerCase()))  &&
            (allCmdNames.contains(commandNames.PUT_DOWN_CMD_NAME.toLowerCase())))
        {
            return "+ Oznámené základní příkazy jsou ve vrácené kolekci " +
                     "všech příkazů hry";
        }
        else {
            return "- VE VRÁCENÉ KOLEKCI VŠECH PŘÍKAZŮ HRY " +
                     "CHYBÍ NĚKTERÉ Z OZNÁMENÝCH ZÁKLADNÍCH PŘÍKAZŮ";
        }
    }


    /***************************************************************************
     * Potvrdí, že prostory třídy vrací souřadnice,
     * kam se má na plánku umístit hráč při pobytu v daném prostoru.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyLocalizablePlaces()
    {
        Collection<? extends IPlaceG> places = gameG.getAllPlaces();
        if ((places == null)  ||  (places.size() < MIN_PLACES)) {
            return "- HRA PRAVDĚPODOBNĚ NEVRACÍ KOLEKCI VŠECH PROSTORŮ" +
                "\n   Prostory: " + places;
        }

        StringBuilder sb = new StringBuilder(
                           "+ Vracení souřadnic hráče v prostru OK");
        Set<Point> points = new HashSet<>();
        for (IPlaceG place : places) {
            if ((place == null)  ||  (! points.add(place.getPosition()))) {
                sb.delete(0, sb.length()).
                   append("- HRA VRACÍ PRO NĚKOLIK PROSTORŮ SHODNÉ SOUŘADNICE");
                break;      //-------------->
            }
        }
        sb.append("\n  Vracené souřadnice:");
                for (IPlaceG p : places) {
                    sb.append("\n    ").append(p.getName()).
                       append(" - ").append(p.getPosition());
                }
        return sb.toString();
    }


    /***************************************************************************
     * Prověří, že hra vrací URL souboru s nápovědou umístěného ve složce
     * {@code data}, který navíc obsahuje hypertextový odkaz na další soubor.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyHelpURL()
    {
        boolean       ok = true;
        StringBuilder sb = new StringBuilder();
        URL      helpURL = gameG.getHelpURL();
        String  helpPath = helpURL.toString();

        if (helpPath.startsWith(dataPackagePath)) {
            sb.append("  + Soubor nápovědy je ve správné složce\n");
        }
        else {
            sb.append("  - SOUBOR NÁPOVĚDY JE VE ŠPATNÉ SLOŽCE:\n");
            ok = false;
        }
        sb.append("    ").append(helpPath).append(NL);

        String  help = readText(helpURL);
        Pattern pattern = Pattern.compile("<\\s*a\\s+href\\s*=[^>]*>");
        Matcher matcher = pattern.matcher(help);

        if (matcher.find()) {
            sb.append("  + Soubor nápovědy obsahuje hypertextový odkaz\n");
        }
        else {
            sb.append("  - SOUBOR NÁPOVĚDY NEOBSAHUJE HYPERTEXTOVÝ ODKAZ\n");
            ok = false;
        }

        return insertSummary(sb, ok,
               "+ Soubor nápovědy je pravděpodobně implementován správně",
               "- IMPLEMENTACE SOUBORU NÁPOVĚDY OBSAHUJE CHYBY");
    }


    /***************************************************************************
     * Potvrdí, že prostory třídy vrací souřadnice,
     * kam se má na plánku umístit hráč při pobytu v daném prostoru.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyMapImage()
    {
        boolean       ok = true;
        StringBuilder sb = new StringBuilder();

        ImageIcon map = gameG.getMap();
        int width  = map.getIconWidth();
        int height = map.getIconHeight();
        if ((width == 640)  &&  (height == 480)) {
            sb.append("  + Plánek hry má správné rozměry");
        }
        else {
            ok = false;
            sb.append("  - PLÁNEK HRY MÁ ŠPATNÉ ROZMĚRY");
        }
        sb.append(": Šířka=").append(width).append(", Výška=").append(height).
           append(NL);

        String mapPath  = map.toString();
        if (mapPath.startsWith(dataPackagePath)) {
            sb.append("  + Plánek je umístěn v podbalíčku data\n");
        }
        else {
            ok = false;
            sb.append("  - PLÁNEK NENÍ UMÍSTĚN V PODBALÍČKU DATA\n");
        }
        sb.append("    ").append(map).append(NL);

        return insertSummary(sb, ok,
               "+ Plánek je pravděpodobně implementován správně",
               "- IMPLEMENTACE PLÁNKU OBSAHUJE CHYBY");
   }


    /***************************************************************************
     * Potvrdí, že prostory třídy vrací souřadnice,
     * kam se má na plánku umístit hráč při pobytu v daném prostoru.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyObjectImages()
    {
        if (! gameG.isAlive()) {
            gameG.executeCommand("");
        }

        boolean       ok = true;
        StringBuilder sb = new StringBuilder();
        int       offset = dataPackagePath.length();

        Collection<? extends IPlaceG> places = gameG.getAllPlaces();
        for (IPlaceG place : places) {
            sb.append("  = Prostor: ").append(place.getName()).append(NL);
            Collection<? extends IObjectG> objects = place.getObjects();

            for (IObjectG object : objects) {
                sb.append("    Objekt ").append(object.getName()).append(": ");
                Icon icon;
                try {
                    icon = object.getPicture();
                    sb.append("Šířka="  ).append(icon.getIconWidth()).
                       append(", Výška=").append(icon.getIconHeight());
                }
                catch (Exception e) {
                    sb.append("NEVRACÍ POUŽITELNÝ OBRÁZEK\n");
                    ok = false;
                    continue;
                }
                String místo = icon.toString();
                if (místo.startsWith(dataPackagePath)) {
                    sb.append(", Zdroj=").append(místo.substring(offset)).
                       append(NL);
                }
                else {
                    sb.append(", NENÍ ULOŽEN VE SLOŽCE data\n");
                    ok = false;
                }
            }
        }
        gameG.stop();
        return insertSummary(sb, ok,
               "+ Obrázky objektů v prostorech jsou pravděpodobně " +
                  "implementovány správně",
               "- OBRÁZKY OBJEKTŮ V MÍSTNOSTECH NEJSOU " +
                  "IMPLEMENTOVÁNY SPRÁVNĚ");
    }


    /***************************************************************************
     * Prověří, že hra nehlásí problémy
     * při přihlašování a odhlašování posluchačů.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyAddRemoveListeners()
    {
        GameGListenerUseTest test = new GameGListenerUseTest(gameG);
        return test.verifyAddRemoveListeners();
    }


    /***************************************************************************
     * Prověří, že hra korektně spolupracuje s povinně podporovanými posluchači.
     *
     * @return Zpráva o výsledku testu
     */
    private String verifyListenersUse()
    {
        GameGListenerUseTest test = new GameGListenerUseTest(gameG);
        return test.verifyListenersUse();
    }


    /***************************************************************************
     * Přečte obsah zdroje textu umístěného na zadané URL
     * a vrátí přečtený obsah jako textový řetězec.
     *
     * @param helpURL URL zdroje, jehož obsah cheme číst
     * @return Načtený text
     */
    private String readText(URL helpURL)
    {
        BufferedReader br;
        try {
             br = new BufferedReader(
                      new InputStreamReader(
                          helpURL.openStream()));
        }
        catch (IOException ex) {
            return "- NEPODAŘILO SE OTEVŘÍT PROUD Z: " + helpURL;
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append(NL);
            }
        }
        catch (IOException ex) {
            return "- PROBLÉMY PŘI ČTENÍ PROUDU Z: " + helpURL;
        }
        return sb.toString();
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     *
     */
    private interface IGetCollection
    {
        /***********************************************************************
         * Vrátí kolekci.
         *
         * @return Požadovaná kolekce
         */
        Collection<?> getCollection();
    }



//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        GameTxtClassTest inst = new GameTxtClassTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
