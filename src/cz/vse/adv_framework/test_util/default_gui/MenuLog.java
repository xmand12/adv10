/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Instance třídy {@code MenuLog} představují položky hlavní nabídky
 * s příkazy, které ovlivňují vytváření a zobrazování žurnálu příkazů
 * zadávaných hře.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
public class MenuLog extends JMenu
        implements IListener<String>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, pro které nabídka pracuje. */
    private final IMyGUI gui;

    /** Seznam zapamatovaných příkazů. */
    private final List<TimeCommand> commandList = new ArrayList<>();

    /** Okno, v němž se bude na požádání vypisovat žurnál zadaných příkazů. */
    private final LogWindow logWindow = new LogWindow();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Příznak toho, zda se vůbec bude ukládat žurnál. */
    private boolean toLog = true;

    /** Příznak zobrazování žurnálu v samostatném okně. */
    private boolean intoWindow = false;

    /** Příznak ukládání žurnálu do zadaného souboru. */
    private boolean intoFile = false;

    /** Soubor, do nějž se ukládá zaznamenávaný žurnál. */
    private File file = null;

    /** Proud, do nějž se ukládají zaznamenávaná data. */
    private Writer writer = null;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nabídku, jejímž prostřednictvím bude možno ovlivňovat
     * zaznamenávání příkazů do žurnálu.
     *
     * @param gui Grafické rozhraní, pro něž nabídka pracuje
     */
    public MenuLog(IMyGUI gui)
    {
        super("Žurnál");
        setMnemonic('Z');
        this.gui = gui;

        commandDoLog();
        commandIntoWindow();
        commandIntoFile();
        commandNewFile();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o zadání příkazu, resp. o odstartování nové hry.
     *
     * @param command Zadaný příkaz nebo {@code null} oznamující
     *                zahájení nové hry
     */
    @Override
    public void notice(String command)
    {
        if (! toLog) {
            return;
        }
        if (command == null) {
            notice("_____________________________");
            notice("===== Spuštěna nová hra =====");
            reportAuthor();
            return;
        }
        TimeCommand record = new TimeCommand(command);
        commandList.add(record);
        String message = record.toString();
        logWindow.append(message);
        if (intoFile) {
            logIntoFile(message);
        }
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Zanese do žurnálu zprávu o tom, čí hra se právě hraje.
     */
    private void reportAuthor()
    {
        notice("      Autor: " + gui.getGame().getAuthorName());
    }


    /***************************************************************************
     * Nastaví, zda se bude zobrazovat aplikační okno
     * se zaznamenanými příkaz.
     */
    private void commandIntoWindow()
    {
        final JCheckBoxMenuItem logIntoWindowItem =
                        new JCheckBoxMenuItem("Zobrazovat v okně", false);
        logIntoWindowItem.setMnemonic('O');
        logIntoWindowItem.setState(intoWindow);
        this.add(logIntoWindowItem);

        logIntoWindowItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                intoWindow = ! intoWindow;
                if (intoWindow) {
                    logWindow.setVisible(true);
                }
            }
        });
    }


    /***************************************************************************
     * Nastaví zaznamenávání žurnálu do souboru.
     * Pokud ještě není nastaven žádný soubor, požádá uživatele o jeho zadání.
     */
    private void commandIntoFile()
    {
        JCheckBoxMenuItem logIntoFileItem =
                        new JCheckBoxMenuItem("Zapisovat do souboru", false);
        logIntoFileItem.setMnemonic('s');
        logIntoFileItem.setState(intoFile);
        this.add(logIntoFileItem);

        logIntoFileItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                intoFile = ! intoFile;
                if (intoFile) {
                    if (writer == null) {
                        prepareWriter();
                    }
                }
            }
        });
    }


    /***************************************************************************
     * Připraví povel nabídky, po jehož zadání se zavře případný současný
     * soubor, do nějž se zapisuje žurnál, a umožní uživateli zadat soubor nový.
     */
    private void commandNewFile()
    {
        JMenuItem newFileItem = new JMenuItem("Nový soubor");
        newFileItem.setMnemonic('N');
        this.add(newFileItem);

        newFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (writer != null) {
                    try {
                        writer.close();
                        writer = null;
                    } catch (IOException ex) {
                        message("\nProblémy při zavírání souboru " + file);
                    }
                }
                file = null;
                askForFile();
            }
        });
    }


    /***************************************************************************
     * Nastaví, zda se bude vytvářet žurnál, tj. zda se budou
     * zaznamenávat jednotlivé příkazy spolu s časem, kdy byly zadány.
     */
    private void commandDoLog()
    {
        final JCheckBoxMenuItem logCommandsItem =
                        new JCheckBoxMenuItem("Zaznamenávat příkazy", false);
        logCommandsItem.setMnemonic('Z');
        logCommandsItem.setState(toLog);
        this.add(logCommandsItem);

        logCommandsItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                boolean newItem = ! toLog;
                toLog = true;
                if (newItem) {
                    notice("+++++ Záznam příkazů zapnut +++++");
                    reportAuthor();
                }
                else {
                    reportAuthor();
                    notice("----- Záznam příkazů vypnut -----");
                }
                toLog = newItem;
            }
        });
    }


    /***************************************************************************
     * Pokusí se otevřít proud, do nějž se bude zapisovat žurnál;
     * pokud se mu to nepodaří, oznámí to uživateli
     * a zápis žurnálu do souboru vypne.
     */
    private void prepareWriter()
    {
        if (file == null) {
            askForFile();
            if (file == null) {
                intoFile = false;
                return;
            }
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            message("Nepodařilo se otevřít výstupní soubor\n\n" + file +
               "\n\nZápis žurnálu do souboru není nastaven");
            intoFile = false;
            writer = null;
        }
    }


    /***************************************************************************
     * Zaznamená zadanou událost do souboru.
     *
     * @param zpráva Zaznamenávaná zpráva
     */
    private void logIntoFile(String zpráva)
    {
        if (writer == null) {
            throw new RuntimeException("\nNení otevřen výstupní proud");
        }
        try {
            writer.write(zpráva);
            writer.flush();
        } catch (IOException ex) {
            message("Při zápisu žurnálu do souboru se vyskytly problémy");
        }
    }


    /***************************************************************************
     * Zjistí jméno souboru, do nějž se má ukládat žurnál.
     */
    private void askForFile()
    {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(gui.getParents());
        if (ret == JFileChooser.APPROVE_OPTION) {
            //Pokud uživatel něco zadal, zloží informaci do proměnné žSoubor
            file = fc.getSelectedFile();
        }
    }


    /***************************************************************************
     * Vypíše v dialogovém okně zprávu o problémech
     * a počká až uživatel potvrdí, že si ji přečetl.
     *
     * @param text Text zprávy
     */
    private void message(String text)
    {
        JOptionPane.showMessageDialog(gui.getParents(), text);
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Instance třídy {@code ČasPříkaz} slouží jako přepravky usnadňující
     * zapamatování a opětnovnou rekonstrukci průběhu hry.
     */
    private static class TimeCommand
    {
        final Date   time;      //Čas, kdy byl příkaz zadáýní
        final String command;   //Zadaný příkaz


        /***********************************************************************
         * Konstruktor vystaqčí pouze se zadáním příkazu,
         * protože za čas zadání příkazu považuje čas svého zavolání,
         * který si zjistí a uloží.
         *
         * @param command
         */
        TimeCommand(String command)
        {
            this.time    = new Date();
            this.command = command;
        }


        /***********************************************************************
         * Vrátí podpis dané položky sestávající z data a času
         * následovaného textem příkazu.
         *
         * @return Podpis
         */
        @Override
        public String toString()
        {
            String text = String.format("%tF - %<tT:  %s\n", time, command);
            return text;
        }


    }



//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        MenuLog inst = new MenuLog();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
