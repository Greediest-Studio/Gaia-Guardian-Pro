package com.smd.gaiapro.common.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityElvenBeacon extends TileEntityBeacon {

    private static final float[] DEFAULT_GREEN = new float[] {0.2F, 0.95F, 0.35F};
    private static final float[] DEFAULT_WHITE = new float[] {1.0F, 1.0F, 1.0F};

    @Override
    @SideOnly(Side.CLIENT)
    public List<TileEntityBeacon.BeamSegment> getBeamSegments() {
        List<TileEntityBeacon.BeamSegment> segments = super.getBeamSegments();

        if (segments.isEmpty()) {
            return segments;
        }

        TileEntityBeacon.BeamSegment firstSegment = segments.get(0);
        float[] colors = firstSegment.getColors();
        if (!isDefaultWhite(colors)) {
            return segments;
        }

        List<TileEntityBeacon.BeamSegment> coloredSegments = new ArrayList<>(segments.size());
        coloredSegments.add(new TileEntityBeacon.BeamSegment(DEFAULT_GREEN));
        coloredSegments.addAll(segments.subList(1, segments.size()));
        return coloredSegments;
    }

    private boolean isDefaultWhite(float[] colors) {
        return colors.length >= 3
                && Math.abs(colors[0] - DEFAULT_WHITE[0]) < 0.0001F
                && Math.abs(colors[1] - DEFAULT_WHITE[1]) < 0.0001F
                && Math.abs(colors[2] - DEFAULT_WHITE[2]) < 0.0001F;
    }
}