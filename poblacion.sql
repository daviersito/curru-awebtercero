use base;
INSERT INTO `rol` (`id`, `nombre`) VALUES
  (1, 'ADMIN'),
  (2, 'CLIENTE')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO `categoria` (`id`, `nombre`) VALUES
  (1, 'Helados'),
  (2, 'Sundaes y Copas'),
  (3, 'Conos y Paletas'),
  (4, 'Toppings'),
  (5, 'Bebidas Frías')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO `producto` (`id`, `nombre`, `descripcion`, `precio`, `stock`, `imagen`, `estado`, `fecha_creacion`, `categoria_id`) VALUES
  (1, 'Helado Vainilla 1 bola', 'Helado artesanal sabor vainilla', 1200, 100, NULL, 1, 20251124, 1),
  (2, 'Helado Chocolate 1 bola', 'Helado artesanal sabor chocolate', 1200, 90, NULL, 1, 20251124, 1),
  (3, 'Copa Especial Chocolate', 'Copa con dos bolas, salsa de chocolate, crema y topping', 4500, 30, NULL, 1, 20251124, 2),
  (4, 'Cono Doble (Vainilla+Fresa)', 'Cono con dos sabores a elección', 2500, 80, NULL, 1, 20251124, 3),
  (5, 'Paleta Frutal Mango', 'Paleta artesanal de mango', 900, 60, NULL, 1, 20251124, 3),
  (6, 'Topping Chispas', 'Chispas de chocolate', 200, 200, NULL, 1, 20251124, 4),
  (7, 'Sirope Dulce', 'Sirope para topping (caramelo o chocolate)', 300, 150, NULL, 1, 20251124, 4),
  (8, 'Limonada Casera 500ml', 'Bebida refrescante', 1500, 50, NULL, 1, 20251124, 5)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), precio = VALUES(precio), stock = VALUES(stock);

INSERT INTO `usuario` (`id`, `nombre`, `email`, `contra`, `estado`, `fecha_creacion`, `rol_id`) VALUES
  (1, 'Admin Heladería', 'admin@gmail.com', '12345', 1, 2025, 1),
  (2, 'Cliente Demo', 'cliente@gmail.com', '12345', 1, 2025, 2),
  (3, 'Cliente Ana', 'ana@gmail.com', '12345', 1, 2025, 2)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), email = VALUES(email);