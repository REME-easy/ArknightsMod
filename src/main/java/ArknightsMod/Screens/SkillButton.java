package ArknightsMod.Screens;

import ArknightsMod.Helper.ArknightsTipHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Patches.ChangeSkillPatch;
import ArknightsMod.Patches.CharacterSelectScreenPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.util.ArrayList;

public class SkillButton {
    public Hitbox hb;
    private static final float HB_W = 128.0F;
    private static final float HB_H = 128.0F;
    private float scale = 1.0F;
    private PowerTip tip;

    private static final Color NormalColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    private static final Color DarkColor = new Color(0.5F, 0.5F, 0.5F, 1.0F);

    public AbstractSkill skill;
    private int index;
    private TextureAtlas.AtlasRegion img;
    private String[] tips;

    public SkillButton(AbstractSkill skill, int index, float hb_x, float hb_y, String[] tips){
        this.skill = skill;
        this.index = index;
        this.img = skill.getTexture();
        this.tips = tips;
        this.hb = new Hitbox(HB_W, HB_H);
        this.hb.move(hb_x, hb_y);
        this.hb.update();
    }

    public void render(SpriteBatch sb){
        if(hb.hovered){
            tip = new PowerTip(tips[0], tips[1]);
            ArknightsTipHelper.queuePowerTips(hb.cX + 140.0F *Settings.scale, hb.cY, new ArrayList<PowerTip>(){{
                add(tip);
            }});
        }

        if(skill == ChangeSkillPatch.viewcard.currentSkill){
            sb.setColor(NormalColor);
        }else{
            sb.setColor(DarkColor);
        }
        sb.draw(img, hb.cX - 64.0F, hb.cY - 64.0F, img.originalWidth / 2.0F - img.offsetX, img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale * Settings.scale, scale * Settings.scale, 0.0F);
        hb.render(sb);
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
                ChangeSkillPatch.viewcard.currentSkill = skill;
                ChangeSkillPatch.viewcard.skillindex = index;
            }
        }

    }

    public void dispose() {
        hb = null;
        tip = null;
        skill = null;
        img = null;
        tips = null;
    }
}
