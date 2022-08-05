public class Card {
    String number;
    String color;
    String special;

    public Card(String number, String color, String special){
        this.number = number;
        this.color = color;
        this.special = special;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public String getSpecial() {
        return special;
    }

    public String toString() {
        if(special != null){
            if(special == "Wildcard"){
                return "WILDCARD";
            }
            else if(special == "Wildcard Plus 4"){
                return "WILDCARD+4";
            }
            else if(special == "Skip"){
                return "Skip";
            }
            return color + " " + special;
        }
        return number + " " + color;
    }
}
