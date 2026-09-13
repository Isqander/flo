CREATE TABLE IF NOT EXISTS public.currency_rates (
    currency varchar(3) PRIMARY KEY,
    units_per_eur numeric(20, 10) NOT NULL,
    fetched_at timestamptz NOT NULL
);

ALTER TABLE public.product
    ADD COLUMN IF NOT EXISTS price_currency varchar(3);

-- Existing prices are in rubles. The application converts them once after
-- the first successful rate refresh and marks them as EUR.
UPDATE public.product
SET price_currency = 'RUB'
WHERE price_currency IS NULL;

ALTER TABLE public.product
    ALTER COLUMN price_currency SET DEFAULT 'EUR',
    ALTER COLUMN price_currency SET NOT NULL;
