import React from 'react';
import Search from '../../components/Search/Search';
import LogOutButton from '../../components/LogOutButton/LogOutButton';
import UploadButton from '../../components/UploadButton/UploadButton'
import './Home.scss'

let Home = () =>{
  return (
    <header className='header'>
         <UploadButton/>
         <Search/>
         <LogOutButton/>
    </header>
  );
}

export default Home;
