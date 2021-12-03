package com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver;

public interface EX15Constants {
    /**
     * Quantity of bits in the byte
     */
    int BYTE_LENGTH = 8;

    /**
     * Size for buffered read and write
     */
    int SIZE_FOR_READ = 65536;

    /**
     * Size for increasing the array at unzipper
     */
    int SIZE_FOR_INCREASED_ARRAY = 8192;

    /**
     * Milli for output information in (kb)
     */
    int MILLI = 1000;

    /**
     * Bytes in which write size of tree in bits
     */
    int SIZE_OF_BYTES_FOR_SIZE_OF_TREE = 2;

    /**
     * Max size of tree 64 bytes - 512 bits
     */
    int MAX_SIZE_OF_TREE = 64;

    /**
     * Coefficient for multiplication(when unzip) or division(when archives)
     * size of tree in bits for write this number to bytes
     */
    int COEF_FOR_TREE_SIZE_IN_BITS = 4;

    /**
     * Half of symbols what can be encrypted
     */
    int HALF_OF_ALL_BYTES = 128;

    /**
     * Inscription for output information about size of files
     */
    String SIZE_OF_ENTERED_FILE = " (kb) - size of entered file";
    String SIZE_OF_PROCESSED_FILE = " (kb) - size of processed file";

    /**
     * Inscription for output messages
     */
    String SECONDS = " seconds";
    String EMPTY_FILE = "Entered file is empty";
    String QUANTITY_OF_UNIQUE_BYTES_SMALL = "For working file must have two and more unique symbols";
    String PERCENTS_OF_EFFICIENCY = " persent of efficiency";
    String DEFAULT_FILE = "test.txt";
    String EXPANSION_FOR_ARCHIVE = ".par";
    String EXPANSION_FOR_UNARCHIVED = ".uar";
    String ENTERED_FILE = "Entered file: ";
    String OUTPUT_FILE = "Output file: ";
    String OPERATION_ARCHIVATE = "Operation archivate.";
    String OPERATION_UNARCHIVATE = "Operation unarchivate.";

    /**
     * Size of file`s expansion
     */
    int SIZE_OF_EXPANSION = 4;

    /**
     * Size of arguments what must be by default
     */
    int SIZE_OF_ORIGINAL_ARGUMENTS = 2;

    /**
     * Size arguments of program with flags
     */
    int SIZE_OF_ARGUMENTS_WITH_FLAG = 3;

    /**
     * Index of last argument if argument 3 with flags
     */
    int INDEX_OF_LAST_ARGUMENT = 2;
}
