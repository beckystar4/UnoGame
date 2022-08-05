import java.util.*;


public class Deck {

    Stack<Card> deck_of_cards = new Stack<>();

    int size=0;
    String[] number = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String[] color = {"Red", "Blue", "Green", "Yellow"};
    String special = "Pick 2";
    String skip = "Skip";
    String wild = "Wildcard";
    String wildcard4 = "Wildcard Plus 4";


    public Deck(){
        for(int i=0; i<color.length; i++){
            this.deck_of_cards.push(new Card(null, null, wild));
            this.deck_of_cards.push(new Card(null, null, wildcard4));
            this.deck_of_cards.push(new Card(null, color[i], special));
            this.deck_of_cards.push(new Card(null, color[i], special));
            //this.deck_of_cards.push(new Card(null, color[i], skip));
            //this.deck_of_cards.push(new Card(null, color[i], skip));
            size+=4;
            for(int j=0; j<number.length; j++){
                this.deck_of_cards.push(new Card(number[j], color[i], null));
                this.deck_of_cards.push(new Card(number[j], color[i], null));
                size+=2;
            }
        }
        Collections.shuffle(this.deck_of_cards);
    }

    public Stack<Card> getDeck(){
        return deck_of_cards;
    }
}