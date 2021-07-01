package ArknightsMod.Operators.Supporter.Sora;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Actions.OperatorHealAction;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Monster.SleepingPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Sora_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 30;
    private static final int ORIGINAL_SKILLPOINT = 20;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Sora_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("tactfulboost");
        for(AbstractMonster m : GeneralHelper.monsters()) {
            addToBot(new ApplyPowerAction(m, owner, new SleepingPower(m, 1)));
        }
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            addToBot(new OperatorHealAction(o, owner, 3));
        }
        owner.changeSkillPoints(-1);
    }

    @Override
    public void EndEffect() {
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            addToBot(new AddSkillPointAction(3, o));
        }
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SORA_1;
    }
}
