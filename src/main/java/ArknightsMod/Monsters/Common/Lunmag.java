package ArknightsMod.Monsters.Common;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Monster.MagicAttackPower;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class Lunmag extends AbstractEnemy {
    private static final String DIR = "Lunmag_2";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 40;
    private static final int CD = 5;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 140.0F;

    public Lunmag() {
        this(0.0F, 0.0F);
    }

    public Lunmag(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 5);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 6, DamageType.THORNS));
            this.specialDamage.add(new DamageInfo(this, 6, DamageType.THORNS));
        } else {
            this.damage.add(new DamageInfo(this, 5, DamageType.THORNS));
            this.specialDamage.add(new DamageInfo(this, 5, DamageType.THORNS));
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, 1)));
        this.addToBot(new ApplyPowerAction(this, this, new MagicAttackPower(this)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    public void takeTurn() {

        if (nextMove == 1) {
            this.playAttackAnim();
            this.playIdleAnim();
            GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.FIRE));
        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }

    @Override
    protected void specialTakeTurn() {
        this.playAttackAnim();
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0), AttackEffect.FIRE));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.ATTACK, this.specialDamage.get(0).base);
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
        this.state.setAnimation(0, "Attack", true);
    }
}
