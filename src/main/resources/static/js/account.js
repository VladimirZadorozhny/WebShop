"use strict";

import{getElemById, setTextInElem, removeContent, showElem} from "./utils.js";

function checkButtons() {
    if (!sessionStorage.getItem("user")) {
        getElemById("info").disabled = true;
        getElemById("delete").disabled = true;
        getElemById("orders").disabled = true;
        getElemById("logout").disabled = true;
    } else {
        getElemById("userInfo").innerText = sessionStorage.getItem("user");
        getElemById("login").disabled = true;
        getElemById("signin").disabled = true;
    }
}

const response = await fetch("principal");
if (response.ok) {
    const userName = await response.text();
    document.getElementById("userInfo").innerText = userName;
    if (userName !== "Unregistered user")
        sessionStorage.setItem("user", userName);
    else
        sessionStorage.removeItem("user");
    checkButtons();
} else {
    alert(response.text);
}

getElemById("login").onclick = () => window.location = "/login";
getElemById("logout").onclick = () => window.location = "/logout";

getElemById("signin").onclick = () => {
    const form = getElemById("signinForm");
    form.hidden = false;

    form.onsubmit = async (e) => {
        e.preventDefault();
        const data = {
            firstname: form.firstname.value,
            lastname: form.lastname.value,
            email: form.email.value,
            password: form.password.value
        };

        const response = await fetch(`users`, {
            method: 'POST',
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(data),
            credentials: "same-origin"
        });

        if (response.ok) {
            alert("You are registered. You can now log in with your email and password.");
            getElemById("signinForm").hidden = true;
        } else if (response.status === 409) {
            alert("This email is already used. Try another email.")
            form.email.value = "";
            form.email.focus();
        } else {
            alert("Unexpected error: " + response.statusText);
            window.location = "/";

        }
    }
}
