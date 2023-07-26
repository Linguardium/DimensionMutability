package mod.linguardium.dimute.api;

import mod.linguardium.dimute.impl.SubordinateLevelPropertiesState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.timer.Timer;

import java.util.UUID;

import static mod.linguardium.dimute.Main.MOD_ID;

public class SubordinateLevelProperties implements ServerWorldProperties {
    private final SaveProperties saveProperties;
    private final ServerWorldProperties parentProperties;
    private SubordinateLevelPropertiesState timeState;

    boolean initialized=false;

    public SubordinateLevelProperties(SaveProperties saveProperties, ServerWorldProperties parentProperties) {
        this.saveProperties = saveProperties;
        this.parentProperties=parentProperties;
    }
    public void loadStateFromPersistentStateManager(PersistentStateManager manager, RegistryKey<World> worldRegistryKey) {
        if (manager != null) {
            this.timeState = manager.getOrCreate(SubordinateLevelPropertiesState::fromNbt, this::fromParentProperties, MOD_ID + "$"+ worldRegistryKey.getValue().toString().replace(":","$"));
        }else{
            this.timeState=this.fromParentProperties();
        }
        this.setInitialized(true);
    }
    public SubordinateLevelPropertiesState fromParentProperties() {
        return SubordinateLevelPropertiesState.fromServerWorldProperties(parentProperties);
    }
    public int getSpawnX() {
        return this.parentProperties.getSpawnX();
    }

    public int getSpawnY() {
        return this.parentProperties.getSpawnY();
    }

    public int getSpawnZ() {
        return this.parentProperties.getSpawnZ();
    }

    public float getSpawnAngle() {
        return this.parentProperties.getSpawnAngle();
    }

    public String getLevelName() {
        return this.saveProperties.getLevelName();
    }

    public long getTime() {
        return timeState.getTime();
    }

    public long getTimeOfDay() {
        return timeState.getTimeOfDay();
    }

    public int getClearWeatherTime() {
        return timeState.getClearWeatherTime();
    }

    public void setClearWeatherTime(int clearWeatherTime) {
        timeState.setClearWeatherTime(clearWeatherTime);
    }

    public boolean isThundering() {
        return timeState.isThundering();
    }

    public int getThunderTime() {
        return timeState.getThunderTime();
    }

    public boolean isRaining() {
        return timeState.isRaining();
    }

    public int getRainTime() {
        return timeState.getRainTime();
    }

    public GameMode getGameMode() {
        return this.saveProperties.getGameMode();
    }

    public void setSpawnX(int spawnX) {
    }

    public void setSpawnY(int spawnY) {
    }

    public void setSpawnZ(int spawnZ) {
    }

    public void setSpawnAngle(float spawnAngle) {
    }

    public void setTime(long time) {
        timeState.setTime(time);
    }

    public void setTimeOfDay(long timeOfDay) {
        timeState.setTimeOfDay(timeOfDay);
    }

    public void setSpawnPos(BlockPos pos, float angle) {
    }

    public void setThundering(boolean thundering) {
        timeState.setThundering(thundering);
    }

    public void setThunderTime(int thunderTime) {
        timeState.setThunderTime(thunderTime);
    }

    public void setRaining(boolean raining) {
        timeState.setRaining(raining);
    }

    public void setRainTime(int rainTime) {
        timeState.setRainTime(rainTime);
    }

    public void setGameMode(GameMode gameMode) {
    }

    public boolean isHardcore() {
        return this.saveProperties.isHardcore();
    }

    public boolean areCommandsAllowed() {
        return this.saveProperties.areCommandsAllowed();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized=initialized;
    }

    public GameRules getGameRules() {
        return this.saveProperties.getGameRules();
    }

    public WorldBorder.Properties getWorldBorder() {
        return this.parentProperties.getWorldBorder();
    }

    public void setWorldBorder(WorldBorder.Properties worldBorder) {
    }

    public Difficulty getDifficulty() {
        return this.saveProperties.getDifficulty();
    }

    public boolean isDifficultyLocked() {
        return this.saveProperties.isDifficultyLocked();
    }

    public Timer<MinecraftServer> getScheduledEvents() {
        return this.parentProperties.getScheduledEvents();
    }

    // TODO: Decouple wandering trader spawner
    // -----------------------------------------
    public int getWanderingTraderSpawnDelay() {
        return 0;
    }

    public void setWanderingTraderSpawnDelay(int wanderingTraderSpawnDelay) {
    }

    public int getWanderingTraderSpawnChance() {
        return 0;
    }

    public void setWanderingTraderSpawnChance(int wanderingTraderSpawnChance) {
    }

    public UUID getWanderingTraderId() {
        return null;
    }

    public void setWanderingTraderId(UUID wanderingTraderId) {
    }
    // ------------------------------------------------

    public void populateCrashReport(CrashReportSection reportSection, HeightLimitView world) {
        reportSection.add("Derived", true);
        this.parentProperties.populateCrashReport(reportSection, world);
    }
}
