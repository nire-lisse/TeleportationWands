package ua.naicue.teleportationwands.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import ua.naicue.teleportationwands.common.init.ConfigRegistry;
import ua.naicue.teleportationwands.common.init.EnchantmentsRegistry;
import ua.naicue.teleportationwands.common.items.TeleportationWandItem;

import java.util.Optional;

import static ua.naicue.teleportationwands.common.utils.Utils.getTarget;

public class Handler {
    public static void renderOutline(Camera camera) {
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        Level level = player.getCommandSenderWorld();

        Optional<Vec3> target = Optional.empty();

        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).getItem() instanceof TeleportationWandItem wand) {
                target = getTarget(level, player,
                        wand.getWandStats().getMaxDistance() * (1 + 0.2 * player.getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentsRegistry.MAX_DISTANCE))),
                        wand.getWandStats().getSafeChecks());
            }
        }

        if (target.isEmpty()) {
            return;
        }

        BlockPos pos = new BlockPos((int) Math.floor(target.get().x), (int) Math.floor(target.get().y), (int) Math.floor(target.get().z));

        PoseStack stack = new PoseStack();
        Vec3 cameraPos = camera.getPosition();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthFunc(GL11.GL_NEVER);

        Tesselator tesselator = Tesselator.getInstance();

        stack.pushPose();

        RenderSystem.applyModelViewMatrix();
        stack.setIdentity();
        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        double x = pos.getX();
        double y = pos.getY() - 1;
        double z = pos.getZ();

        BufferBuilder buffer;

        float red = (float) (ConfigRegistry.highlightColor.getRed() / 255f);
        float green = (float) (ConfigRegistry.highlightColor.getGreen() / 255f);
        float blue = (float) (ConfigRegistry.highlightColor.getBlue() / 255f);

        if (player.isShiftKeyDown()) {
            buffer = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
            LevelRenderer.renderLineBox(stack, buffer, x, y, z, x + 1, y + 1, z + 1, red, green, blue, 1f);
        } else {
            buffer = tesselator.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
            LevelRenderer.addChainedFilledBoxVertices(stack, buffer, x, y, z, x + 1, y + 1, z + 1,
                    red, green, blue, 0.5f);
        }

        try {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
        } catch (IllegalStateException state) {
            System.out.println(state.getMessage());
        }
        stack.popPose();
        RenderSystem.disableBlend();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableDepthTest();
        RenderSystem.lineWidth(1.0F);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}