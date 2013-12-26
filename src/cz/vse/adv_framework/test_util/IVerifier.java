package cz.vse.adv_framework.test_util;
/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */



/*******************************************************************************
 * Instance rozhraní {@code IVerifier} prověří, zda testovaná třída
 * vyhovuje konkrétnímu požadavku.
 * Implementující třídy jsou většinou definovány jako interní
 * a slouží pouze jako obaly kolem vlastních verifikačních metod.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IVerifier
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Otestuje, zda testovaná třída vyhovuje konkrétnímu požadavku
     * a vrátí zprávu o výsledku testu.
     *
     * @return Zpráva o výsledku testu. Pokud test prošel,
     *         měla by začínat znaménkem <b>{@code '+'}</b>,
     *         pokud neprošel, měla by začínat znaménkem <b>{@code '-'}</b>
     */
    String verify();

//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
