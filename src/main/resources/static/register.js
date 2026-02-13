document.getElementById("register").addEventListener('click', register);

async function register() {
    const signupRequest = {
        name : document.getElementById("name").value,
        email : document.getElementById("email").value,
        password : document.getElementById("pwd").value
    };

    try{
        const response = await fetch("http://localhost:8080/api/auth/signup",{
            method: "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(signupRequest)
        });
        const result = await response.json();
        console.log('Response -> ' , result);

        if(!response.ok)
        {
            alert(result.message);
            return;
        }

        alert(result.body);

        //Redirect to login page
        window.location.href="login.html";
    }catch (error) {
        console.error("Error:", error);
        alert("Failed to signup!");
      }
}