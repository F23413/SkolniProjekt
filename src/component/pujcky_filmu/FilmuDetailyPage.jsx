import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';
import DatePicker from 'react-datepicker';

const FilmuDetailyPage = () => {
    const navigate = useNavigate(); // přístup k navigační funkci
    const { idFilmu } = useParams(); // id filmu z URL parametru
    const [filmDetaily, setFilmDetaily] = useState(null);
    const [isLoading, setIsLoading] = useState(true); // trakce stavu načítání
    const [error, setError] = useState(null);
    const [datumPujceni, setDatumPujceni] = useState(null); // proměnná stavu pro datum půjčení
    const [datumVraceni, setDatumVraceni] = useState(null); // proměnná stavu pro datum vrácení
    const [pocetMomentalnePujcenychFilmu, setPocetMomentalnePujcenychFilmu] = useState(1); // proměnná stavu pro počet momentálně půjčených filmů
    const [celkovaCena, setCelkovaCena] = useState(0); // proměnná stavu pro celkovou cenu
    const [showDatePicker, setShowDatePicker] = useState(false);
    const [idUzivatele, setIdUzivatele] = useState('');
    const [showZpravu, setShowZpravu] = useState(false); // proměnná stavu pro kontrolu viditelnosti zpráv
    const [ShowZpravu201, setShowZpravu201] = useState(false);
    const [kodPotvrzeni, setKodPotvrzeni] = useState(''); // proměnná stavu pro kód potvrzení zapůjčení
    const [errorMessage, setErrorMessage] = useState(''); // proměnná stavu pro zprávy o chybách

    useEffect(() => {
        const fetchData = async () => {
            try {
                setIsLoading(true);
                const odpoved = await ApiService.getFilmDleId(idFilmu);
                setFilmDetaily(odpoved.filmPujceny);
                const profilUzivatele = await ApiService.getProfilZalogovanehoUzivatele();
                setIdUzivatele(profilUzivatele.uzivatelPujcuje.id);
            } catch (error) {
                setError(error.odpoved?.data?.zprava || error.zprava);
            } finally {
                setIsLoading(false);// Nastaví stav načítání na false po načtení nebo chybě
            }
        };
        fetchData();
    }, [idFilmu]);// Znovu spustit efekt při změně filmId

    const handleConfirmPujcku = async () => {
        if (!datumPujceni || !datumVraceni) { // kontrola jestli jsou vybrané data půjčení a vrácení
            setErrorMessage('Prosím vyberte datum půjčení a vrácení.');
            setTimeout(() => setErrorMessage(''), 5000); // odstraní zprávu o chybě po 5 sekundách
            return;
        }

        if (isNaN(pocetMomentalnePujcenychFilmu) || pocetMomentalnePujcenychFilmu < 1) {// kontrola jestli počet půjčených filmů je validní
            setErrorMessage('Prosím zadejte validní počet filmů, které už máte vypůjčené');
            setTimeout(() => setErrorMessage(''), 5000); // odstraní zprávu o chybě po 5 sekundách
            return;
        }

        const jedenDen = 24 * 60 * 60 * 1000; // hodiny * minuty * sekundy * milisekundy
        const startDatum = new Date(datumPujceni);
        const endDatum = new Date(datumVraceni);
        const celkemDnu = Math.round(Math.abs((endDatum - startDatum) / jedenDen)) + 1;

        // výpočet celkové ceny
        const cenaFilmuZaPujceni = filmDetaily.cenaFilmu;
        const celkovaCena = cenaFilmuZaPujceni * celkemDnu;

        setCelkovaCena(celkovaCena);
        setPocetMomentalnePujcenychFilmu(pocetMomentalnePujcenychFilmu);
    };

    const prijetiPujcky = async () => {
        const formatDate = (date) => {
            if (!date) return null;
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // měsíce jsou 0–11
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
          };
        try {
            //zajištění datumPujceni a datumVraceni jsou Date objekty
            const startDatum = new Date(datumPujceni);
            const endDatum = new Date(datumVraceni);

            const formatovaneDatumPujceni = formatDate(startDatum);
            const formatovaneDatumVraceni = formatDate(endDatum);

            // objekt pro pujcky
            const pujcka = {
                datumPujceni: formatovaneDatumPujceni,
                datumVraceni: formatovaneDatumVraceni,
                pocetMomentalnePujcenychFilmu: pocetMomentalnePujcenychFilmu
            };
            console.log(pujcka);
            console.log(datumVraceni);
            
            //tvorba pujcky
            const odpoved = await ApiService.pujckaFilmu(idFilmu, idUzivatele, pujcka);
            console.log(odpoved.kodStavu);
            if (odpoved.kodStavu === 200) {
                setKodPotvrzeni(odpoved.kodPotvrzeniZapujceni);
                setShowZpravu(true);
                setTimeout(() => {
                    setShowZpravu(false);
                    navigate('/filmy');
                }, 10000);
            }
            if (odpoved.kodStavu === 201){
                setShowZpravu201(true);
                console.log("201 nastavená");
                setTimeout(() => {
                    setShowZpravu201(false);
                    navigate('/filmy');
                }, 10000);
            }
        } catch (error) {
            setErrorMessage(error.odpoved?.data?.zprava || error.zprava);
            setTimeout(() => setErrorMessage(''), 5000);
        }
    };

    if (isLoading) {
        return <p className='film-detail-loading'>Načítání filmu...</p>;
    }

    if (error) {
        return <p className='film-detail-loading'>{error}</p>;
    }

    if (!filmDetaily) {
        return <p className='film-detail-loading'>Film nenalezen.</p>;
    }

    const { zanrFilmu, cenaFilmu, obrazekFilmu, popisFilmu, pujcky } = filmDetaily;

    return (
        <div className="film-details-pujcka">
            {
                showZpravu && (
                    <p className='pujcka-success-message'>
                        Půjčení proběhlo úspěšně! Kód potvrzení: {kodPotvrzeni}.
                    </p>
                )
            }
            {
                ShowZpravu201 && (
                    <p className='error-message'>
                        Film si nelze půjčit, protože se časově překrývá s jinou výpůjčkou.
                    </p>
                )
            }
            {
                errorMessage && (
                    <p className="error-message">
                        {errorMessage}
                    </p>
                )
            }
            <h2>Detaily filmu</h2>
            <br />
            <img src={obrazekFilmu} alt={zanrFilmu} className='film-details-image' />
            <div className="film-details-info">
                <h3>{zanrFilmu}</h3>
                <p>Cena: {cenaFilmu} Kč / den</p>
                <p>{popisFilmu}</p>
            </div>
            {pujcky && pujcky.length > 0 && (
                <div>
                    <h3>Detaily existujících půjček</h3>
                    <ul className='pujcka-list'>
                        {pujcky.map((pujcka, index) => (
                            <li key={pujcka.id} className='pujcka-item'>
                                <span className="pujcka-number">Půjčka {index + 1} </span>
                                <span className="pujcka-text">Datum půjčení: {pujcka.datumPujceni} </span>
                                <span className="pujcka-text">Datum vrácení: {pujcka.datumVraceni}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            <div className='pujcka-info'>
                <button className='pujcit-now-button' onClick={() => setShowDatePicker(true)}>Vypůjčit</button>
                <button className='go-back-button' onClick={() => navigate(`/filmy`)}>Zpátky</button>
                {showDatePicker && (
                    <div className="date-picker-container">
                        <DatePicker
                            className='detail-search-field'
                            selected={datumPujceni}
                            onChange={(datum) => setDatumPujceni(datum)}
                            selectsStart
                            startDate={datumPujceni}
                            endDate={datumVraceni}
                            placeholderText='Datum pujceni'
                            dateFormat="dd/MM/yyyy"
                        />
                        <DatePicker
                            className='detail-search-field'
                            selected={datumVraceni}
                            onChange={(datum) => setDatumVraceni(datum)}
                            selectsEnd
                            startDate={datumPujceni}
                            endDate={datumVraceni}
                            placeholderText='Datum vraceni'
                            dateFormat="dd/MM/yyyy"
                        />
                        <div className='guest-container'>
                            <div className='guest-div'>
                                <label>Počet Půjčených Filmů</label>
                                <input
                                    type="number"
                                    min="1"
                                    value={pocetMomentalnePujcenychFilmu}
                                    onChange={(e) => setPocetMomentalnePujcenychFilmu(parseInt(e.target.value))}
                                />
                            </div>
                            <button className="confirm-booking" onClick={handleConfirmPujcku}>Potvrzení zapůjčení</button>
                        </div>
                    </div>
                )}

                {celkovaCena > 0 && (
                    <div className='total-price'>
                        <p>Celková cena: {celkovaCena} Kč</p>
                        <p>Celkem půjček: {pocetMomentalnePujcenychFilmu}</p>
                        <button onClick={prijetiPujcky} className='accept-pujcka'>Přijetí půjčky</button>
                    </div>
                )}
            </div>
        </div>
    );
};
export default FilmuDetailyPage;