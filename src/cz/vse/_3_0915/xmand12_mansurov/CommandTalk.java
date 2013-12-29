package cz.vse._3_0915.xmand12_mansurov;

/*******************************************************************************
 * Instance tridy {@code CommandJdi} representuje prikaz,
 * který umožňuje hráčovi vest dialog s herními postavámi
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class CommandTalk extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandTalk()
    {
        super("mluv", "Přikaz,který umožňuje hráčovi vest dialog s herními postavámi");
    }

//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer = "";
        if (arguments.length < 2) {
            answer = "Nebyl zadán objekt, na který se má aplikovat zadaná akce: " + arguments[0];
        } else {
            final Rooms room = Rooms.getCurrentPlace();
            final ListINamed<Things> roomObjects = room.getObjects();
            final Things person = roomObjects.getINamed(arguments[1]);
            if (person == null) {
                answer = "Taková osoba tady není!";
            } else {
                if (person.isAlive()) {
                    if (person.getName().equalsIgnoreCase("gubernátor")) {
                        answer = talkToGubernator();
                    } else if (person.getName().equalsIgnoreCase("prodavač")) {
                        answer = talkToSaler();
                    } else{
                        answer = "Popovídali jste si chvílku, ale nic zajímávého jste se nedozvěděl!";
                    }
                }
            }
        }
        return answer;
    }

   /**
     * Metoda vraci odpved prodavace v zavislosti na nakolika kriteiich:
     * jsetli mluvili s prodavacem
     * jestli mate penize
     * jestli uz kupili lod
     * @return odpoved prodavace
     */
    private String talkToSaler() {
        String answer;
        if (QuestManager.getStateOf("buyBoat")) {
            answer = "Prodavač už níc nemůže vám nabídout";
        } else {
            if (QuestManager.getStateOf("frstDlgWthSlr")) {
                answer = "Prodavač se vám usmal a zeptal se, jestli jste už nasbíral ty peníze";
            } else {
                QuestManager.setStateTo("frstDlgWthSlr", true);
                answer = "Zeptal jste prodavači, kolik stojí loď. Prodavač odpověděl,\n"
                        + "že loď stojí 10000 pesso.";
            }
            if (haveMoney()) {
                QuestManager.setStateTo("buyBoat", true);
                answer += " Koupil jste si loď.";
                for (Things object : Bag.getInstance().getObjects()) {
                    if (object.getName().equalsIgnoreCase("10000")) {
                        Rooms.getCurrentPlace().add(object);
                        Bag.getInstance().removeObject(object);
                        break;
                    }

                }
            } else {
                answer += " Odpověděl jste, že momentalně tolik peněz nemáte. A že se vratíte později";
            }
        }
        return answer;
    }

    private boolean haveMoney() {
        for (Things object : Bag.getInstance().getObjects()) {
            if (object.getName().equalsIgnoreCase("10000")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda vraci odpved gubernatora v zavislosti na nakolika kriteiich:
     * jsetli mluvili s gubernatorem
     * jestli prinesli dukazy
     * jestli vzali penize
     * @return odpoved gubernatora
     */
    private String talkToGubernator() {
        String answer = "";
        Things killer = null;
        Things money = null;
        if (QuestManager.getStateOf("frstDlgWthGbr")) {
            Rooms currRoom = Rooms.getCurrentPlace();
            for (Things object : currRoom.getObjects()) {
                if (object.getName().equalsIgnoreCase("vrah")) {
                    killer = object;
                }
                if (object.getName().equalsIgnoreCase("10000")) {
                    money = object;
                }
            }
            if ((money == null)) {
                answer = "Nemám na Vás čas.Přijděte později";
            } else if ((killer == null) && (money != null)) {
                answer = "Přiďte, když budete mít nějaké důkazy.";
            } else if ((killer != null) && (money != null)) {
                answer = "Gubernátor poděkoval vám a řekl,že vaše odměna na vás už"
                        + "\ndávno čeká na jeho stole, a že teď můžete ji vzít.";
            }
        } else {
            QuestManager.setStateTo("frstDlgWthGbr", true);
            answer = "Zeptal jste gubernátora, nemá-li nějaké zadání. Gubernátor řekl,\n"
                    + "že se ve džunglích vyskituje vrah, který terorizuje lidé.\n"
                    + " Rǐkáte, že zabil jste ho. Ale gubernátor tomu neveří.\n"
                    + "Ptá se po důkazech. Odpověděl jste, že nějaké dostanete.";
        }
        return answer;
    }
}