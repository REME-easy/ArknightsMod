package ArknightsMod.Monsters.Bosses;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

public class Crowns extends AbstractEnemy {
    private static final String DIR = "Crowns";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 200;
    private static final int CD = 99;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 150.0F;

    public Crowns() {
        this(0.0F, 0.0F);
    }

    public Crowns(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 13);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 4));
        } else {
            this.damage.add(new DamageInfo(this, 4));
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, 2)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);

    }

    @Override
    public void takeTurn() {
        if (this.nextMove == 1) {
            GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
            this.playAttackAnim();
            this.playIdleAnim();
            GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    damage.get(0).base = 4;
                    isDone = true;
                }
            });
        }

        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void addAttackCoolDown(int num) {
        super.addAttackCoolDown(num);
        this.damage.get(0).base += num;
        this.applyPowers();
    }

    @Override
    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }

    @Override
    protected void specialTakeTurn() {

    }

    public void die() {
        this.useFastShakeAnimation(3.0F);
        CardCrawlGame.screenShake.rumble(2.0F);
        super.die();
        this.onBossVictoryLogic();
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
        this.state.setAnimation(0, "Move", true);
    }

    @Override
    public void playAttackAnim() {
        this.state.setAnimation(0, "Attack", true);
    }
}
