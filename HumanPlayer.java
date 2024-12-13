import java.util.Scanner;
import java.util.ArrayList;

public class HumanPlayer extends Player
{
    public HumanPlayer(int id, String name){
        super(id,name);
    }
    
    public Action chooseAction(Board board){
        Game.display.out.println("Veuillez choisir une action: ");
        Game.display.out.println("Entrer 1 pour: Prendre deux jetons de la même ressource");
        Game.display.out.println("Entrer 2 pour: Prendre trois jetons de ressources différentes");
        Game.display.out.println("Entrer 3 pour: Acheter une carte développement");
        Game.display.out.println("Entrer 4 pour: Passer votre tour");
        Scanner scanner=new Scanner(Game.display.in);
        int choix=scanner.nextInt(); //Lecture entrée clavier
        Action result = null;
        
        //Choix que peut faire le joueur
        while (choix!=1 && choix!=2 && choix!=3 && choix!=4) {
            Game.display.out.println("Choisir un chiffre entre 1 et 4");
            choix=scanner.nextInt();
        }
        
        if (choix == 1) {
            Game.display.out.println("Veuiller entrer un type de ressource");
            String choixRessource = scanner.nextLine();
            while(choixRessource.equals("DIAMOND") && choixRessource.equals("SAPPHIRE") && choixRessource.equals("EMERALD") && choixRessource.equals("RUBY") && choixRessource.equals("ONYX") ){
                Game.display.out.println("Veuiller choisir un type de ressource");
                choix=scanner.nextInt();
            }
            result = new PickSameTokensAction(Resource.valueOf(choixRessource));
        }
           
        if(choix==2){
            ArrayList<Resource> choixResources = new ArrayList<Resource>();
            while (choixResources.size() < 4) { 
                Game.display.out.println("Veuiller entrer 3 types de ressource :");
                String choixRessource=scanner.nextLine();
                while(choixRessource.equals("DIAMOND") && choixRessource.equals("SAPHIRE") && choixRessource.equals("EMERALD") && choixRessource.equals("RUBY") && choixRessource.equals("ONYX") ){
                    Game.display.out.println("Veuiller choisir 3 types de ressource");
                }
                choixResources.add(Resource.valueOf(choixRessource));
            }
            result = new PickDiffTokensAction(choixResources);
        }
        
        if(choix==3){
            Game.display.out.println("Veuiller choisir une carte à acheter sur le plateau :");
            int positionX = scanner.nextInt(); 
            int positionY = scanner.nextInt();
            result = new BuyCardAction(board.getCard(positionX, positionY));
            
        }
        
        if(choix==4) {
            result = new PassAction();
        }
        return result;
    }
    
    public Resources chooseDiscardingTokens(){
        Scanner scanner=new Scanner(System.in);
        Resources tokensToDiscard = null;
        while (super.getNbTokens()> 10) { 
            Game.display.out.println("Veuiller choisir un jeton à retirer: ");
            String tokenToDiscard=scanner.nextLine();
            while(tokenToDiscard.equals("DIAMOND") && tokenToDiscard.equals("SAPHIRE") && tokenToDiscard.equals("EMERALD") && tokenToDiscard.equals("RUBY") && tokenToDiscard.equals("ONYX") ){
                Game.display.out.println("Veuiller choisir 3 types de ressource");
                tokensToDiscard.updateNbResource(Resource.valueOf(tokenToDiscard), 1);
            }
        }
        return tokensToDiscard;
    }
}
