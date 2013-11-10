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
import java.util.EmptyStackException;

/**
 * A non Synchronized Generified Stack implementation. This stack implements a last-in-first-out (LIFO) stack of
 * objects.
 *
 * @author Ivo Woltring
 */
public class Stack<T> extends ArrayList<T> {

    /**
     * Adds an element to the Stack
     *
     * @param token the element to push on the queue
     */
    public final void push(final T token) {
        super.add(token);
    }

    /**
     * Returns the last object pushed to the Stack (LIFO)
     *
     * @return token the token to return
     */
    public final T pop() {
        if (super.size() <= 0) {
            throw new EmptyStackException();
        }
        final T token = super.get(this.size() - 1);
        super.remove(this.size() - 1);
        return token;
    }

    /**
     * Peek from the Stack
     *
     * @return token the look ahead token
     */
    public final T peek() {
        if (super.size() <= 0) {
            throw new EmptyStackException();
        }
        return super.get(this.size() - 1);
    }

    /**
     * Returns true if the queue is empty and false if not
     *
     * @return boolean if the stack is empty
     */
    public final boolean empty() {
        return super.size() == 0;
    }


}
