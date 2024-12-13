
/**
 * Décrivez votre interface Action ici.
 *
 * @author  (votre nom)
 * @version (un numéro de version ou une date)
 */

public interface Action
{
    void process(Player joueur,Board plateau);
    
    String toString();
}
