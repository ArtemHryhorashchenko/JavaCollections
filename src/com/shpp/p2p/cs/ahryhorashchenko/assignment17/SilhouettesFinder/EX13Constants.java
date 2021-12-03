package com.shpp.p2p.cs.ahryhorashchenko.assignment17.SilhouettesFinder;

import java.awt.*;

/**
 * Interface of constants
 */
public interface EX13Constants {
    /**
     * Default file with the way to this file
     */
    String DEFAULT_FILE = "assets\\test.jpg";

    /**
     * Size of brush
     */
    int BRUSH_SIZE = 65;

    /**
     * Second value after background value
     */
    int SILHOUETTES_VALUE = 0;

    /**
     * Сoefficient for determining garbage
     */
    double PERCENT_FOR_GARBAGE = 0.2;

    /**
     * Deviation from the main color
     * for background
     */
    int DEVIATION_FOR_BACKGROUND = 150;

    /**
     * Color for background
     */
    Color COLOR_FOR_BACKGROUND = new Color(255, 255, 255);

    /**
     * Color for silhouettes
     */
    Color COLOR_FOR_SILHOUETTES = new Color(0, 0, 0);
}
