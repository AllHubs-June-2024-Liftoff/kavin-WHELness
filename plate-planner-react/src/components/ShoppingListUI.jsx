import shoppingListService from "../services/shoppingListService";
import Button from "./Button";
import { useEffect, useState } from "react";

function ShoppingListUI() {
    const [shoppingList, setShoppingList] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchShoppingList() {
            try {
                const response = await shoppingListService.getShoppingList;

                if (response.ok) {
                    const data = await response.json();
                    setShoppingList(data);
                } else if (response.status === 404) {
                    setShoppingList(null);
                } else {
                    setError(`Error fetching shopping list: ${response.status}`);
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }

        fetchShoppingList();
    }, []);

    const handleCreateOrUpdate = async () => {
        try {
            setError(null);

            const response = await shoppingListService.createOrUpdateShoppingList();

            if (!response.ok) {
                throw new Error(`Failed to create/update shopping list (status: ${response.status})`);
            }

            const updatedList = await response.json();
            setShoppingList(updatedList);
        } catch (err) {
            setError(err.message);
        }
    };

    if (error) {
        return <p className="error">Error: {error}</p>;
    }

    if (loading) {
        return <p>Loading shopping list...</p>;
    }

    const hasShoppingList = Boolean(shoppingList);

    return (
        <div className="card">
            <Button 
            label={hasShoppingList ? 'Update Shopping List' : 'Create Shopping List'}
                onClick={handleCreateOrUpdate}
            />

        {hasShoppingList && shoppingList.items && shoppingList.items.length > 0 ? (
            <ul>
                {shoppingList.items.map((item, index) => (
                    <li key={index}>
                        {item.ingredientName} - {item.quantity} {item.unit}
                    </li>
                ))}
            </ul>
        ) : (
            <p>No items yet.</p>
        )}
        </div>
    );
}

export default ShoppingListUI;