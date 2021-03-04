package ArknightsMod.Operators.Guards.Chen;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Chen_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 3;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Chen_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
    }

    @Override
    public void EndEffect() {

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.CHEN_1;
    }

    @Override
    public String getSkillAnim() {
        return "Attack";
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg + 3;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    this.isDone = true;
                } else {
                    AbstractCreature creature = owner.lastTarget;
                    if(creature != null) {
                        this.addToBot(new GainBlockAction(owner, creature.lastDamageTaken));
                    }
                    this.isDone = true;
                }
            }
        });
        this.owner.changeSkillPoints(-1);
    }
}
