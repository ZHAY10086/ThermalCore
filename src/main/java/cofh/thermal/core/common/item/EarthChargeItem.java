package cofh.thermal.core.common.item;

import cofh.core.common.item.ItemCoFH;
import cofh.thermal.core.common.entity.projectile.BasalzProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import static cofh.lib.util.Utils.destroyBlock;
import static net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE;
import static net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL;

public class EarthChargeItem extends ItemCoFH {

    public EarthChargeItem(Properties builder) {

        super(builder);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getClickedFace(), context.getItemInHand()))) {
        //            return ActionResultType.FAIL;
        //        }
        if (state.is(MINEABLE_WITH_SHOVEL) || state.is(MINEABLE_WITH_PICKAXE)) {
            destroyBlock(world, pos, true, player);
            playUseSound(world, pos);
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private void playUseSound(Level worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, worldIn.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F + 1.0F);
    }

    // region DISPENSER BEHAVIOR
    private static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {

            Direction direction = source.state().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.level();
            double d3 = world.random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = world.random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = world.random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(new BasalzProjectile(d0, d1, d2, d3, d4, d5, world));
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {

            source.level().levelEvent(1018, source.pos(), 0);
        }
    };
    // endregion
}
