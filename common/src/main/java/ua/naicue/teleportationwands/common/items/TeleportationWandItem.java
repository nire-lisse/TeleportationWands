package ua.naicue.teleportationwands.common.items;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import ua.naicue.teleportationwands.common.config.WandStats;
import ua.naicue.teleportationwands.common.init.CreativeTabRegistry;
import ua.naicue.teleportationwands.common.init.EnchantmentsRegistry;

import java.util.Optional;

import static ua.naicue.teleportationwands.common.utils.Utils.getTarget;

public class TeleportationWandItem extends Item {
    @Getter
    @Setter
    private WandStats wandStats;

    public TeleportationWandItem(WandStats wandStats) {
        super(new Properties().stacksTo(1).arch$tab(CreativeTabRegistry.TELEPORTATION_WAND_TAB));

        this.wandStats = wandStats;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        var enchants = player.getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        double maxDistance = this.wandStats.getMaxDistance() * (1 + 0.2 * enchants.getLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentsRegistry.MAX_DISTANCE)));
        int cooldown = (int) (this.wandStats.getCooldown() * (1 - 0.2 * enchants.getLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentsRegistry.COOLDOWN))));

        Optional<Vec3> target = getTarget(level, player, maxDistance, this.wandStats.getSafeChecks());

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
        }

        level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 1F, 1F);

        player.getCooldowns().addCooldown(this, cooldown);
        player.fallDistance = 0;

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }
}
