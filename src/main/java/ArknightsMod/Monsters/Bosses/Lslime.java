package ArknightsMod.Monsters.Bosses;

import ArknightsMod.Actions.DamageAllFriendsAction;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Monster.BuringPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class Lslime extends AbstractEnemy {
    private static final String DIR = "Lslime";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String[] TIPS = monsterStrings.DIALOG;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 140;
    private static final int CD = 14;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 200.0F;

    public Lslime() {
        this(0.0F, 0.0F);
    }

    public Lslime(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MAX_HP + 12);
        } else {
            this.setHp(MAX_HP);
        }

        this.damage.add(new DamageInfo(this, 1));
        this.specialDamage.add(new DamageInfo(this, 10));
        this.extraTip.header = TIPS[0];
        this.extraTip.body = TIPS[1];
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 6)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    protected void addTip() {
        super.addTip();
        tips.add(extraTip);
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                this.playAttackAnim();
                this.playIdleAnim();
                GeneralHelper.addToBot(new DamageAllFriendsAction(this.damage.get(0), AttackEffect.FIRE, true));
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    GeneralHelper.addToBot(new ApplyPowerAction(o, this, new BuringPower(o)));
                }
                GeneralHelper.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BuringPower(AbstractDungeon.player)));
                break;
            case 2:
                this.playAttackAnim();
                this.playIdleAnim();
                GeneralHelper.addToBot(new DamageAllFriendsAction(this.damage.get(0), AttackEffect.FIRE, true));
        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
        if (num < 25) {
            if (this.lastMove((byte)1)) {
                this.setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
            } else {
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
            }
        } else if (this.lastTwoMoves((byte)2)) {
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
        } else {
            this.setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
        }
    }

    @Override
    protected void specialTakeTurn() {
        this.state.addAnimation(0, "Skill", false, 0.0F);
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0)));
        GeneralHelper.addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new BuringPower(AbstractDungeon.player)));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.ATTACK_DEBUFF, this.specialDamage.get(0).base);
    }

    @Override
    public void playIdleAnim() {
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public void setIdleAnim() {
        this.state.setAnimation(0, "Idle", true);
    }


    @Override
    public void playMoveAnim() {
        this.state.setAnimation(0, "Move", true);
    }

    @Override
    public void playAttackAnim() {
        this.state.setAnimation(0, "Attack", false);
    }
}
