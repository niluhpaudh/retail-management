-- public.mst_privilege definition

-- Drop table

-- DROP TABLE public.mst_privilege;

CREATE TABLE public.mst_privilege (
	privilege_id varchar(50) NOT NULL,
	description varchar(255) NULL,

	CONSTRAINT mst_privilege_pkey PRIMARY KEY (privilege_id)
);


-- public.mst_role definition

-- Drop table

-- DROP TABLE public.mst_role;

CREATE TABLE public.mst_role (
	role_id varchar(50) NOT NULL,
	role_name varchar(50) NOT NULL,
	created_by varchar(15) NOT NULL,
	created_date timestamp NOT NULL,
	updated_by varchar(15) NULL,
	updated_date timestamp NULL,
	CONSTRAINT mst_role_pkey PRIMARY KEY (role_id)
);


-- public.mst_user definition

-- Drop table

-- DROP TABLE public.mst_user;

CREATE TABLE public.mst_user (
	user_id varchar(15) NOT NULL,
	user_name varchar(20) NOT NULL,
    "name" varchar(150) NOT NULL,
	"password" varchar(255) NOT NULL,
	status varchar(255) NULL,
	created_by varchar(15) NOT NULL,
	created_date timestamp NOT NULL,
	updated_by varchar(15) NULL,
	updated_date timestamp NULL,
	login_failed int4 NULL,
	login_status bool NULL DEFAULT false,
	CONSTRAINT mst_user_pkey PRIMARY KEY (user_id)
);


-- public.mst_role_privilege definition

-- Drop table

-- DROP TABLE public.mst_role_privilege;

CREATE TABLE public.mst_role_privilege (
	privilege_id varchar(255) NOT NULL,
	role_id varchar(255) NOT NULL,
	CONSTRAINT mst_role_privilege_pkey PRIMARY KEY (privilege_id, role_id),
	CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES public.mst_role(role_id),
	CONSTRAINT fk_privilege FOREIGN KEY (privilege_id) REFERENCES public.mst_privilege(privilege_id)
);


-- public.mst_user_role definition

-- Drop table

-- DROP TABLE public.mst_user_role;

CREATE TABLE public.mst_user_role (
	role_id varchar(50) NOT NULL,
	user_id varchar(15) NOT NULL,
	CONSTRAINT mst_user_role_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.mst_user(user_id),
	CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES public.mst_role(role_id)
);


-- public.mst_product definition

-- Drop table

-- DROP TABLE public.mst_product;

CREATE TABLE public.mst_product (
	product_id varchar(30) NOT NULL,
	product_name varchar(100) NULL,
	stock int4 NULL,

	CONSTRAINT mst_product_pkey PRIMARY KEY (product_id)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- public.transaction_log definition

-- Drop table

-- DROP TABLE public.transaction_log;

CREATE TABLE public.transaction_log (
    tx_id UUID NOT NULL DEFAULT uuid_generate_v4(),
    transaction_date TIMESTAMP NULL,
    product_id VARCHAR(20) NULL,
    type_trx VARCHAR(128) NULL,
    amount INT NULL,

    CONSTRAINT transaction_log_pkey PRIMARY KEY (tx_id)
);

ALTER TABLE public.mst_user_role 
ADD CONSTRAINT FK_foreign_key_name
FOREIGN KEY (role_id)
REFERENCES mst_role (role_id);



INSERT INTO public.mst_privilege (privilege_id, description) VALUES('AUTHENTIFICATION', 'login, logout');
INSERT INTO public.mst_privilege (privilege_id, description) VALUES('REGISTER', 'registrasi user');
INSERT INTO public.mst_privilege (privilege_id, description) VALUES('TRANSACTION', 'transaksi');
INSERT INTO public.mst_privilege (privilege_id, description) VALUES('REPORT', 'laporan transaksi');
INSERT INTO public.mst_privilege (privilege_id, description) VALUES('USER_CONFIG', 'konfigurasi user');
INSERT INTO public.mst_privilege (privilege_id, description) VALUES('PRODUCT_CONFIG', 'konfigurasi produk');

