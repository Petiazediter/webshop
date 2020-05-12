--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: cart; Type: TABLE; Schema: public; Owner: peti
--

CREATE TABLE public.cart (
    user_id integer,
    product_id integer,
    quantity integer
);


ALTER TABLE public.cart OWNER TO peti;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: peti
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying,
    description character varying,
    department character varying
);


ALTER TABLE public.categories OWNER TO peti;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: peti
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_id_seq OWNER TO peti;

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: peti
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: peti
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    price double precision NOT NULL,
    supplier integer,
    category integer
);


ALTER TABLE public.products OWNER TO peti;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: peti
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO peti;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: peti
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- Name: suppliers; Type: TABLE; Schema: public; Owner: peti
--

CREATE TABLE public.suppliers (
    id integer NOT NULL,
    name character varying,
    description character varying,
    image character varying DEFAULT 'https://4.bp.blogspot.com/-9S0RNpv8Ovw/V8GaOeuykDI/AAAAAAAABaA/MYN8GHVWgH4CQJfPTuQfuaJDIcLgr2oRgCLcB/s1600/GTA%2BSan%2BAndreas%2Bgame.png'::character varying
);


ALTER TABLE public.suppliers OWNER TO peti;

--
-- Name: suppliers_id_seq; Type: SEQUENCE; Schema: public; Owner: peti
--

CREATE SEQUENCE public.suppliers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.suppliers_id_seq OWNER TO peti;

--
-- Name: suppliers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: peti
--

ALTER SEQUENCE public.suppliers_id_seq OWNED BY public.suppliers.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: peti
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying,
    password character varying,
    adminlevel integer DEFAULT 0,
    email character varying
);


ALTER TABLE public.users OWNER TO peti;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: peti
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO peti;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: peti
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: suppliers id; Type: DEFAULT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.suppliers ALTER COLUMN id SET DEFAULT nextval('public.suppliers_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: peti
--

COPY public.cart (user_id, product_id, quantity) FROM stdin;
2	10	1
2	4	2
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: peti
--

COPY public.categories (id, name, description, department) FROM stdin;
1	Tablet	A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.	Hardware
3	Large Meal	Large meal for real fat guys.	Food
2	Salad	Foods from salad.	Salad
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: peti
--

COPY public.products (id, name, description, price, supplier, category) FROM stdin;
4	Moo Kids Meal	Its a cheeseburger but smaller than usual.	2	2	1
6	Meat Stack	Its a hamburger with double meat.	10	2	1
1	Huge	Huge pizza with lot of meat.	2	1	1
2	Double Deluxe	If you love cheese you will love double deluxe	5	1	1
3	Full size	Its a 32cm pizza with a lot of cheese and meat.	10	1	1
8	Beef Tower	Eat this hamburger if dont care about your health.	6	2	1
10	Salad Meal	Its salad.	6	2	2
\.


--
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: peti
--

COPY public.suppliers (id, name, description, image) FROM stdin;
1	The Well Stacked Pizza Co.	The best pizza in the whole world	https://files.prineside.com/gtasa_samp_game_texture//png/gb_kitchtake.GB_takeaway01.png
2	Burger Shot	The best burgers in the town.	https://files.prineside.com/gtasa_samp_game_texture//png/gb_kitchtake.CJ_BS_MENU4.png
3	Cluckin Bell	Everything from chicken	https://i.pinimg.com/originals/0a/d6/e2/0ad6e2f8637a437ffcbb089b3a50e59c.jpg
4	Jims Sticky Ring	Sweet food for fatties.	https://lh3.googleusercontent.com/proxy/yPtbcDtsRjDa2m2wLNX0UYYo3ADckIz-JJMrNGZ3M65cyJhIB7A5ZXuir6zayicx-u5AIWpbMIP5KxLSxqEJm2W7jtg
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: peti
--

COPY public.users (id, username, password, adminlevel, email) FROM stdin;
1	null	7815696ecbf1c96e6894b779456d330e	0	\N
2	verebes122	ba3f5d84f6691f1b14a67223d58fa982	0	pverebes74@gmail.com
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: peti
--

SELECT pg_catalog.setval('public.categories_id_seq', 3, true);


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: peti
--

SELECT pg_catalog.setval('public.products_id_seq', 10, true);


--
-- Name: suppliers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: peti
--

SELECT pg_catalog.setval('public.suppliers_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: peti
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- Name: categories categories_pk; Type: CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pk PRIMARY KEY (id);


--
-- Name: products products_pk; Type: CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pk PRIMARY KEY (id);


--
-- Name: suppliers suppliers_pk; Type: CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: categories_id_uindex; Type: INDEX; Schema: public; Owner: peti
--

CREATE UNIQUE INDEX categories_id_uindex ON public.categories USING btree (id);


--
-- Name: products_id_uindex; Type: INDEX; Schema: public; Owner: peti
--

CREATE UNIQUE INDEX products_id_uindex ON public.products USING btree (id);


--
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: peti
--

CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);


--
-- Name: cart cart_products_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_products_id_fk FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- Name: cart cart_users_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: products products_categories_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_categories_id_fk FOREIGN KEY (category) REFERENCES public.categories(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: products products_suppliers_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: peti
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_suppliers_id_fk FOREIGN KEY (supplier) REFERENCES public.suppliers(id);


--
-- PostgreSQL database dump complete
--

