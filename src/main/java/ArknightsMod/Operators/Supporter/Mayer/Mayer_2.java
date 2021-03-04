package ArknightsMod.Operators.Supporter.Mayer;

import ArknightsMod.Cards.Operator.Token.MotterCard;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Orbs.Motter;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Mayer_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 10;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Mayer_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("tactfulboost");
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                owner.playSkillAnim();
                owner.playIdleAnim();
                AbstractPlayer p = AbstractDungeon.player;
                int i = 0;
                for(AbstractOrb o : p.orbs) {
                    if(o.ID.equals(Motter.ID)) {
                        i++;
                    }
                }
                addToBot(new EvokeAllOrbsAction());
                for (int j = 0; j < i; j++) {
                    addToBot(new MakeTempCardInHandAction(new MotterCard()));
                }
                isDone = true;
            }
        });
        owner.changeSkillPoints(-1);
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
        return ArknightsImageMaster.MAYER_2;
    }
}
