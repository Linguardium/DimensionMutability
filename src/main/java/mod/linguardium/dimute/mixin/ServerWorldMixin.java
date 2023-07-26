package mod.linguardium.dimute.mixin;

import mod.linguardium.dimute.api.SubordinateLevelProperties;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static mod.linguardium.dimute.Main.config;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    @Mutable
    @Shadow @Final private ServerWorldProperties worldProperties;

    @Mutable
    @Shadow @Final private boolean shouldTickTime;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(at=@At(value="RETURN"),method="<init>")
    private void modifyServerWorldSettings(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo ci) {
        if (worldKey.equals(OVERWORLD)) {
            return;
        }
        if (properties instanceof UnmodifiableLevelProperties && (config.ApplyToMinecraftNamespace || !worldKey.getValue().getNamespace().equals("minecraft"))) {
            PersistentStateManager manager=null;
            try {
                manager = server.getOverworld().getPersistentStateManager();
            }catch (NullPointerException ignored) { }

            SubordinateLevelProperties props =new SubordinateLevelProperties(server.getSaveProperties(),server.getSaveProperties().getMainWorldProperties());
            props.loadStateFromPersistentStateManager(manager, worldKey);
            this.shouldTickTime= shouldTickTime || !debugWorld;
            this.worldProperties=props;
        }
    }

    @Redirect(at=@At(value="INVOKE",target="Lnet/minecraft/server/PlayerManager;sendToAll(Lnet/minecraft/network/Packet;)V"),method="tickWeather")
    private void sendToThisDimension(PlayerManager playerManager, Packet<?> packet) {
        playerManager.sendToDimension(packet, getRegistryKey());
    }
}
