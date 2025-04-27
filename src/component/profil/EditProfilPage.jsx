import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const EditProfilPage = () =>{
    const [uzivatel, setUzivatel] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(()=>{
        const fetchProfilUzivatele = async ()=>{
            try{
                const odpoved = await ApiService.getProfilZalogovanehoUzivatele();
                setUzivatel(odpoved.uzivatel);
            }catch (error) {
                setError(error.zprava);
            }
        };
        fetchProfilUzivatele();
    }, []);

    const handleOdstranitProfil = async() =>{
        if (!window.confirm('Opravdu chcete odstranit svůj účet?')){
            return;
        }
        try{
            await ApiService.deleteUzivatele(uzivatel.id);
            navigate('/registrace');
        }catch (error){
            setError(error.zprava);
        }
    };
    return(
        <div className='edit-profile-page'>
            <h2>Upravit profil</h2>
            {error && <p className='error-message'>{error}</p>}
            {uzivatel && (
                <div className='profile-details'>
                    <p><strong>Jméno:</strong> {uzivatel.jmeno}</p>
                    <p><strong>Email:</strong> {uzivatel.email}</p>
                    <p><strong>Telefonní číslo:</strong> {uzivatel.telCislo}</p>
                    <button className="delete-profile-button" onClick={handleOdstranitProfil}>Odstranit profil</button>
                </div>
            )}
        </div>
    )
}
export default EditProfilPage;