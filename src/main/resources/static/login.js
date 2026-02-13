document.getElementById("login").addEventListener('click', login);

async function login() {
    const loginRequest = {
        email : document.getElementById("email").value,
        password : document.getElementById("pwd").value
    };

    try{
        const response = await fetch("/api/auth/signin",{
            method: "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(loginRequest)
        });
        const result = await response.json();
        console.log('Response -> ' , result);
        alert(result.message);
    }catch (error) {
        console.error("Error:", error);
        alert("Failed to login!");
      }
}