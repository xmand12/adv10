/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_framework.test_util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/*******************************************************************************
 * Instances of class {@code _Result_01} represent set of results
 * returned from a test method.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class _Result_01
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** List of item names. */
    private static final List<String> names = new ArrayList<>();

    /** List of item types. */
    private static final List<Class<?>> types = new ArrayList<>();



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Flag identifying, that first instance was constructed. */
    private static boolean itemsSealed = false;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        names.add("ID");            types.add(String.class);
        names.add("Name");          types.add(String.class);
        names.add("ShortMessage");  types.add(String.class);
        names.add("Score");         types.add(double.class);
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Map of values characterizing the test result. */
    private final Object[] values;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Add next item to the result set.
     * This method may not be called after first instance i constructed.
     */
    public static void addItem(String name, Class<?> type)
    {
        if (itemsSealed) {
            throw new IllegalStateException(
                "\nAdding an item after creation of an instance");
        }
        names.add(name);
        types.add(type);
    }


    /***************************************************************************
     * Returns string signature of the class constructed as
     * a list of allowed item names and types (their simple names).
     *
     * @return String signature of the class
     */
    public static String ToString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0;   i < names.size();   i++) {
            String name = names.get(i);
            String type = types.get(i).getSimpleName();
            sb.append('(').append(name).append(':').append(type).append("), ");
        }
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }




//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Creates a new test result.
     *
     * @param values Values of particular items in given order
     */
    public _Result_01(Object... values)
    {
        try {
            if (values.length != names.size()) {
                throw new IllegalArgumentException();
            }
            this.values = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                Object value = values[i];
                //TODO Dodělat kontrolu typů předávaných parametrů
//                if (! types.get(i).isAssignableFrom(value.getClass())) {
//                    System.out.println("Problems with: " + names.get(i));
//                    throw new IllegalArgumentException();
//                }
                this.values[i] = value;
            }
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "\nWrong constructor arguments - expected: " + names +
                "\nObtained: " + Arrays.toString(values));
        }
        itemsSealed = true;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Returns the value of item with the given name.
     *
     * @param  name Name of the asked value
     * @return Value of the item
     */
    public Object get(String name)
    {
        int index = names.indexOf(name);
        if (index < 0) {
            throw new IllegalArgumentException(
                "\nWrong name of item: " + name + "\nAllowed: " + names);
        }
        return values[index];
    }


    /***************************************************************************
     * Returns the value of item with the given name.
     *
     * @param  name Name of the asked value
     * @return Value of the item
     */
    public String getString(String name)
    {
        Object value = get(name);
        if (value instanceof String) {
            return (String)value;
        }
        throw new IllegalArgumentException(
            "\nThe item: " + name + " is not a String");
    }


    /***************************************************************************
     * Returns the value of item with the given name.
     *
     * @param  name Name of the asked value
     * @return Value of the item
     */
    public double getDouble(String name)
    {
        Object value  = get(name);
        String string = value.toString();
        double result;
        try {
            result = Double.parseDouble(string);
        }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(
                "\nThe item: " + name + " is not a double -- it is a " +
                value.getClass());
        }
        return result;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        _Result_01 inst = new _Result_01();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
