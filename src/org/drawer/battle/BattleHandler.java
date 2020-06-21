package org.drawer.battle;

import org.stuff.cards.Minion;
import org.stuff.cards.Spell;

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
    public static void spellFunction(Battle battle, Spell spell){
        switch (spell.name){
            case "BookOfSpecters":
                BattleCryFunction.drawCards(battle.whoseTurn(),battle,3);
                break;
            case "FireBall":
                BattleSpellFunctions.attack(battle);
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
                break;
            case "Milad":
                break;
            case "PharaohsBlessing":
                break;
            case "Polymorph":
                break;
            case "ReleaseTheRaptors":
                break;
            case "Shahzad":
                break;
            case "Sprint":
                break;
            case "SwarmOfLocusts":
                break;
        }
    }
}
