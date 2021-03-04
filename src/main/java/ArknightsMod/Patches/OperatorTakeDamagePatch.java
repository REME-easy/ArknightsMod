package ArknightsMod.Patches;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class OperatorTakeDamagePatch {
    private static final Logger logger = LogManager.getLogger(OperatorTakeDamagePatch.class.getName());
    public static AbstractCreature target;

    public OperatorTakeDamagePatch(){}

    public static boolean getTarget(){
        ArrayList<AbstractOperator> tmp2 = OperatorGroup.GetOperators();
        ArrayList<AbstractOperator> tmp = new ArrayList<>();
        for(AbstractOperator o1 : tmp2) {
            if(!o1.isDeadOrEscaped())
                tmp.add(o1);
        }
        if(tmp.size() > 0){
            AbstractOperator operator = tmp.get(0);
            for(AbstractOperator o:tmp){
                if(getPosition(o.index) > getPosition(operator.index)) {
                    operator = o;
                }
            }
            if(getPosition(operator.index) < 2) {
                target = null;
                logger.info("getTarget:你承受伤害");
                return false;
            }
            target = operator;
            logger.info("干员" + target.name + "承受伤害");
            return true;
        }else{
            target = null;
            logger.info("getTarget:你承受伤害");
            return false;
        }
    }

    private static int getPosition(int i) {
        return i > 3 ? i - 4 : i;
    }

    @SpirePatch(
            clz = AbstractGameAction.class,
            method = "setValues",
            paramtypez = {AbstractCreature.class, DamageInfo.class}
            )
    public static class ChangeDamageTarget {
        public static void Postfix(AbstractGameAction _inst, AbstractCreature target, DamageInfo info) {
            if (target != null && info.type != DamageInfo.DamageType.HP_LOSS && (info.owner == null || !info.owner.isPlayer) && target == AbstractDungeon.player && getTarget()){
                    _inst.target = OperatorTakeDamagePatch.target;
                    logger.info(OperatorTakeDamagePatch.target + "干员承受伤害");
            } else {
                logger.info("你承受伤害");
            }

        }
    }

    @SpirePatch(
            clz = AbstractGameAction.class,
            method = "setValues",
            paramtypez = {AbstractCreature.class, AbstractCreature.class, int.class}
            )
    public static class ChangeBuffTarget {
        public static void Postfix(AbstractGameAction _inst, AbstractCreature target, AbstractCreature source, int amount) {
            if (source != null && target != null && !source.isPlayer && target == AbstractDungeon.player && getTarget()){
                _inst.target = OperatorTakeDamagePatch.target;
                logger.info(OperatorTakeDamagePatch.target + "干员承受buff");
            }

        }
    }

    @SpirePatch(
            clz = AbstractGameAction.class,
            method = "setValues",
            paramtypez = {AbstractCreature.class, AbstractCreature.class}
            )
    public static class ChangeBuff2Target {
        public static void Postfix(AbstractGameAction _inst, AbstractCreature target, AbstractCreature source) {

            if (source != null && target != null && !source.isPlayer && target == AbstractDungeon.player && getTarget()){
                _inst.target = OperatorTakeDamagePatch.target;
                logger.info(OperatorTakeDamagePatch.target + "干员承受伤害");

            }

        }
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "<ctor>",
            paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}
            )
    public static class ChangeApplyBuffTarget {
        public static void Postfix(ApplyPowerAction _inst, AbstractCreature target, AbstractCreature source, AbstractPower power, int n, boolean b, AbstractGameAction.AttackEffect e) {
            if (source != null && target != null && power.owner != _inst.target && power.owner != AbstractDungeon.player) {
                power.owner = _inst.target;
                logger.info("改变了目标。目前：target:" + _inst.target.name + ",owner:" + power.owner);
            }
        }
    }
}

