import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';
import Pagination from '../common/Pagination';

const SpravaPujcekPage = () => {
    const [pujcky, setPujcky] = useState([]);
    const [filteredPujcky, setFilteredPujcky] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [pujckyPerPage] = useState(6);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPujcky = async () => {
            try {
                const odpoved = await ApiService.getVsechnyPujcky();
                const vsechnyPujcky = odpoved.pujckyList;
                setPujcky(vsechnyPujcky);
                setFilteredPujcky(vsechnyPujcky);
            } catch (error) {
                console.error('Error hledání půjček:', error.message);
            }
        };

        fetchPujcky();
    }, []);

    useEffect(() => {
        filterPujcky(searchTerm);
    }, [searchTerm, pujcky]);

    const filterPujcky = (term) => {
        if (term === '') {
            setFilteredPujcky(pujcky);
        } else {
            const filtrovane = pujcky.filter((pujcky) =>
                pujcky.kodPotvrzeniZapujceni && pujcky.kodPotvrzeniZapujceni.toLowerCase().includes(term.toLowerCase())
            );
            setFilteredPujcky(filtrovane);
        }
        setCurrentPage(1);
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const indexPosledniPujcky = currentPage * pujckyPerPage;
    const indexPrvniPujcky = indexPosledniPujcky - pujckyPerPage;
    const currentpujcky = filteredPujcky.slice(indexPrvniPujcky, indexPosledniPujcky);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className='pujcky-container'>
            <h2>Všechny půjčky</h2>
            <div className='search-div'>
                <label>Filtrování podle čísla půjčení:</label>
                <input
                    type="text"
                    value={searchTerm}
                    onChange={handleSearchChange}
                    placeholder="Zadejte číslo půjčky"
                />
            </div>

            <div className="pujcka-results">
                {currentpujcky.map((pujcka) => (
                    <div key={pujcka.id} className="pujcka-result-item">
                        <p><strong>Kód půjčky:</strong> {pujcka.kodPotvrzeniZapujceni}</p>
                        <p><strong>Datum půjčení:</strong> {pujcka.checkInDate}</p>
                        <p><strong>Datum vrácení:</strong> {pujcka.checkOutDate}</p>
                        <p><strong>Počet půjčených filmů:</strong> {pujcka.pocetMomentalnePujcenychFilmu}</p>
                        <button
                            className="edit-film-button"
                            onClick={() => navigate(`/admin/edit-pujcka/${pujcka.kodPotvrzeniZapujceni}`)}
                        >Manage pujcka</button>
                    </div>
                ))}
            </div>

            <Pagination
                filmyPerPage={pujckyPerPage}
                totalfilmy={filteredPujcky.length}
                currentPage={currentPage}
                paginate={paginate}
            />
        </div>
    );
};

export default SpravaPujcekPage;