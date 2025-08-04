const lineaDrop = document.querySelector('#lineaDrop')
const capolineaDrop = document.querySelector('#capolineaDrop')
const orarioDrop = document.querySelector('#orarioDrop')
const registraBtn = document.querySelector('#registraBtn')
let orari = null


async function popolaLinee() {
    try {
        const response = await fetch('/api/getLinee')
        if (!response.ok) throw new Error()

        const linee = await response.json()

        lineaDrop.innerHTML = ''
        lineaDrop.appendChild(creaDefaultOption("linea"))

        linee.forEach(item => {
            const option = document.createElement('option')
            option.value = item
            option.textContent = item
            lineaDrop.appendChild(option)
        })

        lineaDrop.disabled = false
    } catch (error) {
        console.error(error)
        lineaDrop.innerHTML = '<option value="">Errore di caricamento</option>'
    }
}

async function popolaCapolinea() {
    lockBtn()
    resettaDropdown(orarioDrop)
    try {
        const response = await fetch('/api/getOrari?linea=' + String(lineaDrop.value))
        if (!response.ok) throw new Error()

        orari = await response.json()
        let capolinea = {}

        for (const i in orari) {
            capolinea[orari[i].capolinea] = orari[i].nomeCapolinea
        }

        capolineaDrop.innerHTML = ''
        capolineaDrop.appendChild(creaDefaultOption('capolinea'))

        Object.keys(capolinea).forEach(key => {
            const option = document.createElement('option')
            option.value = key
            option.textContent = capolinea[key]
            capolineaDrop.appendChild(option)
        })

        capolineaDrop.disabled = false
    } catch (error) {
        console.error(error)
        capolineaDrop.innerHTML = '<option value="">Errore di caricamento</option>'
    }
}

async function popolaOrario() {

    orarioDrop.innerHTML = ''
    orarioDrop.appendChild(creaDefaultOption("orario"))

    for (i in orari) {
        if (orari[i].capolinea == capolineaDrop.value) {
            const option = document.createElement('option')
            timeString = orari[i].orario //String(orari[i].orario[0]) + ':' + String(orari[i].orario[1])
            option.value = timeString
            option.textContent = timeString.slice(0, 5)
            orarioDrop.appendChild(option)
        }
    }

    orarioDrop.disabled = false
}

function creaDefaultOption(text) {
    const option = document.createElement('option')
    option.value = 0
    option.textContent = text
    option.disabled = true
    option.selected = true

    return option
}

function resettaDropdown(dropdown) {
    dropdown.innerHTML = '<option value="">Scegli sopra...</option>'
}

async function registra() {
    response = await fetch(
        '/api/reserved/registraViaggio',
        {
            method: 'POST',
            credentials: 'include',  // manda i cookies
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "orarioPartenza": orarioDrop.value,
                "capolinea": capolineaDrop.value,
                "linea": lineaDrop.value
            })
        })
    try {
        if (!response.ok) { throw new Error() }
        window.location.href = "/reserved/monitor.html"
    } catch (e) {
        if (response.status >= 500 && response.status <= 599) {
            alert('server error')
        }
        else {
            alert('unknown error')
        }
    }
}

function lockBtn() {
    registraBtn.disabled = true
}

function unlockBtn() {
    registraBtn.disabled = false
}