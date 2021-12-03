package com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver;


import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyLinkedList;

/**
 * Class for creating a tree
 */
public class EX15Tree implements EX15Constants {
    /**
     * Symbol of current tree
     */
    Byte symbol;

    /**
     * Value of current tree
     */
    int value;

    /**
     * Left subtree of current tree
     */
    EX15Tree left;

    /**
     * Right subtree of current tree
     */
    EX15Tree right;

    /**
     * Index of bit it need for decode tree
     */
    private int indexOfBit = 0;

    /**
     * Index of byte it need for decode tree
     */
    private int indexOfByte = 0;

    /**
     * Masks for bits of byte
     */
    private final byte[] masksOfBits = {(byte) 0b10000000, 0b01000000, 0b00100000,
            0b00010000, 0b00001000, 0b00000100, 0b00000010, 0b00000001};

    /**
     * Constructor for object of Class Tree
     */
    EX15Tree() {
    }

    /**
     * Constructor for object of Class Tree
     *
     * @param symbol Symbol of current tree
     * @param value  Value of current tree
     */
    EX15Tree(Byte symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    /**
     * Constructor for object of Class Tree
     *
     * @param symbol Symbol of current tree
     * @param value  Value of current tree
     * @param left   Left subtree of current tree
     * @param right  Right subtree of current tree
     */
    EX15Tree(Byte symbol, int value, EX15Tree left, EX15Tree right) {
        this.symbol = symbol;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * Decode Huffman tree for unzip all another bytes
     *
     * @param treeBytes   Bytes in which huffman tree encode
     * @param uniqueBytes List of unique bytes need to put them to the tree leaves
     * @param tree        Empty tree
     * @return Filled Huffman tree
     */
    public EX15Tree decodeTree(byte[] treeBytes, MyLinkedList<Byte> uniqueBytes, EX15Tree tree) {
        if ((treeBytes[indexOfByte] & masksOfBits[indexOfBit]) != 0) {
            tree.left = new EX15Tree(null, 0);
            tree.right = new EX15Tree(null, 0);
        } else {
            tree.symbol = uniqueBytes.get(0);
            uniqueBytes.remove(0);
        }
        indexOfBit++;
        if (indexOfBit == BYTE_LENGTH) {
            indexOfBit = 0;
            indexOfByte++;
        }
        if (tree.left != null) {
            decodeTree(treeBytes, uniqueBytes, tree.left);
        }
        if (tree.right != null) {
            decodeTree(treeBytes, uniqueBytes, tree.right);
        }
        return tree;
    }
}
