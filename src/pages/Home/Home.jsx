import React, { useEffect,useState, useRef } from 'react';
import Search from '../../components/Search/Search'
import LogOutButton from '../../components/LogOutButton/LogOutButton'
import UploadButton from '../../components/UploadButton/UploadButton'
import Item from '../../components/Item/Item'
import './Home.scss'
import Icon from '../../../public/Check_icon.svg'

let Home = () =>{
  const [json, setJson] = useState([]);


  return (
   <div className="wrapper">
    <header className='header'>
        <div className="header__row">
          <div className="header__column">
          <UploadButton setMyState={setJson}/>
          <img src={Icon} className = "cloud" alt="" />
          </div>
          <div className="header__column">
          <Search/>
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
           <Item Obj={json} setJson={setJson}/> 
        </section>
      </main>
    </div>
  )
}

export default Home;
