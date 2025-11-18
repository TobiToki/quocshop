use shop_quan_ao;

CREATE USER 'myuser'@'localhost' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON shop_quan_ao.* TO 'myuser'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);

insert INTO users (id, username, password) VALUES (1, 'admin', '123');

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
('Áo Thun Basic', 199000, 'Áo thun nam form rộng basic màu trắng, chất cotton mềm mại', 'https://tse3.mm.bing.net/th/id/OIP.wSp1YV0Opy0v3Agf0rpIUwAAAA?rs=1&pid=ImgDetMain&o=7&rm=3', 'Áo nam'),
('Áo Khoác Jean', 399000, 'Áo khoác jean unisex màu xanh, form ôm chuẩn', 'https://th.bing.com/th/id/R.3514c6174483e5519be0d00df1432bb6?rik=v5%2ftU0lU2OGRtw&pid=ImgRaw&r=0', 'Áo khoác'),
('Váy Nữ Xinh', 499000, 'Váy nữ xinh xắn màu đỏ, dáng xòe trẻ trung', 'https://tse2.mm.bing.net/th/id/OIP.UHxRYyQE9syx9wbn5kQ4awHaHa?rs=1&pid=ImgDetMain&o=7&rm=3', 'Váy'),
('Quần Jeans Nam', 299000, 'Quần jeans nam màu đen, chất denim bền chắc', 'https://th.bing.com/th/id/R.049045abb6b09508fdbe5c1bea25cc53?rik=I25NmzpFmAELKA&pid=ImgRaw&r=0', 'Quần nam'),
('Áo Hoodie Nỉ', 449000, 'Áo hoodie nỉ màu xám, thoải mái ấm áp', 'https://cf.shopee.vn/file/d0219727763a7ce8f0d2ea70997a4c14', 'Áo nam'),
('Chân Váy Xếp Ly', 349000, 'Chân váy xếp ly màu navy, thanh lịch và nữ tính', 'https://tse1.mm.bing.net/th/id/OIP.ok_FlFIx8NbNTJsxvE1VBAHaHa?rs=1&pid=ImgDetMain&o=7&rm=3', 'Váy'),
('Áo Sơ Mi Nữ', 329000, 'Áo sơ mi nữ màu trắng, tay ngắn thoáng mát', 'https://ann.com.vn/wp-content/uploads/mua-he-mac-ao-so-mi-nu-trang-dam-bao-tuoi-khong-can-tuoi-277-468.jpg', 'Áo nữ'),
('Áo Len Cổ Lọ', 399000, 'Áo len cổ lọ màu be, ấm áp cho mùa đông', 'https://tse1.explicit.bing.net/th/id/OIP.VNlL8HSiHpoi8iDEcSaLcQHaHa?rs=1&pid=ImgDetMain&o=7&rm=3', 'Áo nữ'),
('Quần Tây Nam', 449000, 'Quần tây nam màu đen, form ôm vừa vặn', 'https://tse4.mm.bing.net/th/id/OIP.8ZnH3uQ8r9pD2K3w5pJ7wgHaHa?rs=1&pid=ImgDetMain&o=7&rm=3', 'Quần nam'),
('Chân Váy Dự Tiệc', 599000, 'Chân váy dự tiệc màu đỏ wine, kiểu dáng lịch sự', 'https://tse2.mm.bing.net/th/id/OIP.3hVzM4P8nV1x9qH5K8J4_gHaHa?rs=1&pid=ImgDetMain&o=7&rm=3', 'Váy');

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    order_date DATETIME NOT NULL,
    total_price DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    unit_price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);