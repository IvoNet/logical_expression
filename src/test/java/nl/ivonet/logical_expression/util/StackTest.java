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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 * @since 2009-03-29 22:11
 */
public final class StackTest {
    @Test
    public void testPush() {
        final Stack<String> stack = new Stack<String>();
        assertTrue(stack.empty());
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        assertEquals("[1, 2, 3, 4]", stack.toString());
        assertEquals("4", stack.pop());
        assertFalse(stack.empty());
    }
}
