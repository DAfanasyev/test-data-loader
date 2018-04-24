/**
 * MIT License
 *
 * Copyright (c) 2016 TRIOLOGY GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.triology.testdata.loader;

import de.triology.testdata.loader.testentities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestLoadEntitiesIT {

    private TestDataLoader testDataLoader;

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("testdataloader");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        testDataLoader = new TestDataLoader(entityManager);
        testDataLoader.loadTestData(Collections.singletonList("tests/testLoadEntities.groovy"));
    }

    @After
    public void tearDown() {
        testDataLoader.clearEntityCacheAndDatabase();
    }

    @Test
    public void testLoadEntities() {
        getAndTestUser("User1", "Department1");
        getAndTestUser("User2", "Department2");
        getAndTestUser("User3", "Department3");
        getAndTestUser("User4", "Department1");
        getAndTestUser("User5", "Department2");
        getAndTestUser("User6", "Department3");
    }

    private void getAndTestUser(String userName, String departmentName) {
        User user = testDataLoader.getEntityByName(userName, User.class);
        assertThat(user, notNullValue());
        assertThat(user.getDepartment(), notNullValue());
        assertThat(user.getDepartment().getName(), is(equalTo(departmentName)));
    }
}
