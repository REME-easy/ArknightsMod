package ArknightsMod.Helper;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.*;
import ArknightsMod.Cards.Operator.Defenders.LiskarmCard;
import ArknightsMod.Cards.Operator.Defenders.NearlCard;
import ArknightsMod.Cards.Operator.Defenders.NoirCorneCard;
import ArknightsMod.Cards.Operator.Defenders.SpotCard;
import ArknightsMod.Cards.Operator.Guards.*;
import ArknightsMod.Cards.Operator.Medic.AnselCard;
import ArknightsMod.Cards.Operator.Medic.HibiscCard;
import ArknightsMod.Cards.Operator.Snipers.*;
import ArknightsMod.Cards.Operator.Specialists.GravelCard;
import ArknightsMod.Cards.Operator.Specialists.RedCard;
import ArknightsMod.Cards.Operator.Specialists.THRMEXCard;
import ArknightsMod.Cards.Operator.Supporter.MayerCard;
import ArknightsMod.Cards.Operator.Supporter.OrchidCard;
import ArknightsMod.Cards.Operator.Vanguards.*;
import ArknightsMod.Operators.AbstractOperator.OperatorType;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.min;

public class OperatorHelper {
    public static HashMap<String, AbstractOperatorCard> allOperatorsCards = new HashMap<>();

    public static void addOperators(){
        Object[] operators = new Object[] {new AngelCard(), new THRMEXCard(), new SilverashCard(), new TexasCard(),
                new YatoCard(), new NoirCorneCard(), new EyjafjallaCard(), new _12FCard(), new RangersCard(),
                new AnselCard(), new OrchidCard(), new MidnightCard(), new SpotCard(), new DurinCard(), new CatapultCard(),
                new PopukarCard(), new StewardCard(), new KroosCard(), new AdnachCard(), new LavaCard(), new BluepCard(),
                new LiskarmCard(), new RedCard(), new LapplandCard(), new JessicaCard(), new HazeCard(), new GitanoCard(),
                new FangCard(), new HibiscCard(), new ChenCard(), new GravelCard(), new MayerCard(), new SpecterCard(),
                new NearlCard(), new MeteorCard(), new CourierCard(), new ScavengerCard(), new VignaCard()};
        for(Object o:operators){
            allOperatorsCards.put(((AbstractOperatorCard) o).cardID, (AbstractOperatorCard)o);
            BaseMod.addCard(((AbstractOperatorCard) o).makeCopy());
        }
    }

    public static ArrayList<AbstractCard> getOperatorReward(int num, OperatorType type) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for(AbstractOperatorCard c : allOperatorsCards.values()) {
            if(c.operatorType == type)
                cards.add(c);
        }

        int amt = min(num, cards.size());

        while (tmp.size() < amt) {
            boolean canAdd = true;
            AbstractCard c = cards.get(AbstractDungeon.cardRandomRng.random(0, cards.size() - 1));

            for(AbstractCard c1 : AbstractDungeon.player.masterDeck.group) {
                if(c.cardID.equals(c1.cardID)) {
                    canAdd = false;
                    amt--;
                    break;
                }
            }

            for(AbstractCard c2 : tmp) {
                if(c.cardID.equals(c2.cardID)) {
                    canAdd = false;
                    break;
                }
            }

            if(canAdd) tmp.add(c.makeCopy());
        }
        return tmp;
    }

}
