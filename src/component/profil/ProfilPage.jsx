import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const ProfilPage = () => {
    const [uzivatel, setUzivatel] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfilUzivatele = async () => {
            try {
                const odpoved = await ApiService.getProfilZalogovanehoUzivatele();
                const uzivatelPlusPujcky = await ApiService.getPujckyUzivateleDleId(odpoved.uzivatelPujcuje.id);
                setUzivatel(uzivatelPlusPujcky.uzivatelPujcuje);
            } catch (error) {
                setError(error.zprava);
            }
        };
        fetchProfilUzivatele();
    }, []);

    const handleLogout = () => {
        ApiService.logout();
        navigate('/home');
    }

    const handleEditProfil = () => {
        navigate('/edit-profil');
    };
    return(
        <div className="profile-page">
            {uzivatel && <h2>Vítejte, {uzivatel.jmeno}</h2>}
            <div className="profile-actions">
                <button className="edit-profile-button" onClick={handleEditProfil}>Upravit profil</button>
                <button className="logout-button" onClick={handleLogout}>Logout</button>
            </div>
            {error && <p className="error-message">{error}</p>}
            {uzivatel && (
                <div className="profile-details">
                    <h3>Detaily profilu</h3>
                    <p><strong>Email:</strong> {uzivatel.email}</p>
                    <p><strong>Telefonní číslo:</strong> {uzivatel.telCislo}</p>
                </div>
            )}
            <div className="pujcky-section">
                <h3>Historie půjčování</h3>
                <div className="pujcka-list">
                    {uzivatel && uzivatel.pujcky.length > 0 ? (
                        uzivatel.pujcky.map((pujcka) => (
                            <div key={pujcka.id} className="pujcka-item">
                                <p><strong>Kód půjčky:</strong> {pujcka.kodPotvrzeniZapujceni}</p>
                                <p><strong>Název půjčeného filmu:</strong> {pujcka.filmPujceny.nazevFilmu}</p>
                                <p><strong>Datum půjčení:</strong> {pujcka.datumPujceni}</p>
                                <p><strong>Datum vrácení:</strong> {pujcka.datumVraceni}</p>
                                <p><strong>Počet půjčených filmů:</strong> {pujcka.pocetMomentalnePujcenychFilmu}</p>
                                <p><strong>Žánr filmu:</strong> {pujcka.filmPujceny.zanrFilmu}</p>
                                <img src={pujcka.filmPujceny.obrazekFilmu} alt="film" className="film-photo" />
                            </div>
                        ))
                    ) : (
                        <p>Žádné půjčky nalezeny</p>
                    )}
                </div>
            </div>
        </div>
    )
}
export default ProfilPage;