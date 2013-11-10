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

import nl.ivonet.logical_expression.interpreter.Strict;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Utility class for finding words in text.
 *
 * @author Ivo Woltring
 */
public class TextUtil {
    private static final Logger LOG = Logger.getLogger(TextUtil.class);
    private static final String FALSE = "false";

    private TextUtil() {
        //private because it is a utility class.
    }

    /**
     * @param requiredWord the word to match
     * @param strict       indicates if strict parsing is required
     * @param words        the list of words to match against
     * @return true if the word exists in the list or the value of the booleanvalue of the requiredWord
     */
    public static boolean hasWord(final List<String> words, final String requiredWord, final Strict strict) {
        switch (strict) {
            case LOOSE:
                return hasWord(words, requiredWord);
            case STRICT:
                return hasWordStrict(words, requiredWord);
            default:
                throw new IllegalArgumentException("This part if the code should never be reached.");

        }
    }

    private static boolean hasWordStrict(final List<String> words, final String requiredWord) {
        if (Boolean.valueOf(requiredWord)) {
            return true;
        }
        if (FALSE.equals(requiredWord)) {
            return false;
        }
        for (int i = 0; i < words.size(); i++) {
            final String word = words.get(i);
            if (word.equalsIgnoreCase(requiredWord)) {
                words.remove(i);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strict parsing removed word [" + word + "] left are now " + words);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean hasWord(final List<String> words, final String requiredWord) {
        if (Boolean.valueOf(requiredWord)) {
            return true;
        }
        if (FALSE.equals(requiredWord)) {
            return false;
        }

        for (final String word : words) {
            if (word.equalsIgnoreCase(requiredWord)) {
                return true;
            }
        }
        return false;
    }
}
