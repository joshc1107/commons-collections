/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//collections/src/test/org/apache/commons/collections/TestListUtils.java,v 1.7 2003/04/04 22:22:28 scolebourne Exp $
 * $Revision: 1.7 $
 * $Date: 2003/04/04 22:22:28 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Test;

/**
 * Tests for ListUtils.
 * 
 * @author Stephen Colebourne
 * @author Neil O'Toole
 * @author Matthew Hawthorne
 */
public class TestListUtils extends BulkTest {

    public TestListUtils(String name) {
        super(name);
    }

    public static Test suite() {
        return BulkTest.makeSuite(TestListUtils.class);
    }

    public void testNothing() {
    }

    public BulkTest bulkTestPredicatedList() {
        return new TestPredicatedCollection("") {

            public Collection predicatedCollection() {
                Predicate p = getPredicate();
                return ListUtils.predicatedList(new ArrayList(), p);
            }

            public BulkTest bulkTestAll() {
                return new TestList("") {
                    public List makeEmptyList() {
                        return (List)predicatedCollection();
                    }

                    public Object[] getFullElements() {
                        return getFullNonNullStringElements();
                    }

                    public Object[] getOtherElements() {
                        return getOtherNonNullStringElements();
                    }

                };
            }
        };
    }

    public BulkTest bulkTestTypedList() {
        return new TestTypedCollection("") {

            public Collection typedCollection() {
                Class type = getType();
                return ListUtils.typedList(new ArrayList(), type);
            }

            public BulkTest bulkTestAll() {
                return new TestList("") {
                    public List makeEmptyList() {
                        return (List)typedCollection();
                    }

                    public Object[] getFullElements() {
                        return getFullNonNullStringElements();
                    }

                    public Object[] getOtherElements() {
                        return getOtherNonNullStringElements();
                    }

                };
            }
        };
    }


    public void testLazyList() {
        List list = ListUtils.lazyList(new ArrayList(), new Factory() {

            private int index;

            public Object create() {
                index++;
                return new Integer(index);
            }
        });

        Integer I = (Integer)list.get(5);
        assertEquals(6, list.size());

        I = (Integer)list.get(5);
        assertEquals(6, list.size());
    }

	public void testEquals() {
		Collection data = Arrays.asList( new String[] { "a", "b", "c" });
		
		List a = new ArrayList( data );
		List b = new ArrayList( data );
		
        assertEquals(true, a.equals(b));
        assertEquals(true, ListUtils.equals(a, b));
        a.clear();
        assertEquals(false, ListUtils.equals(a, b));
        assertEquals(false, ListUtils.equals(a, null));
        assertEquals(false, ListUtils.equals(null, b));
        assertEquals(true, ListUtils.equals(null, null));
	}
	
	public void testHashCode() {
		Collection data = Arrays.asList( new String[] { "a", "b", "c" });
			
		List a = new ArrayList( data );
		List b = new ArrayList( data );
		
        assertEquals(true, a.hashCode() == b.hashCode());
        assertEquals(true, a.hashCode() == ListUtils.hashCode(a));
        assertEquals(true, b.hashCode() == ListUtils.hashCode(b));
        assertEquals(true, ListUtils.hashCode(a) == ListUtils.hashCode(b));
        a.clear();
        assertEquals(false, ListUtils.hashCode(a) == ListUtils.hashCode(b));
        assertEquals(0, ListUtils.hashCode(null));
	}	
	
}
