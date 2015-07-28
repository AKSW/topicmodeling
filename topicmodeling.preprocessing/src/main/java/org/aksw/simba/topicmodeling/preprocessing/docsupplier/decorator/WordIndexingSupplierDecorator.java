package org.aksw.simba.topicmodeling.preprocessing.docsupplier.decorator;

import java.util.List;

import org.aksw.simba.topicmodeling.algorithms.VocabularyContaining;
import org.aksw.simba.topicmodeling.lang.Term;
import org.aksw.simba.topicmodeling.preprocessing.docsupplier.DocumentSupplier;
import org.aksw.simba.topicmodeling.utils.doc.Document;
import org.aksw.simba.topicmodeling.utils.doc.DocumentTextWordIds;
import org.aksw.simba.topicmodeling.utils.doc.TermTokenizedText;
import org.aksw.simba.topicmodeling.utils.vocabulary.Vocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WordIndexingSupplierDecorator extends
        AbstractPropertyAppendingDocumentSupplierDecorator<DocumentTextWordIds> implements VocabularyContaining {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordIndexingSupplierDecorator.class);

    private Vocabulary vocabulary;

    public WordIndexingSupplierDecorator(DocumentSupplier documentSource, Vocabulary vocabulary) {
        super(documentSource);
        this.vocabulary = vocabulary;
    }

    @Override
    protected DocumentTextWordIds createPropertyForDocument(Document document) {
        TermTokenizedText ttText = document.getProperty(TermTokenizedText.class);
        if (ttText != null) {
            List<Term> terms = ttText.getTermTokenizedText();
            int wordIds[] = new int[terms.size()];
            int wordId;
            Term term;
            for (int w = 0; w < terms.size(); ++w) {
                term = terms.get(w);
                wordId = vocabulary.getId(term.getLemma());
                if (wordId < 0) {
                    vocabulary.add(term.getLemma());
                    wordId = vocabulary.getId(term.getLemma());
                }
                wordIds[w] = wordId;
            }
            return new DocumentTextWordIds(wordIds);
        } else {
            LOGGER.error("Got a Document object without the needed TermTokenizedText property! Returning null.");
            return null;
        }
    }

    @Override
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

}