const apiUrl = 'http://127.0.0.1:5000/';

function showError(message) {
    alert(message);
}

async function getRecord() {
    const name = document.getElementById('getName').value;
    if (!name.trim()) {
        return showError("Por favor, insira um nome para fazer a requisição GET");
    }
    const response = await fetch(`${apiUrl}?name=${name}`);
    const result = await response.json();
    document.getElementById('getResult').textContent = JSON.stringify(result, null, 2);
}

async function getAllRecords() {
    const response = await fetch(`${apiUrl}get-all`);
    const result = await response.json();
    document.getElementById('getAllResult').textContent = JSON.stringify(result, null, 2);
}

async function postRecord() {
    const name = document.getElementById('postName').value;
    const email = document.getElementById('postEmail').value;
    if (!name.trim() || !email.trim()) {
        return showError("Por favor, insira um nome e email para fazer a requisição POST.");
    }
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email })
    });
    const result = await response.json();
    document.getElementById('postResult').textContent = JSON.stringify(result, null, 2);
}

async function putRecord() {
    const name = document.getElementById('putName').value;
    const email = document.getElementById('putEmail').value;
    if (!name.trim() || !email.trim()) {
        return showError("Por favor, insira um nome e email para fazer a requisição PUT.");
    }
    const response = await fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email })
    });
    const result = await response.json();
    document.getElementById('putResult').textContent = JSON.stringify(result, null, 2);
}

async function deleteRecord() {
    const name = document.getElementById('deleteName').value;
    if (!name.trim()) {
        return showError("Por favor, insira um nome para fazer a requisição DELETE.");
    }
    const response = await fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name })
    });
    const result = await response.json();
    document.getElementById('deleteResult').textContent = JSON.stringify(result, null, 2);
}

async function optionsRequest() {
    const response = await fetch(apiUrl, {
        method: 'OPTIONS'
    });
    const result = await response.json();
    document.getElementById('optionsResult').textContent = JSON.stringify(result, null, 2);
}
