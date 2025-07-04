"use strict";

export function getElemById(id) {
    return document.getElementById(id);
}

export function setTextInElem(id, text) {
    getElemById(id).textContent = text;
}

export function showElem(id) {
    getElemById(id).hidden = false;
}

export function hideElem(id) {
    getElemById(id).hidden = true;
}

export function removeContent(element) {
    element.innerHTML = "";
}