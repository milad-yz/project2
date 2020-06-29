package org.drawer.battle;

import org.drawer.battle.functions.*;
import org.stuff.Hero;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;
import org.stuff.cards.minions.BloodFenRaptor;
import org.stuff.cards.minions.GuardBot;
import org.stuff.cards.minions.Locust;
import org.stuff.cards.minions.TombWarden;

public class BattleHandler {
    public static synchronized void battleCryHandler(PlayerDisplay playerDisplay,Battle battle,Minion minion){
        BattleFunctions.battleCardsUpdate(playerDisplay,minion);
        BattleFunctions.attackFromSwamp(battle,minion);
        switch (minion.name){
            case "AzureDrake":
                BattleCryFunction.drawCards(playerDisplay,battle,1);
                break;
            case "BatterHead":
            case "Behrad":
            case "BloodFenRaptor":
            case "DreadScale":
            case "CurioCollector":
            case "Emad":
            case "MagmaRager":
            case "LootHoarder":
            case "HighPriestAmet":
            case "SecurityRover":
            case "SilentKnight":
            case "VoidWalker":
            case "WolfRider":
                break;
            case "CrazedAlchemist":
                BattleCryFunction.swapDamageAndHealth(battle);
                break;
            case "RaidLeader":
                BattleCryFunction.raidLeaderTransmission(playerDisplay,1,minion);
                battle.semaphoreNotify();
                break;
            case "RavagingGhoul":
                BattleCryFunction.dealDamage2allEnemyMinion(battle,battle.whoseNotTurn().battleCards);
                battle.semaphoreNotify();
                break;
            case "Sathrovarr":
                BattleCryFunction.copy2deck2hand(battle);
                battle.semaphoreNotify();
                break;
            case "TombWarden":
                BattleSpellFunctions.summon(battle,battle.whoseTurn(),new TombWarden(),1);
                battle.semaphoreNotify();
                break;
        }
    }
    public static synchronized void deathRattleHandler(PlayerDisplay playerDisplay,Battle battle,Minion minion){
        switch (minion.name){
            case "AzureDrake":
            case "BatterHead":
            case "Behrad":
            case "BloodFenRaptor":
            case "CrazedAlchemist":
            case "CurioCollector":
            case "DreadScale":
            case "Emad":
            case "HighPriestAmet":
            case "MagmaRager":
            case "RavagingGhoul":
            case "Sathrovarr":
            case "SecurityRover":
            case "SilentKnight":
            case "TombWarden":
            case "VoidWalker":
            case "WolfRider":
                break;
            case "LootHoarder":
                BattleCryFunction.drawCards(playerDisplay,battle,1);
                break;
            case "RaidLeader":
                BattleCryFunction.raidLeaderTransmission(playerDisplay,-1,minion);
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
                BattleSpellFunctions.ArmyKnife(battle);
                break;
            case "HellFire":
                BattleSpellFunctions.dealDamage2all(battle,3);
                break;
            case "Milad":
                BattleSpellFunctions.giveHealth2hero(battle,5);
                break;
            case "PharaohsBlessing":
                BattleSpellFunctions.blessing(battle);
                break;
            case "Polymorph":
                BattleSpellFunctions.convert2Sheep(battle);
                break;
            case "ReleaseTheRaptors":
                BattleSpellFunctions.summon(battle,battle.whoseTurn(),new BloodFenRaptor(),3);
                break;
            case "Shahzad":
                BattleSpellFunctions.giveHealth2minions(battle,battle.whoseTurn().battleCards,2);
                break;
            case "Sprint":
                BattleCryFunction.drawCards(battle.whoseTurn(),battle,4);
                break;
            case "SwarmOfLocusts":
                BattleSpellFunctions.summon(battle,battle.whoseTurn(),new Locust(),7);
                break;
            case "Overflow":
                BattleSpellFunctions.restore2allCharacters(battle,5);
                break;
            case "Duel!":
                BattleSpellFunctions.summonFromDeck(battle,battle.whoseTurn(),1);
                BattleSpellFunctions.summonFromDeck(battle,battle.whoseNotTurn(),1);
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
                battle.whoseTurn().sideQuestCounters.add(new SideQuestCounter(battle.whoseTurn(),battle,"Spell",8,battle.whoseTurn().questAndRewardPanel,questAndReward));
                break;
            case "StrengthInNumbers":
                battle.whoseTurn().sideQuestCounters.add(new SideQuestCounter(battle.whoseTurn(),battle,"Minion",10,battle.whoseTurn().questAndRewardPanel,questAndReward));
                break;
        }
    }
    public static void endTurnHandler(Battle battle,PlayerDisplay playerDisplay){
        for (int i = 0; i < 7; i++) {
            if(playerDisplay.battleCards.get(i)!=null){
                if(playerDisplay.battleCards.get(i).name.equals("DreadScale")){
                    playerDisplay.battleCards.get(i).setHealth(playerDisplay.battleCards.get(i).getHealth()+1);
                    BattleFunctions.dealDamage2allMinions(battle);
                }
            }
        }
        switch (playerDisplay.hero.name){
            case "PALADIN":
                BattleFunctions.paladinSpecialPower(playerDisplay);
                break;
        }
        //
        for (int i = 0; i < 7; i++) {
            if (playerDisplay.battleCards.get(i) != null) {
                playerDisplay.battleCards.get(i).rush = playerDisplay.battleCards.get(i).perTurnAttack;
            }
        }
        playerDisplay.hero.rush=1;
        playerDisplay.hero.heroPowerRush=battle.perTurnHeroPower;
        if(playerDisplay.hero.durability>0)
            playerDisplay.hero.durability -=1;
        if (playerDisplay.hero.durability<=0) {
            playerDisplay.hero.durability = 0;
            playerDisplay.hero.damage=0;
        }
    }

    public static void damagedStuffHandler(Battle battle, PlayerDisplay playerDisplay, Minion minion) {
        switch (minion.name){
            case "SecurityRover":
                BattleSpellFunctions.summon(battle,playerDisplay,new GuardBot(),1);
                break;
        }
    }

    public static void heroPowerHandler(Battle battle, Hero hero) {
        switch (hero.name){
            case "MAGE":
                HeroFunction.magePower(battle);
            break;
            case "WARLOCK":
                HeroFunction.warlockPower(battle);
                break;
            case "PRIEST":
                HeroFunction.priestPower(battle);
                break;
            case "PALADIN":
                HeroFunction.paladinPower(battle);
                break;
            case "ROGUE":
                HeroFunction.stealFromEnemyDeck(battle);
                break;
        }
    }
}
