# Kotlin + Spring boot + postgre SQL

- Kotlin 1.2.30
- gradle 4.6
- Spring boot 2.0
    - DevTools: Spring Boot Development Tools  
    - Web: Full-stack web development with Tomcat and Spring MVC
    - JPA: Java Persistence API including spring-data-jpa, spring-orm and Hibernate
    - PostgreSQL: PostgreSQL jdbc driver
    
###layers:
#### controller
#### service
- serviceImpl
#### repository / repositoryCustomer
- repositoryCustomerImpl
#### models
- apiModels
- entities
   
## Database   
### PostgreSQL

#### table naming:
- [Postgre SQL Syntax](https://www.postgresql.org/docs/10/static/sql-syntax-lexical.html#SQL-SYNTAX-IDENTIFIERS)
    - Key words and unquoted identifiers are `case insensitive`
- use plural nouns for table names
- use lower case
- use underscores
- `CREATE TABLE my_first_table`, `CREATE TABLE products` : [Postgre SQL doc](https://www.postgresql.org/docs/10/static/ddl-basics.html)



log in as postgres user:
```bash
psql -U postgres
```
Some flags (to see all, use `-h`):
- `-E`: will describe the under laying queries of the `\` commands

Most `\d` commands support additional param of `__schema__.name__` and accept wildcards like `*.*`

- `\q`: Quit/Exit
- `\c __database__`: Connect to a database
- `\d __table__`: Show table definition including triggers
- `\dt *.*`: List tables from all schemas (if `*.*` is omitted will only show SEARCH_PATH ones)
- `\l`: List databases
- `\dn`: List schemas
- `\df`: List functions
- `\dv`: List views
- `\di`: List indexes
- `\df+ __function__` : Show function SQL code. 
- `\x`: Pretty-format query results instead of the not-so-useful ASCII tables
- `\copy (SELECT * FROM __table_name__) TO 'file_path_and_name.csv' WITH CSV`: Export a table as CSV


mac install via `Brew`

1. `brew doctor`
2. `brew update`

### Install PostgreSQL

`brew install postgresql`

### Run PostgreSQL

To migrate existing data from a previous major version of PostgreSQL run:

```$xslt
  brew postgresql-upgrade-database
```  

#### To have launchd `start` postgresql:
  
```$xslt
  brew services start postgresql
```  

#### `stop` postgresql:

```$xslt
brew services stop postgresql
```

Or, if you don't want/need a background service you can just run:

```$xslt
  pg_ctl -D /usr/local/var/postgres start
```

##### 1. log in as postgres user 
- `psql postgres`

##### 2. all users in db: 
- `postgres=# \du`

##### 3. create user with password 
- `CREATE ROLE junjun WITH LOGIN PASSWORD 'junjun';`

##### 4. add permissions 
- `ALTER ROLE junjun CREATEDB;`

##### 5. create database 
- `CREATE DATABASE test_db;`

##### 6. grant all privileges:
- `GRANT ALL PRIVILEGES ON DATABASE test_db TO junjun;`