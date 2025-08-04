//#region classes

class InfoboxData { //unofficial interface    
    static infoBoxHtml(elementi) {
        let ul = document.createElement('ul')
        elementi.forEach(elemento => {
            const li = document.createElement('li');
            li.textContent = elemento;
            ul.appendChild(li);
        });
        return ul;
    }
}

class Estremo extends InfoboxData {
    infoBoxHtml() {
        return InfoboxData.infoBoxHtml(['Estremo']).outerHTML
    }
}

class Checkpoint extends InfoboxData {
    constructor(nome, orarioPianificato) {
        super()
        this.nome = nome
        this.orarioPianificato = dateCreationAdapter(orarioPianificato)
        this.orarioEffettivo = addMinutes(this.orarioPianificato, vStatus.ritardo)
    }

    infoBoxHtml() {
        let elementi = []
        elementi.push('Tipo:\t\tCheckpoint')
        elementi.push(`Nome:\t\t\t\t\t${this.nome}`)
        elementi.push(`Orario pianificato:\t${getOrarioString(this.orarioPianificato)}`)
        elementi.push(`Orario effettivo:\t${getOrarioString(this.orarioEffettivo)}`)
        return InfoboxData.infoBoxHtml(elementi).outerHTML
    }
}

class Stazione extends Checkpoint {
    constructor(nome, orarioPianificato, lunghezza, attesa) {
        super(nome, orarioPianificato)
        this.lunghezza = lunghezza
        this.attesa = attesa
    }

    infoBoxHtml() {
        let elementi = []
        elementi.push('Tipo:\t\tStazione')
        elementi.push(`Nome:\t\t\t\t\t${this.nome}`)
        elementi.push(`Orario pianificato:\t${getOrarioString(this.orarioPianificato)}`)
        elementi.push(`Orario effettivo:\t${getOrarioString(this.orarioEffettivo)}`)
        elementi.push(`Durata fermata:\t\t${this.attesa}s`)
        elementi.push(`Lunghezza stazione:\t${this.lunghezza}`)
        return InfoboxData.infoBoxHtml(elementi).outerHTML
    }
}

class Status extends InfoboxData {
    constructor(ritardo = 0) {
        super()
        this.vId = sessionStorage.getItem("vId")
        this.guasto = false
        this.ritardo = ritardo
    }

    infoBoxHtml() {
        let elementi = []
        elementi.push(`Id veicolo: ${this.vId}`)
        elementi.push(`Ritardo: ${this.ritardo} minuti`)
        elementi.push(`Stato veicolo: ${this.guasto ? "Guasto" : "Funzionante"}`)
        return InfoboxData.infoBoxHtml(elementi).outerHTML
    }
}
//#endregion
//#region utilities

function dateCreationAdapter(timestamp) {
    // timestamp ottenuto dal backend segue formato HH:mm
    [hours, minutes] = timestamp.split(':')

    date = new Date()

    if (hours < date.getHours()) {
        date.setDate(date.getDate() + 1); //adds one day
    }
    date.setHours(hours)
    date.setMinutes(minutes)

    return date
}

function addMinutes(date, minutes) {
    date.setMinutes(date.getMinutes() + minutes);
    return date
}

function getOrarioString(date) {
    return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`
}

function newCheck(data) {
    tipo = data.tipo
    switch (tipo) {
        case 'estremo':
            return new Estremo()

        case 'stazione':
            return new Stazione(data.nome, data.orarioPianificato, data.lunghezza, data.attesa)

        case 'checkpoint':
            return new Checkpoint(data.nome, data.orarioPianificato)
    }
}

//#endregion
//#region page logic
let vStatus = new Status()
let checkAttuale = null
let checkSucc = null

prossCheckpBox = document.getElementById('prossCheckpBox')
statusBox = document.getElementById('statusBox')
ultCheckpBox = document.getElementById('ultCheckpBox')

async function getInfo() {
    response = await fetch(
        '/api/reserved/infoCheckpoint',
        {
            method: 'GET',
            credentials: 'include',
        })

    if (!response.ok) { alert('server error') }
    data = await response.json()
    aggiornaCheck(data)
    aggiornaRitardo()
    aggiornaUI()
}

function aggiornaCheck(data) {
    checkAttuale = newCheck(data[0])
    checkSucc = newCheck(data[1])
}

function aggiornaUI() {
    prossCheckpBox.innerHTML = checkSucc.infoBoxHtml()
    ultCheckpBox.innerHTML = checkAttuale.infoBoxHtml()
    statusBox.innerHTML = vStatus.infoBoxHtml()
}

function aggiornaRitardo() {
    if (checkAttuale instanceof Estremo) {
        ritardoDate = Date.now() - checkSucc.orarioPianificato.getTime()
    } else {
        ritardoDate = Date.now() - checkAttuale.orarioPianificato.getTime()
    }

    //ritardoDate è in ms
    vStatus.ritardo = Math.round(ritardoDate / 60000) //60s*1000ms
}

async function guasto() {
    vStatus.guasto = !vStatus.guasto

    response = await fetch(
        '/api/reserved/guasto',
        {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "guasto": vStatus.guasto.toString()
            })
        })

    if (!response.ok) { alert('server error') }
    aggiornaUI()
}

async function prosCheckp() {
    aggiornaRitardo()
    response = await fetch(
        '/api/reserved/prossimoCheckpoint',
        {
            method: 'GET',
            credentials: 'include',
        })

    if (!response.ok) { alert('server error') }
    if (response.status == 205) { window.location.href = "/lCompletata.html" }
    data = await response.json()
    aggiornaCheck(data)
    aggiornaUI()
}

//#endregion