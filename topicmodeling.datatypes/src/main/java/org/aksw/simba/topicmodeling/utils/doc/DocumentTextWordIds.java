package org.aksw.simba.topicmodeling.utils.doc;

import java.util.Arrays;

public class DocumentTextWordIds extends AbstractDocumentProperty {

    private static final long serialVersionUID = -7414003911422423930L;

    private int wordIds[];

    public DocumentTextWordIds(int wordIds[]) {
        this.wordIds = wordIds;
    }

    public DocumentTextWordIds(int size) {
        this.wordIds = new int[size];
    }

    @Override
    public Object getValue() {
        return wordIds;
    }

    public int[] getWordIds() {
        return wordIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof DocumentTextWordIds) {
            return Arrays.equals(this.wordIds, ((DocumentTextWordIds) obj).wordIds);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(wordIds);
    }

    @Override
    public String toString() {
        return "DocumentTextWordIds=" + Arrays.toString(wordIds);
    }
}