package ArknightsMod.Actions;

import ArknightsMod.Core.Arknights;
import ArknightsMod.Screens.ArknighsTutorial;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import java.io.IOException;

public class TutorialAction extends AbstractGameAction {

    public int code;

    public TutorialAction(int code) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.code = code;
    }

    public void update() {
        if (Arknights.activeTutorials[this.code]) {
            AbstractDungeon.ftue = new ArknighsTutorial();
            Arknights.activeTutorials[this.code] = false;
            try {
                Arknights.saveData();
                this.isDone = true;
            } catch (IOException e) {
                e.printStackTrace();
                this.isDone = true;
            }
        }
    }
}

