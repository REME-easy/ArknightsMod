package ArknightsMod.Monsters.Common;

import ArknightsMod.Actions.DamageAllFriendsAction;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Powers.Monster.MagicAttackPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class Aoemag extends AbstractEnemy {
    private static final String DIR = "Aoemag_2";
    public static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 60;
    private static final int CD = 2;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 140.0F;

    public Aoemag() {
        this(0.0F, 0.0F);
    }

    public Aoemag(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 8);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 4, DamageType.THORNS));
        } else {
            this.damage.add(new DamageInfo(this, 3, DamageType.THORNS));
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
        if(this.nextMove == 1) {
            this.playAttackAnim();
            this.playIdleAnim();
            this.addToBot(new DamageAllFriendsAction(this.damage.get(0), AttackEffect.FIRE, true));
        }
        GeneralHelper.addToBot(new RollMoveAction(this));


    }

    @Override
    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }

    @Override
    protected void specialTakeTurn() {
        this.addToBot(new GainBlockAction(this, 1));
    }

    @Override
    protected void specialGetMove(int i) {
        this.setSpecialMove((byte)1, Intent.DEFEND, 0);
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
