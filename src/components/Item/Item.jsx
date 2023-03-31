import React, {useEffect, useRef, useState } from "react"
import download from '../../assets/download.svg'
import bucket from '../../assets/bucket.svg'
import pan from '../../assets/pan.svg'
import './Item.scss'
import RedItemIcon from '../../assets/RedItemIcon.svg'
import ContentIcon from '../../assets/ContentIcon.svg'



export default function Item({Obj,setJson,setIconState,StateIconArray})
{
    const [isContextMenuShow, setContextMenuShow] = useState(false);
    const [selectedItemIndex, setSelectedItemIndex] = useState(null);
    const [editInput, setEditInput] = useState('');
    const [editIndex, seteditIndex] = useState(0);
    const [currentValue, setCurrentValue] = useState('');
    const itemEls = useRef([])
    let refItem = useRef([])
    let refMenu = useRef([])
    let refIcon = useRef([])
    

        function ShowContextMenu(index)
        {
           
                const ripple = document.createElement("span");
                ripple.style.width = 10 + 'px';
                ripple.style.height = 10 + 'px';
                ripple.classList.add('setting__item-rippel');
                itemEls.current[index].append(ripple);
                setTimeout(() => {
                  ripple ? ripple.remove() : null
                }, 200)

            setContextMenuShow(prev => !prev);     
            setSelectedItemIndex(index)
        }

        useEffect(()=>{  
            let handler = (event) =>{    
                itemEls.current.map((item,index) => {
                    if(itemEls.current[index] != null)
                    {
                    if(index === selectedItemIndex)
                        {
                    if (!itemEls.current[index].contains(event.target)) {
                                   
                            if(!refMenu.current[index].contains(event.target))
                            {
                        setContextMenuShow(false)  
                        }
                    }
                        }
                    }      
                  })
                
             }
          
                document.addEventListener('mousedown',handler)
                      
             return () =>
             {
                 document.removeEventListener('mousedown',handler)
             }
            })


      async  function deleteFile() {
        let nameFile = ''
            const newJson = Obj.filter((item, idx) => {
                if(idx !== selectedItemIndex)
                {
                    return item
                }
                else
                {
                    nameFile = item.nameFile
                }
            })
            setContextMenuShow(false)
            setJson(newJson)

            await fetch(`/deleteFile?nameFile=${nameFile.toLowerCase()}`, {method: 'DELETE'})
          }

        function editingFile(index)
        {
            seteditIndex(index)
            let content = refItem.current[index].textContent;
            setCurrentValue(content)
            let input = document.createElement('input')
            input.classList.add('container-item__input')
            input.value = content
            input.type = "text"
            input.style.backgroundColor = "#050B17"
            input.style.color = "#fff"
            input.style.width = "120px"
            refItem.current[index].textContent = ''
            refItem.current[index].append(input)
            setEditInput(content)
            setSelectedItemIndex(index)
            setContextMenuShow(false)
        }

        useEffect(()=>{
 
            let input = document.querySelector('.container-item__input')
            if(input != null)
            {
            let getInput = (event) =>
            {
                setEditInput(event.target.value)
            }
            async function writeValue(event)
            { 
                if (event.type === 'blur' || event.key === 'Enter') 
                {    
                input.remove()  
                refItem.current[editIndex].append(editInput);
                refItem.current.map((item,index)=>{
                    if(index === editIndex)
                    {
                        item.attributes[0].value = editInput
                    }
                })
                Obj.map((item,index)=>{
                    if(index === editIndex)
                    {
                        item.nameFile = editInput
                    }
                })
                let res = await fetch(`/editing?oldNameFile=${currentValue.toLowerCase()}&newNameFile=${editInput.toLowerCase()}`, {method: 'PUT'})
                setTypeFile(json.typeFile)

                }
            }
           
                input.addEventListener('input', getInput)
                input.addEventListener('keypress', writeValue)
                input.addEventListener('blur', writeValue)
            
            return () =>{
                input.removeEventListener('input', getInput)
                input.removeEventListener('keypress', writeValue)
                input.removeEventListener('blur', writeValue)
            }
        }
        }) 

         async function downloadFile(index) {
            setIconState(StateIconArray[3])
            try{
            let content = refItem.current[index].textContent;
            let res = await fetch(`api/file/download?nameFile=${content}`)
            const contentType = res.headers.get('content-type');
            if (contentType.includes('application/json')) {
                 let json = res.json();
                 json.map(item => {
                    if(item.data == 'false')
                    {
                        setIconState(StateIconArray[2]) 
                    }
                 })
              } else if (contentType.includes('application/octet-stream')) {   
                setIconState(StateIconArray[0])
                const File = await res.blob()
                refItem.current[index].setAttribute('href', URL.createObjectURL(File))
                refItem.current[index].setAttribute('download', `${content}`)
                refItem.current[index].click()
                refItem.current[index].removeAttribute("download");
                refItem.current[index].removeAttribute('href')
                setSelectedItemIndex(index)
                setContextMenuShow(false)
              }
            }
            catch(e)
            {
                setIconState(StateIconArray[2]) 
            }
          }

        useEffect(()=>{
            let container = document.querySelector('.container')
            function DropFile(event)
            {
                event.preventDefault();
                container.style.border = 'none'
                document.querySelector(".container__content").style.opacity = "0.2";
                let files = event.dataTransfer.files;
                const data = new FormData() 
                const LiveDate = new Date();
                const date = LiveDate.getDate()
                const month = LiveDate.getMonth()
                const year = LiveDate.getFullYear()
                let regEx = /\.+[^.]*$/;
                const FileArray = [...files]
                
                FileArray.map(item =>{
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
            function DragOver(event)
            {
                event.preventDefault();
                container.style.border = 'dashed #ACACAC'
                document.querySelector(".container__content").style.opacity = "0.3";
            }
            function DragLeave()
            {
                container.style.border = 'none'
                document.querySelector(".container__content").style.opacity = "0.2";
            }
            function DragStart(event)
            {
                event.preventDefault();
            }
            container.addEventListener("drop", DropFile)
            container.addEventListener("dragover", DragOver)
            document.addEventListener("dragstart", DragStart)
            container.addEventListener("dragleave", DragLeave)
            return () => {
                container.removeEventListener('drop', DropFile);
                container.removeEventListener('dragover', DragOver);
                document.removeEventListener("dragstart", DragStart)
                container.removeEventListener("dragleave", DragLeave)
              };
        },[])
//   useEffect(()=>{
//     let ContainerItemIcon = document.querySelectorAll('.container-item__icon')
//     let icons = [RedItemIcon,OrangeItemIcon,BlueItemIcon]
//     refIcon.current.map((item,idx) =>{
//     let rand = Math.floor(Math.random() * icons.length)
//     let mainIcon = icons[rand]
//     item.src = mainIcon
//     ContainerItemIcon.forEach((item,index) =>{
//     if(idx === index)
//     {
//         if(mainIcon == RedItemIcon)
//         {
//             item.classList.add('container-item__icon--red')
//         }
//         else if(mainIcon == OrangeItemIcon)
//         {
//             item.classList.add('container-item__icon--orange')
//         }
//         else
//         {
//             item.classList.add('container-item__icon--blue')
//         }
//     }
//     })
//     })
//   },[Obj])
      
   
    return(
    <div className="container">
        <div className="container__content">
            <img src={ContentIcon} alt="" className="container__content-image"/>
            <span className="container__content-title">Завантажити файл</span>
            <p className="container__content-subtitle">Ви можете завантажити файл, перенісши його сюди</p>
        </div>
        {Obj.map((item,index)=>{ 
        return(
        <figure className="container-item" key={item.nameFile} >
            <div className="container-item__setting setting" ref={(element) => itemEls.current[index] = element} onClick={() => ShowContextMenu(index)}>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
                <span className='setting__item'></span>
            </div>
            <div className="container-item__icon container-item__icon--red"> 
                <img src={RedItemIcon} ref={el => refIcon.current[index] = el}className="container-item__icon-item" alt="" /> 
            </div>   
            <div className="container-item__wrap">
                <a title={item.nameFile} ref={el => refItem.current[index] = el} className="container-item__title">{item.nameFile}</a>
                <span className="container-item__size">{item.sizeFile+ ' МБ'}</span>
            </div>
            <div ref={el => refMenu.current[index] = el} className={`container-item__context-menu context-menu ${isContextMenuShow && selectedItemIndex === index ? 'context-menu--show' : ''}`}>            
                <button className="context-menu__item" onClick={()=>downloadFile(index)}><img className="context-menu__image" src={download}/>Завантажити</button>
                <button className="context-menu__item" onClick={deleteFile}><img className="context-menu__image" src={bucket}/>Видалити</button>
                <button className="context-menu__item" onClick={() => editingFile(index)}><img className="context-menu__image" src={pan}/>Редагувати</button>
            </div>
        </figure> )
      })}
      </div>
    )
}
