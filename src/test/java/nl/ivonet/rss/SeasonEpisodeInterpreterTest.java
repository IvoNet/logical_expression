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

package nl.ivonet.rss;

import nl.ivonet.logical_expression.ExpressionEvaluator;
import nl.ivonet.logical_expression.interpreter.IllegalExpressionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
public final class SeasonEpisodeInterpreterTest {
    private ExpressionEvaluator expressionEvaluator;
    private static final String EPISODE_TEXT = "Chuck.S05E10.HDTV";
    private static final String EPISODE_EXPRESSION = "05 & 10";

    @Before
    public void setUp() {
        expressionEvaluator = new SeasonEpisodeInterpreter();
    }

    @Test
    public void testEvaluate() throws IllegalExpressionException {
        assertTrue(expressionEvaluator.evaluate(EPISODE_EXPRESSION, EPISODE_TEXT));

    }
}
