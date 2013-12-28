package cz.vse._3_0915.xmand12_mansurov;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

/*******************************************************************************
 * Instance tridy {@code CommandLoad} representuje
 * přikaz načítající již uloženou hru.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandLoad extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
    private final Scanner scanner = new Scanner(System.in);

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandLoad()
    {
        super("load", "Přikaz načítající již uloženou hru");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        GameData[] gdArr;
        File newDirectory = new File("saves/");
        String[] flArr = newDirectory.list();
        gdArr = new GameData[flArr.length];
        for (int i = 0; i < flArr.length; i++) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/" + flArr[i]))) {
                GameData data = (GameData) ois.readObject();
                gdArr[i] = data;
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder("Vyberte jeden ze souboru, a zadejte odpovidajici mu cislo:\n");
        for (int i = 0; i < flArr.length; i++) {
            sb.append(String.valueOf(i + 1));
            sb.append(" -> ");
            sb.append(flArr[i]);
            sb.append("\n");
        }
        sb.append(String.valueOf(0));
        sb.append(" -> ");
        sb.append("Zrušit čtení ze souboru!");
        sb.append("\n");

        while (true) {
            System.out.println(sb.toString());
            int number = scanner.nextInt();
            if ((number < 0) || (number > flArr.length)) {
                answer = "Zadal špatné číslo. Zkuste to znovu:";
            } else {
                if (number == 0) {
                    answer = "Čtení ze souboru bylo zrušeno!";
                    break;
                } else {
                    Game.loadGame(gdArr[number - 1]);
                    answer = "Čtení z souboru proběhlo uspešně. Můžete pokračovat v hře.";
                    break;
                }
            }
        }
        return answer;
    }
}
