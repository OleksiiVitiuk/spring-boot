DELETE FROM categories;
INSERT INTO categories(id, name, description)
values (1, 'Fantasy', 'Interesting fantastic stories');
ALTER TABLE categories ALTER COLUMN id RESTART WITH 2;