package model;

import java.util.List;

enum Card {
    CARD_1, CARD_2, CARD_3;
    public static int matchCard(List<Card> playerCards){
        int numCard1 = 0;
        int numCard2 = 0;
        int numCard3 = 0;
        for(Card card : playerCards){
            switch (card){
                case CARD_1:numCard1++;break;
                case CARD_2:numCard2++;break;
                case CARD_3:numCard3++;break;
            }
            if(numCard1 == 3){
                return 4;
            }
            else if(numCard2 == 3){
                return 6;
            }
            else if(numCard3 == 3){
                return 8;
            }
            else if (numCard1 >= 1 && numCard2 >= 1 && numCard3 >= 1){
                return 10;
            }
        }
        return 0;
    }
}
