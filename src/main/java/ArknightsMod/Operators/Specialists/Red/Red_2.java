package ArknightsMod.Operators.Specialists.Red;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.Common.RedWolfEffect;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS;

public class Red_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 0;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Red_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void useWhenSummoned() {
        this.owner.activeSkill();
    }

    @Override
    public void ActiveEffect() {
        AbstractDungeon.topLevelEffects.add(new RedWolfEffect(Settings.WIDTH * 0.75F - 128.0F * Settings.scale, AbstractDungeon.floorY + 128.0F * Settings.scale));
        CardCrawlGame.sound.playA("red_2", 0.0F);
        this.addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.owner.Atk * 2, true), THORNS, FIRE));
        for(AbstractMonster m : GeneralHelper.monsters()) {
            if(m instanceof AbstractEnemy) ((AbstractEnemy) m).addAttackCoolDown(-3);
        }
        this.owner.changeSkillPoints(-1);
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
        return ArknightsImageMaster.RED_2;
    }
}
