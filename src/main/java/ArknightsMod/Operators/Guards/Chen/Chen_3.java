package ArknightsMod.Operators.Guards.Chen;

import ArknightsMod.Actions.ChenSkill3Action;
import ArknightsMod.Actions.PlzWaitAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Chen_3 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 18;
    private static final int ORIGINAL_SKILLPOINT = 18;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Chen_3(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        owner.changeSkillPoints(-1);
        owner.state.setAnimation(0, "Skill_3", false);
        CardCrawlGame.sound.play("chen_skill_3");
//        GeneralHelper.addEffect(new ChenLongEffect(owner.drawX - 50.0F * Settings.scale, owner.drawY));
        this.addToBot(new PlzWaitAction(1.0F));
        for (int i = 0; i < 10; i++) {
            this.addToBot(new ChenSkill3Action(i, i == 9, owner));
        }
    }

    @Override
    public void EndEffect() {
        owner.state.addAnimation(0, "Skill_End_3", false, 0.0F);
        owner.playIdleAnim();
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.CHEN_3;
    }

    @Override
    public String getSkillAnim() {
        return "Attack";
    }
}
