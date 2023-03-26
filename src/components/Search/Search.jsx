import React, { useEffect, useRef, useState } from 'react';
import './Search.scss';
import Icon from '../../../public/Search_icon.svg';



export default function Search ({setJson}) {

    const [InputValue,setInputValue] = useState('')
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
    async function handle() {
      setIsActive(false)
      refBtn.current.classList.remove('header__search-reset--show')       
      const response = await fetch(`/api/search?nameFile=`);
      let json = await response.json()
      let dataContains = json.map(item => item.data)
      if(dataContains[0]==='true')
      {
      setJson(prev => prev = json)
      }
    }
    refBtn.current.addEventListener('click',handle)
         
        return () => {
          refBtn.current.removeEventListener('click',handle)
        };

  }, [InputValue]);

 
 

        useEffect(() => {
            if(InputValue !== '')
          {
            const fetchData = async () => {
              const response = await fetch(`/api/search?nameFile=${InputValue}`);
              if (response.ok) {
                const json = await response.json();
                let dataContains = json.map(item => item.data)
                if(dataContains[0]==='true')
                {
                setJson(prev => prev = json)
                }
              }
            }
            fetchData();

            }
            else{
              const fetchData = async () => {
                const response = await fetch(`/api/search?nameFile=${InputValue}`);
                if (response.ok) {
                  const json = await response.json();
                  let dataContains = json.map(item => item.data)
                  if(dataContains[0]==='true')
                  {
                  setJson(prev => prev = json)
                  }
                }
              }
              fetchData();
            }
          }, [InputValue]);


          

  return (
        <div className="header__search">
        <form role="search" autoComplete='off' onSubmit={(event)=> event.preventDefault()}>
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

