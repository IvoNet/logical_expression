/*
 * Copyright (c) 2008 - 2013 ivonet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package nl.ivonet.logical_expression.interpreter.operator;

import nl.ivonet.logical_expression.interpreter.IllegalExpressionException;
import nl.ivonet.logical_expression.interpreter.Strict;
import nl.ivonet.logical_expression.util.Stack;
import org.apache.log4j.Logger;

import java.util.EmptyStackException;
import java.util.List;

import static nl.ivonet.logical_expression.util.TextUtil.hasWord;

/**
 * Class representing the Not operator. The not operator is a unary operator so it is applied to one value from the
 * value {@link Stack}.
 *
 * @author Ivo Woltring
 */
public class NotOperator extends AbstractOperator {
    private static final Logger LOG = Logger.getLogger(NotOperator.class);

    public static final String OPERATOR = "!";
    private final Strict strict;

    /**
     * Constructor.
     */
    public NotOperator() {
        this.strict = Strict.LOOSE;
    }

    /**
     * Constructor.
     *
     * @param strict gives a {@link nl.ivonet.logical_expression.interpreter.Strict} indication.
     */
    public NotOperator(final Strict strict) {
        this.strict = strict;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean apply(final List<String> words, final Stack<String> valueStack) {
        String word = null;
        try {
            word = valueStack.pop();
        } catch (EmptyStackException e) {
            throw new IllegalExpressionException("Please take a look at your expression. The expression could not be" +
                                                 " evaluated on a NOT operator. Found value: NOT [" + word + "]", e);

        }
        final boolean wordExists = hasWord(words, word, strict);
        final boolean result = !wordExists;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Evaluated " + OPERATOR + " [" + word + "] (" + wordExists + ") to [" + result + "].");
        }
        pushResultToValueStack(result, valueStack);
        return result;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The {@link NotOperator} has precendence over AND and OR operators.
     */
    @Override
    public final boolean hasGreaterPrecedenceThen(final Operator thisOp) {
        return thisOp instanceof AndOperator || thisOp instanceof OrOperator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return OPERATOR;
    }
}
