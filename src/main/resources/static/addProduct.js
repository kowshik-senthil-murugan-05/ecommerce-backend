
const params = new URLSearchParams(window.location.search);
const categoryId = params.get("categoryId");
const categoryName = params.get("categoryName");
console.log("category id -> ", categoryId);
document.getElementById('addProduct').addEventListener('click', addProduct);
async function addProduct() {

    const productDTO = {
        productName : document.getElementById('productName').value,
        description : document.getElementById('description').value,
        totalQuantity : document.getElementById('qty').value,
        productPrice : document.getElementById('price').value,
        sellerId : document.getElementById('seller').value, //to remove
        categoryId : categoryId
    };

    try {
        const response = await fetch('http://localhost:8080/api/seller/product/add', {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(productDTO)
        });

    const result = await response.json();
    console.log("Result -> ", result);
    alert(result.message);

    window.location.href = `product.html?categoryId=${categoryId}&categoryName=${encodeURIComponent(categoryName)}`;
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to add product");
    }
}