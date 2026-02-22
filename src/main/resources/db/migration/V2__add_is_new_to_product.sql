DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = 'public'
          AND table_name = 'product'
    ) THEN
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.columns
            WHERE table_schema = 'public'
              AND table_name = 'product'
              AND column_name = 'is_new'
        ) THEN
            ALTER TABLE public.product ADD COLUMN is_new boolean;
        END IF;

        UPDATE public.product
        SET is_new = false
        WHERE is_new IS NULL;

        ALTER TABLE public.product ALTER COLUMN is_new SET DEFAULT false;
        ALTER TABLE public.product ALTER COLUMN is_new SET NOT NULL;
    END IF;
END $$;
