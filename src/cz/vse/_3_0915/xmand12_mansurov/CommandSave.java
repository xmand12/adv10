
package cz.vse._3_0915.xmand12_mansurov;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/*******************************************************************************
 * Instance tridy {@code CommandSave} representuje prikaz
 * pro ulozeni aktualniho stavu hry.
 *
 * @author  Daulet MANSUROV
 * @version 0.00.0000 — 20yy-mm-dd
 * @date 27.12.2013
 */
public class CommandSave extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private final Scanner sc = new Scanner(System.in);

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandSave()
    {
        super("save","Prikaz pro ulozeni aktualniho stavu hry");
    }



//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        GameData gd = new GameData(QuestManager.getQmMap(), Bag.getInstance(), Rooms.getCurrentPlace());
        File newDirectory = new File("saves/");
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                System.out.println("Nová složka je vytvořena!");
            } else {
                System.out.println("Nepodářilo se vytvořit novou složku!");
            }
        }
        System.out.println("Zadejte jmeno souboru: ");
        while (true) {
        String name = sc.nextLine().split("\\s+")[0];
            if (verifyName(name)) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/" + name + ".save"))) {
                    oos.writeObject(gd);
                    answer = "Ulozeni hry probehlo uspesne!";
                } catch (IOException ex) {
                    answer = "Ulozeni hry se nepodarilo!";
                    ex.printStackTrace();
                }
                break;
            } else if(name.trim().equalsIgnoreCase("0")) {
                answer = "Uložení hry bylo zrušeno užívatelem!";
                break;
            }else {
                System.out.println("Ulozeni hry se nepodarilo!\n"
                        + "Zadal jste špatné jméno.Zkuste to ještě jednou nebo zadejte \"0\"");
            }
        }
        return answer;
    }

    public static boolean verifyName(String name) {
        String[] frbddnNm = {"con" ,  "nul", "prn" , "AUX" , "COM1", "COM2", "COM3",
                             "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1",
                             "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8",
                             "LPT9"};

        Pattern p = Pattern.compile("^[a-z0-9_-]{3,16}$");
        Matcher m = p.matcher(name);
        if (m.matches()) {
            for (int i = 0; i < frbddnNm.length; i++) {
                String asd = frbddnNm[i];
                if (name.equalsIgnoreCase(asd)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
