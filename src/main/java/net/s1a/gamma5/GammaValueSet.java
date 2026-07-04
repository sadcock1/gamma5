package net.s1a.gamma5;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;

import java.util.Optional;

// ponytail: NOT UnitDouble.xmap(...) — xmap keeps codec() = Codec.doubleRange(0,1), so
// options.txt would store the pre-image fraction (e.g. "0.2" for max), not "5.0". This
// dedicated class is the minimum correct implementation, not extra abstraction.
public final class GammaValueSet implements OptionInstance.SliderableValueSet<Double> {
    public static final GammaValueSet INSTANCE = new GammaValueSet();
    private static final double MAX = 5.0;

    private GammaValueSet() {}

    @Override
    public double toSliderValue(Double value) {
        return value / MAX;
    }

    @Override
    public Double fromSliderValue(double sliderValue) {
        return sliderValue * MAX;
    }

    @Override
    public Optional<Double> validateValue(Double value) {
        return Optional.of(Math.clamp(value, 0.0, MAX));
    }

    @Override
    public Codec<Double> codec() {
        return Codec.doubleRange(0.0, MAX);
    }
}
