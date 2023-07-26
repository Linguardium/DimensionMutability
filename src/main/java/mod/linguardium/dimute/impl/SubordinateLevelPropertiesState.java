package mod.linguardium.dimute.impl;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.minecraft.world.level.ServerWorldProperties;

public class SubordinateLevelPropertiesState extends PersistentState {

    long time;
    long timeOfDay;
    int clearWeatherTime;
    boolean thundering;
    boolean raining;
    int thunderTime;
    int rainTime;

    public SubordinateLevelPropertiesState(long time, long timeOfDay, int clearWeatherTime, boolean raining, int rainTime, boolean thundering, int thunderTime) {
        this.time = time;
        this.timeOfDay = timeOfDay;
        this.clearWeatherTime = clearWeatherTime;
        this.thundering = thundering;
        this.raining = raining;
        this.thunderTime = thunderTime;
        this.rainTime = rainTime;
    }

    public long getTime() {
        return time;
    }

    public long getTimeOfDay() {
        return this.timeOfDay;
    }

    public int getClearWeatherTime() {
        return this.clearWeatherTime;
    }

    public void setClearWeatherTime(int clearWeatherTime) {
        this.clearWeatherTime=clearWeatherTime;
        this.markDirty();
    }

    public boolean isThundering() {
        return this.thundering;
    }

    public int getThunderTime() {
        return this.thunderTime;
    }

    public boolean isRaining() {
        return this.raining;
    }

    public int getRainTime() {
        return this.rainTime;
    }

    public void setTime(long time) {
        this.time=time;
        this.markDirty();

    }

    public void setTimeOfDay(long timeOfDay) {
        this.timeOfDay=timeOfDay;
        this.markDirty();
    }

    public void setThundering(boolean thundering) {
        this.thundering=thundering;
        this.markDirty();
    }

    public void setThunderTime(int thunderTime) {
        this.thunderTime=thunderTime;
        this.markDirty();
    }

    public void setRaining(boolean raining) {
        this.raining=raining;
        this.markDirty();
    }

    public void setRainTime(int rainTime) {
        this.rainTime=rainTime;
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putLong("Time",this.time);
        nbt.putLong("TimeOfDay",this.timeOfDay);
        nbt.putBoolean("IsRaining",this.raining);
        nbt.putLong("RainingTime",this.rainTime);
        nbt.putBoolean("IsThundering",this.thundering);
        nbt.putLong("ThunderingTime",this.thunderTime);
        nbt.putLong("ClearWeatherTime",this.clearWeatherTime);
        return nbt;
    }

    public static SubordinateLevelPropertiesState fromNbt(NbtCompound nbt) {
        return new SubordinateLevelPropertiesState(
                nbt.getLong("Time"),
                nbt.getLong("TimeOfDay"),
                nbt.getInt("ClearWeatherTime"),
                nbt.getBoolean("IsRaining"),
                nbt.getInt("RainingTime"),
                nbt.getBoolean("IsThundering"),
                nbt.getInt("ThunderTime")
        );
    }
    public static SubordinateLevelPropertiesState fromServerWorldProperties(ServerWorldProperties properties) {
        return new SubordinateLevelPropertiesState(properties.getTime(),properties.getTimeOfDay(),properties.getClearWeatherTime(),properties.isRaining(),properties.getRainTime(),properties.isThundering(),properties.getThunderTime());
    }
}
