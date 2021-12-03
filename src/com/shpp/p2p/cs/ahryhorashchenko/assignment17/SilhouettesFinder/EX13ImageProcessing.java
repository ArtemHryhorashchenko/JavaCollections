package com.shpp.p2p.cs.ahryhorashchenko.assignment17.SilhouettesFinder;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyArrayList;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyLinkedList;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Processing the image and return quantity of silhouettes
 */
public class EX13ImageProcessing implements EX13Constants {

    /**
     * Image with which program will work
     */
    private final BufferedImage image;

    /**
     * Height of image
     */
    private final int HEIGHT;

    /**
     * Width of image
     */
    private final int WIDTH;

    /**
     * Linked list with points of pixels what need to visit
     */
    private final MyLinkedList<Point> points = new MyLinkedList<>();

    /**
     * Constructor for class ImageProcessing
     *
     * @param im image
     */
    EX13ImageProcessing(BufferedImage im) {
        this.image = im;
        HEIGHT = image.getHeight();
        WIDTH = image.getWidth();
    }

    /**
     * Quantity of silhouettes
     */
    private int silhouettesQuantity = 0;

    /**
     * Quantity of pixels in one silhouette
     */
    private int quantityOfSilPixels = 0;

    /**
     * Size in pixels of every silhouette
     */
    private final MyArrayList<Integer> sizeOfSilhouettes = new MyArrayList<>();

    /**
     * Mark field in which passed cells will be marked 1 and not passed 0
     */
    private int[][] marksField;

    /**
     * Find quantity of silhouettes at the picture
     *
     * @return this quantity
     */
    public int findSilhouettes() throws IOException {
        fillMarksField();
        remakeImage();
        processingFind();
        lookingForGarbage();
        return silhouettesQuantity;
    }

    /**
     * Fill marks fields with 0
     */
    private void fillMarksField() {
        marksField = new int[HEIGHT][WIDTH];
        for (int row = 0; row < marksField.length; row++) {
            for (int col = 0; col < marksField[0].length; col++) {
                marksField[row][col] = 0;
            }
        }
    }

    /**
     * Write histogram of use red color from picture
     */
    private void remakeImage() throws IOException {
        if (image.getRGB(0, 0) != 0) {
            Color backgroundColor = new Color(image.getRGB(0, 0));

            for (int row = 0; row < HEIGHT; row++) {
                for (int col = 0; col < WIDTH; col++) {
                    Color color = new Color(image.getRGB(col, row));
                    if (color.getRGB() == backgroundColor.getRGB() || image.getRGB(col, row) == 0) {
                        image.setRGB(col, row, COLOR_FOR_BACKGROUND.getRGB());
                    } else if (Math.abs(color.getRed() - backgroundColor.getRed()) < DEVIATION_FOR_BACKGROUND &&
                            Math.abs(color.getGreen() - backgroundColor.getGreen()) < DEVIATION_FOR_BACKGROUND &&
                            Math.abs(color.getBlue() - backgroundColor.getBlue()) < DEVIATION_FOR_BACKGROUND) {
                        image.setRGB(col, row, COLOR_FOR_BACKGROUND.getRGB());
                    } else {
                        image.setRGB(col, row, COLOR_FOR_SILHOUETTES.getRGB());
                    }
                }
            }
        }
    }

    /**
     * Processing of finding the silhouettes
     */
    private void processingFind() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Color color = new Color(image.getRGB(col, row));
                if (color.getRed() == SILHOUETTES_VALUE && image.getRGB(col, row) != 0 && marksField[row][col] != 1) {
                    bypassTheSilhouette(row, col);
                    if (quantityOfSilPixels > BRUSH_SIZE) {
                        silhouettesQuantity++;
                        sizeOfSilhouettes.add(quantityOfSilPixels);
                    }
                    quantityOfSilPixels = 0;
                }
            }
        }
    }

    /**
     * bypass the silhouette through all its pixels
     *
     * @param row row of the image
     * @param col col of the image
     */
    private void bypassTheSilhouette(int row, int col) {
        marksField[row][col] = 1;
        lookingForFreePixels(row, col);
        while (points.size() > 0) {
            quantityOfSilPixels++;
            marksField[points.getFirst().y][points.getFirst().x] = 1;
            lookingForFreePixels(points.getFirst().y, points.getFirst().x);
            points.removeFirst();
        }

    }

    /**
     * Looking the pixels at 1 pixel and add they to the queue
     *
     * @param row row of the image
     * @param col col of the image
     */
    private void lookingForFreePixels(int row, int col) {
        int[] massForRow = {0, 0, -1, 1};
        int[] massForCol = {1, -1, 0, 0};

        Color color;
        Point point;
        for (int i = 0; i < massForRow.length; i++) {
            if (row + massForRow[i] < HEIGHT && row + massForRow[i] >= 0 &&
                    col + massForCol[i] < WIDTH && col + massForCol[i] >= 0) {

                if (row + 1 < image.getHeight()) {
                    if (marksField[row + massForRow[i]][col + massForCol[i]] != 1) {
                        color = new Color(image.getRGB(col + massForCol[i], row + massForRow[i]));
                        if (color.getRed() == SILHOUETTES_VALUE && image.getRGB(
                                col + massForCol[i],
                                row + massForRow[i]) != 0) {

                            marksField[row + massForRow[i]][col + massForCol[i]] = 1;
                            point = new Point(col + massForCol[i], row + massForRow[i]);
                            points.add(point);
                        }
                    }
                }
            }
        }
    }

    /**
     * Take out brush
     */
    private void lookingForGarbage() {
        int value = 0;
        for (int i = 0; i < sizeOfSilhouettes.size(); i++) {
            int numberOfPixels = sizeOfSilhouettes.get(i);
            if (numberOfPixels > value) {
                value = numberOfPixels;
            }
        }

        double percentOfBiggestValue = value * PERCENT_FOR_GARBAGE;

        for (int i = 0; i < sizeOfSilhouettes.size(); i++) {
            int numberOfPixels = sizeOfSilhouettes.get(i);
            if (numberOfPixels < percentOfBiggestValue) {
                sizeOfSilhouettes.remove(i);
                i--;
                silhouettesQuantity--;
            }
        }
    }
}
