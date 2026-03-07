package com.dipo33.bewitched.block;

import com.dipo33.bewitched.config.Config;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.data.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class BwBlockCrops extends BlockCrops {

    private final ObjectHolder<Item> crop;
    private final ObjectHolder<Item> seed;

    private int stages = 4;
    private EnumPlantType plantType = EnumPlantType.Crop;
    private final List<Pair<ObjectHolder<Item>, Double>> additionalDrops = new ArrayList<>();

    // prefer earlier stages to grow faster
    private static final int[][] META_STAGE_TO_ICON = new int[][]{
        new int[]{0, 0, 0, 0, 0, 0, 0, 0}, // 1 stage
        new int[]{0, 0, 0, 0, 0, 0, 0, 1}, // 2 stages
        new int[]{0, 0, 0, 1, 1, 1, 1, 2}, // 3 stages
        new int[]{0, 0, 1, 1, 2, 2, 2, 3}, // 4 stages
        new int[]{0, 1, 1, 2, 2, 3, 3, 4}, // 5 stages
        new int[]{0, 1, 2, 3, 3, 4, 4, 5}, // 6 stages
        new int[]{0, 1, 2, 3, 4, 5, 5, 6}, // 7 stages
        new int[]{0, 1, 2, 3, 4, 5, 6, 7}, // 8 stages
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    /**
     * Create a BwBlockCrops configured with the provided seed and crop item holders.
     *
     * @param seed
     *     holder for the seed Item; must not be null
     * @param crop
     *     holder for the crop Item produced by the plant; must not be null
     * @throws NullPointerException
     *     if {@code seed} or {@code crop} is null
     */
    public BwBlockCrops(ObjectHolder<Item> seed, ObjectHolder<Item> crop) {
        super();
        this.seed = Objects.requireNonNull(seed, "seed holder cannot be null");
        this.crop = Objects.requireNonNull(crop, "crop holder cannot be null");
    }

    /**
     * Set the number of growth stages for this crop.
     *
     * @param stages
     *     the number of growth stages (must be between 1 and 8 inclusive)
     * @return this {@code BwBlockCrops} instance for method chaining
     * @throws IllegalArgumentException
     *     if {@code stages} is less than 1 or greater than 8
     */
    public BwBlockCrops setStages(int stages) {
        if (stages < 1 || stages > 8) {
            throw new IllegalArgumentException("Stages must be between 1 and 8, got: " + stages);
        }
        this.stages = stages;
        return this;
    }

    /**
     * Configure the plant type used by this crop.
     *
     * @param plantType
     *     the plant type to assign to this crop; must not be null
     * @return this {@code BwBlockCrops} instance for method chaining
     * @throws NullPointerException
     *     if {@code plantType} is null
     */
    public BwBlockCrops setPlantType(EnumPlantType plantType) {
        this.plantType = Objects.requireNonNull(plantType, "plantType cannot be null");
        return this;
    }

    /**
     * Registers an additional item that the crop can drop with the provided probability.
     *
     * @param item
     *     an ObjectHolder wrapping the item to add as an extra drop
     * @param chance
     *     the probability that the item will be dropped (must be between 0.0 and 1.0 inclusive)
     * @return this {@code BwBlockCrops} instance for method chaining
     * @throws NullPointerException
     *     if {@code item} is null
     * @throws IllegalArgumentException
     *     if {@code chance} is NaN or is less than 0.0 or greater than 1.0
     */
    public BwBlockCrops addAdditionalDrops(ObjectHolder<Item> item, double chance) {
        Objects.requireNonNull(item, "item holder cannot be null");
        if (Double.isNaN(chance) || chance < 0.0D || chance > 1.0D) {
            throw new IllegalArgumentException("Chance must be between 0.0 and 1.0, got: " + chance);
        }
        this.additionalDrops.add(new Pair<>(item, chance));
        return this;
    }

    /**
     * Get the texture icon for the specified block face and growth metadata.
     *
     * @param side
     *     the block face being queried
     * @param meta
     *     the block metadata representing the crop's growth stage
     * @return the icon used to render this block for the given face and growth metadata
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icons[META_STAGE_TO_ICON[this.stages - 1][meta]];
    }

    /**
     * Register and initialize per-growth-stage icons for this crop block.
     * <p>
     * Populates the internal icons array with one icon per configured stage by
     * registering textures named {@code <textureName>_stage_<stage>}.
     *
     * @param reg
     *     the icon register used to register stage textures
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.icons = new IIcon[this.stages];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = reg.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }

    /**
     * Obtains the seed Item used to plant this crop.
     *
     * @return the seed Item for this crop
     */
    @Override
    protected Item func_149866_i() {
        return this.seed.get();
    }

    /**
     * Gets the crop item produced by this crop when harvested.
     *
     * @return the Item dropped as the crop's harvest product
     */
    @Override
    protected Item func_149865_P() {
        return this.crop.get();
    }

    /**
     * Collects item drops for this crop, including configured additional drops when the crop is fully mature.
     */
    @Override
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
        if (metadata >= 7) {
            for (Pair<ObjectHolder<Item>, Double> pair : this.additionalDrops) {
                if (world.rand.nextDouble() < pair.second()) {
                    drops.add(new ItemStack(pair.first().get()));
                }
            }
        }

        return drops;
    }

    /**
     * Get the configured plant type for this crop block.
     *
     * @return the configured {@link EnumPlantType} for this block
     */
    @Override
    public EnumPlantType getPlantType(final IBlockAccess world, final int x, final int y, final int z) {
        return this.plantType;
    }

    /**
     * Harvests the crop at the given position by dropping its items and resetting its growth when the crop is fully mature.
     *
     * @return `true` if the crop was harvested and reset, `false` otherwise
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (!Config.rightClickMatureCropHarvest) {
            return false;
        }

        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 7) {
            if (!world.isRemote) {
                this.dropBlockAsItem(world, x, y, z, meta, 0);
                world.setBlock(x, y, z, this, 0, 2);
            }
            return true;
        }

        return false;
    }
}
