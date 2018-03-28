# Kotlin + Spring boot + SQL

- Kotlin 1.2.30
- gradle 4.6
- Spring boot 2.0
    - DevTools: Spring Boot Development Tools  
    - Web: Full-stack web development with Tomcat and Spring MVC
    - JPA: Java Persistence API including spring-data-jpa, spring-orm and Hibernate
    - PostgreSQL: PostgreSQL jdbc driver
    
###layers:
- controller
- service
    - serviceImpl
- dao
    - daoImpl   
- models
    - apiModels
    - entities
    
## PostgreSQL

### basic:
- `\?` list all the commands
- `\l` list databases
- `\conninfo` display information about current connection
- `\c [DBNAME]` connect to new database, e.g., \c template1
- `\dt` list tables
- `\q` quit psql


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

```sql
postgres=# CREATE ROLE junjun WITH LOGIN PASSWORD 'junjun';
CREATE ROLE
postgres=# \du
                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 Junjun    | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
 junjun    |                                                            | {}
```

##### 4. add permissions 
- `ALTER ROLE junjun CREATEDB;`

```sql
postgres=# ALTER ROLE junjun CREATEDB;
ALTER ROLE
postgres=# \du
                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 Junjun    | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
 junjun    | Create DB                                                  | {}
```

##### 5. create database 
- `CREATE DATABASE test_db;`

```sql
postgres=# CREATE DATABASE test_db;
CREATE DATABASE
postgres=# \du
                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 Junjun    | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
 junjun    | Create DB                                                  | {}

postgres=# \list
                               List of databases
   Name    | Owner  | Encoding |   Collate   |    Ctype    | Access privileges 
-----------+--------+----------+-------------+-------------+-------------------
 postgres  | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
 template0 | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/Junjun        +
           |        |          |             |             | Junjun=CTc/Junjun
 template1 | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/Junjun        +
           |        |          |             |             | Junjun=CTc/Junjun
 test_db   | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
(4 rows)
```

##### 6. grant all privileges:
- `GRANT ALL PRIVILEGES ON DATABASE test_db TO junjun;`

```sql
postgres=# GRANT ALL PRIVILEGES ON DATABASE test_db TO junjun;
GRANT
postgres=# \list
                               List of databases
   Name    | Owner  | Encoding |   Collate   |    Ctype    | Access privileges 
-----------+--------+----------+-------------+-------------+-------------------
 postgres  | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
 template0 | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/Junjun        +
           |        |          |             |             | Junjun=CTc/Junjun
 template1 | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/Junjun        +
           |        |          |             |             | Junjun=CTc/Junjun
 test_db   | Junjun | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =Tc/Junjun       +
           |        |          |             |             | Junjun=CTc/Junjun+
           |        |          |             |             | junjun=CTc/Junjun
(4 rows)
```