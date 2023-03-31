import React, { useEffect, useRef, useState } from 'react';
import './Search.scss';
import Icon from '../../../public/Search_icon.svg';



export default function Search ({setJson}) {

    const [InputValue,setInputValue] = useState('')
    const [isActive,setIsActive] = useState(false)
    let refBtn = useRef(null)

   function getInputData(event){
   
            setInputValue(event.target.value.toLowerCase())         
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
      const response = await fetch('api/search?nameFile=');
      let json = await response.json()
      json.map(item => {
        if(item.data == 'true')
        {
          json.map(item => item.nameFile = item.nameFile.charAt(0).toUpperCase() + item.nameFile.slice(1).toLowerCase());
          setJson(json)
        }
      })
      
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
                dataContains.map(item => {
                  if(item == 'true')
                  {
                  json.map(item => item.nameFile = item.nameFile.charAt(0).toUpperCase() + item.nameFile.slice(1).toLowerCase());
                  setJson(prev => prev = json)
                  }
                  else
                  {
                    setJson([])
                  }
                })
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
         
                  dataContains.map(item => {
                    if(item == 'true')
                    {
                      json.map(item => item.nameFile = item.nameFile.charAt(0).toUpperCase() + item.nameFile.slice(1).toLowerCase());
                    setJson(prev => prev = json)
                    }
                    else
                    {
                      setJson([])
                    }
                  })
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

