import { useCart } from '../context/CartContext';

function Cart() {
    const { cart } = useCart();

    return (
        <div>
            <h2>Cart</h2>
            {cart.length === 0 ? (
                <p>Your cart is empty</p>
            ) : (
                <ul data-testid="cart-list">
                    {cart.map(item => (
                        <li key={item.productId}>
                            {item.name} - ${item.price.toFixed(2)} x {item.quantity}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Cart;
