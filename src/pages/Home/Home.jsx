import React, { useEffect,useState, useRef } from 'react';
import Search from '../../components/Search/Search'
import LogOutButton from '../../components/LogOutButton/LogOutButton'
import UploadButton from '../../components/UploadButton/UploadButton'
import Item from '../../components/Item/Item'
import CheckCloudIconcon from '../../../public/CheckCloudIcon.svg'
import DownloadCloudIcon from '../../../public/DownloadCloudIcon.svg'
import ErrorCloudIcon from '../../../public/ErrorCloudIcon.svg'
import UploadCloudIcon from '../../../public/UploadCloudIcon.svg'
import './Home.scss'

 let StateIconArray = [CheckCloudIconcon,DownloadCloudIcon,ErrorCloudIcon,UploadCloudIcon]

let Home = () =>{
  const [json, setJson] = useState([])
  const [IconState,setIconState] = useState(StateIconArray[0])
  return (
   <div className="wrapper">
    <header className='header'>
        <div className="header__row">
          <div className="header__column">
          <UploadButton setJson={setJson} setIconState={setIconState} StateIconArray={StateIconArray}/>
          <img src={IconState} className = "cloud" alt="" />
          </div>
          <div className="header__column">
          <Search setJson={setJson}/>
          </div>
          <div className="header__column">
          <LogOutButton/>
          </div>
        </div>
      </header>
      <main className='content'>
        <section className='filters'>
        </section>
        <section className='items'>     
           <Item Obj={json} setJson={setJson} setIconState={setIconState} StateIconArray={StateIconArray}/> 
        </section>
      </main>
    </div>
  )
}

export default Home;
