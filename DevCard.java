public class DevCard implements Displayable {
    private int niveau;
    private Resources cost;
    private int points;
    private Resource resourceType;
    
    public DevCard(int niv, int coutDIAMOND, int coutSAPPHIRE, int coutEMERALD, int coutRUBY, int outONYX, int point, Resource type){
        cost = new Resources(coutDIAMOND,coutSAPPHIRE,coutEMERALD,coutRUBY,outONYX);
        niveau = niv;
        this.points = point;
        resourceType = type;
    }
    
    /**
     * Renvoie le niveau (tier) de la carte.
     */
    public int getNiveau(){
        return niveau;
    }

    /**
     * Renvoie le cout de la carte (un objet Resource)
     */
    public Resources getCost(){
        return cost;
    }
    /**
     * Renvoie les points que rapporte la carte.
     */
    public int getPoints(){
        return points;
    }

    /**
     * Renvoie type de la carte (la ressource 
     */
    public Resource getResourceType(){
        return resourceType;
    }
    
    
    public String[] toStringArray(){
        /** EXAMPLE
         * ┌────────┐
         * │①    ♠S│
         * │        │
         * │        │
         * │2 ♠S    │
         * │2 ♣E    │
         * │3 ♥R    │
         * └────────┘
         */
        String pointStr = "  ";
        
        if(getPoints()>0){
            pointStr = new String(new int[] {getPoints()+9311}, 0, 1);
        }
        String[] cardStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                            "\u2502"+pointStr+"    "+resourceType.toSymbol()+"\u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        //update cost of the repr
        int i = 6;
             
        for(Resource res:Resource.values()){ //-- parcourir l'ensemble des resources (res)en utilisant l'énumération Resource
            if(getCost().getNbResource(res)>0){
                cardStr[i] = "\u2502"+getCost().getNbResource(res)+" "+res.toSymbol()+"    \u2502";
                i--;
            }
        } 
        return cardStr;
    }

    public static String[] noCardStringArray(){
        /** EXAMPLE
         * ┌────────┐
         * │ \    / │
         * │  \  /  │
         * │   \/   │
         * │   /\   │
         * │  /  \  │
         * │ /    \ │
         * └────────┘
         */
        String[] cardStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                            "\u2502 \\    / \u2502",
                            "\u2502  \\  /  \u2502",
                            "\u2502   \\/   \u2502",
                            "\u2502   /\\   \u2502",
                            "\u2502  /  \\  \u2502",
                            "\u2502 /    \\ \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        
        return cardStr;
    }

    public String toString(){
        String cardStr = "";
            
        cardStr = getPoints()+"pts, type "+resourceType.toSymbol()+" | coût: ";
        for(Resource res:Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            if(getCost().getNbResource(res)>0){
                cardStr += getCost().getNbResource(res)+res.toSymbol()+" ";
            }
        }
        
        return cardStr;
    }
}
