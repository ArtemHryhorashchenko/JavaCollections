package com.shpp.p2p.cs.ahryhorashchenko.assignment17.HuffmanArchiver;

/**
 * Program archives and unzips input files
 */
public class EX15HuffmanArchiver implements EX15Constants {

    /**
     * Name of files first - entered file second - processed;
     */
    private String[] nameOfFiles = new String[2];

    /**
     * Start the program which archives or unzip files
     *
     * @param args arguments of program (name of files or file and flags)
     */
    public void start(String[] args) {
        int resultOfWorkingArchiver = 0;


        double firstTime = System.currentTimeMillis();

        boolean operationForArchivator = processingArguments(args);

        /*try {*/
        if (operationForArchivator) {
            System.out.println(OPERATION_ARCHIVATE);
            EX15Archiver archiver = new EX15Archiver(nameOfFiles[0], nameOfFiles[1]);
            resultOfWorkingArchiver = archiver.archives();
            outputInformation(firstTime, archiver, null);
        } else {
            System.out.println(OPERATION_UNARCHIVATE);
            EX15Unzipper unzipper = new EX15Unzipper(nameOfFiles[0], nameOfFiles[1]);
            resultOfWorkingArchiver = unzipper.unzips();
            outputInformation(firstTime, null, unzipper);
        }

        /*} catch (Exception e) {
            if (resultOfWorkingArchiver == 1) {
                System.out.println("Some problems with archiver" + e.getMessage());
            } else {
                System.out.println("Some problems with files: " + e.getMessage());
            }
            System.exit(1);
        }*/
    }

    /**
     * Looking for arguments and create object of class Archiver
     *
     * @param args arguments of program (name of files or file and flags)
     * @return archiver with entered name of files what need to archives or unzip
     */
    private boolean processingArguments(String[] args) {
        boolean result = true;
        if (args.length == 0) {
            result = processingZeroLenghtOfArguments(args);
        } else if (args.length == 1) {
            result = processingOneLenghtOfArguments(args);
        } else if (args.length == SIZE_OF_ORIGINAL_ARGUMENTS) {
            result = processingTwoLenghtOfArguments(args);
        } else if (args.length == SIZE_OF_ARGUMENTS_WITH_FLAG) {
            result = processingAnotherLenghtOfArguments(args);
        } else {
            System.out.println("You entered wrong number of arguments");
            System.exit(1);
        }
        return result;
    }

    /**
     * Processing empty arguments
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingZeroLenghtOfArguments(String[] args) {
        args = new String[1];
        args[0] = DEFAULT_FILE;
        nameOfFiles[0] = args[0];
        nameOfFiles[1] = args[0] + EX15Constants.EXPANSION_FOR_ARCHIVE;
        return true;
    }

    /**
     * Processing arguments with a length of 1 element
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingOneLenghtOfArguments(String[] args) {
        String expansion = getExpansion(args[0]);
        String nameOfFile = getNameWithoutExpansion(args[0]);
        if (expansion.equals(EXPANSION_FOR_ARCHIVE)) {
            if (isPointConsume(nameOfFile)) {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = nameOfFile + EXPANSION_FOR_UNARCHIVED;
            } else {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = nameOfFile + EXPANSION_FOR_UNARCHIVED;
            }
            return false;
        } else {
            nameOfFiles[0] = args[0];
            nameOfFiles[1] = args[0] + EXPANSION_FOR_ARCHIVE;
            return true;
        }
    }

    /**
     * Processing arguments with a length of 2 elements
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingTwoLenghtOfArguments(String[] args) {
        if (isContainFlags(args)) {
            return processingFlags(args);
        } else {
            return processingWithoutFlags(args);
        }
    }

    /**
     * Processing arguments if they contain flags
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingFlags(String[] args) {
        switch (args[0]) {
            case "-a":
                nameOfFiles[0] = args[1];
                nameOfFiles[1] = args[1] + EXPANSION_FOR_ARCHIVE;
                return true;

            case "-u":
                nameOfFiles[0] = args[1];
                nameOfFiles[1] = args[1] + EXPANSION_FOR_UNARCHIVED;
                return false;
        }
        System.out.println("You entered wrong flags");
        System.exit(1);
        return true;
    }

    /**
     * Processing arguments if they don`t contain flags
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingWithoutFlags(String[] args) {
        String expansion = getExpansion(args[0]);
        if (expansion.equals(EXPANSION_FOR_ARCHIVE)) {
            if (isPointConsume(args[1])) {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = args[1];
            } else {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = args[1] + EXPANSION_FOR_UNARCHIVED;
            }
            return false;

        } else {
            if (isPointConsume(args[1])) {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = args[1];
            } else {
                nameOfFiles[0] = args[0];
                nameOfFiles[1] = args[1] + EXPANSION_FOR_ARCHIVE;
            }
            return true;
        }
    }

    /**
     * Processing arguments with a length of 3 elements
     *
     * @param args arguments of program (name of files or file and flags)
     * @return new object of the class Archiver
     */
    private boolean processingAnotherLenghtOfArguments(String[] args) {
        switch (args[0]) {
            case "-a" -> {
                if (isPointConsume(args[INDEX_OF_LAST_ARGUMENT])) {
                    nameOfFiles[0] = args[1];
                    nameOfFiles[1] = args[INDEX_OF_LAST_ARGUMENT];
                } else {
                    nameOfFiles[0] = args[1];
                    nameOfFiles[1] = args[INDEX_OF_LAST_ARGUMENT] + EXPANSION_FOR_ARCHIVE;
                }
                return true;
            }
            case "-u" -> {
                if (isPointConsume(args[INDEX_OF_LAST_ARGUMENT])) {
                    nameOfFiles[0] = args[1];
                    nameOfFiles[1] = args[INDEX_OF_LAST_ARGUMENT];
                } else {
                    nameOfFiles[0] = args[1];
                    nameOfFiles[1] = args[INDEX_OF_LAST_ARGUMENT] + EXPANSION_FOR_UNARCHIVED;
                }
                return false;
            }
        }
        System.out.println("You entered wrong flags");
        System.exit(1);
        return true;
    }


    /**
     * Looks to see if the arguments contain flags or not
     *
     * @param args arguments of the program (name of files or file and flags)
     * @return true if flags is consume or false if not consume
     */
    private static boolean isContainFlags(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "-a":
                    return true;

                case "-u":
                    return true;
            }
        }
        return false;
    }

    /**
     * See if the line contains a point
     *
     * @param line line in which need to look for point
     * @return true if flags is consume or false if not consume
     */
    private static boolean isPointConsume(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes a string without an extension to the variable and returns it
     *
     * @param line line which need to processing
     * @return line without expansion in the end
     */
    private static String getNameWithoutExpansion(String line) {
        String name = "";
        for (int i = 0; i < line.length() - SIZE_OF_EXPANSION; i++) {
            name += line.charAt(i);
        }
        return name;
    }

    /**
     * Get an expansion from the line
     *
     * @param line line which need to processing
     * @return line without expansion in the end
     */
    private static String getExpansion(String line) {
        String expansion = "";
        for (int i = line.length() - SIZE_OF_EXPANSION; i < line.length(); i++) {
            expansion += line.charAt(i) + "";
        }
        return expansion;
    }

    /**
     * Output information of program
     * - type of operation
     * - entered file and processed file
     * - size of entered and processed file
     * - time of working program
     * - coefficient of efficiency
     *
     * @param firstTime time when start working
     * @param archiver  object of class Archiver
     */
    private void outputInformation(double firstTime, EX15Archiver archiver, EX15Unzipper unzipper) {
        outputNamesOfFiles(archiver, unzipper);
        outputCoefficientOfEfficiency(archiver, unzipper);
        outputTimeOfWorking(firstTime);
        outputSizeOfFiles(archiver, unzipper);
    }

    /**
     * Output names of entered and processed files
     *
     * @param archiver object of class Archiver it need for taking name of entered and processed files
     * @param unzipper object of class Unzipper it need for taking name of entered and processed files
     */
    private void outputNamesOfFiles(EX15Archiver archiver, EX15Unzipper unzipper) {
        if (archiver != null) {
            System.out.println(ENTERED_FILE + archiver.enteredFilename);
            System.out.println(OUTPUT_FILE + archiver.processedFilename);
        } else {
            System.out.println(ENTERED_FILE + unzipper.enteredFilename);
            System.out.println(OUTPUT_FILE + unzipper.processedFilename);
        }
    }

    /**
     * Output coefficient of efficiency
     *
     * @param archiver object of class Archiver it need for taking size of processed and entered files
     * @param unzipper object of class Unzipper it need for taking size of processed and entered files
     */
    private void outputCoefficientOfEfficiency(EX15Archiver archiver, EX15Unzipper unzipper) {
        if (archiver != null) {
            if (archiver.sizeOfProcessedFile > 0) {
                System.out.println(countEfficiency(archiver.sizeOfEnteredFile, archiver.sizeOfProcessedFile)
                        + PERCENTS_OF_EFFICIENCY);
            }
        } else {
            if (unzipper.sizeOfProcessedFile > 0) {
                System.out.println(countEfficiency(unzipper.sizeOfEnteredFile, unzipper.sizeOfProcessedFile)
                        + PERCENTS_OF_EFFICIENCY);
            }
        }
    }

    /**
     * Output time of working
     *
     * @param firstTime time when program start working
     */
    private void outputTimeOfWorking(double firstTime) {
        double timeOfWork = (System.currentTimeMillis() - firstTime) / MILLI;
        System.out.println(timeOfWork + SECONDS);
    }

    /**
     * Output size of entered and processed files
     *
     * @param archiver object of class Archiver it need for taking size of processed and entered files
     * @param unzipper object of class Unzipper it need for taking size of processed and entered files
     */
    private void outputSizeOfFiles(EX15Archiver archiver, EX15Unzipper unzipper) {
        double sizeOfEnteredFile;
        if (archiver != null) {
            sizeOfEnteredFile = (double) archiver.sizeOfEnteredFile / MILLI;
        } else {
            sizeOfEnteredFile = (double) unzipper.sizeOfEnteredFile / MILLI;
        }
        System.out.println(sizeOfEnteredFile + SIZE_OF_ENTERED_FILE);

        if (archiver != null) {
            if (archiver.sizeOfProcessedFile > 0) {
                double sizeOfOutputFile = (double) archiver.sizeOfProcessedFile / MILLI;
                System.out.println(sizeOfOutputFile + SIZE_OF_PROCESSED_FILE);
            }
        } else {
            if (unzipper.sizeOfProcessedFile > 0) {
                double sizeOfOutputFile = (double) unzipper.sizeOfProcessedFile / MILLI;
                System.out.println(sizeOfOutputFile + SIZE_OF_PROCESSED_FILE);
            }
        }
    }

    /**
     * Count percent of efficiency of working of the program
     *
     * @param sizeOfEnteredFile   size of enteredFile
     * @param sizeOfProcessedFile size of processedFile
     * @return return percent of efficiency
     */
    private static long countEfficiency(long sizeOfEnteredFile, long sizeOfProcessedFile) {
        int percentsOfEnteredFile = 100;
        long percentsOfEfficiency = sizeOfProcessedFile * percentsOfEnteredFile / sizeOfEnteredFile;
        return Math.abs(100 - percentsOfEfficiency);
    }


}


