import java.util.*;

public class Game{

    //for special cards, make switch with each player and the number of cards left
        //extend card

    public static void main(String[] args){
        //deck has cards within it, discard is empty
        Deck deck = new Deck();
        Discard discard = new Discard();
        Discard hand = new Discard();

        //player set up
        Scanner scan = new Scanner(System.in);
        System.out.print("How many players? ");
        int num_players = scan.nextInt();
        while(num_players<2 || num_players>6){
            System.out.print("How many players? ");
            num_players = scan.nextInt();
        }
        ArrayList<Player> players = new ArrayList<Player>(); //list made up of player objects


        scan.nextLine();

        for(int i=0; i<num_players; i++){
            System.out.print("Enter name for player: ");
            String name = scan.next();

            for(int j=0; j<7; j++){ //adds first seven cards in the deck to player's hand and pops those cards from main deck
                hand.getDiscard_pile().add(deck.getDeck().peek());
                deck.getDeck().pop();
                deck.size--;
                hand.size++;
            }

            players.add(new Player(name, hand)); //makes a new player
            hand = new Discard(); //makes a new hand
        }

        //adds top card of the deck into the discard/playing pile
        discard.getDiscard_pile().add(deck.getDeck().peek());
        discard.size++;
        deck.getDeck().pop();
        deck.size--;

        if(discard.getDiscard_pile().peek().getSpecial() != null){
            discard.getDiscard_pile().add(deck.getDeck().peek());
            discard.size++;
            deck.getDeck().pop();
            deck.size--;
        }


        System.out.println();


        print_directions();


        while(!check_win(players)){//checks condition to see if the players have won.
            for(int i=0; i<num_players; i++) { //circles through players
                Player current_player = players.get(i);

                if(deck.getDeck().empty()){ //makes sure the main deck isn't empty (CHECK THIS)
                    System.out.println("Deck resetting");
                    Collections.shuffle(discard.discard_pile);
                    deck.deck_of_cards = discard.discard_pile;
                    discard.getDiscard_pile().clear();
                }

                if(check_win(players)){
                    break;
                }
                //prints Player's name and their hand
                System.out.println("Player: " + current_player.getName() + "\n" + current_player.hand.getDiscard_pile());
                System.out.println();

                //prints top card of discard pile
                Card current_card = discard.getDiscard_pile().peek();
                if(current_card.getSpecial() == "Wildcard" || current_card.getSpecial() == "Wildcard Plus 4"){
                    System.out.println("Top: " + current_card.getColor());
                }
                else{
                    System.out.println("Top: " + current_card);
                }

                Card choice = play_a_card(scan, players.get(i)); //player's choice of what card to play
                String ruled = rule(choice, current_card); //checks the move against the rules

                while (ruled == "false") { //if the move was invalid, make the player enter in another choice
                    choice = play_a_card(scan, current_player);
                    ruled = rule(choice, current_card);
                }

                switch(ruled){
                    case "Pick one":
                        current_player.hand.getDiscard_pile().add(deck.getDeck().peek()); //adds top card of deck to player's hand
                        deck.getDeck().pop(); //and pops it
                        deck.size--;
                        players.get(i).hand.size++;
                        break;
/*
                    case "Skip":
                        break;

                if(i == num_players-1){
                    //current player would skip player.get(0);

                }

                 */
                    case "Wildcard":
                        String color = wildcard(scan);
                        System.out.println("The color is now " + color);
                        choice.setColor(color);
                        discard.getDiscard_pile().add(choice); //adds the card to the discard pile
                        current_player.hand.getDiscard_pile().remove(choice); //removes that card from the player's hand
                        discard.size++;
                        current_player.hand.size--;

                        if(choice.getSpecial() == "Wildcard Plus 4"){
                            Player person;
                            if(num_players == 2){
                                if(players.get(0) == current_player){
                                    person = players.get(1);
                                }
                                else{
                                    person = players.get(0);
                                }
                            }
                            else{
                                person = pick_two(scan, players, current_player);
                                while(person == null){
                                    person = pick_two(scan, players, current_player);
                                }
                            }

                            for(int j=0; j<4; j++){
                                person.hand.getDiscard_pile().add(deck.getDeck().peek()); //adds top card of deck to player's hand
                                deck.getDeck().pop(); //and pops it
                                deck.size--;
                                person.hand.size++;
                            }
                            System.out.println("Four Cards have been given to " + person.getName());
                        }

                        break;

                    case "Good":
                        if(choice.getSpecial() == "Pick 2"){
                            Player person;
                            if(num_players == 2){
                                if(players.get(0) == current_player){
                                    person = players.get(1);
                                }
                                else{
                                    person = players.get(0);
                                }
                            }
                            else{
                                person = pick_two(scan, players, current_player);
                                while(person == null){
                                    person = pick_two(scan, players, current_player);
                                }
                            }

                            for(int j=0; j<2; j++){
                                person.hand.getDiscard_pile().add(deck.getDeck().peek()); //adds top card of deck to player's hand
                                deck.getDeck().pop(); //and pops it
                                deck.size--;
                                person.hand.size++;
                            }
                            System.out.println("Two Cards have been given to " + person.getName());

                        }
                        else{
                            discard.getDiscard_pile().add(choice); //adds the card to the discard pile
                            current_player.hand.getDiscard_pile().remove(choice); //removes that card from the player's hand
                            discard.size++;
                            current_player.hand.size--;
                            break;
                        }
                    default:
                        break;
                }

                System.out.println();


            }
        }


    }

    public static void print_directions(){ //prints directions
        System.out.println("Play a card by inputting the index of the card.");
        System.out.println("Index starts at 0");
        System.out.println("If you don't have a card that works type 100");
        System.out.println();
    }

    public static Player pick_two(Scanner scan, ArrayList<Player> players, Player current){
        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.hand.size + " cards left");
        }
        System.out.println("Pick a person to give two cards to by typing in the index.");
        System.out.println("Index starts at 0");
        System.out.print("Enter choice: ");
        int person_chosen = scan.nextInt();

        try{
            if(players.get(person_chosen).getName() == current.getName()){
                System.out.println("You can't cards to yourself");
                return null;
            }
            return players.get(person_chosen); //returns the index chosen
        }
        catch(Exception e){ //ArrayOutofBounds exception
            return null;
        }
    }

    public static String wildcard(Scanner scan){
        System.out.println("1. Red\n2. Blue\n3. Yellow\n4. Green");
        System.out.print("Enter choice: ");
        int color = scan.nextInt();
        switch(color){
            case 1:
                return "Red";
            case 2:
                return "Blue";
            case 3:
                return "Yellow";
            case 4:
                return "Green";
            default:
                break;
        }
       return null;
    }

    public static boolean check_win(ArrayList<Player> players){ //checks if anyone has won at the end of each round
        for(int i=0; i<players.size(); i++){
            if(players.get(i).hand.size==0){
                System.out.println("Congrats " + players.get(i).getName() + "!");
                return true;
            }
        }
        return false;
    }

    public static String rule(Card TBC, Card current){ //checks move against rules of the game
        if(TBC == null){ //if the move was invalid because of ArrayOutofBounds exception
            return "false";
        }
        if(TBC.getSpecial() == "Wildcard" || TBC.getSpecial() == "Wildcard Plus 4"){
            return "Wildcard";
        }
        else if(TBC.getSpecial() == "Skip"){
            return "Skip";
        }
        else if(TBC.getNumber() == current.getNumber() || TBC.getColor() == current.getColor()
               // current.getSpecial() == "Wildcard" || current.getSpecial() == "Wildcard Plus 4" ||
                ){ //if the move is valid
            return "Good";
        }
        else if(TBC.getNumber() == "100"){ //if the user does not have a move
            return "Pick one";
        }
        return "false";
    }


    public static Card play_a_card(Scanner scan, Player current){ //allows user to make a choice about their move
        System.out.print("Enter Choice: ");
        int card_chosen = scan.nextInt();

        Card pick_one = new Card("100", null, null); //card that allows for the user to pick a card

        try{
            if(card_chosen == 100){ //if the user does not have a move
                return pick_one;
            }
            else{
                return current.hand.getDiscard_pile().get(card_chosen); //returns the index chosen
            }
        }
        catch(Exception e){ //ArrayOutofBounds exception
            return null;
        }

    }



}
