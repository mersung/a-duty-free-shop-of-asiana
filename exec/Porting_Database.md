# Database_Porting_Manual

### Environment

- DB: PostgreSQL 15.2
- Os: Ubuntu 18.04



## Install Database

``` bash
# 우분투에 postgreSQL 설치
$ sudo apt-get install postgresql postgresql-contrib

# 버전 확인
$ psql --version

# 자동으로 만들어진 postgre 계정(ubuntu계정)으로 postgre계정 접속
$ sudo -i -u postgres
# -i: Login 옵션
# -u: User옵션

# postgre 계정접속한 상태에서 psql 접속 / 나오는 명령어 \q
$ psql
```



## Database Initialize

``` postgresql
-- 사용자 생성, 삭제 및 확인 (나오는 명령 q)
postgres=# create user {유저명}; 
postgres=# drop user {유저명};

-- 사용자 권한 부여, 해제 및 유저 목록 확인
postgres=# create role {유저명} superuser;
postgres=# drop role {유저명};
postgres=# \du

-- 데이터베이스 생성, 삭제 및 생성된 데이터베이스 목록 확인
postgres=# create database {데이터베이스명}
postgres=# drop database {데이터베이스명}
postgres=# \list

-- 해당 데이터베이스로 접속
postgres=# \c {데이터베이스명}

-- 사용자가 생성한 테이블 이름 조회
postgres=# select * from pg_tables whrer schemaname='public';

-- postgreSQL 빠져나오기
\q
```





## Install pgAdmin4

pgAdmin4를 설치하여 GUI환경을 통해 쉽게 데이터베이스 관리



> PostgreSQL 설치 후, Default값으로 로컬을 제외한 다른 IP에서의 접근을 모두 막고있음.
>
> 따라서 관리자가 여럿이거나 본인과 같이 클라우드로 사용하는 경우  PostgreSQL의 Configuration을 수정하여 외부 IP 접근을 열어주어야 함
>
> ``` bash
> # 방화벽 설정 확인: 열고싶은 포트가 열려있는지 확인
> $ sudo ufw status
> 
> # PostgreSQL 포트 확인 (default: 5432)
> $ sudo netstat -ntlp
> ```
>
> // 결과
>
> ``` bash
> Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
> tcp        0      0 127.0.1.1:53            0.0.0.0:*               LISTEN      -               
> tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      -               
> tcp        0      0 127.0.0.1:5432          0.0.0.0:*               LISTEN      -               
> tcp6       0      0 :::22                   :::*                    LISTEN      -
> ```
>
> -- local Address 필드가 127.0.0.1:5432일 경우 5432포트는 내부에서만 접속할 수 있다는 뜻
>
> 1. postgresql.conf 수정
>
>    ``` bash
>    $ sudo vim/etc/postgresql/15/main/postgresql.conf
>    ```
>
>    - listen_address를 "*"로 수정
>
>    ``` conf
>    생략...
>    # - Connection Settings -
>    
>    listen_addresses = '*'                  # what IP address(es) to listen on;
>                                            # comma-separated list of addresses;
>                                            # defaults to 'localhost'; use '*' for all
>                                            # (change requires restart)
>    port = 5432                             # (change requires restart)
>    max_connections = 100                   # (change requires restart)
>    #superuser_reserved_connections = 3     # (change requires restart)
>    unix_socket_directories = '/var/run/postgresql' # comma-separated list of directories
>                                            # (change requires restart)
>    #unix_socket_group = ''                 # (change requires restart)
>    #unix_socket_permissions = 0777         # begin with 0 to use octal notation
>                                            # (change requires restart)
>    #bonjour = off                          # advertise server via Bonjour
>                                            # (change requires restart)
>    #bonjour_name = ''                      # defaults to the computer name
>                                            # (change requires restart)
>    ```
>
>    
>
> 2. ph_hba.conf 파일 설정 변경
>
>    ``` bash
>    $ sudo vim /etc/postgresql/15/main/pg_hba.conf
>    ```
>
>    - 파일 내용 중 IPv4 local connections 부분에서 모든 유저의 계정으로 모든 데이터베이스에 모든 외부 접속을 허용하도록 수정
>
>    ``` conf
>    # DO NOT DISABLE!
>    # If you change this first entry you will need to make sure that the
>    # database superuser can access the database using some other method.
>    # Noninteractive access to all databases is required during automatic
>    # maintenance (custom daily cronjobs, replication, and similar tasks).
>    #
>    # Database administrative login by Unix domain socket
>    local   all             postgres                                peer
>    
>    # TYPE  DATABASE        USER            ADDRESS                 METHOD
>    
>    # "local" is for Unix domain socket connections only
>    local   all             all                                     peer
>    # IPv4 local connections:
>    host    all             all             0.0.0.0/0               md5
>    # IPv6 local connections:
>    host    all             all             ::1/128                 md5
>    # Allow replication connections from localhost, by a user with the
>    # replication privilege.
>    #local   replication     postgres                                peer
>    #host    replication     postgres        127.0.0.1/32            md5
>    #host    replication     postgres        ::1/128                 md5
>    ```
>
>    
>
>    3. postgresql 재시작
>
>       ``` bash
>       $ sudo /etc/init.d/postgresql restart
>       ```
>
>    4. port 확인
>
>       ``` bash
>       $ netstat -ntlp
>       Active Internet connections (only servers)
>       Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
>       tcp        0      0 127.0.0.53:53           0.0.0.0:*               LISTEN      706/systemd-resolve
>       tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      1153/sshd
>       tcp        0      0 0.0.0.0:5432            0.0.0.0:*               LISTEN   
>       
>       
>       ```
>
>       local Address가 모든 IP의 5432포트로 열린것 확인



**Download**: [pgAdmin4](https://www.pgadmin.org/download/pgadmin-4-windows/)

1. 새 데이터베이스 서버 추가

   

2. 서버등록

   ![서버등록1](C:\Users\USER\Desktop\webPJT\team5\exec\Porting_Database.asset\서버등록1.png)

   ![서버등록2](C:\Users\USER\Desktop\webPJT\team5\exec\Porting_Database.asset\서버등록2.png)

   ​		추가하는 서버의 이름 설정

   

   ![서버등록3](C:\Users\USER\Desktop\webPJT\team5\exec\Porting_Database.asset\서버등록3.png)

   ​	  위에서 부터 차례대로  IP주소 / 포트번호 / 데이터베이스 명 / 사용자 명 / 비밀번호 입력

   

3.  서버 추가 완성

   ![서버연결4](C:\Users\USER\Desktop\webPJT\team5\exec\Porting_Database.asset\서버연결4.PNG)

   

4. QueryTool 실행하여 사용할 테이블 추가
5. 테이블 쿼리 실행 후, 데이터베이스 완성 

![서버연결5](C:\Users\USER\Desktop\webPJT\team5\exec\Porting_Database.asset\서버연결5.PNG)
