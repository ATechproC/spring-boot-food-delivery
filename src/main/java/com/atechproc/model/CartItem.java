package com.atechproc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @OneToMany
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();

    private Integer quantity = 1;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void calculateTotalPrice() {
        totalPrice = food.getPrice().multiply(new BigDecimal(quantity));
    }
}
