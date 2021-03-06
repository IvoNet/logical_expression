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
 * Class representing the Or operator. The or operator is a binary operator so it is applied to two values from the
 * value {@link Stack}.
 *
 * @author Ivo Woltring
 */
public class OrOperator extends AbstractOperator {
    private static final Logger LOG = Logger.getLogger(OrOperator.class);


    public static final String OPERATOR = "|";

    private final Strict strict;

    /**
     * Constructor.
     */
    public OrOperator() {
        this.strict = Strict.LOOSE;
    }

    /**
     * Constructor.
     *
     * @param strict gives a {@link nl.ivonet.logical_expression.interpreter.Strict} indication.
     */
    public OrOperator(final Strict strict) {
        this.strict = strict;
    }

    /**
     * {@inheritDoc}
     */
    public final boolean apply(final List<String> words, final Stack<String> valueStack) {
        String word1 = null;
        String word2 = null;
        try {
            word1 = valueStack.pop();
            word2 = valueStack.pop();
        } catch (EmptyStackException e) {
            throw new IllegalExpressionException("Please take a look at your expression. The expression could not be" +
                                                 " evaluated on a OR operator. Found values are: [" + word1 + "] OR [" +
                                                 word2 + "].", e);
        }
        final boolean word1Exists = hasWord(words, word1, strict);
        final boolean word2Exists = hasWord(words, word2, strict);
        final boolean result = word2Exists || word1Exists;
        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "Evaluated [" + word2 + "] (" + word2Exists + ") " + OPERATOR + " [" + word1 + "] (" + word2Exists +
                    ") to [" + result + "].");
        }
        pushResultToValueStack(result, valueStack);
        return result;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The OrOperator does not have precedence over any operators.
     */
    public final boolean hasGreaterPrecedenceThen(final Operator thisOp) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return OPERATOR;
    }

}
