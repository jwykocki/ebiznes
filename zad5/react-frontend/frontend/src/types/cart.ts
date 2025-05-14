export interface PaymentRequest {
    orderId: string;
    amount: number;
    cardNumber: string;
    cardHolder: string;
    expiryDate: string;
    cvv: string;
    cartItems: CartItem[];
    sessionId: string;
}

export interface PaymentResponse {
    success: boolean;
    message: string;
    transactionId?: string;
}

// frontend/src2/types/cart.ts
export interface CartItem {
    productId: number;
    quantity: number;
    price: number;
    name?: string; // Dodane dla wygody wyświetlania
}

export interface Cart {
    items: CartItem[];
    total: number;
}

export interface AddToCartRequest {
    productId: number;
    quantity: number;
}