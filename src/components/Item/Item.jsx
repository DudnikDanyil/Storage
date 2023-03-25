import React, { useEffect, useRef, useState } from "react"
import './Item.scss'
import RedItemIcon from '../../assets/Red-item_icon.svg'
import download from '../../assets/download.svg'
import bucket from '../../assets/bucket.svg'
import pan from '../../assets/pan.svg'



export default function Item({Obj,setJson})
{

    let ContextMenuIcon = [download,bucket,pan]
    const [isContextMenuShow, setContextMenuShow] = useState(false);
    const [selectedItemIndex, setSelectedItemIndex] = useState(null);
    const itemEls = useRef([])
 
        function ShowContextMenu(index,ref)
        {
            if (ref.current && ref.current[index]) 
            {
                const ripple = document.createElement("span");
                ripple.style.width = 10 + 'px';
                ripple.style.height = 10 + 'px';
                ripple.classList.add('setting__item-rippel');
                ref.current[index].append(ripple);
                setTimeout(() => {
                  ripple ? ripple.remove() : null
                }, 200)
              }
        
  
        setSelectedItemIndex(index)
        setContextMenuShow(true)
        }

        function deleteFile() {
            const newJson = Obj.filter((item, idx) => idx !== selectedItemIndex)
            setContextMenuShow(false)
            setJson(newJson);  
          }

        useEffect(()=>{  
            let handler = (event) =>{
               if(event.target.className === 'context-menu' || event.target.className === 'context-menu__item')
               {
                setContextMenuShow(true)
               } 
               else{
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
    <div className="container">
        {Obj.map((item,index)=>{ 
        return(
        <div className="container-item" key={item.nameFile} >
            <div className="container-item__setting setting" ref={(element) => itemEls.current[index] = element} onClick={() => ShowContextMenu(index, itemEls)}>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
            </div>
            <div className="container-item__icon"> 
                <img src={RedItemIcon} className="container-item__icon-item" alt="" /> 
            </div>   
            <div className="container-item__wrap">
                <span className="container-item__title">{item.nameFile}</span>
                <span className="container-item__size">7.8 MB</span>
            </div>
            <div className={`container-item__context-menu context-menu ${isContextMenuShow && selectedItemIndex === index ? 'context-menu--show' : ''}`}>            
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[0]}/>Завантажити</button>
                <button className="context-menu__item" onClick={deleteFile}><img className="context-menu__image" src={ContextMenuIcon[1]}/>Видалити</button>
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[2]}/>Редагувати</button>
            </div>
        </div> )
      })}
      </div>
    )
}
