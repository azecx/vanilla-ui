package cx.aze.vanillaui.mixin;

import cx.aze.vanillaui.modules.VanillaTAB;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    private void injectVanillaTabName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        VanillaTAB module = Modules.get().get(VanillaTAB.class);
        if (module != null && module.isActive()) {
            cir.setReturnValue(module.getPlayerName(entry));
        }
    }
}
