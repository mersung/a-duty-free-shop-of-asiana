# DB CREATE TABLE Query

## users
``` SQL
drop sequence if exists user_seq;
drop table if exists users;
create sequence user_seq;

create table users
(
	id integer not null default nextval('user_seq') primary key,
  	passwd varchar(200) not null,		-- 비밀번호
  	email varchar(50) not null,			-- email
  	post varchar(10),					-- 우편번호
  	addr1 varchar(50),					-- 도로명 or 지번 주소
  	addr2 varchar(50),					-- 상세 주소
  	roles varchar(10),					-- Guest, User, Admin
  	registration_num varchar(20),		-- 주민등록번호
  	registration_id varchar(10)			-- OAuth 유무
);
  

commit;
```





## product

``` SQL
drop sequence if exists product_seq;
create sequence product_seq;
drop table if exists product;

create table product
(
	id integer not null default nextval('product_seq') primary key,
    name varchar(255),		-- 상품명
    content varchar(255),	-- 상품 내용
    category varchar(20),	-- 카테고리 
  	price bigint,				-- 가격
  	stock integer			-- 재고
 );
 
commit;
```



## productImg

``` SQL
create sequence productImg_seq;
drop table if exists productImg;

create table productImg
(
	id integer not null default nextval('productImg_seq') primary key,
  	img_name varchar(255),		-- 프론트에서 받아온 이미지 명
  	img_url varchar(255),		-- 이미지 저장위치
  	origin_name varchar(255),	-- 파일 명
 	product_id integer,			-- 상품 id
    
	foreign key (product_id) references product(id)
 );


-- insert into productImg(product_img_id, img_name, img_url, origin_name, product_id)
-- values (nextval('productImg_seq'), '마일드세븐', 'C:\img\마일드세븐.png', '원본명', 6);
-- insert into productImg(product_img_id, img_name, img_url, origin_name, product_id)
-- values (nextval('productImg_seq'), '나이키티셔츠', 'C:\img\나이키티셔츠.png', '나이키', 3);

commit;
```



### cart

``` SQL
create sequence cart_seq;	
drop table if exists cart;
 
create table cart 
(
    id integer not null default nextval('cart_seq') primary key,
    cnt integer default 1,		-- 상품 개수
    user_id integer,			-- 장바구니 회원
	product_id integer,			-- 상품 id
    
	foreign key (user_id) references users(id),
	foreign key (product_id) references product(id)
);


commit;
```



### orderList

``` SQL
create sequence order_seq;
drop table if exists orderList;

create table orderList
(
	id integer not null default nextval('order_seq') primary key,
  	user_id integer,			-- 주문자
  	product_id integer,			-- 주문 상품
  	order_status varchar(10),	-- 주문 상태
    cnt integer,				-- 주문수량
    dp_nation varchar(20),		-- 출발 국가
    ar_nation varchar(20),		-- 도착 국가
    dp_airport varchar(20),		-- 출발 공항
    ar_airport varchar(20),		-- 도착 공항
    dp_date varchar(20),		-- 출발 날짜
    order_date varchar(20) not null default current_date,	-- 주문날짜
 	order_time varchar(20),
    
  	foreign key (user_id) references users(id),
  	foreign key (product_id) references product(id)
);

commit;
```





### payment

``` SQL
create sequence payment_seq;
drop table if exists payment;

create table payment
(
	id integer not null default nextval('payment_seq') primary key,
  	order_id integer,		-- 주문 정보
  	pay_date varchar(20) not null default current_date,	-- 결제 날짜
    pay_time varchar(20),	-- 결제 시간
    
  	foreign key (order_id) references orderList(id)
);

commit;
```



### review

``` SQL
create sequence review_seq;
drop table if exists review;

create table review
(
	id integer not null default nextval('review_seq') primary key,
	content varchar(255),		-- 리뷰 내용
	user_id integer,			-- 유저 id
	product_id integer,			-- 상품 id
    
	foreign key (user_id) references users(id),
	foreign key (product_id) references product(id)
);

commit;
```



