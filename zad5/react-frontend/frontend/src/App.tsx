import React from 'react';
import Products from './components/Products';
import Payments from './components/Payments';
import Cart from './components/Cart';
import { CartProvider } from './context/CartContext';
import './App.css';

function App() {
    return (
        <CartProvider>
            <div className="App">
                <header className="App-header">
                    <h1>Sklep Internetowy</h1>
                </header>
                <main>
                    <section className="products-section">
                        <Products />
                    </section>
                    <section className="cart-section">
                        <Cart />
                    </section>
                    <section className="payments-section">
                        <Payments />
                    </section>
                </main>
            </div>
        </CartProvider>
    );
}

export default App;
