UPDATE public.category
SET name_en = CASE id
    WHEN 1 THEN 'Socks'
    WHEN 2 THEN 'Japanese'
    WHEN 3 THEN 'Menswear'
    WHEN 5 THEN 'Outerwear'
    WHEN 7 THEN 'Accessories'
    WHEN 12 THEN 'All'
    WHEN 13 THEN 'Dresses'
    WHEN 14 THEN 'Tops'
    WHEN 15 THEN 'Bottoms'
    WHEN 16 THEN 'Shoes'
    WHEN 17 THEN 'Bags'
    WHEN 18 THEN 'Custom'
    WHEN 19 THEN 'Jewelry'
    ELSE name
END,
name_ru = COALESCE(NULLIF(name_ru, ''), name),
name_zh = CASE id
    WHEN 1 THEN '袜子'
    WHEN 2 THEN '日式'
    WHEN 3 THEN '男装'
    WHEN 5 THEN '外套'
    WHEN 7 THEN '配饰'
    WHEN 12 THEN '全部'
    WHEN 13 THEN '连衣裙'
    WHEN 14 THEN '上装'
    WHEN 15 THEN '下装'
    WHEN 16 THEN '鞋履'
    WHEN 17 THEN '包袋'
    WHEN 18 THEN '定制'
    WHEN 19 THEN '珠宝'
    ELSE name
END,
name_es = CASE id
    WHEN 1 THEN 'Calcetines'
    WHEN 2 THEN 'Japonés'
    WHEN 3 THEN 'Moda masculina'
    WHEN 5 THEN 'Ropa exterior'
    WHEN 7 THEN 'Accesorios'
    WHEN 12 THEN 'Todo'
    WHEN 13 THEN 'Vestidos'
    WHEN 14 THEN 'Parte superior'
    WHEN 15 THEN 'Parte inferior'
    WHEN 16 THEN 'Calzado'
    WHEN 17 THEN 'Bolsos'
    WHEN 18 THEN 'Custom'
    WHEN 19 THEN 'Joyería'
    ELSE name
END,
name_ka = CASE id
    WHEN 1 THEN 'წინდები'
    WHEN 2 THEN 'იაპონური'
    WHEN 3 THEN 'მამაკაცის ტანსაცმელი'
    WHEN 5 THEN 'ზედა ტანსაცმელი'
    WHEN 7 THEN 'აქსესუარები'
    WHEN 12 THEN 'ყველა'
    WHEN 13 THEN 'კაბები'
    WHEN 14 THEN 'ზედა'
    WHEN 15 THEN 'ქვედა'
    WHEN 16 THEN 'ფეხსაცმელი'
    WHEN 17 THEN 'ჩანთები'
    WHEN 18 THEN 'კასტომი'
    WHEN 19 THEN 'სამკაული'
    ELSE name
END;

UPDATE public.size
SET name_en = CASE id
    WHEN 1 THEN 'XS'
    WHEN 2 THEN 'S'
    WHEN 3 THEN 'M'
    WHEN 4 THEN 'L'
    WHEN 5 THEN 'XL'
    WHEN 6 THEN 'XXL'
    WHEN 7 THEN 'XXXL'
    WHEN 8 THEN 'One size'
    WHEN 9 THEN 'XS - S'
    WHEN 10 THEN 'S - M'
    WHEN 11 THEN 'XS - S'
    WHEN 12 THEN 'S - M'
    WHEN 13 THEN 'M - L'
    WHEN 14 THEN 'L'
    ELSE name
END,
name_ru = COALESCE(NULLIF(name_ru, ''), name),
name_zh = CASE id
    WHEN 8 THEN '均码'
    ELSE name
END,
name_es = CASE id
    WHEN 8 THEN 'Talla única'
    ELSE name
END,
name_ka = CASE id
    WHEN 8 THEN 'ერთი ზომა'
    ELSE name
END;

UPDATE public.product
SET name_en = CASE id
    WHEN 4 THEN 'qdwcw'
    WHEN 10 THEN 'Escada gloves by Margaretha Ley'
    WHEN 16 THEN 'Roberto Cavalli skirt with removable belt'
    WHEN 17 THEN 'Moschino jeans shirt'
    WHEN 18 THEN 'Moschino bag'
    WHEN 19 THEN 'Vintage Christian Dior compact mirror'
    WHEN 20 THEN 'Vera Mont skirt'
    WHEN 21 THEN 'Hamlet Couture boots'
    WHEN 22 THEN 'Vintage Plein Sud jeans belt'
    WHEN 23 THEN 'Roccobarocco jeans skirt'
    WHEN 24 THEN 'Versace Versus bag'
    WHEN 25 THEN 'Escada Edition skirt with removable belt'
    WHEN 26 THEN 'Richmond X top'
    WHEN 27 THEN 'Custom Guess bandage skirt with stirrups'
    WHEN 28 THEN 'Alexander McQueen pumps'
    WHEN 29 THEN 'Plein Sud blouse'
    WHEN 30 THEN 'Dries Van Noten sandals'
    WHEN 31 THEN 'Vintage Chloe skirt'
    WHEN 32 THEN 'Vintage Nanni belt'
    WHEN 33 THEN 'Gianmarco Lorenzi sock pumps'
    WHEN 34 THEN 'Vintage necklace'
    WHEN 35 THEN 'Guess by Marciano dress'
    WHEN 36 THEN 'Luciano Padovan pumps'
    WHEN 37 THEN 'Custom leather jacket'
    WHEN 38 THEN 'PRADA skirt'
    WHEN 39 THEN 'Vintage dress'
    WHEN 40 THEN 'Dolce and Gabbana bracelet'
    WHEN 42 THEN 'Vintage leather top'
    WHEN 44 THEN 'Escada 90s pumps'
    WHEN 45 THEN 'Vintage 1950s handbag'
    WHEN 46 THEN 'Vintage Valentino Miss V pumps'
    WHEN 47 THEN 'Gianmarco Lorenzi pumps'
    WHEN 48 THEN 'Stefanel suede jacket'
    WHEN 49 THEN 'Vintage michiyuki'
    WHEN 50 THEN 'Vintage michiyuki'
    WHEN 51 THEN 'Vintage striped michiyuki'
    WHEN 52 THEN 'Vintage kimono'
    WHEN 53 THEN 'Vintage kimono with family crest'
    WHEN 54 THEN 'Vintage kimono with Egyptian motifs'
    WHEN 55 THEN 'Jimmy Choo boots'
    WHEN 56 THEN 'Just Cavalli ankle boots'
    WHEN 57 THEN 'Vintage Givenchy clip-on earrings, 1980s'
    WHEN 58 THEN 'AGL boots'
    WHEN 60 THEN 'Custom vintage skirt'
    WHEN 62 THEN 'Vintage Japanese clutch with mirror'
    WHEN 63 THEN 'Vintage handbag with mirror'
    WHEN 67 THEN 'Vintage Barbara Lee cardigan'
    WHEN 68 THEN 'Armani Exchange jacket'
    WHEN 69 THEN 'Joseph Ribkoff convertible skirt'
    WHEN 71 THEN 'Ralph Lauren blazer'
    WHEN 72 THEN 'Traditional tabi socks'
    WHEN 73 THEN 'Marc Jacobs blouse'
    WHEN 74 THEN 'Ralph Lauren jeans'
    WHEN 75 THEN 'Versace dress'
    WHEN 76 THEN 'Fabric harness with metal details'
    WHEN 77 THEN 'Vintage Guess bag'
    WHEN 78 THEN 'Streets Ahead belt'
    WHEN 79 THEN 'Vintage Fendi bag'
    WHEN 80 THEN 'Vintage Just Elegance blouse'
    WHEN 81 THEN 'VIC MATIE pumps'
    WHEN 82 THEN 'Funk Plus belt'
    WHEN 83 THEN 'Leather Fuck Off choker'
    WHEN 84 THEN 'Donna Karan sandals'
    WHEN 88 THEN 'Vintage sheer Innovative blouse'
    WHEN 89 THEN 'Vintage Vic MATIE bag'
    WHEN 90 THEN 'Vintage Gucci belt'
    WHEN 91 THEN 'Short VIC MATIE cowboy boots'
    WHEN 92 THEN 'Vintage Baruch skirt'
    WHEN 93 THEN 'Vintage Eddie Bauer headband'
    WHEN 94 THEN 'Vintage Yessica dress'
    WHEN 96 THEN 'Diamond Tears pendant'
    WHEN 97 THEN 'Vintage Love Moschino bag'
    WHEN 98 THEN 'Custom Post Xchange boots'
    WHEN 99 THEN 'Vintage peony kimono'
    WHEN 100 THEN 'Vintage michiyuki coat'
    WHEN 101 THEN 'Vintage rose kimono'
    WHEN 102 THEN 'Vintage kimono with trees in snow'
    WHEN 103 THEN 'Vintage michiyuki coat'
    WHEN 104 THEN 'New vintage kimono with belt'
    WHEN 105 THEN 'New vintage Kansai kimono'
    WHEN 106 THEN 'Emporio Armani jacket'
    WHEN 107 THEN 'Vintage Invita Vera Pelle belt'
    WHEN 108 THEN 'Vintage Givenchy lighter'
    WHEN 109 THEN 'Valentino Garavani gloves'
    WHEN 110 THEN 'Vintage Gianni Versace sweater'
    WHEN 111 THEN 'Vintage Christian Dior compact mirror'
    WHEN 112 THEN 'Gianfranco Ferre ties'
    WHEN 113 THEN 'Vintage ball pendant'
    WHEN 114 THEN 'Gianmarco Lorenzi ankle boots'
    WHEN 116 THEN 'Oscar de la Renta belt'
    WHEN 118 THEN 'Vintage Versace Jeans Couture trousers'
    WHEN 119 THEN 'Cesare Paciotti boots'
    WHEN 120 THEN 'Vintage medallion powder compact'
    WHEN 122 THEN 'Feather statement jewelry'
    WHEN 123 THEN 'Marc Jacobs pumps'
    WHEN 124 THEN 'Balenciaga dress'
    WHEN 125 THEN 'Choker with vintage cross'
    WHEN 126 THEN 'Choker with vintage silver cross'
    WHEN 127 THEN 'Giuseppe Zanotti ankle boots'
    WHEN 128 THEN 'Artisan choker with detachable pendants'
    WHEN 129 THEN 'Artisan choker with detachable pendants'
    WHEN 130 THEN 'Space Couture dress'
    WHEN 131 THEN 'Jean Paul Gaultier 90s jeans'
    WHEN 132 THEN 'Vintage Giorgio Armani top'
    WHEN 133 THEN 'Louis Vuitton mules'
    ELSE name
END,
name_ru = COALESCE(NULLIF(name_ru, ''), name),
description_ru = COALESCE(NULLIF(description_ru, ''), description);

UPDATE public.product
SET description_en = description;

DO $$
DECLARE
    item record;
    translated text;
BEGIN
    FOR item IN SELECT id, description FROM public.product LOOP
        translated := item.description;
        translated := replace(translated, 'Состав', 'Materials');
        translated := replace(translated, 'состав', 'Materials');
        translated := replace(translated, 'Размеры', 'Dimensions');
        translated := replace(translated, 'размеры', 'Dimensions');
        translated := replace(translated, 'Размер', 'Size');
        translated := replace(translated, 'размер', 'Size');
        translated := replace(translated, 'Параметры', 'Measurements');
        translated := replace(translated, 'параметры', 'Measurements');
        translated := replace(translated, 'Состояние', 'Condition');
        translated := replace(translated, 'состояние', 'Condition');
        translated := replace(translated, 'ручная работа', 'Handmade');
        translated := replace(translated, 'Материалы', 'Materials');
        translated := replace(translated, 'материалы', 'Materials');
        translated := replace(translated, 'Цвет', 'Color');
        translated := replace(translated, 'цвет', 'Color');
        translated := replace(translated, 'Ширина по спинке', 'Back width');
        translated := replace(translated, 'ширина по спинке', 'Back width');
        translated := replace(translated, 'обхват ладони', 'palm circumference');
        translated := replace(translated, 'длина среднего пальца', 'middle finger length');
        translated := replace(translated, 'высота голенища', 'shaft height');
        translated := replace(translated, 'высота каблука', 'heel height');
        translated := replace(translated, 'длина ручки', 'handle length');
        translated := replace(translated, 'длина цепочки', 'chain length');
        translated := replace(translated, 'длина', 'length');
        translated := replace(translated, 'ширина', 'width');
        translated := replace(translated, 'высота', 'height');
        translated := replace(translated, 'натуральная телячья кожа', 'genuine calf leather');
        translated := replace(translated, 'натуральная кожа', 'genuine leather');
        translated := replace(translated, 'кожа натуральная', 'genuine leather');
        translated := replace(translated, 'натуральной кожи', 'genuine leather');
        translated := replace(translated, 'искусственный шелк', 'artificial silk');
        translated := replace(translated, 'искусственный шёлк', 'artificial silk');
        translated := replace(translated, 'натурального шелка', 'natural silk');
        translated := replace(translated, 'натуральный шелк', 'natural silk');
        translated := replace(translated, 'шёлк', 'silk');
        translated := replace(translated, 'шелк', 'silk');
        translated := replace(translated, 'шерсть', 'wool');
        translated := replace(translated, 'хлопок', 'cotton');
        translated := replace(translated, 'полиэстер', 'polyester');
        translated := replace(translated, 'эластан', 'elastane');
        translated := replace(translated, 'нейлон', 'nylon');
        translated := replace(translated, 'акрил', 'acrylic');
        translated := replace(translated, 'вискоза', 'viscose');
        translated := replace(translated, 'ацетат', 'acetate');
        translated := replace(translated, 'металл', 'metal');
        translated := replace(translated, 'стекло', 'glass');
        translated := replace(translated, 'посеребрение', 'silver plating');
        translated := replace(translated, 'искусственный камень', 'synthetic stone');
        translated := replace(translated, 'натуральная замша', 'genuine suede');
        translated := replace(translated, 'замша', 'suede');
        translated := replace(translated, 'текстиль', 'textile');
        translated := replace(translated, 'ткань', 'fabric');
        translated := replace(translated, 'питона', 'python');
        translated := replace(translated, 'питон', 'python');
        translated := replace(translated, 'перья страуса', 'ostrich feathers');
        translated := replace(translated, 'перья', 'feathers');
        translated := replace(translated, 'бисерная вышивка', 'beaded embroidery');
        translated := replace(translated, 'бисер', 'beads');
        translated := replace(translated, 'отличное', 'excellent');
        translated := replace(translated, 'хорошее', 'good');
        translated := replace(translated, 'новое', 'new');
        translated := replace(translated, 'регулируется', 'adjustable');
        translated := replace(translated, 'съемным поясом', 'with removable belt');
        translated := replace(translated, 'съемный пояс', 'removable belt');
        translated := replace(translated, 'маломерят', 'runs small');
        translated := replace(translated, 'подойдет', 'fits');
        translated := replace(translated, 'есть мини недочет', 'has a minor flaw');
        translated := replace(translated, 'небольшие следы носки', 'minor signs of wear');
        translated := replace(translated, 'на танкетке с открытым носом', 'wedge with an open toe');
        translated := replace(translated, 'Металлический крест с контрастным объемным распятием', 'Metal cross with a contrasting three-dimensional crucifix');
        translated := replace(translated, 'металлический крест с контрастным объемным распятием', 'Metal cross with a contrasting three-dimensional crucifix');
        translated := replace(translated, 'натуральная лаковая кожа', 'patent leather');
        translated := replace(translated, 'жаккардовая канва', 'jacquard canvas');
        translated := replace(translated, 'имеется реставрация стельки', 'the insole has been restored');
        translated := replace(translated, 'имеется', 'there is');
        translated := replace(translated, 'имеет', 'has');
        translated := replace(translated, 'купра', 'cupro');
        translated := replace(translated, 'креп', 'crêpe');
        translated := replace(translated, 'кружево', 'lace');
        translated := replace(translated, 'бархат', 'velvet');
        translated := replace(translated, 'хлопковая', 'cotton');
        translated := replace(translated, 'шерстяная', 'wool');
        translated := replace(translated, 'кожаный', 'leather');
        translated := replace(translated, 'кожаная', 'leather');
        translated := replace(translated, 'кожаное', 'leather');
        translated := regexp_replace(translated, E'([0-9])\\s*см', E'\\1 cm', 'g');
        translated := replace(translated, 'пара', 'pair');
        translated := replace(translated, 'новая', 'new');
        translated := replace(translated, 'новый', 'new');
        translated := replace(translated, 'новое', 'new');
        translated := replace(translated, 'состояние отличное', 'excellent condition');
        translated := replace(translated, 'состояние хорошее', 'good condition');
        translated := replace(translated, 'открытый нос', 'open toe');

        UPDATE public.product SET description_en = translated WHERE id = item.id;
    END LOOP;
END $$;
