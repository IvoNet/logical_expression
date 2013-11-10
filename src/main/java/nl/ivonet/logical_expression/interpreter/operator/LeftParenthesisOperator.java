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

import java.util.List;

/**
 * Class representing the ( operator. The ( operator cannot be applied to an operand from the value {@link Stack} so the
 * apply method throws an IllegalStateException. This class is used by the ExpressionInterpreter to indicate that a (
 * exists so that everything between the () must be threated as a new expression.
 *
 * @author Ivo Woltring
 */
public class LeftParenthesisOperator implements Operator {
    public static final String OPERATOR = "(";

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException Note that the LeftParenthesisOperator cannot be applied to an operand so this
     *                               method throws an IllegalStateException
     */
    @Override
    public final boolean apply(final List<String> words, final Stack<String> valueStack) {
        throw new IllegalStateException("This Operator does not allow for processing.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasGreaterPrecedenceThen(final Operator thisOp) {
        return false; // always false in this case
    }

    /**
     * {@inheritDoc}
     */
    public final String toString() {
        return OPERATOR;
    }
}
