/**
 * This file is part of topicmodeling.preprocessing.
 *
 * topicmodeling.preprocessing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * topicmodeling.preprocessing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with topicmodeling.preprocessing.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dice_research.topicmodeling.preprocessing.docsupplier.decorator;

import org.dice_research.topicmodeling.preprocessing.docconsumer.DocumentConsumer;
import org.dice_research.topicmodeling.preprocessing.docsupplier.DocumentSupplier;
import org.dice_research.topicmodeling.utils.doc.Document;

public class DocumentConsumerAdaptingSupplierDecorator extends AbstractDocumentSupplierDecorator {

    private DocumentConsumer consumer;
    private boolean isSynchronized;

    public DocumentConsumerAdaptingSupplierDecorator(DocumentSupplier documentSource, DocumentConsumer consumer) {
        this(documentSource, consumer, false);
    }

    public DocumentConsumerAdaptingSupplierDecorator(DocumentSupplier documentSource, DocumentConsumer consumer,
            boolean isSynchronized) {
        super(documentSource);
        this.consumer = consumer;
        this.isSynchronized = isSynchronized;
    }

    @Override
    protected Document prepareDocument(Document document) {
        if (document != null) {
            if (isSynchronized) {
                synchronized (consumer) {
                    consumer.consumeDocument(document);
                }
            } else {
                consumer.consumeDocument(document);
            }
        }
        return document;
    }

}
