import React, {useEffect, useRef, useState } from "react";
import './LogOutButton.scss'
import IconUser from "../../../public/User_icon.svg"


export default function LogOutButton()
{
    let [Coordinates,setCoordinates] = useState({x : 0, y : 0})
    let refBtn = useRef(null)
    
    useEffect(()=>{ 
        refBtn.current.addEventListener('click',(event) =>
        {     
            let rect = refBtn.current.getBoundingClientRect()    
            setCoordinates({
            x : event.pageX - (rect.left + scrollX),
            y : event.pageY - (rect.top + scrollY)
            }) 
        })
    },[])
 
        function createObj()
    {
        const ripple = document.createElement("span");
        ripple.style.width = 10 + 'px';
        ripple.style.height = 10 + 'px';
        ripple.style.top = `${Coordinates.y}`+ 'px';
        ripple.style.left = `${Coordinates.x}` + 'px';
        ripple.classList.add('ripple');
        refBtn.current.appendChild(ripple);
        setTimeout(()=>{
            ripple ? ripple.remove() : null
        },510)
    }

 

    return(
        <div className="header__logout">
           <button type="button" ref={refBtn} aria-label="Кнопка виходу з профілю" onClick={createObj} className="header__logout-button">
            <img src={IconUser} alt="Іконка користувача" className="header__logout-icon"/>
            <span className="header__logout-title">Вийти</span>
            </button>
        </div>
    );
}