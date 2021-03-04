package ArknightsMod.Monsters.Bosses;

import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Vfx.Common.PatrtShieldWaveEffect;
import ArknightsMod.Vfx.LoopEffect.PatrtShieldFlashEffect;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class Patrt extends AbstractEnemy {
    private static final String DIR = "Patrt";
    public  static final String ID = "Arknights_" + DIR;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public  static final String NAME = monsterStrings.NAME;
    private static final String ATLAS = "Enemies/" + DIR + "/" + DIR + ".atlas";
    private static final String JSON = "Enemies/" + DIR + "/" + DIR + ".json";
    private static final int MAX_HP = 1000;
    private static final int CD = 5;
    private static final float SIZE = 1.8F;
    private static final float HB_W = 125.0F;
    private static final float HB_H = 225.0F;
    private static final float OFFSET_X = 400.0F;
    private static final float OFFSET_Y = 0.0F;
    private static final float SPEED = 25.0F;

    private Bone shield;
    private float shieldFlashTimer;
    private float shieldWaveTimer;

    private static final float FLASH_TIME = 0.2F;
    private static final float WAVE_TIME = 0.1F;

    private boolean marching;
    private boolean firstTurn;

    //TODO 爱国者：
    //  1：上buff，全体敌人属性提升
    //  2：20x4
    //  3：上debuff
    //  毁灭姿态：
    //  4：掷矛，30
    //  5：15，打全体
    //  6：改变姿态
    //  7：死亡动画

    public Patrt() {
        super(ID, ATLAS, JSON, SIZE, MAX_HP, CD, 0.0F, 0.0F, HB_W, HB_H, OFFSET_X, OFFSET_Y);
        this.type = EnemyType.BOSS;
        this.speed = SPEED;
        this.shield = this.skeleton.findBone("C_Shield_A");
        this.shieldFlashTimer = 0.0F;
        this.shieldWaveTimer = 0.0F;
        this.marching = true;
        this.firstTurn = true;

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MAX_HP + 125);
        } else {
            this.setHp(MAX_HP);
        }


    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.getCurrRoom().cannotLose = true;
        movePosition(this.drawX + 550.0F, this.drawY);
        moveTo(this.drawX - 550.0F, this.drawY);
    }

    @Override
    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                this.state.setAnimation(0, "revive_3", false);
                this.playIdleAnim();
                break;
            case 7:
                break;
            default:
        }
    }

    @Override
    protected void getMove(int num) {
        if(marching) {
            if(this.firstTurn) {
                this.setMove((byte)1, Intent.BUFF);
                this.firstTurn = false;
                return;
            }

            if(num > 25) {
                this.setMove((byte)2, Intent.ATTACK, 20, 4, true);
            }else {
                this.setMove((byte)3, Intent.DEBUFF);
            }
        }else {
            if(this.firstTurn) {
                this.setMove((byte)4, Intent.ATTACK, 30);
                this.firstTurn = false;
                return;
            }

            if(num > 15 && !this.lastTwoMoves((byte)5)) {
                this.setMove((byte)5, Intent.ATTACK, 15);
            }else {
                this.setMove((byte)4, Intent.ATTACK, 30);
            }
        }
    }

    @Override
    protected void specialTakeTurn() {

    }

    @Override
    protected void specialGetMove(int i) {

    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                this.halfDead = true;
            }

            Iterator s = this.powers.iterator();

            AbstractPower p;
            while(s.hasNext()) {
                p = (AbstractPower)s.next();
                p.onDeath();
            }

            s = AbstractDungeon.player.relics.iterator();

            while(s.hasNext()) {
                AbstractRelic r = (AbstractRelic)s.next();
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());

            this.state.setAnimation(0, "revive_1", false);
            this.state.addAnimation(0, "revive_2", true, 0.0F);
        }
    }

    @Override
    public void die() {
        if(!marching) {
            super.die();
        }else {
            marching = false;
            halfDead = true;
        }

    }

    @Override
    public void update() {
        super.update();

        if(marching) {
            this.shieldFlashTimer += Gdx.graphics.getDeltaTime();
            if(this.shieldFlashTimer > FLASH_TIME) {
                this.shieldFlashTimer = 0.0F;
                AbstractDungeon.effectList.add(new PatrtShieldFlashEffect(this.skeleton.getX() + this.shield.getWorldX(), this.skeleton.getY() + this.shield.getWorldY()));
            }

            this.shieldWaveTimer += Gdx.graphics.getDeltaTime();
            if(this.shieldWaveTimer > WAVE_TIME) {
                this.shieldWaveTimer = 0.0F;
                AbstractDungeon.effectList.add(new PatrtShieldWaveEffect(this.skeleton.getX() + this.shield.getWorldX(), this.skeleton.getY() + this.shield.getWorldY()));
            }
        }
    }

    @Override
    public void playIdleAnim() {
        if(marching) {
            this.state.addAnimation(0, "Idle_1", true, 0.0F);
        }else {
            this.state.addAnimation(0, "Idle_2", true, 0.0F);
        }
    }

    @Override
    public void setIdleAnim() {
        if(marching) {
            this.state.setAnimation(0, "Idle_1", true);
        }else {
            this.state.setAnimation(0, "Idle_2", true);
        }
    }

    @Override
    public void playMoveAnim() {
        if(marching) {
            this.state.setAnimation(0, "Move_1", true);
        }else {
            this.state.setAnimation(0, "Move_2", true);
        }
    }

    @Override
    public void playAttackAnim() {
        if(marching) {
            this.state.setAnimation(0, "Attack_1", false);
        }else {
            this.state.setAnimation(0, "Attack_2", false);
        }
    }
}
