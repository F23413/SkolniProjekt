import React from "react";

const Pagination = ({filmyPerPage, totalFilmy, currentPage, paginate}) => {
    const pageNumbers = [];

    for(let i = 1; i < Math.ceil(totalFilmy / filmyPerPage); i++){
        pageNumbers.push(i)
    }
    return (
        <div className="pagination-nav">
            <ul className="pagination-ul">
                {pageNumbers.map((cislo)=>
                <li key={cislo} className="pagination-li">
                    <button onClick={()=> paginate(cislo)} className={`pagination-button ${currentPage === cislo ? 'current-page' : ''}`}>
                        {cislo}
                    </button>
                </li>
                )}
            </ul>
        </div>
    );
}

export default Pagination;