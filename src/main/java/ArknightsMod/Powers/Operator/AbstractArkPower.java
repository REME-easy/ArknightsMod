package ArknightsMod.Powers.Operator;

import ArknightsMod.Helper.GeneralHelper;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

public class AbstractArkPower extends AbstractPower {
    public AbstractArkPower(){}

    public void onOperatorAttack() {}

    public void afterOperatorAttack() {}

    public void onSpawnMonster(AbstractMonster m) {}

    public float onReceiveFatalDamage(float dmg) {return  dmg;}

    protected void addToNext(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.actions.add(1, action);
        }
    }

    @SpirePatch(
            clz = SpawnMonsterAction.class,
            method = "update"
    )
    public static class SpawnMonsterPatch {
        public SpawnMonsterPatch() {}

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(SpawnMonsterAction _inst, AbstractMonster ___m) {
            for(AbstractMonster m : GeneralHelper.monsters()) {
                for(AbstractPower p : m.powers) {
                    if(p instanceof AbstractArkPower) {
                        ((AbstractArkPower) p).onSpawnMonster(___m);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "init");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
