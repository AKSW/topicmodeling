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
package org.aksw.simba.topicmodeling.io.xml;

import org.aksw.simba.topicmodeling.automaton.AutomatonCallback;
import org.aksw.simba.topicmodeling.automaton.BricsAutomatonManager;
import org.aksw.simba.topicmodeling.automaton.MultiPatternAutomaton;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BricsBasedXmlParser implements AutomatonCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(BricsBasedXmlParser.class);

    private XMLParserObserver observer;
    private MultiPatternAutomaton automaton;
    private String text;
    private StringBuilder buffer = new StringBuilder();

    private int lastPos;

    public BricsBasedXmlParser(XMLParserObserver observer) {
        this.observer = observer;
        this.automaton = new BricsAutomatonManager(this, new String[] { "\\<[^\\<\\>]*\\>",
                "\\&[#A-Za-z][A-Za-z0-9]{1,6};" });
    }

    @Override
    public void foundPattern(int patternId, int startPos, int length) {
        buffer.append(text.substring(lastPos, startPos));
        switch (patternId) {
        case 0: {
            parseTag(startPos, length);
            break;
        }
        case 1: {
            parseEscapedCharachter(startPos, length);
            break;
        }
        default: {
            LOGGER.error("Got an unknown patternId from the automaton (patternId=" + patternId + ").");
            break;
        }
        }
        lastPos = startPos + length;
    }

    private void parseTag(int startPos, int length) {
        if (buffer.length() > 0) {
            observer.handleData(buffer.toString());
            buffer.delete(0, buffer.length());
        }
        // if this is a closing tag
        if (text.charAt(startPos + 1) == '/') {
            observer.handleClosingTag(text.substring(startPos + 2, (startPos + length) - 1));
        } else if (text.charAt(startPos + length - 2) == '/') {
            // if this is an empty xml tag
            observer.handleEmptyTag(text.substring(startPos + 1, (startPos + length) - 2));
        } else {
            observer.handleOpeningTag(text.substring(startPos + 1, (startPos + length) - 1));
        }
    }

    private void parseEscapedCharachter(int startPos, int length) {
//        buffer.append(text.substring(lastPos, startPos));
        lastPos = startPos + length;
        buffer.append(StringEscapeUtils.unescapeXml(text.substring(startPos, lastPos)));
    }

    public void parse(String text) {
        lastPos = 0;
        this.text = text;
        automaton.parseText(text);
        this.text = null;
    }
}
