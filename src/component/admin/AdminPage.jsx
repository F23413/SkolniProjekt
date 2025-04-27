import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from '../../service/ApiService';

const AdminPage = () => {
    const [adminJmeno, setAdminJmeno] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAdminJmeno = async () => {
            try {
                const odpoved = await ApiService.getProfilZalogovanehoUzivatele();
                setAdminJmeno(odpoved.uzivatel.jmeno);
            } catch (error) {
                console.error('Chyba při načítání údajů správce:', error.message);
            }
        };

        fetchAdminJmeno();
    }, []);

    return (
        <div className="admin-page">
            <h1 className="welcome-message">Vítejte, {adminJmeno}</h1>
            <div className="admin-actions">
                <button className="admin-button" onClick={() => navigate('/admin/sprava-filmu')}>
                    Správa filmů
                </button>
                <button className="admin-button" onClick={() => navigate('/admin/sprava-pujcek')}>
                    Správa půjček
                </button>
            </div>
        </div>
    );
}

export default AdminPage;