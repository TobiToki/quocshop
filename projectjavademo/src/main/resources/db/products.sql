-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    description TEXT,
    image_url VARCHAR(1000),
    category VARCHAR(100)
);

-- Insert sample products
INSERT INTO products (name, price, description, image_url, category) VALUES
('Áo thun nam basic', 199000, 'Áo thun nam form rộng basic màu trắng', '/images/products/ao-thun-nam-1.jpg', 'Áo nam'),
('Quần jean nữ ống rộng', 399000, 'Quần jean nữ ống rộng màu xanh nhạt', '/images/products/quan-jean-nu-1.jpg', 'Quần nữ'),
('Áo sơ mi trắng nam', 299000, 'Áo sơ mi nam dài tay màu trắng', '/images/products/ao-so-mi-1.jpg', 'Áo nam'),
('Váy liền thân', 499000, 'Váy liền thân màu hồng pastel', '/images/products/vay-1.jpg', 'Váy'),
('Áo khoác denim', 599000, 'Áo khoác denim unisex', '/images/products/ao-khoac-1.jpg', 'Áo khoác'),
('Quần tây nam', 449000, 'Quần tây nam màu đen', '/images/products/quan-tay-1.jpg', 'Quần nam'),
('Áo len nữ', 359000, 'Áo len nữ dệt kim', '/images/products/ao-len-1.jpg', 'Áo nữ'),
('Quần short nam', 249000, 'Quần short nam thể thao', '/images/products/quan-short-1.jpg', 'Quần nam'),
('Áo blazer nữ', 799000, 'Áo blazer nữ màu be', '/images/products/ao-blazer-1.jpg', 'Áo nữ'),
('Chân váy xếp ly', 349000, 'Chân váy xếp ly màu đen', '/images/products/chan-vay-1.jpg', 'Váy');