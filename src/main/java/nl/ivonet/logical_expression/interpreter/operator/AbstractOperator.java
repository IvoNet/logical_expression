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

import nl.ivonet.logical_expression.util.Stack;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Ivo Woltring
 */
abstract class AbstractOperator implements Operator {
    private static final Logger LOG = Logger.getLogger(AbstractOperator.class);

    @Override
    public abstract boolean apply(final List<String> words, final Stack<String> valueStack);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean hasGreaterPrecedenceThen(final Operator thisOp);


    /**
     * Pushes the result of an operator to the value {@link Stack}.
     *
     * @param foundWord  The value that is pushed to the value {@link Stack}.
     * @param valueStack The value {@link Stack} to which the value is pushed.
     */
    final void pushResultToValueStack(final boolean foundWord, final Stack<String> valueStack) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Pushed [" + foundWord + "] value back to valueStack.");
        }
        valueStack.push(Boolean.toString(foundWord));
    }
}
