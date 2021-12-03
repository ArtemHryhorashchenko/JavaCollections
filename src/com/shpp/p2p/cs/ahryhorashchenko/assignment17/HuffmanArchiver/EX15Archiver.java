package com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyArrayList;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyHashMap;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyLinkedList;

import java.io.*;

/**
 * A class that takes parameters when creating a class 2 object and
 * performs data archiving according to Huffman's algorithm
 */
public class EX15Archiver implements EX15Constants {

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
     * Variable for creating way for every unique symbol
     */
    private boolean endOfRecurs = true;

    /**
     * Index of bit mask
     */
    private int indexOfBitMaskForRecursTree = 0;

    /**
     * Index of byte from array
     */
    private int indexOfByteAtArrayForRecursTree = 0;

    /**
     * Size of tree in bits which need to encode
     */
    private short sizeOfTreeInBits = 0;

    /**
     * Masks for bits of byte
     */
    private final byte[] masksOfBits = {(byte) 0b10000000, 0b01000000, 0b00100000,
            0b00010000, 0b00001000, 0b00000100, 0b00000010, 0b00000001};

    /**
     * Constructor for Archiver
     *
     * @param enteredFilename   filename of entered file
     * @param processedFilename filename of processed file
     */
    EX15Archiver(String enteredFilename, String processedFilename) {
        this.enteredFilename = enteredFilename;
        this.processedFilename = processedFilename;
    }

    /**
     * Read file and write archive bytes to new file
     *
     * @return 0 if archive successful 1 if no
     */
    public int archives() {
        try {
        MyHashMap<Byte, Integer> uniqueSymbols = readFileAndWriteUniqueSymbols();
        if (uniqueSymbols.size() < 2) {
            System.out.println(QUANTITY_OF_UNIQUE_BYTES_SMALL);
            System.exit(0);
            return 1;
        } else {
            if (sizeOfEnteredFile > 0) {
                MyLinkedList<EX15Tree> nodesOfTree = initTreeLeaves(uniqueSymbols);
                EX15Tree tree = huffmanTree(nodesOfTree);
                MyHashMap<Byte, MyArrayList<Boolean>> byteResponse = makeByteResponse(uniqueSymbols, tree);
                writeNewFile(byteResponse, tree);
                return 0;
            } else {
                System.out.println(EMPTY_FILE);
                System.exit(0);
                return 1;
            }
        }
        } catch (Exception e) {
            System.exit(1);
            return 1;
        }
    }

    /**
     * Read file and write unique symbols and frequency of their use
     *
     * @return MyHashMap with unique symbols
     */
    private MyHashMap<Byte, Integer> readFileAndWriteUniqueSymbols() {
        MyHashMap<Byte, Integer> uniqueSymbols = new MyHashMap<>();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(enteredFilename));

            byte[] data = new byte[SIZE_FOR_READ];
            int sizeOfData = bis.read(data, 0, SIZE_FOR_READ);

            while (sizeOfData == SIZE_FOR_READ) {
                sizeOfEnteredFile += sizeOfData;
                writeUniqueSymbols(data, uniqueSymbols);
                sizeOfData = bis.read(data, 0, SIZE_FOR_READ);
            }

            if (sizeOfData != -1) {
                byte[] lastData = remakeByteArray(data, sizeOfData);
                writeUniqueSymbols(lastData, uniqueSymbols);
                sizeOfEnteredFile += sizeOfData;
            }

            bis.close();

        } catch (IOException e) {
            System.out.println("Some problems with file" + "\n" + e.getMessage());
            System.exit(1);
        }
        return uniqueSymbols;
    }

    /**
     * Add unique symbols to hashmap and arraylists and add frequency of their use
     *
     * @param data          data from current file
     * @param uniqueSymbols
     */
    private void writeUniqueSymbols(byte[] data, MyHashMap<Byte, Integer> uniqueSymbols) {
        for (byte b : data) {
            if (uniqueSymbols.containsKey(b)) {
                uniqueSymbols.put(b, uniqueSymbols.get(b) + 1);
            } else {
                uniqueSymbols.put(b, 1);
            }
        }

    }

    /**
     * The program initializes tree leaves with unique bytes
     *
     * @param uniqueSymbols Unique symbols of the entered file
     * @return Trees with initializes with unique bytes
     */
    private MyLinkedList<EX15Tree> initTreeLeaves(MyHashMap<Byte, Integer> uniqueSymbols) {
        MyLinkedList<EX15Tree> leavesOfTree = new MyLinkedList<>();
        for (byte b : uniqueSymbols.keySet()) {
            leavesOfTree.add(new EX15Tree(b, uniqueSymbols.get(b)));
        }
        return leavesOfTree;
    }

    /**
     * Create from little trees one big tree by algorithm of Huffman
     *
     * @param nodesOfTree Little trees
     * @return One tree with all little trees
     */
    private EX15Tree huffmanTree(MyLinkedList<EX15Tree> nodesOfTree) {
        while (nodesOfTree.size() > 1) {
            EX15Tree leftLeaf = nodesOfTree.remove(getIndexOfFirstLessObject(nodesOfTree));
            EX15Tree rightLeaf = nodesOfTree.remove(getIndexOfFirstLessObject(nodesOfTree));

            nodesOfTree.add(new EX15Tree(null, leftLeaf.value + rightLeaf.value, leftLeaf, rightLeaf));
        }
        return nodesOfTree.get(0);
    }

    /**
     * Find the object with the lowest value and return index of it
     *
     * @param nodesOfTree Nodes of Huffman tree
     * @return index of the lowest value
     */
    private int getIndexOfFirstLessObject(MyLinkedList<EX15Tree> nodesOfTree) {
        int index = 0;
        int value = nodesOfTree.get(0).value;
        for (int i = 0; i < nodesOfTree.size(); i++) {
            EX15Tree tree = nodesOfTree.get(i);
            if (tree.value < value) {
                value = tree.value;
                index = nodesOfTree.indexOf(tree);
            }
        }
        return index;
    }

    /**
     * Match the bytes to the path from the beginning of the tree to that byte
     *
     * @param uniqueSymbols Unique symbols of the file
     * @param tree          Huffman tree
     * @return response of bytes with way to them from start of tree
     */
    private MyHashMap<Byte, MyArrayList<Boolean>> makeByteResponse(MyHashMap<Byte, Integer> uniqueSymbols, EX15Tree tree) {
        MyHashMap<Byte, MyArrayList<Boolean>> byteResponse = new MyHashMap<Byte, MyArrayList<Boolean>>();
        for (Byte b : uniqueSymbols.keySet()) {
            MyArrayList<Boolean> wayForSymbol = new MyArrayList<>();
            wayForSymbol = findByteAtTheTree(b, tree, wayForSymbol);
            byteResponse.put(b, wayForSymbol);
            endOfRecurs = true;
        }
        return byteResponse;
    }

    /**
     * Find needed byte at the tree and return way for it
     *
     * @param b            Needed byte
     * @param tree         Huffman tree
     * @param wayForSymbol Empty way for symbol
     * @return Way for symbol which contains boolean type of information(false - left, true - right)
     */
    private MyArrayList<Boolean> findByteAtTheTree(byte b, EX15Tree tree,
                                                   MyArrayList<Boolean> wayForSymbol) {
        if (tree.symbol != null && (tree.symbol) == b) {
            endOfRecurs = false;
            return wayForSymbol;
        } else {
            if (tree.left != null && endOfRecurs) {
                findByteAtTheTree(b, tree.left, wayForSymbol);
                if (!endOfRecurs) {
                    wayForSymbol.add(0, false);
                }
            }
            if (tree.right != null && endOfRecurs) {
                findByteAtTheTree(b, tree.right, wayForSymbol);
                if (!endOfRecurs) {
                    wayForSymbol.add(0, true);
                }
            }
        }
        return wayForSymbol;
    }

    /**
     * Archived information and write it to new file
     *
     * @param byteResponse The correspondence of the bytes with the path of the beginning of the tree to this byte
     * @param tree         Huffman tree
     */
    private void writeNewFile(MyHashMap<Byte, MyArrayList<Boolean>> byteResponse, EX15Tree tree) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(enteredFilename));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(processedFilename))) {

            byte[] serviceData = createArrayWithServiceInformationToFile(tree);
            bos.write(serviceData);
            sizeOfProcessedFile += serviceData.length;


            byte[] data = new byte[SIZE_FOR_READ];
            int sizeOfData = bis.read(data, 0, SIZE_FOR_READ);

            while (sizeOfData == SIZE_FOR_READ) {
                byte[] remakeData = archivesData(data, byteResponse);
                bos.write(remakeData);
                sizeOfProcessedFile += remakeData.length;
                sizeOfData = bis.read(data, 0, SIZE_FOR_READ);
            }

            if (sizeOfData != -1) {
                byte[] lastData = remakeByteArray(data, sizeOfData);
                byte[] remakeData = archivesData(lastData, byteResponse);
                bos.write(remakeData);
                sizeOfProcessedFile += remakeData.length;
            }


        } catch (IOException e) {
            System.out.println("Some problems with file");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create array with service information for archive like( 2 bytes - size of tree in bits, x bytes - coded tree
     * y bytes - unique symbols)
     *
     * @param tree Huffman tree
     * @return Byte array with service information
     */
    private byte[] createArrayWithServiceInformationToFile(EX15Tree tree) {
        MyLinkedList<Byte> codedBytes = new MyLinkedList<>();
        byte[] codedTree = createCodedTree(tree, codedBytes);
        int sizeForServiceInformation = SIZE_OF_BYTES_FOR_SIZE_OF_TREE + codedTree.length + codedBytes.size();
        byte[] serviceInformation = new byte[sizeForServiceInformation];
        byte[] sizeOfTree = writeSizeOfTree();
        System.arraycopy(sizeOfTree, 0, serviceInformation, 0, sizeOfTree.length);
        System.arraycopy(codedTree, 0, serviceInformation, sizeOfTree.length, codedTree.length);
        int indexOfByte = codedTree.length + sizeOfTree.length;
        for (int i = 0; i < codedBytes.size(); i++) {
            byte b = codedBytes.get(i);
            serviceInformation[indexOfByte] = b;
            indexOfByte++;
        }
        return serviceInformation;
    }

    /**
     * Passes on a tree and encodes it by such algorithm that if the node has value notNull
     * that is encoded by zero if zero that is one
     *
     * @param tree       Huffman tree which need to code
     * @param codedBytes Empty linked list in which program will write unique symbols (leaves of tree)
     * @return Byte array with coded Huffman tree
     */
    private byte[] createCodedTree(EX15Tree tree, MyLinkedList<Byte> codedBytes) {
        byte[] codedTree = new byte[MAX_SIZE_OF_TREE];

        recursTree(tree, codedTree, codedBytes);

        if (indexOfBitMaskForRecursTree == 0) {
            return remakeByteArray(codedTree, indexOfByteAtArrayForRecursTree);
        } else {
            return remakeByteArray(codedTree, indexOfByteAtArrayForRecursTree + 1);
        }
    }

    /**
     * Pass the tree recursively and encode its nodes with zeros and ones
     *
     * @param tree       Huffman tree which need to encode
     * @param codedTree  Byte array for encode tree
     * @param codedBytes Empty linked list in which program will write unique symbols (leaves of tree)
     */
    private void recursTree(EX15Tree tree, byte[] codedTree, MyLinkedList<Byte> codedBytes) {
        sizeOfTreeInBits++;
        if (tree.symbol == null) {
            codedTree[indexOfByteAtArrayForRecursTree] = (byte) (codedTree[indexOfByteAtArrayForRecursTree] |
                    masksOfBits[indexOfBitMaskForRecursTree]);
            indexOfBitMaskForRecursTree++;
        } else {
            codedBytes.add(tree.symbol);
            indexOfBitMaskForRecursTree++;
        }
        if (indexOfBitMaskForRecursTree == BYTE_LENGTH) {
            indexOfBitMaskForRecursTree = 0;
            indexOfByteAtArrayForRecursTree++;
        }
        if (tree.left != null) {
            recursTree(tree.left, codedTree, codedBytes);
        }
        if (tree.right != null) {
            recursTree(tree.right, codedTree, codedBytes);
        }
    }

    /**
     * Write size of tree in bits to two bytes
     *
     * @return Array of two bytes in which contains (byte[0] contain number divided for 4 and byte[1] contain
     * the remainder at which the number was not divisible by 4
     */
    private byte[] writeSizeOfTree() {
        byte[] sizeOfTree = new byte[SIZE_OF_BYTES_FOR_SIZE_OF_TREE];
        for (int i = 0; i < HALF_OF_ALL_BYTES; i++) {
            if ((sizeOfTreeInBits - i) % COEF_FOR_TREE_SIZE_IN_BITS == 0) {
                if (sizeOfTreeInBits / i < HALF_OF_ALL_BYTES) {
                    sizeOfTree[0] = (byte) ((sizeOfTreeInBits - i) / COEF_FOR_TREE_SIZE_IN_BITS);
                    sizeOfTree[1] = (byte) i;
                    break;
                }
            }
        }
        return sizeOfTree;
    }

    /**
     * Get bytes from source file and archive them
     *
     * @param data         bytes from source file
     * @param byteResponse response of bytes with way to them from start of tree
     * @return Array with archived bytes
     */
    private byte[] archivesData(byte[] data, MyHashMap<Byte, MyArrayList<Boolean>> byteResponse) {
        byte[] resultData = new byte[data.length * 2];
        int indexOfBit = 0;
        int indexOfNewArray = 0;
        byte sizeOfZeroInLastByte;
        for (byte b : data) {
            MyArrayList<Boolean> codedByte = byteResponse.get(b);
            for (int i = 0; i < codedByte.size(); i++) {
                boolean bit = codedByte.get(i);
                if (bit) {
                    resultData[indexOfNewArray] |= masksOfBits[indexOfBit];
                    indexOfBit++;
                } else {
                    indexOfBit++;
                }
                if (indexOfBit == BYTE_LENGTH) {
                    indexOfBit = 0;
                    indexOfNewArray++;
                }
            }
        }
        if (data.length < SIZE_FOR_READ) {
            if (indexOfBit != 0) {
                sizeOfZeroInLastByte = (byte) ((BYTE_LENGTH - 1) - (indexOfBit - 1));
                resultData = remakeByteArray(resultData, indexOfNewArray + 2);
                resultData[resultData.length - 1] = sizeOfZeroInLastByte;
            } else {
                resultData = remakeByteArray(resultData, indexOfNewArray + 1);
            }
            return resultData;
        } else {
            if (indexOfBit == 0) {
                return remakeByteArray(resultData, indexOfNewArray);
            } else {
                return remakeByteArray(resultData, indexOfNewArray + 1);
            }
        }
    }

    /**
     * Remake byte array if array size less then Size for read
     *
     * @param data an array with information the size of which is SIZE_FOR_READ but
     *             it is not completely filled with data
     * @param size size of current information for new array
     * @return new array size which == size of current information
     */
    private byte[] remakeByteArray(byte[] data, int size) {
        byte[] newData = new byte[size];
        System.arraycopy(data, 0, newData, 0, size);
        return newData;
    }
}
