package ArknightsMod.Helper;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundInfo;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;

public class ArknightsSoundMaster {
    public static HashMap<String, Sfx> map = new HashMap<>();

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.audio.SoundMaster",
            method = "play",
            paramtypes = {"java.lang.String", "boolean"}
    )
    public static class SoundMasterplayPatch {

        public SoundMasterplayPatch() {
        }

        public static long Postfix(long res, SoundMaster _inst, String key, boolean useBgmVolume) {
            if (map.containsKey(key)) {
                return useBgmVolume ? (map.get(key)).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME) : (map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
            } else {
                return res;
            }
        }

        private static Sfx load(String filename) {
            return new Sfx("ArkAudios/" + filename, false);
        }

        static {
            map.put("angel_start", load("Angel/angel_start.ogg"));
            map.put("angel_skill", load("Angel/angel_skill.ogg"));
            map.put("THRMEX_start", load("THRMEX/THRMEX_start.ogg"));
            map.put("THRMEX_skill", load("THRMEX/THRMEX_skill.ogg"));
            map.put("svash_start", load("Svash/svash_start.ogg"));
            map.put("svash_skill", load("Svash/svash_skill.ogg"));
            map.put("texas_start", load("Texas/texas_start.ogg"));
            map.put("texas_skill", load("Texas/texas_skill.ogg"));
            map.put("yato_start", load("Yato/yato_start.ogg"));
            map.put("noircorne_start", load("NoirCorne/noircorne_start.ogg"));
            map.put("amgoat_start", load("Eyjafjalla/amgoat_start.ogg"));
            map.put("amgoat_skill", load("Eyjafjalla/amgoat_skill.ogg"));
            map.put("ranger_start", load("Rangers/ranger_start.ogg"));
            map.put("12F_start", load("12F/12F_start.ogg"));
            map.put("durin_start", load("Durin/durin_start.ogg"));
            map.put("midn_start", load("Midnight/midn_start.ogg"));
            map.put("midn_skill", load("Midnight/midn_skill.ogg"));
            map.put("orchid_start", load("Orchid/orchid_start.ogg"));
            map.put("orchid_skill", load("Orchid/orchid_skill.ogg"));
            map.put("spot_start", load("Spot/spot_start.ogg"));
            map.put("spot_skill", load("Spot/spot_skill.ogg"));
            map.put("ansel_start", load("Ansel/ansel_start.ogg"));
            map.put("ansel_skill", load("Ansel/ansel_skill.ogg"));
            map.put("papukar_start", load("Papukar/papukar_start.ogg"));
            map.put("papukar_skill", load("Papukar/papukar_skill.ogg"));
            map.put("catap_start", load("Catap/catap_start.ogg"));
            map.put("catap_skill", load("Catap/catap_skill.ogg"));
            map.put("steward_start", load("Steward/steward_start.ogg"));
            map.put("steward_skill", load("Steward/steward_skill.ogg"));
            map.put("kroos_start", load("Kroos/kroos_start.ogg"));
            map.put("kroos_skill", load("Kroos/kroos_skill.ogg"));
            map.put("hibisc_start", load("Hibisc/hibisc_start.ogg"));
            map.put("hibisc_skill", load("Hibisc/hibisc_skill.ogg"));
            map.put("adnach_start", load("Adnach/adnach_start.ogg"));
            map.put("adnach_skill", load("Adnach/adnach_skill.ogg"));
            map.put("lava_start", load("Lava/lava_start.ogg"));
            map.put("lava_skill", load("Lava/lava_skill.ogg"));
            map.put("liskarm_start", load("Liskarm/liskarm_start.ogg"));
            map.put("liskarm_skill", load("Liskarm/liskarm_skill.ogg"));
            map.put("bluep_start", load("Bluep/bluep_start.ogg"));
            map.put("bluep_skill", load("Bluep/bluep_skill.ogg"));
            map.put("red_start_1", load("Red/red_start_1.ogg"));
            map.put("red_start_2", load("Red/red_start_2.ogg"));
            map.put("lappland_start", load("Lappland/lappland_start.ogg"));
            map.put("lappland_skill", load("Lappland/lappland_skill.ogg"));
            map.put("haze_start", load("Haze/haze_start.ogg"));
            map.put("haze_skill", load("Haze/haze_skill.ogg"));
            map.put("gitano_start", load("Gitano/gitano_start.ogg"));
            map.put("gitano_skill", load("Gitano/gitano_skill.ogg"));
            map.put("meteor_start", load("Meteor/meteor_start.ogg"));
            map.put("meteor_skill", load("Meteor/meteor_skill.ogg"));
            map.put("jessica_start", load("Jessica/jessica_start.ogg"));
            map.put("jessica_skill", load("Jessica/jessica_skill.ogg"));
            map.put("fang_start", load("Fang/fang_start.ogg"));
            map.put("fang_skill", load("Fang/fang_skill.ogg"));
            map.put("chen_start", load("Chen/chen_start.ogg"));
            map.put("chen_skill_1", load("Chen/chen_skill_1.ogg"));
            map.put("chen_skill_2", load("Chen/chen_skill_2.ogg"));
            map.put("chen_skill_3", load("Chen/chen_skill_3.ogg"));
            map.put("gravel_start_1", load("Gravel/gravel_start_1.ogg"));
            map.put("gravel_start_2", load("Gravel/gravel_start_2.ogg"));
            map.put("mayer_start", load("Mayer/mayer_start.ogg"));
            map.put("mayer_skill", load("Mayer/mayer_skill.ogg"));
            map.put("specter_start", load("Specter/specter_start.ogg"));
            map.put("specter_skill", load("Specter/specter_skill.ogg"));
            map.put("nearl_start", load("Nearl/nearl_start.ogg"));
            map.put("nearl_skill", load("Nearl/nearl_skill.ogg"));
            map.put("courier_start", load("Courier/courier_start.ogg"));
            map.put("courier_skill", load("Courier/courier_skill.ogg"));
            map.put("scavenger_start", load("Scavenger/scavenger_start.ogg"));
            map.put("scavenger_skill", load("Scavenger/scavenger_skill.ogg"));
            map.put("vigna_start", load("Vigna/vigna_start.ogg"));
            map.put("vigna_skill", load("Vigna/vigna_skill.ogg"));
            map.put("castle3_start", load("Castle3/castle3_start.ogg"));
            map.put("lancet2_start", load("Lancet2/lancet2_start.ogg"));

            map.put("svash_3", load("Svash/svash_3.ogg"));
            map.put("red_2", load("Red/red_2.ogg"));
            map.put("chen_2", load("Chen/chen_2.ogg"));
            map.put("chen_3", load("Chen/chen_3.ogg"));
            map.put("char_dead", load("_Common/char_dead.ogg"));
            map.put("char_set", load("_Common/char_set.ogg"));
            map.put("atkboost", load("_Common/atkboost.ogg"));
            map.put("defboost", load("_Common/defboost.ogg"));
            map.put("healboost", load("_Common/healboost.ogg"));
            map.put("tactboost", load("_Common/tactboost.ogg"));
            map.put("tactfulboost", load("_Common/tactfulboost.ogg"));
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.audio.SoundMaster",
            method = "playA",
            paramtypes = {"java.lang.String", "float"}
    )
    public static class SoundMasterplayAPatch {
        public static HashMap<String, Sfx> map = new HashMap<>();

        public SoundMasterplayAPatch() {
        }

        public static long Postfix(long res, SoundMaster _inst, String key, float pitchAdjust) {
            return map.containsKey(key) ? (map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0F + pitchAdjust, 0.0F) : 0L;
        }

        private static Sfx load(String filename) {
            return new Sfx("ArkAudios/" + filename, false);
        }

        static {

        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.audio.SoundMaster",
            method = "update"
    )
    public static class SoundMasterUpdatePatch {

        public SoundMasterUpdatePatch() {
        }

        @SpireInsertPatch(
                rloc = 4,
                localvars = {"e", "sfx"}
        )
        public static void Insert(SoundMaster _inst, SoundInfo e, @ByRef(type = "com.megacrit.cardcrawl.audio.Sfx") Object[] sfx) {
            if (sfx[0] == null) {
                sfx[0] = map.get(e.name);
            }

        }
    }
}
