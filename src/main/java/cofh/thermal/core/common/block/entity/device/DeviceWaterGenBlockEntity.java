package cofh.thermal.core.common.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.client.sounds.ConditionalSoundInstance;
import cofh.lib.common.fluid.FluidStorageCoFH;
import cofh.lib.common.inventory.ItemStorageCoFH;
import cofh.thermal.core.common.config.ThermalCoreConfig;
import cofh.thermal.core.common.inventory.device.DeviceWaterGenMenu;
import cofh.thermal.lib.common.block.entity.DeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.api.StorageGroup.INTERNAL;
import static cofh.lib.api.StorageGroup.OUTPUT;
import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.Constants.TANK_SMALL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.registries.TCoreBlockEntities.DEVICE_WATER_GEN_TILE;
import static cofh.thermal.core.init.registries.TCoreSounds.SOUND_DEVICE_WATER_GEN;
import static cofh.thermal.lib.util.ThermalAugmentRules.createAllowValidator;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceWaterGenBlockEntity extends DeviceBlockEntity implements ITickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_FILTER);

    protected static final int GENERATION_RATE = 250;
    protected static final Supplier<FluidStack> WATER = () -> new FluidStack(Fluids.WATER, 0);

    protected ItemStorageCoFH fillSlot = new ItemStorageCoFH(1, FluidHelper::hasFluidHandlerCap);
    protected FluidStorageCoFH tank = new FluidStorageCoFH(TANK_SMALL, e -> false).setEmptyFluid(WATER).setEnabled(() -> isActive);

    protected boolean cached;
    protected boolean valid;

    public DeviceWaterGenBlockEntity(BlockPos pos, BlockState state) {

        super(DEVICE_WATER_GEN_TILE.get(), pos, state);

        inventory.addSlot(fillSlot, INTERNAL);

        tankInv.addTank(tank, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();

        renderFluid = new FluidStack(Fluids.WATER, BUCKET_VOLUME);
    }

    @Override
    protected void updateValidity() {

        if (level == null || !level.isAreaLoaded(worldPosition, 1)) {
            return;
        }
        int adjWaterSource = 0;
        valid = false;

        BlockPos[] cardinals = new BlockPos[]{
                worldPosition.north(),
                worldPosition.south(),
                worldPosition.west(),
                worldPosition.east(),
        };
        for (BlockPos adj : cardinals) {
            FluidState state = level.getFluidState(adj);
            if (state.getType().equals(Fluids.WATER)) {
                ++adjWaterSource;
            }
        }
        if (adjWaterSource > 1) {
            valid = true;
        } else {
            tank.clear();
        }
        cached = true;
    }

    @Override
    protected void updateActiveState() {

        if (!cached) {
            updateValidity();
        }
        super.updateActiveState();
    }

    @Override
    protected boolean isValid() {

        return valid;
    }

    protected void fillFluid() {

        if (!fillSlot.isEmpty()) {
            var handler = fillSlot.getItemStack().getCapability(Capabilities.FluidHandler.ITEM);
            if (handler != null) {
                tank.drain(handler.fill(new FluidStack(tank.getFluidStack(), (int) (BUCKET_VOLUME * baseMod)), EXECUTE), EXECUTE);
                fillSlot.setItemStack(handler.getContainer());
            }
        }
    }

    @Override
    public void tickServer() {

        updateActiveState();

        if (isActive) {
            tank.modify((int) (GENERATION_RATE * baseMod));
            fillFluid();
        }
    }

    @Nonnull
    @Override
    public ModelData getModelData() {

        return ModelData.builder()
                .with(FLUID, renderFluid)
                .build();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceWaterGenMenu(i, level, worldPosition, inventory, player);
    }

    @Override
    protected Object getSound() {

        return new ConditionalSoundInstance(SOUND_DEVICE_WATER_GEN.get(), SoundSource.AMBIENT, this, () -> !remove && isActive);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
