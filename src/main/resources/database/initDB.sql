CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL,
    person_id bigint NOT NULL,
    login character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(1000) COLLATE pg_catalog."default",
    roles character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS public.person_users
(
    person_user_id bigint NOT NULL,
    person_id bigint NOT NULL,
    role_id bigint,
    user_name character varying(50) COLLATE pg_catalog."default",
    user_id bigint,
    active integer NOT NULL DEFAULT 1,
    CONSTRAINT person_users_pkey PRIMARY KEY (person_user_id),
    CONSTRAINT person_users_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.d_roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.d_roles
(
    role_id bigint NOT NULL,
    role_code character varying(100) COLLATE pg_catalog."default" NOT NULL,
    role_name character varying(500) COLLATE pg_catalog."default",
    active integer NOT NULL DEFAULT 1,
    CONSTRAINT d_roles_pkey PRIMARY KEY (role_id)
);
