package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.Stuff;
import org.stuff.cards.Minion;

public class BatterHead extends Minion {
    public BatterHead() {
        super("BatterHead", 23, 8, 3, 12, "rush,after killing a minion it may attack again", "All", "images\\BatterHead.png", 4);
        this.rush=1;
    }

    @Override
    public Card getClone() {
        return new BatterHead();
    }
    //Card batterhead = new Card("BatterHead", 23, 8, 3, 12, "after killing may attack", "after killing a minion it may attack again", "All", "images\\batterhead.png", 4);
    //finish


    @Override
    public void attack(Stuff enemyStuff) {
        super.attack(enemyStuff);
        if(enemyStuff.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
            Minion enemyMinion=(Minion) enemyStuff;
            if(enemyMinion.getHealth()<=0&&Math.random()>=0.5)
                this.rush+=1;
        }
    }
}
