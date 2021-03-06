package ArknightsMod.Operators.Specialists.Gravel;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Gravel_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 0;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Gravel_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    public void useWhenSummoned() {
        owner.activeSkill();
    }

    @Override
    public void ActiveEffect() {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard card = null;
                for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if(c.cost == 0) {
                        card = c;
                        break;
                    }
                }

                if(card == null) {
                    for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                        if(c.cost == 0) {
                            card = c;
                            AbstractDungeon.player.drawPile.removeCard(card);
                            break;
                        }
                    }
                }

                if(card != null) {
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    card.target_y = (float)Settings.HEIGHT / 2.0F;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    this.addToTop(new NewQueueCardAction(card, GeneralHelper.getRandomMonsterSafe(), false, true));
                }
                this.isDone = true;
            }
        });
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
        return ArknightsImageMaster.GRAVEL_1;
    }
}
