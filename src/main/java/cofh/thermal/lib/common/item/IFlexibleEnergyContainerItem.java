package cofh.thermal.lib.common.item;

import cofh.lib.api.item.IEnergyContainerItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IFlexibleEnergyContainerItem extends IEnergyContainerItem {

    default Capability<? extends IEnergyStorage> getEnergyCapability() {

        return ThermalEnergyHelper.getBaseEnergySystem();
    }

}
