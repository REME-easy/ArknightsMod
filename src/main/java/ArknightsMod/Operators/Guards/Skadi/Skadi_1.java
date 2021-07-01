package ArknightsMod.Operators.Guards.Skadi;

import ArknightsMod.Cards.Operator.Guards.SkadiCard;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Skadi_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 12;
    private static final int ORIGINAL_SKILLPOINT = 7;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Skadi_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        AbstractMonster m1 = null;
        for(AbstractMonster m : GeneralHelper.monsters()) {
            if(m1 != null && m.currentHealth < m1.currentHealth) {
                m1 = m;
            }else {
                m1 = m;
            }
        }
        owner.lastTarget = m1;
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                owner.Attack();
                isDone = true;
            }
        });
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(owner.lastTarget != null && owner.lastTarget.isDeadOrEscaped()) {
                    for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                        if(c.cardID.equals(SkadiCard.ID)) {
                            c.misc += 1;
                            break;
                        }
                    }
                    owner.addAttack(1);
                }
                owner.changeSkillPoints(-1);
                isDone = true;
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
        return ArknightsImageMaster.QUICK_ATTACK_3;
    }
}
