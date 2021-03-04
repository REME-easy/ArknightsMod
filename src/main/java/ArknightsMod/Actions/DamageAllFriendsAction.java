package ArknightsMod.Actions;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageAllFriendsAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(DamageAllFriendsAction.class);

    private AbstractCreature source;
    private int dmg;


    public DamageAllFriendsAction(AbstractCreature source, int dmg, DamageType type, AttackEffect effect, boolean isFast) {
        this.source = source;
        this.dmg = dmg;
        this.duration = DEFAULT_DURATION;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
        this.startDuration = this.duration;
    }

    public DamageAllFriendsAction(DamageInfo info, AttackEffect effect, boolean isFast) {
        this(info.owner, info.base, info.type, effect, isFast);
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == startDuration) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    if(!o.isDeadOrEscaped()) {
                        o.damage(new DamageInfo(source, dmg, damageType));
                        GeneralHelper.addEffect(new FlashAtkImgEffect(o.hb.cX, o.hb.cY, this.attackEffect));
                    }
                }
                AbstractPlayer p = AbstractDungeon.player;
                p.damage(new DamageInfo(source, dmg, damageType));
                GeneralHelper.addEffect(new FlashAtkImgEffect(p.hb.cX, p.hb.cY, this.attackEffect));

            }
            this.tickDuration();
        }
    }
}
