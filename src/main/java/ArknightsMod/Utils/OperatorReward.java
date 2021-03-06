package ArknightsMod.Utils;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.OperatorHelper;
import ArknightsMod.Operators.AbstractOperator.OperatorType;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;
import java.util.HashMap;

public class OperatorReward extends CustomReward {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT");
    public static final String[] TEXTS = uiStrings.TEXT;
    public ArrayList<AbstractCard> retVal2 = new ArrayList<>();

    public OperatorType type = OperatorType.SNIPER;
    public int index = -1;
    public boolean renderLink = false;

    public static final HashMap<OperatorType, Stat> table = new HashMap<>();
    static {
        table.put(OperatorType.DEFENDER, new Stat(9, ArknightsImageMaster.RECRUIT_DEFENDER));
        table.put(OperatorType.GUARD, new Stat(10, ArknightsImageMaster.RECRUIT_GUARD));
        table.put(OperatorType.VANGUARD, new Stat(11, ArknightsImageMaster.RECRUIT_VANGUARD));
        table.put(OperatorType.CASTER, new Stat(12, ArknightsImageMaster.RECRUIT_CASTER));
        table.put(OperatorType.SUPPORTER, new Stat(14, ArknightsImageMaster.RECRUIT_SUPPORTER));
        table.put(OperatorType.SNIPER, new Stat(13, ArknightsImageMaster.RECRUIT_SNIPER));
        table.put(OperatorType.SPECIALIST, new Stat(15, ArknightsImageMaster.RECRUIT_SPECIALIST));
        table.put(OperatorType.MEDIC, new Stat(16, ArknightsImageMaster.RECRUIT_MEDIC));
    }

    public OperatorReward() {
        super(ArknightsImageMaster.TICKET, TEXTS[2], RewardItemEnum.OPERATOR);
    }

    public OperatorReward(ArrayList<AbstractCard> cards) {
        this();
        this.retVal2 = cards;
    }

    public OperatorReward(OperatorType type, int index) {
        super(table.get(type).texture, TEXTS[table.get(type).index], RewardItemEnum.OPERATOR);
        this.type = type;
        this.index = index;
    }

    public OperatorReward(OperatorType type) {
        this(type, -1);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(renderLink) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LINKED, this.hb.cX - 64.0F, this.y - 64.0F + 52.0F * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }
    }

    @Override
    public boolean claimReward() {
        if(!ignoreReward) {
            for (int i = AbstractDungeon.getCurrRoom().rewards.size() - 1; i >= 0; i--) {
                RewardItem r = AbstractDungeon.getCurrRoom().rewards.get(i);
                if (r instanceof OperatorReward && r != this) {
                    r.isDone = true;
                    r.ignoreReward = true;
                }
            }
            renderLink = false;
            if(!retVal2.isEmpty()) {
                AbstractDungeon.cardRewardScreen.open(retVal2, this, TEXTS[2]);
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            }else {
                AbstractDungeon.cardRewardScreen.open(OperatorHelper.getOperatorReward(3, type), this, TEXTS[2]);
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            }
            return false;
        }
        return true;
    }

    public static class RewardItemEnum {
        @SpireEnum
        public static RewardType OPERATOR;

        public RewardItemEnum() {
        }
    }

    public static class Stat {
        Texture texture;
        int index;

        Stat(int index, Texture texture) {
            this.index = index;
            this.texture = texture;
        }
    }
}
