import React, { useEffect, useRef, useState } from "react"
import './Item.scss'
import RedItemIcon from '../../assets/Red-item_icon.svg'
import download from '../../assets/download.svg'
import bucket from '../../assets/bucket.svg'
import pan from '../../assets/pan.svg'



export default function Item()
{
    let ContextMenuIcon:Array<string> = [download,bucket,pan]
    let [isContextMenuShow,setContextMenuShow] = useState(false)
    let refMenu = useRef(null)
    let refMenuBtn = useRef(null)
        function ShowContextMenu()
        {
        const ripple: HTMLSpanElement = document.createElement("span");
        ripple.style.width = 10 + 'px';
        ripple.style.height = 10 + 'px';
        ripple.classList.add('setting__item-rippel');
        refMenuBtn.current.append(ripple);
        setTimeout(()=>{
            ripple ? ripple.remove() : null
        },200)
            setContextMenuShow((prev)=>!prev)

        }

     useEffect(()=>{   
          let handler = (event:MouseEvent) =>
          {
            const target = event.target as HTMLElement;
            if(!refMenu.current.contains(target) && !refMenuBtn.current.contains(target))
            {
            setContextMenuShow(false)
            }
          }
       document.addEventListener('mousedown',handler)
       return () =>
       {
        document.removeEventListener('mousedown',handler)
       }
    })
  

    
    return(
        <div className="container-item">
            <div className="container-item__setting setting" ref={refMenuBtn} onClick={ShowContextMenu}>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
            </div>
            <div className="container-item__icon"> 
                <img src={RedItemIcon} className="container-item__icon-item" alt="" /> 
            </div>   
            <div className="container-item__wrap">
                <span className="container-item__title">Test.exe</span>
                <span className="container-item__size">7.8 MB</span>
            </div>
            <div ref={refMenu} className={`container-item__context-menu context-menu ${isContextMenuShow ? 'context-menu--show' : ''}`}>            
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[0]}/>Завантажити</button>
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[1]}/>Видалити</button>
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[2]}/>Редагувати</button>
            </div>
        </div>
    )
}
