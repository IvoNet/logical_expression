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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the {@link LogicalExpressionInterpreter} class.
 *
 * @author Ivo Woltring
 */
public final class LogicalExpressionInterpreterTest {
    private static final String DELIMITERS = " \"._/()";

    @Test
    public void testLoose() throws IllegalExpressionException {
        final LogicalExpressionInterpreter interpreter = new LogicalExpressionInterpreter(DELIMITERS, Strict.LOOSE);
        assertFalse("Should fail in loose interpreting", interpreter
                .evaluate("Hello & Hello a &!Hello", "Hello World.s01e01.Hello People"));
    }

    @Test
    public void testStrict() throws IllegalExpressionException {
        final LogicalExpressionInterpreter interpreter = new LogicalExpressionInterpreter(DELIMITERS, Strict.STRICT);
        final String text = "Hello World.s01e01.Hello People";
        assertFalse("Should fail in Strict interpreting", interpreter.evaluate("Hello & Hello&! Hello", text));
        assertTrue("Hello should be accepted twice", interpreter.evaluate("hello & hello", text));
        assertTrue("Hello or people should evaluate to true", interpreter.evaluate("(Hello | People) & hello", text));
    }

    @Test(expected = IllegalExpressionException.class)
    public void testErrorOnAND() {
        final LogicalExpressionInterpreter interpreter = new LogicalExpressionInterpreter(DELIMITERS);
        interpreter.evaluate("&&&", "foo bar");
    }

    @Test(expected = IllegalExpressionException.class)
    public void testErrorOnOR() {
        final LogicalExpressionInterpreter interpreter = new LogicalExpressionInterpreter(DELIMITERS);
        interpreter.evaluate("hello ||", "foo bar");
    }

    @Test(expected = IllegalExpressionException.class)
    public void testErrorOnNOT() {
        final LogicalExpressionInterpreter interpreter = new LogicalExpressionInterpreter(DELIMITERS);
        interpreter.evaluate("!!", "foo bar");
    }
}
