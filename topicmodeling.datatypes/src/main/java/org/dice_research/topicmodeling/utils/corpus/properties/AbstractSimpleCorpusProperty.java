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
package org.dice_research.topicmodeling.utils.corpus.properties;

public class AbstractSimpleCorpusProperty<T> extends AbstractCorpusProperty {

    private static final long serialVersionUID = -8657635396026055335L;
    
    private T value;

    public AbstractSimpleCorpusProperty(T value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return get();
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
