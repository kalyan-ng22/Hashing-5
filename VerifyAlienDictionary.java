// Time Complexity : O(n*k) - n is the number of words and k is the average length of a word
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Approach : We maintain the order of priority of the characters present in order string. Now we check every pair in the words list and
// validate order of characters by getting it's corresponding order value from hashmap. We return false whenever the rule fails and if
// it's true, we check the length of the words given that one word has reached the end.

class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < order.length(); i++) { //hashmap for character and it's order
            map.put(order.charAt(i), i);
        }
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int j = 0;
            while(j < word1.length() && j < word2.length()) {
                if (map.get(word1.charAt(j)) > map.get(word2.charAt(j))) { //if higher order character comes first
                    return false;
                } if (map.get(word1.charAt(j)) < map.get(word2.charAt(j))) { //if order is correct
                    break;
                }
                j++;
            }
            if (j == word2.length() && word1.length() > word2.length()) {//check length of words when one reached end
                return false;
            }

        }
        return true;
    }
}