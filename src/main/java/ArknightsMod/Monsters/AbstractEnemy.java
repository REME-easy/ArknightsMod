package ArknightsMod.Monsters;

import ArknightsMod.Powers.Monster.MagicAttackPower;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.DebuffParticleEffect;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect;
import com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractEnemy extends AbstractMonster {
    private int currentAttackCoolDown;
    private int attackCoolDown;
    private Color fontColor;
    private Color StatColor;
    private float fontScale;

    private float targetX;
    private float targetY;
    protected float speed;

    private Intent specialIntent;
    private PowerTip specialIntentTip;
    protected PowerTip extraTip;
    private EnemyMoveInfo specialMove;
    private int specialIntentDmg;
    private Hitbox specialIntentHb;
    private byte nextSpecialMove;
    protected ArrayList<DamageInfo> specialDamage;

    private Texture specialIntentImg;
    private float specialIntentAngle;
    private BobEffect bobEffect;
    private ArrayList<AbstractGameEffect> specialIntentVfx;
    private float specialIntentParticleTimer;

    public ArrayList<EnemyTag> enemyTags;

    private boolean isMoving = false;

    public static final String[] SPECIAL_TEXT = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_MONSTER").TEXT;


    public AbstractEnemy(String id, String atlas, String json,float size, int maxHealth, int cd, float hb_x, float hb_y, float hb_w, float hb_h, float offsetX, float offsetY) {
        super(CardCrawlGame.languagePack.getMonsterStrings(id).NAME, id, maxHealth, hb_x, hb_y, hb_w, hb_h, null, offsetX, offsetY);
        this.loadAnimation(atlas, json, size);
        this.stateData.setDefaultMix(0.5F);
        this.skeleton.getRootBone().setScaleX(-1.0F);
        this.attackCoolDown = cd;
        this.targetX = this.drawX;
        this.targetY = this.drawY;
        this.speed = 100.0F;
        this.StatColor = Color.WHITE.cpy();
        this.specialIntent = Intent.DEBUG;
        this.specialIntentDmg = -1;
        this.specialIntentTip = new PowerTip();
        this.extraTip = new PowerTip();
        this.bobEffect = new BobEffect();
        this.specialIntentVfx = new ArrayList<>();
        this.enemyTags = new ArrayList<>();
        this.specialIntentParticleTimer = 0.0F;
        this.nextSpecialMove = -1;
        this.specialIntentHb = new Hitbox(64.0F * Settings.scale, 64.0F * Settings.scale);
        this.fontScale = 0.6F;
        this.fontColor = Color.WHITE.cpy();
        this.specialDamage = new ArrayList<>();
    }

    @Override
    public abstract void takeTurn();

    @Override
    protected abstract void getMove(int i);

    protected abstract void specialTakeTurn();

    protected abstract void specialGetMove(int i);

    public abstract void playIdleAnim();

    public abstract void setIdleAnim();

    public abstract void playMoveAnim();

    public abstract void playAttackAnim();

    @Override
    public void usePreBattleAction() {
        this.playIdleAnim();
        this.specialIntentHb.move(this.hb.cX + this.intentOffsetX +56.0F, this.hb.cY + this.hb_h / 2.0F + 64.0F * Settings.scale / 2.0F);
    }

    @Override
    public void init() {
        super.init();
        this.specialGetMove(0);
    }

    @Override
    public void createIntent() {
        super.createIntent();
        this.specialIntent = this.specialMove.intent;
        this.nextSpecialMove = this.specialMove.nextMove;
        this.specialIntentImg = getIntentImg();
        this.specialIntentParticleTimer = 0.5F;

        this.updateSpecialIntentTip();
    }

    @Override
    protected void setHp(int hp) {
        this.maxHealth = this.currentHealth = hp;
    }

    protected void setSpecialMove(byte nextMove, AbstractMonster.Intent intent, int baseDamage) {
        this.specialMove = new EnemyMoveInfo(nextMove, intent, baseDamage, 0, false);
    }

    protected void addTip() {
        if (this.intentAlphaTarget == 1.0F && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractMonster.Intent.NONE) {
            this.tips.add(this.specialIntentTip);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        for(DamageInfo info : this.specialDamage) {
            info.applyPowers(this, AbstractDungeon.player);
            if (AbstractDungeon.player.hasPower("Surrounded") && (AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX || !AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX)) {
                info.output = (int)((float)info.output * 1.5F);
            }
        }
        if(this.specialMove.baseDamage > 0) {
            AbstractCreature target = AbstractDungeon.player;
            int dmg = this.specialMove.baseDamage;
            float tmp = (float)dmg;
            if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
                float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
                tmp *= mod;
            }

            DamageType type = this.hasPower(MagicAttackPower.POWER_ID) ? DamageType.THORNS : DamageType.NORMAL;

            AbstractPower p;
            Iterator var6;
            for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, type)) {
                p = (AbstractPower)var6.next();
            }

            for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageReceive(tmp, type)) {
                p = (AbstractPower)var6.next();
            }

            tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, type);

            for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, type)) {
                p = (AbstractPower)var6.next();
            }

            for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalReceive(tmp, type)) {
                p = (AbstractPower)var6.next();
            }

            dmg = MathUtils.floor(tmp);
            if (dmg < 0) {
                dmg = 0;
            }

            this.specialIntentDmg = dmg;
        }

        this.updateSpecialIntentTip();
    }

    @SpireOverride
    protected void calculateDamage(int dmg) {
        AbstractCreature target = AbstractDungeon.player;
        float tmp = (float)dmg;
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
            float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
            tmp *= mod;
        }

        DamageType type = this.hasPower(MagicAttackPower.POWER_ID) ? DamageType.THORNS : DamageType.NORMAL;

        AbstractPower p;
        Iterator var6;
        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, type)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageReceive(tmp, type)) {
            p = (AbstractPower)var6.next();
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, type);
        if (AbstractDungeon.player.hasPower("Surrounded") && (AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX || !AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX)) {
            tmp = (float)((int)(tmp * 1.5F));
        }

        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, type)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalReceive(tmp, type)) {
            p = (AbstractPower)var6.next();
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        //this.intentDmg = dmg;
        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }

    @Override
    public void die() {
        super.die();
        this.state.setAnimation(0, "Die", false);
    }

    private void updateSpecialIntentTip() {
        switch(this.specialIntent) {
            case ATTACK:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[0];
                this.specialIntentTip.body = TEXT[4] + this.specialIntentDmg + TEXT[5];

                this.specialIntentTip.img = ImageMaster.INTENT_ATK_2;
                break;
            case ATTACK_BUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[6];
                this.specialIntentTip.body = TEXT[9] + this.specialIntentDmg + TEXT[5];

                this.specialIntentTip.img = ImageMaster.INTENT_ATTACK_BUFF;
                break;
            case ATTACK_DEBUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[10];
                this.specialIntentTip.body = TEXT[11] + this.specialIntentDmg + TEXT[5];
                this.specialIntentTip.img = ImageMaster.INTENT_ATTACK_DEBUFF;
                break;
            case ATTACK_DEFEND:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[0];
                this.specialIntentTip.body = TEXT[12] + this.specialIntentDmg + TEXT[5];

                this.specialIntentTip.img = ImageMaster.INTENT_ATTACK_DEFEND;
                break;
            case BUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[10];
                this.specialIntentTip.body = TEXT[19];
                this.specialIntentTip.img = ImageMaster.INTENT_BUFF;
                break;
            case DEBUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[10];
                this.specialIntentTip.body = TEXT[20];
                this.specialIntentTip.img = ImageMaster.INTENT_DEBUFF;
                break;
            case STRONG_DEBUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[10];
                this.specialIntentTip.body = TEXT[21];
                this.specialIntentTip.img = ImageMaster.INTENT_DEBUFF2;
                break;
            case DEFEND:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[13];
                this.specialIntentTip.body = TEXT[22];
                this.specialIntentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            case DEFEND_DEBUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[13];
                this.specialIntentTip.body = TEXT[23];
                this.specialIntentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            case DEFEND_BUFF:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[13];
                this.specialIntentTip.body = TEXT[24];
                this.specialIntentTip.img = ImageMaster.INTENT_DEFEND_BUFF;
                break;
            case ESCAPE:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[14];
                this.specialIntentTip.body = TEXT[25];
                this.specialIntentTip.img = ImageMaster.INTENT_ESCAPE;
                break;
            case MAGIC:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[15];
                this.specialIntentTip.body = TEXT[26];
                this.specialIntentTip.img = ImageMaster.INTENT_MAGIC;
                break;
            case SLEEP:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[16];
                this.specialIntentTip.body = TEXT[27];
                this.specialIntentTip.img = ImageMaster.INTENT_SLEEP;
                break;
            case STUN:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[17];
                this.specialIntentTip.body = TEXT[28];
                this.specialIntentTip.img = ImageMaster.INTENT_STUN;
                break;
            case UNKNOWN:
                this.specialIntentTip.header = SPECIAL_TEXT[0] + TEXT[18];
                this.specialIntentTip.body = TEXT[29];
                this.specialIntentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            case NONE:
                this.specialIntentTip.header = "";
                this.specialIntentTip.body = "";
                this.specialIntentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            default:
                this.specialIntentTip.header = "NOT SET";
                this.specialIntentTip.body = "NOT SET";
                this.specialIntentTip.img = ImageMaster.INTENT_UNKNOWN;
        }
    }

    private Texture getIntentImg() {
        switch(this.specialIntent) {
            case ATTACK:
            case ATTACK_BUFF:
            case ATTACK_DEBUFF:
            case ATTACK_DEFEND:
                return this.getAttackIntent();
            case BUFF:
                return ImageMaster.INTENT_BUFF_L;
            case DEBUFF:
                return ImageMaster.INTENT_DEBUFF_L;
            case STRONG_DEBUFF:
                return ImageMaster.INTENT_DEBUFF2_L;
            case DEFEND:
            case DEFEND_DEBUFF:
                return ImageMaster.INTENT_DEFEND_L;
            case DEFEND_BUFF:
                return ImageMaster.INTENT_DEFEND_BUFF_L;
            case ESCAPE:
                return ImageMaster.INTENT_ESCAPE_L;
            case MAGIC:
                return ImageMaster.INTENT_MAGIC_L;
            case SLEEP:
                return ImageMaster.INTENT_SLEEP_L;
            case STUN:
                return null;
            default:
                return ImageMaster.INTENT_UNKNOWN_L;
        }
    }

    public void addAttackCoolDown(int num){
        this.currentAttackCoolDown += num;
        if(this.currentAttackCoolDown >= this.attackCoolDown && !this.isDeadOrEscaped() && !this.isDead){
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if(currentAttackCoolDown >= attackCoolDown && !isDying && !isEscaping && !halfDead && !isDead) {
                        currentAttackCoolDown = 0;
                        specialTakeTurn();
                        specialGetMove(AbstractDungeon.aiRng.random(99));
                    }
                    this.isDone = true;
                }
            });
        }
    }

    public void showAttackCoolDown(int num) {
        if(this.attackCoolDown - this.currentAttackCoolDown <= num) {
            this.fontScale = 0.9F;
            this.fontColor = Color.CORAL.cpy();
        }else {
            this.fontScale = 0.6F;
            this.fontColor = Color.WHITE.cpy();
        }
    }

    protected void movePosition(float x, float y) {
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.animX = 0.0F;
        this.animY = 0.0F;
        this.refreshHitboxLocation();
    }

    protected void moveTo(float x, float y) {
        this.playMoveAnim();
        this.targetX = x;
        this.targetY = y;
        this.isMoving = true;
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player.hoveredCard == null) showAttackCoolDown(-1);
        this.updatePosition();
        updateSpecialIntent();
    }

    @Override
    protected void updateHitbox(float hb_x, float hb_y, float hb_w, float hb_h) {
        super.updateHitbox(hb_x, hb_y, hb_w, hb_h);

    }

    private void updateSpecialIntent() {
        this.bobEffect.update();
        this.specialIntentHb.move(this.hb.cX + 70.0F + this.intentOffsetX, this.hb.cY + this.hb_h / 2.0F + 32.0F * Settings.scale);

        if (!this.isDying && !this.isEscaping) {
            this.updateSpecialIntentVFX();
        }

        Iterator i = this.specialIntentVfx.iterator();

        while(i.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
    }

    private void updateSpecialIntentVFX() {
        if (this.intentAlpha > 0.0F) {
            if (this.specialIntent != AbstractMonster.Intent.ATTACK_DEBUFF && this.specialIntent != AbstractMonster.Intent.DEBUFF && this.specialIntent != AbstractMonster.Intent.STRONG_DEBUFF && this.specialIntent != AbstractMonster.Intent.DEFEND_DEBUFF) {
                if (this.specialIntent != AbstractMonster.Intent.ATTACK_BUFF && this.specialIntent != AbstractMonster.Intent.BUFF && this.specialIntent != AbstractMonster.Intent.DEFEND_BUFF) {
                    switch (this.specialIntent) {
                        case ATTACK_DEFEND:
                            this.specialIntentParticleTimer -= Gdx.graphics.getDeltaTime();
                            if (this.specialIntentParticleTimer < 0.0F) {
                                this.specialIntentParticleTimer = 0.5F;
                                this.specialIntentVfx.add(new ShieldParticleEffect(this.specialIntentHb.cX, this.specialIntentHb.cY));
                            }
                            break;
                        case UNKNOWN:
                            this.specialIntentParticleTimer -= Gdx.graphics.getDeltaTime();
                            if (this.specialIntentParticleTimer < 0.0F) {
                                this.specialIntentParticleTimer = 0.5F;
                                this.specialIntentVfx.add(new UnknownParticleEffect(this.specialIntentHb.cX, this.specialIntentHb.cY));
                            }
                            break;
                        case STUN:
                            this.specialIntentParticleTimer -= Gdx.graphics.getDeltaTime();
                            if (this.specialIntentParticleTimer < 0.0F) {
                                this.specialIntentParticleTimer = 0.67F;
                                this.specialIntentVfx.add(new StunStarEffect(this.specialIntentHb.cX, this.specialIntentHb.cY));
                            }
                            break;
                    }
                } else {
                    this.specialIntentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.specialIntentParticleTimer < 0.0F) {
                        this.specialIntentParticleTimer = 0.1F;
                        this.specialIntentVfx.add(new BuffParticleEffect(this.specialIntentHb.cX, this.specialIntentHb.cY));
                    }
                }
            } else {
                this.specialIntentParticleTimer -= Gdx.graphics.getDeltaTime();
                if (this.specialIntentParticleTimer < 0.0F) {
                    this.specialIntentParticleTimer = 1.0F;
                    this.specialIntentVfx.add(new DebuffParticleEffect(this.specialIntentHb.cX, this.specialIntentHb.cY));
                }
            }
        }
    }

    private void updatePosition() {
        if(isMoving) {
            if(this.targetX != this.drawX) {
                if(Math.abs(this.targetX - this. drawX) < 5.0F) {
                    this.drawX = MathUtils.lerp(this.drawX, this.targetX, 0.6F);
                }else{
                    if(this.targetX > this.drawX) {
                        this.drawX += this.speed * Gdx.graphics.getDeltaTime();
                    }else{
                        this.drawX -= this.speed * Gdx.graphics.getDeltaTime();
                    }
                }
                this.movePosition(this.drawX, this.drawY);
            }else if(this.targetY != this.drawY) {
                if(Math.abs(this.targetY - this. drawY) < 5.0F) {
                    this.drawY = MathUtils.lerp(this.drawY, this.targetY, 0.5F);
                }else{
                    if(this.targetY > this.drawY) {
                        this.drawY += this.speed * Gdx.graphics.getDeltaTime();
                    }else{
                        this.drawY -= this.speed * Gdx.graphics.getDeltaTime();
                    }
                }
                this.movePosition(this.drawX, this.drawY);
            }
            if(this.targetX == this.drawX && this.targetY == this.drawY) {
                this.playIdleAnim();
                this.isMoving = false;
            }
        }

    }

    @SpireOverride
    protected void renderDamageRange(SpriteBatch sb) {
        SpireSuper.call(new Object[]{sb});
        this.renderStat(sb);
        if (this.specialIntent.name().contains("ATTACK")) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.specialIntentDmg), this.specialIntentHb.cX - 30.0F * Settings.scale, this.specialIntentHb.cY + this.bobEffect.y - 12.0F * Settings.scale, this.StatColor);
        }
    }

    @SpireOverride
    protected void renderIntentVfxBehind(SpriteBatch sb) {
        SpireSuper.call(new Object[]{sb});
        for (AbstractGameEffect e : this.specialIntentVfx) {
            if (e.renderBehind) {
                e.render(sb);
            }
        }
    }

    @SpireOverride
    protected void renderIntent(SpriteBatch sb) {
        SpireSuper.call(new Object[]{sb});
        if (this.specialIntentImg != null && this.specialIntent != AbstractMonster.Intent.UNKNOWN) {
            if (this.intent != AbstractMonster.Intent.DEBUFF && this.intent != AbstractMonster.Intent.STRONG_DEBUFF) {
                this.specialIntentAngle = 0.0F;
            } else {
                this.specialIntentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
            }
            sb.draw(this.specialIntentImg, specialIntentHb.cX - 64.0F, specialIntentHb.cY - 64.0F + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.specialIntentAngle, 0, 0, 128, 128, false, false);
        }
    }

    @SpireOverride
    protected void renderIntentVfxAfter(SpriteBatch sb) {
        SpireSuper.call(new Object[]{sb});
        for (AbstractGameEffect e : this.specialIntentVfx) {
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
    }

    @SpireOverride
    protected void renderName(SpriteBatch sb) {
        SpireSuper.call(new Object[]{sb});
    }

    private void renderStat(SpriteBatch sb){
        float x = specialIntentHb.cX;
        float y = specialIntentHb.cY;
        sb.setColor(this.StatColor);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, this.currentAttackCoolDown + "/" + this.attackCoolDown, x + 18.0F * Settings.scale, y, fontColor, fontScale);

    }

    public enum EnemyTag {
        YETI,
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "renderTip"
    )
    public static class RenderTipPatch {
        public RenderTipPatch() {}

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractMonster _inst, SpriteBatch sb){
            if(_inst instanceof AbstractEnemy) {
                ((AbstractEnemy) _inst).addTip();
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }

}
