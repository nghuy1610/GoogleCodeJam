package y2019.round1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class AlienRhyme {

    static int maxSamePrefix(String source, String tar) {
        if (source.equals("") || tar.equals("")) {return 0;}
        int count = 0;
        while (source.charAt(count) == tar.charAt(count)) {
            count++;
            if (source.length() <= count || tar.length() <= count) {break;}
        }
        return count;
    }

    static int countRhyme(String[] words) {
        Set<String> foundString = new HashSet<>();
        int count = 0;
        List<String> reverseWords = new ArrayList<>();
        for (String word : words) {
            reverseWords.add(new StringBuilder(word).reverse().toString());
        }
        Collections.sort(reverseWords);
        boolean isFound = true;
        while (isFound) {
            int max = 0;
            int pos = -1;
            // Because of sort, max possible same prefix will be between adjacent strings (they are similar)
            for (int i = 0; i < reverseWords.size() - 1; i++) {
                int nChar = maxSamePrefix(reverseWords.get(i), reverseWords.get(i+1));
                String sameString = reverseWords.get(i).substring(0, nChar);
                // Check if prefix exist already then remove character from prefix and check again
                while (foundString.contains(sameString)) {
                    nChar--;
                    if (nChar <= max) {break;}
                    sameString = reverseWords.get(i).substring(0, nChar);
                }
                if (nChar > max) {
                    max = nChar;
                    pos = i;
                }
            }
            // Fouund longest same prefix first and remove the respective strings from list.
            // Not found any then remaining string don't have same prefix
            if (max == 0) {
                isFound = false;
            } else {
                foundString.add(reverseWords.get(pos).substring(0, max));
                count += 2;
                reverseWords.remove(pos+1);
                reverseWords.remove(pos);
            }
        }
        return count;
    }

    static class TrieNode {
        boolean endString;
        TrieNode[] next = new TrieNode[26];
    }

    static int countRedundantString(TrieNode node) {
        if (node == null) {return 0;}
        int sum = 0;
        for(TrieNode childNode : node.next) {
            sum += countRedundantString(childNode);
        }
        if (node.endString) {
            sum++;
        }
        return sum >= 2 ? sum -2 : sum;
    }

    static int countRhymeUsingTrie(String[] words) {
        TrieNode root = new TrieNode();
        int totalRedundant = 0;
        TrieNode trav;
        for (String word : words) {
            trav = root;
            int len = word.length();
            for (int i = 1; i <= len; i++) {
                char c = word.charAt(len - i);
                if (trav.next[c - 'A'] == null) {
                    trav.next[c - 'A'] = new TrieNode();
                }
                trav = trav.next[c - 'A'];
            }
            trav.endString = true;
        }
        for (TrieNode node : root.next) {
            totalRedundant += countRedundantString(node);
        }
        return words.length - totalRedundant;
    }

    static int countRhymeBruteForce(String[] words) {
        return 0;
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("alien_rhyme.txt")));
        int round = scanner.nextInt();
        for(int i = 0; i < round; i++) {
            int size = scanner.nextInt();
            String[] words = new String[size];
            for (int j = 0; j < size; j++) {
                words[j] = scanner.next();
            }
            System.out.printf("Case #%d: %d\n", i+1, countRhymeUsingTrie(words));
        }
    }
}
