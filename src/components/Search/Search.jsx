import React, { useEffect, useRef, useState } from 'react';
import './Search.scss';
import Icon from '../../../public/Search_icon.svg';



export default function Search () {

    const [InputValue,setInputValue] = useState('')
    const [value, setData] = useState([])
    const [isActive,setIsActive] = useState(false)
    let refBtn = useRef(null)

   function getInputData(event){
            setInputValue(event.target.value)         
        }

  
    useEffect(() => {
      if(InputValue)
    {
      setIsActive(true)
    }
    else{
      setIsActive(false)
    }
    refBtn.current.addEventListener('click', () => {
      setIsActive(false)
      refBtn.current.classList.remove('header__search-reset--show')
    })
  }, [InputValue]);

 
 

        useEffect(() => {
            if(InputValue !== '')
          {
            const fetchData = async () => {
              const response = await fetch(`/api/search?nameFile=${InputValue}`);
              if (response.ok) {
                const result = await response.json();
                setData(result);
              }
            }
            fetchData();
          }}, [InputValue]);


          

  return (
        <div className="header__search">
        <form role="search" autoComplete='off'>
              <img src={Icon} className='search-icon__item' alt="Іконка пошуку" />
              <input type="text" 
                          placeholder = "Пошук докуметів та файлів"
                          className = "header__search-input" 
                          id="search" 
                          onChange={getInputData}
                          />
            <button ref={refBtn} className={`header__search-reset ${isActive ? 'header__search-reset--show' : ''}`} type='reset'></button>
          </form>
        </div>
  );
}

