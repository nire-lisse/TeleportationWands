package ua.naicue.teleportationwands.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {
    public static Optional<Vec3> getTarget(Level level, Entity entity, double maxDistance, int safeChecks) {
        Vec3 view = entity.getViewVector(0);
        Vec3 eyeVec = entity.getEyePosition(0);

        List<BlockPos> list = rayTracing(level, eyeVec, view, maxDistance);

        if (entity.isShiftKeyDown()) {
            BlockPos pos = list.getLast();

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
            if (list.size() == 1) {
                return Optional.empty();
            }

            BlockPos pos = list.get(list.size() - 2);

            return Optional.of(pos.getBottomCenter());
        }

        return Optional.empty();
    }


    public static List<BlockPos> rayTracing(Level level, Vec3 start, Vec3 view, double maxDistance) {
        List<BlockPos> list = new ArrayList<>();

        list.add(new BlockPos((int) Math.floor(start.x), (int) Math.floor(start.y), (int) Math.floor(start.z)));

        for (int i = 0; i < maxDistance * 2; i++) {
            BlockPos blockPos = new BlockPos((int) Math.floor(start.x), (int) Math.floor(start.y), (int) Math.floor(start.z));
            start = start.add(view.multiply(0.5, 0.5, 0.5));

            if (list.getLast().getCenter().equals(blockPos.getCenter())) {
                continue;
            }

            list.add(blockPos);

            if (level.getBlockState(blockPos).isSolid()) {
                return list;
            }
        }

        return list;
    }
}
