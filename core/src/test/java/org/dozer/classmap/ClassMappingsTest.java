/*
 * Copyright 2005-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer.classmap;

import org.dozer.AbstractDozerTest;
import org.dozer.MappingException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dmitry.buzdin
 */
public class ClassMappingsTest extends AbstractDozerTest{

  private ClassMappings classMappings;

  @Before
  public void setUp() {
    classMappings = new ClassMappings();
  }

  @Test
  public void testFind_Null() {
    assertNull(classMappings.find(String.class, Integer.class));
  }

  @Test
  public void testFind_Simple() {
    classMappings.add(String.class, Integer.class, mock(ClassMap.class));
    ClassMap result = classMappings.find(String.class, Integer.class);
    assertNotNull(result);
  }

  @Test
  public void testFind_Nested() {
    classMappings.add(NestedClass.class, String.class, mock(ClassMap.class));
    ClassMap result = classMappings.find(NestedClass.class, String.class);
    assertNotNull(result);
  }

  @Test(expected = MappingException.class)
  public void testNotFoundByMapid() {
    classMappings.find(NestedClass.class, String.class, "A");
  }

  @Test
  public void shouldAdd() {
    classMappings.add(String.class, Integer.class, mock(ClassMap.class));
    classMappings.add(String.class, Integer.class, "id", mock(ClassMap.class));

    Map<String,ClassMap> result = classMappings.getAll();
    assertEquals(2, result.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailOnDuplicate() {
    classMappings.add(String.class, Integer.class, mock(ClassMap.class));
    classMappings.add(String.class, Integer.class, mock(ClassMap.class));
  }

  public static class NestedClass {

  }

}
