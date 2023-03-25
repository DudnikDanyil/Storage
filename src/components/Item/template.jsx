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

    let refMenu = useRef(null)
    let refMenuBtn = useRef(null)



   
        function ShowContextMenu(index)
        {
           
        // const ripple = document.createElement("span");
        // ripple.style.width = 10 + 'px';
        // ripple.style.height = 10 + 'px';
        // ripple.classList.add('setting__item-rippel');
        // refMenuBtn.current.append(ripple);
        // setTimeout(()=>{
        //     ripple ? ripple.remove() : null
        // },200)
          
        setContextMenuShow(true);
        setSelectedItemIndex(index);
        }

        function deleteFile(index) {
            const newJson = Obj.filter((item, idx) => idx !== selectedItemIndex)
            setContextMenuShow(false)
            setJson(newJson);  
          }

    useEffect(()=>{   
        let handler = (event) =>
          {
            const target = event.target;
           if(refMenu.current !== null)
           {
            if(!refMenu.current.contains(target) && !refMenuBtn.current.contains(target))
            {
            setContextMenuShow(false)
            }
        }
          }
       document.addEventListener('mousedown',handler)
       return () =>
       {
        document.removeEventListener('mousedown',handler)
       }
    },[])
 


    return(
    <div className="container">
        {Obj.map((item,index)=>{ 
        return(
        <div className="container-item" key={item.nameFile} >
            <div className="container-item__setting setting" ref={refMenuBtn} onClick={() => ShowContextMenu(index)}>
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
            <div ref={refMenu} className={`container-item__context-menu context-menu ${isContextMenuShow && selectedItemIndex === index ? 'context-menu--show' : ''}`}>            
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[0]}/>Завантажити</button>
                <button className="context-menu__item"  onClick={() => deleteFile(index)}><img className="context-menu__image" src={ContextMenuIcon[1]}/>Видалити</button>
                <button className="context-menu__item"><img className="context-menu__image" src={ContextMenuIcon[2]}/>Редагувати</button>
            </div>
        </div> )
      })}
      </div>
    )
}
