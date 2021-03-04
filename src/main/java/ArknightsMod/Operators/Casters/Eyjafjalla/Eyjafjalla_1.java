package ArknightsMod.Operators.Casters.Eyjafjalla;

import ArknightsMod.Actions.PlayACardAction;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Eyjafjalla_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 20;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Eyjafjalla_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        this.owner.changeSkillPoints(-1);
        OperatorGroup tmp = OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player);
        int amount = 0;
        if(tmp.operators.size() > 0)
            for(AbstractOperator o:tmp.operators){
                if(o.operatorType == AbstractOperator.OperatorType.CASTER){
                    amount++;
                }
            }
        if(amount <= 1){
            //this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 3)));
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
            this.addToBot(new PlayACardAction(c));
        }
        if(tmp.operators.size() > 0)
            for(AbstractOperator o:tmp.operators){
                if(o.operatorType == AbstractOperator.OperatorType.CASTER){
                    this.addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            o.Attack();
                            isDone = true;
                        }
                    });
                }
            }
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
        return ArknightsImageMaster.EYJAFJALLA_1;
    }
}
