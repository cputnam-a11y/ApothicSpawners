package io.github.cputnama11y.antipothicspawners.impl.attachment;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.cputnama11y.antipothicspawners.impl.modifier.SpawnerModifier;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public class AntipothicAttachments {
//    public static final AttachmentType<Unit> REDSTONE_CONTROL = AttachmentRegistry.create(
//            id("redstone_control"),
//            builder -> builder
//                    .persistent(Unit.CODEC)
//                    .syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.all())
//    );

    public static final AttachmentType<ImmutableList<SpawnerModifier>> MODIFIERS = AttachmentRegistry.create(
            id("spawner_modifiers"),
            builder -> builder
                    .persistent(SpawnerModifier.CODEC.codec().listOf().xmap(
                            ImmutableList::copyOf,
                            Function.identity()
                    ))
                    .copyOnDeath()
                    .syncWith(SpawnerModifier.STREAM_CODEC.apply(ByteBufCodecs.list()).map(
                            ImmutableList::copyOf,
                            Function.identity()
                    ), AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<Float> INITIAL_HEALTH = AttachmentRegistry.create(id("initial_health"),
            builder -> builder.persistent(Codec.FLOAT).syncWith(ByteBufCodecs.FLOAT, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> IGNORE_PLAYERS = AttachmentRegistry.create(id("ignore_players"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> IGNORE_CONDITIONS = AttachmentRegistry.create(id("ignore_conditions"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> REDSTONE_CONTROL = AttachmentRegistry.create(id("redstone_control"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> IGNORE_LIGHT = AttachmentRegistry.create(id("ignore_light"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> NO_AI = AttachmentRegistry.create(id("no_ai"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> SILENT = AttachmentRegistry.create(id("silent"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> YOUTHFUL = AttachmentRegistry.create(id("youthful"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Boolean> BURNING = AttachmentRegistry.create(id("burning"),
            builder -> builder.persistent(Codec.BOOL).syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Integer> ECHOING = AttachmentRegistry.create(id("echoing"),
            builder -> builder.persistent(Codec.INT).syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all()));
}
