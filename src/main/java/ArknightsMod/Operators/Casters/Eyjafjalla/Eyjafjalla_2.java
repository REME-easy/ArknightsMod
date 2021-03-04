package ArknightsMod.Operators.Casters.Eyjafjalla;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Eyjafjalla_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 7;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Eyjafjalla_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        owner.attackTargets += 2;
    }

    @Override
    public void EndEffect() {
        owner.attackTargets -= 2;
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.EYJAFJALLA_2;
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 2.0F;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && AbstractDungeon.getMonsters().monsters.size() > 0){
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                this.addToBot(new ApplyPowerAction(m, owner, new VulnerablePower(m, 1, true), 1));
            }
        }
        this.owner.changeSkillPoints(-1);
    }
}
