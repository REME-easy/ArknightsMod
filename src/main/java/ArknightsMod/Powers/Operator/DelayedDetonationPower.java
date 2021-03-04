package ArknightsMod.Powers.Operator;

import ArknightsMod.Actions.OperatorSuicideAction;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class DelayedDetonationPower extends AbstractArkPower {
    private static final String POWER_ID = "Arknights_DelayedDetonationPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractOperator owner;
    public DelayedDetonationPower(AbstractOperator owner){
        this.ID = POWER_ID;
        this.name =NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        String path128 = "ArkImg/powers/DelayedDetonation84.png";
        String path48 = "ArkImg/powers/DelayedDetonation32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void onOperatorAttack() {
        super.onOperatorAttack();
        this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(owner.Atk * 3, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                this.addToBot(new ApplyPowerAction(m, this.owner, new VulnerablePower(m, 1, false)));
            }
        }
        this.addToBot(new OperatorSuicideAction(this.owner));
        this.addToBot(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }
}
