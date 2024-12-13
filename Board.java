import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;
import java.util.Random;

public class Board implements Displayable {
    private DevCard[][] visibleCards;
    private ArrayList<Stack<DevCard>> stackCards;
    private Resources resourcesOnBoard;
    private ArrayList<DevCard> pileCartes;
    
    public Board(int nbPlayer) {
        //initialisation de la liste contenant les piles de cartes
        stackCards = new ArrayList<Stack<DevCard>>();
        
        //initialisation du tableau contenant les cartes visibles
        visibleCards = new DevCard[3][4];
         
        //initialisation du nombre de jetons en fonction du nombre de joueurs
        if (nbPlayer > 1 && nbPlayer < 5) {
            if (nbPlayer == 2) {
                resourcesOnBoard = new Resources(4, 4, 4, 4, 4);
            } else if (nbPlayer == 3) {
                resourcesOnBoard = new Resources(5, 5, 5, 5, 5);
            } else {
                resourcesOnBoard = new Resources(7, 7, 7, 7, 7);
            }
        }
        
        //lecture du fichier stat.csv
        pileCartes=new ArrayList<DevCard>();
        try {
        Scanner sc=new Scanner(new File("stats.csv"));
        String ligneFichier=sc.nextLine();
        Random rand=new Random();
        stackCards.add(new Stack<DevCard>());
        stackCards.add(new Stack<DevCard>());
        stackCards.add(new Stack<DevCard>());
        
        //mélange des cartes
        for (int i=0;i<98;i++) {
            ligneFichier=sc.nextLine();
            String infoCarte[]=ligneFichier.split(",");
            
            //on ignore les nobles dans cette implémentation
            if(!(infoCarte[7].equals("NOBLE"))) { 
                DevCard carte=new DevCard(Integer.parseInt(infoCarte[0]),Integer.parseInt(infoCarte[1]),Integer.parseInt(infoCarte[2]),Integer.parseInt(infoCarte[3]),Integer.parseInt(infoCarte[4]),Integer.parseInt(infoCarte[5]),Integer.parseInt(infoCarte[6]),Resource.valueOf(infoCarte[7]));
                pileCartes.add(carte);  
            }
        }
        
        //initialisation des trois paquets de cartes
        for (int i=0;i<98;i++) {
            int index=rand.nextInt(pileCartes.size());
            if(pileCartes.get(index).getNiveau()==1){
                stackCards.get(0).add(pileCartes.get(index));
            }
            if(pileCartes.get(index).getNiveau()==2){
                stackCards.get(1).add(pileCartes.get(index));
            }
            if(pileCartes.get(index).getNiveau()==3){
                stackCards.get(2).add(pileCartes.get(index));
            }          
        }
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        
        //initialisation des cartes visibles
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                visibleCards[i][j] = stackCards.get(i).pop();
            }
        }
    }
    public Resources getResourcesOnBoard(){
        return resourcesOnBoard;
    }
    /**
     * Renvoie le nombre de jetons d'une ressource donnée.
     */
    public int getNbResource(Resource resource) {
        return resourcesOnBoard.getNbResource(resource);
    }
    
    /**
     * Change le nombre de jetons d'une ressource donnée par un entier donné.
     */
    public void setNbResource(Resource resource, int value) {
        resourcesOnBoard.setNbResource(resource, value);
    }
    
    /**
     * Ajoute le nombre de jetons d'une ressource donnée par un entier donné.
     */
    public void updateNbResource(Resource resource, int value) {
        resourcesOnBoard.updateNbResource(resource, value);
    }
    
    /**
     * Renvoie les ressources dont le nombre de jetons est supérieur à zéro.
     */
    public ArrayList<Resource> getAvailableResources() {
        return resourcesOnBoard.getAvailableResources();
    }
    
    public DevCard getCard(int i, int j) {
        return visibleCards[i][j];
    }
    
    /**
     * Trouve la carte donnée en paramètre parmi les cartes visibles et la remplace par la carte du paquet.
     */
    public DevCard updateCard(DevCard card) {
        DevCard result = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (visibleCards[i][j].equals(card)) {
                    if (stackCards.get(card.getNiveau()).isEmpty()) {
                        visibleCards[i][j] = null;
                    } else if (!(stackCards.get(card.getNiveau()).isEmpty())) {
                        result = visibleCards[i][j];
                        visibleCards[i][j] = stackCards.get(card.getNiveau()).pop();
                    }
                }
            }
        }
        return result;
    }

    /**
     * Retire et renvoie la carte d'une pile de carte donnée.
     */
    public DevCard drawCard(Stack<DevCard> stack) {
        if (stack.isEmpty()) {
            return null;
        } else {
            return stack.pop();
        }
    }
    
    /**
     * Vérifie qu'il reste assez de jetons d'un type donné pour en retirer.
     */
    public boolean canGiveSameTokens(Resource resource) {
        return resourcesOnBoard.getNbResource(resource) <= 4;
    }
    
    /**
     * Vérifie qu'il reste assez de jetons pour plusieurs type donnés pour en retirer.
     */
    public boolean canGiveDiffTokens(ArrayList<Resource> resources) {
        boolean result = true;
        for (int i = 0; i < resources.size(); i++) {
            result = result && canGiveSameTokens(resources.get(i));
        }
        return result;
    }        
    
    /* --- Stringers --- */

    private String[] deckToStringArray(int tier){
        /** EXAMPLE
         * ┌────────┐
         * │        │╲ 
         * │ reste: │ │
         * │   16   │ │
         * │ cartes │ │
         * │ tier 3 │ │
         * │        │ │
         * └────────┘ │
         *  ╲________╲│
         */
        int nbCards = stackCards.get(tier-1).size();
        String[] deckStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  ",
                            "\u2502        \u2502\u2572 ",
                            "\u2502 reste: \u2502 \u2502",
                            "\u2502   "+String.format("%02d", nbCards)+"   \u2502 \u2502",
                            "\u2502 carte"+(nbCards>1 ? "s" : " ")+" \u2502 \u2502",
                            "\u2502 tier "+tier+" \u2502 \u2502",
                            "\u2502        \u2502 \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 \u2502",
                            " \u2572________\u2572\u2502"};
        return deckStr;
    }

    private String[] resourcesToStringArray(){
        /** EXAMPLE
         * Resources disponibles : 4♥R 4♣E 4♠S 4♦D 4●O
         */
        String[] resStr = {"Resources disponibles : "};

        for(Resource res: Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resStr[0] += resourcesOnBoard.getNbResource(res)+res.toSymbol()+" ";
        }
                 
        resStr[0] += "        ";
        return resStr;
    }

    private String[] boardToStringArray(){
        String[] res = Display.emptyStringArray(0, 0);

        //Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for(int i=stackCards.size();i>0;i--){
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        //Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for(int i = 0; i < 3; i++){ //-- parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for(int j = 0; j < 4; j++){ //-- parcourir les 4 cartes faces visibles pour un niveau donné (j)
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay, visibleCards[i][j]!=null ? visibleCards[i][j].toStringArray() : DevCard.noCardStringArray(), false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }
        
        res = Display.concatStringArray(deckDisplay, cardDisplay, false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 52), true);
        res = Display.concatStringArray(res, resourcesToStringArray(), true);
        res = Display.concatStringArray(res, Display.emptyStringArray(35, 1, " \u250A"), false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 54, "\u2509"), true);
        
        return res;
    }

    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
}
