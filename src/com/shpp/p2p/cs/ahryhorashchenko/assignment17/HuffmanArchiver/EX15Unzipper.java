package com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyLinkedList;

import java.io.*;

/**
 * A class that takes parameters when creating a class 2 object and
 * unzip data according to Huffman's algorithm
 */
public class EX15Unzipper implements EX15Constants {

    /**
     * Name of entered file
     */
    public String enteredFilename;

    /**
     * Name of processed file
     */
    public String processedFilename;
    /**
     * Size of entered file
     */
    public long sizeOfEnteredFile;

    /**
     * Size of processed file
     */
    public long sizeOfProcessedFile;

    /**
     * Index of linkedlist`s element which use for unzip information
     */
    private int indexOfCombination = 0;
    private int counterForZeros = 0;

    /**
     * Masks for bits of byte
     */
    private final byte[] masksOfBits = {(byte) 0b10000000, 0b01000000, 0b00100000,
            0b00010000, 0b00001000, 0b00000100, 0b00000010, 0b00000001};

    /**
     * Constructor for unzipper in which the input file and the output are associated
     *
     * @param enteredFilename   Entered file name from which program will readInformation
     * @param processedFilename File in which will be created and in it will wrote unzip information
     */
    EX15Unzipper(String enteredFilename, String processedFilename) {
        this.enteredFilename = enteredFilename;
        this.processedFilename = processedFilename;
    }

    /**
     * Unzip file
     *
     * @return 0 if unzip successful 1 if not
     */
    public int unzips() {
        try {
            processingFile();
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    /**
     * Create buffered streams for read and write file and then read and write file
     */
    private void processingFile() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(enteredFilename));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(processedFilename))) {


            readAndUnzipFile(bis, bos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read original file unzip it and write to new file which name is contain in processedFileName
     *
     * @param bis buffered input stream for reading file
     * @param bos buffered output stream for write file
     */
    private void readAndUnzipFile(BufferedInputStream bis, BufferedOutputStream bos) {
        try {

            EX15Tree tree = readServiceInformationAndMakeHuffmanTree(bis);
            int sizeOfData;

            byte[] data = new byte[SIZE_FOR_READ];
            sizeOfData = bis.read(data, 0, SIZE_FOR_READ);

            while (sizeOfData == SIZE_FOR_READ) {
                sizeOfEnteredFile += sizeOfData;
                byte[] unarchivedData = unzipData(data, tree);
                sizeOfProcessedFile += unarchivedData.length;
                bos.write(unarchivedData, 0, unarchivedData.length);
                sizeOfData = bis.read(data, 0, SIZE_FOR_READ);
            }

            if (sizeOfData != -1) {
                byte[] lastData = remakeByteArray(data, sizeOfData);
                byte[] unarchivedData = unzipData(lastData, tree);
                sizeOfProcessedFile += unarchivedData.length;
                bos.write(unarchivedData, 0, unarchivedData.length);
                sizeOfEnteredFile += lastData.length;
            }

        } catch (Exception e) {
            System.out.println("Some problems with unzip: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * reed service information and make huffman tree
     *
     * @param bis buffered input stream to read the file
     * @return Huffman tree
     */
    private EX15Tree readServiceInformationAndMakeHuffmanTree(BufferedInputStream bis) throws Exception {
        EX15Tree tree = new EX15Tree();
        byte[] data = new byte[SIZE_OF_BYTES_FOR_SIZE_OF_TREE];
        int sizeOfData;

        sizeOfData = bis.read(data, 0, SIZE_OF_BYTES_FOR_SIZE_OF_TREE);
        sizeOfEnteredFile += sizeOfData;
        short sizeOfTreeInBits = (short) ((Math.abs(data[0]) * COEF_FOR_TREE_SIZE_IN_BITS) + data[1]);
        int sizeOfBytesForTree = sizeOfTreeInBits % BYTE_LENGTH == 0 ? sizeOfTreeInBits / BYTE_LENGTH :
                sizeOfTreeInBits / BYTE_LENGTH + 1;
        byte[] treeBytes = new byte[sizeOfBytesForTree];
        sizeOfData = bis.read(treeBytes, 0, sizeOfBytesForTree);
        sizeOfEnteredFile += sizeOfData;
        int quantityOfUniqueSymbols = countUniqueSymbols(treeBytes, sizeOfTreeInBits);
        byte[] uniqueBytes = new byte[quantityOfUniqueSymbols];
        sizeOfData = bis.read(uniqueBytes, 0, quantityOfUniqueSymbols);
        MyLinkedList<Byte> uniqBytes = new MyLinkedList<>();
        for (byte uniqueByte : uniqueBytes) {
            uniqBytes.add(uniqueByte);
        }
        sizeOfEnteredFile += sizeOfData;
        tree = tree.decodeTree(treeBytes, uniqBytes, tree);
        return tree;
    }

    /**
     * Unzip archives bytes from archived file
     *
     * @param data archives bytes which read from file
     * @param tree Huffman tree with combinations for every unique byte
     * @return unzip bytes
     */
    private byte[] unzipData(byte[] data, EX15Tree tree) {
        byte[] unzipData = new byte[data.length + SIZE_FOR_READ];
        int indexOfByte = 0;

        int quantityOfBits = BYTE_LENGTH;
        MyLinkedList<Boolean> combination = new MyLinkedList<>();
        for (int j = 0; j < data.length; j++) {
            byte bt = data[j];
            for (int i = 0; i < quantityOfBits; i++) {
                if ((bt & masksOfBits[i]) == 0) {
                    combination.add(false);
                } else {
                    combination.add(true);
                }
                Byte unzipByte = findByteAtTree(tree, combination);
                indexOfCombination = 0;
                if (unzipByte != null) {
                    combination.clear();
                    unzipData[indexOfByte] = unzipByte;
                    indexOfByte++;
                    counterForZeros++;
                    if (j == data.length - 2 && data.length < SIZE_FOR_READ) {
                        j++;
                        quantityOfBits = quantityOfBits - data[j];
                    } else if (counterForZeros == SIZE_FOR_READ + 1) {
                        counterForZeros = 0;
                        break;
                    }
                    if (indexOfByte == unzipData.length) {
                        unzipData = increaseByteArray(unzipData);
                    }
                }
            }
            if (indexOfByte == unzipData.length) {
                unzipData = increaseByteArray(unzipData);
            }
        }
        return remakeByteArray(unzipData, indexOfByte);
    }


    /**
     * Find byte at the tree for combinations of true and false
     * true - 1 - fo to right false - 0 - go to left
     * if finding nothing return null
     *
     * @param tree        Huffman tree
     * @param combination combination of steps for byte
     * @return byte if program find it and null if not find it
     */
    private Byte findByteAtTree(EX15Tree tree, MyLinkedList<Boolean> combination) {
        Byte bt = null;
        if (tree.symbol != null) {
            return tree.symbol;
        }
        if (indexOfCombination < combination.size()) {
            if (combination.get(indexOfCombination)) {
                indexOfCombination++;
                bt = findByteAtTree(tree.right, combination);
            } else {
                indexOfCombination++;
                bt = findByteAtTree(tree.left, combination);
            }
        }
        return bt;
    }

    /**
     * Increase size of byte array if program need this
     *
     * @param unzipData old array
     * @return new array with information of old array but have bigger size
     */
    private byte[] increaseByteArray(byte[] unzipData) {
        byte[] newArray = new byte[unzipData.length + SIZE_FOR_INCREASED_ARRAY];
        for (int i = 0; i < unzipData.length; i++) {
            newArray[i] = unzipData[i];
        }
        return newArray;
    }

    /**
     * Count quantity of unique symbols at the archive
     *
     * @param treeBytes        bytes in which coded tree
     * @param sizeOfTreeInBits size of tree in bits
     * @return quantity of unique symbols
     */
    private int countUniqueSymbols(byte[] treeBytes, short sizeOfTreeInBits) {
        int uniqueSymbolsQuantity = 0;
        int sizeOfProcessedBits = 0;
        for (byte b : treeBytes) {
            for (int i = 0; i < BYTE_LENGTH; i++) {
                sizeOfProcessedBits++;
                if ((b & masksOfBits[i]) == 0) {
                    uniqueSymbolsQuantity++;
                }
                if (sizeOfProcessedBits == sizeOfTreeInBits) {
                    break;
                }
            }
        }
        return uniqueSymbolsQuantity;
    }

    /**
     * Remake byte array if array size less then Size for read
     *
     * @param data an array with information the size of which is SIZE_FOR_READ but it is not completely filled with data
     * @param size size of current information for new array
     * @return new array size which == size of current information
     */
    private byte[] remakeByteArray(byte[] data, int size) {
        byte[] newData = new byte[size];
        System.arraycopy(data, 0, newData, 0, size);
        return newData;
    }
}
