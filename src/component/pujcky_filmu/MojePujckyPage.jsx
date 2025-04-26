import React, { useState } from "react";
import ApiService from "../../service/ApiService";

const MojePujckyPage = () => {
    const [kodPotvrzeni, setKodPotvrzeni] = useState('');
    const [detailyPujcovani, setDetailyPujcovani] = useState(null);
    const [error, setError] = useState(null);

    const handleSearch = async () => {
        if (!kodPotvrzeni.trim()) {
            setError("Prosím poskytněte kód potvrzení zapůjčení");
            setTimeout(() => setError(''), 5000);
            return;
        } try { // zavolá API pro získání dat o půjčce
            const odpoved = await ApiService.getPujckuDleKoduPotvrzeni(kodPotvrzeni);
            setDetailyPujcovani(odpoved.oPujcce);
            /*"oPujcce": {
        "id": 1,
        "datumPujceni": "2025-04-23",
        "datumVraceni": "2025-04-30",
        "pocetMomentalnePujcenychFilmu": 1,
        "kodPotvrzeniZapujceni": "S6G24G424K"
    }*/
            setError(null); // vyčištění erroru pokud je úspěch
        } catch (error) {
            setError(error.odpoved?.data?.zprava || error.zprava);
            setTimeout(() => setError(''), 5000);
        }
    };
    return (
        <div className="find-pujcka-page">
            <h2>Hledat půjčku</h2>
            <div className="search-container">
                <input
                    required
                    type="text"
                    placeholder="Vložte Váš kód potvrzení zapůjčení"
                    value={kodPotvrzeni}
                    onChange={(e) => setKodPotvrzeni(e.target.value)} />
                <button onClick={handleSearch}>Hledat</button>
            </div>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {detailyPujcovani && (
                <div className="pujcka-details">
                    <h3>Detaily půjčky</h3>
                    <p>Kód potvrzení: {detailyPujcovani.kodPotvrzeniZapujceni}</p>
                    <p>Datum půjčení: {detailyPujcovani.datumPujceni}</p>
                    <p>Datum vrácení: {detailyPujcovani.datumVraceni}</p>
                    <p>Počet momentálně půjčených filmů: {detailyPujcovani.pocetMomentalnePujcenychFilmu}</p>
                    <br />
                    <hr />
                    <br />
                    <h3>Detaily uživatele</h3>
                    <div>
                        {/* <p>Jméno: {detailyPujcovani.uzivatelPujcuje.jmeno}</p>
                        <p>Email {detailyPujcovani.uzivatelPujcuje.email}</p>
                        <p>Telefonní číslo: {detailyPujcovani.uzivatelPujcuje.telCislo}</p> */}
                    </div>
                    <br />
                    <hr />
                    <br />
                    <h3>Detaily filmu</h3>
                    <div>
                        {/* <p> Žánr filmu: {detailyPujcovani.filmPujceny.zanrFilmu}</p>
                        <img src={detailyPujcovani.filmPujceny.obrazekFilmu} alt="obrazek filmu" sizes="" srcSet="" /> */}
                    </div>
                </div >
            )}
        </div>
    );
};

export default MojePujckyPage;