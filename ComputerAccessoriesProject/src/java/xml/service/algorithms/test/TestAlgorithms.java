/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service.algorithms.test;

import xml.service.algorithms.JaroWinklerDistance;

/**
 *
 * @author admin
 */
public class TestAlgorithms {
    public static void main(String[] args) {
        testSimilarity();
    }
    
    public static void testSimilarity() {
        JaroWinklerDistance service = new JaroWinklerDistance();
        
        System.out.println("Test Case 1:");
        System.out.println(service.calculateSimilarity("MARTHA", "MARHTA"));
        System.out.println("");
        
        service = new JaroWinklerDistance();
        System.out.println("Test Case 2:");
        System.out.println(service.calculateSimilarity("DIXON", "DICKSONX"));
        service = new JaroWinklerDistance();
        System.out.println("");
        
        System.out.println("Test Case 3:");
        System.out.println(service.calculateSimilarity("JELLYFISH", "SMELLYFISH"));
        System.out.println("");
        
        System.out.println("Test Case 4:");
        System.out.println(service.calculateSimilarity("ớL", "OL"));
        System.out.println("");
        
        System.out.println("Test Case 5:");
        System.out.println(service.calculateSimilarity("ớL", "OL"));
        System.out.println("");
        
        System.out.println("Test Case 6:");
        System.out.println(service.calculateSimilarity("JONES", "JOHNSON"));
        System.out.println("");
        
        System.out.println("Test Case 7:");
        System.out.println(service.calculateSimilarity("ABCVWXYZ", "CABVWXYZ"));
        System.out.println("");
        
        System.out.println("Test Case 8:");
        System.out.println(service.calculateSimilarity("ABCVWXYZ", "CBAVWXYZ"));
        System.out.println("");
        
        System.out.println("Test Case 9:");
        System.out.println(service.calculateSimilarity("may tinh", "Màn hình AOC 27''I2769 LED IPS"));
        System.out.println("");
        
        System.out.println("Test Case 10:");
        System.out.println(service.calculateSimilarity("ABCDUVWXYZ", "DABCUVWXYZ"));
        System.out.println("");
        
        System.out.println("Test Case 11:");
        System.out.println(service.calculateSimilarity("Màn hình", "Màn hình AOC 27''I2769 LED IPS"));
        System.out.println("");
        
        System.out.println("Test Case 12:");
        System.out.println(service.calculateSimilarity("Màn hình", "Màn hình cong LCD 32'' Kinglight M3265P 144hz"));
        System.out.println("");
        
        System.out.println("Test Case 13:");
        System.out.println(service.calculateSimilarity("Màn hình", "LCD 25'' Kinglight M2559P 144hz"));
        System.out.println("");
        
        System.out.println("Test Case 14:");
        System.out.println(service.calculateSimilarity("Màn hình", "VGA MSI Armor RX580 8G 2 Fan, Điểm Benchmark: 8443 (Cũ, còn full box, còn bảo hành 2 năm)"));
        System.out.println("");
        
        System.out.println("Test Case 15:");
        System.out.println(service.calculateSimilarity("Màn hình", "Wifi card gắn trong cho Laptop"));
        System.out.println("");
        
        System.out.println("Test Case 16:");
        System.out.println(service.calculateSimilarity("Màn hình", "Chuột chơi game Redragon Cobra M711"));
        System.out.println("");
    }
}
