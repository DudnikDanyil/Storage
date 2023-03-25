import React, { useEffect } from "react";
import './UploadButton.scss'
import UploadIcon from '../../../public/UploadButton_icon.svg'

let tab = [0]

export default function UploadButton({setMyState})
{
    function SubmitFile()
    {
        const file = document.querySelector('.upload-button__input')
        const LiveDate = new Date();
        const data = new FormData() 
        const date = LiveDate.getDate()
        const month = LiveDate.getMonth()
        const year = LiveDate.getFullYear()
        let arr = [...file.files]
        arr.map(item =>{
            data.append('nameFile', item.name);
            data.append('typeFile', item.type);
            data.append('dateFile', `${year}-${month + 1}-${date}`);
            data.append('fileFile', item);
        })
            sentFile(data)  
    }

    async  function sentFile(data)
     {
        if (Array.from(data.entries()).length != 0) {
            let res = await fetch('/loading', {
              method: 'POST',
              body: data,
            })
            // let json = await res.json()
            let json =[{
                nameFile:'123',
                data:'true'
            },
            {
                nameFile:'134',
                data:'true'
            },
            {
                nameFile:'1',
                data:'true'
            },
            ]

            let dataContains = json.map(item => item.data)
            if(dataContains[0]==='true')
            {
            json.map(val => {
                setMyState(prevItems => {
                
                            if (prevItems.some(item => item.nameFile === val.nameFile)) 
                            {
                                return prevItems;
                            } 
                            else 
                            {
                                return [...prevItems, val];
                            }
                        })
                  })
                }
      }
    }
    return(
        <div className="header__upload-button upload-button"> 
            <label role="button" aria-label="Кнопка завантаження файлів та папок" tabIndex={tab[0]} htmlFor="file-btn" className="upload-button__label">
            <img src={UploadIcon} className="upload-button__icon"alt="Іконка кнопки завантадення" />
                Завантаження файла
            </label>
            <input onChange={SubmitFile} type="file" className="upload-button__input" id="file-btn" multiple />
        </div>
    )
}