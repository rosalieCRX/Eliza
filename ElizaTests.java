
//
// Title: (bigprogram2)
// Files: (none)
// Course: (cs200 fall 2018)
//
// Author: ROSALIE CAI
// Email: RCAI25 @wisc.edu
// Lecturer's Name: MARC RENAULT
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (NONE)
// Online Sources: (NONE)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class contains a few methods for testing methods in the Eliza class as they are developed.
 * These methods are private since they are only intended for use within this class.
 * 
 * @author Jim Williams
 * @author TODO add your name here when you add tests and comment the tests
 *
 */
public class ElizaTests {

    /**
     * This is the main method that runs the various tests. Uncomment the tests when you are ready
     * for them to run.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {

        // Milestone 1: Process User Input
        // M1: main nothing to do
        testSeparatePhrases();
        testFoundQuitWord();
        testSelectPhrase();
        testReplaceWord();
        testAssemblePhrase();

        // Milestone 2:
        // M2: implement parts of main as described in main method comments
        testSwapWords();
        testPrepareInput();
        testLoadResponseTable();

        // Milestone 3:
        // main: implement the rest of main as described in the main method comments
        testFindKeyWordsInPhrase();
        testSelectResponse();
        testInputAndResponse();
        testSaveDialog();
    }

    /**
     * This runs some tests on the separatePhrases method. 1. TODO describe each
     * test in your own words. 2.
     */
    private static void testSeparatePhrases() {
        boolean error = false;

        // test1
        ArrayList<String> phrases1 = Eliza.separatePhrases("No.  I'm talking  to my dog! Bye.");
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("no");
        expected1.add("i'm talking to my dog");
        expected1.add("bye");

        if (phrases1.size() != expected1.size()) {
            error = true;
            System.out.println("testSeparatePhrases: expected " + expected1.size() + " but found "
                + phrases1.size() + " phrases.");
        }

        // outprint1
        for (int i = 0; i < expected1.size(); i++) {
            if (!expected1.get(i).equals(phrases1.get(i))) {
                error = true;
                System.out.println("testSeparatePhrases: phrases not the same");
                System.out.println("  " + expected1.get(i));
                System.out.println("  " + phrases1.get(i));
            }
        }

        // test2
        ArrayList<String> phrases2 =
            Eliza.separatePhrases("!!!What? This isn't the 4th messy-sentence!");
        ArrayList<String> expected2 = new ArrayList<>();
        expected2.add("what");
        expected2.add("this isn't the 4th messy sentence");

        if (phrases2.size() != expected2.size()) {
            error = true;
            System.out.println("testSeparatePhrases: expected " + expected2.size() + " but found "
                + phrases2.size() + " phrases.");
        }

        // outprint2
        for (int i = 0; i < expected2.size(); i++) {
            if (!expected2.get(i).equals(phrases2.get(i))) {
                error = true;
                System.out.println("testSeparatePhrases: phrases not the same");
                System.out.println("  " + expected2.get(i));
                System.out.println("  " + phrases2.get(i));
            }
        }

        // test3-test null
        ArrayList<String> phrases3 = Eliza.separatePhrases(null);
        ArrayList<String> expected3 = null;

        if (phrases3 != expected3) {
            error = true;
            System.out.println("testSeparatePhrases: expected " + expected3.size() + " but found "
                + phrases3.size() + " phrases.");
        }

        // test4-test empty
        ArrayList<String> phrases4 = Eliza.separatePhrases("");
        ArrayList<String> expected4 = new ArrayList<>();

        if (phrases4.size() != expected4.size()) {
            error = true;
            System.out.println("testSeparatePhrases: expected " + expected3.size() + " but found "
                + phrases3.size() + " phrases.");
        }

        // Additional test suggestions
        // "What? This isn't the 4th messy-sentence!" should result in ?
        // "NO" should result in?
        // "this tab" should result in?
        // "What?" should result in?
        // "Thank you, but no, I have to go. Goodbye!!!" should result in?

        if (error) {
            System.out.println("testSeparatePhrases failed");
        } else {
            System.out.println("testSeparatePhrases passed");
        }
    }

    /**
     * This runs some tests on the foundQuitWord method. 1. TODO describe each test
     * in your own words. 2.
     */
    // TODO should QUIT_WORDS be embedded within foundQuitWord?
    private static void testFoundQuitWord() {
        boolean error = false;

        // test1
        ArrayList<String> phrases1 = new ArrayList<>();
        phrases1.add("thank you for talking");
        phrases1.add("bye");

        boolean quit = Eliza.foundQuitWord(phrases1);
        if (!quit) {
            error = true;
            System.out.println("testFoundQuitWord 1: failed");
        }

        // test2-bye is a part of a phrase
        ArrayList<String> phrases2 = new ArrayList<>();
        phrases2.add("byethank you for talking");

        quit = Eliza.foundQuitWord(phrases2);
        if (quit) {
            error = true;
            System.out.println("testFoundQuitWord 2: failed");
        }

        // test3-bye is the first or only phrase
        ArrayList<String> phrases3 = new ArrayList<>();
        phrases3.add("bye");

        quit = Eliza.foundQuitWord(phrases3);
        if (!quit) {
            error = true;
            System.out.println("testFoundQuitWord 3: failed");
        }

        // test4-no quit words
        ArrayList<String> phrases4 = new ArrayList<>();
        phrases4.add("noquitword");

        quit = Eliza.foundQuitWord(phrases4);
        if (quit) {
            error = true;
            System.out.println("testFoundQuitWord 4: failed");
        }
        // additional test suggestions:
        // should foundQuitWord return true if bye is a part of a phrase rather than
        // separate?
        // should foundQuitWord return true if goodbye is the first or only phrase?
        // should foundQuitWord return true if there are no quit words in the phrases
        // list.

        if (error) {
            System.err.println("testFoundQuitWord failed");
        } else {
            System.out.println("testFoundQuitWord passed");
        }
    }

    /**
     * This runs some tests on the selectPhrase method. 1. TODO describe each test
     * in your own words. 2.
     */
    private static void testSelectPhrase() {
        boolean error = false;

        // test1-choose the longest
        ArrayList<String> phrases1 = new ArrayList<>();
        phrases1.add("no");
        phrases1.add("sometimes I remember being on a boat");
        phrases1.add("not often");
        String choice1 = Eliza.selectPhrase(phrases1);
        if (!choice1.equals("sometimes I remember being on a boat")) {
            error = true;
            System.out.println("testSelectPhrase 1: failed");
        }

        // test2-2have same length
        ArrayList<String> phrases2 = new ArrayList<>();
        phrases2.add("no");
        phrases2.add("at");
        String choice2 = Eliza.selectPhrase(phrases2);
        if (!choice2.equals("no")) {
            error = true;
            System.out.println("testSelectPhrase 2: failed");
        }

        // test3-null list
        ArrayList<String> phrases3 = null;
        String choice3 = Eliza.selectPhrase(phrases3);
        if (!(choice3 == null)) {
            error = true;
            System.out.println("testSelectPhrase 3: failed");
        }

        // test4-empty list
        ArrayList<String> phrases4 = new ArrayList<>();
        String choice4 = Eliza.selectPhrase(phrases4);
        if (!(choice4 == null)) {
            error = true;
            System.out.println("testSelectPhrase 4: failed");
        }
        // additional test suggestions:
        // What should happen when there are 2 choices of the same length?
        // What should happen if an empty list is passed to selectPhrase?

        if (error) {
            System.err.println("testSelectPhrase failed");
        } else {
            System.out.println("testSelectPhrase passed");
        }
    }

    /**
     * This runs some tests on the assemblePhrase method. 1. TODO describe each test
     * in your own words. 2.
     */
    private static void testAssemblePhrase() {
        boolean error = false;

        // test1-normal
        String[] words1 = {"This", "is a", "sentence"};
        String assem1 = Eliza.assemblePhrase(words1);
        String expectedSentence = "This is a sentence";

        if (!assem1.equals(expectedSentence)) {
            error = true;
            System.out.println("testAssembleSentence 1 failed '" + assem1 + "'");
        }

        // test2-empty
        if (!Eliza.assemblePhrase(new String[] {""}).equals("")) {
            error = true;
            System.out.println(
                "testAssembleSentence 2 failed '" + Eliza.assemblePhrase(new String[] {""}) + "'");

        }

        // test3-null
        if (!Eliza.assemblePhrase(new String[2]).equals("")) {
            error = true;
            System.out.println(
                "testAssembleSentence 3 failed '" + Eliza.assemblePhrase(new String[2]) + "'");

        }

        // additional test suggestions:
        // what should happen if an array with no strings in it is passed in?
        // what should happen if just a list of words, with no internal spaces, is
        // passed in?

        if (error) {
            System.err.println("testAssemblePhrase failed");
        } else {
            System.out.println("testAssemblePhrase passed");
        }
    }

    /**
     * This runs some tests on the findKeyWordsInPhrase method. 1. TODO describe
     * each test in your own words. 2.
     */
    private static void testFindKeyWordsInPhrase() {
        boolean error = false;

        {// block so each test has its own variable scope.
         // 1.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("computer");
            String[] phrase = {"are", "you", "a", "computer"};

            String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
            if (matches == null || matches.length != 2 || !matches[0].equals("are you a")
                || !matches[1].equals("")) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 1 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        {
            // 2.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("computer");
            String[] phrase = {"computer", "are", "you"};

            String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
            if (matches == null || matches.length != 2 || !matches[0].equals("")
                || !matches[1].equals("are you")) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 2 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        {
            // 3.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("computer");
            String[] phrase = {"does", "that", "computer", "on", "your", "desk", "work"};
            String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
            if (matches == null || matches.length != 2 || !matches[0].equals("does that")
                || !matches[1].equals("on your desk work")) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 3 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        {
            // 4.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("you");
            keywords.add("me");
            String[] phrase = {"why", "don't", "you", "like", "me"};
            String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
            if (matches == null || matches.length != 3 || !matches[0].equals("why don't")
                || !matches[1].equals("like") || !matches[2].equals("")) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 4 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        {
            // 5.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("you");
            keywords.add("me");
            String[] sentence = {"me", "don't", "like", "you"};
            String[] matches = Eliza.findKeyWordsInPhrase(keywords, sentence);
            if (matches != null) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 5 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        {
            // 6.
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add("you");
            keywords.add("me");
            String[] phrase = {"why", "don't", "you", "like"};
            String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
            if (matches != null) {
                error = true;
                System.out.println("testFindKeyWordsInPhrase 6 failed.");
                System.out.println(Arrays.toString(matches));
            }
        }

        if (error) {
            System.err.println("testFindKeyWordsInPhrase failed");
        } else {
            System.out.println("testFindKeyWordsInPhrase passed");
        }
    }

    /**
     * This runs some tests on the saveDialog method. 1. TODO describe each test in
     * your own words. 2.
     */
    private static void testSaveDialog() {
        boolean error = false;
        final String TEST_FILENAME = "testing.txt";
        { // 1.
            ArrayList<String> list = new ArrayList<>();
            list.add("this is a single line.");
            try {
                Eliza.saveDialog(list, TEST_FILENAME);
                String readFromFile = readFile(TEST_FILENAME);
                if (!readFromFile.equals(list.get(0) + "\n")) {
                    error = true;
                    System.out.println("testSaveDialog 1 failed.");
                    System.out.println("content read: " + readFromFile);
                }
                removeFile(TEST_FILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // 2. test multiple lines of output written to the file.
        // additional tests?
        { // 2.
            ArrayList<String> list = new ArrayList<>();
            list.add("this is a first line.");
            list.add("this is a second line.");
            try {
                Eliza.saveDialog(list, TEST_FILENAME);
                String readFromFile = readFile(TEST_FILENAME);
                if (!readFromFile.equals(list.get(0) + "\n" + list.get(1) + "\n")) {
                    error = true;
                    System.out.println("testSaveDialog 2 failed.");
                    System.out.println("content read: " + readFromFile);
                }
                removeFile(TEST_FILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (error) {
            System.err.println("testSaveDialog failed");
        } else {
            System.out.println("testSaveDialog passed");
        }
    }

    /**
     * Supporting method for testSaveDialog
     * 
     * @param fileName name of the file to read
     * @return the contents of the file
     */
    private static String readFile(String fileName) {
        StringBuffer buf = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
                buf.append("\n");
            }
            return buf.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Supporting method for testSaveDialog.
     * 
     * @param filename file to be removed.
     */
    private static void removeFile(String filename) {
        File file = new File(filename);
        try {
            if (file.exists())
                file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This runs some tests on the replaceword method. 1. TODO describe each test in
     * your own words. 2.
     */
    private static void testReplaceWord() {
        boolean error = false;

        { // 1.
            String word = "machine";
            String expected = "computer";
            String result = Eliza.replaceWord(word, Config.INPUT_WORD_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println("testReplaceWord 1 failed. result:'" + result + "' expected:'"
                    + expected + "'");
            }
        }

        { // 2.
            String word = "yourself";
            String expected = "myself";
            String result = Eliza.replaceWord(word, Config.PRONOUN_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println("testReplaceWord 2 failed. result:'" + result + "' expected:'"
                    + expected + "'");
            }
        }

        { // 3.If word is null, returns null
            String word = null;
            String expected = null;
            String result = Eliza.replaceWord(word, Config.PRONOUN_MAP);
            if (result != expected) {
                error = true;
                System.out.println("testReplaceWord 3 failed. result:'" + result + "' expected:'"
                    + expected + "'");
            }
        }

        { // 4.a null wordMap
            String word = "yourself";
            String expected = "yourself";
            String result = Eliza.replaceWord(word, null);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println("testReplaceWord 4 failed. result:'" + result + "' expected:'"
                    + expected + "'");
            }
        }
        // additional suggestions:
        // Do these tests meet all the requirements described in the replaceWord header
        // comment,
        // such as handling a null wordMap?

        if (error) {
            System.err.println("testReplaceWord failed");
        } else {
            System.out.println("testReplaceWord passed");
        }
    }

    /**
     * This runs some tests on the swapWords method. 1. TODO describe each test in
     * your own words. 2.
     */
    private static void testSwapWords() {
        boolean error = false;

        { // 1.
            String someWords = "i'm cant recollect";
            String expected = "i am cannot remember";
            String result = Eliza.swapWords(someWords, Config.INPUT_WORD_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println(
                    "testSwapWords 1 failed. result:'" + result + "' expected:'" + expected + "'");
            }
        }

        { // 2.
            String someWords = "i'm happy";
            String expected = "you are happy";
            String result = Eliza.swapWords(someWords, Config.PRONOUN_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println(
                    "testSwapWords 2 failed. result:'" + result + "' expected:'" + expected + "'");
            }
        }

        { // 3.
            String someWords = "about my dog";
            String expected = "about your dog";
            String result = Eliza.swapWords(someWords, Config.PRONOUN_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println(
                    "testSwapWords 3 failed. result:'" + result + "' expected:'" + expected + "'");
            }
        }

        { // 4.test null wordmap
            String someWords = "about my dog";
            String expected = "about my dog";
            String result = Eliza.swapWords(someWords, null);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println(
                    "testSwapWords 4 failed. result:'" + result + "' expected:'" + expected + "'");
            }
        }

        { // 5.test input word map and reading /
            String someWords = "about dont need my/dog";
            String expected = "about don't desire my/dog";
            String result = Eliza.swapWords(someWords, Config.INPUT_WORD_MAP);
            if (result == null || !result.equals(expected)) {
                error = true;
                System.out.println(
                    "testSwapWords 5 failed. result:'" + result + "' expected:'" + expected + "'");
            }
        }

        if (error) {
            System.err.println("testSwapWords failed");
        } else {
            System.out.println("testSwapWords passed");
        }
    }

    /**
     * This runs some tests on the selectResponse method. 1. TODO describe each test
     * in your own words. 2.
     */
    private static void testSelectResponse() {
        boolean error = false;

        { // 1.
          // is one of the 3 strings chosen?
            Random randGen = new Random(434);
            ArrayList<String> strList = new ArrayList<>();
            strList.add("The");
            strList.add("happy");
            strList.add("cat");
            String choice = Eliza.selectResponse(randGen, strList);

            if (!(choice.equals("The") || choice.equals("happy") || choice.equals("cat"))) {
                error = true;
                System.out.println("testSelectResponse 1 failed.");
            }
        }

        { // 2.
          // if called 1000 times, are the choices approximately random?
            Random randGen = new Random(765);
            ArrayList<String> strList = new ArrayList<>();
            strList.add("this");
            strList.add("is");
            strList.add("a");
            strList.add("list");
            strList.add("to");
            strList.add("choose");
            strList.add("from");
            int[] actualCount = new int[strList.size()];
            int[] expectedCount = new int[] {156, 146, 142, 138, 160, 130, 128};
            for (int iterationIndex = 0; iterationIndex < 1000; iterationIndex++) {
                String choice = Eliza.selectResponse(randGen, strList);
                for (int wordIndex = 0; wordIndex < strList.size(); wordIndex++) {
                    if (choice.equals(strList.get(wordIndex))) {
                        actualCount[wordIndex]++;
                    }
                }
            }
            // since we set a seed on the random number generator we should know the
            // expected
            // outcome
            for (int countIndex = 0; countIndex < actualCount.length; countIndex++) {
                if (actualCount[countIndex] != expectedCount[countIndex]) {
                    error = true;
                    System.out.println("testSelectResponse 2 failed.");
                    System.out.println("  expectedCount: " + Arrays.toString(expectedCount));
                    System.out.println("  actualCount: " + Arrays.toString(actualCount));
                }
            }

        }

        { // 3.only one element
            Random randGen = new Random(434);
            ArrayList<String> strList = new ArrayList<>();
            strList.add("The");
            String choice = Eliza.selectResponse(randGen, strList);

            if (!(choice.equals("The"))) {
                error = true;
                System.out.println("testSelectResponse 3 failed.");
            }
        }

        { // 4.null list
            Random randGen = new Random(434);
            ArrayList<String> strList = null;
            String choice = Eliza.selectResponse(randGen, strList);

            if (choice != null) {
                error = true;
                System.out.println("testSelectResponse 4 failed.");
            }
        }
        // additional test suggestions:
        // What should happen when a list a single string is provided?
        // What should happen when null is passed to selectResponse?

        if (error) {
            System.err.println("testSelectResponse failed");
        } else {
            System.out.println("testSelectResponse passed");
        }
    }

    /**
     * This runs some tests on the prepareInput method. 1. TODO describe each test
     * in your own words. 2.
     */
    private static void testPrepareInput() {
        boolean error = false;

        { // 1.
            String input = "bye";
            String[] result = null;
            result = Eliza.prepareInput("bye");
            if (result != null) {
                error = true;
                System.out.println("testPrepareInput 1: failed");
                System.out.println("  input: " + input);
                System.out.println("  result: " + Arrays.toString(result));
            }
        }

        { // 2.test with I said goodbye
            String input = "I said goodbye";
            String[] result = {"i", "said", "goodbye"};
            if (!Arrays.equals(result, Eliza.prepareInput("I said goodbye"))) {
                error = true;
                System.out.println("testPrepareInput 2: failed");
                System.out.println("  input: " + input);
                System.out
                    .println("  result: " + Arrays.toString(Eliza.prepareInput("I said goodbye")));
            }
        }

        { // 3.test with I can't do that
            String input = "I can't do that";
            String[] result = {"i", "cannot", "do", "that"};
            if (!Arrays.equals(result, Eliza.prepareInput("I can't do that"))) {
                error = true;
                System.out.println("testPrepareInput 3: failed");
                System.out.println("  input: " + input);
                System.out
                    .println("  result: " + Arrays.toString(Eliza.prepareInput("I can't do that")));
            }
        }

        { // 4.test seperation and assemble
            String input = "   //cant==I'm[]? 7";
            String[] result = {"cannot", "i", "am"};
            if (!Arrays.equals(result, Eliza.prepareInput("   //cant==I'm[]? 7"))) {
                error = true;
                System.out.println("testPrepareInput 4: failed");
                System.out.println("  input: " + input);
                System.out.println(
                    "  result: " + Arrays.toString(Eliza.prepareInput("   //cant==I'm[]? 7")));
            }
        }

        // additional test suggestions
        // "I said goodbye" should result in "i", "said", "goodbye"
        // "I can't do that" should result in "i", "cannot", "do", "that"

        if (error) {
            System.err.println("testPrepareInput failed");
        } else {
            System.out.println("testPrepareInput passed");
        }
    }

    /**
     * This runs some tests on the loadResponseTable method. 1. TODO describe each
     * test in your own words. 2.
     */
    private static void testLoadResponseTable() {
        boolean error = false;

        { // 1.
            ArrayList<String> expected1stRow = new ArrayList<String>();
            expected1stRow.add("computer");
            ArrayList<ArrayList<String>> table = Eliza.loadResponseTable("Eliza.rsp");
            if (!table.get(0).equals(expected1stRow)) {
                error = true;
                System.out.println("testLoadResponseTable 1: failed");
                System.out.println("  expected1stRow: " + expected1stRow);
                System.out.println("  table1stRow: " + table.get(0));
            }

            // 2.
            if (table.size() % 2 != 0) {
                error = true;
                System.out.println("testLoadResponseTable 2: failed");
                System.out.println(
                    "  expected an even number of elements in table but found: " + table.size());
            }
        }

        { // 3.right number of rows
            ArrayList<ArrayList<String>> table = Eliza.loadResponseTable("Eliza.rsp");
            if (table.size() != 132) {
                error = true;
                System.out.println(
                    "testLoadResponseTable 3: failed,should have 132 elements,but actually has "
                        + table.size());
            }
        }

        { // 4.last row
            ArrayList<String> expectedLastRow = new ArrayList<String>();
            expectedLastRow.add("");
            ArrayList<ArrayList<String>> table = Eliza.loadResponseTable("Eliza.rsp");
            if (!table.get(table.size() - 2).equals(expectedLastRow)) {
                error = true;
                System.out.println("testLoadResponseTable 4: failed");
                System.out.println("  expectedLastRow: " + expectedLastRow);
                System.out.println("  tableLastRow: " + table.get(table.size() - 2));
            }
        }

        // additional test suggestions
        // Are the right number of keywords and responses read in for a keyword/response
        // pair?
        // Are blank lines (containing no spaces or only whitespace) ignored?
        // Are the last rows of the file read in?
        // Are the right number of rows read in?

        if (error) {
            System.err.println("testLoadResponseTable failed");
        } else {
            System.out.println("testLoadResponseTable passed");
        }
    }

    /*
     * Supporting method for testInputAndResponse. Returns 1 if the test passed and 0 if the test
     * failed.
     */
    private static int checkResponse(String input, String expectedResponse, Random rand,
        ArrayList<ArrayList<String>> table) {

        String[] words = Eliza.prepareInput(input);
        if (words == null) {
            if (expectedResponse == null) {
                return 1;
            } else {
                System.out.println("testInputLines  checkResponse error");
                System.out.println("  input='" + input + "'");
                System.out.println("  response=null");
                System.out.println("  expected='" + expectedResponse + "'");
                return 0;
            }
        }

        String response = Eliza.prepareResponse(words, rand, table);
        if (!response.equals(expectedResponse)) {
            System.out.println("testPrepareResponse  checkResponse error");
            System.out.println("  input='" + input + "'");
            System.out.println("  response='" + response + "'");
            System.out.println("  expected='" + expectedResponse + "'");
            return 0;
        } else {
            return 1;
        }
    }

    /*
     * Runs tests on a bunch of example sentences using a Random number generator with a seed.
     */
    private static void testInputAndResponse() {
        int numPassed = 0;
        // since we are using a seed in the random number generator, the responses
        // should be fixed for the provided response table.
        Random randGen = new Random(923);
        ArrayList<ArrayList<String>> responseTable =
            Eliza.loadResponseTable("Eliza" + Config.RESPONSE_FILE_EXTENSION);

        numPassed += checkResponse("I like computers.",
            "What do you think machines have to do with your problem?", randGen, responseTable);

        numPassed += checkResponse("What is your name?",
            "I don't care about names -- please continue.", randGen, responseTable);
        numPassed += checkResponse("You remind me of a grumpy uncle.",
            "What other connections do you see?", randGen, responseTable);
        numPassed += checkResponse("You don't seem to say much.", "What are your feelings now?",
            randGen, responseTable);
        numPassed += checkResponse("You reflect me.", "What makes you think I reflect you?",
            randGen, responseTable);
        numPassed += checkResponse("I wish to believe you.",
            "Why do you think you wish to believe me?", randGen, responseTable);
        numPassed += checkResponse("I dreamed of going into space.",
            "Have you ever dreamed of going into space before?", randGen, responseTable);
        numPassed += checkResponse("I apologize for boring you.",
            "Why do you think you apologize for boring me?", randGen, responseTable);
        numPassed += checkResponse("Because it seems the polite thing to do.",
            "Does that reason seem to explain anything else?", randGen, responseTable);
        numPassed += checkResponse("Yes.", "You are sure.", randGen, responseTable);
        numPassed += checkResponse("I remember being in 1st grade.",
            "What is the connection between me and being in 1st grade?", randGen, responseTable);
        numPassed += checkResponse("No, not often. Sometimes I remember being on a boat.",
            "Why do you recollect being on a boat just now?", randGen, responseTable);
        numPassed += checkResponse("My family liked to play cards.",
            "Tell me more about your family.", randGen, responseTable);
        numPassed += checkResponse("Do you remember growing up?", "What about growing up?", randGen,
            responseTable);
        numPassed += checkResponse("Are you a Packers fan?",
            "Perhaps I am a packers fan in your fantasies.", randGen, responseTable);
        numPassed += checkResponse("I am sad to hear that.", "Can you explain what made you sad?",
            randGen, responseTable);
        numPassed += checkResponse("I cannot explain this.",
            "Do you really want to be able to explain this?", randGen, responseTable);
        numPassed += checkResponse("You seem to have a different perspective than many.",
            "You're not really talking about me -- are you?", randGen, responseTable);
        numPassed += checkResponse("I'm talking to my dog.",
            "How long have you been talking to your dog?", randGen, responseTable);
        numPassed += checkResponse("goodbye", null, randGen, responseTable);

        numPassed +=
            checkResponse("", "I'm not sure I understand you fully.", randGen, responseTable);

        int expected = 21;
        if (numPassed == expected) {
            System.out.println("testInputAndResponse passed ");
        } else {
            System.err.println("testInputAndResponse failed " + (expected - numPassed));
        }
    }

}
