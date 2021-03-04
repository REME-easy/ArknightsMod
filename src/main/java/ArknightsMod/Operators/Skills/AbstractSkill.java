package ArknightsMod.Operators.Skills;

import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Orbs.AbstractAnimatedOrb;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public abstract class AbstractSkill {
    public AbstractOperator owner;
    public boolean isAutomatic;
    public int maxSkill;
    public int initSkill;
    public int skillTimes;
    public SkillType skillType;
    public boolean isSpelling;
    public boolean canSpell = false;

    public AbstractSkill(boolean isAutomatic, int maxSkill, int initSkill, int skillTimes, SkillType skillType){
        this.isAutomatic = isAutomatic;
        this.maxSkill = maxSkill;
        this.initSkill = initSkill;
        this.skillTimes = skillTimes;
        this.skillType = skillType;
    }

    public void init() {
        this.owner = null;
        this.isSpelling = false;
        this.canSpell = false;
    }

    public boolean Attack(){
        return false;
    }

    public void useWhenSummoned(){}

    public abstract void ActiveEffect();

    public abstract void EndEffect();

    public abstract AbstractGameEffect skillEffect();

    public abstract TextureAtlas.AtlasRegion getTexture();

    public void onStartOfTurn(){}

    public void onEndOfTurn(){}

    public void onUseCard(AbstractCard card){}

    public int getAttackTimes(int times){
        return times;
    }

    public float onAttack(float dmg){return dmg;}

    public void afterAttack() {}

    public float onDamage(float dmg){return dmg;}

    public void onOtherOperatorDamage(AbstractOperator operator) {}

    public void onUseEnergy(int amt) {}

    public void onChannelToken(AbstractAnimatedOrb orb) {}

    public String getSkillAnim(){
        return "Attack";
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public enum SkillType{
        NATURAL,
        ATTACK,
        HIT,
        PASSIVE,
    }
}
