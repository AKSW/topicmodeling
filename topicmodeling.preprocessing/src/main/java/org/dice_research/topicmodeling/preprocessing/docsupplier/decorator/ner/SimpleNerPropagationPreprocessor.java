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
package org.dice_research.topicmodeling.preprocessing.docsupplier.decorator.ner;

import java.util.Set;

import org.dice_research.topicmodeling.utils.doc.ner.NamedEntityInText;


public class SimpleNerPropagationPreprocessor extends AbstractNerPropagationPreprocessor {

    @Override
    protected String processEntity(NamedEntityInText entity, String surfaceForm, Set<String> tokens) {
        return surfaceForm;
    }

}
