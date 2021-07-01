package ArknightsMod.Operators;

import ArknightsMod.Actions.OperatorsDamageAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Skills.Empty;
import ArknightsMod.Powers.Monster.MagicAttackPower;
import ArknightsMod.Powers.Operator.AbstractArkPower;
import ArknightsMod.Powers.Operator.CantAttackPower;
import ArknightsMod.Powers.Operator.ResummonPower;
import ArknightsMod.Powers.Operator.SolidnessPower;
import ArknightsMod.Vfx.Common.GainAttackEffect;
import ArknightsMod.Vfx.UI.AttackTargetEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractOperator extends AbstractCreature {
    private static final Logger logger = LogManager.getLogger(AbstractOperator.class);
    private float hoverTimer = 0.0F;
    private float deathTimer = 0.0F;
    private float skillBarAnimTimer = 0.0F;
    private float skillHideTimer = 0.0F;
    private float glowTimer = 0.0F;
    private float escapeButtonTimer = 0.0F;
    private float skillBarWidth;
    private float targetSkillBarWidth;
    private float escapeBtnAlpha;
    private float escapeBtnX;
    private float escapeBtnSize;
    private float fontScale;
    private Color nameColor;
    private Color nameBgColor;
    private Color ShbBgColor;
    private Color ShbShadowColor;
    private Color ShbTextColor;
    private Color bgGreenBarColor;
    private Color bgOrangeBarColor;
    private Color greenBarColor;
    private Color orangeBarColor;
    private Color StatColor;
    private Color CDFontColor;
    private boolean tintFadeOutCalled;
    public Hitbox skillHb;
    public Hitbox skillReadyHb;
    private Hitbox escapeHb;
    protected List<Disposable> disposables;
    private BobEffect bobEffect;

    public int Atk;
    public int def;
    private int showAtk;
    public int attackTimes = 1;
    public int attackTargets = 1;
    public boolean attackToAll = false;
    public int attackCoolDown;
    private int currentAttackCoolDown;
    public int currentSkill;
    private int maxSkill;
    public int resummonTime;
    public int cost;
    public boolean isHealer;
    protected boolean showTarget;
    public boolean canAttack;
    public boolean canPlayAttack = true;
    public DamageType damageType;
    public AbstractGameAction.AttackEffect attackEffect;
    private AttackTargetEffect showAtkEffect;

    public boolean canPlayStart = true;
    public boolean isMelee;
    private boolean isToEscape = false;
    public AbstractCreature lastTarget = null;

    private ArrayList<AbstractGameEffect> effects;
    public ArrayList<AbstractSkill> skills;
    public AbstractSkill currentBattleSkill;
    public int skillindex;

    public int level;
    public OperatorType operatorType;
    public CardStrings cardStrings;
    private String[] texts;

    public int index;

    private static final float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
    private static final float HEALTH_BG_OFFSET_X = 31.0F * Settings.scale;
    private static final float HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
    private static final float HEALTH_TEXT_OFFSET_Y = 6.0F * Settings.scale;
    private static final float INTENT_HB_W = 64.0F * Settings.scale;

    public static final OperatorType[] OPERATOR_TYPES = OperatorType.values();
    private boolean isShowingAtk = false;


    public AbstractOperator(String id, String atlas, String json,int Atk, int attackCoolDown, int maxHP, int def, int resummonTime, int level, OperatorType operatorType, float hb_x, float hb_y){
        this.nameColor = new Color();
        this.nameBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.ShbBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.ShbShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.ShbTextColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.greenBarColor = new Color(0.25F, 0.5F, 0.25F, 0.0F);
        this.orangeBarColor = new Color(1.0F, 0.5F, 0.0F, 0.0F);
        this.bgGreenBarColor = new Color(0.5F, 1.0F, 0.5F, 0.0F);
        this.bgOrangeBarColor = new Color(1.0F, 0.7F, 0.5F ,0.0F);
        this.StatColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.CDFontColor = Color.WHITE.cpy();
        this.tintFadeOutCalled = false;
        this.disposables = new ArrayList<>();
        this.escapeBtnAlpha = 0.0F;
        this.escapeBtnX = 0.0F;
        this.escapeBtnSize = 1.0F;
        this.fontScale = 0.6F;

        this.id = id;
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.texts = this.cardStrings.EXTENDED_DESCRIPTION;
        this.name = cardStrings.NAME;
        this.isPlayer = true;
        this.level = level;
        this.operatorType = operatorType;
        this.isHealer = this.operatorType == OperatorType.MEDIC;
        this.showTarget = !this.isHealer;
        this.isMelee = !(this.operatorType != OperatorType.GUARD && this.operatorType != OperatorType.VANGUARD && this.operatorType != OperatorType.DEFENDER && this.operatorType != OperatorType.SPECIALIST);
        if(CardCrawlGame.isInARun()) {
            if (this.operatorType == OperatorType.CASTER || this.operatorType == OperatorType.SUPPORTER)
                this.damageType = DamageType.THORNS;
            else
                this.damageType = DamageType.NORMAL;
        }
        this.canAttack = true;
        this.currentAttackCoolDown = 0;
        this.attackCoolDown = attackCoolDown;
        this.showAtk = this.Atk = Atk;
        this.def = def;
        this.maxHealth = this.currentHealth = maxHP;
        this.resummonTime = resummonTime;
        this.attackEffect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        this.skills = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.bobEffect = new BobEffect();
        this.showAtkEffect = new AttackTargetEffect();

        this.loadAnimation(atlas, json, 2.2F);
        this.stateData.setDefaultMix(0.1F);

        this.hb_w = 135.0F * Settings.scale;
        this.hb_h = 150.0F * Settings.scale;
        this.hb_x = hb_x * Settings.scale;
        this.hb_y = hb_y * Settings.scale;
        this.hb = new Hitbox(120.0F * Settings.scale, 130.0F * Settings.scale);
        this.healthHb = new Hitbox(this.hb_w * Settings.scale, 72.0F * Settings.scale);
        this.skillHb = new Hitbox(this.hb_w * Settings.scale, 72.0F * Settings.scale);
        this.skillReadyHb = new Hitbox(INTENT_HB_W, INTENT_HB_W);
        this.escapeHb = new Hitbox(INTENT_HB_W, INTENT_HB_W);
        this.index = -1;

        this.refreshHitboxLocation();
    }

    public void unhover() {
        this.healthHb.hovered = false;
        this.skillHb.hovered = false;
        this.hb.hovered = false;
        this.skillReadyHb.hovered = false;
        this.escapeHb.hovered = false;
    }

    public void getSkillOwner(){
        if(this.currentBattleSkill != null){
            this.currentBattleSkill.init();
            this.currentBattleSkill.owner = this;
            this.maxSkill = this.currentBattleSkill.maxSkill;
            this.currentSkill = this.currentBattleSkill.initSkill;
            if(this.currentSkill == this.maxSkill){
                this.currentBattleSkill.canSpell = true;
            }
        }
    }

    @Override
    protected void refreshHitboxLocation() {
        super.refreshHitboxLocation();
        this.skillHb.move(this.hb.cX, this.hb.cY - this.hb_h / 2.0F - this.healthHb.height / 2.0F - 26.0F * Settings.scale);
        this.skillReadyHb.move(this.hb.cX, this.hb.cY + this.hb_h / 2.0F + INTENT_HB_W / 2.0F);
        this.escapeHb.move(this.hb.cX - this.hb_w / 2.0F - 35.0F * Settings.scale, this.hb.cY);
    }

    public void movePosition(float x, float y) {
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.animX = 0.0F;
        this.animY = 0.0F;
        this.refreshHitboxLocation();
    }

    public void usePreBattleAction(){

    }

    public void playStartAnim(){
        this.state.setAnimation(0, "Start", false);
        this.playIdleAnim();
    }

    public String playAttackAnim(){
        return "Attack";
    }

    public String playAttackBeginAnim() {return null;}

    public String playAttackEndAnim() {return null;}

    public void playIdleAnim(){
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public void playDeathAnim(){
        this.state.setAnimation(0, "Die", false);
    }

    public void playSkillAnim(){
        this.state.setAnimation(0, "Skill", false);
    }


    public void UseWhenSummoned(){
        this.playStartSfx();
        //this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, Atk), Atk));
        //this.addAttack(Atk);
        AbstractCreature m = getAttackTarget();
        if(m != null) {
            this.lastTarget = m;
        } else {
            this.lastTarget = GeneralHelper.getRandomMonsterSafe();
        }
        for(AbstractOperator o:OperatorGroup.GetOperators()){
            o.OtherOperatorEnter(this);
        }
        if(currentBattleSkill != null){
            currentBattleSkill.useWhenSummoned();
        }

        if(def > 0) {
            addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, def)));
        }

        skillBarUpdatedEvent();
    }

    public void StartOfTurnAction(){
        for(AbstractPower p:powers){
            p.atStartOfTurn();
        }
        if(currentBattleSkill != null && currentBattleSkill.isSpelling){
            currentBattleSkill.onStartOfTurn();
        }
    }

    public void EndOfTurnAction(){
        for(AbstractPower p:powers){
            p.atEndOfTurn(isPlayer);
            p.atEndOfRound();
            p.atEndOfTurnPreEndTurnCards(true);
        }
        if(currentBattleSkill != null && currentBattleSkill.isSpelling){
            currentBattleSkill.onEndOfTurn();
        }
    }

    public void OtherOperatorEnter(AbstractOperator o){}

    public void UseWhenEscape(){}

    public float UseWhenHeal(AbstractCreature t, AbstractCreature s, float amt){
        return amt;
    }

    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt){return amt;}

    public float OtherOperatorDamage(float dmg, DamageInfo info) {return dmg;}

    public void AfterAttack() {}

    private void Resummon(int amount){
        if(amount > 0){
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResummonPower(AbstractDungeon.player, amount, getOperatorCard())));
        }
    }

    public void ChangeDamageType(DamageType type) {
        if(type == DamageType.NORMAL) {
            this.damageType = type;
            if(this.hasPower(MagicAttackPower.POWER_ID)) {
                this.addToBot(new RemoveSpecificPowerAction(this, this, MagicAttackPower.POWER_ID));
            }
        }else if(type == DamageType.THORNS) {
            this.damageType = type;
            if(!this.hasPower(MagicAttackPower.POWER_ID)) {
                this.addToBot(new ApplyPowerAction(this, this, new MagicAttackPower(this)));
            }
        }
    }

    public void Attack(){
        if(!this.hasPower(CantAttackPower.POWER_ID)) {
            if(currentBattleSkill == null || currentBattleSkill instanceof Empty){
                Iterator var5 = this.powers.iterator();
                AbstractPower p;
                String anim = playAttackAnim();
                while(var5.hasNext()) {
                    p = (AbstractPower) var5.next();
                    if(p instanceof AbstractArkPower){
                        ((AbstractArkPower) p).onOperatorAttack();
                    }
                }
                this.addToBot(new OperatorsDamageAction(this, anim, attackToAll));
                logger.info(this.name + "无技能发动攻击");
            }else if(!currentBattleSkill.Attack()){
                Iterator var5 = this.powers.iterator();
                AbstractPower p;
                String anim = playAttackAnim();
                while(var5.hasNext()) {
                    p = (AbstractPower) var5.next();
                    if(p instanceof AbstractArkPower){
                        ((AbstractArkPower) p).onOperatorAttack();
                    }
                }
                if(currentBattleSkill.isSpelling){
                    anim = currentBattleSkill.getSkillAnim();
                }
                this.addToBot(new OperatorsDamageAction(this, anim, attackToAll));
                if(currentBattleSkill.skillType == AbstractSkill.SkillType.ATTACK && !currentBattleSkill.isSpelling){
                    this.changeSkillPoints(1);
                }
                logger.info(this.name + "发动攻击");
            }
        }
    }

    public void addAttack(int i) {
        GeneralHelper.addEffect(new GainAttackEffect());
        this.Atk += i;
        this.showAtk = this.Atk;
    }

    public int getAttackToTarget() {
        float atk = Atk;

        for(AbstractPower p : this.powers) atk = p.atDamageGive(atk, damageType);

        for(AbstractPower p : this.powers) atk = p.atDamageFinalGive(atk, damageType);

        if(this.currentBattleSkill != null) {
            atk = this.currentBattleSkill.onAttack(atk);
        }

            if(atk <= 0.0F) atk = 0.0F;

        return MathUtils.floor(atk);
    }

    public int[] getAttackToAll() {
        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
        float[] atk = new float[m.size()];

        for (int i = 0; i < atk.length; i++) {
            atk[i] = Atk;
//            if(this.hasPower("Strength"))
//                atk[i] = Math.max(0, this.getPower("Strength").amount);
//            else
//                atk[i] = 0;

            for(AbstractPower p : this.powers) atk[i] = p.atDamageGive(atk[i], damageType);

            for(AbstractPower p : this.powers) atk[i] = p.atDamageFinalGive(atk[i], damageType);

            if(this.currentBattleSkill != null) {
                atk[i] = this.currentBattleSkill.onAttack(atk[i]);
            }

            if(atk[i] <= 0.0F) atk[i] = 0.0F;
        }

        int[] tmp = new int[m.size()];
        for (int j = 0; j < atk.length; j++) {
            tmp[j] = MathUtils.floor(atk[j]);
        }

        return tmp;
    }

    public AbstractCreature getAttackTarget(){
        if(lastTarget != null) {
            if(!lastTarget.isDeadOrEscaped() && !lastTarget.isDead) {
                return lastTarget;
            }else {
                lastTarget = null;
            }
        }
        return lastTarget;
    }

    public AbstractGameEffect playAttackBulletEffect(AbstractCreature creature){
        return null;
    }

    public void playStartSfx(){}

    public void playSkillSfx(){}

    public void die(){
        if(!this.isDying){
            this.isDying = true;
            playDeathAnim();
            CardCrawlGame.sound.play("char_dead");
            this.deathTimer++;
            this.Resummon(resummonTime <= 0 ? 0 : resummonTime + 1);
            if(this.currentBattleSkill != null) this.currentBattleSkill.EndEffect();
            this.UseWhenEscape();
        }
    }

    private void escape(){
        if(!this.isDying){
            this.isDying = true;
            this.deathTimer++;
            this.Resummon(resummonTime);
            if(this.currentBattleSkill != null) this.currentBattleSkill.EndEffect();
            if(getOperatorCard().cost > 0) this.addToBot(new GainEnergyAction(1));
            this.UseWhenEscape();
        }
    }

    public abstract AbstractOperator makeCopy();

    public AbstractOperatorCard getOperatorCard(){
        return null;
    }

    public void changeSkillPoints(int sp){
        if(!this.isDying && this.currentBattleSkill != null){
            int tmp = sp;

            if (tmp <= 0 || !this.hasPower(CantAttackPower.POWER_ID)) {
                this.currentSkill += tmp;
                if(this.currentSkill > this.maxSkill) this.currentSkill = this.maxSkill;
            }
            if(!this.currentBattleSkill.isSpelling && this.currentSkill >= this.maxSkill && this.currentBattleSkill.skillType != AbstractSkill.SkillType.PASSIVE){
                this.currentSkill = this.maxSkill;
                if(currentBattleSkill.isAutomatic){
                    activeSkill();
                }else{
                    currentBattleSkill.canSpell = true;
                }
            }

            if(this.currentBattleSkill.isSpelling && this.currentSkill <= 0){
                endSkill();
            }
        }
        this.skillBarUpdatedEvent();
    }


    public void activeSkill(){
        this.currentBattleSkill.isSpelling = true;
        this.currentSkill = this.maxSkill = this.currentBattleSkill.skillTimes;
        currentBattleSkill.ActiveEffect();
        if(this.currentBattleSkill.skillType == AbstractSkill.SkillType.ATTACK) {
            if(MathUtils.randomBoolean()) {
                this.playSkillSfx();
            }
        }else{
            this.playSkillSfx();
        }
    }

    private void endSkill(){
        this.currentBattleSkill.isSpelling = false;
        this.maxSkill = this.currentBattleSkill.maxSkill;
        this.currentSkill = 0;
        currentBattleSkill.EndEffect();
    }

    public void addAttackCoolDown(int num){
        this.currentAttackCoolDown += num;
        if(this.currentAttackCoolDown >= this.attackCoolDown){
            this.currentAttackCoolDown = 0;
            this.Attack();
        }
    }

    public void changeMaxAttackCoolDown(int num){
        this.attackCoolDown += num;
        if(this.attackCoolDown <= 0) this.attackCoolDown = 1;
        if(this.currentAttackCoolDown >= this.attackCoolDown){
            this.currentAttackCoolDown = 0;
            this.Attack();
        }
    }

    public void showAttackCoolDown(int num) {
        if(!GeneralHelper.isAlive(lastTarget)) {
            AbstractCreature m = getAttackTarget();
            if(m != null) {
                this.lastTarget = m;
            } else {
                this.lastTarget = GeneralHelper.getRandomMonsterSafe();
            }
        }
        if(lastTarget != null) {
            if(this.attackCoolDown - this.currentAttackCoolDown <= num && !isShowingAtk) {
                this.fontScale = 0.75F;
                this.CDFontColor = Color.CYAN.cpy();
                this.showAtk = getAttackToTarget();
                this.isShowingAtk = true;
                if(showTarget) {
                    showAtkEffect = new AttackTargetEffect();
                    showAtkEffect.set(lastTarget.hb.cX, lastTarget.hb.cY, hb.cX, hb.cY);
                    AbstractDungeon.effectList.add(showAtkEffect);
                }
            }else if(isShowingAtk){
                this.fontScale = 0.6F;
                this.CDFontColor = Color.WHITE.cpy();
                this.showAtk = this.Atk;
                this.isShowingAtk = false;
                if(showTarget && showAtkEffect != null) {
                    showAtkEffect.isDone = true;
                }
            }
        }
    }

    public void damage(DamageInfo info) {
        float damageAmount = info.output;
        if (!this.isDying && !this.isToEscape) {
            if (damageAmount < 0) {
                damageAmount = 0;
            }

            boolean hadBlock = true;
            if (this.currentBlock == 0) {
                hadBlock = false;
            }

            boolean weakenedToZero = damageAmount == 0;
            damageAmount = this.decrementBlock(info, (int)damageAmount);

            AbstractPower p;
            Iterator var6;

            for(var6 = this.powers.iterator(); var6.hasNext(); damageAmount = p.atDamageFinalReceive(damageAmount, info.type)) {
                p = (AbstractPower)var6.next();
            }

            for(var6 = info.owner.powers.iterator(); var6.hasNext(); damageAmount = p.atDamageFinalGive(damageAmount, info.type)) {
                p = (AbstractPower)var6.next();
            }
            
            Iterator var5;

            if (info.owner != null) {
                for(var5 = info.owner.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackToChangeDamage(info, (int)damageAmount)) {
                    p = (AbstractPower)var5.next();
                }
            }

            logger.info(name + "受到的伤害：" + damageAmount);
            for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackedToChangeDamage(info, (int)damageAmount)) {
                p = (AbstractPower)var5.next();
            }

            if(currentBattleSkill != null && currentBattleSkill.isSpelling){
                damageAmount = (int)currentBattleSkill.onDamage(damageAmount);
            }

            var5 = this.powers.iterator();

            while(var5.hasNext()) {
                p = (AbstractPower)var5.next();
                p.wasHPLost(info, (int)damageAmount);
            }

            if (info.owner != null) {
                var5 = info.owner.powers.iterator();

                while(var5.hasNext()) {
                    p = (AbstractPower)var5.next();
                    p.onAttack(info, (int)damageAmount, this);
                }
            }

            for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttacked(info, (int)damageAmount)) {
                p = (AbstractPower)var5.next();
            }

            damageAmount = onDamage(info, (int)damageAmount);

            for(AbstractPower p1 : powers) {
                if(p1 instanceof AbstractArkPower) damageAmount = ((AbstractArkPower) p1).onReceiveFatalDamage(damageAmount);
            }

            this.lastDamageTaken = Math.min((int)damageAmount, this.currentHealth);
            boolean probablyInstantKill = this.currentHealth == 0;
            if (damageAmount > 0) {
                if (info.owner != this) {
                    this.useStaggerAnimation();
                }

                this.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, (int)damageAmount));
                }

                if (this.currentHealth < 0) {
                    this.currentHealth = 0;
                }

                this.healthBarUpdatedEvent();
            } else if (!probablyInstantKill) {
                if (weakenedToZero && this.currentBlock == 0) {
                    if (hadBlock) {
                        AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[1]));
                    } else {
                        AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                    }
                } else if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[1]));
                }
            }

            if(this.currentHealth >= 0) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    if(o.currentBattleSkill != null) o.currentBattleSkill.onOtherOperatorDamage(this);
                }
            }

            if (this.currentHealth <= 0) {
                this.die();

                if (this.currentBlock > 0) {
                    this.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
                }
            }

        }
    }

    protected float onDamage(DamageInfo info, int amt) {
        logger.info( this.name + "受到攻击");
        float tmp = amt;
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            tmp = o.OtherOperatorDamage(tmp, info);
        }
        if(info.type != DamageType.HP_LOSS && tmp > 0) {
            if (currentBattleSkill != null && currentBattleSkill.skillType == AbstractSkill.SkillType.HIT)
                this.changeSkillPoints(1);
        }

        return (int)tmp;
    }

    public void render(SpriteBatch sb) {
        if (!this.isDead) {
            this.renderPower(sb);
            if (this.atlas != null) {
                this.state.update(Gdx.graphics.getDeltaTime());
                this.state.apply(this.skeleton);
                this.skeleton.updateWorldTransform();
                this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY);
                this.skeleton.setColor(this.tint.color);
                this.skeleton.setFlip(AbstractDungeon.player.flipHorizontal, this.flipVertical);
                sb.end();
                CardCrawlGame.psb.begin();
                sr.draw(CardCrawlGame.psb, this.skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
                sb.setBlendFunction(770, 771);
            }

            this.hb.render(sb);
            if (!AbstractDungeon.player.isDead) {
                this.escapeHb.render(sb);
                this.healthHb.render(sb);
                this.skillReadyHb.render(sb);
                this.skillHb.render(sb);
                this.renderEscape(sb);
                this.renderSkill(sb);
                this.renderHealth(sb);
                this.renderName(sb);
                this.renderReadySkill(sb);
                this.renderStat(sb);
            }
        }

    }

    @SpireOverride
    protected void renderPowerIcons(SpriteBatch sb, float x, float y) {
        float offset = 10.0F * Settings.scale;
        float offsety = this.maxSkill > 0 ? 74.0F : 48.0F;
        float offsety2 = this.maxSkill > 0 ? 92.0F : 66.0F;

        Iterator var5;
        AbstractPower p;
        for(var5 = this.powers.iterator(); var5.hasNext(); offset += 48.0F * Settings.scale) {
            p = (AbstractPower)var5.next();
            p.renderIcons(sb, x + offset, y - offsety * Settings.scale, this.ShbTextColor);
        }

        offset = 0.0F * Settings.scale;

        for(var5 = this.powers.iterator(); var5.hasNext(); offset += 48.0F * Settings.scale) {
            p = (AbstractPower)var5.next();
            p.renderAmount(sb, x + offset + 26.0F * Settings.scale, y - offsety2 * Settings.scale, this.ShbTextColor);
        }
    }

    private void updatePower(){
        if(currentBattleSkill != null && currentBattleSkill.isSpelling){
            this.glowTimer -= Gdx.graphics.getDeltaTime();
            if (this.glowTimer < 0.0F) {
                this.effects.add(currentBattleSkill.skillEffect());
                this.glowTimer = 0.3F;
            }
        }

//        Iterator<AbstractGameEffect> var3 = this.effects.iterator();
//        while(var3.hasNext()) {
//            AbstractGameEffect e = var3.next();
//            if (e.isDone) {
//                var3.remove();
//            }
//            e.update();
//        }

        for(int i = effects.size() - 1 ; i > 0 ; i--){
            AbstractGameEffect effect = effects.get(i);
            effect.update();
            if(effect.isDone){
                effects.remove(effect);
            }
        }
    }

    private void renderPower(SpriteBatch sb){
        for(int i = effects.size() - 1 ; i > 0 ; i--){
            AbstractGameEffect effect = effects.get(i);
            effect.render(sb);
        }
    }

    private void renderName(SpriteBatch sb) {
        if (!this.hb.hovered) {
            this.hoverTimer = MathHelper.fadeLerpSnap(this.hoverTimer, 0.0F);
        } else {
            this.hoverTimer += Gdx.graphics.getDeltaTime();
        }

        if ((!AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.hoveredCard == null || AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY) && !this.isDying) {
            if (this.hoverTimer != 0.0F) {
                if (this.hoverTimer * 2.0F > 1.0F) {
                    this.nameColor.a = 1.0F;
                } else {
                    this.nameColor.a = this.hoverTimer * 2.0F;
                }
            } else {
                this.nameColor.a = MathHelper.slowColorLerpSnap(this.nameColor.a, 0.0F);
            }

            float tmp = Interpolation.exp5Out.apply(1.5F, 2.0F, this.hoverTimer);
            this.nameColor.r = Interpolation.fade.apply(Color.DARK_GRAY.r, Settings.CREAM_COLOR.r, this.hoverTimer * 10.0F);
            this.nameColor.g = Interpolation.fade.apply(Color.DARK_GRAY.g, Settings.CREAM_COLOR.g, this.hoverTimer * 3.0F);
            this.nameColor.b = Interpolation.fade.apply(Color.DARK_GRAY.b, Settings.CREAM_COLOR.b, this.hoverTimer * 3.0F);
            float y = Interpolation.exp10Out.apply(this.healthHb.cY, this.healthHb.cY - (this.maxSkill > 0 ? 1 : 0) * 40.0F * Settings.scale, this.nameColor.a);
            float x = this.hb.cX - this.animX;
            this.nameBgColor.a = this.nameColor.a / 2.0F * this.hbAlpha;
            sb.setColor(this.nameBgColor);
            TextureAtlas.AtlasRegion img = ImageMaster.MOVE_NAME_BG;
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, Settings.scale * tmp, Settings.scale * 2.0F, 0.0F);
            Color var10000 = this.nameColor;
            var10000.a *= this.hbAlpha;
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, this.name, x, y, this.nameColor);
        }

    }

    public void update() {

        for (AbstractPower p : this.powers) {
            p.updateParticles();
        }

        this.escapeHb.update();
        this.skillReadyHb.update();
        this.skillHb.update();
        this.updateReticle();
        this.updateHealthBar();
        this.updateSkillBar();
        this.updateSkillReady();
        this.updateEscapeButton();
        this.updateAnimations();
        this.updateDeathAnimation();
        this.updatePower();
        this.tint.update();
        this.updateInput();
    }

    private void updateInput(){
        if(!AbstractDungeon.isScreenUp && !this.isDying){
            if(!AbstractDungeon.actionManager.turnHasEnded && currentBattleSkill != null && currentBattleSkill.canSpell && currentBattleSkill.skillType != AbstractSkill.SkillType.PASSIVE){
                if(this.skillReadyHb.justHovered){
                    CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
                }

                if(this.skillReadyHb.hovered){
                    if(InputHelper.justClickedLeft){
                        CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
                    }

                    if(InputHelper.justReleasedClickLeft){
                        if(!this.currentBattleSkill.isSpelling && !this.currentBattleSkill.isAutomatic){
                            activeSkill();
                            currentBattleSkill.canSpell = false;
                        }
                    }
                }
            }

            //if(this.hb.hovered){
//                if(!IsDragging && InputHelper.justClickedLeft && this.hb.hovered){
//                    IsDragging = true;
//                    HoveredOperator = this;
//                    this.isMoving = true;
//                    this.start_x = (float)InputHelper.mX;
//                    this.start_y = (float)InputHelper.mY;
//                }
//
//                if(IsDragging && HoveredOperator == this){
//                    if(InputHelper.justReleasedClickLeft){
//                        if(isMoving && canPlayStart)
//                            this.playStartAnim();
//                        IsDragging = false;
//                        HoveredOperator = null;
//                        this.isMoving = false;
//                    }
//                    this.drawX += (float)InputHelper.mX - this.start_x;
//                    this.drawY += (float)InputHelper.mY - this.start_y;
//                    this.start_x = (float)InputHelper.mX;
//                    this.start_y = (float)InputHelper.mY;
//                }
//                if (this.drawX < this.hb.width / 2.0F) {
//                    this.drawX = this.hb.width / 2.0F;
//                } else if (this.drawX > (float)Settings.WIDTH - this.hb.width / 2.0F) {
//                    this.drawX = (float)Settings.WIDTH - this.hb.width / 2.0F;
//                }
//                if (this.drawY < 0.0F) {
//                    this.drawY = 0.0F;
//                } else if (this.drawY > (float)Settings.HEIGHT - this.hb.height) {
//                    this.drawY = (float)Settings.HEIGHT - this.hb.height;
//                }


                if(this.hb.hovered && InputHelper.justReleasedClickRight){
                    this.escapeButtonTimer = 3.0F;
                    this.isToEscape = true;
                    logger.info(this.name + "技能：" + this.skills.toString());
                    logger.info(this.name + "当前技能：" + this.currentBattleSkill.toString());
                    logger.info(this.name + "当前技能类型：" + this.currentBattleSkill.skillType.toString());
                }
            //}
            
            if(!AbstractDungeon.actionManager.turnHasEnded && this.isToEscape){
                if(this.escapeHb.justHovered){
                    CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
                }
                if(this.escapeHb.hovered){
                    this.escapeBtnSize = 1.2F;
                    if(InputHelper.justClickedLeft){
                        CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
                    }
                    if(InputHelper.justReleasedClickLeft) {
                        this.escape();
                    }
                }else{
                    this.escapeBtnSize = 1.0F;
                }
            }



        }
    }

    public void skillBarUpdatedEvent() {
        this.skillBarAnimTimer = 0.3F;
        this.targetSkillBarWidth = this.hb.width * (float)this.currentSkill / (float)this.maxSkill;
        if (this.currentSkill >= this.maxSkill) {
            this.skillBarWidth = this.targetSkillBarWidth;
        } else if (this.currentSkill == 0) {
            this.skillBarWidth = 0.0F;
            this.targetSkillBarWidth = 0.0F;
        }

//        if (this.targetSkillBarWidth > this.skillBarWidth) {
//            this.skillBarWidth = this.targetSkillBarWidth;
//        }

    }

    public void skillBarRevivedEvent() {
        this.skillBarAnimTimer = 0.3F;
        this.targetSkillBarWidth = this.hb.width * (float)this.currentSkill / (float)this.maxSkill;
        this.skillBarWidth = this.targetSkillBarWidth;
        this.ShbBgColor.a = 1.0F;
        this.ShbShadowColor.a = 0.5F;
        this.ShbTextColor.a = 1.0F;
    }

    private void updateSkillBar(){
        this.updateHbHoverFade();
        this.updateSkillChangeAnimation();
        this.updateSHbAlpha();
    }

    public void showSkillBar() {
        this.hbAlpha = 0.0F;
    }

    private void updateHbHoverFade() {
        if (this.skillHb.hovered) {
            this.skillHideTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.skillHideTimer < 0.2F) {
                this.skillHideTimer = 0.2F;
            }
        } else {
            this.skillHideTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.skillHideTimer > 1.0F) {
                this.skillHideTimer = 1.0F;
            }
        }

    }

    private void updateSHbAlpha() {
//        if (this.targetSkillBarWidth == 0.0F && this.skillBarAnimTimer <= 0.0F) {
//            this.ShbShadowColor.a = MathHelper.fadeLerpSnap(this.ShbShadowColor.a, 0.0F);
//            this.ShbBgColor.a = MathHelper.fadeLerpSnap(this.ShbBgColor.a, 0.0F);
//            this.ShbTextColor.a = MathHelper.fadeLerpSnap(this.ShbTextColor.a, 0.0F);
//        } else {
            this.ShbBgColor.a = this.hbAlpha * 0.5F;
            this.ShbShadowColor.a = this.hbAlpha * 0.2F;
            this.ShbTextColor.a = this.hbAlpha;
            this.bgGreenBarColor.a = this.hbAlpha;
            this.bgOrangeBarColor.a = this.hbAlpha;
            this.greenBarColor.a = this.hbAlpha;
            this.orangeBarColor.a = this.hbAlpha;
//        }
    }

//    private void updateSHbPopInAnimation() {
//        if (this.hbShowTimer > 0.0F) {
//            this.hbShowTimer -= Gdx.graphics.getDeltaTime();
//            if (this.hbShowTimer < 0.0F) {
//                this.hbShowTimer = 0.0F;
//            }
//
//            this.hbAlpha = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - this.hbShowTimer / 0.7F);
//            this.hbYOffset = Interpolation.exp10Out.apply(60.0F * Settings.scale, 0.0F, 1.0F - this.hbShowTimer / 0.7F);
//        }
//
//    }

    private void updateSkillChangeAnimation() {
        if (this.skillBarAnimTimer > 0.0F) {
            this.skillBarAnimTimer -= Gdx.graphics.getDeltaTime();
        }

        if (this.skillBarWidth != this.targetSkillBarWidth && this.skillBarAnimTimer <= 0.0F) {
            this.skillBarWidth = MathHelper.uiLerpSnap(this.skillBarWidth, this.targetSkillBarWidth);
        }

    }

    private void updateDeathAnimation() {
        if (this.isDying) {
            this.deathTimer -= Gdx.graphics.getDeltaTime() * 2.0F;
            if (this.deathTimer < 1.8F && !this.tintFadeOutCalled) {
                this.tintFadeOutCalled = true;
                this.tint.fadeOut();
            }
        }

        if (this.deathTimer < 0.0F) {
            this.isDead = true;
            this.dispose();
            this.powers.clear();
            logger.info("删除成功");
        }

    }

    private void updateSkillReady(){
        this.bobEffect.update();
    }

    private void updateEscapeButton(){
        if(this.isToEscape && !this.escapeHb.hovered && this.escapeButtonTimer > 0.0F){
            this.escapeButtonTimer -= Gdx.graphics.getDeltaTime();
            if(this.escapeButtonTimer <= 0.0F){
                this.isToEscape = false;
            }
        }
        if(this.escapeButtonTimer > 2.5F){
            this.escapeBtnAlpha = MathUtils.lerp(this.escapeBtnAlpha, 1.0F, (3.0F - this.escapeButtonTimer) * 2);
            this.escapeBtnX = MathUtils.lerp(this.escapeBtnX, -120.0F, (3.0F - this.escapeButtonTimer) * 2);
        }else if(this.escapeButtonTimer < 0.5F){
            this.escapeBtnAlpha = MathUtils.lerp(0.0F, this.escapeBtnAlpha, escapeButtonTimer * 2);
            this.escapeBtnX = MathUtils.lerp(0.0F, this.escapeBtnX, escapeButtonTimer * 2);
        }
    }

    private void renderStat(SpriteBatch sb){
        float x = this.hb.cX + this.hb.width / 2.0F;
        float y = this.hb.cY + this.hb.height / 2.0F - 26.0F * Settings.scale;
        sb.setColor(this.StatColor);
        sb.draw(ArknightsImageMaster.ATTACK_COOLDOWN, x - 26.0F * Settings.scale, y, x - 16.0F, y - 16.0F , 26.0F * Settings.scale, 26.0F * Settings.scale, 1.0F, 1.0F, 0.0F);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, this.currentAttackCoolDown + "/" + this.attackCoolDown, x + 18.0F * Settings.scale, y + 14.0F * Settings.scale, CDFontColor, fontScale);
        sb.draw(ArknightsImageMaster.ATTACK, x - 26.0F * Settings.scale, y - 25.0F * Settings.scale, x, y - 16.0F, 26.0F * Settings.scale, 26.0F * Settings.scale, 1.0F, 1.0F, 0.0F);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.showAtk), x + 18.0F * Settings.scale, y - 9.0F * Settings.scale, CDFontColor, fontScale);
    }

    public void renderTip() {
        this.tips.clear();

        if(currentBattleSkill != null){
            tips.add(new PowerTip(texts[2 * skillindex], texts[2 * skillindex + 1]));
        }

        for (AbstractPower p : this.powers) {
            if (p.region48 != null) {
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
            } else {
                this.tips.add(new PowerTip(p.name, p.description, p.img));
            }
        }

        if (!this.tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            } else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
        }

    }



    private void renderSkill(SpriteBatch sb) {
        if (!Settings.hideCombatElements && this.currentBattleSkill != null) {
            float x = this.hb.cX - this.hb.width / 2.0F;
            float y = this.hb.cY - 26.0F - this.hb.height / 2.0F;
            this.renderSkillBg(sb, x, y);
            this.renderBgSkillBar(sb, x, y);
            this.renderGreenSkillBar(sb, x, y);
            if(maxSkill > 0)
                this.renderSkillText(sb, y);
        }
    }


    private void renderSkillBg(SpriteBatch sb, float x, float y) {
        sb.setColor(this.ShbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - HEALTH_BAR_HEIGHT, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, this.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.hb.width, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setColor(this.ShbBgColor);
        if (this.currentSkill != this.maxSkill) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }

    }

    private void renderBgSkillBar(SpriteBatch sb, float x, float y) {
        sb.setColor(currentBattleSkill.isSpelling ? this.bgOrangeBarColor : this.bgGreenBarColor);
        if (this.currentSkill > 0)
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.skillBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.skillBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderGreenSkillBar(SpriteBatch sb, float x, float y) {
        sb.setColor(currentBattleSkill.isSpelling ? this.orangeBarColor : this.greenBarColor);
        if (this.currentSkill > 0)
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.targetSkillBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetSkillBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderSkillText(SpriteBatch sb, float y) {
        float tmp = this.ShbTextColor.a;
        Color var10000 = this.ShbTextColor;
        var10000.a *= this.skillHideTimer;
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.currentSkill + "/" + this.maxSkill, this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.ShbTextColor);
        this.ShbTextColor.a = tmp;
    }

    private void renderReadySkill(SpriteBatch sb){
        if(this.currentBattleSkill != null && !this.currentBattleSkill.isAutomatic && this.currentBattleSkill.canSpell){
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
            sb.draw(ArknightsImageMaster.SKILL_READY, this.skillReadyHb.x - 35.0F, this.skillReadyHb.y - 41.0F + bobEffect.y, 67.0F, 67.0F, 134.0F, 134.0F, 0.5F * Settings.scale, 0.5F * Settings.scale, 0.0F);
        }
    }

    private void renderEscape(SpriteBatch sb){
        if(escapeButtonTimer > 0.0F){
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, escapeBtnAlpha));
            sb.draw(ArknightsImageMaster.ESCAPE, this.drawX + this.escapeBtnX - 35.0F * Settings.scale, this.drawY + 35.0F * Settings.scale, 40.0F, 40.0F, 80.0F, 80.0F, Settings.scale * this.escapeBtnSize, Settings.scale * this.escapeBtnSize, 0.0F);
        }
    }

    public void dispose() {

        for (Disposable d : this.disposables) {
            logger.info("Disposed extra monster assets");
            d.dispose();
        }

        if (this.atlas != null) {
            this.atlas.dispose();
            this.atlas = null;
            logger.info("Disposed Texture: " + this.name);
        }

        this.skills.clear();
        this.effects.clear();
        this.showAtkEffect.isDone = true;
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public enum OperatorType{
        VANGUARD,
        GUARD,
        DEFENDER,
        SPECIALIST,
        SNIPER,
        CASTER,
        MEDIC,
        SUPPORTER,
    }
}
