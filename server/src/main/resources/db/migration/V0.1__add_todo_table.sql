-- Table: public.todo

-- DROP TABLE IF EXISTS public.todo;

CREATE TABLE IF NOT EXISTS public.todo
(
    id integer NOT NULL,
    description text NOT NULL,
    done boolean NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.todo
    OWNER to postgres;

INSERT INTO public.todo(id, description, done) VALUES (1, 'buy the paper', true);
INSERT INTO public.todo(id, description, done) VALUES (2, 'find sample of picture', false);
INSERT INTO public.todo(id, description, done) VALUES (3, 'draw picture', false);
