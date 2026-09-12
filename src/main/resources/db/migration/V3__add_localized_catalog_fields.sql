ALTER TABLE public.product
    ADD COLUMN IF NOT EXISTS name_en varchar(255),
    ADD COLUMN IF NOT EXISTS name_ru varchar(255),
    ADD COLUMN IF NOT EXISTS name_zh varchar(255),
    ADD COLUMN IF NOT EXISTS name_es varchar(255),
    ADD COLUMN IF NOT EXISTS name_ka varchar(255),
    ADD COLUMN IF NOT EXISTS description_en varchar(255),
    ADD COLUMN IF NOT EXISTS description_ru varchar(255),
    ADD COLUMN IF NOT EXISTS description_zh varchar(255),
    ADD COLUMN IF NOT EXISTS description_es varchar(255),
    ADD COLUMN IF NOT EXISTS description_ka varchar(255);

ALTER TABLE public.category
    ADD COLUMN IF NOT EXISTS name_en varchar(255),
    ADD COLUMN IF NOT EXISTS name_ru varchar(255),
    ADD COLUMN IF NOT EXISTS name_zh varchar(255),
    ADD COLUMN IF NOT EXISTS name_es varchar(255),
    ADD COLUMN IF NOT EXISTS name_ka varchar(255);

ALTER TABLE public.size
    ADD COLUMN IF NOT EXISTS name_en varchar(255),
    ADD COLUMN IF NOT EXISTS name_ru varchar(255),
    ADD COLUMN IF NOT EXISTS name_zh varchar(255),
    ADD COLUMN IF NOT EXISTS name_es varchar(255),
    ADD COLUMN IF NOT EXISTS name_ka varchar(255);

UPDATE public.product
SET name_ru = COALESCE(NULLIF(name_ru, ''), name),
    description_ru = COALESCE(NULLIF(description_ru, ''), description);

UPDATE public.category
SET name_ru = COALESCE(NULLIF(name_ru, ''), name);

UPDATE public.size
SET name_ru = COALESCE(NULLIF(name_ru, ''), name);
