/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.scenario;



/*******************************************************************************
 * Instance výčtového typu {@code TypeOfScenario} představují možné typy
 * scénářů hry.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public enum TypeOfScenario
{
//== HODNOTY VÝČTOVÉHO TYPU ====================================================

    /** Scénář procházející možnou cestu vedoucí k dosažení cíle
     *  a obsahující současně informace pro otestování reakcí hry
     *  na zadávané akce. Tento druh scénáře musí každý správce nabízet.
     *  Tento scénář musí vyhovovat sadě požadavků, mezi něž patří např.
     *  <ul>
     *  <li>minimální počet kroků scénáře,</li>
     *  <li>minimální počet navštívených prostorů,</li>
     *  <li>minimální počet různých druhů příkazů,</li>
     *  <li>provedení základních povinných akcí, tj.
     *  <ul><li>přechodu mezi prostory,</li>
     *      <li>zvednutí objektu v aktuálním prostoru,</li>
     *      <li>položení objektu v aktuálního prostoru.</li>
     *  </ul>
     *  <li>úspěšné ukončení hry.</li>
     *  </ul>
     */                                                                 scHAPPY,

    /** Scénář sloužící k otestování reakcí hry na chybně zadané akce uživatele.
     *  Scénář musí obsahovat všechny druhy kroků, které jsou ve výčtovém typu
     *  {@code cz.vse.adv_framework.scenario.TypeOfStep}
     *  uvedeny jako povinné, tj. kroků, které mají podtyp
     *  {@code TypeOfStep.Subtype.stMISTAKE}.
     *  Mezi tyto kroky patří vedle kroků definujících reakci programu
     *  na chybně zadané příkazy hráče, také příkaz žádající nápovědu
     *  a příkaz pro explicitní ukončení hry.
     */                                                              scMISTAKES,

    /** Scénář, podle nějž je možno testovat chod hry,
     *  ale který nepatří do žádného z výše uvedených dvou povinných typů.
     *  Tento scénář nesmí obsahovat žádný demonstrační krok.
     */                                                               scGENERAL,

    /** Scénář sloužící pouze k demonstraci možné cesty
     *  a neumožňující testování chodu hry.
     *  Scénář smí obsahovat testovatelné kroky, avšak nesmí se podle nich
     *  testovat, protože mezi nimi mohou být i kroky čistě demonstrační.
     */                                                                  scDEMO;
}
