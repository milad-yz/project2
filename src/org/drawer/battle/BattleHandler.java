package org.drawer.battle;

import org.drawer.battle.functions.BattleCryFunction;
import org.drawer.battle.functions.BattleSpellFunctions;
import org.drawer.battle.functions.BattleWeaponFunctions;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;
import org.stuff.cards.minions.BloodFenRaptor;
import org.stuff.cards.minions.Locust;

public class BattleHandler {
    public static synchronized void battleCryHandler(PlayerDisplay playerDisplay,Battle battle,Minion minion){
        switch (minion.name){
            case "AzureDrake":
                BattleCryFunction.drawCards(playerDisplay,battle,1);
                break;
            case "BatterHead":
                break;
            case "Behrad":
                break;
            case "BloodFenRaptor":
                break;
            case "CrazedAlchemist":
                BattleCryFunction.swapDamageAndHealth(battle);
                break;
            case "CurioCollector":
                break;
            case "DreadScale":
                break;
            case "Emad":
                break;
            case "HighPriestAmet":
                break;
            case "LootHoarder":
                break;
            case "MagmaRager":
                break;
            case "RaidLeader":
                break;
            case "RavagingGhoul":
                BattleCryFunction.dealDamage2allEnemyMinion(battle.whoseNotTurn().battleCards);
                battle.semaphoreNotify();
                break;
            case "Sathrovarr":
                BattleCryFunction.copy2deck2hand(battle);
                battle.semaphoreNotify();
                break;
            case "SecurityRover":
                break;
            case "SilentKnight":
                break;
            case "TombWarden":
                BattleCryFunction.summon(battle,minion);
                break;
            case "VoidWalker":
                break;
            case "WolfRider":
                break;
        }
    }
    public static synchronized void deathRattleHandler(PlayerDisplay playerDisplay,Battle battle,Minion minion){
        switch (minion.name){
            case "AzureDrake":
                break;
            case "BatterHead":
                break;
            case "Behrad":
                break;
            case "BloodFenRaptor":
                break;
            case "CrazedAlchemist":
                break;
            case "CurioCollector":
                break;
            case "DreadScale":
                break;
            case "Emad":
                break;
            case "HighPriestAmet":
                break;
            case "LootHoarder":
                BattleCryFunction.drawCards(playerDisplay,battle,1);
                break;
            case "MagmaRager":
                break;
            case "RaidLeader":
                break;
            case "RavagingGhoul":
                break;
            case "Sathrovarr":
                break;
            case "SecurityRover":
                break;
            case "SilentKnight":
                break;
            case "TombWarden":
                break;
            case "VoidWalker":
                break;
            case "WolfRider":
                break;
        }
    }
    public static void spellHandler(Battle battle, Spell spell){
        switch (spell.name){
            case "BookOfSpecters":
                BattleCryFunction.drawCards(battle.whoseTurn(),battle,3);
                break;
            case "FireBall":
                BattleSpellFunctions.dealDamage(battle);
                break;
            case "Foad":
                BattleSpellFunctions.takeMinion(battle);
                break;
            case "FriendlySmith":
                BattleSpellFunctions.Discover(battle,1);
                break;
            case "GnomishArmyKnife":
                break;
            case "HellFire":
                BattleSpellFunctions.dealDamage2all(battle,3);
                break;
            case "Milad":
                BattleSpellFunctions.giveHealth2hero(battle,5);
                break;
            case "PharaohsBlessing":
                break;
            case "Polymorph":
                BattleSpellFunctions.convert2Sheep(battle);
                break;
            case "ReleaseTheRaptors":
                BattleSpellFunctions.summon(battle,new BloodFenRaptor(),3);
                break;
            case "Shahzad":
                BattleSpellFunctions.giveHealth2minions(battle,battle.whoseTurn().battleCards,2);
                break;
            case "Sprint":
                BattleCryFunction.drawCards(battle.whoseTurn(),battle,4);
                break;
            case "SwarmOfLocusts":
                BattleSpellFunctions.summon(battle,new Locust(),7);
                break;
        }
    }
    public static void WeaponHandler(Battle battle, Weapon weapon){
        switch (weapon.name){
            case "GearBlade":
                BattleWeaponFunctions.weaponOnHero(battle,weapon);
                break;
            case "HeavyAxe":
                BattleWeaponFunctions.weaponOnHero(battle,weapon);
                break;
            case "WickedKnife":
                BattleWeaponFunctions.weaponOnHero(battle,weapon);
                break;
        }
    }

    public static void QuestAndRewardHandler(Battle battle, QuestAndReward questAndReward) {
        switch (questAndReward.name){
            case "LearnDraconic":
                battle.whoseTurn().sideQuestCounters.add(new SideQuestCounter(battle.whoseTurn(),battle,"Spell",8));
                break;
            case "StrengthInNumbers":
                battle.whoseTurn().sideQuestCounters.add(new SideQuestCounter(battle.whoseTurn(),battle,"Minion",10));
                break;
        }
    }
}
