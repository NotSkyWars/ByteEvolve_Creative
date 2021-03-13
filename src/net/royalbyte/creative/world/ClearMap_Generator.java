package net.royalbyte.creative.world;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClearMap_Generator extends ChunkGenerator {

        @Override
        public List<BlockPopulator> getDefaultPopulators(World world) {
            // TODO Auto-generated method stub
            return new ArrayList<>();
        }

        @Override
        public boolean canSpawn(World world, int x, int z) {
            return true;
        }

        @Override
        public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
            return new byte[world.getMaxHeight() / 16][];
        }
    }
