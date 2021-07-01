package ArknightsMod.Operators.Supporter.Sora;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class Sora_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 22;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    private ArrayList<AbstractOperator> targets = new ArrayList<>();

    public Sora_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void init() {
        super.init();
        targets.clear();
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("tactfulboost");
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            targets.add(o);
            addToBot(new ApplyPowerAction(o, owner, new StrengthPower(o, 2)));
        }
        owner.changeSkillPoints(-1);
    }

    @Override
    public void EndEffect() {
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            addToBot(new AddSkillPointAction(3, o));
        }
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(targets.contains(o)) {
                addToBot(new ReducePowerAction(o, o, StrengthPower.POWER_ID, 2));
            }
        }
        targets.clear();
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SORA_2;
    }
}
