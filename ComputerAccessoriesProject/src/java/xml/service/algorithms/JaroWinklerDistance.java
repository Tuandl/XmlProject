/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service.algorithms;

import java.util.Arrays;
import xml.utils.StringUtils;

/**
 * Algorithms to calculate the similarity percentage of 2 strings
 * references: https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance
 * @author admin
 */
public class JaroWinklerDistance {
    private final double SCALING_FACTOR = 0.1;
    private boolean[] visited = null;
    private int[] permutation = null;
    private int[] parent = null;
    
    public double calculateSimilarity(String string1, String string2) {
        String unescapeS1 = StringUtils.unescapeHtmlEntities(string1);
        String unescapeS2 = StringUtils.unescapeHtmlEntities(string2);
        
        String s1 = StringUtils.normalize(unescapeS1).toUpperCase().trim();
        String s2 = StringUtils.normalize(unescapeS2).toUpperCase().trim();
        
        
        double jaroSim = calculateJaroSimilarity(s1, s2);
        double result = calculateWinklerSimilarity(jaroSim, s1, s2);
        
//        System.out.println("S1 = " + s1 + " S2 = " + s2 + " result: " + result);
        return result;
    }
    
    private double calculateJaroSimilarity(String s1, String s2) {
        double matches = calculateMatching(s1, s2);
        if(matches == 0) {
            return 0;
        }
        double transposition = calculateTransposition();
        
        double result = ((matches / s1.length()) + 
                (matches / s2.length()) +
                ((matches - transposition) / matches)) / 3.0;
           
//        System.out.println("Jaro Similarity = " + result);
        return result;
    }
    
    private double calculateWinklerSimilarity(double jaroSim, String s1, String s2) {
        int commonPrefix = getCommonPrefix(s1, s2);
        
        double result = jaroSim + commonPrefix * SCALING_FACTOR * (1 - jaroSim);
        
//        System.out.println("Winkler Similarity = " + result);
        return result;
    }
        
    private double calculateMatching(String s1, String s2) {
        double matchCount = 0;
        
        int maxMatchingDistance = getMaxMatchDistance(s1, s2);
        boolean[] match = new boolean[s2.length()];
        Arrays.fill(match, false);
        permutation = new int[Math.max(s1.length(), s2.length())];
        Arrays.fill(permutation, -1);
        
        for(int i = 0; i < s1.length(); i++) {
            int begin = Math.max(0, i - maxMatchingDistance);
            int end = Math.min(s2.length() - 1, i + maxMatchingDistance);
            
            for(int j = begin; j <= end; j++) {
                if(!match[j] && s1.charAt(i) == s2.charAt(j)) {
                    match[j] = true;
                    permutation[i] = j;
                    matchCount = matchCount + 1;
                    break;
                }
            }
        }
//        
//        System.out.println("Matching " + matchCount);
//        System.out.println("March[]: " + Arrays.toString(match));
//        System.out.println("Permutation[]: " + Arrays.toString(permutation));
        return matchCount;
    }
    
    private double calculateTransposition() {
        visited = new boolean[permutation.length];
        Arrays.fill(visited, false);
        parent = new int[permutation.length];
        Arrays.fill(parent, -1);
        
        //reverse DFS travel
        for(int i = 0; i < permutation.length; i++) {
            if(permutation[i] != -1) {
                parent[permutation[i]] = i;
            }
        }
        
        double transposition = 0;
        for(int i = 0; i < permutation.length; i++) {
            if(!visited[i] && permutation[i] != -1) {
                int count = dfs(i);
                transposition = transposition + count - 1;
            }
        }
        
//        System.out.println("Transposition: " + transposition);
        return transposition;
    }
    
    private int dfs(int position) {
        if(position == -1) {
            return 0;
        }
        
        if(visited[position]) {
            return 0;
        }
        
        visited[position] = true;
        int currentLevel = dfs(parent[position]);
        return currentLevel + 1;
    }
    
    public int getMaxMatchDistance(String s1, String s2) {
        int maxLen = Math.max(s1.length(), s2.length());
        int result = Math.floorDiv(maxLen, 2) - 1;
        
//        System.out.println("Max matching distance = " + result);
        return result;
    }
    
    public int getCommonPrefix(String s1, String s2) {
        int count = 0;
        
        while(count < s1.length() && 
                count < s2.length() &&
                s1.charAt(count) == s2.charAt(count)) {
            count++;
        }
        
        int commonPrefix = Math.min(count, 4);
//        System.out.println("Common Prefix = " + commonPrefix);
        return commonPrefix;
    }
}
