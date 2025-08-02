async function login() {
    response = await fetch(
        '/api/login',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: document.querySelector('#cf').value,
                password: document.querySelector('#pwd').value,
                vid: document.querySelector('#vid').value
            })
        })

    try {
        if (!response.ok) throw new Error("login error")

        data = await response.json()
        sessionStorage.setItem('nome', data.nome)
        sessionStorage.setItem('cognome', data.cognome)

        sessionStorage.setItem('vId', document.querySelector('#vid').value)

        window.location.href = "/reserved/register.html"
    }
    catch (error) {
        if (response.status == 403) {
            wrongLogin()
        }

        else {
            alert('qualcosa è andato storto nel login :/')
        }
    }
}

function wrongLogin() {
    document.querySelector('#loginDiv').classList.add('wrong')
    document.querySelector('#wct').style.visibility = 'visible'
}

function controllaRedirect() {
    const params = new URLSearchParams(window.location.search)
    const redirect = params.get("redirect")
    if (redirect) {
        alert('Sei stato riportato alla pagina di login a causa di un errore di autenticazione')
    }
}