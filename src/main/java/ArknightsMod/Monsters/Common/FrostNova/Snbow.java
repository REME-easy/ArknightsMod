package ArknightsMod.Monsters.Common.FrostNova;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

public class Snbow extends AbstractEnemy {
    private static final String DIR = "Snbow";
    public  static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 25;
    private static final int CD = 4;
    private static final float SIZE = 2.0F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 140.0F;

    public Snbow() {
        this(0.0F, 0.0F);
    }

    public Snbow(float x, float y) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MAX_HP + 2);
        } else {
            this.setHp(MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.specialDamage.add(new DamageInfo(this, 7));
        } else {
            this.specialDamage.add(new DamageInfo(this, 6));
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int num) {
        this.setMove((byte)1, Intent.UNKNOWN);
    }

    @Override
    protected void specialTakeTurn() {
        this.playAttackAnim();
        this.playIdleAnim();
        GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.specialDamage.get(0), AttackEffect.BLUNT_LIGHT));
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
