package ArknightsMod.Patches;

import ArknightsMod.Helper.OperatorHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Screens.OperatorSelectButton;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;
import java.util.Iterator;

import static ArknightsMod.Patches.PlayerClassEnum.ARKNIGHTS_CERBER;

public class CharacterSelectScreenPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT").TEXT;
    public static int operatorRequiredNum = 4;
    public static ArrayList<AbstractOperator> readyOperators = new ArrayList<>();
    public static GridCardSelectScreen SelectScreen = new GridCardSelectScreen();
    public static boolean isSelectScreenOpen = false;

    public static void openSelectScreen(){
        isSelectScreenOpen = true;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        tmp.group.addAll(OperatorHelper.allOperatorsCards.values());
        SelectScreen.open(tmp, operatorRequiredNum, true, TEXT[0] + operatorRequiredNum + TEXT[1]);
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "<class>"
    )
    public static class OperatorSelectField {
        public static SpireField<ArrayList<OperatorSelectButton>> OptionList = new SpireField(() -> {
            return new ArrayList<>();
        });

        public OperatorSelectField() {
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitializeOperatorsPatch{
        public InitializeOperatorsPatch(){}

        public static void Postfix(CharacterSelectScreen _inst){
            if(OperatorSelectField.OptionList.get(_inst) != null && OperatorSelectField.OptionList.get(_inst).size() > 0){
                OperatorSelectField.OptionList.get(_inst).clear();

            }
            if(OperatorSelectField.OptionList.get(_inst) != null){
                OperatorSelectField.OptionList.get(_inst).add(new OperatorSelectButton());
                OperatorSelectField.OptionList.get(_inst).add(new OperatorSelectButton());
                OperatorSelectField.OptionList.get(_inst).add(new OperatorSelectButton());
                OperatorSelectField.OptionList.get(_inst).add(new OperatorSelectButton());
            }
            if(OperatorSelectField.OptionList.get(_inst) != null && OperatorSelectField.OptionList.get(_inst).size() > 0) {
                Iterator var4 = OperatorSelectField.OptionList.get(_inst).iterator();
                int i = 0;
                while(var4.hasNext()){
                    OperatorSelectButton o = (OperatorSelectButton) var4.next();
                    o.hb.move(Settings.WIDTH - 160.0F - 200.0F * i, Settings.HEIGHT / 2.0F);
                    i++;
                }
            }

        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class UpdateOperatorsPatch{
        public UpdateOperatorsPatch(){}

        public static void Postfix(CharacterSelectScreen _inst){
            if((boolean)ReflectionHacks.getPrivate(_inst, CharacterSelectScreen.class, "anySelected") && CardCrawlGame.chosenCharacter == ARKNIGHTS_CERBER){
                if(OperatorSelectField.OptionList.get(_inst) != null && OperatorSelectField.OptionList.get(_inst).size() > 0) {
                    Iterator var2 = (OperatorSelectField.OptionList.get(_inst)).iterator();

                    while(var2.hasNext()) {
                        OperatorSelectButton o = (OperatorSelectButton) var2.next();
                        o.update();
                    }
                }


                if(isSelectScreenOpen)
                    SelectScreen.update();

                if(SelectScreen.selectedCards.size() != 0){
                    if(readyOperators != null){
                        readyOperators.clear();
                    }else{
                        readyOperators = new ArrayList<>();
                    }


                    if(OperatorSelectField.OptionList.get(_inst) != null && OperatorSelectField.OptionList.get(_inst).size() > 0) {
                        int i;
                        for(i = 0 ; i < readyOperators.size() ; i++){
                            OperatorSelectField.OptionList.get(_inst).get(i).changeOperator(readyOperators.get(i));
                        }
                    }

                }
            }
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderOperatorsPatch{
        public RenderOperatorsPatch(){}

        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb){
            if((boolean)ReflectionHacks.getPrivate(_inst, CharacterSelectScreen.class, "anySelected") && CardCrawlGame.chosenCharacter != null && CardCrawlGame.chosenCharacter == ARKNIGHTS_CERBER) {
                if(OperatorSelectField.OptionList.get(_inst) != null && OperatorSelectField.OptionList.get(_inst).size() > 0) {
                    Iterator var1 = (OperatorSelectField.OptionList.get(_inst)).iterator();

                    while(var1.hasNext()) {
                        OperatorSelectButton o = (OperatorSelectButton) var1.next();
                        o.render(sb);
                    }
                }


                if(SelectScreen != null && isSelectScreenOpen)
                    SelectScreen.render(sb);
            }

        }
    }


}
