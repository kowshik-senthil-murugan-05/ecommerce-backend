document.getElementById('add').addEventListener('click', addCategory);

async function addCategory() {
    const category = {
        categoryName : document.getElementById('categoryName').value
    };

    try{
        const response = await fetch("http://localhost:8080/api/admin/category/add",{
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(category)
        });

        const result = await response.json();
        console.log('Response -> ' , result);
        alert(result.message);

        loadCategories();
    }catch (error) {
        console.error("Error:", error);
        alert("Failed to add category");
      }
}
