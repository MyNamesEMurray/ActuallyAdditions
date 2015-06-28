package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheWildPlants;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.ArrayList;
import java.util.Random;

public class WorldDecorationEvent{

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent event){
        if(ConfigBoolValues.DO_RICE_GEN.isEnabled()){
            for(int i = 0; i < ConfigIntValues.RICE_AMOUNT.getValue(); i++){
                if(new Random().nextInt(10) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ);

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == Material.water){
                        ArrayList<Material> blocksAroundBottom = WorldUtil.getMaterialsAround(event.world, genX, genY, genZ);
                        ArrayList<Material> blocksAroundTop = WorldUtil.getMaterialsAround(event.world, genX, genY+1, genZ);
                        if(blocksAroundBottom.contains(Material.grass) || blocksAroundBottom.contains(Material.ground) || blocksAroundBottom.contains(Material.rock) || blocksAroundBottom.contains(Material.sand)){
                            if(!blocksAroundTop.contains(Material.water) && event.world.getBlock(genX, genY+1, genZ).getMaterial() == Material.air){
                                event.world.setBlock(genX, genY+1, genZ, InitBlocks.blockWildPlant, TheWildPlants.RICE.ordinal(), 2);
                            }
                        }
                    }
                }
            }
        }

        this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.CANOLA.ordinal(), ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.grass, event);
        this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.FLAX.ordinal(), ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.grass, event);
        this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.COFFEE.ordinal(), ConfigIntValues.COFFEE_AMOUNT.getValue(), ConfigBoolValues.DO_COFFEE_GEN.isEnabled(), Material.grass, event);
    }

    public void genPlantNormally(Block plant, int meta, int amount, boolean doIt, Material blockBelow, DecorateBiomeEvent event){
        if(doIt){
            for(int i = 0; i < amount; i++){
                if(new Random().nextInt(100) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ)-1;

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == blockBelow){
                        event.world.setBlock(genX, genY+1, genZ, plant, meta, 2);
                    }
                }
            }
        }
    }

}
