package org.aksw.simba.topicmodeling.utils.vocabulary;

import java.util.HashMap;
import java.util.Iterator;

public class SimpleVocabulary implements Vocabulary {

    private static final long serialVersionUID = 1647610274276283464L;

    protected HashMap<String, Integer> wordIndexMap = new HashMap<String, Integer>();

    public int size() {
        return wordIndexMap.size();
    }

    public void add(String word) {
        wordIndexMap.put(word, wordIndexMap.size());
    }

    public Integer getId(String word) {
        Integer wordId = wordIndexMap.get(word);
        if (wordId == null) {
            return -1;
        }
        return wordId;
    }

    public Iterator<String> iterator() {
        return wordIndexMap.keySet().iterator();
    }

    public String getWord(int wordId) {
        Iterator<String> iterator = wordIndexMap.keySet().iterator();
        String word;
        while (iterator.hasNext()) {
            word = iterator.next();
            if (getId(word) == wordId) {
                return word;
            }
        }
        return null;
    }

    @Override
    public void setWord(String word, String newWord) {
        wordIndexMap.put(newWord, wordIndexMap.get(word));
        wordIndexMap.remove(word);
    }
}