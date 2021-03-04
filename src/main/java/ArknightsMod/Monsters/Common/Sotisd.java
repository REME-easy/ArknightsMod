package ArknightsMod.Monsters.Common;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Vfx.LoopEffect.SotisdShieldFlashEffect;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

public class Sotisd extends AbstractEnemy {
    private static final String DIR = "Sotisd";
    public  static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 250;
    private static final int CD = 4;
    private static final float SIZE = 1.8F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 225.0F;
    private static final float SPEED = 25.0F;

    private Bone shield;
    private float shieldFlashTimer;
    private boolean block;
    private int blockAmt;

    private static final float FLASH_TIME = 0.2F;


    public Sotisd() {
        this(0.0F, 0.0F, false);
    }

    public Sotisd(float x, float y, boolean block) {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, x, y);
        this.type = EnemyType.NORMAL;
        this.speed = SPEED;
        this.shield = this.skeleton.findBone("Shield2");
        this.shieldFlashTimer = 0.0F;
        this.block = block;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 10));
            this.blockAmt = 15;
        } else {
            this.damage.add(new DamageInfo(this, 8));
            this.blockAmt = 12;
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
        switch(this.nextMove) {
            case 1:
                GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
                GeneralHelper.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                GeneralHelper.addToBot(new MakeTempCardInDiscardAction(new Slimed(), 1));
                break;
            case 2:
                GeneralHelper.addToBot(new AnimateSlowAttackAction(this));
                GeneralHelper.addToBot(new GainBlockRandomMonsterAction(this, this.blockAmt));
            default:
                break;
        }
        GeneralHelper.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if(lastMove((byte)2)) {
            this.setMove((byte)1, Intent.DEFEND);
        }else if(lastMove((byte)1)) {
            this.setMove((byte)2, Intent.ATTACK, this.damage.get(0).base);
        }else if(block) {
            this.setMove((byte)1, Intent.DEFEND);
        }else {
            this.setMove((byte)2, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    @Override
    public void update() {
        super.update();
        this.shieldFlashTimer += Gdx.graphics.getDeltaTime();
        if(this.shieldFlashTimer > FLASH_TIME) {
            this.shieldFlashTimer = 0.0F;
            AbstractDungeon.effectList.add(new SotisdShieldFlashEffect(this.skeleton.getX() + this.shield.getWorldX(), this.skeleton.getY() + this.shield.getWorldY()));
        }
    }

    @Override
    protected void specialTakeTurn() {
        GeneralHelper.addToBot(new GainBlockRandomMonsterAction(this, this.blockAmt - 5));
    }

    @Override
    protected void specialGetMove(int i) {

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
        this.state.setAnimation(0, "Move_Begin", false);
        this.state.addAnimation(0, "Move_Loop", true, 0.0F);
    }

    @Override
    public void playAttackAnim() {
        this.state.setAnimation(0, "Attack", true);
    }
}
