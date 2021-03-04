package ArknightsMod.Helper;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static java.lang.Math.min;

public class GeneralHelper {
    private static final Logger logger = LogManager.getLogger(GeneralHelper.class.getName());
    public GeneralHelper(){}

    public static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void addToNext(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.actions.add(1, action);
        }
    }

    public static void addEffect(AbstractGameEffect effect) {
        AbstractDungeon.effectList.add(effect);
    }

    public static ArrayList<AbstractMonster> monsters() {return  AbstractDungeon.getMonsters().monsters;}

    public static boolean isAlive(AbstractCreature c) {
        return !c.isDeadOrEscaped() && !c.isDead;
    }

    public static int aliveMonstersAmount() {
        int i = 0;
        for(AbstractMonster m : monsters()) {
            if(!m.isDeadOrEscaped() && !m.isDead) {
                i++;
            }
        }
        return i;
    }

    public static ArrayList<AbstractMonster> getRandomMonsters(int amt) {
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        int num = min(amt, GeneralHelper.aliveMonstersAmount());
        while (monsters.size() < num) {
            boolean dupe = false;

            AbstractMonster m = GeneralHelper.getRandomMonsterSafe();
            for(AbstractMonster m1 : monsters)
                if (m == m1) {
                    dupe = true;
                    break;
                }

            if(!dupe) monsters.add(m);
        }
        return monsters;
    }

    public static AbstractMonster getRandomMonsterSafe(){
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
            if(m != null && !m.isDeadOrEscaped() && !m.isDead){
                monsters.add(m);
            }
        }
        //logger.info(monsters.toString());
        if(monsters.size() == 0){
            return null;
        }else if(monsters.size() == 1){
            return monsters.get(0);
        }
        //logger.info(m.toString());
        return monsters.get(AbstractDungeon.cardRandomRng.random(0, monsters.size() - 1));
    }

    public static AbstractOperator getRandomOperatorToHeal(){
        ArrayList<AbstractOperator> operators = new ArrayList<>();
        for(AbstractOperator o: OperatorGroup.GetOperators()){
            if(o != null && !o.isDeadOrEscaped() && !o.isDead){
                operators.add(o);
            }
        }
        //logger.info(monsters.toString());
        if(operators.size() == 0){
            return null;
        }else if(operators.size() == 1){
            return operators.get(0);
        }
        AbstractOperator o = operators.get(0);
        for(AbstractOperator o1:operators){
            if((float)o1.currentHealth / o1.maxHealth < (float)o.currentHealth / o.maxHealth){
                o = o1;
            }
        }
        //logger.info(m.toString());
//        for(AbstractOperator o2 : operators) {
//            logger.info("operators:" + o2.name);
//        }
        AbstractPlayer p = AbstractDungeon.player;
        if((float)p.currentHealth / p.maxHealth < (float)o.currentHealth / o.maxHealth) {
            return null;
        }
        logger.info(o.name + "接受治疗");
        return o;
    }

    public static AbstractOperator getRandomOperatorExceptSelf(AbstractOperator target) {
        if(OperatorGroup.GetOperators().size() > 2) {
            ArrayList<AbstractOperator> operators = new ArrayList<>();
            for(AbstractOperator o: OperatorGroup.GetOperators()){
                if(o != null && !o.isDeadOrEscaped() && !o.isDead && o != target){
                    operators.add(o);
                }
            }
            if(operators.size() > 0) {
                if(operators.size() == 1)
                    return operators.get(0);
                else
                    return operators.get(AbstractDungeon.cardRandomRng.random(0, operators.size() - 1));
            }
        }
        return null;
    }

    public static AbstractOperator getRandomOperatorHasSkillExceptSelf(AbstractOperator target) {
        if(OperatorGroup.GetOperators().size() >= 2) {
            ArrayList<AbstractOperator> operators = new ArrayList<>();
            for(AbstractOperator o: OperatorGroup.GetOperators()){
                if(o != null && !o.isDeadOrEscaped() && !o.isDead && o != target && o.currentBattleSkill != null && !o.currentBattleSkill.isSpelling && o.currentBattleSkill.maxSkill > 0){
                    operators.add(o);
                }
            }
            if(operators.size() > 0) {
                if(operators.size() == 1)
                    return operators.get(0);
                else
                    return operators.get(AbstractDungeon.cardRandomRng.random(0, operators.size() - 1));
            }
        }
        return null;
    }

    public static void DamageLastOperator(DamageInfo info, AttackEffect effect) {
        AbstractCreature target = null;
        for (int i = OperatorGroup.maxSize; i > 0; i--) {
            if(OperatorGroup.GetOperatorByIndex(i - 1) != null) {
                target = OperatorGroup.GetOperatorByIndex(i - 1);
                break;
            }
        }
        if(target != null) {
            addToBot(new DamageAction(target, info, effect));
        }
    }
    
    public static void tempLoseStrength(AbstractCreature mo, AbstractCreature p, int amt){
        addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amt), -amt, true, AttackEffect.NONE));
        if (!mo.hasPower("Artifact")) {
            addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amt), amt, true, AttackEffect.NONE));
        }
    }

}
