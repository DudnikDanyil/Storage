import React, { useEffect, useState } from 'react';
import './Search.scss';

type val = {
  nameFile: string
}[]

let Search = () =>{
    const [InputValue,setInputValue] = useState('')
    const [value, setData] = useState<val>([]);
   

    function getInputData(event:any): string {
            setInputValue(event.target.value.trim())
            return InputValue
        }

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
//    async function setInputData(value:string):Promise<any> {
//         if(InputValue !== '')
//            {
//            let res = await fetch(`/api/search?value=${value}`)    
//             if (res.ok) {
//                 const data = await res.json();
//                 useEffect(() => {
                  
//                   }, [data]);
//                 // let str:string = ''
//                 // data.map(item =>{
//                 //     str+=`<div className="header__search-item">${item.name}</div>`
//                 //     document.querySelector('.header__search-container').innerHTML = str
//                 // })
//             }
//            }
        // }
        // setInputData(InputValue)

    
            

  return (
    <div className="_wrap">
        <div className="header__search">
            <label className = "header__search-label" htmlFor="search">Search</label>
            <input type="search" className = "header__search-input" id="search" onChange={getInputData}/>
            <div className="header__search-container">
            {value.map(item => (
                      <div key={item.nameFile} className="header__search-item">{item.nameFile}</div>
                    ))}
            </div>
        </div>
    </div>
  );
}

export default Search;
