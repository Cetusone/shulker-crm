-- Замена TEXT на безразмерный varchar.
-- В PostgreSQL это один и тот же тип: varchar без длины хранится и работает
-- ровно так же, как text. Изменение сделано ради единообразия со остальными
-- колонками и переносимости на другие СУБД, где text ведёт себя иначе.
-- Приведение text -> varchar бинарно совместимо, перезапись таблиц не требуется.

alter table products
    alter column description type varchar;

alter table stock_movements
    alter column reason type varchar;

alter table own_warehouses
    alter column address type varchar;

alter table partner_warehouses
    alter column address type varchar;
