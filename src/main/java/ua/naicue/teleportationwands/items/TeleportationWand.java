package ua.naicue.teleportationwands.items;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import ua.naicue.teleportationwands.Config;
import ua.naicue.teleportationwands.network.NetworkHandler;
import ua.naicue.teleportationwands.network.packets.PacketPlayerMotion;

import java.util.Optional;

import static ua.naicue.teleportationwands.utils.Utils.getTarget;

@Getter
public class TeleportationWand extends Item {
    private final double maxDistance;
    private final int cooldown;
    private final int safeChecks;

    int tickCount = 0;

    public TeleportationWand(Config.WandStats stats) {
        super(new Properties().stacksTo(1));

        this.maxDistance = stats.maxDistance.get();
        this.cooldown = stats.cooldown.get();
        this.safeChecks = stats.safeChecks.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        Optional<Vec3> target = getTarget(level, player, this);

        if (target.isEmpty()) {
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));
        }

        ServerLevel server = (ServerLevel) level;

        server.sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 50, 0, 0, 0, 0.1);
        server.sendParticles(ParticleTypes.WITCH, target.get().x, target.get().y, target.get().z, 50, 0, 0, 0, 0.1);

        Vec3 current = player.position();

        player.teleportTo(target.get().x, target.get().y, target.get().z);

        if (!player.isShiftKeyDown()) {
            BlockPos pos = new BlockPos((int) target.get().x, (int) target.get().y + 1, (int) target.get().z);

            if (level.getBlockState(pos).blocksMotion()) {
                player.setPose(Pose.SWIMMING);
                player.setSprinting(false);
            }

            Vec3 motion = player.getDeltaMovement().add(target.get().subtract(current).normalize());

            NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), (ServerPlayer) player);

            player.setDeltaMovement(Vec3.ZERO);
        }

        player.getCooldowns().addCooldown(this, cooldown);
        player.fallDistance = 0;

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
