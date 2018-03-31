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

### DB schema
1. always recommended to generate the schema manually, preferably by a tool supporting database schema revisions.
2. productions: best to have manually generated and review the schema [stackoverflow](https://stackoverflow.com/questions/2585641/hibernate-jpa-db-schema-generation-best-practices)


### [Database Initialization](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html)
- [Database Migrations tool: flyway](https://github.com/flyway/flyway)

### PostgreSQL

#### table naming:
- [Postgre SQL Syntax](https://www.postgresql.org/docs/10/static/sql-syntax-lexical.html#SQL-SYNTAX-IDENTIFIERS)
    - Key words and unquoted identifiers are `case insensitive`
- use plural nouns for table names
- use lower case
- use underscores
- `CREATE TABLE my_first_table`, `CREATE TABLE products` : [Postgre SQL doc](https://www.postgresql.org/docs/10/static/ddl-basics.html)

#### [Security Database Schema](https://docs.spring.io/autorepo/docs/spring-security/current/reference/html/appendix-schema.html)

```sql
create table acl_sid(
	id bigserial not null primary key,
	principal boolean not null,
	sid varchar(100) not null,
	constraint unique_uk_1 unique(sid,principal)
);

create table acl_class(
	id bigserial not null primary key,
	class varchar(100) not null,
	constraint unique_uk_2 unique(class)
);

create table acl_object_identity(
	id bigserial primary key,
	object_id_class bigint not null,
	object_id_identity varchar(36) not null,
	parent_object bigint,
	owner_sid bigint,
	entries_inheriting boolean not null,
	constraint unique_uk_3 unique(object_id_class,object_id_identity),
	constraint foreign_fk_1 foreign key(parent_object)references acl_object_identity(id),
	constraint foreign_fk_2 foreign key(object_id_class)references acl_class(id),
	constraint foreign_fk_3 foreign key(owner_sid)references acl_sid(id)
);

create table acl_entry(
	id bigserial primary key,
	acl_object_identity bigint not null,
	ace_order int not null,
	sid bigint not null,
	mask integer not null,
	granting boolean not null,
	audit_success boolean not null,
	audit_failure boolean not null,
	constraint unique_uk_4 unique(acl_object_identity,ace_order),
	constraint foreign_fk_4 foreign key(acl_object_identity) references acl_object_identity(id),
	constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);
```

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