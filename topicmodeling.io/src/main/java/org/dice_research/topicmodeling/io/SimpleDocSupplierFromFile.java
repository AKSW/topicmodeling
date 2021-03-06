/**
 * This file is part of topicmodeling.io.
 *
 * topicmodeling.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * topicmodeling.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with topicmodeling.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dice_research.topicmodeling.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.dice_research.topicmodeling.io.factories.ByteArrayReadingFileBasedDocumentFactory;
import org.dice_research.topicmodeling.io.factories.FileBasedDocumentFactory;
import org.dice_research.topicmodeling.preprocessing.docsupplier.AbstractDocumentSupplier;
import org.dice_research.topicmodeling.utils.doc.Document;
import org.dice_research.topicmodeling.utils.doc.DocumentCharset;
import org.dice_research.topicmodeling.utils.doc.DocumentName;
import org.dice_research.topicmodeling.utils.doc.DocumentRawData;
import org.dice_research.topicmodeling.utils.doc.DocumentText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Michael R&ouml;der (michael.roeder@uni-paderborn.de)
 *
 */
public class SimpleDocSupplierFromFile extends AbstractDocumentSupplier {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDocSupplierFromFile.class);

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    protected FileBasedDocumentFactory documentFactory;

    protected Document nextDocument = null;

    public SimpleDocSupplierFromFile() {
        this(new ByteArrayReadingFileBasedDocumentFactory());
    }

    public SimpleDocSupplierFromFile(FileBasedDocumentFactory documentFactory) {
        super();
        this.documentFactory = documentFactory;
    }

    public SimpleDocSupplierFromFile(int documentStartId) {
        this(new ByteArrayReadingFileBasedDocumentFactory(), documentStartId);
    }

    public SimpleDocSupplierFromFile(FileBasedDocumentFactory documentFactory, int documentStartId) {
        super(documentStartId);
        this.documentFactory = documentFactory;
    }

    public void createAdHoc(File file) {
        nextDocument = documentFactory.createDocument(file, this.getNextDocumentId());
    }

    /**
     * 
     * @param file
     * 
     * @deprecated use {@link #createAdHoc(File)} instead with the necessary factory
     */
    @Deprecated
    public void createDocumentAdHoc(File file) {
        String content;
        try {
            content = FileUtils.readFileToString(file, DEFAULT_CHARSET.name());
        } catch (IOException e) {
            logger.error("Error while reading file \"" + file.getAbsolutePath() + '"', e);
            nextDocument = null;
            return;
        }
        nextDocument = new Document(this.getNextDocumentId());
        nextDocument.addProperty(new DocumentName(file.getName()));
        nextDocument.addProperty(new DocumentText(content));
        nextDocument.addProperty(new DocumentCharset(DEFAULT_CHARSET));
    }

    /**
     * 
     * @param file
     * 
     * @deprecated use {@link #createAdHoc(File)} instead with the necessary factory
     */
    public void createRawDocumentAdHoc(File file) {
        byte content[];
        try {
            content = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            logger.error("Error while reading file \"" + file.getAbsolutePath() + '"', e);
            nextDocument = null;
            return;
        }
        nextDocument = new Document(this.getNextDocumentId());
        nextDocument.addProperty(new DocumentName(file.getName()));
        nextDocument.addProperty(new DocumentRawData(content));
    }

    @Override
    public Document getNextDocument() {
        Document tempDoc = nextDocument;
        nextDocument = null;
        return tempDoc;
    }
}
