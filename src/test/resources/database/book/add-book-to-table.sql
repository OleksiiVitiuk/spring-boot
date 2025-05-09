INSERT INTO books(id, title, author, isbn, price, description, cover_image)
VALUES (1, 'BookTitle', 'BookAuthor', '123-456-789', 20, 'desc', 'path');
INSERT INTO books_categories(book_id, category_id) VALUES (1, 1);