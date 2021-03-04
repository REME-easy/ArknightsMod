package ArknightsMod.Patches;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Screens.SkillButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ChangeSkillPatch {
    private static final Logger logger = LogManager.getLogger(ChangeSkillPatch.class.getName());
    public static AbstractOperatorCard viewcard;
    private static ArrayList<SkillButton> hitboxes = new ArrayList<>();

    private static int i;


    private static final float hb_x = Settings.WIDTH / 2.0F + 300.0F * Settings.scale;
    private static final float hb_y = Settings.HEIGHT / 2.0F + 220.0F * Settings.scale;

    private static void ChangeOperator(AbstractOperatorCard card){
        if(card != null) {
            if(card.skills.size() > 0){
                for(int i = hitboxes.size() - 1 ; i > 0 ; i--) {
                    SkillButton b = hitboxes.get(i);
                    b.dispose();
                }
                hitboxes.clear();
                viewcard = card;
                int num = card.skills.size();
                for(i = 0 ; i < num ; i++){
                    hitboxes.add(new SkillButton(card.skills.get(i), i, hb_x, hb_y - 135.0F * Settings.scale * i, new String[]{card.tips.get(2 * i), card.tips.get(2 * i + 1)} ));
                    viewcard.currentSkill = viewcard.skills.get(viewcard.skillindex);
                }
                logger.info("技能" + card.skills.toString());
                logger.info("当前技能" + card.currentSkill.toString());
                logger.info("碰撞箱" + hitboxes.toString());
            }else{
                viewcard = card;
                hitboxes.clear();
            }
        }else {
            viewcard = null;
            hitboxes.clear();
        }


    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {AbstractCard.class, CardGroup.class}
    )
    public static class SCPOpenPatch{
        public SCPOpenPatch(){}

        public static void Postfix(SingleCardViewPopup _inst, AbstractCard card, CardGroup group){
            if(card instanceof AbstractOperatorCard){
                ChangeOperator((AbstractOperatorCard) card);
            }else
                ChangeOperator(null);
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {AbstractCard.class}
    )
    public static class SCPOpenPatch2{
        public SCPOpenPatch2(){}

        public static void Postfix(SingleCardViewPopup _inst, AbstractCard card){
            if(card instanceof AbstractOperatorCard){
                ChangeOperator((AbstractOperatorCard) card);
            }else
                ChangeOperator(null);
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "update"
    )
    public static class updateSecondButton {
        public updateSecondButton(){}

        public static void Postfix(SingleCardViewPopup _inst) {
            if(hitboxes.size() > 0){
                for(i = 0 ; i < hitboxes.size() ; i++){
                    SkillButton button = hitboxes.get(i);
                    button.update();
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class StopClosingButton {
        public StopClosingButton() {
        }

        public static SpireReturn<Void> Prefix(SingleCardViewPopup _inst) {
            boolean canreturn = false;
            if(hitboxes.size() > 0){
                for(i = 0 ; i < hitboxes.size() ; i++){
                    SkillButton button = hitboxes.get(i);
                    if(button.hb.hovered){
                        canreturn = true;
                        break;
                    }
                }
            }
            if(canreturn){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderSkillPatch{
        public RenderSkillPatch(){}

        public static void Postfix(SingleCardViewPopup _inst, SpriteBatch sb){
            if(hitboxes.size() > 0){
                for(i = 0 ; i < hitboxes.size() ; i++){
                    SkillButton button = hitboxes.get(i);
                    button.render(sb);
                }
            }
        }
    }

}
