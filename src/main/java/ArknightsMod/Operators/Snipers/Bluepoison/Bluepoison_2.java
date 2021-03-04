package ArknightsMod.Operators.Snipers.Bluepoison;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Bluepoison_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 25;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Bluepoison_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        for(AbstractOperator o: OperatorGroup.GetOperators()) {
            if(o != this.owner)
                this.addToBot(new ApplyPowerAction(o, this.owner, new EnvenomPower(o, 1)));
        }
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnvenomPower(AbstractDungeon.player, 1)));
    }

    @Override
    public void EndEffect() {
        for(AbstractOperator o: OperatorGroup.GetOperators()) {
            if(o != this.owner)
                this.addToBot(new ReducePowerAction(o, this.owner, EnvenomPower.POWER_ID, 1));
        }
        this.addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, EnvenomPower.POWER_ID, 1));

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.BLUEP_2;
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        this.owner.changeSkillPoints(-1);
    }
}
