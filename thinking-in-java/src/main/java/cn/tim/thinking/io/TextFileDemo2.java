package cn.tim.thinking.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by LuoLiBing on 16/12/2.
 */
public class TextFileDemo2 extends TreeMap<Character, Integer> {

    // 读取
    public static String read(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String s;
            // 实用了两个try嵌套保证reader会被及时关闭
            try {
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public TextFileDemo2(String filename) {
        String text = read(filename);
        for(char ch : text.toCharArray()) {
            compute(ch, (k, v) -> v == null ? 1 : (v + 1));
        }
    }

    public static void main(String[] args) {
        TextFileDemo2 textFile =
                new TextFileDemo2("/Users/tim/Documents/samples-tim/thinking-in-java/thinking-in-java.iml");
        SortedSet<Map.Entry<Character, Integer>> entries = entriesSortedByValues(textFile);
        entries.forEach((entry) -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
