"use strict";

import{getElemById, setTextInElem, removeContent, showElem, getPrincipal} from "./utils.js";

const userName = await getPrincipal();
getElemById("userInfo").innerText = userName;

let shopcart = {};
if (sessionStorage.getItem("shopcart"))
    shopcart = JSON.parse(sessionStorage.getItem("shopcart"));

checkButtons(userName);

function checkButtons(userName) {
    if (userName === "Unregistered user") {
        getElemById("info").disabled = true;
        getElemById("shopcart").disabled = true;
        getElemById("delete").disabled = true;
        getElemById("orders").disabled = true;
        getElemById("logout").disabled = true;
    } else {
        getElemById("login").disabled = true;
        getElemById("signin").disabled = true;
    }
}

await synchronizeShopcarts(userName);

async function synchronizeShopcarts(userName) {
    const unregisteredKey = "Unregistered user";
    if (userName !== unregisteredKey) {
        if (!shopcart[userName]) {
            shopcart[userName] = {};
            await loadShopcartFromDB(shopcart, userName);
            sessionStorage.setItem("shopcart", JSON.stringify(shopcart));
        }

        if (shopcart[unregisteredKey]) {
            for (const [key, value] of Object.entries(shopcart[unregisteredKey])) {
                if (!shopcart[userName][key])
                    shopcart[userName][key] = value;
            }
            delete shopcart[unregisteredKey];
            sessionStorage.setItem("shopcart", JSON.stringify(shopcart));
        }
    }

}

async function loadShopcartFromDB(shopcart, userName) {
    const responseShopcart = await fetch (`users/${encodeURIComponent(userName)}/shopcart`);
    if (responseShopcart.ok) {
        const shopcartProducts = await responseShopcart.json();
        for (const product of shopcartProducts) {
            shopcart[userName][product.id] = product;
        }

    } else {
        alert("There is an error!")
    }
}

// const response = await fetch("principal");
// if (response.ok) {
//     const userName = await response.text();
//     document.getElementById("userInfo").innerText = userName;
//     if (userName !== "Unregistered user") {
//         if (sessionStorage.getItem("user") !== userName ) {
//             sessionStorage.setItem("user", userName);
//             const unknownUser = "unknown";
//             let shopcart = {};
//             if (sessionStorage.getItem("shopcart"))
//                 shopcart = JSON.parse(sessionStorage.getItem("shopcart"));
//             if (!shopcart[userName])
//                 shopcart[userName] = {};
//             shopcart[userName] = shopcart[unknownUser];
//             delete shopcart[unknownUser];
//
//             const responseShopcart = await fetch (`users/${encodeURIComponent(userName)}/shopcart`);
//             if (responseShopcart.ok) {
//                 const shopcartProducts = await responseShopcart.json();
//                 for (const product of shopcartProducts) {
//                     shopcart[userName][product.id] = product;
//                 }
//                 sessionStorage.setItem("shopcart", JSON.stringify(shopcart));
//             } else {
//                 alert("There is an error!")
//             }
//
//         }
//
//     }
//     else
//         sessionStorage.removeItem("user");
//     checkButtons();
// } else {
//     alert(response.text);
// }

getElemById("shopcart").onclick = () => window.location = "/shopcart.html";
getElemById("login").onclick = () => {
    sessionStorage.removeItem("user");
    window.location = "/login";
}
getElemById("logout").onclick = async () => {

    if (shopcart?.[userName]) {
        const productIds = [];
        for (let idx of Object.keys(shopcart[userName])) {
            productIds.push(idx);
        }
        const response = await fetch(`users/${encodeURIComponent(userName)}/shopcart`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(productIds),
            credentials: "same-origin"
        });
        if (response.ok) {

            // write data to shopcart DB
            delete shopcart[userName];
            sessionStorage.setItem("shopcart", JSON.stringify(shopcart));

        } else {
            alert("There is an error!")
        }
    }
    sessionStorage.removeItem("user");
    window.location = "/logout";
}

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
