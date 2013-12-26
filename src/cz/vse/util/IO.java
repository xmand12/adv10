/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺.
 */
package cz.vse.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Library class {@code IO} contains a set of methods
 * for easy input and output through dialogs,
 * a method freezing program for a specified number of miliseconds
 * and a method converting text to ASCII by removing accents
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 1.01.4240 — 2012-10-13
 */
public final class IO
{
    //== CONSTANT CLASS FIELDS =================================================

    /** Container for zero size borders */
    private static final Insets ZERO_BORDER = new Insets(0, 0, 0, 0);

    /** Difference between width of border reported before and after
     *  calling of method {@link #setResizable(boolean)}.
     *  This difference in Windows does not change set size and location.
     *  When {@code setResizable(true)} it's values are bigger,
     *  therefore  we calculate it as "true" - "false".*/
    private static final Insets INSETS_DIF;

    /** Whether correcting of size and location of windowswill occur. */
    private static final boolean CORRECT;



    //== VARIABLE CLASS FIELDS =================================================

    /** Location of dialogs. */
    private static Point windowLocation = new Point(0,0);

    /** Flag for the test mode - if it is {@code true},
     *  the method {@link #message(Object)} doesn't open a dialog
     *  and method {@link #pause(int)} doesn't wait. */
    private static boolean testMode = false;



    //== STATIC INICIALIZATION BLOCK - STATIC CONSTRUCTOR ======================

    /** Windows Vista + Windows 7 are not able to agree with Java on real
     *  size of winodows and their borders. Therefore dialog positioning
     *  to the given coordinates doesn't work well.
     *  The following static constructor tries to reveal the OS behavior
     *  and prepare the accordinf corredctions.
     *  Let's hope tha it will be not needed soon.
     */
    static {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            JFrame frame = new JFrame();
            frame.setLocation(-1000, -1000);
            frame.setResizable(true);
            frame.pack();
            Insets insTrue  = frame.getInsets();
            frame.setResizable(false);
            Insets insFalse = frame.getInsets();
            Insets insets;
            insets = new Insets(insTrue.top    - insFalse.top,
                                insTrue.left   - insFalse.left,
                                insTrue.bottom - insFalse.bottom,
                                insTrue.right  - insFalse.right);
            if (ZERO_BORDER.equals(insets)) {
                //I don't believe it
                int decrement = (insTrue.left == 8)  ?  5  :  1;
                insets = new Insets(decrement, decrement, decrement, decrement);
            }
            INSETS_DIF = insets;
            CORRECT = true;
//            CORRECT = ! ZERO_BORDER.equals(INSETS_DIF);
        }
        else {
            INSETS_DIF = ZERO_BORDER;
            CORRECT  = false;
        }
    }



    //== CONSTANT INSTANCE FIELDS ==============================================
    //== VARIABLE INSTANCE FIELDS ==============================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

    /***************************************************************************
     * Freezes the program execution for specified nuber of miliseconds.
     * It doesn't react on interrupt - it only ends earlier.
     * However before end it sets the interrupt flag to allot the calling method
     * to recognize that the thread was interrupted.
     *
     * @param milliseconds The length of freezing time in milliseconds
     */
    public static void pause(int milliseconds)
    {
        if (testMode) {
            Informant.informer.hold(milliseconds);
        }
        else {
            try {
                Thread.sleep(milliseconds);
            }catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    /***************************************************************************
     * If the condition in parameter is met then opens a dialog with message END
     * When this dialog is closed, aplication exits
     *
     * @param end  Condition, when met aplication exits
     */
    public static void endIf(boolean end)
    {
        endIf(end, null);
    }


    /***************************************************************************
     * If the condition is met then opens a dialog with specified message
     * When this dialog is closed, aplication exits
     *
     * @param end     Condition, when met aplication exits
     * @param message Message displayed in the dialog. If equals {@code null}
     *                or empty string, displays <b>{@code END}</b>.
     */
    public static void endIf(boolean end, String message)
    {
        if (end) {
            if ((message == null)  ||  (message.equals(""))) {
                message = "END";
            }
            inform(message);
            System.exit(0);
        }
    }


    /***************************************************************************
     * Removes all accents from the given text;
     * simultaneously it also removes all characters which are not in ASCII.
     *
     * @param text Text converted to ASCII
     * @return Result text
     */
    public static String removeAccents(String text)
    {
        return RemoveAccents.text(text);
    }


    /***************************************************************************
     * Set the future dialog positon.
     *
     * @param x  x-coordinate of desired location
     * @param y  x-coordinate of desired location
     */
    public static void setDialogsPosition(int x, int y)
    {
        windowLocation = new Point(x, y);
        if (CORRECT) {
            windowLocation.x += INSETS_DIF.left;
            windowLocation.y += INSETS_DIF.top + INSETS_DIF.bottom;
        }
    }


    /***************************************************************************
     * Opens a dialog titled "Question" with a given message (asked question)
     * and asks user to answer YES or NO. If it doesn't answer
     * and closes the dialog, the whole application exits.
     *
     * @param question The asked question
     * @return <b>{@code true}</b> if the user has answered <b>YES</b>,
     *         <b>{@code false}</b> if the user answered <b>NO</b>
     */
    public static boolean confirm(Object question)
    {
        JOptionPane pane = new JOptionPane(
                               question,
                               JOptionPane.QUESTION_MESSAGE,   //Message type
                               JOptionPane.YES_NO_OPTION       //Option type
                               );
        processJOP(pane);
        int answer = (Integer)pane.getValue();
        return (answer == JOptionPane.YES_OPTION);
    }


    /***************************************************************************
     * Opens a dialog asking user for entering a double value;
     * after closing the window by the closing button the application exits.
     *
     * @param prompt        The displayed text message
     * @param defaultDouble The default value
     * @return              Value entered by user or confirmed implicit value
     */
    public static double enter(Object prompt, double defaultDouble)
    {
        String defVal = Double.toString(defaultDouble).trim();
        String answer = enter(prompt, defVal);
        return Double.parseDouble(answer);
    }


    /***************************************************************************
     * Opens a dialog asking user for entering requested integer value;
     * after closing the window by the closing or Cancel button,
     * the application exits.
     *
     * @param  prompt       The displayed text message
     * @param  defaultInt   The default value
     * @return              Value entered by user or confirmed implicit value
     */
    public static int enter(Object prompt, int defaultInt)
    {
        String defVal = Integer.toString(defaultInt).trim();
        String answer = enter(prompt, defVal);
        return Integer.parseInt(answer);
    }


    /***************************************************************************
     * Opens a dialog asking user for entering requested String value;
     * when it closes the window with the closing or Cancel button,
     * application exits.
     *
     * @param prompt        The displayed text message
     * @param defaultString The default value
     * @return              Value entered by user or confirmed implicit value
     */
    public static String enter(Object prompt, String defaultString)
    {
        JOptionPane pane = new JOptionPane(
                           prompt,
                           JOptionPane.QUESTION_MESSAGE,   //Message type
                           JOptionPane.DEFAULT_OPTION  //Option type - OK
                           );
        pane.setWantsInput(true);
        pane.setInitialSelectionValue(defaultString);
        processJOP(pane);
        String answer = pane.getInputValue().toString();
        return answer;
    }


    /***************************************************************************
     * Opens a dialog with specified message and wait for confirmation
     * by pressing OK;
     * when the dialog is closed by closing button the aplication exits.
     *
     * @param text  The shown message
     */
    public static void inform(Object text)
    {
        if (testMode) {
            Informant.informer.test(text);
        }
        else {
            JOptionPane jop = new JOptionPane(
                              text,                            //Sended message
                              JOptionPane.INFORMATION_MESSAGE  //Message type
                             );
            processJOP(jop);
        }
    }



    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

    /***************************************************************************
     * The class {@code IO} is a library class and therefore
     * it should not allow constructing instances.
     */
    public IO() {}



    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================

    /***************************************************************************
     * Creates a dialog from the given {@link JOptionPane},
     * makes it non-modal and waits for its closing
     * leaving the entered value in the parameter's attribute {@code value}.
     * If the user closed the dialog from the window's system menu,
     * exits the whole application.
     *
     * @param pane The processed JOptionPane
     */
    private static void processJOP(JOptionPane pane)
    {
//        final int WAITING=0, CANCELLED=1;
        final AtomicBoolean answered = new AtomicBoolean(false);

        final JDialog dialog = pane.createDialog((JDialog)null, "Information");

        dialog.addWindowListener(new WinAdapter(dialog, answered));

        dialog.setModal(false);
        dialog.setVisible(true);
        dialog.setLocation(windowLocation);
        dialog.toFront();
        dialog.setAlwaysOnTop(true);
//        dialog.setAlwaysOnTop(false);

        //Waites until the user answers or closes the dialog
        synchronized(answered) {
            while (! answered.get()) {
                try {
                    answered.wait();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED AND INNER CLASSES ============================================

    /***************************************************************************
     * Class {@code Correction} is library class providing methods
     * for correcting various inconsistencies of
     * working with graphical input and output
     */
    public static class Correction
    {
    //== CONSTANT CLASS FIELDS =================================================
    //== VARIABLE CLASS FIELDS =================================================
    //== STATIC INICIALIZATION BLOCK - STATIC CONSTRUCTOR ======================
    //== CONSTANT INSTANCE FIELDS ==============================================
    //== VARIABLE INSTANCE FIELDS ==============================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

        /***********************************************************************
         * In Windows 7 Java defines window size
         * that do not correspond to the size of the inner panel.
         *
         * @param cont   Container dimensions of which we are modifying
         */
        public static void windowLocation(Container cont)//, Insets insDif)
        {
            Point  loc;
            if (CORRECT) {
                loc = cont.getLocation();
                cont.setLocation(loc.x + INSETS_DIF.left,
                                 loc.y + INSETS_DIF.top);
            }
        }


        /***********************************************************************
         * In Windows 7 Java defines window which differs from the real size
         * of the panel.
         *
         * @param cont     Container dimensions of which we are modifying
         */
        public static void windowSize(Container cont)
        {
            Dimension dim;
            if (CORRECT) {
                dim = cont.getSize();
                cont.setSize(dim.width - INSETS_DIF.left - INSETS_DIF.right,
                             dim.height- INSETS_DIF.top  - INSETS_DIF.bottom);
            }
        }



    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

       /** Private constructor preventing creation of instances. */
        private Correction() {}


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED AND INNER CLASSES ============================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



///#############################################################################
///#############################################################################
///#############################################################################

    /***************************************************************************
     * Instances of the class {@code Informant} are mediators between
     * testing and tested objects.
     */
    public static class Informant
    {
    //== CONSTANT CLASS FIELDS =================================================

    /** Prostředník, který přihlášeným testovacím programům přeposílá
     *  zprávy o zavolání definovaných metod. */
    public static final Informant informer = new Informant();



    //== VARIABLE CLASS FIELDS =================================================
    //== STATIC INITIALIZER (CLASS CONSTRUCTOR) ================================
    //== CONSTANT INSTANCE FIELDS ==============================================

        /** Seznam přihlášených testovacích programů,
         *  kterým budou přeposílány zprávy o volání zadaných metod. */
        private final List<ITester> seznam = new ArrayList<ITester>();



    //== VARIABLE INSTANCE FIELDS ==============================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================


       /** Private constructor protecting instance creation. */
        private Informant() {}


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================

        /***********************************************************************
         * Add the given object to the list of object
         * obtaining notes that the specified method was called.
         *
         * @param tester Added testing object
         */
        public void register(ITester tester)
        {
            if (seznam.contains(tester)) { return; }
            seznam.add(tester);
            testMode = true;
        }


        /***********************************************************************
         * Add the given object to the list of object
         * obtaining notes that the specified method was called.
         *
         * @param tester Odebíraný testovací objekt
         */
        public void unregister(ITester tester)
        {
            seznam.remove(tester);
            if (seznam.isEmpty()) {
                testMode = false;
            }
        }



    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================

        /***********************************************************************
         * Reports calling the method {@link IO#pause(int)}
         * to all registerred observers and pass its argument in argument.
         *
         * @param ms Mow many miliseconds should be the program halten
         */
        private void hold(int ms)
        {
            for (ITester it : seznam) {
                it.pause(ms);
            }
        }


        /***********************************************************************
         * Reports calling the method {@link IO#inform(Object)}
         * and pass the obtained text in arguments.
         *
         * @param message The message to be shown
         */
        private void test(Object message)
        {
            for (ITester it : seznam) {
                it.inform(message);
            }
        }



    //== MEMBER DATA TYPES =====================================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



///#############################################################################
///#############################################################################
///#############################################################################

    /***************************************************************************
     * Instances of the interface {@code ITester} are testing objects
     * which want to be informed about interesting events.
     */
    public interface ITester
    {
    //== CONSTANTS =============================================================
    //== DECLARED METHODS ======================================================

        /***********************************************************************
         * Reports a call of the method {@link IO#pause(int)}
         * and pass the set time in the argument.
         *
         * @param ms The set time in miliseconds
         */
        public void pause(int ms);


        /***********************************************************************
         * Reports calling the method {@link IO#inform(Object)}
         * and pass the text in the argument.
         *
         * @param message The shown text
         */
        public void inform(Object message);



    //== INHERITED METHODS =====================================================
    //== EMBEDDED DATA TYPES ===================================================
    }



///#############################################################################
///#############################################################################
///#############################################################################

    /***************************************************************************
     * Class {@code RemoveAccents_RUP} is library class providing method for
     * removal of accents from provided text and consequential replacement of
     * all characters whose code is still higher than 127 to their respective
     * escape sequences.
     */
    private static class RemoveAccents
    {
    //== CONSTANT CLASS FIELDS =================================================

        /** Map with conversion of characters to ASCII. */
        private static final Map<Character,String> CONVERSION =
                                            new HashMap<Character, String>(64);
    //== VARIABLE CLASS FIELDS =================================================
    //== STATIC INICIALIZATION BLOCK - STATIC CONSTRUCTOR ======================
        static {
            String[][] pairs = {

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
            for (String[] ss : pairs) {
                CONVERSION.put(new Character(ss[0].charAt(0)),  ss[1]);
            }
            pairs = null;
        }



    //== CONSTANT INSTANCE FIELDS ==============================================
    //== VARIABLE INSTANCE FIELDS ==============================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

        /***********************************************************************
         * Removes accents from the given text - <b>WARNING</b> -
         * Along with it removes all characters whose code is greater than 127.
         *
         * @param text Text for the accent removal
         * @return  Text with removed accents
         */
        public static String text(CharSequence text)
        {
            final int LENGTH = text.length();
            final StringBuilder sb = new StringBuilder(LENGTH);
            for (int i = 0;   i < LENGTH;   i++) {
                char c = text.charAt(i);
                if (c < 128) {
                    sb.append(c);
                }else if (CONVERSION.containsKey(c)) {
                    sb.append(CONVERSION.get(c));
                }else {
                    sb.append(expand(c));
                }
            }
            return sb.toString();
        }



    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

       /** Private constructor preventing creation of instances. */
        private RemoveAccents() {}


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================

        /***********************************************************************
         * Expands the specified character into it's escape sequence
         *
         * @param c   Character to expand
         * @return   Text formated \\uXXXX
         */
        private static String expand(char c) {
            return String.format("\\u%04x", (int)c);
        }



    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED AND INNER CLASSES ============================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



///#############################################################################
///#############################################################################
///#############################################################################



    /***************************************************************************
     * Instances of class {@code WindoewAdapter} represents ...
     */
    public static class WinAdapter extends WindowAdapter
    {
    //== CONSTANT CLASS FIELDS =================================================
    //== VARIABLE CLASS FIELDS =================================================
    //== STATIC INITIALIZER (CLASS CONSTRUCTOR) ================================
    //== CONSTANT INSTANCE FIELDS ==============================================

        private final JDialog dialog;
        private final AtomicBoolean answered;



    //== VARIABLE INSTANCE FIELDS ==============================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

    /***********************************************************************
     *
     * @param dialog
     * @param answered
     */
    public WinAdapter(JDialog dialog, AtomicBoolean answered)
    {
        this.dialog   = dialog;
        this.answered = answered;
    }



    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================

        /***********************************************************************
         * Processes information about closing the window
         * through its system menu - the application will be cancelled.
         *
         * @param e The processed event
         */
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(1);
        }


        /***********************************************************************
         * Look for the reason of deacitvation of dialog -
         * if its window is not visible, the user answered.
         *
         * @param e The processed event
         */
        @Override
        public void windowDeactivated(WindowEvent e) {
            windowLocation = dialog.getLocation();
            if (dialog.isShowing()) {
                return;
            }else{
                dialog.dispose();
                synchronized(answered) {
                    answered.set(true);
                    answered.notifyAll();
                }
            }
        }

    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== MEMBER DATA TYPES =====================================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



///#############################################################################
///#############################################################################
///#############################################################################



    //== TESTING CLASSES AND METHODS ===========================================
}
