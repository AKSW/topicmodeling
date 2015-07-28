package org.aksw.simba.topicmodeling.io.xml;

import java.io.File;
import java.io.IOException;

import org.aksw.simba.topicmodeling.preprocessing.docconsumer.DocumentConsumer;
import org.aksw.simba.topicmodeling.utils.doc.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlBasedCorpusPartWriter extends AbstractDocumentXmlWriter implements DocumentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlBasedCorpusPartWriter.class);

    public static final String PART_FILE_PREFIX = "part";
    public static final String PART_FILE_SUFFIX = ".xml";

    private final File outputFolder;
    private final int documentPerPart;
    private int currentPartDocCount;
    private int currentPartId = 0;
    private XmlWritingDocumentConsumer currentXmlWriter;

    public XmlBasedCorpusPartWriter(File outputFolder, int documentPerPart) {
        this.outputFolder = outputFolder;
        this.documentPerPart = documentPerPart;
    }

    @Override
    public void consumeDocument(Document document) {
        if (currentXmlWriter == null) {
            currentXmlWriter = XmlWritingDocumentConsumer.createXmlWritingDocumentConsumer(new File(outputFolder
                    .getAbsolutePath() + File.separator + PART_FILE_PREFIX + currentPartId + PART_FILE_SUFFIX));
        }
        if (currentXmlWriter != null) {
            currentXmlWriter.consumeDocument(document);
            ++currentPartDocCount;
            if (currentPartDocCount >= documentPerPart) {
                LOGGER.info("Finished part #" + currentPartId + ".");
                try {
                    currentXmlWriter.close();
                } catch (IOException e) {
                    LOGGER.error("Error while closing XmlWritingDocumentConsumer.", e);
                }
                currentXmlWriter = null;
                currentPartDocCount = 0;
                ++currentPartId;
            }
        } else {
            LOGGER.error("Couldn't create XmlWritingDocumentConsumer. Ignoring the current document.");
        }
    }

    public void close() {
        if (currentXmlWriter != null) {
            try {
                currentXmlWriter.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing XmlWritingDocumentConsumer.", e);
            }
        }
    }
}