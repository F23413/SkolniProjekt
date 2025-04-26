import React from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";

const FilmResult = ({filmSearchResults})=>{
    const navigate = useNavigate(); // inicializace hooku useNavigate
    const isAdmin = ApiService.isAdmin();
    return(
        <section className="film-results">
            {filmSearchResults && filmSearchResults.length > 0 &&  (
                <div className="film-list">
                {filmSearchResults.map(film =>(
                    <div key={film.id} className="film-list-item">
                        <img className='film-list-item-image' src={film.obrazekFilmu} alt={film.zanrFilmu}/>
                        <div className="film-details">
                            <h3>{film.nazevFilmu}</h3>
                            <p>Cena: {film.cenaFilmu} Kč na jedno vypůjčení</p>
                            <p>Popis: {film.popisFilmu}</p>
                        </div>

                        <div className='pujcit-now-div'>
                            {isAdmin ?(
                                <button className="edit-film-button" onClick={()=> navigate(`/admin/edit-film/${film.id}`)}>Upravit data filmu</button>
                            ):(
                                <button className="pujcit-now-button" onClick={()=> navigate(`/film-details-pujcit/${film.id}`)}>Zobrazit nebo zapůjčit film</button>
                            )}
                        </div>
                    </div>
                ))}
            </div>
            )}
        </section>
    )
}
export default FilmResult;