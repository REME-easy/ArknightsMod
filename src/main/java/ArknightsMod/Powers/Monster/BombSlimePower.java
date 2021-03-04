package ArknightsMod.Powers.Monster;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Patches.OperatorTakeDamagePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class BombSlimePower extends AbstractPower {
    public static final String POWER_ID = "Arknights_BombSlimePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public BombSlimePower(AbstractCreature owner, int Amt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amt;
        this.updateDescription();
        this.loadRegion("explosive");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onDeath() {
        if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
            CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");
            this.flashWithoutSound();
            float x = this.owner.drawX;
            float y = this.owner.drawY;
            GeneralHelper.addEffect(new ExplosionSmallEffect(x, y));
            if(OperatorTakeDamagePatch.getTarget()) {
                OperatorTakeDamagePatch.target.damage(new DamageInfo(owner, this.amount));
            }else if(OperatorTakeDamagePatch.target == null) {
                AbstractDungeon.player.damage(new DamageInfo(owner, this.amount));
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
