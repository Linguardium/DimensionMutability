package mod.linguardium.dimute;

import com.google.gson.Gson;
import mod.linguardium.dimute.config.DimMuteConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main implements ModInitializer {


    public static final String MOD_ID = "dimute";
    public static final String MOD_NAME = "Dimension Mutability";
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("dimmute.json");

    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static DimMuteConfig config = new DimMuteConfig();
    @Override
    public void onInitialize() {
        Gson g=new Gson().newBuilder().setPrettyPrinting().create();
        if (!loadConfig()) {
            try {
                Files.writeString(configPath, g.toJson(config,DimMuteConfig.class), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            } catch (IOException ioException) {
                LOGGER.warn("Failed to write the Dimension Mutability config file at "+configPath,ioException);
            }
        }
    }
    public static boolean loadConfig() {
        Gson g=new Gson();
        boolean configLoaded = false;
        if (Files.exists(configPath)) {
            try {
                config = g.fromJson(Files.newBufferedReader(configPath), DimMuteConfig.class);
                configLoaded=true;
            } catch (IOException ioException) {
                LOGGER.warn("Failed to read the Dimension Mutability config file at "+configPath,ioException);
            }
        }
        return configLoaded;
    }
}