/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.IAuthor;

import java.text.Normalizer;




/*******************************************************************************
 * Instance třídy {@code AuthorTest} slouží k otestování
 * správné implementace rozhraní {@code IAuthor}.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
class AuthorTest extends ATest implements ITest<IAuthor>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Ověří, zda zadaný objekt typu {@link IAuthor} vyhovuje
     * příslušnému kontraktu, tj. zda:
     * <ul>
     * <li>xname je psán velkými písmeny,</li>
     * <li>jméno začíná příjmením (shodují se první tři písmena s xname),</li>
     * <li>příjmení je psáno velkými písmeny</li>
     * <li>křestní začíná znakem uvedeným v xname,</li>
     * <li>křestní začíná velkým a pokračuje malými.</li>
     *
     * @param author Testovaný objekt
     * @return Zpráva o výsledku testu
     */
    public static String verifyContract(IAuthor author)
    {
        AuthorTest authorTest = new AuthorTest();
        try {
            String[] xname = authorTest.splitXName(author);
            authorTest.verifyAuthor(author, xname);
        } catch (TestException te) {
            return "- Jméno autora a/nebo jeho xname neodpovídají kontraktu";
        } catch (Exception ex) {
            throw new RuntimeException("\nNeočekávaná výjimka", ex);
        }
        return "+ Jméno autora i jeho xname pravděpodobně odpovídají kontraktu";
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    AuthorTest()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Ověří, zda zadaný objekt typu {@link IAuthor} vyhovuje
     * příslušnému kontraktu, tj. zda:
     * <ul>
     * <li>xname je psán velými písmeny,</li>
     * <li>jméno začíná příjmením (shodují se první tři písmena s xname),</li>
     * <li>příjmení je psáno velkými písmeny</li>
     * <li>křestní začíná znakem uvedeným v x.name,</li>
     * <li>křestní začíná velkým a pokračuje malými.</li>
     *
     * @param author Testovaný objekt
     */
    @Override
    public void test(IAuthor author)
    {
        try {
            String[] xname = splitXName(author);
            verifyAuthor(author, xname);
        } catch (TestException te) {
            //Vyjimku vyhazujeme pouze pro snazší zpracování
            //Vše už ale bylo řečeno metodou ERROR_T
            return;
        } catch (Exception ex) {
            throw new RuntimeException(
                    "\nNeočekávaná výjimka", ex);
        }
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

        /***********************************************************************
         * Ověří, že jméno autora zadaného objektu odpovídá kontraktu.
         *
         * @param author Objekt, jahož jméno autora testujeme
         * @param xname  Pole s jednotlivými částmi xname objektu
         */
        @SuppressWarnings("empty-statement")
        private void verifyAuthor(IAuthor author, String[] xname)
        {
            String[] name = splitName(author);
            //Následují dvě možné podoby zápisu ekvivalentího kódu
            //Nechte odkomentovanou tu, která vám více vyhovuje
            int characters = 3;
            while (xname[0].charAt(--characters) == '_');    //empty-statement

//            int characters = 2;
//            while (xname[0].charAt(characters) == '_') {
//                characters --;
//            }

            if (! xname[0].substring(0, characters+1).equals(
                   name[0].substring(0, characters+1)))
            {
                ERROR_T("První znaky příjmení nesouhlasí s xname. Příjmení: " +
                        name[0] + ",  xname: " + xname);
            }
        }


        /***********************************************************************
         * Vytvoří pole textů obsahující příjmení a jméno autora zadaného
         * objektu a přitom ověří, že příjmení je psané velkými písmeny
         * a křestní jméno začíná velkým písmenem a ostatní písmena jsou malá.
         *
         * @param author Objekt, jehož jméno autora se rozděluje a testuje
         * @return Pole s částmi jména
         */
        private String[] splitName(IAuthor author)
        {
            //Jméno zbavíme diakritiky pro snazší následnou kontrolu
            String name      = author.getAuthorName();
            String asciiName = Normalizer.normalize(name, Normalizer.Form.NFD).
                                         replaceAll("[^\\p{ASCII}]", "");

            //Rozdělíme je na jednotlivá slova
            String[] nameWords = asciiName.split(" ");
            String[] check     = asciiName.split("\\s+");
            if (nameWords.length != check.length) {
                ERROR("Špatně použité bílé znaky ve jméně autora.");
                return null;
            }

            //Ověříme, že jméno dodržuje konvence
            if ((nameWords   .length    < 2)  ||
                (nameWords[0].length() == 0)  ||
                (nameWords[1].length() == 0))
            {
                ERROR("Autor nemá uvedeno příjmení + křestní jméno");
                return null;
            }
            String surname   = nameWords[0];
            String firstName = nameWords[1];
            if (! surname.matches("[A-Z]+")) {
                ERROR("První slovo jména autora není zapsané velkými písmeny");
                return null;
            }
            if (! firstName.matches("[A-Z][a-z]+")) {
                ERROR("Druhé slovo jména autora nemá " +
                      "první písmeno velké a ostatní malá");
                return null;
            }
            for (int i=2;   i < nameWords.length;   i++) {
                if (! nameWords[i].matches("[A-Z][a-z]+")) {
                    ERROR("Další ze slov jména autora nemá " +
                          "první písmeno velké a ostatní malá");
                    return null;
                }
            }
            return nameWords;
        }


        /***********************************************************************
         * Vytvoří pole textů s jednotlivými částmi xname zadaného objektu
         * a při té příležitosti zkontroluje formální správnost tohoto xname.
         *
         * @param author Objekt, jehož xname se rozděluje a testuje
         * @return Pole textů s jednotlivými částmi xname zadaného objektu
         * @throws TestException Xname formálně nevyhovuje
         */
        private String[] splitXName(IAuthor author)
        {
            String[] array = new String[3];
            try {
                String xname = author.getAuthorID();
                if (xname.charAt(0) != 'X') {
                    ERROR_T("První znak vráceného Xname není velké X: " +
                            xname);
                }
                String surname3 = xname.substring(1, 4);
                String pattern3 = "[A-Z]+_*";
                if (! surname3.matches(pattern3)) {
                    ERROR_T("Příjmení v xname objektu nevyhovuje konvencím: " +
                            xname);
                }
                char firstName = xname.charAt(4);
                if ((firstName < 'A')  ||  ('Z' < firstName))  {
                    ERROR_T("Iniciála křestního jména v xname objektu " +
                            "není zadána jako velké písmeno: " + xname);
                }
                String number  = xname.substring(5);
                String pattern = "\\d{2,3}";
                if (! number.matches(pattern)) {
                    ERROR_T("Xname objektu nekončí dvěmi nebo třemi " +
                            "číslicemi: "+ xname);
                }

                array[0] = surname3;
                array[1] = Character.toString(firstName);
                array[2] = number;
            } catch(Exception exception) {
                ERROR_T("Xname zadaného objektu nevyhovuje konvencím: " +
                        author + "\nByla vyhozena výjimka ",
                        exception.toString());
            }
            return array;
        }

//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        AuthorTest inst = new AuthorTest();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
