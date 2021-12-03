package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.Calculator.EX11Calculator;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver.EX15HuffmanArchiver;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.SilhouettesFinder.EX13SilhouettesFinder;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests.*;

import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Class for start use another objects of classes
 */
public class Assignment17Part1 {

    /**
     * Main method of program
     *
     * @param args of program for entering arguments open Run/Debug Configurations
     *             and you need to enter there the mathematical expression (for calculator)
     * @param args arguments of program for entering arguments open Run/Debug Configurations
     *             and you need to enter name of the file in which need to find silhouettes (for silhouettes finder)
     * @param args arguments of program for entering arguments open Run/Debug Configurations
     *             and enter file name the program itself will determine what to do with the file
     *             can use the flags ("-u" - unzip) and ("-a" - archives) at the first argument (for Archiver)
     */
    public static void main(String[] args) throws Exception {
        Assignment17Part1 program = new Assignment17Part1();
        program.run(args);
    }

    /**
     * Method which run all program
     */
    private void run(String[] args){
        MyHashMap<String, String> as= new MyHashMap<>();
        as.put("hello1","1");
        as.put("hello1","2");
        as.put("hello","3");
        System.out.println(as.size());
        System.out.println(as);
        /* Run the calculator exercise 11 */
        //EX11Calculator calculator = new EX11Calculator();
        //calculator.run(args);

        /* Run the silhouettes finder exercise 13 */
        /*EX13SilhouettesFinder silhouettes = new EX13SilhouettesFinder();
        try {
            silhouettes.run(args);
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
        /* Run the Huffman archiver exercise 15 */
        //EX15HuffmanArchiver huffman = new EX15HuffmanArchiver();
        //huffman.start(args);
        /* tests */
        TestsMyLinkedList testLink = new TestsMyLinkedList();
        testLink.testMyLinkedList();
        TestMyArrayList testArray = new TestMyArrayList();
        testArray.testMyArrayList();
        TestMyStack testStack = new TestMyStack();
        testStack.testMyStack();
        TestMyQueue testQueue = new TestMyQueue();
        testQueue.testMyQueue();
        TestMyHashMap testHashMap = new TestMyHashMap();
        testHashMap.testMyHashMap();
        TestMyPriorityQueue testPriorQueue = new TestMyPriorityQueue();
        testPriorQueue.testMyPriorityQueue();
    }
}
