package ArknightsMod.Patches;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Monsters.AbstractEnemy;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.city.Healer;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import javassist.CtBehavior;
import org.apache.logging.log4j.Logger;

public class ServalPatches {

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "addBlock"
    )
    public static class ValidBlockPatch {
        public ValidBlockPatch() {}

        public static SpireReturn<Void> Prefix(AbstractCreature _inst) {
            if(!_inst.isDeadOrEscaped() && !_inst.isDead) {
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz = EscapeAction.class,
            method = "update"
    )
    public static class DontEscapePatch {
        public DontEscapePatch() {}

        public static SpireReturn<Void> Prefix(EscapeAction _inst) {
            if(_inst.source instanceof AbstractEnemy) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SuicideAction.class,
            method = "update"
    )
    public static class DontSuicidePatch {
        public DontSuicidePatch() {}

        public static SpireReturn<Void> Prefix(SuicideAction _inst) {
            if(_inst.source instanceof AbstractEnemy) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = HideHealthBarAction.class,
            method = "update"
    )
    public static class DontHideHealthBarPatch {
        public DontHideHealthBarPatch() {}

        public static SpireReturn<Void> Prefix(HideHealthBarAction _inst) {
            if(_inst.source instanceof AbstractEnemy) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = Darkling.class,
            method = "damage"
    )
    public static class DarklingDamagePatch {
        public DarklingDamagePatch() {}

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"allDead"}
        )
        public static void Insert(Darkling _inst, DamageInfo info, @ByRef boolean[] allDead) {
            if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                allDead[0] = false;
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
            }
        }
    }

    @SpirePatch(
            clz = MummifiedHand.class,
            method = "onUseCard"
    )
    public static class MummifiedHandPatch {
        public MummifiedHandPatch() {}

        public static SpireReturn<Void> Prefix(MummifiedHand _inst, AbstractCard card, UseCardAction action) {
            if(card instanceof AbstractOperatorCard) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CuriosityPower.class,
            method = "onUseCard",
            paramtypez = {AbstractCard.class, UseCardAction.class}
    )
    public static class CuriosityPowerPatch {
        public CuriosityPowerPatch() {}

        public static SpireReturn<Void> Prefix(CuriosityPower _inst, AbstractCard card, UseCardAction action) {
            if(card instanceof AbstractOperatorCard) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(
            clz = FlightPower.class,
            method = "onRemove"
    )
    public static class FlightPowerPatch {
        public FlightPowerPatch() {}

        public static SpireReturn<Void> Prefix(FlightPower _inst) {
            if(!_inst.owner.id.equals(Byrd.ID)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = HealAction.class,
            method = "update"
    )
    public static class DontHealingAll1 {
        public DontHealingAll1() {}

        public static SpireReturn<Void> Prefix(HealAction _inst) {
            if(_inst.source != null && Healer.ID.equals(_inst.source.id) && !Centurion.ID.equals(_inst.target.id)) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class DontHealingAll2 {
        public DontHealingAll2() {}

        public static SpireReturn<Void> Prefix(ApplyPowerAction _inst) {
            if(_inst.source != null && Healer.ID.equals(_inst.source.id) && !Centurion.ID.equals(_inst.target.id)) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }else if(_inst.source != null && GremlinLeader.ID.equals(_inst.source.id) && (_inst.target instanceof AbstractEnemy)) {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
