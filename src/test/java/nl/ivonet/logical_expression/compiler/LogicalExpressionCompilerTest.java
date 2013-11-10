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

import nl.ivonet.logical_expression.util.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
public final class LogicalExpressionCompilerTest {
    private static final String DELIM = " \"._/()-";

    @Test
    public void testParse() {
//        LogicalExpressionCompiler compiler = new LogicalExpressionCompiler();
        final CompiledExpression compiledExpression = LogicalExpressionCompiler.parse("Chuck & (720p & HDTV) & s02e18");
        assertTrue("Parse: Should eval to true", compiledExpression
                .matches(new Tokenizer("Chuck.S02E18.720p.HDTV.x264-CTU", DELIM).getTokens()));
    }

    @Test
    public void testNot() {
        assertTrue("Evaluate: Should eval to true", new LogicalExpressionCompiler()
                .evaluate("Chuck ! House", "Chuck.S02E18.720p.HDTV.x264-CTU"));
    }

    @Test
    public void testOR() {
        assertTrue("Evaluate: Should eval to true", new LogicalExpressionCompiler()
                .evaluate("Chuck | House", "Chuck.S02E18.720p.HDTV.x264-CTU"));
    }

    @Test
    public void testEvaluate() {
        assertTrue("Evaluate: Should eval to true", new LogicalExpressionCompiler()
                .evaluate("Chuck & (720p & HDTV) & s02e18", "Chuck.S02E18.720p.HDTV.x264-CTU"));
    }
}
