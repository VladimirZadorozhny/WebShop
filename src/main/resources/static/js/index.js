"use strict";

const response = await fetch("principal");
if (response.ok) {
    const userName = await response.text();
    document.getElementById("userInfo").innerText = userName;
} else {
    alert(response.text);
}

document.getElementById("loginButton").onclick = () => {
    window.location.href = "login.html";
}