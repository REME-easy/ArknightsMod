package ArknightsMod.Actions;

import ArknightsMod.Helper.GeneralHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayACardAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(PlayACardAction.class);
    private AbstractCard card;
    private AbstractCreature target;

    public PlayACardAction(AbstractCard card, AbstractCreature target) {
        this.duration = DEFAULT_DURATION;
        this.card = card;
        this.target = target;
    }

    public PlayACardAction(AbstractCard card){
        this(card, null);
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == DEFAULT_DURATION) {
                if(target != null){
                    if(target.isDeadOrEscaped() && !target.isDead){
                        GameActionManager.queueExtraCard(card, (AbstractMonster) target);
                    }else{
                        GameActionManager.queueExtraCard(card, GeneralHelper.getRandomMonsterSafe());
                    }
                    logger.info("打出了" + card.name);
                }

            }
            this.tickDuration();
        }
    }
}
