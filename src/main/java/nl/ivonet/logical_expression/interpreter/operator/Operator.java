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
 * Interface representing an Operator which can be applied to a value from a {@link nl.ivonet.logical_expression.util
 * .Stack}.
 *
 * @author Ivo Woltring
 */
public interface Operator {

    /**
     * Checks if the words match against the valueStack according to the Operator and the value in the valueStack.
     *
     * @param words      the words to match against
     * @param valueStack The stack containing the values from the expression
     * @return true when a word matches against the operator
     */
    boolean apply(List<String> words, Stack<String> valueStack);

    /**
     * Determines if this operator has greater precendece than the given operator.
     *
     * @param thisOp the given {@link Operator}
     * @return true of this {@link Operator} has more precendence than the given {@link Operator}
     */
    boolean hasGreaterPrecedenceThen(Operator thisOp);
}
