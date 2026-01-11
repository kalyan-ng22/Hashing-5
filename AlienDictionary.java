// Time Complexity : O(n*l) - n is the number of words and l is the average length of a word
// Space Complexity : O(V+E)
// Did this code successfully run on Leetcode : Yes
// Approach : We identify that it is a graph problem and maintain an adjacency list in the form of HashMap. key will be the characters and values will be a hashset of
// it's dependent characters. We maintain an indegrees array also to capture the dependent characters count. We used topological sort algorithm and process the keys
// of the map by inserting the independent characters to the queue. Then we process the dependent charcaters of it and reduce the indegrees count. Once they become independent,
// we them to the queue as well and continue until we build a result string of hashmap's size.

class Solution {
    public String foreignDictionary(String[] words) {
        HashMap<Character, HashSet<Character>> map = new HashMap(); // for adjacency list
        int[] indegrees = new int[26]; //indegrees array
        buildGraph(words, map, indegrees);
        Queue<Character> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder();
        for(char ch : map.keySet()){
            if(indegrees[ch - 'a'] == 0){ //implies the characters that are independent
                queue.add(ch);
                result.append(ch);
            }
        }
        if(result.length() == map.size()){ //result is already formed as all characters are covered
            return result.toString();
        }
        if(queue.isEmpty() && map.size() != 0){ //no independent chars
            return "";
        }
        while(!queue.isEmpty()){
            char ch = queue.poll();
            HashSet<Character> resultSet = map.get(ch);
            for(char c : resultSet){
                indegrees[c - 'a']--; //decrement the indegrees array of that position
                if(indegrees[c - 'a'] == 0){
                    result.append(c);
                    queue.add(c);
                    if(result.length() == map.size()){
                        return result.toString();
                    }
                }
            }
        }
        return "";
    }

    private void buildGraph(String[] words, HashMap<Character, HashSet<Character>> map, int[] indegrees){
        for(String word: words){ //build the hashmap with empty sets
            for(int i = 0; i<word.length(); i++){
                char ch = word.charAt(i);
                if(!map.containsKey(ch)){
                    map.put(ch, new HashSet());
                }
            }
        }

        for(int i = 0; i<words.length-1;i++){
            String word1 = words[i];
            String word2 = words[i+1];
            int j= 0;
            if(word1.startsWith(word2) && word1.length() != word2.length()){ //edge case where word1 is greater length and contains word2
                map.clear();
                return;
            }
            while(j<word1.length() && j<word2.length()){
                char indep = word1.charAt(j);
                char dep = word2.charAt(j);
                if(indep != dep){ //chars are not equal
                    HashSet<Character> currSet = map.get(indep);
                    if(!currSet.contains(dep)){
                        currSet.add(dep); //add to key's value Set
                        indegrees[dep - 'a']++;//increment indegrees array of that index
                    }
                    break;
                }
                j++;
            }

        }
    }
}
