package ArknightsMod.Monsters;

import ArknightsMod.Helper.ArknightsTipHelper;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.Bosses.Crowns;
import ArknightsMod.Monsters.Bosses.FrostNova_1;
import ArknightsMod.Monsters.Bosses.Lslime;
import ArknightsMod.Monsters.Bosses.Patrt;
import ArknightsMod.Monsters.Common.*;
import ArknightsMod.Monsters.Common.FrostNova.Icebrk;
import ArknightsMod.Monsters.Common.FrostNova.Snbow;
import ArknightsMod.Monsters.Common.FrostNova.Snsbr;
import ArknightsMod.Monsters.Common.SlimeGather.BombSlime;
import ArknightsMod.Monsters.Common.SlimeGather.Mslime;
import ArknightsMod.Monsters.Common.SlimeGather.Slime;
import ArknightsMod.Vfx.UI.ShowTeamEffect;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static ArknightsMod.Helper.GeneralHelper.addToBot;

public class EnemyManager implements CustomSavable<String>, ISubscriber {
    private static final Logger logger = LogManager.getLogger(EnemyManager.class);

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_OPERATION");
    public static String[] TEXT = uiStrings.TEXT;

    private static EnemyManager Inst = new EnemyManager();
    private static HashMap<String, AbstractMonster> allMonsters = new HashMap<>();
    private static ArrayList<HashMap<String, ArrayList<MonsterStat>>> Teams = new ArrayList<>();

    private static String currentTeam;
    private static ArrayList<String> currentGroup = new ArrayList<>();
    private static ArrayList<MonsterStat> monsterStats = new ArrayList<>();
    private static int maxCount;
    private static boolean firstDeath = true;

    private static final int MAX_ENEMIES = 6;
    private static final int MAX_WAVE_NUM = 3;

    private static String makeID(String id) {
        return "Arknights_" + id;
    }

    private EnemyManager() {
        BaseMod.subscribe(this);
        BaseMod.addSaveField(makeID("currentTeam"), this);
    }

    public static void InitEnemies() {
        BaseMod.addMonster(makeID("Patriot"), () -> new MonsterGroup(new AbstractMonster[]{
                new Sotisd(400.0F, 140.0F, true), new Sotisd(600.0F, 140.0F, false), new Sotisd(800.0F, 140.0F, true),
                new Patrt(), new Sotisd(600.0F, 0.0F, false), new Sotisd(800.0F, 0.0F, true),
                new Sotisd(400.0F, -140.0F, true), new Sotisd(600.0F, -140.0F, false), new Sotisd(800.0F, -140.0F, true),
        }));
        Object[] objects = new Object[] {
                new Slime(), new Litamr(), new BombSlime(), new Defdrn(), new Mortar(),
                new Lunmag(), new Aoemag(), new Mslime(), new Crowns(), new Lslime(),
                new Snsbr(), new Snbow(), new Icebrk(), new FrostNova_1(),
        };
        for(Object o : objects) {
            allMonsters.put(((AbstractMonster)o).id, (AbstractMonster) o);
        }
        InitSeries();
    }

    private static void InitSeries() {
        //TODO test series

        //第一章
        Teams.add(new HashMap<>());
        //测试怪物池

        AddTeam(0, TEXT[10], new MonsterStat[] {
                new MonsterStat(1, "Slime_2"), new MonsterStat(3, "Mslime"),
                new MonsterStat(3, "BombSlime_2"), new MonsterStat(3, "Litamr"),
                new MonsterStat(99, "Lslime"),
        });

        //第二章
        Teams.add(new HashMap<>());
        AddTeam(1, TEXT[4], new MonsterStat[] {
                new MonsterStat(3, "Snsbr"), new MonsterStat(3, "Snbow"),
                new MonsterStat(7, "Icebrk"), new MonsterStat(3, "Litamr"),
                new MonsterStat(99, "Frstar_1")
        });

        //第三章
        Teams.add(new HashMap<>());
        MonsterStat[] testTeam = new MonsterStat[] {
                new MonsterStat(1, "Slime_2"), new MonsterStat(2, "BombSlime_2"),
                new MonsterStat(3, "Defdrn"), new MonsterStat(3, "Mortar_2"),
                new MonsterStat(3, "Lunmag_2"), new MonsterStat(5, "Aoemag_2"),
                new MonsterStat(3, "Mslime"), new MonsterStat(99, "Crowns"),
        };
        AddTeam(2, TEXT[1], testTeam);

        //第四章
        Teams.add(new HashMap<>());
        AddTeam(3, TEXT[1], testTeam);

    }

    private static void AddTeam(int i, String id, MonsterStat[] stat) {
        Teams.get(i).put(id, new ArrayList<>());
        Teams.get(i).get(id).addAll(Arrays.asList(stat));
    }


    private static void GenerateEnemyGroup() {
        if(AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.getCurrRoom().cannotLose = true;
            currentGroup.clear();

            //TODO value to change.
            int val = Math.max((int)(AbstractDungeon.floorNum * 0.8F), 1);
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                val /= 2;
            }

            int tmp = 0;
            monsterStats.clear();
            for(HashMap<String, ArrayList<MonsterStat>> map : Teams) {
                for(String s : map.keySet()) {
                    if(s.equals(currentTeam)) {
                        monsterStats.addAll(map.get(s));
                        break;
                    }
                }
            }

            if(monsterStats.size() > 0) {
                while (tmp < val) {
                    MonsterStat m = monsterStats.get(AbstractDungeon.merchantRng.random(0, monsterStats.size() - 1));
                    if(m.value <= val) {
                        tmp += m.value;
                        currentGroup.add(m.id);
                    }
                }
            }
            maxCount = currentGroup.size();

        }
    }

    private static void NextWave() throws IllegalAccessException, InstantiationException {
        if(!currentGroup.isEmpty()) {
            AbstractDungeon.getCurrRoom().cannotLose = true;
        }
        int rest = MAX_ENEMIES;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped() && !m.isDead) {
                rest--;
            }
        }

        if(rest > 0) {
            int num = AbstractDungeon.merchantRng.random(1, Math.min(MAX_WAVE_NUM, rest));
            for (int i = 0; i < num; i++) {
                if(!currentGroup.isEmpty()) {
                    AbstractMonster m = allMonsters.get(currentGroup.get(0)).getClass().newInstance();
                    spawnMonster(m);
                    if(m instanceof AbstractEnemy) {
                        ((AbstractEnemy) m).movePosition(m.drawX + MathUtils.random(-400.0F * Settings.scale, 300.0F * Settings.scale), m.drawY + MathUtils.random(-100.0F * Settings.scale, 350.0F * Settings.scale));
                    }
                    m.usePreBattleAction();
                    currentGroup.remove(0);
                    if (currentGroup.isEmpty() && AbstractDungeon.getCurrRoom() != null) {
                        AbstractDungeon.getCurrRoom().cannotLose = false;
                        break;
                    }
                }
            }
        }
    }

    public static void OnBattleStart() {
        GenerateEnemyGroup();
        firstDeath = true;
    }

    private static void OnTurnStart() throws InstantiationException, IllegalAccessException {
        NextWave();
    }

    public static void OnActChanged() {
        currentTeam = null;
        int act = AbstractDungeon.actNum;
        if(act > 0 && act < 5) {
            ArrayList<String> tmp = new ArrayList<>(Teams.get(act - 1).keySet());
            currentTeam = tmp.get(AbstractDungeon.merchantRng.random(0, tmp.size() - 1));
        }else if(act >= 5){
            ArrayList<String> tmp = new ArrayList<>(Teams.get(3).keySet());
            currentTeam = tmp.get(AbstractDungeon.merchantRng.random(0, tmp.size() - 1));
        }
        AbstractDungeon.topLevelEffects.add(new ShowTeamEffect(currentTeam));
        logger.info(TEXT[0] + "," +TEXT[1] + "," + EnemyManager.currentTeam);
    }
    
    private static void spawnMonster(AbstractMonster m) {

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onSpawnMonster(m);
        }

        m.init();
        m.applyPowers();
        int position = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.drawX > mo.drawX) {
                ++position;
            }
        }
        AbstractDungeon.getCurrRoom().monsters.addMonster(position, m);

        m.showHealthBar();
        if(m.type != EnemyType.BOSS)
            addToBot(new ApplyPowerAction(m, m, new MinionPower(m)));
        if (ModHelper.isModEnabled("Lethality"))
            addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 3), 3));

        if (ModHelper.isModEnabled("Time Dilation"))
            addToBot(new ApplyPowerAction(m, m, new SlowPower(m, 0)));

    }

    @Override
    public String onSave() {
        return currentTeam;
    }

    @Override
    public void onLoad(String s) {
        if(s != null) {
            currentTeam = s;
            logger.info("加载成功：" + EnemyManager.currentTeam);
        }
    }

    @Override
    public Type savedType()
    {
        return new TypeToken<String>(){}.getType();
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "renderTopRightIcons",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderExtraMapTipPatch {
        public RenderExtraMapTipPatch() {}

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(TopPanel _inst, SpriteBatch sb) {
            ArknightsTipHelper.renderGenericTip(1550.0F * Settings.scale, Settings.HEIGHT - 230.0F * Settings.scale, TEXT[0], currentTeam);
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(TipHelper.class, "renderGenericTip");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[found.length - 1]};
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "renderBlights"
    )
    public static class RenderCountPatch {
        public RenderCountPatch() {}

        public static void Postfix(AbstractPlayer _inst, SpriteBatch sb) {
            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                FontHelper.renderDeckViewTip(sb,  (maxCount - currentGroup.size()) + "/" + maxCount, Settings.HEIGHT * 0.85F, Color.WHITE);
            }
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endBattle"
    )
    public static class MonsterDiePatch {
        public MonsterDiePatch() {}

        public static SpireReturn<Void> Prefix(AbstractRoom _inst) {
            if(!currentGroup.isEmpty()) {
                return SpireReturn.Return(null);
            }else {

                return SpireReturn.Continue();
            }
        }

    }

        @SpirePatch(
            clz = MonsterGroup.class,
            method = "areMonstersBasicallyDead"
    )
    public static class MonsterDiePatch3 {
        public MonsterDiePatch3() {}

        public static SpireReturn<Boolean> Prefix(MonsterGroup _inst) {
            if(!currentGroup.isEmpty()) {
                return SpireReturn.Return(false);
            }else {

                return SpireReturn.Continue();
            }
        }

    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "applyPreTurnLogic"
    )
    public static class MonsterDiePatch4 {
        public MonsterDiePatch4() {}

        public static void Postfix(MonsterGroup _inst) throws IllegalAccessException, InstantiationException {
            EnemyManager.OnTurnStart();
        }
    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "addMonster",
            paramtypez = {int.class, AbstractMonster.class}
    )
    public static class MonsterSortPatch {
        public MonsterSortPatch() {}

        public static void Postfix(MonsterGroup _inst, int newIndex, AbstractMonster m) {
            _inst.monsters.sort(AbsMonsterComparator);
        }

        static Comparator<AbstractMonster> AbsMonsterComparator = new Comparator<AbstractMonster>() {
            @Override
            public int compare(AbstractMonster o1, AbstractMonster o2) {
                if(o1.drawY == o2.drawY) return 0;
                return o1.drawY < o2.drawY ? 1 : -1;
            }
        };
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {boolean.class}
    )
    public static class NextWavePatch {
        public NextWavePatch() {}

        public static void Prefix(AbstractMonster _inst, boolean triggerRelics) throws InstantiationException, IllegalAccessException {
            if(GeneralHelper.aliveMonstersAmount() == 0) {
                NextWave();
            }
            if(firstDeath && _inst.type == EnemyType.BOSS && AbstractDungeon.actNum < 4) {
                GenerateEnemyGroup();
                for(MonsterStat s : monsterStats) {
                    if(s.value == 99) {
                        AbstractMonster m1 = allMonsters.get(s.id).getClass().newInstance();
                        spawnMonster(m1);
                        if(m1 instanceof AbstractEnemy) {
                            ((AbstractEnemy) m1).movePosition(m1.drawX, m1.drawY);
                        }
                        m1.usePreBattleAction();
                    }
                }
                firstDeath = false;
            }
        }
    }
}
