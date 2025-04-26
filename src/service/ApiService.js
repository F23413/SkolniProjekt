import axios from "axios"

export default class ApiService{
    static BASE_URL = "http://localhost:4040"

    static getHeader(){
        const token = localStorage.getItem("token");
        return{
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    /* registrace nového uživatele*/
    static async registrUzivatel(registrace){
        const odpoved = await axios.post(`${this.BASE_URL}/autent/registrace`, registrace)
        return odpoved.data
    }

    /* zalogováni nového uživatele */
    static async loginUzivatel(prihlaseni){
        const odpoved = await axios.post(`${this.BASE_URL}/autent/login`, prihlaseni)
        return odpoved.data
    }

    // vypsání všech uživatelských profilů
    static async getVsechnyUzivatele(){
        const odpoved = await axios.get(`${this.BASE_URL}/uzivatele/vse`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // vypsání profilu zalogovaného uživatele
    static async getProfilZalogovanehoUzivatele(){
        const odpoved = await axios.get(`${this.BASE_URL}/uzivatele/get-profile-info`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // vypsání profilu uživatele podle id
    static async getProfilUzivateleDleId(idUzivatele){
        const odpoved = await axios.get(`${this.BASE_URL}/uzivatele/get-by-id/${idUzivatele}`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // vypsání půjček uživatele podle id
    static async getPujckyUzivateleDleId(idUzivatele){
        const odpoved = await axios.get(`${this.BASE_URL}/uzivatele/get-uzivatel-pujcky/${idUzivatele}`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // odstranění uživatele
    static async deleteUzivatele(idUzivatele){
        const odpoved = await axios.delete(`${this.BASE_URL}/uzivatele/delete/${idUzivatele}`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // přidání nového filmu do databáze
    static async pridejNovyFilm(dataFormulare){
        const odpoved = await axios.post(`${this.BASE_URL}/filmy/pridat`, dataFormulare, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return odpoved.data;
    }

    // vypsání všech možných filmů
    static async getVsechnyMozneFilmy(){
        const odpoved = await axios.get(`${this.BASE_URL}/filmy/vsechny-mozne-filmy`)
        return odpoved.data
    }

    // vypsání všech možných filmů dle dat a žánrů
    static async getVsechnyMozneFilmyDleDataAZanru(datumPujceni, datumVraceni, zanrFilmu){
        const odpoved = await axios.get(
            `${this.BASE_URL}/filmy/mozne-filmy-dle-data-a-zanru?datumPujceni=${datumPujceni}&datumVraceni=${datumVraceni}&zanrFilmu=${zanrFilmu}`
        )
        return odpoved.data
    }

    // vypsání žánrů všech filmů
    static async getVsechnyZanry(){
        const odpoved = await axios.get(`${this.BASE_URL}/filmy/zanry`)
        return odpoved.data
    }

    // vypsání všech filmů, k mání i vypůjčených
    static async getVsechnyFilmy(){
        const odpoved = await axios.get(`${this.BASE_URL}/filmy/vse`)
        return odpoved.data
    }
    
    // vypsání filmu dle id
    static async getFilmDleId(filmId){
        const odpoved = await axios.get(`${this.BASE_URL}/filmy/film-by-id/${filmId}`)
        return odpoved.data
    }

    // odstranění filmu dle id
    static async odstranFilmDleId(filmId){
        const odpoved = await axios.delete(`${this.BASE_URL}/filmy/delete/${filmId}`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // update filmu v databázi
    static async updateFilm(filmId, dataFormulare){
        const odpoved = await axios.put(`${this.BASE_URL}/filmy/update/${filmId}`, dataFormulare, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return odpoved.data;
    }

    // vložení půjčky do databáze
    static async pujckaFilmu(filmId, idUzivatele, pujcka){
        console.log("ID UZIVATELE JE: " + idUzivatele)

        const odpoved = await axios.post(`${this.BASE_URL}/pujcky/pujcka-filmu/${filmId}/${idUzivatele}`, pujcka, {
            headers: this.getHeader()
        })
        return odpoved.data;
    }

    // vypsání všech půjček z databáze
    static async getVsechnyPujcky(){
        const odpoved = await axios.get(`${this.BASE_URL}/pujcky/vsechny-pujcky`,{
            headers: this.getHeader()
        })
        return odpoved.data;
    }

    // vypsání půjčky dle kódu potvrzení
    static async getPujckuDleKoduPotvrzeni(kodPotvrzeni){
        const odpoved = await axios.get(`${this.BASE_URL}/pujcky/get-by-kod-potvrzeni/${kodPotvrzeni}`)
        return odpoved.data
    }
    
    // zrušení půjčky
    static async zrusPujcku(pujckaId){
        const odpoved = await axios.delete(`${this.BASE_URL}/pujcky/zrusit/${pujckaId}`, {
            headers: this.getHeader()
        })
        return odpoved.data
    }

    // kontrola autentizace
    // při odlogování se odstraní token a role
    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    // kontrola, zda je uživatel autentikovaný
    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }

    // admin nebo uzivatel mají různé přístupy k jiným věcem
    static isAdmin(){
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUzivatel(){
        const role = localStorage.getItem('role')
        return role === 'UZIVATEL'
    }
}
// export default new ApiService();