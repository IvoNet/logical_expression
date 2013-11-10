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

package nl.ivonet.logical_expression.compiler;

import nl.ivonet.logical_expression.ExpressionEvaluator;
import nl.ivonet.logical_expression.interpreter.IllegalExpressionException;
import nl.ivonet.logical_expression.interpreter.Strict;
import nl.ivonet.logical_expression.util.Tokenizer;
import org.apache.log4j.Logger;

import java.util.List;

import static nl.ivonet.logical_expression.util.TextUtil.hasWord;

/**
 * This class takes care of the compilation of a logical (conditional) expression.
 * <p/>
 * Expressions may contain the keywords (reserved words):
 * AND, OR and ! (case insensitive)
 * <br/> Delimiters are: {@code " " (space), "(" and ")"}
 * <p/>
 * The internal Tokenizer class functions as the lexer for this compiler.
 * The lexicon is very straight forward.
 * We need the words and the ")" and "(" tokens.
 * Whitespace can be ignored. Based on these tokens the expression can be compiled.
 * <p/>
 * This class does not need to be instantiated to make it work.<br/> Example: </p>
 * <pre>
 * String expr = &quot;foo or (bar or 42)&quot;;
 * CompiledExpression compiledExpression = LogicalExpressionCompiler.compile(expr);
 * //... when the words list object is available or can call
 * compiledExpression.matches(authenticationObject);
 * //... or if the compiled expression may be discarded after evaluation:
 * LogicalExpressionCompiler.parse(expr).matches(words list);
 * </pre>
 * <p/>
 * Because it is not a complete compiler it assumes that all expressions given to it are correct. This means
 * that the compiler does not check the grammar / syntax of the expression. When e.g. an expression looks like {@code
 * foo and} the compiler will accept this. The moment the matches method is called though a NullPointerException is
 * thrown because there is no "right" side of the expression and an AND expression expects this right side.
 * <p/>
 * NOTE: Because the (@link LogicalExpressionCompiler} pre-compiles the whole expression a possible optimization is
 * possible. Compiled expressions can be saved for the next time they are needed. Compilation of an expression is
 * (relatively) the most expensive part of the process.
 * </p>
 *
 * @author Ivo Woltring
 */
public final class LogicalExpressionCompiler implements ExpressionEvaluator {
    private static final Logger LOG = Logger.getLogger(LogicalExpressionCompiler.class);
    private static final String TEXT_DELIMITERS = " \"._/()-";


    /**
     * This static method compiles the given expression
     *
     * @param expression to be compiled
     * @return CompiledExpression containing the whole expression tree
     */
    public static CompiledExpression parse(final String expression) {
        return new Compiler(new Tokenizer(expression)).parse();
    }

    /**
     * The Compiler class does the real evaluation of the expression. It needs a Tokenizer as input. It builds an
     * expression tree.
     *
     * @author Ivo Woltring
     */
    static final class Compiler {
        private static final String AND_OPERATOR = "&";
        private static final String OR_OPERATOR = "|";
        private static final String NOT_OPERATOR = "!";
        private static final String LPAREN = "(";
        private static final String RPAREN = ")";
        private final Tokenizer tokenizer;

        /**
         * The constructor
         *
         * @param tokenizer (the lexer of this compiler)
         */
        public Compiler(final Tokenizer tokenizer) {
            this.tokenizer = tokenizer;
        }

        /**
         * The compile method is called to compile the expression into an expression tree
         *
         * @return CompiledExpression
         */
        public CompiledExpression parse() {
            return compile(compile(null));
        }

        /**
         * This method builds recursively the expression tree
         *
         * @param left the left side of the expression
         * @return CompiledExpression
         */
        CompiledExpression compile(final CompiledExpression left) {
            final String token = this.tokenizer.nextToken();
            if (token == null) {
                return left;
            }
            abstract class NextExpression implements CompiledExpression {
                final CompiledExpression right;

                NextExpression(final CompiledExpression right) {
                    this.right = right;
                }
            }
            if (token.equalsIgnoreCase(NOT_OPERATOR)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Compiling: [NOT_OPERATOR]");
                }
                return compile(new NextExpression(compile(null)) {
                    @Override
                    public boolean matches(final List<String> words) {
                        final boolean rht = this.right.matches(words);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Evaluated ! [" + rht + "] to [" + (!rht) + "]");
                        }
                        return !rht;
                    }
                });
            }
            if (token.equalsIgnoreCase(OR_OPERATOR)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Compiling: [OR_OPERATOR]");
                }
                return new NextExpression(compile(compile(null))) {
                    @Override
                    public boolean matches(final List<String> words) {
                        final boolean lft = left.matches(words);
                        final boolean rht = this.right.matches(words);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Evaluated [" + lft + "] | [" + rht + "] to [" + (lft || rht) + "]");
                        }
                        return lft || rht;
                    }
                };
            }
            if (token.equalsIgnoreCase(AND_OPERATOR)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Compiling: [AND_OPERATOR]");
                }
                return compile(new NextExpression(compile(null)) {
                    @Override
                    public boolean matches(final List<String> words) {
                        final boolean lft = left.matches(words);
                        final boolean rht = this.right.matches(words);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Evaluated [" + lft + "] & [" + rht + "] to [" + (lft && rht) + "]");
                        }
                        return lft && rht;
                    }
                });
            }
            if (token.equals(LPAREN)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Compiling: [LPAREN]");
                }
                return compile(new NextExpression(compile(null)) {
                    @Override
                    public boolean matches(final List<String> words) {
                        final boolean rht = this.right.matches(words);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Evaluated braced expression to [" + rht + "]");
                        }
                        return this.right.matches(words);
                    }
                });
            }
            if (token.equals(RPAREN)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Compiling: [RPAREN]");
                }
                return left;
            }
            // Atomic part of an expression
            if (LOG.isDebugEnabled()) {
                LOG.debug("Compiling: [" + token + "]");
            }
            return new CompiledExpression() {
                @Override
                public boolean matches(final List<String> words) {
                    return hasWord(words, token, Strict.LOOSE);
                }
            };
        }
    }

    @Override
    public boolean evaluate(final String expr, final String text) {
        final List<String> words = new Tokenizer(text, TEXT_DELIMITERS).getTokens();
        try {
            return parse(expr).matches(words);
        } catch (final Exception e) {
            throw new IllegalExpressionException(
                    "Error occurred during the evaluation of the following expression: [" + expr + "].", e);
        }
    }

}
