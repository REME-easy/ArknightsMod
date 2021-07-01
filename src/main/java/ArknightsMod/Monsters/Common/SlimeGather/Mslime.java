package ArknightsMod.Monsters.Common.SlimeGather;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class Mslime extends AbstractEnemy {
    private static final String DIR = "Mslime";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 26;
    private static final int CD = 5;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 100.0F;

    public Mslime() {
        this(0.0F, 0.0F);
    }

    public Mslime(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 3);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 6));
        } else {
            this.damage.add(new DamageInfo(this, 5));
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 3)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);

    }

    @Override
    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                this.playAttackAnim();
                this.playIdleAnim();
                GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.POISON));
                break;
            case 2:
                this.playAttackAnim();
                this.playIdleAnim();
                GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.POISON));
                GeneralHelper.addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new SolidnessPower(AbstractDungeon.player, -1)));
        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
        if (num < 25) {
            if (this.lastMove((byte)2)) {
                this.setMove((byte)1, Intent.ATTACK, (this.damage.get(0)).base);
            } else {
                this.setMove((byte)2, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
            }
        } else if (this.lastTwoMoves((byte)1)) {
            this.setMove((byte)2, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
        } else {
            this.setMove((byte)1, Intent.ATTACK, (this.damage.get(0)).base);
        }
    }

    @Override
    protected void specialTakeTurn() {
        GeneralHelper.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 1)));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.BUFF, 0);
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
        this.state.setAnimation(0, "Move_Loop", true);
    }

    @Override
    public void playAttackAnim() {
        this.state.setAnimation(0, "Attack", false);
    }
}
