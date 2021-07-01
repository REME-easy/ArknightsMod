package ArknightsMod.Operators.Snipers.Firewatch;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Firewatch_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 0;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 0;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Firewatch_1(){
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
        return null;
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.FIREWATCH_1;
    }
}
