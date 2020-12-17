package model;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public enum Card {
    CARD_1, CARD_2, CARD_3;
    public static String matchCard(List<Card> playerCards , int typical , Player player) {
        String toPrint = "";
        int numCard1 = 0;
        int numCard2 = 0;
        int numCard3 = 0;
        for (Card card : playerCards) {
            switch (card) {
                case CARD_1:
                    numCard1++;
                    break;
                case CARD_2:
                    numCard2++;
                    break;
                case CARD_3:
                    numCard3++;
                    break;
            }
        }
        if (numCard1 >= 3 && typical == 1) {
            Iterator<Card> it = playerCards.iterator();
            int checkNumber = 0;
            while (it.hasNext() && checkNumber < 3){
                Card checkCard = it.next();
                if(checkCard.equals(CARD_1)){
                    it.remove();
                    checkNumber++;
                }
            }
            player.addDraftSoldier(4);
            toPrint = "Accepted, 4 soldiers has been added";
        } else if (numCard2 >= 3 && typical == 2 ) {
            Iterator<Card> it = playerCards.iterator();
            int checkNumber = 0;
            while (it.hasNext() && checkNumber < 3){
                Card checkCard = it.next();
                if(checkCard.equals(CARD_2)){
                    it.remove();
                    checkNumber++;
                }
            }
            player.addDraftSoldier(6);
            toPrint = "Accepted, 6 soldiers has been added";
        } else if (numCard3 >= 3 && typical == 3) {
            Iterator<Card> it = playerCards.iterator();
            int checkNumber = 0;
            while (it.hasNext() && checkNumber < 3){
                Card checkCard = it.next();
                if(checkCard.equals(CARD_3)){
                    it.remove();
                    checkNumber++;
                }
            }
            player.addDraftSoldier(8);
            toPrint = "Accepted, 8 soldiers has been added";
        } else if (numCard1 >= 1 && numCard2 >= 1 && numCard3 >= 1 & typical == 4) {
            Iterator<Card> it = playerCards.iterator();
            int checkNumber1 = 0;
            int checkNumber2 = 0;
            int checkNumber3 = 0;
            while (it.hasNext() && (checkNumber1 == 0 || checkNumber2 == 2 || checkNumber3 == 3)){
                Card checkCard = it.next();
                if(checkCard.equals(CARD_1) && checkNumber1 == 0){
                    it.remove();
                    checkNumber1++;
                }
                if(checkCard.equals(CARD_2) && checkNumber2 == 0){
                    it.remove();
                    checkNumber2++;
                }
                if(checkCard.equals(CARD_3) && checkNumber3 == 0){
                    it.remove();
                    checkNumber3++;
                }
            }
            player.addDraftSoldier(10);
            toPrint = "Accepted, 10 soldiers has been added";
        }else{
            toPrint = "Declined, no soldiers has been added";
        }
        return toPrint;
    }
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
