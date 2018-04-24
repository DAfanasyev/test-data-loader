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
import de.triology.testdata.loader.testentities.Department
import de.triology.testdata.loader.testentities.User

create Department, "Department1_1", { id = 1; name = "Department1" }
create Department, "Department2_1", { id = 2; name = "Department2" }
create Department, "Department3_1", { id = 3; name = "Department3" }

load Department, 'Department1_2', Department1_1.id
load Department, 'Department2_2', Department2_1.id
load Department, 'Department3_2', Department3_1.id

loadAll Department, { d -> d.name + "_3" }

create User, "User1", { id = 1; department = Department1_2 }
create User, 'User2', { id = 2; department = Department2_2 }
create User, 'User3', { id = 3; department = Department3_2 }

create User, "User4", { id = 4; department = Department1_3 }
create User, 'User5', { id = 5; department = Department2_3 }
create User, 'User6', { id = 6; department = Department3_3 }
