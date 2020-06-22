package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.stuff.cards.Weapon;

public class BattleWeaponFunctions {

    public static void weaponOnHero(Battle battle, Weapon weapon) {
        battle.whoseTurn().hero.defence+=weapon.getDefence();
        battle.whoseTurn().hero.damage+=weapon.getDamage();
        battle.semaphoreNotify();
    }
}
