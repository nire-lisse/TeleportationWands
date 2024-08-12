package ua.naicue.teleportationwands.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.lwjgl.opengl.GL11;
import ua.naicue.teleportationwands.Config;
import ua.naicue.teleportationwands.TeleportationWands;
import ua.naicue.teleportationwands.items.TeleportationWand;

import java.util.Optional;

import static ua.naicue.teleportationwands.utils.Utils.getTarget;


@EventBusSubscriber(modid = TeleportationWands.MODID)
public class Handler {
    @SubscribeEvent
    public static void RenderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            return;
        }

        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        Level level = player.getCommandSenderWorld();

        Optional<Vec3> target = Optional.empty();

        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).getItem() instanceof TeleportationWand wand) {
                target = getTarget(level, player, wand);
            }
        }

        if (target.isEmpty()) {
            return;
        }

        BlockPos pos = new BlockPos((int) Math.floor(target.get().x), (int) Math.floor(target.get().y), (int) Math.floor(target.get().z));

        PoseStack stack = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();

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
        stack.translate(-camera.x, -camera.y, -camera.z);

        double x = pos.getX();
        double y = pos.getY() - 1;
        double z = pos.getZ();

        BufferBuilder buffer;

        float red = Config.color.red.get().floatValue() / 255f;
        float green = Config.color.red.get().floatValue() / 255f;
        float blue = Config.color.red.get().floatValue() / 255f;

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
