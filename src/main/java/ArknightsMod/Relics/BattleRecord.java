package ArknightsMod.Relics;

import ArknightsMod.Helper.OperatorHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.AbstractOperator.OperatorType;
import ArknightsMod.Utils.OperatorReward;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.gridSelectScreen;
import static java.lang.Math.min;

public class BattleRecord extends CustomRelic {
    private boolean RclickStart = false;
    private boolean Rclick = false;
    private CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public BattleRecord() {
        super("Arknights_BattleRecord", ImageMaster.loadImage("ArkImg/relics/BattleRecord.png"),
                RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = 1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if(AbstractDungeon.cardRandomRng.randomBoolean()) {
            ArrayList<OperatorType> types = new ArrayList<>();
            ArrayList<OperatorReward> rewards = new ArrayList<>();
            while(types.size() < 3) {
                OperatorType type = AbstractOperator.OPERATOR_TYPES[AbstractDungeon.merchantRng.random(0, 7)];
                if(!types.contains(type)) {
                    types.add(type);
                }
            }
            for(OperatorType t : types) {
                rewards.add(new OperatorReward(t));
            }
            for (int i = 0; i < rewards.size(); i++) {
                OperatorReward reward = rewards.get(i);
                AbstractDungeon.getCurrRoom().rewards.add(reward);
                reward.index = i;
                if(i != 0) {
                    reward.renderLink = true;
                }
            }
        }
    }

    private void onRightClick(){
        if(this.counter == 1){
            this.counter = -1;
            ArrayList<AbstractCard> retVal = new ArrayList<>(OperatorHelper.allOperatorsCards.values());
            int amt = 5;
            ArrayList<AbstractCard> retVal2 = new ArrayList<>();
            while(retVal2.size() < amt){
                AbstractCard c = retVal.get(AbstractDungeon.cardRandomRng.random(0, retVal.size() - 1));
                boolean canAdd = true;
                if(retVal2.size() > 0){
                    for(AbstractCard c1:retVal2){
                        if(c.cardID.equals(c1.cardID)){
                            canAdd = false;
                            break;
                        }
                    }
                }

                if(canAdd){
                    retVal2.add(c.makeCopy());
                }

                amt = min(5, retVal.size());
            }
            tmp.clear();
            tmp.group.addAll(retVal2);
            if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(tmp, 3, true, this.DESCRIPTIONS[1]);
            } else {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                AbstractDungeon.gridSelectScreen.open(tmp, 3, true, this.DESCRIPTIONS[1]);
            }
        }

    }

    public void update() {
        super.update();
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                this.Rclick = true;
            }
            this.RclickStart = false;
        }
        if (this.isObtained && this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
            this.RclickStart = true;
        }
        if (this.Rclick) {
            this.Rclick = false;
            this.onRightClick();
        }
        if (this.counter == -1 && tmp.group != null && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for(AbstractCard c: gridSelectScreen.selectedCards){
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
            this.tmp.clear();
            this.counter = -2;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public AbstractRelic makeCopy() {
        return new BattleRecord();
    }
}
