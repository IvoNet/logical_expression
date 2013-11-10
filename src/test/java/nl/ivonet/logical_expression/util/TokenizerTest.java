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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivo Woltring
 * @since 2009-03-29 21:39
 */
public final class TokenizerTest {
    private static final String SENTENCE = "\"SAMPLE_Street.Fighter.IV.The.Ties.That.Bind.2009.1080p.BluRay.DTS"
                                           + ".x264-PerfectionHD.mkv\" yEnc (1/231)";
    private static final String SENTENCE_PARSED = "[SAMPLE, Street, Fighter, IV, The, Ties, That, Bind, 2009, 1080p, "
                                                  + "BluRay, DTS, x264-PerfectionHD, mkv, yEnc, 1, 231]";

    private static final String EXPRESSION = "house and not (desparate or wives)";
    private static final String EXPRESSION_PARSED = "[house, and, not, (, desparate, or, wives, )]";

    @Test
    public void testGetTokens() {
        final Tokenizer tokenizer = new Tokenizer(SENTENCE, " \"._/()");
        Assert.assertEquals(SENTENCE_PARSED, tokenizer.getTokens().toString());
//        System.out.println("tokenizer.getTokens() = " + tokenizer.getTokens());

    }

    @Test
//
    public void getTokensWithDelims() {
        final Tokenizer tokenizer = new Tokenizer(EXPRESSION);
        Assert.assertEquals(EXPRESSION_PARSED, tokenizer.getTokens().toString());
//        System.out.println("tokenizer.getTokens() = " + tokenizer.getTokens());
    }
}
