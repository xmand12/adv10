/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Knihovní třída {@code IO} obsahuje sadu metod
 * pro jednoduchý vstup a výstup prostřednictvím dialogových oken
 * spolu s metodou zastavující běh programu na daný počet milisekund
 * a metodu převádějící texty na ASCII jednoduchým odstraněním diakritiky.
 *
 * @author   Rudolf PECINOVSKÝ
 * @version  3.00.001
 */
public final class IO
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Logger, prostřednictvím nějž zaznamenáváme veškeré informace. */
    protected final static DBG_Logger DBG = DBG_Logger.getInstance();

    /** Přepravka pro nulové velikosti okrajů. */
    private static final Insets NULOVÝ_OKRAJ = new Insets(0, 0, 0, 0);

    /** Rozdíl mezi tloušťkou rámečku okna ohlašovanou před a po
     *  volání metody {@link JFrame#setResizable(boolean)}.
     *  Tento rozdíl ve Windows ovlivňuje nastavení velikosti a pozice.
     *  Při {@code setResizable(true)} jsou jeho hodnoty větší,
     *  a proto se spočte se jako "true" - "false". */
    private static final Insets INSETS_DIF;

    /** Informace o tom, budou-li se opravovat pozice a rozměry oken. */
    private static final boolean OPRAVOVAT;



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Pozice dialogových oken. */
    private static Point poziceOken = new Point(0,0);



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            JFrame okno = new JFrame();
            okno.setLocation(-1000, -1000);
            okno.setResizable(true);
            okno.pack();
            Insets insTrue  = okno.getInsets();
            DBG.fine("Insets - resizable=true:  {0}", insTrue);
            okno.setResizable(false);
            Insets insFalse = okno.getInsets();
            DBG.fine("Insets - resizable=false: {0}", insFalse);
            Insets insets;
            insets = new Insets(insTrue.top   - insFalse.top,
                                   insTrue.left   - insFalse.left,
                                   insTrue.bottom - insFalse.bottom,
                                   insTrue.right  - insFalse.right);
            if (NULOVÝ_OKRAJ.equals(insets)) {
                //Nevěříám mu, určitě kecá
                int úbytek = (insTrue.left == 8)  ?  5  :  1;
                insets = new Insets(úbytek, úbytek, úbytek, úbytek);
            }
            INSETS_DIF = insets;
            OPRAVOVAT = true;
//            OPRAVOVAT = ! NULOVÝ_OKRAJ.equals(INSETS_DIF);
        }
        else {
            INSETS_DIF = NULOVÝ_OKRAJ;
            OPRAVOVAT  = false;
        }
        DBG.fine("INSETS_DIF: {0}" +
               "\nOPRAVOVAT:  {1}\n", INSETS_DIF, OPRAVOVAT);
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Počká zadaný počet milisekund.
     * Na přerušení nijak zvlášť nereaguje - pouze skončí dřív.
     * Před tím však nastaví příznak, aby volající metoda poznala,
     * že vlákno bylo žádáno o přerušení.
     *
     * @param milisekund   Počet milisekund, po něž se má čekat.
     */
    public static void hold(int milisekund)
    {
        try {
            Thread.sleep(milisekund);
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /***************************************************************************
     * Při splnění zadané podmínky otevře dialogové okno s nápisem KONEC
     * a po jeho zavření ukončí program.
     *
     * @param condition  Podmínka, po jejímž splnění se program ukončí
     */
    public static void endIf(boolean condition)
    {
        endIf(condition, null);
    }


    /***************************************************************************
     * Při splnění zadané podmínky otevře dialogové okno se zadanou zprávou
     * a po jeho zavření ukončí program.
     *
     * @param condition  Podmínka, po jejímž splnění se program ukončí
     * @param message Zpráva vypisovaná v dialogovém okně. Je-li {@code null}
     *                nebo prázdný řetězec, vypíše <b>{@code KONEC}</b>.
     */
    @SuppressWarnings("AssignmentToMethodParameter")
    public static void endIf(boolean condition, String message)
    {
        if (condition) {
            if ((message == null)  ||  ("".equals(message))) {
                message = "KONEC";
            }
            message(message);
            System.exit(0);
        }
    }


    /***************************************************************************
     * Zbaví zadaný text diakritických znamének; současně ale odstraní také
     * všechny další znaky nespadající do tabulky ASCII.
     *
     * @param text Text určený k "odháčkování"
     * @return  "Odháčkovaný" text
     */
    public static String toASCII(String text)
    {
        return Odháčkuj.text(text);
    }


    /***************************************************************************
     * Nastaví pozici příštího dialogového okna.
     *
     * @param x  Vodorovná souřadnice
     * @param y  Svislá souřadnice
     */
    public static void placeWindowsAt(int x, int y)
    {
        poziceOken = new Point(x, y);
        if (OPRAVOVAT) {
            poziceOken.x += INSETS_DIF.left;
            poziceOken.y += INSETS_DIF.top + INSETS_DIF.bottom;
        }
    }


    /***************************************************************************
     * Zobrazí dialogové okno se zprávou a umožní uživateli odpovědět
     * ANO nebo NE. Vrátí informaci o tom, jak uživatel odpověděl.
     * Neodpoví-li a zavře dialog, ukončí program.
     *
     * @param question   Zobrazovaný text otázky.
     * @return <b>{@code true}</b> Odpověděl-li uživatel <b>ANO</b>,
     *         <b>{@code false}</b> odpověděl-li <b>NE</b>
     */
    public static boolean agree(Object question)
    {
        JOptionPane jop = new JOptionPane(
                                question,
                                JOptionPane.QUESTION_MESSAGE,   //Message type
                                JOptionPane.YES_NO_OPTION       //Option type
                               );
        processJOP(jop);
        int answer = (Integer)jop.getValue();
        return (answer == JOptionPane.YES_OPTION);
    }


    /***************************************************************************
     * Zobrazí dialogové okno s výzvou k zadání reálné hodoty;
     * při zavření okna zavíracím tlačítkem ukončí aplikaci.
     *
     * @param message       Text, který se uživateli zobrazí.
     * @param doubleImpl   Implicitní hodnota.
     * @return Uživatelem zadaná hodnota, resp. potvrzená implicitní hodnota.
     */
    public static double input(Object message, double doubleImpl)
    {
        return Double.parseDouble(input(message, ""+doubleImpl).trim());
    }


    /***************************************************************************
     * Zobrazí dialogové okno s výzvou k zadání celočíselné hodoty;
     * při zavření okna nebo stisku tlačítka Cancel ukončí aplikaci.
     *
     * @param message     Text, který se uživateli zobrazí.
     * @param intImpl   Implicitní hodnota.
     * @return Uživatelem zadaná hodnota, resp. potvrzená implicitní hodnota.
     */
    public static int input(Object message, int intImpl)
    {
        return Integer.parseInt(input(message, ""+intImpl).trim());
    }


    /***************************************************************************
     * Zobrazí dialogové okno s výzvou k zadání textové hodoty;
     * při zavření okna nebo stisku tlačítka Cancel ukončí aplikaci.
     *
     * @param výzva        Text, který se uživateli zobrazí.
     * @param stringImpl   Implicitní hodnota.
     * @return Uživatelem zadaná hodnota, resp. potvrzená implicitní hodnota.
     */
    public static String input(Object výzva, String stringImpl)
    {
        JOptionPane jop = new JOptionPane(
                              výzva,
                              JOptionPane.QUESTION_MESSAGE,   //Message type
                              JOptionPane.DEFAULT_OPTION  //Option type - OK
                             );
        jop.setWantsInput(true);
        jop.setInitialSelectionValue(stringImpl);
        processJOP(jop);
        String answer = jop.getInputValue().toString();
        return answer;
    }


    /***************************************************************************
     * Zobrazí dialogové okno se zprávou a počká, až je uživatel odklepne;
     * při zavření okna ukončí aplikaci.
     *
     * @param text   Zobrazovaný text.
     */
    public static void message(Object text)
    {
        JOptionPane jop = new JOptionPane(
                          text,                            //Sended message
                          JOptionPane.INFORMATION_MESSAGE  //Message type
                         );
        processJOP(jop);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Třída IO je knihovní třídou a proto není určena k tomu,
     * aby měla nějaké instance.
     */
    private IO() {}


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Creates a dialog from the given {@link JOptionPane}, makes it non-modal
     * and waits for its closing leaving the entered value in the parameter's
     * attribute {@code value}. If the user closed the dialog
     * from the window's system menu, exit the whole application.
     *
     * @param jop {@link JOptionPane} serving as a base for the dialog
     */
    private static void processJOP(JOptionPane jop)
    {
        final int WAITING=0, CANCELLED=1;
        final Boolean[] USER = {true, false};

        final JDialog jd = jop.createDialog((JDialog)null, "Information" );

        jd.addWindowListener(new WindowAdapter()
        {
            /** Set the information about closing the window from its
             *  systme menu - the application will be cancelled. */
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized(USER) {
                    USER[CANCELLED] = true;
                    System.exit(1);
                }
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
                poziceOken = jd.getLocation();
                if (jd.isShowing()) {
                    return;
                }else{
                    jd.dispose();
                    synchronized(USER) {
                        USER[WAITING] = false;
                        USER.notifyAll();
                    }
                }
            }
         });

        jd.setModal(false);
        jd.setVisible(true);
        jd.setLocation(poziceOken );
        jd.toFront();
        jd.setAlwaysOnTop(true);
//        jd.setAlwaysOnTop(false);

        //Waiting until the user answers or closes the dialog
        synchronized(USER) {
            while (USER[WAITING]) {
                try {
                    USER.wait();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Třída {@code Odháčkuj_RUP} je knihovní třídou poskytující metodu na
     * odstranění diakritiky ze zadaného textu a následné převedení všech znaků,
     * jejichž kód je stále větší než 127, na příslušné kódové
     * únikové posloupnosti (escape sekvence).
     */
    private static class Odháčkuj
    {
    //== CONSTANT CLASS ATTRIBUTES =============================================

        /** Mapa s převody znaků do ASCII. */
        private static final Map<Character,String> PŘEVOD =
                                            new HashMap<Character, String>(64);
    //== VARIABLE CLASS ATTRIBUTES =============================================
    //== STATIC INITIALIZER (CLASS CONSTRUCTOR) ================================
        static {
            String[][] dvojice = {

                {"Á", "A"},  {"á", "a"},    {"Ä", "AE"}, {"ä", "ae"},
                {"Č", "C"},  {"č", "c"},
                {"Ď", "D"},  {"ď", "d"},    {"Ë", "E"},  {"ë", "e"},
                {"É", "E"},  {"é", "e"},
                {"Ě", "E"},  {"ě", "e"},
                {"Í", "I"},  {"í", "i"},    {"Ï", "IE"}, {"ï", "ie"},
                {"Ĺ", "L"},  {"ĺ", "l"},    {"Ľ", "L"},  {"ľ", "l"},
                {"Ň", "N"},  {"ň", "n"},
                {"Ó", "O"},  {"ó", "o"},    {"Ö", "OE"}, {"ö", "oe"},
                {"Ô", "O"},  {"ô", "o"},
                {"Ŕ", "R"},  {"ŕ", "r"},    {"Ř", "R"},  {"ř", "r"},
                {"Š", "S"},  {"š", "s"},
                {"Ť", "T"},  {"ť", "t"},
                {"Ú", "U"},  {"ú", "u"},    {"Ü", "UE"}, {"ü", "ue"},
                {"Ů", "U"},  {"ů", "u"},
                {"Ý", "Y"},  {"ý", "y"},    {"Ÿ", "YE"}, {"ÿ", "ye"},
                {"Ž", "Z"},  {"ž", "z"},
                {"ß", "ss"},
                {"«", "<<"}, {"»", ">>"},
//                {"",""},
            };
            for (String[] ss : dvojice) {
                PŘEVOD.put(new Character(ss[0].charAt(0)),  ss[1]);
            }
            dvojice = null;
        }



    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

        /***********************************************************************
         * Zbaví zadaný text diakritických znamének a všechny znaky s kódem
         * větším než 127 převede na odpovídající posloupnost ASCII znaků,
         * resp. na standardní tvar {@code \}{@code uXXXX}.
         *
         * @param text Text určený k "odháčkování"
         * @return  "Odháčkovaný" text
         */
        public static String text(CharSequence text)
        {
            final int DÉLKA = text.length();
            final StringBuilder sb = new StringBuilder(DÉLKA);
            for (int i = 0;   i < DÉLKA;   i++) {
                char c = text.charAt(i);
                if (c < 128) {
                    sb.append(c);
                }else if (PŘEVOD.containsKey(c)) {
                    sb.append(PŘEVOD.get(c));
                }else {
                    sb.append(rozepiš(c));
                }
            }
            return sb.toString();
        }



    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

       /** Soukromy konstruktor bránící vytvoření instance. */
        private Odháčkuj() {}


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================

        /***********************************************************************
         * Rozepíše zadaný znak do příslušné ńikové k´dové posloupnosti.
         *
         * @param c Převáděný znak
         * @return Text ve formátu \\uXXXX
         */
        private static String rozepiš(char c) {
            return String.format("\\u%04x", (int)c);
        }



    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED TYPES AND INNER CLASSES ======================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



    /***************************************************************************
     * Třída {@code Oprava} je knihovní třídou poskytující metody
     * pro opravy nejrůznějších nesrovnalostí týkajících se práce
     * s grafickým vstupem a výstupem.
     */
    public static class Oprava
    {
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    //== STATIC INITIALIZER (CLASS CONSTRUCTOR) ================================
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

        /***********************************************************************
         * Ve Windows 7 používajících definuje Java jinou velikost okna,
         * než odpovídá velikosti panelu obrázku.
         *
         * @param cont   Kontejner, jehož rozměry upravujeme
         */
        public static void poziceOkna(Container cont)
//          , Insets insDif)
//          * @param insDif Rozdíly rozměrů okrajů deklarované před a po zákazu
//          *               změny velikosti okna - ve Windows ovlivňuje pozici
       {
            Point  loc;
            if (OPRAVOVAT) {
                loc = cont.getLocation();
                cont.setLocation(loc.x + INSETS_DIF.left,
                                 loc.y + INSETS_DIF.top);
            }
        }


        /***********************************************************************
         * Ve Windows 7 definuje Java jinou velikost okna,
         * než odpovídá velikosti panelu obrázku.
         *
         * @param cont     Kontejner, jehož rozměry upravujeme
         */
        public static void rozměrOkna(Container cont)
//                                        Insets insTrue, Insets insFalse)
//         * @param insTrue  Velikosti rozměrů okrajů ohlašované před zákazem
//         *                 změny velikosti okna (tj. dokud je změna povolena)
//         * @param insFalse Velikosti rozměrů okrajů ohlašované po zákazu
//         *                 změny velikosti okna (tj. když už je změna zakázána)
//         * @return Rozdíly mezi rozměry zadanými v parametrech
        {
//            Insets    insDif;
//            insDif = new Insets(insTrue.top    - insFalse.top,
//                                insTrue.left   - insFalse.left,
//                                insTrue.bottom - insFalse.bottom,
//                                insTrue.right  - insFalse.right);
            Dimension dim;
            if (OPRAVOVAT) {
                dim = cont.getSize();
                cont.setSize(dim.width - INSETS_DIF.left - INSETS_DIF.right,
                             dim.height- INSETS_DIF.top  - INSETS_DIF.bottom);
            }
//            return insDif;
        }



    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

       /** Soukromy konstruktor bránící vytvoření instance. */
        private Oprava() {}


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED TYPES AND INNER CLASSES ======================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



//== TESTING CLASSES AND METHODS ===============================================
////%X+ main
//
//    /***************************************************************************
//     * Ověřuje možnost umístit rodiče dialogového okna (a tím i dialogové
//     * okno) na požadované místo na obrazovce.
//     */
//    public static void test()
//    {
//        String txt = "Příliš žluťoučký kůň úpěl ďábelské ódy.\n" +
//                     "PŘÍLIŠ ŽLUŤOUČKÝ KŮŇ ÚPĚL ĎÁBELSKÉ ÓDY.\n" +
//                     "The quick brown fox jumps over the lazy dog.\n" +
//                     "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.\n" +
//                     "[ä-ë-ï-ö-ü-ÿ] - [Ä-Ë-Ï-Ö-Ü-Ÿ]\n" +
//                     "(§-ß-%-‰)\n" +
//                     "";    //Aby bylo možno předchozí řádky proházet
//
////        cz.pecinovsky.česky.tvary.SprávcePlátna cm =
////                 cz.pecinovsky.česky.tvary.SprávcePlátna.getInstance();
////        Plátno cm = Plátno.getPlátno();
//        String bhc = Odháčkuj.text(txt);
//        DBG.fine(txt + "\n\n" + bhc);
//        IO.message(txt + "\n" + bhc);
////        placeWindowsAt(2000, 500);
////        message("Druhý pokus: [2000;500]");
////
////         String ss;
////         boolean c = agree("Do you agree?");
////         ss = "Souhlas:  " + c;
////
//////         setDialogsPosition(500,  500);
////         String s = input(ss, "ABC");
////         ss = "String: " + s;
////
//////         setDialogsPosition(1000, 500);
////         double d = input(ss, 12.34);
////         ss = "Double: " + d;
////         message(ss);
//         System.exit(0);
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args) {
//        test();
//    }
////%X- main
}
