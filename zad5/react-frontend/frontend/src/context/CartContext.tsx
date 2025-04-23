import React, { createContext, useContext, useState, ReactNode } from 'react';

interface CartItem {
    productId: number;
    name: string;
    price: number;
    quantity: number;
}

interface CartContextType {
    cart: CartItem[];
    addToCart: (item: CartItem) => void;
}

const CartContext = createContext<CartContextType | undefined>(undefined);
export const CartProvider = ({ children }: { children: ReactNode }) => {
    const [cart, setCart] = useState<CartItem[]>([]);

    const sessionId = (() => {
        const existing = localStorage.getItem('sessionId');
        const newId = existing || Math.random().toString(36).substring(2);
        if (!existing) localStorage.setItem('sessionId', newId);
        return newId;
    })();

    const addToCart = (item: CartItem) => {
        setCart(prevCart => {
            const existing = prevCart.find(i => i.productId === item.productId);
            if (existing) {
                return prevCart.map(i =>
                    i.productId === item.productId
                        ? { ...i, quantity: i.quantity + item.quantity }
                        : i
                );
            } else {
                return [...prevCart, item];
            }
        });

        fetch('http://localhost:8080/api/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Session-Id': sessionId, // ✅ now uses the same session as Payments.tsx
            },
            body: JSON.stringify({
                productId: item.productId,
                quantity: item.quantity,
            }),
        });
    };

    return (
        <CartContext.Provider value={{ cart, addToCart }}>
            {children}
        </CartContext.Provider>
    );
};


export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) throw new Error('useCart must be used within a CartProvider');
    return context;
};
