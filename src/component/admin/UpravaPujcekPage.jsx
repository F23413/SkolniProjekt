import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const UpravaPujcekPage = () => {
    const navigate = useNavigate();
    const { kodPujcky } = useParams();
    const [detailyPujcky, setDetailyPujcky] = useState(null); // State variable for booking details
    const [error, setError] = useState(null); // Track any errors
    const [success, setSuccessMessage] = useState(null); // Track any errors

    useEffect(() => {
        const fetchDetailyPujcky = async () => {
            try {
                const odpoved = await ApiService.getPujckuDleKoduPotvrzeni(kodPujcky);
                setDetailyPujcky(odpoved.oPujcce);
            } catch (error) {
                setError(error.zprava);
            }
        };

        fetchDetailyPujcky();
    }, [kodPujcky]);


    const dokoncitPujcku = async (idPujcky) => {
        if (!window.confirm('Jste si jisti, že chcete tuto půjčku provést?')) {
            return; // nic se nestane když uživatel odejde
        }

        try {
            const odpoved = await ApiService.zrusPujcku(idPujcky);
            if (odpoved.kodStavu === 200) {
                setSuccessMessage("Půjčka byla úspěšně provedena")
                
                setTimeout(() => {
                    setSuccessMessage('');
                    navigate('/admin/sprava-pujcek');
                }, 3000);
            }
        } catch (error) {
            setError(error.odpoved?.data?.zprava || error.zprava);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className="find-pujcka-page">
            <h2>Booking Detail</h2>
            
            {error && <p className='error-message'>{error}</p>}
            {success && <p className='success-message'>{success}</p>}
            {detailyPujcky && (
                <div className="pujcka-details">
                    <h3>Detaily půjčky</h3>
                    <p>Kód potvrzení: {detailyPujcky.kodPotvrzeniZapujceni}</p>
                    <p>Datum půjčení: {detailyPujcky.datumPujceni}</p>
                    <p>Datum vrácení: {detailyPujcky.datumVraceni}</p>
                    <p>Počet zapůjčených filmů: {detailyPujcky.pocetMomentalnePujcenychFilmu}</p>
                    {/* <p>Email: {detailyPujcky.guestEmail}</p>
                    <br />
                    <hr />
                    <br />
                    <h3>Detaily půjčujícího</h3>
                    <div>
                        <p> Jméno: {detailyPujcky.uzivatel.jmeno}</p>
                        <p> Email: {detailyPujcky.uzivatel.email}</p>
                        <p> Telefonní číslo: {detailyPujcky.uzivatel.telCislo}</p>
                    </div>
                    <br />
                    <hr />
                    <br />
                    <h3>Detaily filmu</h3>
                    <div>
                        <p> Žánr filmu: {detailyPujcky.film.zanrFilmu}</p>
                        <p> Cena filmu: ${detailyPujcky.film.cenaFilmu}</p>
                        <p> Popis filmu: {detailyPujcky.film.popisFilmu}</p>
                        <img src={detailyPujcky.film.obrazekFilmu} alt="Obrazek filmu" sizes="" srcSet="" />
                    </div> */}
                    <button
                        className="acheive-pujcka"
                        onClick={() => dokoncitPujcku(detailyPujcky.id)}>Dokončit půjčku
                    </button>
                </div>
            )}
        </div>
    );
};

export default UpravaPujcekPage;