package io.github.cputnama11y.anitpothicspawners.impl.compat.wthit;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import mcp.mobius.waila.api.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jspecify.annotations.NullMarked;
import org.lwjgl.glfw.GLFW;

@NullMarked
public class SpawnerPeakingPlugin implements IWailaClientPlugin {
    @Override
    public void register(IClientRegistrar registrar) {
        registrar.body(new IBlockComponentProvider() {
            @Override
            public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
                if (hasControlDown()) {
                    var blockEntity = accessor.getBlockEntity();
                    if (!(blockEntity instanceof SpawnerBlockEntity spawner)) return;
                    SpawnerStats.generateTooltip(spawner, tooltip::addLine);
                } else {
                    tooltip.addLine(AntipothicSpawners.lang("misc", "ctrl_stats"));
                }
            }
        }, SpawnerBlockEntity.class);
    }

    private static boolean hasControlDown() {
        return Util.getPlatform().equals(Util.OS.OSX)
               ? InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_LEFT_SUPER)
                       || InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_RIGHT_SUPER)
               : InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)
                       || InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_RIGHT_CONTROL);
    }
}
