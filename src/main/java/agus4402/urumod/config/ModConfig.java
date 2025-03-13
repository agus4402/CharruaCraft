package agus4402.urumod.config;


import agus4402.urumod.Urumod;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModConfig {
    private static final String LANG_PREFIX = "config." + Urumod.MOD_ID + ".";

    public static final Common COMMON;
    public static final Client CLIENT;

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    static {
        COMMON = new Common(COMMON_BUILDER);
        CLIENT = new Client(CLIENT_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void register(ModLoadingContext context){
        context.registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, CLIENT_CONFIG, getFileName("common"));
        context.registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, COMMON_CONFIG, getFileName("client"));
        sync(COMMON_CONFIG, getFileName("common"));
        sync(CLIENT_CONFIG, getFileName("client"));
    }

    private static String getFileName(String name){
        return Urumod.MOD_ID + "-" + name + ".toml";
    }

    private static void sync(ForgeConfigSpec config, String file){
        final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(file).toString())
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        config.setConfig(configData);
    }

    public static class Mobs {
        private Mobs(final ForgeConfigSpec.Builder builder) {
            builder.push("mob_config");
            this.capybaraLooksIntoPlayer = builder.comment("Set true to Capybara look into players")
                    .translation(LANG_PREFIX + "capybara_looks_into_player")
                    .define("capybara_looks_into_player", true);
            builder.pop();
        }

        public final ForgeConfigSpec.BooleanValue capybaraLooksIntoPlayer;

    }

    public static class Client {
        private Client(ForgeConfigSpec.Builder builder) {

        }
    }

    public static class Common {
        private Common(final ForgeConfigSpec.Builder builder) {
            MOBS = new Mobs(builder);
        }

        public final Mobs MOBS;
    }
}
