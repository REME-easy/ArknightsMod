package ArknightsMod.Powers.Operator;

import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingBlueEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RulesOfSurvivalPower extends AbstractPower {
    private static final String POWER_ID;
    private static final PowerStrings powerStrings;
    private static final String NAME;
    private static final String[] DESCRIPTIONS;

    private float Timer = 0.0F;

    public RulesOfSurvivalPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        String path128 = "ArkImg/powers/RulesOfSurvival84.png";
        String path48 = "ArkImg/powers/RulesOfSurvival32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL){
            return damage * 0.7F;
        }
        return damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            this.flash();
            this.addToBot(new HealAction(owner, owner, 3));
        }
    }

    @Override
    public void updateParticles() {
        Timer -= Gdx.graphics.getDeltaTime();
        if(Timer <= 0.0F){
            Timer = 0.3F;
            if(owner instanceof AbstractOperator)
                AbstractDungeon.effectsQueue.add(new OperatorSpellingBlueEffect((AbstractOperator) owner));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    static {
        POWER_ID = "Arknights_RulesOfSurvivalPower";
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
