
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DidYouMean {

    private final String[] words;
    private Map<String, Map<Character, Integer>> countMap = new ConcurrentHashMap<>();

    public DidYouMean(String[] words) {
        this.words = words;
        for (String word : words) {
            for (int pos = 0; pos < word.length(); pos++) {
                Character ch = word.charAt(pos);
                if (countMap.get(word) == null) {
                    Map<Character, Integer> map = new ConcurrentHashMap<Character, Integer>();
                    map.put(ch, 1);
                    //countMap.put(word, )
                    countMap.put(word, map);
                } else if (countMap.get(word).get(ch) == null) {
                    Map<Character, Integer> map = countMap.get(word);
                    map.put(ch, 1);
                    countMap.put(word, map);
                } else {
                    int charCount = countMap.get(word).get(ch);
                    Map<Character, Integer> map = countMap.get(word);
                    map.put(ch, charCount + 1);
                    countMap.put(word, map);
                }
            }
        }
    }

    public String findMostSimilar(String to) {
        if (countMap.containsKey(to)) {
            return to;
        }
        HashMap<String, Integer> compCount = new HashMap<>();
        Map<Character, Integer> map = new ConcurrentHashMap<Character, Integer>();
        for (int pos = 0; pos < to.length(); pos++) {
            Character ch = to.charAt(pos);
            if (map.get(ch) == null) {
                map.put(ch, 1);
            } else {
                int charCount = map.get(ch);
                map.put(ch, charCount + 1);
            }
        }

        Iterator listIterator = countMap.keySet().iterator();
        while (listIterator.hasNext()) {
            Map<Character, Integer> tempMap = new ConcurrentHashMap<Character, Integer>();
            tempMap.putAll(map);
            String str = (String) listIterator.next();
            Map<Character, Integer> srcCharCount = countMap.get(str);
            Map<Character, Integer> tempsrcMap = new ConcurrentHashMap<Character, Integer>();
            tempsrcMap.putAll(srcCharCount);
            Iterator comCharIterator = tempMap.keySet().iterator();
            while (comCharIterator.hasNext()) {
                Character ch = (Character) comCharIterator.next();
                if (tempsrcMap.containsKey(ch)) {
                    int diff = tempsrcMap.get(ch) - tempMap.get(ch);
                    if (diff == 0) {
                        tempsrcMap.remove(ch);
                    } else {
                        diff = diff > 0 ? diff : -diff;
                        compCount
                            .put(str, (compCount.get(str) == null ? 0 : compCount.get(str)) + diff);
                        tempsrcMap.remove(ch);
                    }
                } else {

                    compCount.put(str, (compCount.get(str) == null ?
                        tempMap.get(ch) :
                        compCount.get(str) + tempMap.get(ch)));
                    tempsrcMap.remove(ch);
                }
            }

            if (!tempsrcMap.isEmpty()) {
                System.out.println(tempsrcMap);
                compCount.put(str, (compCount.get(str) == null ?
                    tempsrcMap.keySet().size() :
                    compCount.get(str) + tempsrcMap.keySet().size()));
            }
        }


        String closeMatch = new String();
        int leastVal = -1;
        Iterator iterator = compCount.keySet().iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            if (leastVal == -1 || leastVal > compCount.get(str)) {
                leastVal = compCount.get(str);
                closeMatch = str;
            }
            if (leastVal == compCount.get(str) && closeMatch.compareTo(str) > 0) {
                leastVal = compCount.get(str);
                closeMatch = str;

            }
        }
        System.out.println("closeMatch:"+closeMatch);
        return closeMatch;
    }
}


