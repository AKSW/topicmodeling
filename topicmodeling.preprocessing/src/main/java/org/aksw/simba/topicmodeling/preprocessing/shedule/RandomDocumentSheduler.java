package org.aksw.simba.topicmodeling.preprocessing.shedule;

import java.util.Random;

import org.aksw.simba.topicmodeling.preprocessing.docsupplier.DocumentSupplier;
import org.aksw.simba.topicmodeling.utils.doc.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RandomDocumentSheduler extends AbstractDocumentSheduler{

	private static final Logger logger = LoggerFactory
			.getLogger(RandomDocumentSheduler.class);
	
	protected Random random;
	protected double portions[];

	public RandomDocumentSheduler(DocumentSupplier documentSource,
			int numberOfParts) {
		super(documentSource, numberOfParts);
		random = new Random(System.currentTimeMillis());
		portions = new double[numberOfParts - 1];
	}

	public RandomDocumentSheduler(DocumentSupplier documentSource,
			int numberOfParts, long seed) {
		super(documentSource, numberOfParts);
		random = new Random(seed);
		portions = new double[numberOfParts - 1];
	}

	@Override
	protected Document getNextDocument(int partId) {
		Document document = documentSource.getNextDocument();
		if (document != null) {
			double sample = random.nextDouble();
			int partIdForDocument = 0;

			// Search the part to which this document belong to
			while ((partIdForDocument < portions.length)
					&& (portions[partIdForDocument] < sample)) {
				++partIdForDocument;
			}

			while ((document != null) && (partId != partIdForDocument)) {
				// If this document is not in the part of the corpus which asks
				// for a new document add the document to the queue of the part
				// it belongs to and get a new document
				listOfParts[partIdForDocument].addDocumentToQueue(document);
				document = documentSource.getNextDocument();
				partIdForDocument = 0;
				sample = random.nextDouble();
				// Search the part to which this document belong to
				while ((partIdForDocument < portions.length)
						&& (portions[partIdForDocument] < sample)) {
					++partIdForDocument;
				}
			}
		}
		return document;
	}

	public void setPortionOfPart(int partId, double portion) {
		if ((partId < portions.length) && (partId >= 0)) {
			double difference = portion - portions[partId];
			portions[partId] = (partId == 0) ? portion : portion
					+ portions[partId - 1];
			// update all following portions
			for (int i = partId + 1; i < portions.length; ++i) {
				portions[i] += difference;
			}
		} else {
			if (partId == portions.length) {
				logger.info("Someone tried to set the portion of the "
						+ partId
						+ ". part of the corpus. This portion can not be set, because it will be calculated using the portions of the other parts.");
			}
		}
	}
}
