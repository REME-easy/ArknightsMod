package ArknightsMod.Monsters.Common.SlimeGather;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Monster.BombSlimePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BombSlime extends AbstractEnemy {
    private static final String DIR = "BombSlime_2";
    public  static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 21;
    private static final int CD = 4;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 100.0F;

    private int bombAmt;

    public BombSlime() {
        this(0.0F, 0.0F);
    }

    public BombSlime(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 3);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 6));
            this.bombAmt = 12;
        } else {
            this.damage.add(new DamageInfo(this, 5));
            this.bombAmt = 10;
        }
        this.specialDamage.add(new DamageInfo(this, 2));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new BombSlimePower(this, bombAmt)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                this.addToBot(new AnimateSlowAttackAction(this));
                this.playAttackAnim();
                this.playIdleAnim();
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                break;
        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
        if (num < 25) {
            if (this.lastMove((byte)2)) {
                this.setMove((byte)1, Intent.ATTACK, (this.damage.get(0)).base);
            } else {
                this.setMove((byte)2, Intent.BUFF);
            }
        } else if (this.lastTwoMoves((byte)1)) {
            this.setMove((byte)2, Intent.BUFF);
        } else {
            this.setMove((byte)1, Intent.ATTACK, (this.damage.get(0)).base);
        }
    }

    @Override
    protected void specialTakeTurn() {
        this.addToBot(new AnimateSlowAttackAction(this));
        this.playAttackAnim();
        this.playIdleAnim();
        this.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0), AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.ATTACK, 2);
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
