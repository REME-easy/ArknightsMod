package ArknightsMod.Screens;

import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Patches.CharacterSelectScreenPatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class OperatorSelectButton {
    public Hitbox hb;
    private static final float HB_W = 160.0F;
    private static final float HB_H = 300.0F;
    private float scale = 1.0F;

    private static Texture backImg = ImageMaster.loadImage("ArkImg/char/SelectButton_Back.png");
    private Texture operatorImg;
    private static Texture frontImg = ImageMaster.loadImage("ArkImg/char/SelectButton_Front.png");

    private AbstractOperator operator;
    private String name;

    public OperatorSelectButton(AbstractOperator operator){
        if(operator != null){
            this.operator = operator;
            this.name = operator.name;

        }
        this.hb = new Hitbox(HB_W, HB_H);
    }

    public OperatorSelectButton(){
        this(null);
    }

    public void changeOperator(AbstractOperator toChange){
        this.operator = toChange;
        this.name = this.operator.name;
    }

    public void render(SpriteBatch sb){
        this.renderOptionButton(sb);
        this.hb.render(sb);

    }

    public void renderOptionButton(SpriteBatch sb){

        sb.draw(backImg, this.hb.cX - 125.0F, this.hb.cY - 163.5F, 125.0F, 163.5F, 250.0F, 327.0F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 250, 327, false, false);
        if(operatorImg != null)
            sb.draw(operatorImg, this.hb.cX - 125.0F, this.hb.cY - 163.5F, 125.0F, 163.5F, 250.0F, 327.0F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 250, 327, false, false);
        sb.draw(frontImg, this.hb.cX - 125.0F, this.hb.cY - 163.5F, 125.0F, 163.5F, 250.0F, 327.0F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 250, 327, false, false);

    }

    public void update(){
        updateHitBox();
    }

    private void updateHitBox(){
        this.hb.update();
        if(!CharacterSelectScreenPatch.isSelectScreenOpen){
            if (this.hb.justHovered) {
                CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
            }

            if(this.hb.hovered){
                this.scale = 1.1F;
            }else{
                this.scale = 1.0F;
            }

            if (InputHelper.justClickedLeft && this.hb.hovered) {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
                this.hb.clickStarted = true;
            }

            if (this.hb.clicked) {
                this.hb.clicked = false;
                //CharacterSelectScreenPatch.openSelectScreen();
                //CharacterSelectScreenPatch.isSelectScreenOpen = true;
            }
        }

    }
}
