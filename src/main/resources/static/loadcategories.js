async function loadCategories() {
    try{
        const response = await fetch("http://localhost:8080/api/admin/categories/listAll");
        const categoryPageDetails = await response.json();

        const categories = categoryPageDetails.list;
        console.log("full response -> ", categories);

        const list = document.getElementById("categoryList");
        list.innerHTML = "";

        categories.forEach(category => {
            const li = document.createElement("li");
            li.textContent = category.categoryName;

            li.style.cursor = "pointer";
            li.addEventListener("click", () => {
                // window.location.href = "product.html";
                window.location.href = `product.html?categoryId=${category.id}&categoryName=${encodeURIComponent(category.categoryName)}`;
            });
            list.appendChild(li);
        });
    }catch (error) {
        console.error("Error fetching categories:", error);
        alert("Failed to load categories");
    }
}

loadCategories();