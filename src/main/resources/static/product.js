import { displayProducts } from "./displayproducts.js";

//Navigated from category
const category = new URLSearchParams(window.location.search);

//Navigated from search
const searchText = new URLSearchParams(window.location.search);

const categoryId = category.get("categoryId");
const categoryName = category.get("categoryName");

const keyword = searchText.get("keyword");

if(categoryId)
{
    loadProductsForCategory();
}
else if(keyword)
{
    searchProducts();
}


async function loadProductsForCategory()
{
    document.getElementById("category").textContent = `Products in ${categoryName}`;

    try{
        const response = await fetch(`http://localhost:8080/api/admin/products/listAll/byCategory/${categoryId}`);
        const productsPage = await response.json();

        const products = productsPage.list;
        console.log("Products -> ", products);

        displayProducts(products, "productList");
        // const productList = document.getElementById("productList");
        // productList.innerHTML = "";

        // products.forEach(product => {
        //     const li = document.createElement('li');
        //     li.textContent = product.productName;

        //     li.style.cursor="pointer";
        //     li.addEventListener("click", () => {
        //         window.location.href = `productpage.html?productId=${product.id}`;
        //     });


        //     productList.appendChild(li);
        // });

        // const tableBody = document.getElementById("productsTableBody");
        // tableBody.innerHTML = "";

        // products.forEach(product => {
        //     const tr = document.createElement('tr');

        //     tr.innerHTML =
        //         `<td>${product.id}</td>
        //         <td>${product.productName}</td>
        //         <td>${product.finalPrice}</td>`;

        //         tableBody.appendChild(tr);
        // });
    }catch(error)
    {
        console.error("Error loading products:", error);
        alert("Failed to load products!!");
    }
}

async function searchProducts() {
    document.getElementById("category").textContent = `Products for keyword - ${keyword}`;

    try {
        const response = await fetch(`http://localhost:8080/api/user/product/search?keyword=${keyword}`);
        const productsPage = await response.json();

        const products = productsPage.list;

        displayProducts(products, "productList");
    } catch (error) {
        console.error("Error loading products:", error);
        alert("Failed to load products!!");
    }
}

document.getElementById('addProductBtn').addEventListener('click', () => {
    window.location.href = `addproduct.html?categoryId=${categoryId}&categoryName=${categoryName}`;
});
