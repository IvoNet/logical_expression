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

package nl.ivonet.logical_expression.interpreter;

import nl.ivonet.logical_expression.ExpressionEvaluator;
import nl.ivonet.logical_expression.interpreter.operator.AndOperator;
import nl.ivonet.logical_expression.interpreter.operator.LeftParenthesisOperator;
import nl.ivonet.logical_expression.interpreter.operator.NotOperator;
import nl.ivonet.logical_expression.interpreter.operator.Operator;
import nl.ivonet.logical_expression.interpreter.operator.OrOperator;
import nl.ivonet.logical_expression.util.Stack;
import nl.ivonet.logical_expression.util.Tokenizer;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Ivo Woltring
 */
public class LogicalExpressionInterpreter implements ExpressionEvaluator {
    private static final Logger LOG = Logger.getLogger(LogicalExpressionInterpreter.class);
    public static final String RIGHT_PARENTHESIS_OPERATOR = ")";
    private final String delimiters;
    private final Strict strict;

    /**
     * Constructor.
     *
     * @param delimiters a string containing single char delimiters
     * @param strict     decide if strict word processing should be followed
     */
    public LogicalExpressionInterpreter(final String delimiters, final Strict strict) {
        this.delimiters = delimiters;
        this.strict = strict;
    }

    /**
     * Constructor.
     *
     * @param delimiters a string containing single char delimiters
     */
    public LogicalExpressionInterpreter(final String delimiters) {
        this.delimiters = delimiters;
        this.strict = Strict.LOOSE;
    }

    @Override
    public final boolean evaluate(final String expression, final String sentence) {
        //Initialize Stacks.
        final Stack<String> valueStack = new Stack<String>();
        final Stack<Operator> operatorStack = new Stack<Operator>();
        //Get the words from a sentence.
        final List<String> words = new Tokenizer(sentence, this.delimiters).getTokens();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Expression: " + expression);
            LOG.debug("Words     : " + words);
        }


        //initialize tokenizer for the Expression
        final Tokenizer tokenizer = new Tokenizer(expression);

        boolean matches = false; //start negative :-)
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            if (OrOperator.OPERATOR.equalsIgnoreCase(token)) {
                matches = processOperator(valueStack, operatorStack, words, new OrOperator(this.strict));
            } else if (AndOperator.OPERATOR.equalsIgnoreCase(token)) {
                matches = processOperator(valueStack, operatorStack, words, new AndOperator(this.strict));
            } else if (NotOperator.OPERATOR.equalsIgnoreCase(token)) {
                matches = processOperator(valueStack, operatorStack, words, new NotOperator(this.strict));
            } else if (LeftParenthesisOperator.OPERATOR.equalsIgnoreCase(token)) {
                operatorStack.push(new LeftParenthesisOperator());
            } else if (RIGHT_PARENTHESIS_OPERATOR.equalsIgnoreCase(token)) {
                while (!operatorStack.empty() && !(operatorStack.peek() instanceof LeftParenthesisOperator)) {
                    matches = operatorStack.pop().apply(words, valueStack);
                }
                // Don't forget to remove the left parenthesis from the operator stack
                operatorStack.pop();
            } else {
                valueStack.push(token);
            }
        }
        while (!operatorStack.empty()) {
            matches = operatorStack.pop().apply(words, valueStack);
        }
        return matches;
    }

    private boolean processOperator(final Stack<String> valueStack, final Stack<Operator> operatorStack,
                                    final List<String> words, final Operator thisOp) {
        boolean match = false;
        while (!operatorStack.empty() && operatorStack.peek().hasGreaterPrecedenceThen(thisOp)) {
            match = operatorStack.pop().apply(words, valueStack);
        }
        operatorStack.push(thisOp);
        return match;
    }
}
