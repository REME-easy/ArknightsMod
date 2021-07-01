package ArknightsMod.Operators.Vanguards.Texas;

import ArknightsMod.Cards.Attack.SwordRain;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Texas_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 12;
    private static final int ORIGINAL_SKILLPOINT = 7;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Texas_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("tactboost");
        this.owner.changeSkillPoints(-1);
        this.addToBot(new MakeTempCardInHandAction(new SwordRain()));
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
        return ArknightsImageMaster.TEXAS_2;
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg;
    }
}
