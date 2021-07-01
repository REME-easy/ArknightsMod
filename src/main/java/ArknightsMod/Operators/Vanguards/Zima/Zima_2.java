package ArknightsMod.Operators.Vanguards.Zima;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.SolidnessPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class Zima_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 17;
    private static final int ORIGINAL_SKILLPOINT = 7;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    private ArrayList<AbstractOperator> targets = new ArrayList<>();

    public Zima_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void init() {
        super.init();
        targets.clear();
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("tactboost");
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(o.operatorType == AbstractOperator.OperatorType.VANGUARD) {
                targets.add(o);
                o.addAttack(2);
                addToBot(new ApplyPowerAction(o, owner, new SolidnessPower(o, 2)));
            }
        }
    }

    @Override
    public void EndEffect() {
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(targets.contains(o)) {
                o.addAttack(-2);
                addToBot(new ReducePowerAction(o, o, SolidnessPower.POWER_ID, 2));
            }
        }
        targets.clear();
    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        owner.changeSkillPoints(-1);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.ZIMA_2;
    }
}
