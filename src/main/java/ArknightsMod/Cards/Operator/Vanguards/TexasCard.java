package ArknightsMod.Cards.Operator.Vanguards;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Vanguards.Texas.Texas;
import ArknightsMod.Operators.Vanguards.Texas.Texas_1;
import ArknightsMod.Operators.Vanguards.Texas.Texas_2;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class TexasCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Texas";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/TexasCard.png";
    private static final int COST = 1;
    public TexasCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.isInnate = true;
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Texas_1());
        tmp.add(new Texas_2());
        return tmp;
    }

    @Override
    public void onBattleStartInDeck(AbstractRoom room) {
        if(room.phase == AbstractRoom.RoomPhase.COMBAT){
            this.addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public AbstractOperator getOperator() {
        return new Texas();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}