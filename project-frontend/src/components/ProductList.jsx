import React, { useState, useEffect } from "react";
import axios from "axios";
import Payment from "./Payment";
import { getToken } from "../Services/AuthServices";

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [showAddProduct, setShowAddProduct] = useState(false);
    const [showAddCategory, setShowAddCategory] = useState(false);
    const [editingProduct, setEditingProduct] = useState({ category: { categoryId: '' } });
    const [newCategory, setNewCategory] = useState({ name: '' });

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get("http://localhost:7000/api/products", {
                    headers : {
                        "Authorization" : "Bearer " + getToken()
                    }
                });
                setProducts(response.data);
            } catch (error) {
                setError(error);
            }
        };

        fetchProducts();
    }, []);

    const handleDelete = async (productId) => {
        try {
            await axios.delete(`http://localhost:7000/api/products/${productId}`, {
                headers : {
                    "Authorization" : "Bearer " + getToken()
                }
            });
            setProducts(products.filter(product => product.productId !== productId));
        } catch (error) {
            console.error('Error deleting product:', error);
            setError(error);
        }
    };

    const handleEdit = (product) => {
        setEditingProduct(product);
        setShowAddProduct(true);
    };

    const handleAddProduct = () => {
        setEditingProduct({ category: { categoryId: '' } });
        setShowAddProduct(true);
    };

    const handleAddCategory = () => {
        setNewCategory({ name: '' });
        setShowAddCategory(true);
    };

    const handleSubmitProduct = async (event) => {
        event.preventDefault();
        try {
            if (editingProduct.productId) {
                await axios.put("http://localhost:7000/api/products", {
                    ...editingProduct,
                    category: {
                        categoryId: editingProduct.category.categoryId,
                        name: editingProduct.category.name || ''
                    }
                }, {
                    headers : {
                        "Authorization" : "Bearer " + getToken()
                    }
                });
                setProducts(products.map(product => product.productId === editingProduct.productId ? editingProduct : product));
            } else {
                const response = await axios.post(`http://localhost:7000/api/products/${editingProduct.category.categoryId}`, editingProduct, {
                    headers : {
                        "Authorization" : "Bearer " + getToken()
                    }
                });
                setProducts([...products, response.data]);
            }
            setShowAddProduct(false);
            setEditingProduct({ category: { categoryId: '' } });
        } catch (error) {
            console.error('Error saving product:', error);
            setError(error);
        }
    };

    const handleSubmitCategory = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post("http://localhost:7000/api/categories", { name: newCategory.name }, {
                headers : {
                    "Authorization" : "Bearer " + getToken()
                }
            });
            console.log('Category added:', response.data);
            setShowAddCategory(false);
        } catch (error) {
            console.error('Error adding category:', error);
            setError(error);
        }
    };

    const handleProductInputChange = (event) => {
        const { name, value } = event.target;
        if (name === "categoryId" || name === "categoryName") {
            setEditingProduct({
                ...editingProduct,
                category: {
                    ...editingProduct.category,
                    [name === "categoryId" ? "categoryId" : "name"]: value
                }
            });
        } else {
            setEditingProduct({ ...editingProduct, [name]: value });
        }
    };

    const handleCategoryInputChange = (event) => {
        const { name, value } = event.target;
        setNewCategory({ ...newCategory, [name]: value });
    };

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    const navigateToPayment = () => {
        <Payment product={products} />
    }

    return (
        <>
            <div className="container">
                <h2 className='header'>Product List</h2>
                <button className="button" onClick={handleAddProduct}>Add Product</button>
                <button className="button" onClick={handleAddCategory}>Add Category</button>
                <table>
                    <thead>
                        <tr>
                            <th>Product Id</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th>Available</th>
                            <th>Category</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map(product => (
                            <tr
                                key={product.productId}
                            >
                                <td>{product.productId}</td>
                                <td>{product.name}</td>
                                <td>₹{product.price}</td>
                                <td>{product.description}</td>
                                <td>{product.available ? 'Yes' : 'No'}</td>
                                <td>{product.category?.name || 'No Category'}</td>
                                <td>
                                    {/* <button className="button" onClick={() => navigateToPayment(product)}>Buy</button> */}
                                    <button className="button" onClick={() => handleEdit(product)}>Edit</button>
                                    <button className="button" onClick={() => handleDelete(product.productId)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {showAddProduct && (
                <div className="popup">
                    <div className="popupInner">
                        <button className="closeButton" onClick={() => setShowAddProduct(false)}>Close</button>
                        <h2 className='header'>{editingProduct?.productId ? "Edit Product" : "Add Product"}</h2>
                        <form onSubmit={handleSubmitProduct} className="formContainer">
                            {editingProduct?.productId && (
                                <div className="formGroup">
                                    <label className="formLabel">Product Id:</label>
                                    <input className="formInput" type="text" name="productId" value={editingProduct.productId} onChange={handleProductInputChange} readOnly />
                                </div>
                            )}
                            <div className="formGroup">
                                <label className="formLabel">Product Name:</label>
                                <input className="formInput" type="text" name="name" value={editingProduct?.name || ''} onChange={handleProductInputChange} required />
                            </div>
                            <div className="formGroup">
                                <label className="formLabel">Price:</label>
                                <input className="formInput" type="number" name="price" value={editingProduct?.price || ''} onChange={handleProductInputChange} required />
                            </div>
                            <div className="formGroup">
                                <label className="formLabel">Description:</label>
                                <input className="formInput" type="text" name="description" value={editingProduct?.description || ''} onChange={handleProductInputChange} required />
                            </div>
                            <div className="formGroup">
                                <label className="formLabel">Photo Path:</label>
                                <input className="formInput" type="text" name="photoPath" value={editingProduct?.photoPath || ''} onChange={handleProductInputChange} required />
                            </div>
                            <div className="formGroup">
                                <label className="formLabel">Available:</label>
                                <input className="formInput" type="checkbox" name="available" checked={editingProduct?.available || false} onChange={(e) => handleProductInputChange({ target: { name: 'available', value: e.target.checked } })} />
                            </div>
                            <div className="formGroup">
                                <label className="formLabel">Category Id:</label>
                                <input className="formInput" type="text" name="categoryId" value={editingProduct?.category?.categoryId || ''} onChange={handleProductInputChange} required />
                            </div>
                            {editingProduct?.productId && (
                                <div className="formGroup">
                                    <label className="formLabel">Category Name:</label>
                                    <input className="formInput" type="text" name="categoryName" value={editingProduct?.category?.name || ''} onChange={handleProductInputChange} />
                                </div>
                            )}
                            <div className="formGroup">
                                <button className="submitButton" type="submit">{editingProduct?.productId ? "Update" : "Add"}</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {showAddCategory && (
                <div className="popup">
                    <div className="popupInner">
                        <button className="closeButton" onClick={() => setShowAddCategory(false)}>Close</button>
                        <h2 className='header'>Add Category</h2>
                        <form onSubmit={handleSubmitCategory} className="formContainer">
                            <div className="formGroup">
                                <label className="formLabel">Category Name:</label>
                                <input className="formInput" type="text" name="name" value={newCategory.name} onChange={handleCategoryInputChange} required />
                            </div>
                            <div className="formGroup">
                                <button className="submitButton" type="submit">Add</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
};

export default ProductList;
