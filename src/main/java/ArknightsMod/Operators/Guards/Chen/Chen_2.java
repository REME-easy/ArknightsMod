package ArknightsMod.Operators.Guards.Chen;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.Common.Chen2Effect;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

import java.util.ArrayList;

public class Chen_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 12;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Chen_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        owner.changeSkillPoints(-1);
        CardCrawlGame.sound.play("chen_skill_2");
        owner.state.setAnimation(0, "Skill_2", false);
        owner.playIdleAnim();
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                CardCrawlGame.sound.play("chen_2");
                GeneralHelper.addEffect(new Chen2Effect(owner.drawX + 50.0F * Settings.scale, owner.drawY));
                ArrayList<AbstractMonster> monsters = GeneralHelper.getRandomMonsters(4);

                DamageInfo info1 = new DamageInfo(owner, owner.getAttackToTarget() * 2, DamageType.NORMAL);
                DamageInfo info2 = new DamageInfo(owner, owner.getAttackToTarget() * 2, DamageType.THORNS);
                for(AbstractMonster m : monsters) {
                    m.damage(info1);
                    m.damage(info2);
                    GeneralHelper.addEffect(new AdditiveSlashImpactEffect(m.drawX, m.drawY, Color.RED));
                    GeneralHelper.addEffect(new AdditiveSlashImpactEffect(m.drawX, m.drawY, Color.RED));
                    GeneralHelper.addEffect(new AdditiveSlashImpactEffect(m.drawX, m.drawY, Color.RED));
                }
                this.isDone = true;
            }
        });
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
        return ArknightsImageMaster.CHEN_2;
    }

    @Override
    public String getSkillAnim() {
        return "Attack";
    }
}
