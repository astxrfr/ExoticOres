package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.block.entity.RadioactiveBlockEntity;
import net.kirks.exoticores.block.entity.CatalyzerTableBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    ExoticOres.MODID
            );

    public static final Supplier<BlockEntityType<RadioactiveBlockEntity>>
            RADIOACTIVE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(
                    "radioactive_block_entity",
                    () -> new BlockEntityType<>(
                            RadioactiveBlockEntity::new,
                            ModBlocks.THORITE_ORE.get()
                    )
            );

    public static final Supplier<BlockEntityType<CatalyzerTableBlockEntity>>
            CATALYZER_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(
                    "catalyzer_table_entity",
                    () -> new BlockEntityType<>(
                            CatalyzerTableBlockEntity::new,
                            ModBlocks.CATALYZER_TABLE_BLOCK.get()
                    )
            );

    private ModBlockEntities() {
    }
}