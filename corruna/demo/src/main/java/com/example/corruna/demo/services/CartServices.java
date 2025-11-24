package com.example.corruna.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.entities.BoletaItem;
import com.example.corruna.demo.entities.Cart;
import com.example.corruna.demo.entities.CartItem;
import com.example.corruna.demo.entities.Producto;
import com.example.corruna.demo.entities.Usuario;
import com.example.corruna.demo.repositories.BoletaResitory;
import com.example.corruna.demo.repositories.CartRepository;

import com.example.corruna.demo.repositories.ProductoRepositories;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductoRepositories productoRepository;

    @Autowired
    private BoletaResitory boletaRepository;

    // ===========================================================
    // OBTENER CARRITO
    // ===========================================================
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUsuarioId(userId)
                .orElseGet(() -> createCartForUser(userId));
    }

    private Cart createCartForUser(Long userId) {
        Cart cart = new Cart();
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        cart.setUsuario(usuario);
        cart.setTotal(0.0);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // AGREGAR ITEM
    // ===========================================================
    public Cart addItem(Long userId, Long productId, int cantidad) {
        Cart cart = getCartByUser(userId);
        Producto producto = productoRepository.findById(productId).orElseThrow();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setSubtotal(producto.getPrecio().doubleValue() * cantidad);

        cart.getItems().add(item);
        cartItemRepository.save(item);

        calcularTotal(cart);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // ELIMINAR ITEM
    // ===========================================================
    public Cart removeItem(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);

        cart.getItems().removeIf(item -> item.getProducto().getId().equals(productId));
        calcularTotal(cart);

        return cartRepository.save(cart);
    }

    // ===========================================================
    // ACTUALIZAR CANTIDAD
    // ===========================================================
    public Cart updateCantidad(Long userId, Long productId, int cantidad) {
        Cart cart = getCartByUser(userId);

        for (int i = 0; i < cart.getItems().size(); i++) {
            CartItem item = cart.getItems().get(i);
            if (item.getProducto().getId().equals(productId)) {
                if (cantidad <= 0) {
                    cart.getItems().remove(i);
                    i--; // Ajustar índice tras eliminación
                } else {
                    item.setCantidad(cantidad);
                    item.setSubtotal(item.getProducto().getPrecio().doubleValue() * cantidad);
                }
            }
        }

        calcularTotal(cart);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // VACIAR CARRITO
    // ===========================================================
    public void clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);
    }

    // ===========================================================
    // CHECKOUT = CREAR BOLETA + VACIAR CARRITO
    // ===========================================================
    public Boleta checkout(Long userId) {
        Cart cart = getCartByUser(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        Boleta boleta = new Boleta();
        boleta.setUsuarioId(userId);
        boleta.setTotal(total);
        boleta.setFecha(LocalDateTime.now());

        // Copiar items del carrito a la boleta
        List<BoletaItem> boletaItems = cart.getItems().stream().map(cartItem -> {
            BoletaItem bi = new BoletaItem();
            bi.setProductoId(cartItem.getProducto().getId());
            bi.setCantidad(cartItem.getCantidad());
            bi.setPrecio(cartItem.getProducto().getPrecio().doubleValue());
            bi.setNombreProducto(cartItem.getProducto().getNombre());
            return bi;
        }).toList();

        boleta.setItems(boletaItems);
        boletaRepository.save(boleta);

        // Vaciar carrito
        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);

        return boleta;
    }

    // ===========================================================
    // RE-CALCULAR TOTAL
    // ===========================================================
    private void calcularTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        cart.setTotal(total);
    }
}