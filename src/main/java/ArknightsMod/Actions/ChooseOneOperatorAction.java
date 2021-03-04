package ArknightsMod.Actions;

import ArknightsMod.Cards.AbstractArkCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Screens.TargetArrowScreen;
import ArknightsMod.Utils.TargetArrow;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class ChooseOneOperatorAction extends AbstractGameAction implements TargetArrowScreen.TargetArrowScreenSubscriber {

    private AbstractArkCard card;
    private int amt;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT").TEXT;


    public ChooseOneOperatorAction(AbstractArkCard card, int amt) {
        this.card = card;
        this.amt = amt;
        this.duration = DEFAULT_DURATION;
        TargetArrowScreen.register(this);
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if(this.duration == DEFAULT_DURATION) {

                if(OperatorGroup.GetOperators().size() <= 0) {
                    GeneralHelper.addEffect(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[8], true));
                    this.isDone = true;
                }else {
                    TargetArrowScreen.Inst.open(TEXT[6] + amt + TEXT[7], TargetArrow.TargetType.Friend);
                }

                this.tickDuration();
            }

            if(TargetArrowScreen.Inst.isActive && !TargetArrow.isActive) {
                this.isDone = true;
            }

        }
    }

    @Override
    public void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target) {
        this.card.receiveScreenTargetCreature(source, target);
        this.isDone = true;
    }

    @Override
    public void receiveScreenTargetHitbox(Hitbox hitbox) {
    }

    @Override
    public void receiveEnd() {
        this.isDone = true;
    }
}
