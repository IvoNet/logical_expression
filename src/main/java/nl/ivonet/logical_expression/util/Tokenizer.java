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

package nl.ivonet.logical_expression.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * It extents the StringTokenizer and instantiates it with the needed delimiters and tells the StringTokenizer to return
 * the delimiters as tokens. We need the delimiters as tokens because otherwise it is not possible to determine which
 * expression belongs to which part in the expression.
 * <p/>
 * This version of nextToken (overridden) returns all relevant tokens. Whitespace is eaten.
 *
 * @author Ivo Woltring
 */
public class Tokenizer extends StringTokenizer {
    private static final boolean DO_RETURN_DELIMS = true;
    private static final boolean DO_NOT_RETURN_DELIMS = false;

    private static final int EMPTY_TOKEN_LENGTH = 0;

    /**
     * Constructor.
     *
     * @param expression   the expression to tokenize
     * @param delim        delimiters for the tokenizer
     * @param returnDelims true if delimiters should be returned
     */
    public Tokenizer(final String expression, final String delim, final boolean returnDelims) {
        super(expression, delim, returnDelims);
    }

    /**
     * Tokenizes a sentence based on given delimiters without returning the delimiters themselves.
     *
     * @param expression the sentence to tokenize
     * @param delim      the delimiters used to tokenize the sentence
     */
    public Tokenizer(final String expression, final String delim) {
        super(expression, delim, DO_NOT_RETURN_DELIMS);
    }

    /**
     * This constructor is used with default delimiters.
     * <p/>
     * Tokens recognized are: space (empty token), LPAREN "(" and RPAREN ")".
     * <p/>
     * Al tokens including delimiters are returned except for spaces.
     *
     * @param expression the sentence to tokenize
     */
    public Tokenizer(final String expression) {
        super(expression, " ()&|!", DO_RETURN_DELIMS);
    }

    /**
     * Overrides the default implementation, becuase it should eat empty tokens (spaces).
     * <p/>
     * {@inheritDoc}
     */
    public final String nextToken() {
        while (super.hasMoreTokens()) {
            final String token = super.nextToken().trim();
            if (token.length() > EMPTY_TOKEN_LENGTH) {
                return token;
            }
        }
        return null;
    }

    /**
     * Get all Tokens.
     *
     * @return List of Strings containing all tokens
     */
    public final List<String> getTokens() {
        final List<String> tokens = new ArrayList<String>();
        while (this.hasMoreTokens()) {
            tokens.add(nextToken());
        }
        return tokens;
    }
}
