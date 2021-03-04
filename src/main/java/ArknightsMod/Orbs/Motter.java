package ArknightsMod.Orbs;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Patches.OperatorTakeDamagePatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class Motter extends AbstractAnimatedOrb {
    private static final String DIR_U = "Motter";
    private static final String DIR_L = "motter";
    public static final String ID = "Arknights_" + DIR_U;
    private static final String ATLAS = "Operators/" + DIR_U + "/" + DIR_L + ".atlas";
    private static final String JSON = "Operators/" + DIR_U + "/" + DIR_L + ".json";
    private static final String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private static final String[] DESCRIPTION = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;
    private static final int PASSIVE_AMOUNT = 3;
    private static final int EVOKE_AMOUNT = 7;

    public Motter() {
        super(ID, NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTION);
        this.loadAnimation(ATLAS, JSON, 2.2F);
        this.stateData.setDefaultMix(0.1F);
        AnimationState.TrackEntry e = this.state.addAnimation(0, "Idle", true, 0.0F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description = rawDescriptions[0] + passiveAmount + rawDescriptions[1] + evokeAmount + rawDescriptions[2];
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("char_set");
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        GeneralHelper.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(OperatorTakeDamagePatch.getTarget()) {
                    addToBot(new GainBlockAction(OperatorTakeDamagePatch.target, passiveAmount));
                }else {
                    addToBot(new GainBlockAction(AbstractDungeon.player, passiveAmount));
                }
                state.addAnimation(0, "Attack", false, 0.0F);
                state.addAnimation(0, "Idle", true, 0.0F);
                isDone = true;
            }
        });
    }

    @Override
    public void onEvoke() {
        super.onEvoke();
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
        GeneralHelper.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(evokeAmount), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
    }
}
