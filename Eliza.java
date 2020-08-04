
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The Eliza class holds the user input and response formation for a system that collects user input
 * and responds appropriately. Eliza is based off of a computer program written at MIT in the 1960's
 * by Joseph Weizenbaum. Eliza uses keyword matching to respond to users in a way that displays
 * interest in the users and continues the conversation until instructed otherwise.
 */
public class Eliza {

    /*
     * This method does input and output with the user. It calls supporting methods to read and
     * write files and process each user input.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {
        // Milestone 2
        // create a scanner for reading user input and a random number
        // generator with Config.SEED as the seed
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random(Config.SEED);

        ArrayList<String> dialog = new ArrayList<>();
        String line = null;
        String fileName = null;
        boolean save = false;


        // Milestone 3
        // How the program starts depends on the command-line arguments.
        // Command-line arguments can be names of therapists for example:
        // Eliza Joe Laura
        // If no command-line arguments then the therapists name is Eliza
        // and this reads a file with that name and the Config.RESPONSE_FILE_EXTENSION.
        // Example filename: Eliza.rsp
        // If only one command-line argument, then read the responses from
        // the corresponding file with Config.RESPONSE_FILE_EXTENSION.
        // If there is more than one command-line argument then offer them
        // as a list of people to talk with. For the three therapists above the prompt
        // is
        // "Would you like to speak with Eliza, Joe, or Laura?"
        // When a user types a name then read the responses from the file which
        // is the selected name and Config.RESPONSE_FILE_EXTENSION extension.
        // Whatever name the user types has the extension appended and
        // is read using loadResponseTable. If loadResponseTable can't load
        // the file then it will report an error.
        String therapyName = "Eliza";
        if (args != null && args.length != 0) {
            if (args.length == 1) {
                therapyName = args[0];
            } else {
                System.out.print("Would you like to speak with");
                for (int i = 0; i < args.length - 1; i++) {
                    System.out.print(" " + args[i] + ",");
                }
                System.out.print(" or " + args[args.length - 1] + "? ");
                therapyName = scnr.nextLine();
            }
        }
        // loading responseTable
        ArrayList<ArrayList<String>> responseTable =
            loadResponseTable(therapyName + Config.RESPONSE_FILE_EXTENSION);
        // Milestone 2
        // name prompt
        System.out.println(line = "Hi I'm " + therapyName + ", what is your name?");
        dialog.add(line);
        String patientName = scnr.nextLine();
        dialog.add(patientName);

        // Milestone 2
        // welcome prompt
        System.out.println(line = "Nice to meet you " + patientName + ". What is on your mind?");
        dialog.add(line);
        // Milestone 2
        // begin conversation loop
        do {
            // Milestone 2
            // obtain user input
            String userInput = scnr.nextLine();
            dialog.add(userInput);
            String[] userWords = null;
            // Milestone 2
            // prepareInput
            // Milestone 2
            // end loop if quit word
            if ((userWords = prepareInput(userInput)) == null) {
                break;
            }

            // Milestone 3
            // if no quit words then prepareResponse
            System.out.println(line = prepareResponse(userWords, rand, responseTable));
            dialog.add(line);
        } while (true);

        // Milestone 2
        // ending prompt
        System.out.println(line = "GoodBye " + patientName);
        dialog.add(line);

        // Milestone 3
        // Save all conversation (user and system responses) starting
        // with this program saying "Hi I'm..." and concludes with
        // "Goodbye <name>.".
        // Always prompt the user to see if they would like to save a
        // record of the conversation. If the user enters a y or Y as the
        // first non-whitespace character then prompt for filename and save,
        // otherwise don't save dialog. After successfully saving a dialog
        // print the message "Thanks again for talking! Our conversation is saved in:
        // <filename>".
        // If saveDialog throws an IOException then catch it, print out the error:
        // "Unable to save conversation to: " <name of file>
        // Repeat the code prompting the user if they want to save the dialog.
        System.out.print("Would you like to have a record of our conversation (y/n): ");
        line = scnr.nextLine();
        if (line.toLowerCase().charAt(0) == 'y') {
            boolean again = false;
            do {
                System.out.print("Enter filename: ");
                File file = new File(fileName = scnr.nextLine());
                try {
                    saveDialog(dialog, fileName);
                    save = true;
                } catch (IOException e) {
                    System.out.println("Unable to save conversation to: " + fileName);
                    System.out.print("Would you like to have a record of our conversation (y/n): ");
                    line = scnr.nextLine();
                    if (line.toLowerCase().charAt(0) == 'y') {
                        again = true;
                    }
                }
            } while (again);

            if (save) {
                System.out
                    .println("Thanks again for talking! Our conversation is saved in: " + fileName);
            }
        }

    }

    /**
     * This method processes the user input, returning an ArrayList containing
     * Strings, where each String is a phrase from the user's input. This is done by
     * removing leading and trailing whitespace, making the user's input all lower
     * case, then going through each character of the user's input. When going
     * through each character this keeps all digits, alphabetic characters and '
     * (single quote). The characters ? ! , . signal the end of a phrase, and
     * possibly the beginning of the next phrase, but are not included in the
     * result. All other characters such as ( ) - " ] etc. should be replaced with a
     * space. This method makes sure that every phrase has some visible characters
     * but no leading or trailing whitespace and only a single space between words
     * of a phrase. If userInput is null then return null, if no characters then
     * return a 0 length list, otherwise return a list of phrases. Empty phrases and
     * phrases with just invalid/whitespace characters should NOT be added to the
     * list.
     * 
     * Example userInput: "Hi, I am! a big-fun robot!!!" Example returned: "hi", "i
     * am", "a big fun robot"
     * 
     * @param userInput text the user typed
     * @return the phrases from the user's input
     */
    public static ArrayList<String> separatePhrases(String userInput) {
        // the arraylist for phrases from user's input
        ArrayList<String> userWords = new ArrayList<String>();

        // If userInput is null, returns null
        if (userInput == null) {
            return null;
        }

        // if no characters, returns a 0 length list
        if (userInput.trim().toLowerCase().length() == 0) {
            return userWords;
        }

        // removing leading and trailing whitespace& to lower case
        userInput = userInput.trim().toLowerCase();

        // replace ? ! , by .
        userInput = userInput.replace('?', '.');
        userInput = userInput.replace('!', '.');
        userInput = userInput.replace(',', '.');

        // replace everything with space except for .
        for (int i = 0; i < userInput.length(); i++) {
            if (!(Character.isLetterOrDigit(userInput.charAt(i))
                || Character.isWhitespace(userInput.charAt(i)) || userInput.charAt(i) == '.'
                || userInput.charAt(i) == '\'')) {
                userInput = userInput.replace(userInput.charAt(i), ' ');
            }
        }

        // keeping only one space between words
        while (userInput.indexOf("  ") != -1) {
            userInput = userInput.replaceAll("  ", " ");
        }

        // Separating phrases
        // if need to seperate
        while (userInput.indexOf('.') != -1 && userInput.length() != 0) {
            if (userInput.indexOf('.') == 0) {
                userInput = userInput.substring(userInput.indexOf('.') + 1).trim();
                continue;
            }
            userWords.add(userInput.substring(0, userInput.indexOf('.')).trim());
            userInput = userInput.substring(userInput.indexOf('.') + 1).trim();
        }
        // if dont need to seperate
        if (userInput.indexOf('.') == -1 && userInput.trim().length() != 0) {
            userWords.add(userInput.trim());
        }


        return userWords;
    }

    /*
     * Checks whether any of the phrases in the parameter match a quit word from Config.QUIT_WORDS.
     * Note: complete phrases are matched, not individual words within a phrase.
     * 
     * @param phrases List of user phrases
     * 
     * @return true if any phrase matches a quit word, otherwise false
     */
    public static boolean foundQuitWord(ArrayList<String> phrases) {
        for (int i = 0; i < phrases.size(); i++) {
            for (int j = 0; j < Config.QUIT_WORDS.length; j++) {
                if (phrases.get(i).equals(Config.QUIT_WORDS[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Iterates through the phrases of the user's input, finding the longest phrase
     * to which to respond. If two phrases are the same length, returns whichever
     * has the lower index in the list. If phrases parameter is null or size 0 then
     * return null.
     * 
     * @param phrases List of user phrases
     * @return the selected phrase
     */
    public static String selectPhrase(ArrayList<String> phrases) {
        // If phrases parameter is null or size 0, returns null
        if (phrases == null || phrases.isEmpty()) {
            return null;
        }

        // the index of the longest phrase
        int longest = 0;

        // finding the longest phrase
        for (int i = 1; i < phrases.size(); i++) {
            if (phrases.get(i).length() > phrases.get(longest).length()) {
                longest = i;
            }
        }

        return phrases.get(longest);
    }

    /**
     * Looks for a replacement word for the word parameter and if found, returns the
     * replacement word. Otherwise if the word parameter is not found then the word
     * parameter itself is returned. The wordMap parameter contains rows of match
     * and replacement strings. On a row, the element at the 0 index is the word to
     * match and if it matches return the string at index 1 in the same row. Some
     * example word maps that will be passed in are Config.INPUT_WORD_MAP and
     * Config.PRONOUN_MAP.
     * 
     * If word is null return null. If wordMap is null or wordMap length is 0 simply
     * return word parameter. For this implementation it is reasonable to assume
     * that if wordMap length is >= 1 then the number of elements in each row is at
     * least 2.
     * 
     * @param word    The word to look for in the map
     * @param wordMap The map of words to look in
     * @return the replacement string if the word parameter is found in the wordMap
     *         otherwise the word parameter itself.
     */
    public static String replaceWord(String word, String[][] wordMap) {
        // If word is null, returns null.
        if (word == null) {
            return null;
        }
        // If wordMap is null or wordMap length is 0, returns word parameter
        if (wordMap == null || wordMap.length == 0) {
            return word;
        }

        // finding the replacement word
        for (int i = 0; i < wordMap.length; i++) {
            if (word.equals(wordMap[i][0])) {
                return wordMap[i][1];
            }
        }

        return word;
    }

    /**
     * Concatenates the elements in words parameter into a string with a single
     * space between each array element. Does not change any of the strings in the
     * words array. There are no leading or trailing spaces in the returned string.
     * 
     * @param words a list of words
     * @return a string containing all the words with a space between each.
     */
    public static String assemblePhrase(String[] words) {
        // the assembled phrase
        String phrases = "";

        // assembling phrases
        for (int i = 0; i < words.length; i++) {
            if (words[i] == null) {
                continue;
            }
            phrases += words[i] + " ";
        }

        // deleting the trailing space
        if (phrases.length() > 0) {
            return phrases.substring(0, phrases.length() - 1);
        } else {
            return phrases;
        }

    }

    /**
     * Replaces words in phrase parameter if matching words are found in the mapWord
     * parameter. A word at a time from phrase parameter is looked for in wordMap
     * which may result in more than one word. For example: i'm => i am Uses the
     * replaceWord and assemblePhrase methods. Example wordMaps are
     * Config.PRONOUN_MAP and Config.INPUT_WORD_MAP. If wordMap is null then phrase
     * parameter is returned. Note: there will Not be a case where a mapping will
     * itself be a key to another entry. In other words, only one pass through
     * swapWords will ever be necessary.
     * 
     * @param phrase  The given phrase which contains words to swap
     * @param wordMap Pairs of corresponding match & replacement words
     * @return The reassembled phrase
     */
    public static String swapWords(String phrase, String[][] wordMap) {
        // null wordMap
        if (wordMap == null) {
            return phrase;
        }

        // seperateing phrases
        String[] words = null;
        words = phrase.split(" ");


        // swapping
        for (int i = 0; i < words.length; i++) {
            words[i] = replaceWord(words[i], wordMap);
        }

        // assemble phrases
        phrase = assemblePhrase(words);

        return phrase;
    }

    /**
     * This prepares the user input. First, it separates input into phrases (using
     * separatePhrases). If a phrase is a quit word (foundQuitWord) then return
     * null. Otherwise, select a phrase (selectPhrase), swap input words (swapWords
     * with Config.INPUT_WORD_MAP) and return an array with each word its own
     * element in the array.
     * 
     * @param input The input from the user
     * @return words from the selected phrase
     */
    public static String[] prepareInput(String input) {
        if (input == null || input.isEmpty()) {
            return new String[] {""};
        }
        // words from the selected phrase
        String[] selectWords;

        // separates input into phrases
        ArrayList<String> phrases = separatePhrases(input);
        // check quit word
        if (foundQuitWord(phrases)) {
            return null;
        } else {
            String select = selectPhrase(phrases);
            select = swapWords(select, Config.INPUT_WORD_MAP);
            selectWords = select.split(" ");
        }

        return selectWords;
    }

    /**
     * Reads a file that contains keywords and responses. A line contains either a
     * list of keywords or response, any blank lines are ignored. All leading and
     * trailing whitespace on a line is ignored. A keyword line begins with
     * "keywords" with all the following tokens on the line, the keywords. Each line
     * that follows a keyword line that is not blank is a possible response for the
     * keywords. For example (the numbers are for our description purposes here and
     * are not in the file):
     * 
     * 1 keywords computer 2 Do computers worry you? 3 Why do you mention computers?
     * 4 5 keywords i dreamed 6 Really, <3>? 7 Have you ever fantasized <3> while
     * you were awake? 8 9 Have you ever dreamed <3> before?
     *
     * In line 1 is a single keyword "computer" followed by two possible responses
     * on lines 2 and 3. Line 4 and 8 are ignored since they are blank (contain only
     * whitespace). Line 5 begins new keywords that are the words "i" and "dreamed".
     * This keyword list is followed by three possible responses on lines 6, 7 and
     * 9.
     * 
     * The keywords and associated responses are each stored in their own ArrayList.
     * The response table is an ArrayList of the keyword and responses lists. For
     * every keywords list there is an associated response list. They are added in
     * pairs into the list that is returned. There will always be an even number of
     * items in the returned list.
     * 
     * Note that in the event an IOException occurs when trying to read the file
     * then an error message "Error reading <fileName>", where <fileName> is the
     * parameter, is printed and a non-null reference is returned, which may or may
     * not have any elements in it.
     * 
     * @param fileName The name of the file to read
     * @return The response table
     */
    public static ArrayList<ArrayList<String>> loadResponseTable(String fileName) {
        // keyWords ArrayList
        ArrayList<ArrayList<String>> resTable = new ArrayList<>();

        // creating response table
        try (Scanner read = new Scanner(new FileReader(fileName));) {
            // read file loop
            while (read.hasNextLine()) {
                String aline = read.nextLine();
                // read a keyword and its following response
                if (aline.contains("keywords")) {
                    resTable
                        .add(new ArrayList<String>(Arrays.asList(aline.substring(9).split(" "))));
                    resTable.add(new ArrayList<String>());
                    // read following response loop
                    while (read.hasNextLine() && !read.hasNext("keywords")) {
                        aline = read.nextLine();
                        if (aline.equals("")) {
                            continue;
                        }
                        resTable.get(resTable.size() - 1).add(aline);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + fileName);
            return resTable;
        }


        return resTable;
    }

    /**
     * Checks to see if the keywords match the sentence. In other words, checks to
     * see that all the words in the keyword list are in the sentence and in the
     * same order. If all the keywords match then this method returns an array with
     * the unmatched words before, between and after the keywords. If the keywords
     * do not match then null is returned.
     * 
     * When the phrase contains elements before, between, and after the keywords,
     * each set of the three is returned in its own element String[] keywords =
     * {"i", "dreamed"}; String[] phrase = {"do", "you", "know", that", "i", "have",
     * "dreamed", "of", "being", "an", "astronaut"};
     * 
     * toReturn[0] = "do you know that" toReturn[1] = "have" toReturn[2] = "of being
     * an astronaut"
     * 
     * In an example where there is a single keyword, the resulting List's first
     * element will be the the pre-sequence element and the second element will be
     * everything after the keyword, in the phrase String[] keywords = {"always"};
     * String[] phrase = {"I", "always", "knew"};
     * 
     * toReturn[0] = "I" toReturn[1] = "knew"
     * 
     * In an example where a keyword is not in the phrase in the correct order, null
     * is returned. String[] keywords = {"computer"}; String[] phrase = {"My","dog",
     * "is", "lost"};
     * 
     * return null
     * 
     * @param keywords The words to match, in order, in the sentence.
     * @param phrase   Each word in the sentence.
     * @return The unmatched words before, between and after the keywords or null if
     *         the keywords are not all matched in order in the phrase.
     */
    public static String[] findKeyWordsInPhrase(ArrayList<String> keywords, String[] phrase) {

        // the unmatched words to retrun
        String[] unmatch = new String[keywords.size() + 1];
        Arrays.fill(unmatch, "");

        // matching
        int i = 0;
        for (int j = 0; j < phrase.length; j++) {
            if (i < keywords.size() && phrase[j].equals(keywords.get(i))) {
                unmatch[i] = unmatch[i].trim();
                i++;
                continue;
            }
            unmatch[i] += phrase[j] + " ";
        }
        unmatch[unmatch.length - 1] = unmatch[unmatch.length - 1].trim();

        // if not all keywords are matched
        if (i < keywords.size()) {
            return null;
        }

        return unmatch;
    }

    /**
     * Selects a randomly generated response within the list of possible responses
     * using the provided random number generator where the number generated
     * corresponds to the index of the selected response. Use Random nextInt(
     * responseList.size()) to generate the random number. If responseList is null
     * or 0 length then return null.
     * 
     * @param rand         A random number generator.
     * @param responseList A list of responses to choose from.
     * @return A randomly selected response
     */
    public static String selectResponse(Random rand, ArrayList<String> responseList) {
        // responseList is null or 0 length then return null.
        if (responseList == null || responseList.isEmpty()) {
            return null;
        }

        return responseList.get(rand.nextInt(responseList.size()));
    }

    /**
     * This method takes processed user input and forms a response. This looks
     * through the response table in order checking to see if each keyword pattern
     * matches the userWords. The first matching keyword pattern found determines
     * the list of responses to choose from. A keyword pattern matches the
     * userWords, if all the keywords are found, in order, but not necessarily
     * contiguous. This keyword matching is done by findKeyWordsInPhrase method. See
     * the findKeyWordsInPhrase algorithm in the Eliza.pdf.
     * 
     * If no keyword pattern matches then Config.NO_MATCH_RESPONSE is returned.
     * Otherwise one of possible responses for the matched keywords is selected with
     * selectResponse method. The response selected is checked for the replacement
     * symbol <n> where n is 1 to the length of unmatchedWords array returned by
     * findKeyWordsInPhrase. For each replacement symbol the corresponding unmatched
     * words element (index 0 for <1>, 1 for <2> etc.) has its pronouns swapped with
     * swapWords using Config.PRONOUN_MAP and then replaces the replacement symbol
     * in the response.
     * 
     * @param userWords     using input after preparing.
     * @param rand          A random number generator.
     * @param responseTable A table containing a list of keywords and response
     *                      pairs.
     * @return The generated response
     */
    public static String prepareResponse(String[] userWords, Random rand,
        ArrayList<ArrayList<String>> responseTable) {

        int matchRow = -1;
        String[] unmatch = null;
        String respon = null;

        // Iterate through the response table.
        // The response table has paired rows. The first row is a list of key
        // words, the next a list of corresponding responses. The 3rd row another
        // list of keywords and 4th row the corresponding responses.
        for (int i = 0; i < responseTable.size(); i += 2) {
            // checks to see if the current keywords match the user's words
            // using findKeyWordsInPhrase.
            if ((unmatch = findKeyWordsInPhrase(responseTable.get(i), userWords)) != null) {
                matchRow = i;
                break;
            }
        }
        // if no keyword pattern was matched, return Config.NO_MATCH_RESPONSE
        // else, select a response using the appropriate list of responses for the
        // keywords
        if (matchRow == -1 && responseTable.size() == 0) {
            return Config.NO_MATCH_RESPONSE;
        }
        if (matchRow == -1) {
            return selectResponse(rand, responseTable.get(responseTable.size() - 1));
        } else {
            respon = selectResponse(rand, responseTable.get(matchRow + 1));
        }

        // Look for <1>, <2> etc in the chosen response. The number starts with 1 and
        // there won't be more than the number of elements in unmatchedWords returned by
        // findKeyWordsInPhrase. Note the number of elements in unmatchedWords will be
        // 1 more than the number of keywords.
        // For each <n> found, find the corresponding unmatchedWords phrase (n-1) and
        // swap
        for (int i = 0; i < responseTable.get(matchRow).size() + 2; i++) {
            if (respon.contains("<" + i + ">")) {
                respon =
                    respon.replace("<" + i + ">", swapWords(unmatch[i - 1], Config.PRONOUN_MAP));
            }
        }
        // its pronoun words (swapWords using Config.PRONOUN_MAP). Then use the
        // result to replace the <n> in the chosen response.

        // in the selected echo, swap pronouns

        // inserts the new phrase with pronouns swapped, into the response

        return respon;
    }

    /**
     * Creates a file with the given name, and fills that file line-by-line with the
     * tracked conversation. Every line ends with a newline. Throws an IOException
     * if a writing error occurs.
     * 
     * @param dialog   the complete conversation
     * @param fileName The file in which to write the conversation
     * @throws IOException
     */
    public static void saveDialog(ArrayList<String> dialog, String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(fileName);

        for (int i = 0; i < dialog.size(); i++) {
            pw.println(dialog.get(i));
        }

        pw.close();

    }
}
