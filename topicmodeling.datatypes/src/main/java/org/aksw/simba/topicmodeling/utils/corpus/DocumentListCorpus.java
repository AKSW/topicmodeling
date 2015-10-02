/**
 * This file is part of topicmodeling.datatypes.
 *
 * topicmodeling.datatypes is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * topicmodeling.datatypes is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with topicmodeling.datatypes.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.simba.topicmodeling.utils.corpus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aksw.simba.topicmodeling.utils.doc.Document;
import org.aksw.simba.topicmodeling.utils.doc.DocumentText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DocumentListCorpus<T extends List<Document>> extends AbstractCorpus {

    private static final long serialVersionUID = 761394991935529006L;

    private static final Logger logger = LoggerFactory.getLogger(DocumentListCorpus.class);

    protected T corpus;

    public DocumentListCorpus(T listImplementation)
    {
        corpus = listImplementation;
    }

    public void addDocument(Document document)
    {
        // corpus.add(document.getDocumentId(), document);
        corpus.add(document);
    }

    public String[] getTextArray() {
        ArrayList<String> texts = new ArrayList<String>(corpus.size());
        DocumentText text;
        for (Document document : corpus)
        {
            text = document.getProperty(DocumentText.class);
            if (text != null)
            {
                texts.add(text.getText());
            }
            else
            {
                logger.warn("got a Document without a DocumentText property!");
            }
        }
        return texts.toArray(new String[0]);
    }

    public Iterator<Document> getIterator() {
        return corpus.iterator();
    }

    public int size() {
        return getNumberOfDocuments();
    }

    public Document getDocument(int documentId) {
        return corpus.get(documentId);
    }

    @Override
    public int getNumberOfDocuments() {
        return corpus.size();
    }

    @Override
    public void clear() {
        corpus.clear();
    }

    @Override
    public Iterator<Document> iterator() {
        return corpus.iterator();
    }

    @Override
    public List<Document> getDocuments(int startId, int endId) {
        return corpus.subList(startId, endId);
    }
}
