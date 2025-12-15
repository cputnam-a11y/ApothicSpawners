package io.github.cputnama11y.antipothicspawners.impl.modifier;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SpawnerModifier {
    public static final MapCodec<SpawnerModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> inst
            .group(
                    Ingredient.CODEC.fieldOf("mainhand").forGetter(SpawnerModifier::getMainhandInput),
                    Ingredient.CODEC.optionalFieldOf("offhand").forGetter(SpawnerModifier::getOffhandInput),
                    Codec.BOOL.optionalFieldOf("consumes_offhand", false).forGetter(SpawnerModifier::consumesOffhand),
                    StatModifier.CODEC.listOf().fieldOf("stat_changes").forGetter(SpawnerModifier::getStatModifiers))
            .apply(inst, SpawnerModifier::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SpawnerModifier> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            SpawnerModifier::getMainhandInput,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs::optional),
            SpawnerModifier::getOffhandInput,
            ByteBufCodecs.BOOL,
            SpawnerModifier::consumesOffhand,
            StatModifier.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SpawnerModifier::getStatModifiers,
            SpawnerModifier::new
    );

    protected final Ingredient mainHand;
    protected final Optional<Ingredient> offHand;
    protected final boolean consumesOffhand;
    protected final List<StatModifier<?>> statChanges;

    public SpawnerModifier(Ingredient mainHand, Optional<Ingredient> offHand, boolean consumesOffhand, List<StatModifier<?>> stats) {
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.consumesOffhand = consumesOffhand;
        this.statChanges = ImmutableList.copyOf(stats);
    }

    public SpawnerModifier(Ingredient mainHand, List<StatModifier<?>> stats) {
        this(mainHand, Optional.empty(), false, stats);
    }

    /**
     * Tests if this modifier matches the held items.
     *
     * @return If this modifier matches the given items.
     */
    public boolean matches(SpawnerBlockEntity blockEntity, ItemStack mainhand, ItemStack offhand) {
        return this.mainHand.test(mainhand)
                && this.offHand.map(it -> it.test(offhand)).orElse(true);
    }

    /**
     * Applies this modifier.
     *
     * @return If any part of the modification was successful, and items should be consumed.
     */
    public boolean apply(SpawnerBlockEntity blockEntity) {
        boolean success = false;
        for (StatModifier<?> m : this.statChanges) {
            if (m.apply(blockEntity)) {
                success = true;
                blockEntity.setChanged();
            }
        }
        return success;
    }

    public boolean consumesOffhand() {
        return this.consumesOffhand;
    }

    public Ingredient getMainhandInput() {
        return this.mainHand;
    }

    public Optional<Ingredient> getOffhandInput() {
        return this.offHand;
    }

    public List<StatModifier<?>> getStatModifiers() {
        return this.statChanges;
    }

    @Nullable
    public static SpawnerModifier findMatch(ImmutableList<SpawnerModifier> modifiers, SpawnerBlockEntity tile, ItemStack mainhand, ItemStack offhand) {
        return modifiers
                .stream()
                .sorted(
                        (r1, r2) ->
                                r1.offHand.isEmpty()
                                ? r2.offHand.isEmpty()
                                  ? 0
                                  : 1
                                : -1
                )
                .filter(r -> r.matches(tile, mainhand, offhand))
                .findFirst()
                .orElse(null);
    }
}
