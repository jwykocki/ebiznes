import { useEffect, useState } from 'react';
import { useCart } from '../context/CartContext';

interface Product {
    id: number;
    name: string;
    price: number;
    description?: string;
}

function Products() {
    const [products, setProducts] = useState<Product[]>([]);
    const { addToCart } = useCart();

    useEffect(() => {
        fetch('http://localhost:8080/api/products')
            .then(response => response.json())
            .then(data => setProducts(data))
            .catch(error => console.error('Error fetching products:', error));
    }, []);

    return (
        <div>
            <h2>Products</h2>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        <h3>{product.name}</h3>
                        <p>Price: ${product.price.toFixed(2)}</p>
                        {product.description && <p>{product.description}</p>}
                        <button onClick={() => addToCart({
                            productId: product.id,
                            name: product.name,
                            price: product.price,
                            quantity: 1
                        })}>
                            Add to Cart
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Products;
