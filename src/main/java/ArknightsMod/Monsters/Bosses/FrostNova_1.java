package ArknightsMod.Monsters.Bosses;

import ArknightsMod.Actions.DamageAllFriendsAction;
import ArknightsMod.Actions.PlzWaitAction;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Monster.MagicAttackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FrostNova_1 extends AbstractEnemy {
    private static final String DIR = "Frstar_1";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 212;
    private static final int CD = 14;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 200.0F;

    private boolean isRevive = false;

    public FrostNova_1() {
        this(0.0F, 0.0F);
    }

    public FrostNova_1(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MAX_HP + 12);
        } else {
            this.setHp(MAX_HP);
        }

        this.damage.add(new DamageInfo(this, 5, DamageType.THORNS));
        this.specialDamage.add(new DamageInfo(this, 4, DamageType.THORNS));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if(currentHealth <= 0 && !isRevive) {
            hideHealthBar();
            state.setAnimation(0, "Skill_2", false);
            isRevive = true;
            GeneralHelper.addToNext(new AbstractGameAction() {
                @Override
                public void update() {
                    playIdleAnim();
                    if (AbstractDungeon.ascensionLevel >= 9) {
                        maxHealth = 230;
                    } else {
                        maxHealth = 216;
                    }

                    if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                        float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                        maxHealth = (int)((float)maxHealth * mod);
                    }
                    isDone = true;
                }
            });
            addToBot(new PlzWaitAction(8.0F));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    showHealthBar();
                    isDone = true;
                }
            });
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
            addToBot(new HealAction(this, this, maxHealth));
        }
    }

    @Override
    public void die() {
        this.die(true);
    }

    @Override
    public void die(boolean triggerRelics) {
        if(isRevive) {
            this.state.setAnimation(0, "Die", false);
            super.die(triggerRelics);
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.addToBot(new ApplyPowerAction(this, this, new MagicAttackPower(this)));
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                this.playAttackAnim();
                this.playIdleAnim();
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                this.playAttackAnim();
                this.playIdleAnim();
        }
        GeneralHelper.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }

    @Override
    protected void specialTakeTurn() {
        this.state.addAnimation(0, "Skill_1", false, 0.0F);
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAllFriendsAction(this.specialDamage.get(0), AttackEffect.BLUNT_LIGHT, true));
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
        this.state.setAnimation(0, "Attack", false);
    }
}
