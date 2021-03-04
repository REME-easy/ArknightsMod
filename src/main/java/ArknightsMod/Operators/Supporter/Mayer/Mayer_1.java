package ArknightsMod.Operators.Supporter.Mayer;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Orbs.AbstractAnimatedOrb;
import ArknightsMod.Orbs.Motter;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.HashMap;
import java.util.Map;

public class Mayer_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 0;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Mayer_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Map<Integer, AbstractOperator> operatorMap = new HashMap<>();
                int[] ints = new int[] {2, 3, 6, 7};
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    for(int i : ints) {
                        if(i == o.index && !operatorMap.containsKey(i) && GeneralHelper.isAlive(o)) {
                            operatorMap.put(i, o);
                            break;
                        }
                    }
                }
                for(AbstractOperator o : operatorMap.values()) {
                    this.addToBot(new GainBlockAction(o, 2));
                }
                isDone = true;
            }
        });
        this.owner.changeSkillPoints(-1);
    }

    @Override
    public void EndEffect() {

    }

    @Override
    public void onChannelToken(AbstractAnimatedOrb orb) {
        super.onChannelToken(orb);
        if(orb.ID.equals(Motter.ID)) {
            owner.activeSkill();
        }
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.MAYER_1;
    }
}
