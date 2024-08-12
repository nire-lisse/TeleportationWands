package ua.naicue.teleportationwands.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import ua.naicue.teleportationwands.items.TeleportationWand;

import java.util.Optional;

public class Utils {
    public static Optional<Vec3> getTarget(Level level, Entity entity, TeleportationWand wand) {
        double maxDistance = wand.getMaxDistance();
        int safeChecks = wand.getSafeChecks();

        Vec3 view = entity.getViewVector(0);
        Vec3 eyeVec = entity.getEyePosition(0);
        BlockHitResult ray = level.clip(
                new ClipContext(eyeVec, eyeVec.add(view.x * maxDistance, view.y * maxDistance, view.z * maxDistance),
                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));

        if (entity.isShiftKeyDown()) {
            BlockPos pos = ray.getBlockPos();

            if (!level.getBlockState(pos).isSolid() || level.getBlockState(pos = pos.above()).liquid()) {
                return Optional.empty();
            }

            for (int i = 0; i < safeChecks; i++) {
                if (level.getBlockState(pos).blocksMotion() || level.getBlockState(pos.above()).blocksMotion()) {
                    pos = pos.above();

                    continue;
                }

                return Optional.of(pos.getBottomCenter());
            }
        } else {
            Vec3 target = ray.getLocation();

            target = target.subtract(view);

            return Optional.of(target);
        }

        return Optional.empty();
    }
}
