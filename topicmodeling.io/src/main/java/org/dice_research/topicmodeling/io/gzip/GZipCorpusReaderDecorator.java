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
package org.dice_research.topicmodeling.io.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.dice_research.topicmodeling.io.CorpusReader;
import org.dice_research.topicmodeling.io.CorpusReaderDecorator;
import org.dice_research.topicmodeling.io.CorpusWriter;
import org.dice_research.topicmodeling.io.CorpusWriterDecorator;
import org.dice_research.topicmodeling.preprocessing.docsupplier.DocumentSupplier;
import org.dice_research.topicmodeling.utils.corpus.Corpus;

public class GZipCorpusReaderDecorator implements CorpusReaderDecorator {

    protected CorpusReader reader;

    public GZipCorpusReaderDecorator(CorpusReader reader) {
        this.reader = reader;
    }

    @Override
    @Deprecated
    public void readCorpus() {
        reader.readCorpus();
    }

    @Override
    @Deprecated
    public void addDocuments(DocumentSupplier documentFactory) {
        reader.addDocuments(documentFactory);
    }

    @Override
    public Corpus getCorpus() {
        return reader.getCorpus();
    }

    @Override
    public boolean hasCorpus() {
        return reader.hasCorpus();
    }

    @Override
    public void deleteCorpus() {
        reader.readCorpus();
    }

    @Override
    public CorpusReader getDecorated() {
        return reader;
    }

}
