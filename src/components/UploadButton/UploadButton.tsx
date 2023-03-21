import React from "react";
import './UploadButton.scss'
import UploadIcon from '../../../public/UploadButton_icon.svg'

let tab:Array<number> = [0]

export default function UploadButton()
{
  

    return(
        <div className="header__upload-button upload-button"> 
            <label role="button" aria-label="Кнопка завантаження файлів та папок" tabIndex={tab[0]} htmlFor="file-btn" className="upload-button__label">
            <img src={UploadIcon} className="upload-button__icon"alt="Іконка кнопки завантадення" />
                Завантаження файла
            </label>
            <input type="file" className="upload-button__input" id="file-btn" multiple />
        </div>
    )
}