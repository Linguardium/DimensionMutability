package mod.linguardium.dimute.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mod.linguardium.dimute.Main.loadConfig;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method="createWorlds",at=@At("HEAD"))
    private void reloadDimMuteConfig(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        loadConfig();
    }
}
