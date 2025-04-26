import React, {useState, useEffect} from "react";
import ApiService from "../../service/ApiService";
import Pagination from "../common/Pagination";
import FilmResult from "../common/FilmResult";
import FilmSearch from "../common/FilmSearch";

const VsechnyFilmyPage = () => {
    const [filmy, setFilmy] = useState([]);
    const [filtrovaneFilmy, setFiltrovaneFilmy] = useState([]);
    const [zanryFilmu, setZanryFilmu] = useState([]);
    const [zanrVybranehoFilmu, setZanrVybranehoFilmu] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [filmyPerPage] = useState(5);

    //funkce pro handling vyhledaných výsledků
    const handleSearchResult = (vysledky) => {
        setFilmy(vysledky);
        setFiltrovaneFilmy(vysledky);
    };

    useEffect(()=>{
        const fetchFilmy = async () => {
        try{
            const odpoved = await ApiService.getVsechnyFilmy();
            const vsechnyFilmy = odpoved.seznamFilmu;
            setFilmy(vsechnyFilmy);
            setFiltrovaneFilmy(vsechnyFilmy);
        }catch(error){
            console.error("Nastala chyba při nacházení filmů: " + error.zprava);
        }
    };

    const fetchZanryFilmu = async () => {
        try{
            const zanry = await ApiService.getVsechnyZanry();
            setZanryFilmu(zanry);
        }catch (error){
            console.error("Nastala chyba při nacházení žánrů: " + error.zprava);
        }
    };
        fetchFilmy();
        fetchZanryFilmu();
    }, []);

    const handleFilmZanrChange = (e) => {
        setZanrVybranehoFilmu(e.target.value);
        filtrovaneFilmy(e.target.value);
    };

    const filtrujFilmy = (zanr) => {
        if (zanr === '') {
            setFiltrovaneFilmy(filmy);
        } else {
            const filtrovane = filmy.filter((film) => film.zanrFilmu === zanr);
            setFiltrovaneFilmy(filtrovane);
        }
        setCurrentPage(1); // resetování na první stránku po filtrování
    };

    //pagination
    const indexPoslednihoFilmu = currentPage * filmyPerPage;
    const indexPrvnihoFilmu = indexPoslednihoFilmu - filmyPerPage;
    const currentFilmy = filtrovaneFilmy.slice(indexPrvnihoFilmu, indexPoslednihoFilmu);

    const paginate = (cisloStranky) => setCurrentPage(cisloStranky);

    return (
        <div className="all-filmy">
            <h2>Všechny filmy</h2>
            <div className="all-film-filter-div">
                <label>Filtrování dle žánru:</label>
                <select value={zanrVybranehoFilmu} onChange={handleFilmZanrChange}>
                    <option value="">Všechny</option>
                    {zanryFilmu.map((zanr)=>(
                        <option key={zanr} value={zanr}>
                            {zanr}
                        </option>
                    ))}
                </select>
            </div>
            <FilmSearch handleSearchResult={handleSearchResult}/>
            <FilmResult filmSearchResults={currentFilmy}/>

            <Pagination
            filmyPerPage={filmyPerPage}
            totalFilmy={filtrovaneFilmy.length}
            currentPage={currentPage}
            paginate={paginate}/>
        </div>
    );
};
export default VsechnyFilmyPage;