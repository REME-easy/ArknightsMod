package ArknightsMod.Monsters.Common.FrostNova;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class Icebrk extends AbstractEnemy {
    private static final String DIR = "Icebrk";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String[] TIPS = monsterStrings.DIALOG;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 60;
    private static final int CD = 3;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 140.0F;

    public Icebrk() {
        this(0.0F, 0.0F);
    }

    public Icebrk(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 5);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 12));
            this.specialDamage.add(new DamageInfo(this, 4));
        } else {
            this.damage.add(new DamageInfo(this, 10));
            this.specialDamage.add(new DamageInfo(this, 3));
        }
        this.enemyTags.add(EnemyTag.YETI);
        this.extraTip.header = TIPS[0];
        this.extraTip.body = TIPS[1];
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    protected void addTip() {
        super.addTip();
        tips.add(extraTip);
    }

    @Override
    public void applyPowers() {
        AbstractPower tmp = null;
        int i = 0;
        for(AbstractPower p : powers) {
            if(StrengthPower.POWER_ID.equals(p.ID)) {
                tmp = p;
                i = p.amount;
                p.amount *= 3;
                break;
            }
        }
        super.applyPowers();
        if(tmp != null) tmp.amount = i;
    }

    @Override
    public void takeTurn() {
        if (this.nextMove == 1) {
            GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
            this.playAttackAnim();
            this.playIdleAnim();
            GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.SLASH_DIAGONAL));
        }
        GeneralHelper.addToBot(new RollMoveAction(this));

    }

    @Override
    protected void getMove(int num) {
            this.setMove((byte)1, Intent.ATTACK,  this.damage.get(0).base);
    }

    @Override
    protected void specialTakeTurn() {
        GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
        this.playAttackAnim();
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0), AttackEffect.SLASH_DIAGONAL));
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
