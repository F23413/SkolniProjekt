import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';
import Pagination from '../common/Pagination';
import FilmResult from '../common/FilmResult';

const ManagefilmPage = () => {
  const [filmy, setfilmy] = useState([]);
  const [filtrovaneFilmy, setFiltrovaneFilmy] = useState([]);
  const [filmTypes, setfilmTypes] = useState([]);
  const [selectedFilmType, setselectedFilmType] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [filmyPerPage] = useState(5);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchfilmy = async () => {
      try {
        const response = await ApiService.getVsechnyFilmy();
        const allfilmy = response.filmList;
        setfilmy(allfilmy);
        setFiltrovaneFilmy(allfilmy);
      } catch (error) {
        console.error('Error hledání filmů:', error.message);
      }
    };

    const fetchfilmTypes = async () => {
      try {
        const types = await ApiService.getVsechnyZanry();
        setfilmTypes(types);
      } catch (error) {
        console.error('Error hledání žánrů filmů:', error.message);
      }
    };

    fetchfilmy();
    fetchfilmTypes();
  }, []);

  const handlefilmTypeChange = (e) => {
    setselectedFilmType(e.target.value);
    filterfilmy(e.target.value);
  };
  const filterfilmy = (term) => {
    if (!Array.isArray(filmy)) {
      setFiltrovaneFilmy([]);
        return;
    }

    if (term === '') {
      setFiltrovaneFilmy(filmy);
    } else {
        const filtrovane = filmy.filter((film) =>
            film.kodPotvrzeniZapujceni &&
            film.kodPotvrzeniZapujceni.toLowerCase().includes(term.toLowerCase())
        );
        setFiltrovaneFilmy(filtrovane);
    }
    setCurrentPage(1);
};


  // Pagination
  const indexPoslednihoFilmu = currentPage * filmyPerPage;
  const indexPrvnihoFilmu = indexPoslednihoFilmu - filmyPerPage;
  const currentFilmy = (filtrovaneFilmy || []).slice(indexPrvnihoFilmu, indexPoslednihoFilmu);

  // Change page
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className='all-filmy'>
      <h2>Všechny filmy</h2>
      <div className='all-film-filter-div' style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div className='filter-select-div'>
          <label>Filtrovat filmy podle žánru:</label>
          <select value={selectedFilmType} onChange={handlefilmTypeChange}>
            <option value="">Vše</option>
            {filmTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
          <button className='add-film-button' onClick={() => navigate('/admin/pridat')}>
            Add film
          </button>
        </div>
      </div>

      <FilmResult filmyearchResults={currentFilmy} />

      <Pagination
        filmyPerPage={filmyPerPage}
        totalfilmy={(filtrovaneFilmy || []).length}
        currentPage={currentPage}
        paginate={paginate}
      />
    </div>
  );
};

export default ManagefilmPage;