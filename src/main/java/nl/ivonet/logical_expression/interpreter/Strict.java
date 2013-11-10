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

/**
 * Enumeration telling the interpreter to be strict in processing or loose.
 * <p/>
 * The strickt processing means that a token if found on the words list will be deleted if found and will therefore not
 * be found again if it is twice+ in the expression.
 * <p/>
 * Loose processing means that a token is never deleted from the words list.
 *
 * @author Ivo Woltring
 */
public enum Strict {
    STRICT,
    LOOSE
}
