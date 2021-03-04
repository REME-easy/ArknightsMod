package ArknightsMod.Monsters.Common;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class Litamr extends AbstractEnemy {
    private static final String DIR = "Litamr";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 25;
    private static final int CD = 4;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 140.0F;

    private int blockAmt;

    public Litamr() {
        this(0.0F, 0.0F);
    }

    public Litamr(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 3);
            this.blockAmt = 9;
        } else {
            this.setHp(MAX_HP);
            this.blockAmt = 7;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 4));
            this.specialDamage.add(new DamageInfo(this, 4));
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 3));
            this.specialDamage.add(new DamageInfo(this, 3));
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, 1)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);

    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
                this.playAttackAnim();
                this.playIdleAnim();
                GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.SLASH_DIAGONAL));
                break;
            case 2:
                GeneralHelper.addToBot(new GainBlockRandomMonsterAction(this, this.blockAmt));

        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
        int aliveCount = 0;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDying && !m.isEscaping) {
                ++aliveCount;
            }
        }
        if(aliveCount > 0) {
            if(num < 25) {
                if(this.lastTwoMoves((byte)2)) {
                    this.setMove((byte)1, Intent.ATTACK,  this.damage.get(0).base);
                }else {
                    this.setMove((byte)2, Intent.DEFEND);
                }
            }else {
                if(this.lastTwoMoves((byte)1)) {
                    this.setMove((byte)2, Intent.DEFEND);
                }else {
                    this.setMove((byte)1, Intent.ATTACK,  this.damage.get(0).base);
                }
            }
        }else
            this.setMove((byte)1, Intent.ATTACK,  this.damage.get(0).base);

    }

    @Override
    protected void specialTakeTurn() {
        GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
        this.playAttackAnim();
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0), AttackEffect.SLASH_DIAGONAL));
        GeneralHelper.addToBot(new GainBlockRandomMonsterAction(this, this.blockAmt - 4));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.ATTACK_DEFEND, this.specialDamage.get(0).base);
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
