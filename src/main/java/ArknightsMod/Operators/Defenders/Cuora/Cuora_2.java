package ArknightsMod.Operators.Defenders.Cuora;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.SolidnessPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Cuora_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 20;
    private static final int ORIGINAL_SKILLPOINT = 8;
    private static final int SKILLTIMES = 12;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Cuora_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("defboost");
        owner.canAttack = false;
        addToBot(new ApplyPowerAction(owner, owner, new SolidnessPower(owner, 4)));
        addToBot(new ApplyPowerAction(owner, owner, new MetallicizePower(owner, 4)));
        owner.state.setAnimation(0, "Skill_Begin", false);
        owner.playIdleAnim();
    }

    @Override
    public void EndEffect() {
        owner.canAttack = true;
        addToBot(new ReducePowerAction(owner, owner, SolidnessPower.POWER_ID, 4));
        addToBot(new ReducePowerAction(owner, owner, MetallicizePower.POWER_ID, 4));
        owner.state.setAnimation(0, "Skill_End", false);
        owner.playIdleAnim();
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.CUORA_2;
    }

    @Override
    public void onUseEnergy(int amt) {
        owner.changeSkillPoints(-amt);
    }
}
