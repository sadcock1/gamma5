package net.s1a.gamma5.mixin;

import net.s1a.gamma5.GammaValueSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Options.class)
public abstract class OptionsGammaMixin {

    @Shadow @Final @Mutable
    private OptionInstance<Double> gamma;

    // Runs right before this.load() (the last statement of the ctor) so load()'s codec()
    // lookup sees OUR OptionInstance already installed in the field.
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;load()V"))
    private void gamma5$widenGamma(Minecraft minecraft, File optionsFile, CallbackInfo ci) {
        this.gamma = new OptionInstance<>(
            "options.gamma",
            OptionInstance.noTooltip(),
            // ponytail: dropped vanilla's Moody/None/Bright special-casing at 0/50/100 —
            // doesn't make sense once the range extends past 100% anyway.
            (caption, value) -> Options.genericValueLabel(caption, (int) (value * 100)),
            GammaValueSet.INSTANCE,
            0.5,
            OptionInstance.NO_ACTION
        );
    }
}
