import java.util.ArrayList;
public abstract class Player implements Displayable {
    private int id;
    private String name;
    private int points;
    private ArrayList<DevCard> purchasedCards;
    private Resources resources;
    
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        purchasedCards = new ArrayList<DevCard>();
        points = 0;
        resources = new Resources(0,0,0,0,0);
    }
    /* --- Stringers --- */
    public String[] toStringArray(){
        /** EXAMPLE. The number of resource tokens is shown in brackets (), and the number of cards purchased from that resource in square brackets [].
         * Player 1: Camille
         * ⓪pts
         * 
         * ♥R (0) [0]
         * ●O (0) [0]
         * ♣E (0) [0]
         * ♠S (0) [0]
         * ♦D (0) [0]
         */
        String pointStr = " ";
        String[] strPlayer = new String[8];
         
            /* A decommenter une fois la classe implémentée */
        if(points>0){
            pointStr = new String(new int[] {getPoints()+9311}, 0, 1);
        }else{
            pointStr = "\u24EA";
        }

        
        strPlayer[0] = "Player "+(id+1)+": "+name;
        strPlayer[1] = pointStr + "pts";
        strPlayer[2] = "";
        for(Resource res:Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            strPlayer[3+(Resource.values().length-1-res.ordinal())] = res.toSymbol() + " ("+resources.getNbResource(res)+") ["+getResFromCards(res)+"]";
        }
        
        return strPlayer;
    }

    //Renvoie le nom du joueur
    public String getName(){
        return name;
    }

    //Renvoie les points du joueur
    public int getPoints(){
        return points;
    }

    //Renvoie le nombre total de jetons que le joueur possède
    public int getNbTokens(){
        int res = 0;
        res += resources.getNbResource(Resource.DIAMOND);
        res += resources.getNbResource(Resource.SAPPHIRE);
        res += resources.getNbResource(Resource.EMERALD);
        res += resources.getNbResource(Resource.ONYX);
        res += resources.getNbResource(Resource.RUBY);  
        return res;
        
    }

    //Renvoie le nombre de cartes achetées par le joueur
    public int getNbPurchasedCards(){
        return purchasedCards.size();
    }

    //Renvoie le nombre de ressources achetées pour un type donné
    public int getNbResource(Resource resource){
        return resources.getNbResource(resource);
    }

    //Renvoie la liste des ressources disponibles
    public Resources getAvailableResources(){
        return resources;
    }

   //Renvoie les ressources bonus
    public Resources getResourceBonus() {
        Resources resourcesBonus = new Resources();
        for (DevCard card : purchasedCards) {
            resourcesBonus.updateNbResource(card.getResourceType(), 1);
        }
        return resourcesBonus;
    }


    //Renvoie le nombre de ressources d’un type donné présents sur les cartes achetées
    public int getResFromCards(Resource type){
        int res = 0;
        for(int i = 0; i < purchasedCards.size(); i++){
            DevCard card = purchasedCards.get(i);
            Resource cardType = card.getResourceType();
            if (type == cardType) {
                 res ++;
            }
        }
        return res;
    }

    //Prend en paramètre un type de ressource et une quantité (v), et qui ajoute (v>0) ou supprime (v<0) cette quantité à la ressource correspondante. Le nombre de ressources disponibles pour chaque type ne pourra pas être inférieur à 0.
    public void updateNbResource(Resource type, int i){
        resources.updateNbResource(type, i);
    }

    //Incrémente le nombre de points prestige acquis par le joueur.
    public void updatePoints(int v){
        points += v;
    }

    //Permet d’ajouter une carte donnée à la liste des cartes achetées par le joueur.
    public void addPurchasedCard(DevCard card){
        purchasedCards.add(card);
    }

    //Vérifie si le joueur a assez de ressources pour acheter une carte donnée
    public Boolean canBuyCard(DevCard card){
        Resources resourcesBonus = getResourceBonus();
        if(card.getCost().getNbResource(Resource.DIAMOND) - resourcesBonus.getNbResource(Resource.DIAMOND) <= resources.getNbResource(Resource.DIAMOND) &&
        card.getCost().getNbResource(Resource.SAPPHIRE) - resourcesBonus.getNbResource(Resource.SAPPHIRE) <= resources.getNbResource(Resource.SAPPHIRE) &&
        card.getCost().getNbResource(Resource.EMERALD) - resourcesBonus.getNbResource(Resource.EMERALD) <= resources.getNbResource(Resource.EMERALD) &&
        card.getCost().getNbResource(Resource.ONYX) - resourcesBonus.getNbResource(Resource.ONYX) <= resources.getNbResource(Resource.ONYX) &&
        card.getCost().getNbResource(Resource.RUBY) - resourcesBonus.getNbResource(Resource.RUBY) <= resources.getNbResource(Resource.RUBY)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public abstract Action chooseAction(Board board);
    public abstract Resources chooseDiscardingTokens();
}
