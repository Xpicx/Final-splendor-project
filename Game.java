/*
 * @author    Corentin Dufourg
 * @version     1.1
 * @since       1.0
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    /* L'affichage et la lecture d'entrée avec l'interface de jeu se fera entièrement via l'attribut display de la classe Game.
     * Celui-ci est rendu visible à toutes les autres classes par souci de simplicité.
     * L'intéraction avec la classe Display est très similaire à celle que vous auriez avec la classe System :
     *    - affichage de l'état du jeu (méthodes fournies): Game.display.outBoard.println("Nombre de joueurs: 2");
     *    - affichage de messages à l'utilisateur: Game.display.out.println("Bienvenue sur Splendor ! Quel est ton nom?");
     *    - demande d'entrée utilisateur: new Scanner(Game.display.in);
     */
    private static final int ROWS_BOARD=36, ROWS_CONSOLE=8, COLS=82;
    public static final  Display display = new Display(ROWS_BOARD, ROWS_CONSOLE, COLS);

    private Board board;
    private List<Player> players;

    public static void main(String[] args) {
        //-- à modifier pour permettre plusieurs scénarios de jeu
        display.outBoard.println("Bienvenue sur Splendor !");
        Game game = new Game(2);
        game.play();
        //display.close();
    }

    public Game(int nbOfPlayers) throws IllegalArgumentException{
        board = new Board(nbOfPlayers);
        players = new ArrayList<Player>();
        
        if (nbOfPlayers<2 || nbOfPlayers>4){
            throw new IllegalArgumentException("Le nombre de joueur doit être entre 2 et 4");
        }
        Game.display.out.println("Entrer votre nom");
        Scanner scanner =new Scanner(Game.display.in);
        String name =scanner.nextLine();
        HumanPlayer human=new HumanPlayer(1,name);
        players.add(human);
        //Créer un nombre n de robot en fonction du nombre de joueur
        for (int i = 0; i < nbOfPlayers-1; i++){
            DumbRobotPlayer robot=new DumbRobotPlayer(i,"Robot"+i); 
            players.add(robot);
        }
    
        play();
    }

    public int getNbPlayers(){
        return players.size();
    }

    private void display(int currentPlayer){
        String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for(int i=0;i<players.size();i++){
            String[] pArr = players.get(i).toStringArray();
            if(i==currentPlayer){
                pArr[0] = "\u27A4 " + pArr[0];
            }
            playerDisplay = Display.concatStringArray(playerDisplay, pArr, true);
            playerDisplay = Display.concatStringArray(playerDisplay, Display.emptyStringArray(1, COLS-54, "\u2509"), true);
        }
        String[] mainDisplay = Display.concatStringArray(boardDisplay, playerDisplay, false);

        display.outBoard.clean();
        display.outBoard.println(String.join("\n", mainDisplay));
    }

    public void play(){
        while(!isGameOver()){
            for (Player player : players) {
                display(4);
                move(player);
                if (player.getNbTokens() > 10) {
                    discardToken(player);
                }
            }
        }
        gameOver();
    }

    private void move(Player player){
        Action action=player.chooseAction(board);
        action.process(player,board);
    }

    private void discardToken(Player player){
        Resources r=player.chooseDiscardingTokens();
        DiscardTokensAction discard=new DiscardTokensAction(r);
        discard.process(player,board);
    }

    public boolean isGameOver(){
        for(int i=0; i<players.size();i++){
            if(players.get(i).getPoints()>=15){
                return true;
            }
        }
        return false;
    }

    private void gameOver(){
        Game.display.out.println("Bravo!");
        ArrayList<Player> gagnants=new ArrayList<Player>();
        for(int i=0; i<players.size();i++){
            if(players.get(i).getPoints()>=15){
                gagnants.add(players.get(i));
            }
        }
        if(gagnants.size()==1){
            Game.display.out.println(gagnants.get(0));
        }else{
            String gagnant="";
            for(int i=1; i<gagnants.size();i++){
                if(gagnants.get(i).getNbPurchasedCards()<gagnants.get(i-1).getNbPurchasedCards()){
                    gagnant=gagnants.get(i).getName();
                }else{
                    gagnant=gagnants.get(i-1).getName();
                }
            }
            Game.display.out.println(gagnant);
        }

    }
}
