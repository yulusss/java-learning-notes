## 2026-03-08
### 一、多表查询基础

#### 1.1 笛卡尔积（Cartesian Product）

当查询多个表且没有指定连接条件时，会产生笛卡尔积——即第一个表的每一行与第二个表的每一行组合。例如：

```sql
SELECT * FROM employee, department;   -- 返回 employee 行数 * department 行数
```



通常应避免无条件的笛卡尔积，因为它会产生大量无意义的数据。

#### 1.2 连接条件

通过连接条件（`ON` 子句）指定表之间的关联关系，从而过滤掉无意义的组合，只保留满足条件的数据。

------

### 二、连接类型详解

MySQL 支持以下主要连接类型：

| 连接类型       | 说明                                           |
| :------------- | :--------------------------------------------- |
| **INNER JOIN** | 返回两个表中满足连接条件的行（交集）           |
| **LEFT JOIN**  | 返回左表全部行 + 右表匹配行（无匹配则补 NULL） |
| **RIGHT JOIN** | 返回右表全部行 + 左表匹配行（无匹配则补 NULL） |
| **CROSS JOIN** | 返回笛卡尔积，与无条件的多表查询相同           |
| **SELF JOIN**  | 表与自身连接，需使用表别名                     |

#### 2.1 内连接（INNER JOIN）

- **语法**：`表1 INNER JOIN 表2 ON 连接条件`

- **返回**：只返回两表中连接条件匹配的行。

- **示例**：查询员工姓名及其所在部门名称

  ```sql
  SELECT e.name, d.dept_name
  FROM employee e
  INNER JOIN department d ON e.dept_id = d.id;
  ```

  

  `INNER` 可省略，直接写 `JOIN` 默认为内连接。

#### 2.2 左外连接（LEFT JOIN）

- **语法**：`表1 LEFT JOIN 表2 ON 连接条件`

- **返回**：返回左表（表1）的所有行，即使右表（表2）没有匹配行，右表列用 NULL 填充。

- **示例**：查询所有员工及其部门名称（包括未分配部门的员工）

  ```sql
  SELECT e.name, d.dept_name
  FROM employee e
  LEFT JOIN department d ON e.dept_id = d.id;
  ```

  

#### 2.3 右外连接（RIGHT JOIN）

- **语法**：`表1 RIGHT JOIN 表2 ON 连接条件`

- **返回**：返回右表（表2）的所有行，左表无匹配则补 NULL。

- **示例**：查询所有部门及其员工（包括空部门）

  ```sql
  SELECT e.name, d.dept_name
  FROM employee e
  RIGHT JOIN department d ON e.dept_id = d.id;
  ```

  

  通常可用 `LEFT JOIN` 调换表顺序实现相同效果，因此 `RIGHT JOIN` 较少使用。

#### 2.4 交叉连接（CROSS JOIN）

- **语法**：`表1 CROSS JOIN 表2` 或 `FROM 表1, 表2`

- **返回**：笛卡尔积，一般配合 WHERE 条件使用（相当于有条件的连接）。

- **示例**：

  ```sql
  SELECT * FROM employee CROSS JOIN department;   -- 无条件的笛卡尔积
  SELECT * FROM employee, department WHERE employee.dept_id = department.id;  -- 相当于内连接
  ```

  

#### 2.5 自连接（SELF JOIN）

- **概念**：同一张表通过别名视为两张不同的表，然后进行连接。

- **典型场景**：员工-经理关系（经理也是员工）。

- **示例**：查询员工及其直属经理姓名

  ```sql
  SELECT e.name AS employee, m.name AS manager
  FROM employee e
  LEFT JOIN employee m ON e.manager_id = m.id;
  ```

  

------

### 三、多表查询语法与示例

#### 3.1 标准 ANSI SQL 语法（推荐）

```sql
SELECT 列名
FROM 表1
连接类型 JOIN 表2 ON 连接条件
[连接类型 JOIN 表3 ON 连接条件 ...]
[WHERE 过滤条件]
[GROUP BY ...]
[HAVING ...]
[ORDER BY ...]
[LIMIT ...];
```



#### 3.2 示例：三表连接

假设有 `orders`（订单）、`customers`（客户）、`products`（商品）三张表，查询订单详情（包含客户名和商品名）：

```sql
SELECT o.order_id, c.cust_name, p.prod_name, o.quantity
FROM orders o
INNER JOIN customers c ON o.cust_id = c.id
INNER JOIN products p ON o.prod_id = p.id;
```



------

### 四、连接条件 vs 过滤条件（ON 与 WHERE）

- **ON**：指定连接条件，用于决定如何匹配两表。
- **WHERE**：在连接生成的结果集上进一步过滤。

**区别示例**：

```sql
-- LEFT JOIN 时，ON 条件在连接时起作用，WHERE 条件在连接后过滤
SELECT * FROM employee e
LEFT JOIN department d ON e.dept_id = d.id
WHERE d.name = '技术部';
```



此查询会先左连接（保留所有员工），然后只保留部门名为“技术部”的行，导致其他员工被过滤掉，效果等同于内连接。如果想保留所有员工，但只显示技术部的部门信息，应将部门条件放在 ON 子句中：

```sql
SELECT * FROM employee e
LEFT JOIN department d ON e.dept_id = d.id AND d.name = '技术部';
```



这样非技术部的员工仍然显示，但部门列为 NULL。

------

### 五、多张表连接（超过2张）

连接多张表时，MySQL 按照 JOIN 顺序依次处理。优化器会自行选择最佳执行计划，但书写时需确保连接条件清晰。

**示例**：四表连接（用户-订单-订单明细-商品）

```sql
SELECT u.name, o.order_no, p.prod_name, od.quantity
FROM users u
JOIN orders o ON u.id = o.user_id
JOIN order_details od ON o.id = od.order_id
JOIN products p ON od.prod_id = p.id;
```



------

### 六、联合查询（UNION 与 UNION ALL）

`UNION` 用于合并多个 `SELECT` 的结果集，要求每个 `SELECT` 的列数相同且数据类型兼容。

- **UNION**：去重合并。
- **UNION ALL**：直接合并（包含重复行，性能更好）。

**示例**：查询所有员工和经理的联系方式（假设员工和经理在不同表）

```sql
SELECT name, phone FROM employee
UNION
SELECT name, phone FROM manager;
```



------

### 七、多表查询优化建议

1. **为连接字段建立索引**：`ON` 条件中的字段（尤其是外键）应创建索引，大幅提升连接速度。
2. **尽量使用 INNER JOIN**：外连接通常比内连接复杂，能不用尽量不用。
3. **减少连接表的数量**：不必要的连接会增加复杂度，可考虑反范式化或汇总表。
4. **使用 EXPLAIN 分析**：检查连接顺序、索引使用情况，避免全表扫描。
5. **避免在 ON 子句中使用函数或计算**：会导致索引失效。
6. **小表驱动大表**：优化器通常自动选择，但也可通过 `STRAIGHT_JOIN` 强制指定连接顺序（需谨慎）。

------

### 八、总结

- **内连接**：取交集，最常用。
- **外连接**：保留一侧全部数据，常用于包含 NULL 的查询。
- **自连接**：处理层级结构。
- **交叉连接**：慎用，除非必要。
- **连接条件**：明确表间关系，避免笛卡尔积。
- **ON vs WHERE**：理解执行顺序，合理放置条件。
- **性能**：索引是关键，避免过度连接。

```sql
SELECT ename , sal , dname , e.deptno 
FROM emp e 
JOIN dept d 
ON e.deptno = d.deptno 
ORDER BY e.deptno , e.sal;

SELECT dname , ename , sal , emp.deptno 
FROM emp , dept 
WHERE emp.deptno = dept.deptno 
AND dept.deptno = 10;

SELECT * FROM salgrade;

SELECT ename , sal , grade 
FROM emp , salgrade 
WHERE sal BETWEEN losal AND hisal
ORDER BY grade;


SELECT worker.ename AS worker_name ,
 worker.sal AS worker_sal , 
 manager.ename AS manager_name , 
 manager.sal AS manager_sal 
		FROM emp worker 
		JOIN emp manager
		ON worker.mgr = manager.empno
		ORDER BY worker_sal , manager_sal;

SELECT * FROM emp WHERE deptno = 
(SELECT deptno 
FROM emp 
WHERE ename = 'SMITH');

SELECT DISTINCT job 
		FROM emp 
		WHERE deptno = 10;
		
SELECT ename , job , sal , deptno  
		FROM emp 	
		WHERE job IN 
		(SELECT DISTINCT job 
		FROM emp 
		WHERE deptno = 10) AND deptno != 10;

SELECT * 
		FROM emp e1
		WHERE e1.sal > (SELECT AVG(e2.sal) FROM emp e2 WHERE e2.deptno = e1.deptno
		) ORDER BY deptno;


SELECT e.* FROM emp e JOIN (
		SELECT deptno , MAX(sal) AS max_sal FROM emp GROUP BY deptno ) t 
		ON t.deptno = e.deptno AND e.sal = t.max_sal;
```
