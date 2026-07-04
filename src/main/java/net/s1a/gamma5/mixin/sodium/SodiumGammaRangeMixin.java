package net.s1a.gamma5.mixin.sodium;

import net.caffeinemc.mods.sodium.client.gui.SodiumConfigBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SodiumConfigBuilder.class)
public abstract class SodiumGammaRangeMixin {

    // ordinal=2: gamma is the 3rd of 4 setRange(III) calls in buildGeneralPage
    // (render distance, simulation distance, gamma, then one more later).
    @ModifyArg(
        method = "buildGeneralPage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/caffeinemc/mods/sodium/api/config/structure/IntegerOptionBuilder;setRange(III)Lnet/caffeinemc/mods/sodium/api/config/structure/IntegerOptionBuilder;",
            ordinal = 2
        ),
        index = 1
    )
    private int gamma5$widenGammaMax(int max) {
        return 500;
    }
}
