package ua.naicue.teleportationwands.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import ua.naicue.teleportationwands.network.NetworkHandler;
import ua.naicue.teleportationwands.network.packets.PacketPlayerMotion;


public class TeleportationWand extends Item {
    private final double maxDistance;
    private final int cooldown;
    private final int safeChecks;

    public TeleportationWand(double maxDistance, int cooldown, int safeChecks) {
        super(new Properties().stacksTo(1));

        this.maxDistance = maxDistance;
        this.cooldown = cooldown;
        this.safeChecks = safeChecks;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {return InteractionResultHolder.success(player.getItemInHand(usedHand));}

        Vec3 view = player.getViewVector(0);
        Vec3 eyeVec = player.getEyePosition(0);
        BlockHitResult ray = level.clip(
                new ClipContext(eyeVec, eyeVec.add(view.x * maxDistance, view.y * maxDistance, view.z * maxDistance),
                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        if (player.isShiftKeyDown()) {
            BlockPos pos = ray.getBlockPos();

            if (!level.getBlockState(pos).isSolid() || level.getBlockState(pos = pos.above()).liquid()) {
                return InteractionResultHolder.fail(player.getItemInHand(usedHand));
            }

            for (int i = 0; i < safeChecks; i++) {
                if (level.getBlockState(pos).blocksMotion() || level.getBlockState(pos.above()).blocksMotion()) {
                    pos = pos.above();

                    continue;
                }

                player.getCooldowns().addCooldown(this, cooldown);

                player.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ()+ 0.5d);

                break;
            }

            player.fallDistance = 0;

            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        Vec3 current = player.position();
        Vec3 target = ray.getLocation();

        Vec3 motion = player.getDeltaMovement().add(target.subtract(current).normalize());

        player.teleportTo(target.x - view.x, target.y - view.y, target.z - view.z);

        NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), (ServerPlayer) player);

        player.getCooldowns().addCooldown(this, cooldown);

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
