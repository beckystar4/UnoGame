import java.util.*;

public class Player {
    String name;
    Discard hand;


    public Player(String name, Discard hand){
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }


    public Discard getHand() {
        return hand;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setHand(Discard hand) {
        this.hand = hand;
    }

    //hand
    //number of cards left
}
