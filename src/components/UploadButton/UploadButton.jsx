import React, { useEffect } from "react";
import './UploadButton.scss'
import UploadIcon from '../../../public/UploadButton_icon.svg'

let tab = [0]

export default function UploadButton({setJson,setIconState,StateIconArray})
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
        let regEx = /\.+[^.]*$/;

        arr.map(item =>{
            let type = item.name.match(regEx)
            data.append('nameFile', item.name.toLowerCase());
            data.append('sizeFile', item.size);
            data.append('typeFile', type);
            data.append('dateFile', `${year}-${month + 1}-${date}`);
            data.append('fileFile', item);
        })
            sentFile(data)  
    }

    async  function sentFile(data)
     {
        if (Array.from(data.entries()).length != 0) {
            setIconState(StateIconArray[1])
            let res = await fetch('/loading', {
              method: 'POST',
              body: data,
            })
            try{
            setIconState(StateIconArray[0])
            let json = await res.json()
            //  let json = [{nameFile:'123.txt',data:'true',typeFile:".txt"}]
            json.map(item => item.nameFile = item.nameFile.charAt(0).toUpperCase() + item.nameFile.slice(1).toLowerCase());
            let dataContains = json.map(item => item.data)
            dataContains.map(item => {
                if(item == 'true')
                {      
                    json.map(val => {
                        setJson(prevItems => {
                        
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
            })
         }
         catch(e){
            setIconState(StateIconArray[2])
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



